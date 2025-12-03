package com.olr261dn.data.di.utils

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.security.keystore.StrongBoxUnavailableException
import android.util.Base64
import androidx.core.content.edit
import java.security.KeyStore
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey


internal object EncryptionService {
    const val DATABASE_NAME = "secure_app_database.db"
    private const val KEY_ALIAS = "your_key_alias"
    private const val ENCRYPTED_SEED_PREF_KEY = "encrypted_database_seed"

    fun getPassphrase(context: Context): ByteArray {
        val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
        if (!keyStore.containsAlias(KEY_ALIAS)) {
            generateSecretKeyWithStrongBoxFallback()
        }
        val secretKey = keyStore.getKey(KEY_ALIAS, null) as SecretKey
        val encryptedSeedWithIv = getOrCreateEncryptedSeed(context, secretKey)
        val passphrase = MessageDigest.getInstance("SHA-256").digest(encryptedSeedWithIv)
        return passphrase
    }

    private fun getOrCreateEncryptedSeed(context: Context, secretKey: SecretKey): ByteArray {
        val prefs = context.getSharedPreferences("encryption_prefs", Context.MODE_PRIVATE)
        val storedSeed = prefs.getString(ENCRYPTED_SEED_PREF_KEY, null)

        return if (storedSeed != null) {
            Base64.decode(storedSeed, Base64.DEFAULT)
        } else {
            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            val encryptedSeed = cipher.doFinal("database_seed".toByteArray())
            val encryptedSeedWithIv = encryptedSeed + cipher.iv
            val encodedSeed = Base64.encodeToString(encryptedSeedWithIv, Base64.DEFAULT)
            prefs.edit { putString(ENCRYPTED_SEED_PREF_KEY, encodedSeed) }
            encryptedSeedWithIv
        }
    }

    private fun generateSecretKeyWithStrongBoxFallback() {
        KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            "AndroidKeyStore"
        ).apply {
            try {
                init(getKeyGenParameterSpecBuilder().setIsStrongBoxBacked(true).build())
                generateKey()
            } catch (_: StrongBoxUnavailableException) {
                init(getKeyGenParameterSpecBuilder().build())
                generateKey()
            }
        }
    }

    private fun getKeyGenParameterSpecBuilder(): KeyGenParameterSpec.Builder {
        return KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
    }
}