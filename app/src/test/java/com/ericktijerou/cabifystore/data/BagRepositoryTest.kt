/*
 * Copyright 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ericktijerou.cabifystore.data

import com.ericktijerou.cabifystore.data.local.BagDataStore
import com.ericktijerou.cabifystore.data.repository.BagRepositoryImpl
import com.ericktijerou.cabifystore.mock.ModelMock
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class BagRepositoryTest {

    private val dataStore = mockk<BagDataStore>()
    private val userRepository = BagRepositoryImpl(dataStore)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN Repository WHEN getBagList THEN model is filled`() = runTest {
        val expected = flowOf(listOf(ModelMock.productBagModel, ModelMock.productBagModel))
        coEvery { dataStore.getBagList() } returns expected
        val response = userRepository.getBagList().first()
        Assert.assertNotNull(response)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN Repository WHEN getBagSize THEN number is returned`() = runTest {
        val expected = flowOf(3)
        coEvery { dataStore.getBagSize() } returns expected
        val response = userRepository.getBagSize().first()
        Assert.assertNotNull(response)
    }
}