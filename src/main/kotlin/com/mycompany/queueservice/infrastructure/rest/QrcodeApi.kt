package com.mycompany.queueservice.infrastructure.rest

import com.mycompany.queueservice.application.QrcodeService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*
import java.net.InetAddress
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/qrcode")
class QrcodeApi(
    private val qrcodeService: QrcodeService
) {

    @GetMapping(value = ["/user/{id}"], produces = [MediaType.IMAGE_PNG_VALUE])
    @ResponseBody
    fun generateQrcode(@PathVariable id: Long): ResponseEntity<ByteArray> {
        return ok(qrcodeService.generateQRCodeImage(generateUrl(id)))
    }

    private fun generateUrl(id: Long) = InetAddress.getLocalHost().hostAddress + "/appointmentQueue/user/$id"
}