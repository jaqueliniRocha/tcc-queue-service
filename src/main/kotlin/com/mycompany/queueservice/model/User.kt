package com.mycompany.queueservice.model

import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
data class User(
    @Id val id: Long,
    @field:NotBlank val firstName: String,
    @field:NotBlank val lastName: String,
    @field:Email @field:NotBlank @field:Column(unique=true) val email: String,
    @field:NotBlank var password: String,

    @Enumerated(EnumType.STRING)
    @field:NotNull
    val category: UserCategory,

    @Enumerated(EnumType.STRING)
    @field:NotNull
    val profile: UserProfile,
) {

    override fun toString(): String {
        return "User(id=$id, firstName='$firstName', lastName='$lastName', email='$email', category=$category)"
    }
}