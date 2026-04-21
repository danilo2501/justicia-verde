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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dani.denuncia.Componentes.ReporteCompleto
import com.dani.denuncia.Componentes.FormularioReporteMaltrato
import com.dani.denuncia.Componentes.UserPreferencesDataStore
import com.dani.denuncia.Componentes.UserPreferences
import com.dani.denuncia.Pantallas.PantallaPrincipal
import com.dani.denuncia.Pantallas.ReportesScreen
import com.dani.denuncia.Pantallas.PantallaInicio
import com.dani.denuncia.ui.theme.DenunciaTheme
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {

    private val userPrefs by lazy { UserPreferencesDataStore(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DenunciaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color    = MaterialTheme.colorScheme.background
                ) {
                    NavegacionPantallas(userPrefs)
                }
            }
        }
    }
}

@Composable
fun NavegacionPantallas(userPrefs: UserPreferencesDataStore) {
    val navController = rememberNavController()

    val userData by userPrefs.getUserData().collectAsState(
        initial = UserPreferences("", false)
    )

    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            PantallaInicio(
                userPrefs = userPrefs,
                Siguiente = { nombre -> navController.navigate("home/$nombre") }
            )
        }

        composable("home/{username}") { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("username") ?: ""
            PantallaPrincipal(
                nombre       = nombre,
                Atras        = { navController.popBackStack(route = "login", inclusive = false) },
                IrAFormulario = { navController.navigate("formulario") },
                IrAReporte   = { navController.navigate("reporte") }
            )
        }

        composable("reporte") {
            ReportesScreen { reporteId ->
                navController.navigate("Reporte_completo/$reporteId")
            }
        }

        composable("Reporte_completo/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            var reporte by remember { mutableStateOf<Map<String, Any>?>(null) }

            LaunchedEffect(id) {
                FirebaseFirestore.getInstance().collection("reportes")
                    .document(id)
                    .get()
                    .addOnSuccessListener { doc ->
                        reporte = doc.data?.let { it + mapOf("id" to doc.id) }
                    }
            }

            reporte?.let {
                @Suppress("UNCHECKED_CAST")
                val imagenesUrls = (it["imagenesUrls"] as? List<*>)
                    ?.filterIsInstance<String>() ?: emptyList()
                ReporteCompleto(
                    titulo        = it["tipo"].toString(),
                    descripcion   = it["descripcion"].toString(),
                    ubicacion     = it["ubicacion"].toString(),
                    fecha         = (it["fecha"] as? Long) ?: it["fecha"].toString().toLongOrNull() ?: 0L,
                    usuario       = it["usuario"].toString(),
                    imagenesUrls  = imagenesUrls
                )
            } ?: Text("Cargando reporte...")
        }

        composable("formulario") {
            FormularioReporteMaltrato(
                Atras = {
                    navController.popBackStack(
                        route     = "home/${userData.nombre}",
                        inclusive = false
                    )
                },
                nombreUsuario = userData.nombre,
                anonimo       = userData.anonimo
            )
        }
    }
}
