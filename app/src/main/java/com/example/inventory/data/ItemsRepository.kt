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
 * Interface Repository yang mendefinisikan kontrak untuk operasi data Item.
 * Mengikuti pattern Repository yang memisahkan logic bisnis dari sumber data.
 *
 * Repository berfungsi sebagai single source of truth dan
 * abstraksi antara layer data (Room, API, dll) dan layer UI/Domain.
 *
 * Keuntungan menggunakan interface:
 * - Memungkinkan multiple implementasi (offline/online)
 * - Memudahkan testing dengan mock objects
 * - Loose coupling antara komponen
 * - Memungkinkan dependency injection yang clean
 */
interface ItemsRepository {
    /**
     * Mengambil semua item dari sumber data.
     * Menggunakan Flow untuk reactive stream data.
     *
     * @return Flow<List<Item>> Stream dari list item yang akan
     *         mengemit nilai baru setiap kali data berubah
     *
     * Flow memungkinkan:
     * - Observasi perubahan data secara real-time
     * - Automatic UI updates
     * - Efficient memory management
     */
    fun getAllItemsStream(): Flow<List<Item>>

    /**
     * Mengambil item spesifik berdasarkan ID.
     *
     * @param id ID dari item yang dicari
     * @return Flow<Item?> Stream dari item yang bisa null
     *         jika item dengan ID tersebut tidak ditemukan
     *
     * Nullable return type (Item?) mengindikasikan bahwa:
     * - Item mungkin tidak ditemukan
     * - Caller harus handle null case
     */
    fun getItemStream(id: Int): Flow<Item?>

    /**
     * Menyimpan item baru ke dalam sumber data.
     *
     * @param item Item yang akan disimpan
     *
     * suspend function untuk:
     * - Operasi asynchronous
     * - Eksekusi di background thread
     * - Tidak blocking main thread
     */
    suspend fun insertItem(item: Item)

    /**
     * Menghapus item dari sumber data.
     *
     * @param item Item yang akan dihapus
     *
     * suspend function memastikan:
     * - Safe database operations
     * - Proper error handling
     * - Coroutine context management
     */
    suspend fun deleteItem(item: Item)

    /**
     * Memperbarui data item yang sudah ada.
     *
     * @param item Item dengan data terbaru
     *
     * suspend function untuk:
     * - Thread safety
     * - Database transaction management
     * - Asynchronous execution
     */
    suspend fun updateItem(item: Item)
}

/**
 * Catatan implementasi:
 * - Semua operasi database menggunakan suspend functions atau Flow
 * - Interface ini bisa diimplementasikan untuk berbagai sumber data
 * - Method names mencerminkan operasi bisnis, bukan operasi database
 * - Setiap method memiliki single responsibility
 */