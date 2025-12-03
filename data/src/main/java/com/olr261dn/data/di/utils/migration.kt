package com.olr261dn.data.di.utils

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

internal val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE GoalEntity ADD COLUMN isDisabled INTEGER NOT NULL DEFAULT 0")
        db.execSQL("UPDATE GoalEntity SET isDisabled = isCompleted")
//        db.execSQL("UPDATE GoalEntity SET isDisabled = CASE WHEN isCompleted = 1 THEN 0 ELSE 1 END")
        db.execSQL("CREATE TABLE GoalEntity_new (id BLOB PRIMARY KEY NOT NULL, title TEXT NOT NULL, scheduledTime INTEGER NOT NULL, delayedTime INTEGER, description TEXT, createdAt INTEGER NOT NULL, isDisabled INTEGER NOT NULL)")
        db.execSQL("INSERT INTO GoalEntity_new (id, title, scheduledTime, delayedTime, description, createdAt, isDisabled) SELECT id, title, scheduledTime, delayedTime, description, createdAt, isDisabled FROM GoalEntity")
        db.execSQL("DROP TABLE GoalEntity")
        db.execSQL("ALTER TABLE GoalEntity_new RENAME TO GoalEntity")
    }
}
val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE goals_new (
                id TEXT PRIMARY KEY NOT NULL,
                title TEXT NOT NULL,
                description TEXT NOT NULL,
                scheduledTime INTEGER NOT NULL,
                createdAt INTEGER NOT NULL,
                isDisabled INTEGER NOT NULL
            )
            """.trimIndent()
        )
        db.execSQL(
            """
            INSERT INTO goals_new (id, title, description, scheduledTime, createdAt, isDisabled)
            SELECT id, title, COALESCE(description, ''), scheduledTime, createdAt, isDisabled
            FROM goals
            """.trimIndent()
        )
        db.execSQL("DROP TABLE goals")
        db.execSQL("ALTER TABLE goals_new RENAME TO goals")
    }
}

val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Step 1: Add the new scheduleType column with default value
        db.execSQL(
            "ALTER TABLE RecurringTaskEntity ADD COLUMN scheduleType TEXT NOT NULL DEFAULT 'EVERY_DAY'"
        )

        // Step 2: Migrate existing data based on isWorkDaysOnly
        db.execSQL(
            """
            UPDATE RecurringTaskEntity 
            SET scheduleType = CASE 
                WHEN isWorkDaysOnly = 1 THEN 'WORKDAYS_ONLY' 
                ELSE 'EVERY_DAY' 
            END
            """
        )

        // Step 3: Create new table without isWorkDaysOnly
        db.execSQL(
            """
            CREATE TABLE RecurringTaskEntity_new (
                id BLOB NOT NULL PRIMARY KEY,
                title TEXT NOT NULL,
                description TEXT NOT NULL,
                scheduledTime INTEGER NOT NULL,
                delayedTime INTEGER,
                isDisabled INTEGER NOT NULL,
                scheduleType TEXT NOT NULL
            )
            """
        )

        // Step 4: Copy data to new table
        db.execSQL(
            """
            INSERT INTO RecurringTaskEntity_new (id, title, description, scheduledTime, delayedTime, isDisabled, scheduleType)
            SELECT id, title, description, scheduledTime, delayedTime, isDisabled, scheduleType
            FROM RecurringTaskEntity
            """
        )

        // Step 5: Drop old table
        db.execSQL("DROP TABLE RecurringTaskEntity")

        // Step 6: Rename new table
        db.execSQL("ALTER TABLE RecurringTaskEntity_new RENAME TO RecurringTaskEntity")
    }
}

val MIGRATION_5_6 = object : Migration(5, 6) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "ALTER TABLE RecurringTaskEntity ADD COLUMN customDays TEXT NOT NULL DEFAULT ''"
        )
    }
}