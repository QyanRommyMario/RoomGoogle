/*
 * Copyright (C) 2023 The Android Open Source Project
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

package com.example.inventory.data

import kotlinx.coroutines.flow.Flow

/**
 * Implementasi offline dari ItemsRepository yang menggunakan Room Database
 * sebagai sumber data lokal melalui ItemDao.
 *
 * Kelas ini mengimplementasikan ItemsRepository interface dan bertindak sebagai
 * single source of truth untuk data aplikasi dalam mode offline.
 *
 * @property itemDao Data Access Object untuk operasi database
 *
 * Karakteristik implementasi:
 * - Hanya menggunakan penyimpanan lokal
 * - Thread-safe operations
 * - Reactive data streams dengan Flow
 */
class OfflineItemsRepository(private val itemDao: ItemDao) : ItemsRepository {
    /**
     * Mengambil semua item dari database lokal.
     * Menggunakan function delegation untuk langsung meneruskan
     * ke itemDao.getAllItems()
     *
     * @return Flow<List<Item>> Stream dari list item yang akan
     *         update otomatis saat ada perubahan data
     */
    override fun getAllItemsStream(): Flow<List<Item>> = itemDao.getAllItems()

    /**
     * Mengambil item spesifik berdasarkan ID dari database lokal.
     * Menggunakan function delegation untuk efisiensi
     *
     * @param id ID dari item yang dicari
     * @return Flow<Item?> Stream dari item yang akan update
     *         otomatis saat ada perubahan
     */
    override fun getItemStream(id: Int): Flow<Item?> = itemDao.getItem(id)

    /**
     * Menyimpan item baru ke database lokal.
     * Menggunakan suspend function untuk operasi asynchronous
     *
     * @param item Item baru yang akan disimpan
     */
    override suspend fun insertItem(item: Item) = itemDao.insert(item)

    /**
     * Menghapus item dari database lokal.
     * Menggunakan suspend function untuk operasi asynchronous
     *
     * @param item Item yang akan dihapus
     */
    override suspend fun deleteItem(item: Item) = itemDao.delete(item)

    /**
     * Memperbarui item dalam database lokal.
     * Menggunakan suspend function untuk operasi asynchronous
     *
     * @param item Item dengan data terbaru
     */
    override suspend fun updateItem(item: Item) = itemDao.update(item)
}

/**
 * Catatan implementasi:
 * - Menggunakan function delegation untuk clean code
 * - Semua operasi database dijalankan di background thread
 * - Tidak ada transformasi data tambahan
 * - Implementasi langsung dari interface contract
 * - Single responsibility: hanya menangani operasi database lokal
 */