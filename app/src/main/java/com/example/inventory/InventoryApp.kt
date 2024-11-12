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

@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.inventory

import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.inventory.R.string
import com.example.inventory.ui.navigation.InventoryNavHost

/**
 * InventoryApp merupakan komponen UI tingkat tertinggi yang berfungsi sebagai
 * entry point utama aplikasi inventaris. Komponen ini:
 * - Mengintegrasikan navigasi menggunakan NavHostController untuk perpindahan antar screen
 * - Bertindak sebagai container utama yang menghubungkan seluruh komponen UI
 * - Memungkinkan pengujian yang lebih mudah dengan parameter navController yang dapat diinjeksi
 *
 * Hubungan dengan Room/Data:
 * - Meskipun tidak langsung berinteraksi dengan Room, komponen ini menjadi host untuk
 *   screen-screen yang akan menampilkan data dari Room database
 * - Screen-screen yang ditampilkan oleh InventoryNavHost akan menggunakan ViewModel
 *   untuk mengakses data dari repository yang terhubung dengan Room database
 */
@Composable
fun InventoryApp(navController: NavHostController = rememberNavController()) {
    InventoryNavHost(navController = navController)
}

/**
 * InventoryTopAppBar adalah komponen UI yang menampilkan app bar di bagian atas aplikasi.
 * Fungsi ini berperan penting dalam:
 * - Menampilkan judul halaman yang sedang aktif
 * - Menyediakan navigasi kembali ketika diperlukan
 * - Mendukung perilaku scroll yang dinamis
 *
 * Hubungan dengan Room/Data:
 * - Title yang ditampilkan seringkali mencerminkan konteks data yang sedang ditampilkan,
 *   misalnya "Detail Item" ketika menampilkan data item spesifik dari Room database
 * - Navigasi kembali (back button) digunakan untuk kembali ke layar sebelumnya setelah
 *   operasi database seperti menambah atau mengedit item
 *
 * Parameter:
 * @param title: Judul yang akan ditampilkan di app bar
 * @param canNavigateBack: Flag yang menentukan apakah tombol back ditampilkan
 * @param modifier: Modifier untuk kustomisasi tampilan
 * @param scrollBehavior: Mengatur perilaku app bar saat konten di-scroll
 * @param navigateUp: Lambda function yang dipanggil saat tombol back ditekan
 */
@Composable
fun InventoryTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Filled.ArrowBack,
                        contentDescription = stringResource(string.back_button)
                    )
                }
            }
        }
    )
}