package com.mycompany.queueservice.infrastructure.rest

import com.mycompany.queueservice.model.UserCategory
import com.mycompany.queueservice.model.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserApi(
    private val userRepository: UserRepository
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/customer/{cpf}")
    fun findCustomerByCpf(
        @PathVariable cpf: String
    ): HttpEntity<Any?> {
        log.info("finding user with cpf $cpf")
        return ok(userRepository.findByCpfAndCategory(cpf, UserCategory.CUSTOMER))
    }
}