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

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.inventory.ui.theme.InventoryTheme

/**
 * MainActivity adalah entry point utama UI aplikasi Inventory.
 * Kelas ini bertanggung jawab untuk:
 * - Menginisialisasi tampilan utama menggunakan Jetpack Compose
 * - Menyiapkan tema dan container UI utama
 * - Mengintegrasikan navigasi dan komponen UI lainnya
 *
 * Hubungan dengan Room/Data:
 * - Menjadi host untuk InventoryApp yang menampilkan data dari Room database
 * - Memastikan ViewModel dan repository sudah siap melalui aplikasi instance
 * - Memberikan scope lifecycle untuk operasi database dan coroutines
 *
 * Architectural Role:
 * - Bertindak sebagai jembatan antara sistem Android dan aplikasi
 * - Menyediakan context untuk ViewModel yang akan mengakses database
 * - Mengatur lifecycle untuk operasi database asynchronous
 */
class MainActivity : ComponentActivity() {

    /**
     * onCreate() adalah method lifecycle utama yang:
     * - Menginisialisasi UI menggunakan Jetpack Compose
     * - Menyiapkan tema dan surface container
     * - Memulai InventoryApp yang akan menangani navigasi dan tampilan data
     *
     * Hubungan dengan Room/Data:
     * - Memastikan semua komponen data (database, repository) sudah diinisialisasi
     *   melalui InventoryApplication sebelum UI ditampilkan
     * - Menyediakan coroutine scope untuk operasi database asynchronous
     * - Mengatur lifecycle untuk ViewModel yang akan mengakses database
     *
     * Parameter:
     * @param savedInstanceState: Bundle yang menyimpan state aktivitas saat konfigurasi berubah
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            InventoryTheme {
                // Surface container yang menggunakan warna background dari tema
                // Container ini akan menampung seluruh UI yang berinteraksi dengan database
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    /**
                     * InventoryApp adalah composable utama yang:
                     * - Mengatur navigasi antar screen
                     * - Menampilkan data dari Room database
                     * - Mengelola state UI dan operasi database
                     */
                    InventoryApp()
                }
            }
        }
    }
}