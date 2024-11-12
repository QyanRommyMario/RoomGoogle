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

package com.example.inventory

import android.app.Application
import com.example.inventory.data.AppContainer
import com.example.inventory.data.AppDataContainer

/**
 * InventoryApplication adalah kelas utama aplikasi yang mewarisi Android Application.
 * Kelas ini memiliki peran krusial dalam inisialisasi komponen-komponen data:
 *
 * Fungsi utama:
 * - Menginisialisasi dependency injection container pada level aplikasi
 * - Menyediakan akses ke database dan repository untuk seluruh aplikasi
 * - Memastikan komponen data tersedia sebelum UI mulai berinteraksi
 *
 * Hubungan dengan Room/Data:
 * - Bertanggung jawab membuat instance AppDataContainer yang menginisialisasi:
 *   * Room Database instance
 *   * Data Access Objects (DAOs)
 *   * Repositories
 * - Menjadi entry point untuk Dependency Injection manual tanpa framework DI
 * - Memastikan satu instance database digunakan di seluruh aplikasi (Singleton pattern)
 *
 * Lifecycle:
 * - Dibuat saat aplikasi pertama kali diluncurkan
 * - Tetap aktif selama aplikasi berjalan
 * - Bertanggung jawab atas inisialisasi komponen data sebelum UI ditampilkan
 */
class InventoryApplication : Application() {

    /**
     * Property container adalah instance dari AppContainer yang:
     * - Menyimpan semua dependencies yang dibutuhkan aplikasi
     * - Diinisialisasi secara lazy (lateinit) untuk menghindari overhead saat startup
     * - Dapat diakses oleh komponen lain seperti ViewModel untuk mendapatkan repository
     *
     * Hubungan dengan Room/Data:
     * - Melalui container, ViewModel dapat mengakses repository
     * - Repository dalam container terhubung dengan Room Database
     * - Memastikan penggunaan yang konsisten dari database instance
     */
    lateinit var container: AppContainer

    /**
     * onCreate() dipanggil saat aplikasi pertama kali diinisialisasi.
     * Method ini sangat penting karena:
     * - Menginisialisasi AppDataContainer yang membuat koneksi ke database
     * - Menyiapkan repository dan DAO yang akan digunakan
     * - Memastikan semua komponen data siap sebelum UI mulai berinteraksi
     *
     * Hubungan dengan Room/Data:
     * - Membuat koneksi database melalui AppDataContainer
     * - Menyiapkan repository pattern untuk akses data
     * - Menginisialisasi komponen Room yang dibutuhkan
     */
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}