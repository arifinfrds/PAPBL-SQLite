package com.arifinfrds.papbl_sqlite.model.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.arifinfrds.papbl_sqlite.model.Barang
import com.arifinfrds.papbl_sqlite.model.MitraDagang
import com.arifinfrds.papbl_sqlite.util.RandomStringGenerator

/**
 * Created by arifinfrds on 2/22/18.
 */
class DatabaseHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION), BarangCRUDContract, MitraDagangCRUDContract {

    // MARK: - Static
    companion object {
        val DATABASE_NAME = "PAPBL-SQLite.db"
        val DATABASE_VERSION = 1
    }

    // MARK: - Properties

    // Table
    private val TABLE_BARANG = "TABLE_BARANG"
    private val COLUMN_1_ID = "ID"
    private val COLUMN_2_NAMA = "NAMA"
    private val COLUMN_3_BRAND = "BRAND"

    private val TABLE_MITRA_DAGANG = "MITRA_DAGANG"
    private val COLUMN_1_ID_MITRA_DAGANG = "ID"
    private val COLUMN_2_NAMA_MITRA_DAGANG = "NAMA"
    private val COLUMN_3_TAHUN_KERJASAMA_MITRA_DAGANG = "TAHUN_KERJASAMA"


    // MARK: - SQLiteOpenHelper
    override fun onCreate(p0: SQLiteDatabase?) {

        val queryTableBarang = "CREATE TABLE " + TABLE_BARANG + "(" +
                COLUMN_1_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_2_NAMA + " TEXT, " +
                COLUMN_3_BRAND + " TEXT " +
                ");"
        p0?.execSQL(queryTableBarang)

        val queryTableMitraDagang = "CREATE TABLE " + TABLE_MITRA_DAGANG + "(" +
                COLUMN_1_ID_MITRA_DAGANG + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_2_NAMA_MITRA_DAGANG + " TEXT, " +
                COLUMN_3_TAHUN_KERJASAMA_MITRA_DAGANG + " TEXT " +
                ");"
        p0?.execSQL(queryTableMitraDagang)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE IF EXISTS" + TABLE_BARANG)
        p0?.execSQL("DROP TABLE IF EXISTS" + TABLE_MITRA_DAGANG)
        onCreate(p0)
    }


    // MARK: - BarangCRUDContract
    override fun insertBarang(barang: Barang): Boolean {
        val contentValues = ContentValues()
        val db = this.writableDatabase

        contentValues.put(COLUMN_2_NAMA, barang.nama)
        contentValues.put(COLUMN_3_BRAND, barang.brand)

        val result = db.insert(TABLE_BARANG, null, contentValues)

        db.close()

        return result != -1L
    }

    override fun fetchAllBarang(): Cursor {
        val db = this.writableDatabase
        val cursor = db.rawQuery("SELECT * FROM " + TABLE_BARANG, null)
        db.close()
        return cursor
    }

    override fun fetchBarang(namaBarang: String): Cursor {
        val db = this.writableDatabase
        val cursor = db.rawQuery("SELECT * FROM " + TABLE_BARANG + " WHERE " + COLUMN_2_NAMA + " LIKE " + "'%" + namaBarang + "%'", null)
        db.close()
        return cursor
    }

    override fun updateBarang(barang: Barang): Boolean {
        val contentValues = ContentValues()
        val db = this.writableDatabase

        contentValues.put(COLUMN_2_NAMA, barang.nama)
        contentValues.put(COLUMN_3_BRAND, barang.brand)

        val result = db.update(TABLE_BARANG, contentValues, "ID = " + barang.id, null)

        db.close()

        return result != -1
    }

    override fun deleteBarang(idBarang: Int): Boolean {
        val db = this.writableDatabase
        val success = db.delete(TABLE_BARANG, COLUMN_1_ID + "=" + idBarang, null) > 0
        db.close()
        return success
    }

    override fun deleteAllBarang(): Boolean {
        val db = this.writableDatabase
        val success = db.delete(TABLE_BARANG, null, null) > 0
        db.close()
        return success
    }

    override fun insertBarangTransaction(): Boolean {
        var isSuccess = false

        val db = this.writableDatabase
        db.beginTransaction();
        try {
            handleInsertBarangTransaction();
            db.setTransactionSuccessful();
            isSuccess = true
        } catch (e: Exception) {
            //Error in between database transaction
            Log.d("TAG_TRANSACTION", e.localizedMessage)
            isSuccess = false
        } finally {
            db.endTransaction();
        }
        return isSuccess
    }

    private fun handleInsertBarangTransaction() {
        val db = this.writableDatabase
        for (i in 0..500) {
            val contentValues = ContentValues()
            contentValues.put(COLUMN_2_NAMA, RandomStringGenerator.randomStringOfLength(RandomStringGenerator.DEFAULT_STRING_OF_LENGTH))
            contentValues.put(COLUMN_3_BRAND, RandomStringGenerator.randomStringOfLength(RandomStringGenerator.DEFAULT_STRING_OF_LENGTH))

            val result = db.insert(TABLE_BARANG, null, contentValues)
        }
        db.close()
    }


    // MARK: - MitraDagangCRUDContract
    override fun insertMitraDagang(mitraDagang: MitraDagang): Boolean {
        val contentValues = ContentValues()
        val db = this.writableDatabase

        contentValues.put(COLUMN_2_NAMA_MITRA_DAGANG, mitraDagang.nama)
        contentValues.put(COLUMN_3_TAHUN_KERJASAMA_MITRA_DAGANG, mitraDagang.tahunKerjasama)

        val result = db.insert(TABLE_MITRA_DAGANG, null, contentValues)

        db.close()

        return result != -1L
    }

    override fun fetchAllMitraDagang(): Cursor {
        val db = this.writableDatabase
        val cursor = db.rawQuery("SELECT * FROM " + TABLE_MITRA_DAGANG, null)
        db.close()
        return cursor
    }

    override fun fetchMitraDagang(nama: String): Cursor {
        val db = this.writableDatabase
        val cursor = db.rawQuery("SELECT * FROM " + TABLE_MITRA_DAGANG + "WHERE " + COLUMN_2_NAMA + " LIKE " + "'% " + nama + " %'", null)
        db.close()
        return cursor
    }

    override fun updateMitraDagang(mitraDagang: MitraDagang): Boolean {
        val contentValues = ContentValues()
        val db = this.writableDatabase

        contentValues.put(COLUMN_2_NAMA_MITRA_DAGANG, mitraDagang.nama)
        contentValues.put(COLUMN_3_TAHUN_KERJASAMA_MITRA_DAGANG, mitraDagang.tahunKerjasama)

        val result = db.update(TABLE_MITRA_DAGANG, contentValues, "ID = " + mitraDagang.id, null)

        db.close()

        return result != -1
    }

    override fun deleteMitraDagang(idMitraDagang: Int): Boolean {
        val db = this.writableDatabase
        val success = db.delete(TABLE_MITRA_DAGANG, COLUMN_1_ID_MITRA_DAGANG + "=" + idMitraDagang, null) > 0
        db.close()
        return success
    }
}