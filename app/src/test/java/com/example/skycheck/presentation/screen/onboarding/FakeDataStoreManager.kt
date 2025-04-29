package com.example.skycheck.presentation.screen.onboarding

import android.content.Context
import com.example.skycheck.data.manager.DataStoreManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakeDataStoreManager(context: Context) : DataStoreManager(context = context) {
    private val onboardingDone = MutableStateFlow<Boolean?>(null)

    override suspend fun setOnboardingDone() {
        onboardingDone.update { true }
    }

    override fun getOnboardingDone(): Flow<Boolean?> {
        return onboardingDone
    }
}