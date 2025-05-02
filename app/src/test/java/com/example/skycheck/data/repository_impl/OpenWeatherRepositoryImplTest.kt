package com.example.skycheck.data.repository_impl

import com.example.skycheck.data.api.OpenWeatherApi
import com.example.skycheck.data.mocks.OpenWeatherMocks
import com.example.skycheck.data.model.dto.ForecastDto
import com.example.skycheck.data.model.dto.GeocodeLocationDto
import com.example.skycheck.data.model.dto.Next5DaysForecastDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class OpenWeatherRepositoryImplTest {

    private lateinit var repository: OpenWeatherRepositoryImpl
    private lateinit var openWeatherApi: OpenWeatherApi

    @Before
    fun setUp() {
        openWeatherApi = mockk<OpenWeatherApi>()
        repository = OpenWeatherRepositoryImpl(openWeatherApi)
    }

    @Test
    fun `getCurrentForecast returns success when API responds successfully`() = runTest {
        // given
        val lat = -23.5506507
        val lng = -46.6333824
        val expectedResponse: Response<ForecastDto> = OpenWeatherMocks.successForecastResponse()

        coEvery {
            openWeatherApi.getCurrentForecast(lat = lat, lon = lng, apiKey = any(), lang = any())
        } returns expectedResponse

        // when
        val result = repository.getCurrentForecast(lat = lat, lng = lng)

        // then
        Assert.assertEquals(expectedResponse.body(), result.body())
        coVerify(exactly = 1) {
            openWeatherApi.getCurrentForecast(lat = lat, lon = lng, apiKey = any(), lang = any())
        }
    }

    @Test
    fun `getNext5DaysForecast returns success when API responds successfully`() = runTest {
        // given
        val lat = -23.5506507
        val lng = -46.6333824
        val expectedResponse: Response<Next5DaysForecastDto> =
            OpenWeatherMocks.successNext5DaysResponse()

        coEvery {
            openWeatherApi.getNext5DaysForecast(lat = lat, lon = lng, apiKey = any(), lang = any())
        } returns expectedResponse

        // when
        val result = repository.getNext5DaysForecast(lat = lat, lng = lng)

        // then
        Assert.assertEquals(expectedResponse.body(), result.body())
        coVerify(exactly = 1) {
            openWeatherApi.getNext5DaysForecast(lat = lat, lon = lng, apiKey = any(), lang = any())
        }
    }

    @Test
    fun `getGeocodeFromText returns success when API responds successfully`() = runTest {
        // given
        val query = "São Paulo"
        val limit = 1
        val expectedResponse: Response<List<GeocodeLocationDto>> =
            OpenWeatherMocks.successGeocodeResponse(limit = limit)

        coEvery {
            openWeatherApi.getGeocodeFromText(
                query = query,
                limit = limit,
                apiKey = any(),
                lang = any()
            )
        } returns expectedResponse

        // when
        val result = repository.getGeocodeFromText(query = query, limit = limit)

        // then
        Assert.assertEquals(expectedResponse.body(), result.body())
        Assert.assertEquals(limit, result.body()?.size)
        coVerify(exactly = 1) {
            openWeatherApi.getGeocodeFromText(
                query = query,
                limit = limit,
                apiKey = any(),
                lang = any()
            )
        }
    }

    @Test
    fun `getCurrentForecast returns error when API doesn't respond successfully`() = runTest {
        // given
        val query: String = 123.toString()
        val limit = 1
        val expectedResponse = OpenWeatherMocks.errorResponse<List<GeocodeLocationDto>>()

        coEvery {
            openWeatherApi.getGeocodeFromText(
                query = query,
                limit = limit,
                apiKey = any(),
                lang = any()
            )
        } returns expectedResponse

        // when
        val result = repository.getGeocodeFromText(query = query, limit = limit)

        // then
        Assert.assertEquals(expectedResponse.body(), result.body())
        Assert.assertEquals(404, result.code())
    }
}