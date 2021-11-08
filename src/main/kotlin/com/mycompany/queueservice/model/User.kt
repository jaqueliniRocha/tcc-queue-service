package com.mycompany.queueservice.model

import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import javax.persistence.*
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
data class User(
    @Id val id: Long,
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    var password: String?,

    @Enumerated(EnumType.STRING)
    val category: UserCategory?,

    @Enumerated(EnumType.STRING)
    val profile: UserProfile?,

    @field:NotNull
    @field:Valid
    @Cascade(CascadeType.ALL)
    @OneToMany
    @JoinColumn(name = "user_id")
    val pets: Collection<Pet>,
) {

    override fun toString(): String {
        return "User(id=$id, firstName=$firstName, lastName=$lastName, email=$email, password=$password, " +
                "category=$category, profile=$profile, pets=$pets)"
    }
}