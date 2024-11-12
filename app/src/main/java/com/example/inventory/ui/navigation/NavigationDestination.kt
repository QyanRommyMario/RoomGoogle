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

/**
 * Interface yang berfungsi sebagai kontrak untuk mendefinisikan destinasi navigasi dalam aplikasi.
 * Interface ini membantu dalam pengelolaan rute navigasi antar screen/halaman dengan lebih terstruktur.
 *
 * Setiap screen yang dapat dinavigasi dalam aplikasi harus mengimplementasikan interface ini
 * untuk memastikan konsistensi dalam penamaan rute dan pengelolaan judul halaman.
 */
interface NavigationDestination {
    /**
     * Property route menyimpan string unik yang menjadi identifier untuk setiap halaman.
     * Route ini digunakan oleh Navigation Component untuk menentukan destinasi navigasi.
     *
     * Contoh nilai route: "home", "detail/{itemId}", "settings"
     */
    val route: String

    /**
     * Property titleRes menyimpan ID resource string yang berisi judul untuk ditampilkan di halaman.
     * Menggunakan resource ID memungkinkan dukungan multi bahasa/lokalisasi.
     *
     * Resource ID mengacu pada string yang didefinisikan di res/values/strings.xml
     * Contoh: R.string.home_title
     */
    val titleRes: Int
}