package com.mycompany.queueservice.infrastructure.rest

import com.mycompany.queueservice.application.AppointmentQueueService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity.*
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
        @Valid @RequestBody request: AddToQueueRequest,
        uriComponentsBuilder: UriComponentsBuilder
    ): HttpEntity<Any?> {
        log.info("creating appointment $request")
        val savedUser = appointmentQueueService.create(request.customerId)
        log.info("finished with id $request.id")
        return created(uriComponentsBuilder.path("/appointment/{id}").buildAndExpand(savedUser.id).toUri()).build()
    }

    @GetMapping("/user/{id}")
    fun findByUserId(
        @PathVariable id: Long
    ): HttpEntity<Any?> {
        log.info("finding appointment with user id $id")
        return ok(appointmentQueueService.findByUser(id))
    }

    @GetMapping()
    fun findAll(
    ): HttpEntity<Any?> {
        log.info("finding queue")
        return ok(appointmentQueueService.findAll())
    }

    @DeleteMapping()
    fun remove(
    ): HttpEntity<Any?> {
        val removed = appointmentQueueService.removeFirst()
        log.info("removed user id ${removed?.id} from queue")
        return ok(removed)
    }

    @DeleteMapping("/user/{id}")
    fun remove(
        @PathVariable id: Long
    ): HeadersBuilder<*> {
        appointmentQueueService.remove(id)
        log.info("removed user id $id from queue")
        return noContent()
    }
}