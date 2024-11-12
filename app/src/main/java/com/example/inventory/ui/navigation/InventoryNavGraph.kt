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

package com.example.inventory.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.inventory.ui.home.HomeDestination
import com.example.inventory.ui.home.HomeScreen
import com.example.inventory.ui.item.ItemDetailsDestination
import com.example.inventory.ui.item.ItemDetailsScreen
import com.example.inventory.ui.item.ItemEditDestination
import com.example.inventory.ui.item.ItemEditScreen
import com.example.inventory.ui.item.ItemEntryDestination
import com.example.inventory.ui.item.ItemEntryScreen

/**
 * Composable yang mendefinisikan graph navigasi untuk aplikasi inventory.
 * Mengatur alur navigasi antar layar dan perpindahan data menggunakan NavHost.
 *
 * Flow navigasi utama:
 * Home -> Item Entry (tambah item baru)
 * Home -> Item Details -> Item Edit (edit item existing)
 *
 * @param navController Controller untuk mengelola navigasi antar destinasi
 * @param modifier Modifier untuk kustomisasi tampilan
 */
@Composable
fun InventoryNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        /*
         * Destinasi Home Screen
         * - Menampilkan list semua item dari database
         * - Menyediakan navigasi ke:
         *   1. Item Entry (untuk menambah item baru)
         *   2. Item Details (untuk melihat detail item)
         */
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(ItemEntryDestination.route) },
                navigateToItemUpdate = {
                    // Mengirim ID item sebagai parameter navigasi ke layar detail
                    navController.navigate("${ItemDetailsDestination.route}/${it}")
                }
            )
        }

        /*
         * Destinasi Item Entry Screen
         * - Form untuk menambah item baru ke database
         * - Menggunakan popBackStack() untuk kembali ke Home setelah save
         * - Menyediakan navigateUp untuk kembali ke layar sebelumnya
         */
        composable(route = ItemEntryDestination.route) {
            ItemEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }

        /*
         * Destinasi Item Details Screen
         * - Menampilkan detail item dari database berdasarkan ID
         * - Menerima parameter ID item melalui route argument
         * - Navigasi ke Edit screen dengan membawa ID item
         *
         * Menggunakan navArgument untuk mendefinisikan tipe parameter ID
         * sebagai Integer yang akan digunakan untuk query ke database
         */
        composable(
            route = ItemDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ItemDetailsDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            ItemDetailsScreen(
                navigateToEditItem = { navController.navigate("${ItemEditDestination.route}/$it") },
                navigateBack = { navController.navigateUp() }
            )
        }

        /*
         * Destinasi Item Edit Screen
         * - Form untuk mengubah data item yang sudah ada di database
         * - Menerima parameter ID item untuk mengambil data existing
         * - Menggunakan popBackStack() untuk kembali ke layar sebelumnya
         *   setelah update berhasil
         */
        composable(
            route = ItemEditDestination.routeWithArgs,
            arguments = listOf(navArgument(ItemEditDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            ItemEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}