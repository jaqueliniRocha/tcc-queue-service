package com.mycompany.queueservice.infrastructure

import com.mycompany.queueservice.application.AppointmentQueueService
import com.mycompany.queueservice.model.AppointmentQueue
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity.created
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping("/appointmentQueue")
class AppointmentQueueApi(
    private val appointmentQueueService: AppointmentQueueService
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping
    fun create(
        @Valid @RequestBody queueAppointmentQueue: AppointmentQueue,
        uriComponentsBuilder: UriComponentsBuilder
    ): HttpEntity<Any?> {
        log.info("creating appointment $queueAppointmentQueue")
        val savedUser = appointmentQueueService.create(queueAppointmentQueue)
        log.info("finished with id $queueAppointmentQueue.id")
        return created(uriComponentsBuilder.path("/appointment/{id}").buildAndExpand(savedUser.id).toUri()).build()
    }

    @GetMapping("/user/{id}")
    fun findByUserId(
        @PathVariable id: Long
    ): HttpEntity<Any?> {
        log.info("finding appointment with user id $id")
        return ok(appointmentQueueService.findByUser(id))
    }

    @DeleteMapping()
    fun remove(
    ): HttpEntity<Any?> {
        val removed = appointmentQueueService.remove()
        log.info("remover user id ${removed?.id} from queue")
        return ok(removed)
    }
}