package com.dani.denuncia.Componentes

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Data class para las preferencias del usuario
data class UserPreferences(val nombre: String, val anonimo: Boolean)

// Extensión para DataStore
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "userPrefs")

class UserPreferencesDataStore(private val context: Context) {
    companion object {
        val NOMBRE_KEY = stringPreferencesKey("nombre")
        val ANONIMO_KEY = booleanPreferencesKey("anonimo")
    }

    private val dataStore = context.dataStore

    // Obtener datos como Flow para usar en Compose
    fun getUserData(): Flow<UserPreferences> = dataStore.data.map { preferences ->
        UserPreferences(
            nombre = preferences[NOMBRE_KEY] ?: "",
            anonimo = preferences[ANONIMO_KEY] ?: false
        )
    }

    // Guardar datos (suspend function para coroutines)
    suspend fun saveUserData(nombre: String, anonimo: Boolean) {
        dataStore.edit { preferences ->
            preferences[NOMBRE_KEY] = nombre
            preferences[ANONIMO_KEY] = anonimo
        }
    }
}

