package poke.rogue.helper.local.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val Migration1To2 =
    object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE Pokemon ADD COLUMN backImageUrl TEXT NOT NULL DEFAULT ''")
        }
    }
