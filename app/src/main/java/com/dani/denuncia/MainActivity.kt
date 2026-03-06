package com.dani.denuncia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dani.denuncia.Componentes.FormularioReporteMaltrato
import com.dani.denuncia.Componentes.ReporteCompleto
import com.dani.denuncia.Componentes.UserPreferencesDataStore
import com.dani.denuncia.Componentes.UserPreferences
import com.dani.denuncia.Pantallas.PantallaPrincipal
import com.dani.denuncia.Pantallas.ReportesScreen
import com.dani.denuncia.Pantallas.PantallaInicio
import com.dani.denuncia.ui.theme.DenunciaTheme
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    // Instancia del DataStore (tipo inferido correctamente con la implementación)
    private val userPrefs by lazy { UserPreferencesDataStore(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DenunciaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavegacionPantallas(userPrefs)
                }
            }
        }
    }
}

// Composable que gestiona la navegación entre pantallas
@Composable
fun NavegacionPantallas(userPrefs: UserPreferencesDataStore) {
    val navController = rememberNavController()

    // Observa los datos del usuario desde DataStore
    val userData by userPrefs.getUserData().collectAsState(
        initial = UserPreferences("", false) // Valor por defecto si no hay datos
    )

    // Definición de rutas de navegación
    NavHost(navController = navController, startDestination = "login") {
        // Pantalla de inicio donde se ingresa el nombre de usuario
        composable("login") {
            PantallaInicio(userPrefs = userPrefs, Siguiente = { nombre ->
                navController.navigate("home/$nombre")
            })
        }

        // Pantalla principal después del login
        composable("home/{username}") { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("username") ?: ""
            PantallaPrincipal(
                nombre = nombre,
                Atras = { navController.popBackStack(route = "login", inclusive = false) },
                IrAFormulario = { navController.navigate("formulario") },
                IrAReporte = { navController.navigate("reporte") }
            )
        }

        // Pantalla que muestra la lista de reportes
        composable("reporte") {
            ReportesScreen { reporteId ->
                navController.navigate("Reporte_completo/$reporteId")
            }
        }

        // Pantalla que muestra el detalle completo de un reporte
        composable("Reporte_completo/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            var reporte by remember { mutableStateOf<Map<String, Any>?>(null) }

            // Carga los datos del reporte desde Firebase usando el ID
            LaunchedEffect(id) {
                FirebaseDatabase.getInstance().getReference("reportes")
                    .child(id)
                    .get()
                    .addOnSuccessListener { snapshot ->
                        reporte = snapshot.value as? Map<String, Any>
                    }
            }

            // Si el reporte fue cargado, se muestra el componente con los datos
            reporte?.let {
                ReporteCompleto(
                    titulo = it["tipo"].toString(),
                    descripcion = it["descripcion"].toString(),
                    ubicacion = it["ubicacion"].toString(),
                    fecha = it["fecha"].toString().toLong(),
                    usuario = it["usuario"].toString()
                )
            } ?: Text("Cargando reporte...")
        }

        // Pantalla del formulario para crear un nuevo reporte
        composable("formulario") {
            FormularioReporteMaltrato(
                Atras = {
                    navController.popBackStack(
                        route = "home/${userData.nombre}",
                        inclusive = false
                    )
                },
                nombreUsuario = userData.nombre,
                anonimo = userData.anonimo
            )
        }
    }
}
