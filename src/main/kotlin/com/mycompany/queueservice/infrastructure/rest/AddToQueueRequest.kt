package com.mycompany.queueservice.infrastructure.rest

class AddToQueueRequest(
    val cpf: String
) {
    override fun toString(): String {
        return "AddToQueueRequest(cpf=$cpf)"
    }
}