package com.mycompany.queueservice.model.repository

import com.mycompany.queueservice.model.AppointmentQueue
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AppointmentQueueRepository : CrudRepository<AppointmentQueue, Long> {
    fun findAllByOrderByPositionAsc(): LinkedList<AppointmentQueue>
    fun findFirstByOrderByPositionDesc(): AppointmentQueue?
    fun findByCustomerId(userId: Long): AppointmentQueue?
    fun existsByCustomerId(customerId: Long): Boolean
}