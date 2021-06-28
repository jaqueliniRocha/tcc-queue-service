package com.mycompany.queueservice.model

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AppointmentQueueRepository : CrudRepository<AppointmentQueue, Long> {
    fun findAllByOrderByPositionAsc(): LinkedList<AppointmentQueue>
    fun existsByCustomerEmail(email: String?): Boolean
    fun findFirstByOrderByPositionDesc(): AppointmentQueue?
    fun findByCustomerId(userId: Long): AppointmentQueue
    fun existsByCustomerId(customerId: Long): Boolean
}