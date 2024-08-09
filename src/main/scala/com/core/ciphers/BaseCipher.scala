package com.core.ciphers

import com.core.cipherdata._

/** Base class for all ciphers.
  * @tparam T
  *   The input type of the CipherDataBlock for the cipher, `CipherDataBlock[T]`.
  * @tparam K
  *   The output type of the CipherDataBlock for the cipher, `CipherDataBlock[K]`.
  * @tparam V
  *   The type of the key used in the cipher.
  */
trait BaseCipher[T, K, V] {
    def encrypt(data: CipherDataBlock[T], key: V): CipherResult[T, K]
    def decrypt(data: CipherDataBlock[K], key: V): CipherResult[K, T]
}
