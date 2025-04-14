enum class WeatherCondition(
    val code: Int,
    val day: String,
    val night: String,
    val icon: Int
) {
    SUNNY(1000, "Sunny", "Clear", 113),
    PARTLY_CLOUDY(1003, "Partly cloudy", "Partly cloudy", 116),
    CLOUDY(1006, "Cloudy", "Cloudy", 119),
    OVERCAST(1009, "Overcast", "Overcast", 122),
    MIST(1030, "Mist", "Mist", 143),
    PATCHY_RAIN(1063, "Patchy rain possible", "Patchy rain possible", 176),
    PATCHY_SNOW(1066, "Patchy snow possible", "Patchy snow possible", 179),
    PATCHY_SLEET(1069, "Patchy sleet possible", "Patchy sleet possible", 182),
    PATCHY_FREEZING_DRIZZLE(1072, "Patchy freezing drizzle possible", "Patchy freezing drizzle possible", 185),
    THUNDERY_OUTBREAKS(1087, "Thundery outbreaks possible", "Thundery outbreaks possible", 200),
    BLOWING_SNOW(1114, "Blowing snow", "Blowing snow", 227),
    BLIZZARD(1117, "Blizzard", "Blizzard", 230),
    FOG(1135, "Fog", "Fog", 248),
    FREEZING_FOG(1147, "Freezing fog", "Freezing fog", 260),
    PATCHY_LIGHT_DRIZZLE(1150, "Patchy light drizzle", "Patchy light drizzle", 263),
    LIGHT_DRIZZLE(1153, "Light drizzle", "Light drizzle", 266),
    FREEZING_DRIZZLE(1168, "Freezing drizzle", "Freezing drizzle", 281),
    HEAVY_FREEZING_DRIZZLE(1171, "Heavy freezing drizzle", "Heavy freezing drizzle", 284),
    PATCHY_LIGHT_RAIN(1180, "Patchy light rain", "Patchy light rain", 293),
    LIGHT_RAIN(1183, "Light rain", "Light rain", 296),
    MODERATE_RAIN(1186, "Moderate rain at times", "Moderate rain at times", 299),
    HEAVY_RAIN(1195, "Heavy rain", "Heavy rain", 308),
    LIGHT_SNOW(1210, "Light snow", "Light snow", 323),
    MODERATE_SNOW(1213, "Moderate snow", "Moderate snow", 326),
    HEAVY_SNOW(1225, "Heavy snow", "Heavy snow", 338),
    ICE_PELLETS(1237, "Ice pellets", "Ice pellets", 350),
    THUNDERSTORM(1273, "Patchy light rain with thunder", "Patchy light rain with thunder", 386),
    MODERATE_THUNDERSTORM(1276, "Moderate or heavy rain with thunder", "Moderate or heavy rain with thunder", 389);

    companion object {
        fun fromCode(code: Int): WeatherCondition? =
            entries.find { it.code == code }
    }
}
