package com.example.inventory.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Interface Data Access Object (DAO) untuk mengakses dan memanipulasi data Item dalam database.
 * @Dao menandakan bahwa interface ini adalah DAO Room yang akan di-generate implementasinya
 * secara otomatis oleh Room saat compile-time.
 *
 * DAO ini menggunakan Kotlin Coroutines untuk operasi asynchronous dan Flow untuk reactive streams.
 */
@Dao
interface ItemDao {
    /**
     * Menambahkan item baru ke database.
     * @param item Item yang akan ditambahkan
     *
     * @Insert: Anotasi untuk operasi INSERT ke database
     * onConflict = OnConflictStrategy.IGNORE: Mengabaikan insert jika terjadi konflik
     * suspend: Menandakan bahwa fungsi ini harus dijalankan dalam coroutine
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    /**
     * Memperbarui data item yang sudah ada dalam database.
     * @param item Item dengan data terbaru
     *
     * @Update: Anotasi untuk operasi UPDATE
     * Room akan mencocokkan primary key untuk menentukan item yang diupdate
     */
    @Update
    suspend fun update(item: Item)

    /**
     * Menghapus item dari database.
     * @param item Item yang akan dihapus
     *
     * @Delete: Anotasi untuk operasi DELETE
     * Room akan mencocokkan primary key untuk menentukan item yang dihapus
     */
    @Delete
    suspend fun delete(item: Item)

    /**
     * Mengambil item spesifik berdasarkan ID.
     * @param id ID item yang dicari
     * @return Flow<Item> Stream data item yang akan emit nilai baru saat data berubah
     *
     * @Query: Anotasi untuk custom SQL query
     * Menggunakan parameter SQL dengan :id yang akan digantikan nilai parameter fungsi
     */
    @Query("SELECT * from items WHERE id = :id")
    fun getItem(id: Int): Flow<Item>

    /**
     * Mengambil semua item dari database, diurutkan berdasarkan nama secara ascending.
     * @return Flow<List<Item>> Stream list item yang akan emit nilai baru saat data berubah
     *
     * @Query dengan ORDER BY untuk mengurutkan hasil
     * Flow digunakan untuk observasi perubahan data secara reaktif
     */
    @Query("SELECT * from items ORDER BY name ASC")
    fun getAllItems(): Flow<List<Item>>
}

/**
 * Catatan tambahan:
 * - Semua operasi database berjalan di background thread (suspend functions)
 * - Flow digunakan untuk reactive programming, memungkinkan UI bereaksi terhadap perubahan data
 * - Query bisa ditambahkan sesuai kebutuhan dengan anotasi @Query
 * - Room memverifikasi query SQL saat compile-time
 */