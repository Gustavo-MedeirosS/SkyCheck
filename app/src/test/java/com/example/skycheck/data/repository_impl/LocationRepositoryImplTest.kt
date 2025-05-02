package com.example.skycheck.data.repository_impl

import android.location.Location
import com.example.skycheck.data.mocks.LocationMocks
import com.example.skycheck.data.model.dao.LocationDao
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class LocationRepositoryImplTest {

    private lateinit var locationDao: LocationDao
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var repository: LocationRepositoryImpl
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        locationDao = mockk()
        fusedLocationClient = mockk()
        repository = LocationRepositoryImpl(fusedLocationClient, locationDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        stopKoin()
    }

    @Test
    fun testGetCurrentLocation_success() = runTest {
        // given
        val mockTask = mockk<Task<Location>>(relaxed = true)
        val mockLocation = LocationMocks.mockCurrentLocation()

        every {
            fusedLocationClient.getCurrentLocation(100, any())
        } returns mockTask

        every {
            mockTask.addOnSuccessListener(any())
        } answers {
            val listener = arg<OnSuccessListener<Location>>(0)
            listener.onSuccess(mockLocation)
            mockTask
        }

        // when
        val result = repository.getCurrentLocation()

        // then
        Assert.assertNotNull(result)
        Assert.assertEquals(1.23, result?.latitude)
        Assert.assertEquals(4.56, result?.longitude)
    }

    @Test
    fun testGetSavedLocations_success() = runTest {
        // given
        val expectedResponse = LocationMocks.mockSavedLocations()
        coEvery { locationDao.getLocations() } returns flowOf(expectedResponse)

        // given
        val result = repository.getSavedLocations()

        // given
        Assert.assertEquals(expectedResponse, result)
        Assert.assertEquals(expectedResponse.size, result.size)
        coVerify(exactly = 1) { locationDao.getLocations() }
    }

    @Test
    fun testSaveLocation_success() = runTest {
        // given
        val locationToSave = LocationMocks.mockLocation()
        val prevSavedLocations = LocationMocks.mockSavedLocations()
        val expectedLocationsList = LocationMocks.mockSavedLocations().plus(locationToSave)
        coEvery { locationDao.saveLocation(locationToSave) } returns locationToSave.id!!.toLong()

        // when
        val result = repository.saveLocation(locationToSave)

        // then
        Assert.assertTrue(prevSavedLocations.size < expectedLocationsList.size)
        Assert.assertTrue(expectedLocationsList.contains(locationToSave))
        Assert.assertEquals(locationToSave.id!!.toLong(), result)
        coVerify(exactly = 1) { locationDao.saveLocation(locationToSave) }
    }

    @Test
    fun testDeleteLocation_success() = runTest {
        // given
        val locationToDelete = LocationMocks.mockLocation()
        val prevSavedLocations = LocationMocks.mockSavedLocations()
        val expectedLocationsList = LocationMocks.mockSavedLocations().minus(locationToDelete)
        coEvery { locationDao.deleteLocation(locationToDelete) } just Runs

        // when
        repository.deleteLocation(locationToDelete)

        // then
        Assert.assertTrue(prevSavedLocations.size > expectedLocationsList.size)
        Assert.assertTrue(!expectedLocationsList.contains(locationToDelete))
        coVerify(exactly = 1) { locationDao.deleteLocation(locationToDelete) }
    }
}