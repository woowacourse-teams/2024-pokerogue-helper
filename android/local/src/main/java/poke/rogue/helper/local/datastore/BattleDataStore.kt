
package poke.rogue.helper.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class SavedPokemonWithSkill(val pokemonId: String, val skillId: String)

class BattleDataStore(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = BATTLE_PREFERENCE_NAME)

    suspend fun savePokemonWithSkill(
        pokemonId: String,
        skillId: String,
    ) {
        context.dataStore.edit {
            it[PAIR_POKEMON_SELECTION_KEY] = pokemonId
            it[PAIR_SKILL_SELECTION_KEY] = skillId
        }
    }

    suspend fun savePokemon(pokemonId: String) {
        context.dataStore.edit {
            it[SINGLE_POKEMON_SELECTION_KEY] = pokemonId
        }
    }

    fun pokemonWithSkillId(): Flow<SavedPokemonWithSkill?> =
        context.dataStore.data.map { preference ->
            val pokemonId = preference[PAIR_POKEMON_SELECTION_KEY]
            val skillId = preference[PAIR_SKILL_SELECTION_KEY]
            if (pokemonId == null || skillId == null) {
                null
            } else {
                SavedPokemonWithSkill(pokemonId, skillId)
            }
        }

    fun pokemonId(): Flow<String?> =
        context.dataStore.data.map { preferences ->
            preferences[SINGLE_POKEMON_SELECTION_KEY]
        }

    private companion object {
        const val BATTLE_PREFERENCE_NAME = "battle"
        val PAIR_POKEMON_SELECTION_KEY = stringPreferencesKey("pair_pokemon_selection")
        val PAIR_SKILL_SELECTION_KEY = stringPreferencesKey("pair_skill_selection")
        val SINGLE_POKEMON_SELECTION_KEY = stringPreferencesKey("single_pokemon_selection")
    }
}
