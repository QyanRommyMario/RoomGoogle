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

package com.example.inventory.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.inventory.data.ItemsRepository

/**
 * ViewModel yang bertanggung jawab untuk mengelola operasi CRUD (Create, Read, Update, Delete)
 * pada item inventory melalui [ItemsRepository]. ViewModel ini khusus menangani
 * proses pengeditan item yang sudah ada dalam database.
 *
 * @property savedStateHandle Komponen Android Architecture yang menyimpan state ViewModel,
 *   digunakan untuk menyimpan dan mengambil ID item yang sedang diedit
 */
class ItemEditViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    /**
     * State holder untuk UI yang merepresentasikan data item yang sedang diedit.
     * Menggunakan mutableStateOf untuk mengimplementasikan observable pattern,
     * sehingga UI akan diupdate secara otomatis ketika nilai berubah.
     *
     * Private setter digunakan untuk memastikan bahwa perubahan state
     * hanya bisa dilakukan melalui fungsi-fungsi yang telah ditentukan dalam ViewModel.
     */
    var itemUiState by mutableStateOf(ItemUiState())
        private set

    /**
     * ID item yang sedang diedit, diambil dari navigation arguments melalui SavedStateHandle.
     * checkNotNull memastikan bahwa ID selalu tersedia saat ViewModel diinisialisasi.
     * ID ini digunakan untuk mengambil data item dari database melalui repository.
     */
    private val itemId: Int = checkNotNull(savedStateHandle[ItemEditDestination.itemIdArg])

    /**
     * Memvalidasi input user untuk memastikan data yang akan disimpan ke database valid.
     * Validasi mencakup pemeriksaan bahwa semua field required telah diisi.
     *
     * @param uiState Object ItemDetails yang berisi data item yang akan divalidasi
     * @return Boolean yang menunjukkan apakah input valid (true) atau tidak (false)
     */
    private fun validateInput(uiState: ItemDetails = itemUiState.itemDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank()
        }
    }
}