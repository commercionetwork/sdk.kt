package network.commercio.sdk.crypto

import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.SecureRandom
import java.security.Security
import java.security.spec.ECGenParameterSpec
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec


/**
 * Allows to easily generate new keys either to be used with AES or RSA key.
 */
object KeysHelper {

    init {
        Security.insertProviderAt(BouncyCastleProvider(), 1)
    }

    /**
     * Generates a new random AES-256 secret key without any initializing vector.
     */
    fun generateAesKey(): SecretKey {
        return KeyGenerator.getInstance("AES").apply {
            init(256)
        }.generateKey()
    }

    fun recoverAesKey(bytes: ByteArray): SecretKey {
        return SecretKeySpec(bytes, "AES")
    }

    /**
     * Generates a new RSA key pair having the given [bytes] length.
     * If no length is specified, the default is going to be 2048.
     */
    fun generateRsaKeyPair(bytes: Int = 2048): KeyPair {
        return KeyPairGenerator.getInstance("RSA").apply {
            initialize(bytes)
        }.generateKeyPair()
    }

    fun generateEcKeyPair(): KeyPair {
        return KeyPairGenerator.getInstance("EC", "BC").apply {
            initialize(ECGenParameterSpec("secp256k1"), SecureRandom())
        }.generateKeyPair()
    }
}