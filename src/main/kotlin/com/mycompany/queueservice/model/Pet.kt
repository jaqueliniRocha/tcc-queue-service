package com.mycompany.queueservice.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@Entity
data class Pet (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,

    @field:NotBlank
    val name: String,

    @field:NotBlank
    var breed: String

 ) {

    override fun toString(): String {
        return "Pet(id=$id, name='$name', breed='$breed')"
    }
}
