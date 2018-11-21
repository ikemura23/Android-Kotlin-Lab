package com.ikemura.android_kotlin_lab

/**
 * 天気予報のためのクラス
 */
class WeatherForCast {
    val statellite = Satellite() //気象情報クラス

    /**
     * 雨の時のみ真を返す
     */
    fun sholdBringUmbrella(): Boolean {
        val weather = statellite.getWeather() //天気予報を取得
        return when (weather) {
            Weather.SUNNY, Weather.CLOUDY -> false
            Weather.RAINY -> true
        }
    }
}

class Satellite {
    fun getWeather(): Weather {
        return Weather.RAINY
    }
}

enum class Weather {
    SUNNY, CLOUDY, RAINY
}