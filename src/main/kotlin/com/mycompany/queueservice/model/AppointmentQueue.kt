package com.mycompany.queueservice.model

import javax.persistence.*
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Entity
data class AppointmentQueue(
    var estimatedRemainingTime: Int?,

    @field:NotNull @field:Min(0) var position: Int,

    @OneToOne
    @field:NotNull var customer: User,

    @field:Valid
    @OneToOne
    val pet: Pet?

) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null

}