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

package com.example.inventory.ui.home

import androidx.lifecycle.ViewModel
import com.example.inventory.data.Item

/**
 * ViewModel yang berfungsi sebagai penghubung antara UI dan data layer.
 * Kelas ini meng-extend ViewModel() untuk mempertahankan data ketika terjadi
 * configuration changes seperti rotasi layar.
 */
class HomeViewModel : ViewModel() {
    companion object {
        /**
         * Konstanta yang menentukan batas waktu (timeout) untuk operasi database.
         * Nilai 5000L milliseconds (5 detik) digunakan sebagai batas maksimal
         * waktu tunggu untuk query database sebelum timeout exception dilempar.
         */
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * Data class yang merepresentasikan state UI untuk HomeScreen.
 * Berfungsi sebagai kontainer yang menyimpan daftar Item dari database Room.
 *
 * @property itemList List yang berisi objek-objek Item dari database.
 *                    Diinisialisasi dengan list kosong sebagai nilai default.
 *                    List ini akan diupdate ketika ada perubahan data di database
 *                    dan ditampilkan di UI.
 */
data class HomeUiState(val itemList: List<Item> = listOf())