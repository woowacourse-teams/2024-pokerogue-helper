package poke.rogue.helper.local.utils

import android.database.Cursor
import androidx.room.migration.Migration
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * [MigrationTestHelper]를 사용하여 마이그레이션 테스트를 BDD 스타일로 작성하기 위한 확장 함수
 *
 * ref: https://github.com/Ivy-Apps/ivy-wallet/blob/main/shared/data/core/src/androidTest/java/com/ivy/data/db/Migration128to129Test.kt#L29
 * */
fun MigrationTestHelper.migrationTestCase(
    tableName: String,
    from: Int,
    to: Int,
    onBeforeMigration: SupportSQLiteDatabase.() -> Unit,
    onAfterMigration: Cursor.() -> Unit,
    testDbName: String = "test-db",
    vararg migrations: Migration,
) {
    // Given
    createDatabase(testDbName, from).apply {
        onBeforeMigration()
        close()
    }

    // When
    val newDb = runMigrationsAndValidate(
        testDbName,
        to,
        true,
        *migrations,
    )

    // Then
    newDb.query("SELECT * FROM $tableName").apply {
        onAfterMigration()
    }
    newDb.close()
}