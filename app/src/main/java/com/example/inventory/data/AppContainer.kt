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

import android.content.Context

/**
 * Interface AppContainer berfungsi sebagai kontrak untuk Dependency Injection.
 * Interface ini mendefinisikan dependency yang diperlukan oleh aplikasi,
 * dalam hal ini repository untuk mengelola data item.
 *
 * Penggunaan interface ini memungkinkan:
 * - Loose coupling antara komponen
 * - Memudahkan testing dengan mock objects
 * - Fleksibilitas dalam implementasi (bisa offline/online repository)
 */
interface AppContainer {
    val itemsRepository: ItemsRepository
}

/**
 * Implementasi konkrit dari AppContainer yang menyediakan instance OfflineItemsRepository.
 * Kelas ini bertanggung jawab untuk:
 * - Menginisialisasi database Room melalui InventoryDatabase
 * - Menyediakan implementasi repository yang menggunakan penyimpanan offline
 * - Menggunakan lazy initialization untuk memastikan database hanya dibuat saat dibutuhkan
 *
 * @param context Context aplikasi yang diperlukan untuk membuat database Room
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Property override yang menyediakan implementasi ItemsRepository.
     * Menggunakan lazy delegation untuk:
     * - Menunda inisialisasi database sampai benar-benar dibutuhkan
     * - Menyimpan instance yang sama selama lifecycle aplikasi (singleton pattern)
     * - Mengoptimalkan penggunaan memori
     *
     * Proses yang terjadi:
     * 1. Mengambil instance database melalui InventoryDatabase.getDatabase()
     * 2. Mendapatkan DAO dari database tersebut
     * 3. Membuat instance OfflineItemsRepository dengan DAO yang sudah didapat
     */
    override val itemsRepository: ItemsRepository by lazy {
        OfflineItemsRepository(InventoryDatabase.getDatabase(context).itemDao())
    }
}