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

package com.example.inventory.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Kelas Entity yang merepresentasikan tabel dalam database Room.
 *
 * @Entity: Anotasi yang menandakan bahwa kelas ini adalah entitas database
 * tableName = "items": Mendefinisikan nama tabel dalam database
 *
 * Sebagai data class, kelas ini secara otomatis menyediakan:
 * - toString()
 * - equals()/hashCode()
 * - copy()
 * - component functions untuk destructuring
 */
@Entity(tableName = "items")
data class Item(
    /**
     * Primary key untuk tabel items
     * @PrimaryKey: Menandakan bahwa properti ini adalah primary key
     * autoGenerate = true: Room akan otomatis generate nilai ID unik
     * Nilai default 0 digunakan untuk insert item baru
     */
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    /**
     * Nama item dalam inventori
     * Room akan otomatis membuat kolom dalam database
     * dengan tipe TEXT untuk String
     */
    val name: String,

    /**
     * Harga item dalam format Double
     * Room akan membuat kolom dengan tipe REAL
     * untuk menyimpan nilai desimal
     */
    val price: Double,

    /**
     * Jumlah item dalam inventori
     * Room akan membuat kolom dengan tipe INTEGER
     * untuk menyimpan nilai bilangan bulat
     */
    val quantity: Int
)

/**
 * Catatan tambahan:
 * - Semua properti dideklarasikan sebagai val (immutable) untuk thread safety
 * - Tidak ada logic bisnis dalam entity class (Single Responsibility Principle)
 * - Entity ini bisa ditambahkan anotasi tambahan seperti:
 *   @ColumnInfo(name = "column_name") untuk nama kolom kustom
 *   @Ignore untuk mengabaikan properti dari penyimpanan database
 *   @Index untuk mengoptimalkan query
 */