package com.partos.flashback.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build.ID
import android.provider.BaseColumns
import com.example.flashcards.models.Flashcard
import com.example.flashcards.models.MyPackage
import com.partos.flashback.models.MyFlashcard
import com.partos.flashback.models.MyPackage

object TableInfo : BaseColumns {
    const val DATABASE_NAME = "FlashBack"
    const val TABLE_NAME_PACKAGES = "Packages"
    const val TABLE_COLUMN_PACKAGES_NAME = "name"
    const val TABLE_COLUMN_PACKAGES_USER_ID = "userId"
    const val TABLE_NAME_FLASHCARDS = "Flashcards"
    const val TABLE_COLUMN_FLASHCARDS_PACKAGE_ID = "packageId"
    const val TABLE_COLUMN_FLASHCARDS_USER_ID = "userId"
    const val TABLE_COLUMN_FLASHCARDS_ENGLISH = "english"
    const val TABLE_COLUMN_FLASHCARDS_POLISH = "polish"
    const val TABLE_COLUMN_FLASHCARDS_KNOWLEDGE = "knowledgeLevel"
    const val TABLE_COLUMN_FLASHCARDS_IS_NEW = "isNew"
    const val TABLE_COLUMN_FLASHCARDS_IS_KNOWN = "isKnown"


}

object BasicCommand {
    const val SQL_CREATE_TABLE_PACKAGES =
        "CREATE TABLE ${TableInfo.TABLE_NAME_PACKAGES} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${TableInfo.TABLE_COLUMN_PACKAGES_NAME} TEXT NOT NULL," +
                "${TableInfo.TABLE_COLUMN_PACKAGES_USER_ID} INTEGER NOT NULL)"

    const val SQL_CREATE_TABLE_FLASHCARDS =
        "CREATE TABLE ${TableInfo.TABLE_NAME_FLASHCARDS} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${TableInfo.TABLE_COLUMN_FLASHCARDS_USER_ID} INTEGER NOT NULL," +
                "${TableInfo.TABLE_COLUMN_FLASHCARDS_PACKAGE_ID} INTEGER NOT NULL," +
                "${TableInfo.TABLE_COLUMN_FLASHCARDS_ENGLISH} TEXT NOT NULL," +
                "${TableInfo.TABLE_COLUMN_FLASHCARDS_POLISH} TEXT NOT NULL," +
                "${TableInfo.TABLE_COLUMN_FLASHCARDS_KNOWLEDGE} INTEGER NOT NULL," +
                "${TableInfo.TABLE_COLUMN_FLASHCARDS_IS_NEW} INTEGER NOT NULL," +
                "${TableInfo.TABLE_COLUMN_FLASHCARDS_IS_KNOWN} INTEGER NOT NULL)"


    const val SQL_DELETE_TABLE_PACKAGES = "DROP TABLE IF EXISTS ${TableInfo.TABLE_NAME_PACKAGES}"
    const val SQL_DELETE_TABLE_FLASHCARDS = "DROP TABLE IF EXISTS ${TableInfo.TABLE_NAME_PACKAGES}"
}

