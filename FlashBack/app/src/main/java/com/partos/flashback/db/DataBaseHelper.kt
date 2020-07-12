package com.partos.flashback.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.partos.flashback.models.MyFlashcard
import com.partos.flashback.models.MyPackage
import com.partos.flashback.models.Settings

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
    const val TABLE_NAME_SETTINGS = "Settings"
    const val TABLE_COLUMN_SETTINGS_USER_ID = "userId"
    const val TABLE_COLUMN_SETTINGS_REVIEW_CLASSIC_AMOUNT = "classic"
    const val TABLE_COLUMN_SETTINGS_REVIEW_HARD_AMOUNT = "hard"
    const val TABLE_COLUMN_SETTINGS_LEARNING_AMOUNT = "learn"


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

    const val SQL_CREATE_TABLE_SETTINGS =
        "CREATE TABLE ${TableInfo.TABLE_NAME_SETTINGS} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${TableInfo.TABLE_COLUMN_SETTINGS_USER_ID} INTEGER NOT NULL," +
                "${TableInfo.TABLE_COLUMN_SETTINGS_REVIEW_CLASSIC_AMOUNT} INTEGER NOT NULL," +
                "${TableInfo.TABLE_COLUMN_SETTINGS_REVIEW_HARD_AMOUNT} INTEGER NOT NULL," +
                "${TableInfo.TABLE_COLUMN_SETTINGS_LEARNING_AMOUNT} INTEGER NOT NULL)"


    const val SQL_DELETE_TABLE_PACKAGES = "DROP TABLE IF EXISTS ${TableInfo.TABLE_NAME_PACKAGES}"
    const val SQL_DELETE_TABLE_FLASHCARDS =
        "DROP TABLE IF EXISTS ${TableInfo.TABLE_NAME_FLASHCARDS}"
    const val SQL_DELETE_TABLE_SETTINGS = "DROP TABLE IF EXISTS ${TableInfo.TABLE_NAME_SETTINGS}"
}

class DataBaseHelper(context: Context) :
    SQLiteOpenHelper(context, TableInfo.DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(BasicCommand.SQL_CREATE_TABLE_PACKAGES)
        db?.execSQL(BasicCommand.SQL_CREATE_TABLE_FLASHCARDS)
        db?.execSQL(BasicCommand.SQL_CREATE_TABLE_SETTINGS)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(BasicCommand.SQL_DELETE_TABLE_PACKAGES)
        db?.execSQL(BasicCommand.SQL_DELETE_TABLE_FLASHCARDS)
        db?.execSQL(BasicCommand.SQL_DELETE_TABLE_SETTINGS)

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

    fun addPackage(name: String, userId: Long): Boolean {
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

    fun getFlashcard(flashcardId: Long): MyFlashcard {
        var flashcardsList = ArrayList<MyFlashcard>()
        val db = readableDatabase
        val selectQuery =
            "Select * from ${TableInfo.TABLE_NAME_FLASHCARDS} where ${BaseColumns._ID} = " + flashcardId.toString()
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
                flashcardsList.add(flashcard)
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return flashcardsList[0]
    }

    fun getNewFlashcardsList(userId: Long): ArrayList<MyFlashcard> {
        var flascardsList = ArrayList<MyFlashcard>()
        val db = readableDatabase
        val selectQuery =
            "Select * from ${TableInfo.TABLE_NAME_FLASHCARDS} where ${TableInfo.TABLE_COLUMN_FLASHCARDS_IS_NEW} = 1" +
                    " and ${TableInfo.TABLE_COLUMN_FLASHCARDS_USER_ID} = " + userId.toString()
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

    fun getSettings(userId: Long): ArrayList<Settings> {
        var settingsList = ArrayList<Settings>()
        val db = readableDatabase
        val selectQuery = "Select * from ${TableInfo.TABLE_NAME_PACKAGES} where " +
                "${TableInfo.TABLE_COLUMN_SETTINGS_USER_ID} = " + userId.toString()
        val result = db.rawQuery(selectQuery, null)
        if (result.moveToFirst()) {
            do {
                var mySettings = Settings(
                    result.getInt(result.getColumnIndex(BaseColumns._ID)).toLong(),
                    result.getString(result.getColumnIndex(TableInfo.TABLE_COLUMN_PACKAGES_USER_ID))
                        .toLong(),
                    result.getInt(result.getColumnIndex(TableInfo.TABLE_COLUMN_SETTINGS_REVIEW_CLASSIC_AMOUNT)),
                    result.getInt(result.getColumnIndex(TableInfo.TABLE_COLUMN_SETTINGS_LEARNING_AMOUNT)),
                    result.getInt(result.getColumnIndex(TableInfo.TABLE_COLUMN_SETTINGS_REVIEW_HARD_AMOUNT))
                )
                settingsList.add(mySettings)
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return settingsList
    }

    fun addSettings(userId: Long, review: Int, learning: Int, hard: Int): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TableInfo.TABLE_COLUMN_SETTINGS_USER_ID, userId)
        values.put(TableInfo.TABLE_COLUMN_SETTINGS_REVIEW_CLASSIC_AMOUNT, review)
        values.put(TableInfo.TABLE_COLUMN_SETTINGS_LEARNING_AMOUNT, learning)
        values.put(TableInfo.TABLE_COLUMN_SETTINGS_REVIEW_HARD_AMOUNT, hard)
        val success = db.insert(TableInfo.TABLE_NAME_SETTINGS, null, values)
        db.close()
        return (Integer.parseInt("$success") != -1)
    }

    fun updateSettings(settings: Settings): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TableInfo.TABLE_COLUMN_SETTINGS_USER_ID, settings.userId)
        values.put(
            TableInfo.TABLE_COLUMN_SETTINGS_REVIEW_CLASSIC_AMOUNT,
            settings.reviewClassicAmount
        )
        values.put(TableInfo.TABLE_COLUMN_SETTINGS_LEARNING_AMOUNT, settings.learningAmount)
        values.put(TableInfo.TABLE_COLUMN_SETTINGS_REVIEW_HARD_AMOUNT, settings.reviewHardAmount)
        val success = db.update(
            TableInfo.TABLE_NAME_SETTINGS, values, BaseColumns._ID + "=?",
            arrayOf(settings.id.toString())
        ).toLong()
        return Integer.parseInt("$success") != -1
    }


    fun deleteSettings(id: Long): Boolean {
        val db = this.writableDatabase
        val success =
            db.delete(TableInfo.TABLE_NAME_SETTINGS, BaseColumns._ID + "=?", arrayOf(id.toString()))
                .toLong()
        db.close()
        return Integer.parseInt("$success") != -1
    }


}