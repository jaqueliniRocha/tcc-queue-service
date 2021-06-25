package com.mycompany.queueservice.model

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long> {
    fun findByEmail(currentUserEmail: String?): User
    fun existsByEmail(email: String): Boolean
}