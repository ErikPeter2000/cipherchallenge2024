package com.core.cipherdata

import munit.FunSuite
import com.core.alphabets.UppercaseLetters

class CipherResultTest extends FunSuite {

  test("CipherResult should be created with inData and outData as CipherDataBlock") {
    val inData = new CipherDataBlock[Char](UppercaseLetters)
    val outData = new CipherDataBlock[Char](UppercaseLetters)
    val result = CipherResult.create(inData, outData)
    
    assertEquals(result.inData, inData)
    assertEquals(result.outData, outData)
    assertEquals(result.status, CipherResultStatus.Success)
  }

  test("CipherResult should be created with inData as CipherDataBlock and outData as Seq") {
    val inData = new CipherDataBlock[Char](UppercaseLetters)
    val outData = Seq('A', 'B', 'C')
    val result = CipherResult.create(inData, outData, UppercaseLetters)
    
    assertEquals(result.inData, inData)
    assertEquals(result.outData, new CipherDataBlock[Char](outData, UppercaseLetters))
    assertEquals(result.status, CipherResultStatus.Success)
  }

  test("CipherResult should have status Unspecified by default") {
    val result = new CipherResult[Char, Char]()
    
    assertEquals(result.status, CipherResultStatus.Unspecified)
  }
}