package com.example.skycheck.data.mocks

import com.example.skycheck.data.model.dto.ForecastDto
import com.example.skycheck.data.model.dto.GeocodeLocationDto
import com.example.skycheck.data.model.dto.Next5DaysForecastDto
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

object OpenWeatherMocks {
    private fun mockForecastDto(): ForecastDto {
        return ForecastDto(
            weather = listOf(
                ForecastDto.Weather(
                    main = "Rain",
                    description = "light rain",
                    icon = "10n"
                )
            ),
            main = ForecastDto.MainForecast(
                temp = 285.87,
                feelsLike = 285.42,
                minTemperature = 285.87,
                maxTemperature = 286.16,
                humidity = 85
            ),
            wind = ForecastDto.Wind(
                speed = 2.56
            ),
            clouds = ForecastDto.Clouds(
                all = 52
            ),
            datetime = 1745528400L,
            sys = ForecastDto.SystemInfo(
                sunrise = 1745528400L,
                sunset = 1745528400L
            ),
            timezone = 0,
            localityName = "Nome da Localidade",
            cod = 200,
            datetimeText = "2025-04-24 21:00:00"
        )
    }

    private fun mockNext5DaysForecastDto(): Next5DaysForecastDto {
        return Next5DaysForecastDto(
            list = listOf(
                ForecastDto(
                    weather = listOf(
                        ForecastDto.Weather(
                            main = "Rain",
                            description = "light rain",
                            icon = "10n"
                        )
                    ),
                    main = ForecastDto.MainForecast(
                        temp = 285.87,
                        feelsLike = 285.42,
                        minTemperature = 285.87,
                        maxTemperature = 286.16,
                        humidity = 85
                    ),
                    wind = ForecastDto.Wind(
                        speed = 2.56
                    ),
                    clouds = ForecastDto.Clouds(
                        all = 52
                    ),
                    datetime = 1745528400L,
                    sys = ForecastDto.SystemInfo(
                        sunrise = 1745528400L,
                        sunset = 1745528400L
                    ),
                    timezone = 0,
                    localityName = "Nome da Localidade",
                    cod = 200,
                    datetimeText = "2025-04-24 21:00:00"
                ),
                ForecastDto(
                    weather = listOf(
                        ForecastDto.Weather(
                            main = "Rain",
                            description = "light rain",
                            icon = "10n"
                        )
                    ),
                    main = ForecastDto.MainForecast(
                        temp = 285.87,
                        feelsLike = 285.42,
                        minTemperature = 285.87,
                        maxTemperature = 286.16,
                        humidity = 85
                    ),
                    wind = ForecastDto.Wind(
                        speed = 2.56
                    ),
                    clouds = ForecastDto.Clouds(
                        all = 52
                    ),
                    datetime = 1745528400L,
                    sys = ForecastDto.SystemInfo(
                        sunrise = 1745528400L,
                        sunset = 1745528400L
                    ),
                    timezone = 0,
                    localityName = "Nome da Localidade",
                    cod = 200,
                    datetimeText = "2025-04-24 21:00:00"
                )
            )
        )
    }

    private fun mockGeocodeLocationDtoList(limit: Int): List<GeocodeLocationDto> {
        return List(size = limit) {
            GeocodeLocationDto(
                name = "São Paulo",
                country = "BR",
                state = "São Paulo",
                lat = -23.5506507,
                lon = -46.6333824,
            )
        }
    }

    fun successForecastResponse(): Response<ForecastDto> {
        return Response.success(mockForecastDto())
    }

    fun successNext5DaysResponse(): Response<Next5DaysForecastDto> {
        return Response.success(mockNext5DaysForecastDto())
    }

    fun successGeocodeResponse(limit: Int): Response<List<GeocodeLocationDto>> {
        return Response.success(mockGeocodeLocationDtoList(limit))
    }

    fun <T> errorResponse(): Response<T> {
        return Response.error(404, "Not found".toResponseBody(null))
    }
}