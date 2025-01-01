package com.core.ciphers

import com.core.cipherdata.CipherDataBlock

/** Base class for all ciphers.
  * @tparam T
  *   The input (plaintext) type of the CipherDataBlock for the cipher, `CipherDataBlock[T]`.
  * @tparam K
  *   The output (ciphertext) type of the CipherDataBlock for the cipher, `CipherDataBlock[K]`.
  * @tparam V
  *   The type of the key used in the cipher.
  */
abstract trait BaseCipher[T, K, V] {
    def encrypt(data: CipherDataBlock[T], key: V): CipherDataBlock[K]
    def decrypt(data: CipherDataBlock[K], key: V): CipherDataBlock[T]
}
