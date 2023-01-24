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
package com.ericktijerou.cabifystore.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ericktijerou.cabifystore.data.local.entity.BagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BagDao {
    @Query("SELECT * FROM Bag")
    fun getAll(): Flow<List<BagEntity>>

    @Query("SELECT * FROM Bag WHERE code = :id")
    suspend fun getProductById(id: String): BagEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg products: BagEntity)

    @Query("UPDATE Bag SET quantity = :quantity WHERE code = :id")
    suspend fun update(id: String, quantity: Int)

    @Query("SELECT SUM(quantity) FROM Bag")
    fun getBagSize(): Flow<Int?>

    @Query("DELETE FROM Bag WHERE code = :productId")
    fun remove(productId: String)
}