package com.xyz.app.ui.currency_list

import com.google.gson.Gson
import com.xyz.app.domain.CurrencyInfo


object CurrencyInfoFactory {
    fun list(): List<CurrencyInfo> {
        return Gson().fromJson(testSuit, Array<CurrencyInfo>::class.java).asList()
    }
}

const val testSuit = """
    [
      {
        "id": "BTC",
        "name": "Bitcoin",
        "symbol": "BTC"
      },
      {
        "id": "ETH",
        "name": "Ethereum",
        "symbol": "ETH"
      },
      {
        "id": "XRP",
        "name": "XRP",
        "symbol": "XRP"
      },
      {
        "id": "BCH",
        "name": "Bitcoin Cash",
        "symbol": "BCH"
      },
      {
        "id": "LTC",
        "name": "Litecoin",
        "symbol": "LTC"
      },
      {
        "id": "EOS",
        "name": "EOS",
        "symbol": "EOS"
      },
      {
        "id": "BNB",
        "name": "Binance Coin",
        "symbol": "BNB"
      },
      {
        "id": "LINK",
        "name": "Chainlink",
        "symbol": "LINK"
      },
      {
        "id": "NEO",
        "name": "NEO",
        "symbol": "NEO"
      },
      {
        "id": "ETC",
        "name": "Ethereum Classic",
        "symbol": "ETC"
      },
      {
        "id": "ONT",
        "name": "Ontology",
        "symbol": "ONT"
      },
      {
        "id": "CRO",
        "name": "Crypto.com Chain",
        "symbol": "CRO"
      },
      {
        "id": "CUC",
        "name": "Cucumber",
        "symbol": "CUC"
      },
      {
        "id": "USDC",
        "name": "USD Coin",
        "symbol": "USDC"
      }
    ]    
"""
