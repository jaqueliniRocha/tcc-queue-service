package com.mycompany.queueservice.infrastructure.messaging

import com.mycompany.queueservice.application.AppointmentQueueService
import com.mycompany.queueservice.model.Serializer
import com.mycompany.queueservice.model.User
import com.mycompany.queueservice.model.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.stereotype.Service

@Service
class UserCreatedListener (
    private val messageConverter: MessageConverter,
    private val userRepository: UserRepository,
    private val appointmentQueueService: AppointmentQueueService,
    private val serializer: Serializer
){

    private val log = LoggerFactory.getLogger(javaClass)

    @RabbitListener(queues = ["user-management.created"])
    fun create(message: Message) {
        val message = messageConverter.fromMessage(message)
        val user = serializer.deserialize(serializer.serialize(message), User::class.java)
        userRepository.save(user)
        appointmentQueueService.updatePositionsInQueue()
        log.info("received user created: $user")
    }

}