package com.mycompany.queueservice.infrastructure.rest

class AddToQueueRequest(
    val cpf: String,
    val petId: Long
) {
    override fun toString(): String {
        return "AddToQueueRequest(cpf='$cpf', petId=$petId)"
    }
}