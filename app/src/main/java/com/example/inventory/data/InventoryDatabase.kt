package com.example.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Kelas database utama yang menggunakan Room persistence library.
 * Anotasi @Database menandakan bahwa ini adalah kelas database Room dengan:
 * - entities: Mendefinisikan tabel-tabel dalam database (dalam hal ini hanya Item)
 * - version: Versi skema database, digunakan untuk migrasi
 * - exportSchema: false berarti skema tidak di-export ke folder resources
 *
 * Kelas ini abstract karena Room akan membuat implementasi konkritnya saat compile-time
 */
@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {

    /**
     * Method abstract yang mengembalikan DAO (Data Access Object).
     * Room akan mengimplementasikan method ini secara otomatis.
     * DAO digunakan untuk melakukan operasi database seperti insert, update, delete, dan query.
     */
    abstract fun itemDao(): ItemDao

    /**
     * Companion object untuk implementasi Singleton pattern.
     * Memastikan hanya ada satu instance database yang dibuat,
     * karena pembuatan instance database adalah operasi yang mahal.
     */
    companion object {
        /**
         * Instance database yang di-cache.
         * @Volatile memastikan nilai Instance selalu up-to-date dan sama untuk semua execution thread.
         * Ini mencegah race condition di lingkungan multi-thread.
         */
        @Volatile
        private var Instance: InventoryDatabase? = null

        /**
         * Method untuk mendapatkan instance database.
         * Menggunakan Double-Checked Locking pattern untuk thread-safety.
         *
         * Proses yang terjadi:
         * 1. Cek apakah Instance sudah ada
         * 2. Jika belum, buat instance baru dalam synchronized block
         * 3. Gunakan databaseBuilder untuk konfigurasi dan pembuatan database
         * 4. Cache instance yang baru dibuat
         *
         * @param context Context aplikasi yang diperlukan untuk membuat database
         * @return Instance dari InventoryDatabase
         */
        fun getDatabase(context: Context): InventoryDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, InventoryDatabase::class.java, "item_database")
                    /**
                     * Bisa ditambahkan konfigurasi tambahan di sini, misalnya:
                     * - fallbackToDestructiveMigration()
                     * - addCallback()
                     * - allowMainThreadQueries() (tidak disarankan untuk production)
                     */
                    .build()
                    .also { Instance = it }
            }
        }
    }
}