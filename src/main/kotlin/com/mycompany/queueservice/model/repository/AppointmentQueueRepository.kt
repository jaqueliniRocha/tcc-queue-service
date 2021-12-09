package com.mycompany.queueservice.model.repository

import com.mycompany.queueservice.model.AppointmentQueue
import com.mycompany.queueservice.model.Pet
import com.mycompany.queueservice.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AppointmentQueueRepository : CrudRepository<AppointmentQueue, Long> {
    fun findAllByOrderByPositionAsc(): LinkedList<AppointmentQueue>
    fun findFirstByOrderByPositionDesc(): AppointmentQueue?
    fun findByCustomerId(userId: Long): AppointmentQueue?
    fun existsByCustomerAndPet(customer: User, pet: Pet?): Boolean
}