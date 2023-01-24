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
package com.ericktijerou.cabifystore.di

import com.ericktijerou.cabifystore.domain.repository.BagRepository
import com.ericktijerou.cabifystore.domain.repository.DiscountRepository
import com.ericktijerou.cabifystore.domain.repository.ProductRepository
import com.ericktijerou.cabifystore.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object DomainModule {
    @Provides
    @ActivityRetainedScoped
    fun provideGetProductListUseCase(repository: ProductRepository): GetProductListUseCase {
        return GetProductListUseCase(repository)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideAddProductToBagUseCase(repository: BagRepository): AddProductToBagUseCase {
        return AddProductToBagUseCase(repository)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideGetBagListUseCase(repository: BagRepository): GetBagListUseCase {
        return GetBagListUseCase(repository)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideIncreaseProductBagUseCase(repository: BagRepository): UpdateProductBagUseCase {
        return UpdateProductBagUseCase(repository)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideGetBagSizeUseCase(repository: BagRepository): GetBagSizeUseCase {
        return GetBagSizeUseCase(repository)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideCheckoutTotalUseCase(
        repository: BagRepository,
        discountRepository: DiscountRepository
    ): CheckoutTotalUseCase {
        return CheckoutTotalUseCase(repository, discountRepository)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideRemoveProductBagUseCase(
        repository: BagRepository
    ): RemoveProductBagUseCase {
        return RemoveProductBagUseCase(repository)
    }
}