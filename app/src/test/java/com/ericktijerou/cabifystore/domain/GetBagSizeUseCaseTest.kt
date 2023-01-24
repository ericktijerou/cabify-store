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
package com.ericktijerou.cabifystore.domain

import com.ericktijerou.cabifystore.domain.repository.BagRepository
import com.ericktijerou.cabifystore.domain.usecase.GetBagSizeUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class GetBagSizeUseCaseTest {

    private val repository = mockk<BagRepository>()
    private val useCase = GetBagSizeUseCase(repository)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN GetBagSize WHEN repository returns Success THEN returns data`() = runTest {
        val expected: Flow<Int> = flow { emit(3) }
        coEvery { repository.getBagSize() } returns expected
        val output = useCase()
        output.first()
        coVerify { repository.getBagSize() }
        Assert.assertEquals(1, output.count())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test(expected = Throwable::class)
    fun `GIVEN GetBagSize WHEN repository returns Failure THEN throw exception`() = runTest {
        val exception = Exception()
        coEvery { repository.getBagSize() } throws exception
        useCase().first()
    }
}