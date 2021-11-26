package com.mycompany.queueservice.application

import com.mycompany.queueservice.model.AppointmentQueue
import com.mycompany.queueservice.model.NotFoundException
import com.mycompany.queueservice.model.repository.AppointmentQueueRepository
import com.mycompany.queueservice.model.UserAlreadyExistsException
import com.mycompany.queueservice.model.UserCategory
import com.mycompany.queueservice.model.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class AppointmentQueueService(
    val appointmentQueueRepository: AppointmentQueueRepository,
    val userRepository: UserRepository
) {

    fun create(customerId: Long): AppointmentQueue {
        val customer = userRepository.findByIdAndCategory(customerId, UserCategory.CUSTOMER) ?: throw NotFoundException()

        var position = 0
        if(appointmentQueueRepository.existsByCustomerId(customerId)){
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
                estimatedRemainingTime = Duration.ZERO,
                customer = customer
            )
        )
    }

    fun findAll(): Collection<AppointmentQueue>? {
        return appointmentQueueRepository.findAllByOrderByPositionAsc()
    }

    fun findByUser(userId: Long): AppointmentQueue? {
        val queueAppointment = appointmentQueueRepository.findByCustomerId(userId)
        queueAppointment?.estimatedRemainingTime =
            Duration.of(10, ChronoUnit.MINUTES)
        return queueAppointment
    }

    fun removeFirst(): AppointmentQueue? {
        val queue = appointmentQueueRepository.findAllByOrderByPositionAsc()
        if(queue.isEmpty()){
            return null
        }
        val removedItem = queue.remove()
        removedItem.id?.let { appointmentQueueRepository.deleteById(it) }
        updatePositionsInQueue(queue)
        return removedItem
    }

    fun remove(customerId: Long) {
        var appointment = appointmentQueueRepository.findByCustomerId(customerId) ?: throw NotFoundException()
        appointmentQueueRepository.delete(appointment)
        updatePositionsInQueue(appointmentQueueRepository.findAllByOrderByPositionAsc())
    }

    private fun updatePositionsInQueue(queue: LinkedList<AppointmentQueue>) {
        var counter = 1
        for (appointment in queue) {
            appointment.position = counter
            appointmentQueueRepository.save(appointment)
            counter += 1
        }
    }
}