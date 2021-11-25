package com.mycompany.queueservice.infrastructure.rest

class AddToQueueRequest(
    val customerId: Long
) {
    override fun toString(): String {
        return "AddToQueueRequest(customerId=$customerId)"
    }
}