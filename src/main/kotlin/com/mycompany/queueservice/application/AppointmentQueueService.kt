package com.mycompany.queueservice.application

import com.mycompany.queueservice.model.AppointmentQueue
import com.mycompany.queueservice.model.AppointmentQueueRepository
import com.mycompany.queueservice.model.UserAlreadyExistsException
import com.mycompany.queueservice.model.UserRepository
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.temporal.ChronoUnit

@Service
class AppointmentQueueService(
    val appointmentQueueRepository: AppointmentQueueRepository,
    val userRepository: UserRepository
) {

    companion object {
        private const val APPOINTMENT_DURATION_IN_MIN: Int = 20
    }

    fun create(appointmentQueue: AppointmentQueue): AppointmentQueue {
        appointmentQueue.customer = userRepository.findById(appointmentQueue.customer.id).get()
        if(appointmentQueueRepository.existsByCustomerId(appointmentQueue.customer.id)){
            throw UserAlreadyExistsException()
        }
        val queue = appointmentQueueRepository.findFirstByOrderByPositionDesc()
        if(queue == null){
            appointmentQueue.position = 1
        } else {
            appointmentQueue.position = queue.position + 1
        }
        return appointmentQueueRepository.save(appointmentQueue)
    }

    fun findByUser(userId: Long): AppointmentQueue {
        val queueAppointment = appointmentQueueRepository.findByCustomerId(userId)
        queueAppointment.estimatedRemainingTime =
            Duration.of((queueAppointment.position * APPOINTMENT_DURATION_IN_MIN).toLong(), ChronoUnit.MINUTES)
        return queueAppointment
    }

    fun remove(): AppointmentQueue? {
        val queue = appointmentQueueRepository.findAllByOrderByPositionAsc()
        if(queue.isEmpty()){
            return null
        }
        val removedItem = queue.remove()
        appointmentQueueRepository.deleteById(removedItem.id)
        var counter = 1
        for (o in queue){
            o.position = counter
            appointmentQueueRepository.save(o)
            counter += 1
        }
        return removedItem
    }
}