package com.mycompany.queueservice.model

import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import java.time.Duration
import javax.persistence.*
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import kotlin.jvm.Transient

@Entity
data class AppointmentQueue(
    @Transient var estimatedRemainingTime: Duration?,

    @field:NotNull @field:Min(0) var position: Int,

    @OneToOne
    @field:NotNull var customer: User,

    @field:Valid
    @OneToOne
    val pet: Pet?

) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null

}