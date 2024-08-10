package com.core.data

class DataTablesTests extends munit.FunSuite {
    test("Can access all data tables") {
        val unigrams = DataTable.unigramFrequencies.take(5).toSeq
        val bigrams = DataTable.bigramFrequencies.take(5).toSeq
        val trigrams = DataTable.trigramFrequencies.take(5).toSeq
        val quadgrams = DataTable.quadgramFrequencies.take(5).toSeq
        val commonWords = DataTable.iterateCommonWords.take(5).toSeq
    }  
}