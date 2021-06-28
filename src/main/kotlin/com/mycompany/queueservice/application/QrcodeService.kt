package com.mycompany.queueservice.application

import com.google.zxing.BarcodeFormat
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import org.springframework.stereotype.Service
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

@Service
class QrcodeService(
) {
    fun generateQRCodeImage(barcodeText: String): ByteArray {
        val barcodeWriter = QRCodeWriter()
        val bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 200, 200)
        return toByteArray(MatrixToImageWriter.toBufferedImage(bitMatrix))
    }

    fun toByteArray(bi: BufferedImage): ByteArray {
        val baos = ByteArrayOutputStream()
        ImageIO.write(bi, "png", baos)
        return baos.toByteArray()
    }
}