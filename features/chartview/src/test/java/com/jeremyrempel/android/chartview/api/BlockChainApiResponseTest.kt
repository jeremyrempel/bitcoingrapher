package com.jeremyrempel.android.chartview.api

import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import org.intellij.lang.annotations.Language
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BlockChainApiResponseTest {

    private lateinit var moshi: Moshi

    @Before
    fun setup() {
        moshi = Moshi.Builder().build()
    }

    @Test
    fun `test parse valid json`() {

        @Language("JSON")
        val input = """{
  "status": "ok",
  "name": "Transaction Rate",
  "unit": "Transactions Per Second",
  "period": "minute",
  "description": "The number of Bitcoin transactions added to the mempool per second.",
  "values": [
    {
      "x": 1570901940,
      "y": 3.761875000000005
    },
    {
      "x": 1570903980,
      "y": 3.780868055555561
    }
  ]
}""".trimIndent()

        val actualResult = BlockChainApiResponseJsonAdapter(moshi).fromJson(input)

        val expectedResult = BlockChainApiResponse(
            "ok",
            "Transaction Rate",
            "Transactions Per Second",
            "minute",
            "The number of Bitcoin transactions added to the mempool per second.",
            listOf(
                BlockChainApiResponse.Value(
                    1570901940, 3.761875000000005
                ),
                BlockChainApiResponse.Value(
                    1570903980, 3.780868055555561
                )
            )
        )

        assertEquals(expectedResult, actualResult)
    }

    @Test(expected = JsonDataException::class)
    fun `test parse invalid json with missing status`() {
        @Language("JSON")
        val input = """{
  "name": "Transaction Rate",
  "unit": "Transactions Per Second",
  "period": "minute",
  "description": "The number of Bitcoin transactions added to the mempool per second.",
  "values": [
    {
      "x": 1570901940,
      "y": 3.761875000000005
    },
    {
      "x": 1570903980,
      "y": 3.780868055555561
    }
  ]
}""".trimIndent()

        // fail whale status is reqd field
        BlockChainApiResponseJsonAdapter(moshi).fromJson(input)
    }
}
