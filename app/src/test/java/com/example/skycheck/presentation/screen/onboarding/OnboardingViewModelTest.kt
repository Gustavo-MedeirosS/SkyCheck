package com.example.skycheck.presentation.screen.onboarding

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.example.skycheck.data.repository_impl.DataStoreRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class OnboardingViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private val dispatcher = StandardTestDispatcher()

    private lateinit var context: android.content.Context
    private lateinit var fakeDataStoreManager: FakeDataStoreManager
    private lateinit var dataStoreRepository: DataStoreRepositoryImpl
    private lateinit var viewModel: OnboardingViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher = dispatcher)
        context = ApplicationProvider.getApplicationContext()
        fakeDataStoreManager = FakeDataStoreManager(context = context)
        dataStoreRepository = DataStoreRepositoryImpl(dataStoreManager = fakeDataStoreManager)
        viewModel = OnboardingViewModel(dataStoreRepository = dataStoreRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `When click in start button, the onboardingDone should be save as true`() = runTest {
        viewModel.onButtonClick()
        advanceUntilIdle()
        assert(viewModel.getOnboardingDone())
    }
}