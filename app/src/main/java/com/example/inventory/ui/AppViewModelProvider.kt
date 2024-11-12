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

package com.example.inventory.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.inventory.InventoryApplication
import com.example.inventory.ui.home.HomeViewModel
import com.example.inventory.ui.item.ItemDetailsViewModel
import com.example.inventory.ui.item.ItemEditViewModel
import com.example.inventory.ui.item.ItemEntryViewModel

/**
 * Object singleton yang bertanggung jawab untuk menyediakan factory pembuatan ViewModel
 * di seluruh aplikasi Inventory.
 *
 * Factory ini menggunakan pattern Dependency Injection untuk menyediakan dependencies
 * yang dibutuhkan oleh setiap ViewModel seperti Repository dan SavedStateHandle.
 */
object AppViewModelProvider {
    /**
     * Property Factory yang mendefinisikan cara pembuatan berbagai ViewModel dalam aplikasi.
     * Menggunakan DSL viewModelFactory untuk membuat factory dengan sintaks yang lebih terstruktur.
     *
     * Setiap initializer block mendefinisikan cara pembuatan instance spesifik ViewModel
     * beserta dependencies yang dibutuhkan.
     */
    val Factory = viewModelFactory {
        // Initializer untuk ItemEditViewModel
        // Menyediakan SavedStateHandle untuk menyimpan state saat konfigurasi berubah
        initializer {
            ItemEditViewModel(
                this.createSavedStateHandle()
            )
        }

        // Initializer untuk ItemEntryViewModel
        // Menyediakan itemsRepository untuk akses dan manipulasi data item
        initializer {
            ItemEntryViewModel(inventoryApplication().container.itemsRepository)
        }

        // Initializer untuk ItemDetailsViewModel
        // Menyediakan SavedStateHandle untuk menyimpan state seperti ID item yang sedang dilihat
        initializer {
            ItemDetailsViewModel(
                this.createSavedStateHandle()
            )
        }

        // Initializer untuk HomeViewModel
        // Tidak memerlukan dependencies khusus
        initializer {
            HomeViewModel()
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.inventoryApplication(): InventoryApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as InventoryApplication)