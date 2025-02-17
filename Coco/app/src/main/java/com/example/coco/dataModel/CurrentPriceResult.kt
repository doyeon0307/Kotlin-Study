package com.example.coco.dataModel

data class CurrentPriceResult(

    val coinName: String,
    val coinInfo: CurrentPrice

)

//  "status": "0000",
//  "data": {
//    "BTC": {
//      "opening_price": "154710000",
//      "closing_price": "156987000",
//      "min_price": "154160000",
//      "max_price": "158600000",
//      "units_traded": "542.11014204",
//      "acc_trade_value": "85163110342.92038",
//      "prev_closing_price": "154804000",
//      "units_traded_24H": "1049.4322686",
//      "acc_trade_value_24H": "163785773649.45263",
//      "fluctate_24H": "3835000",
//      "fluctate_rate_24H": "2.5"
//    },
//    "ETH": {
//      "opening_price": "4927000",
//      "closing_price": "4954000",
//      "min_price": "4893000",
//      "max_price": "4987000",
//      "units_traded": "6310.401079560177903449",
//      "acc_trade_value": "31176750139.089995655574512",
//      "prev_closing_price": "4929000",
//      "units_traded_24H": "13417.477025717252246131",
//      "acc_trade_value_24H": "66155166947.146323312493182",
//      "fluctate_24H": "79000",
//      "fluctate_rate_24H": "1.62"
//    },
//    "ETC": {
//      "opening_price": "39650",
//      "closing_price": "40230",
//      "min_price": "39550",
//      "max_price": "41190",
//      "units_traded": "108175.0950615995228113",
//      "acc_trade_value": "4374764485.71282087381030774",
//      "prev_closing_price": "39670",
//      "units_traded_24H": "192093.943487007736281233",
//      "acc_trade_value_24H": "7646919503.26901988145240671",
//      "fluctate_24H": "2000",
//      "fluctate_rate_24H": "5.23"
//    },
