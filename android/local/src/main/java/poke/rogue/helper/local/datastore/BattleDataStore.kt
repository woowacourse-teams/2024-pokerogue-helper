package poke.rogue.helper.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class SavedPokemonWithSkill(val pokemonId: String, val skillId: String)

class BattleDataStore(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = BATTLE_PREFERENCE_NAME)

    suspend fun savePokemonWithSkill(
        pokemonId: String,
        skillId: String,
    ) {
        val data = SavedPokemonWithSkill(pokemonId, skillId)
        val saved = Json.encodeToString(data)
        context.dataStore.edit {
            it[POKEMON_WITH_SKILL_SELECTION_KEY] = saved
        }
    }

    suspend fun savePokemon(pokemonId: String) {
        context.dataStore.edit {
            it[POKEMON_SELECTION_KEY] = pokemonId
        }
    }

    fun pokemonWithSkillId(): Flow<SavedPokemonWithSkill?> =
        context.dataStore.data.map { preference ->
            val data = preference[POKEMON_WITH_SKILL_SELECTION_KEY]
            if (data != null) {
                Json.decodeFromString<SavedPokemonWithSkill>(data)
            } else {
                null
            }
        }

    fun pokemonId(): Flow<String?> =
        context.dataStore.data.map { preferences ->
            preferences[POKEMON_SELECTION_KEY]
        }

    private companion object {
        const val BATTLE_PREFERENCE_NAME = "battle"
        val POKEMON_WITH_SKILL_SELECTION_KEY = stringPreferencesKey("pokemon_with_skill_selection")
        val POKEMON_SELECTION_KEY = stringPreferencesKey("pokemon_selection")
    }
}
