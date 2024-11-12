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
import androidx.lifecycle.ViewModel
import com.example.inventory.data.Item
import java.text.NumberFormat
import com.example.inventory.data.ItemsRepository

/**
 * ViewModel yang bertanggung jawab untuk:
 * 1. Validasi data input sebelum disimpan ke database Room
 * 2. Melakukan operasi insert ke database melalui repository
 * 3. Mengelola state UI untuk form entry item
 *
 * @param itemsRepository Repository yang menyediakan akses ke database Room
 */
class ItemEntryViewModel(private val itemsRepository: ItemsRepository) : ViewModel() {

    /**
     * State holder untuk UI yang menggunakan delegated property 'by mutableStateOf'
     * untuk membuat state yang observable oleh Compose.
     * Private set untuk memastikan hanya ViewModel yang bisa mengubah state
     */
    var itemUiState by mutableStateOf(ItemUiState())
        private set

    /**
     * Fungsi untuk memperbarui state UI dengan memvalidasi input baru.
     * Dipanggil setiap kali user mengubah nilai di form input.
     *
     * @param itemDetails Data baru dari form yang akan divalidasi
     */
    fun updateUiState(itemDetails: ItemDetails) {
        itemUiState =
            ItemUiState(itemDetails = itemDetails, isEntryValid = validateInput(itemDetails))
    }

    /**
     * Fungsi helper untuk memvalidasi input form.
     * Memastikan semua field required telah diisi sebelum menyimpan ke database.
     *
     * @param uiState Data yang akan divalidasi
     * @return Boolean true jika semua field valid
     */
    private fun validateInput(uiState: ItemDetails = itemUiState.itemDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank()
        }
    }

    /**
     * Fungsi suspend untuk menyimpan item ke database Room.
     * Hanya akan menyimpan jika semua input valid.
     * Menggunakan repository untuk melakukan operasi insert ke database.
     */
    suspend fun saveItem() {
        if (validateInput()) {
            itemsRepository.insertItem(itemUiState.itemDetails.toItem())
        }
    }
}

/**
 * Data class yang merepresentasikan state UI untuk item.
 * Menyimpan data input form dan status validasinya.
 *
 * @property itemDetails Detail item yang sedang diinput
 * @property isEntryValid Status validasi input
 */
data class ItemUiState(
    val itemDetails: ItemDetails = ItemDetails(),
    val isEntryValid: Boolean = false
)

/**
 * Data class untuk menyimpan detail item dalam format yang sesuai untuk UI.
 * Berbeda dengan entity Room (Item), class ini menggunakan String untuk price dan quantity
 * untuk memudahkan handling input form.
 */
data class ItemDetails(
    val id: Int = 0,
    val name: String = "",
    val price: String = "",
    val quantity: String = "",
)

/**
 * Extension function untuk mengkonversi ItemDetails (format UI) ke Item (entity Room).
 * Melakukan konversi tipe data:
 * - price: String -> Double (defaultnya 0.0 jika invalid)
 * - quantity: String -> Int (defaultnya 0 jika invalid)
 *
 * Konversi ini diperlukan karena database Room membutuhkan tipe data yang spesifik
 */
fun ItemDetails.toItem(): Item = Item(
    id = id,
    name = name,
    price = price.toDoubleOrNull() ?: 0.0,
    quantity = quantity.toIntOrNull() ?: 0
)

/**
 * Extension function untuk memformat harga item ke format mata uang lokal.
 * Menggunakan NumberFormat untuk memastikan format yang konsisten.
 */
fun Item.formatedPrice(): String {
    return NumberFormat.getCurrencyInstance().format(price)
}

/**
 * Extension function untuk mengkonversi Item (entity Room) ke ItemUiState.
 * Digunakan ketika ingin menampilkan data dari database ke UI.
 */
fun Item.toItemUiState(isEntryValid: Boolean = false): ItemUiState = ItemUiState(
    itemDetails = this.toItemDetails(),
    isEntryValid = isEntryValid
)

/**
 * Extension function untuk mengkonversi Item (entity Room) ke ItemDetails.
 * Mengkonversi tipe data numerik menjadi String untuk ditampilkan di UI.
 */
fun Item.toItemDetails(): ItemDetails = ItemDetails(
    id = id,
    name = name,
    price = price.toString(),
    quantity = quantity.toString()
)