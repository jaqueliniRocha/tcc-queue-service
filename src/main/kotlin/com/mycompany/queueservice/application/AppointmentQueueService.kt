package com.mycompany.queueservice.application

import com.mycompany.queueservice.model.AppointmentQueue
import com.mycompany.queueservice.model.NotFoundException
import com.mycompany.queueservice.model.repository.AppointmentQueueRepository
import com.mycompany.queueservice.model.UserAlreadyExistsException
import com.mycompany.queueservice.model.UserCategory
import com.mycompany.queueservice.model.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.util.CollectionUtils
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class AppointmentQueueService(
    val appointmentQueueRepository: AppointmentQueueRepository,
    val userRepository: UserRepository
) {

    companion object{
        private val APPOINTMENT_DURATION: Int = 20
    }

    fun create(cpf: String, petId: Long): AppointmentQueue {
        val customer = userRepository.findByCpfAndCategory(cpf, UserCategory.CUSTOMER) ?: throw NotFoundException()
        val pet = customer.pets?.filter { it.id == petId }?.first()
        var position: Int
        if(appointmentQueueRepository.existsByCustomerAndPet(customer, pet)){
            throw UserAlreadyExistsException()
        }
        val queue = appointmentQueueRepository.findFirstByOrderByPositionDesc()
        if(queue == null){
            position = 1
        } else {
            position = queue.position + 1
        }
        return appointmentQueueRepository.save(
            AppointmentQueue(
                position = position,
                estimatedRemainingTime = calculatesEstimatedRemainingTime(position),
                customer = customer,
                pet = customer.pets?.find { p -> p.id == petId }
            )
        )
    }

    private fun calculatesEstimatedRemainingTime(position: Int): Int {
        val veterinaries = userRepository.findByCategory(UserCategory.VETERINARY)
        return position.minus(1).div(veterinaries.size).times(APPOINTMENT_DURATION);
    }

    fun findAll(): Collection<AppointmentQueue>? {
        return appointmentQueueRepository.findAllByOrderByPositionAsc()
    }

    fun findByUser(userId: Long): AppointmentQueue? {
        return appointmentQueueRepository.findByCustomerId(userId)
    }

    fun findById(id: Long): AppointmentQueue? {
        return appointmentQueueRepository.findById(id).get()
    }

    fun removeFirst(): AppointmentQueue? {
        val queue = appointmentQueueRepository.findAllByOrderByPositionAsc()
        if(queue.isEmpty()){
            return null
        }
        val removedItem = queue.remove()
        removedItem.id?.let { appointmentQueueRepository.deleteById(it) }
        updatePositionsInQueue()
        return removedItem
    }

    fun remove(queueId: Long) {
        var appointment = appointmentQueueRepository.findById(queueId)
        appointmentQueueRepository.delete(appointment.get())
        updatePositionsInQueue()
    }

    fun updatePositionsInQueue() {
        var queue = appointmentQueueRepository.findAllByOrderByPositionAsc();
        var counter = 1
        for (appointment in queue) {
            appointment.position = counter
            appointment.estimatedRemainingTime = calculatesEstimatedRemainingTime(appointment.position)
            appointmentQueueRepository.save(appointment)
            counter += 1
        }
    }
}