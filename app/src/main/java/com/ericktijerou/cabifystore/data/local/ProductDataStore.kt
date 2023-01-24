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
package com.ericktijerou.cabifystore.data.local

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.ericktijerou.cabifystore.data.entity.ProductModel
import com.ericktijerou.cabifystore.data.entity.toLocal
import com.ericktijerou.cabifystore.data.local.dao.ProductDao
import com.ericktijerou.cabifystore.data.local.entity.ProductEntity
import com.ericktijerou.cabifystore.data.local.system.AppDatabase
import com.ericktijerou.cabifystore.data.local.system.PagingManager

class ProductDataStore(
    private val dao: ProductDao,
    private val database: AppDatabase,
    private val pagingManager: PagingManager
) {
    fun getProductList(): PagingSource<Int, ProductEntity> {
        return dao.getAll()
    }

    suspend fun saveProductList(list: List<ProductModel>) {
        dao.insert(*list.map { it.toLocal() }.toTypedArray())
    }

    suspend fun doOperationInTransaction(method: suspend () -> Unit) {
        database.withTransaction {
            method()
        }
    }

    suspend fun clearProducts() {
        dao.clearAll()
    }

    fun getLastProductCursor(): Int = pagingManager.lastProductCursor

    fun setLastProductCursor(value: Int) {
        pagingManager.lastProductCursor = value
    }
}