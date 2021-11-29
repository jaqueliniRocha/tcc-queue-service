package com.mycompany.queueservice.model.repository

import com.mycompany.queueservice.model.User
import com.mycompany.queueservice.model.UserCategory
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long> {
    fun findByCpfAndCategory(cpf: String, customer: UserCategory): User?
}