class DataBaseHelper(context: Context) :
    SQLiteOpenHelper(context, TableInfo.DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(BasicCommand.SQL_CREATE_TABLE_PACKAGES)
        db?.execSQL(BasicCommand.SQL_CREATE_TABLE_FLASHCARDS)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(BasicCommand.SQL_CREATE_TABLE_PACKAGES)
        db?.execSQL(BasicCommand.SQL_CREATE_TABLE_FLASHCARDS)

        onCreate(db)
    }

    fun getPackagesList(userId: Long): ArrayList<MyPackage> {
        var packagesList = ArrayList<MyPackage>()
        val db = readableDatabase
        val selectQuery = "Select * from ${TableInfo.TABLE_NAME_PACKAGES} where " +
                "${TableInfo.TABLE_COLUMN_PACKAGES_USER_ID} = " + userId.toString()
        val result = db.rawQuery(selectQuery, null)
        if (result.moveToFirst()) {
            do {
                var myPackage = MyPackage(
                    result.getInt(result.getColumnIndex(BaseColumns._ID)).toLong(),
                    result.getString(result.getColumnIndex(TableInfo.TABLE_COLUMN_PACKAGES_NAME)),
                    result.getInt(result.getColumnIndex(TableInfo.TABLE_COLUMN_PACKAGES_USER_ID))
                )
                packagesList.add(myPackage)
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return packagesList
    }

    fun addPackage(name: String, userId: Int): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TableInfo.TABLE_COLUMN_PACKAGES_NAME, name)
        values.put(TableInfo.TABLE_COLUMN_PACKAGES_USER_ID, userId)
        val success = db.insert(TableInfo.TABLE_NAME_PACKAGES, null, values)
        db.close()
        return (Integer.parseInt("$success") != -1)
    }

    fun updatePackage(myPackage: MyPackage): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TableInfo.TABLE_COLUMN_PACKAGES_NAME, myPackage.title)
        values.put(TableInfo.TABLE_COLUMN_PACKAGES_USER_ID, myPackage.userId)
        val success = db.update(
            TableInfo.TABLE_NAME_PACKAGES, values, BaseColumns._ID + "=?",
            arrayOf(myPackage.id.toString())
        ).toLong()
        return Integer.parseInt("$success") != -1
    }


    fun deletePackage(id: Long): Boolean {
        val db = this.writableDatabase
        val success =
            db.delete(TableInfo.TABLE_NAME_PACKAGES, BaseColumns._ID + "=?", arrayOf(id.toString()))
                .toLong()
        db.close()
        return Integer.parseInt("$success") != -1
    }

    fun getFlashcardsList(packageId: Long, userId: Long): ArrayList<MyFlashcard> {
        var flascardsList = ArrayList<MyFlashcard>()
        val db = readableDatabase
        val selectQuery =
            "Select * from ${TableInfo.TABLE_NAME_FLASHCARDS} where ${TableInfo.TABLE_COLUMN_FLASHCARDS_PACKAGE_ID} = " +
                    packageId.toString() + " and ${TableInfo.TABLE_COLUMN_FLASHCARDS_USER_ID} = " + userId.toString()
        val result = db.rawQuery(selectQuery, null)
        if (result.moveToFirst()) {
            do {
                var flashcard = MyFlashcard(
                    result.getInt(result.getColumnIndex(BaseColumns._ID)).toLong(),
                    result.getInt(result.getColumnIndex(TableInfo.TABLE_COLUMN_FLASHCARDS_USER_ID))
                        .toLong(),
                    result.getInt(result.getColumnIndex(TableInfo.TABLE_COLUMN_FLASHCARDS_PACKAGE_ID))
                        .toLong(),
                    result.getString(result.getColumnIndex(TableInfo.TABLE_COLUMN_FLASHCARDS_POLISH)),
                    result.getString(result.getColumnIndex(TableInfo.TABLE_COLUMN_FLASHCARDS_ENGLISH)),
                    result.getInt(result.getColumnIndex(TableInfo.TABLE_COLUMN_FLASHCARDS_KNOWLEDGE)),
                    result.getInt(result.getColumnIndex(TableInfo.TABLE_COLUMN_FLASHCARDS_IS_NEW)),
                    result.getInt(result.getColumnIndex(TableInfo.TABLE_COLUMN_FLASHCARDS_IS_KNOWN))
                )
                flascardsList.add(flashcard)
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return flascardsList
    }

    fun addFlashcard(
        userId: Long,
        packageId: Long,
        polish: String,
        english: String,
        knowledgeLevel: Int,
        isNew: Int,
        isKnown: Int
    ): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TableInfo.TABLE_COLUMN_FLASHCARDS_USER_ID, userId)
        values.put(TableInfo.TABLE_COLUMN_FLASHCARDS_PACKAGE_ID, packageId)
        values.put(TableInfo.TABLE_COLUMN_FLASHCARDS_POLISH, polish)
        values.put(TableInfo.TABLE_COLUMN_FLASHCARDS_ENGLISH, english)
        values.put(TableInfo.TABLE_COLUMN_FLASHCARDS_KNOWLEDGE, knowledgeLevel)
        values.put(TableInfo.TABLE_COLUMN_FLASHCARDS_IS_NEW, isNew)
        values.put(TableInfo.TABLE_COLUMN_FLASHCARDS_IS_KNOWN, isKnown)
        val success = db.insert(TableInfo.TABLE_NAME_FLASHCARDS, null, values)
        db.close()
        return (Integer.parseInt("$success") != -1)
    }

    fun updateFlashcard(flashcard: MyFlashcard): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TableInfo.TABLE_COLUMN_FLASHCARDS_USER_ID, flashcard.userId)
        values.put(TableInfo.TABLE_COLUMN_FLASHCARDS_PACKAGE_ID, flashcard.packageId)
        values.put(TableInfo.TABLE_COLUMN_FLASHCARDS_POLISH, flashcard.polish)
        values.put(TableInfo.TABLE_COLUMN_FLASHCARDS_ENGLISH, flashcard.english)
        values.put(TableInfo.TABLE_COLUMN_FLASHCARDS_KNOWLEDGE, flashcard.knowledgeLevel)
        values.put(TableInfo.TABLE_COLUMN_FLASHCARDS_IS_NEW, flashcard.isNew)
        values.put(TableInfo.TABLE_COLUMN_FLASHCARDS_IS_KNOWN, flashcard.isKnown)
        val success = db.update(
            TableInfo.TABLE_NAME_FLASHCARDS, values, BaseColumns._ID + "=?",
            arrayOf(flashcard.id.toString())
        ).toLong()
        db.close()
        return Integer.parseInt("$success") != -1
    }


    fun deleteFlashcard(id: Long): Boolean {
        val db = this.writableDatabase
        val success =
            db.delete(
                TableInfo.TABLE_NAME_FLASHCARDS,
                BaseColumns._ID + "=?",
                arrayOf(id.toString())
            )
                .toLong()
        db.close()
        return Integer.parseInt("$success") != -1
    }
}