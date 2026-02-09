package com.alogram.payrisk

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import java.security.MessageDigest

object WebhookVerifier {
    private const val HMAC_SHA256 = "HmacSHA256"

    fun verify(payload: ByteArray, headerSignature: String?, secret: String?): Boolean {
        if (headerSignature == null || secret == null || secret.isEmpty()) {
            return false
        }

        return try {
            val mac = Mac.getInstance(HMAC_SHA256)
            val secretKeySpec = SecretKeySpec(secret.toByteArray(Charsets.UTF_8), HMAC_SHA256)
            mac.init(secretKeySpec)
            val hashBytes = mac.doFinal(payload)
            
            val expectedSignature = hashBytes.joinToString("") { "%02x".format(it) }

            MessageDigest.isEqual(
                expectedSignature.toByteArray(Charsets.UTF_8), 
                headerSignature.toByteArray(Charsets.UTF_8)
            )
        } catch (e: Exception) {
            false
        }
    }
}
