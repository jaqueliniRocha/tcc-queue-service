package com.mycompany.queueservice.model

import java.time.Duration
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import kotlin.jvm.Transient

@Entity
data class AppointmentQueue(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,
    @Transient var estimatedRemainingTime: Duration?,
    @field:NotNull @field:Min(0) var position: Int,

    @OneToOne
    @field:NotNull var customer : User,
) {

}