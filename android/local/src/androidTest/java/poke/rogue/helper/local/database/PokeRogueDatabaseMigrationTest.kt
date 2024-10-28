package poke.rogue.helper.local.database

import android.database.sqlite.SQLiteDatabase
import androidx.core.content.contentValuesOf
import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.test.platform.app.InstrumentationRegistry
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEmpty
import io.kotest.matchers.string.shouldNotBeEmpty
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import poke.rogue.helper.local.db.PokeRogueDatabase
import poke.rogue.helper.local.entity.PokemonEntity
import poke.rogue.helper.local.utils.migrationTestCase
import poke.rogue.helper.local.utils.testContext
import java.io.IOException

/**
 * [PokeRogueDatabase]의 AutoMigration 테스트
 *
 * ref: https://cs.android.com/androidx/architecture-components-samples/+/main:PersistenceMigrationsSample/app/src/androidTestRoom3/java/com/example/android/persistence/migrations/MigrationTest.java;l=58?q=MigrationTestHelper
 * ref2: https://github.com/Ivy-Apps/ivy-wallet/blob/main/shared/data/core/src/androidTest/java/com/ivy/data/db/Migration128to129Test.kt#L29
 * docs: https://developer.android.com/training/data-storage/room/migrating-db-versions#all-migrations-test
 */
class PokeRogueDatabaseMigrationTest {
    @get:Rule
    val helper =
        MigrationTestHelper(
            InstrumentationRegistry.getInstrumentation(),
            PokeRogueDatabase::class.java,
        )

    @Test
    @Throws(IOException::class)
    @DisplayName("버전 1에서 버전 2로 마이그레이션 데이터 무결성 테스트 - backImageUrl 추가")
    fun test_migration1To2() =
        helper.migrationTestCase(
            tableName = PokemonEntity.TABLE_NAME,
            from = 1,
            to = 2,
            onBeforeMigration = {
                val contentValue =
                    contentValuesOf(
                        "id" to 1,
                        "dexNumber" to 25,
                        "formName" to "Normal",
                        "name" to "Pikachu",
                        "imageUrl" to "url_to_image",
                        "types" to "Electric",
                        "generation" to 1,
                        "baseStat" to 320,
                        "hp" to 35,
                        "attack" to 55,
                        "defense" to 40,
                        "specialAttack" to 50,
                        "specialDefense" to 50,
                        "speed" to 90,
                    )
                insert(PokemonEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE, contentValue)
            },
            onAfterMigration = {
                moveToFirst() shouldBe true
                val backImageUrlIndex = getColumnIndex("backImageUrl")
                val backImageUrl = getString(backImageUrlIndex)
                backImageUrl.shouldBeEmpty()
                moveToNext() shouldBe false
            },
            migrations = PokeRogueDatabase.MIGRATIONS,
        )

    @Test
    @Throws(IOException::class)
    @DisplayName("버전 2에서 버전 3로 마이그레이션 데이터 무결성 테스트 - backImageUrl 에 defaultValue 추가")
    fun test_migration2To3() =
        helper.migrationTestCase(
            tableName = PokemonEntity.TABLE_NAME,
            from = 2,
            to = 3,
            onBeforeMigration = {
                val contentValue =
                    contentValuesOf(
                        "id" to 1,
                        "dexNumber" to 25,
                        "formName" to "Normal",
                        "name" to "Pikachu",
                        "imageUrl" to "url_to_image",
                        "types" to "Electric",
                        "generation" to 1,
                        "baseStat" to 320,
                        "hp" to 35,
                        "attack" to 55,
                        "backImageUrl" to "testUrl",
                        "defense" to 40,
                        "specialAttack" to 50,
                        "specialDefense" to 50,
                        "speed" to 90,
                    )
                insert(PokemonEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE, contentValue)
            },
            onAfterMigration = {
                moveToFirst() shouldBe true
                val backImageUrlIndex = getColumnIndex("backImageUrl")
                val backImageUrl = getString(backImageUrlIndex)
                backImageUrl.shouldNotBeEmpty()
                backImageUrl shouldBe "testUrl"
                moveToNext() shouldBe false
            },
            migrations = PokeRogueDatabase.MIGRATIONS,
        )

    @Test
    @DisplayName("모든 database 버전 마이그레이션 테스트")
    @Throws(IOException::class)
    fun test_migrateAll() {
        // given
        val dbName = "TEST_DB"
        val oldestDbVersion = 1
        // when : 가장 오래된 버전의 DB를 생성하고 닫음
        helper.createDatabase(dbName, oldestDbVersion)
            .close()

        // then : 최신 버전 DB로 마이그레이션 후 검증
        Room.databaseBuilder(
            testContext,
            PokeRogueDatabase::class.java,
            dbName,
        ).addMigrations(*PokeRogueDatabase.MIGRATIONS)
            .build().apply {
                openHelper.writableDatabase.close()
            }
    }
}
