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

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.inventory.data.ItemsRepository

/**
 * ViewModel yang bertanggung jawab untuk mengambil, memperbarui dan menghapus item
 * dari sumber data [ItemsRepository].
 * Menggunakan SavedStateHandle untuk menyimpan state yang bertahan setelah proses mati.
 * itemId diambil dari parameter navigasi yang disimpan dalam SavedStateHandle.
 */
class ItemDetailsViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val itemId: Int = checkNotNull(savedStateHandle[ItemDetailsDestination.itemIdArg])

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * Data class yang merepresentasikan state UI untuk ItemDetailsScreen.
 * Menyimpan informasi:
 * - outOfStock: status ketersediaan stok item
 * - itemDetails: detail item yang ditampilkan di UI
 */
data class ItemDetailsUiState(
    val outOfStock: Boolean = true,
    val itemDetails: ItemDetails = ItemDetails()
)