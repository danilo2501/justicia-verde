package com.dani.denuncia.Pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dani.denuncia.Componentes.ReporteEnLista
import com.dani.denuncia.ui.theme.DenunciaTheme
import com.dani.denuncia.ui.theme.VerdePrincipal
import com.google.firebase.database.FirebaseDatabase

@Composable
fun ReportesScreen(onClickReporte: (String) -> Unit = {}) {
    val database = FirebaseDatabase.getInstance().getReference("reportes")
    val reportes = remember { mutableStateListOf<Map<String, Any>>() }

    LaunchedEffect(Unit) {
        database.get().addOnSuccessListener { snapshot ->
            reportes.clear()
            snapshot.children.forEach { child ->
                child.value?.let { value ->
                    if (value is Map<*, *>) {
                        @Suppress("UNCHECKED_CAST")
                        reportes.add(value as Map<String, Any>)
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.background
                    ),
                    startY = 0f,
                    endY   = 900f
                )
            )
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {

            // --- Header con degradado ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation    = 10.dp,
                        shape        = RoundedCornerShape(20.dp),
                        ambientColor = VerdePrincipal.copy(alpha = 0.15f),
                        spotColor    = VerdePrincipal.copy(alpha = 0.2f)
                    )
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.secondary
                            )
                        )
                    )
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = "Reportes",
                            tint     = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text  = "Lista de Reportes",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign  = TextAlign.Center
                    )

                    Text(
                        text  = "${reportes.size} reporte${if (reportes.size != 1) "s" else ""} encontrado${if (reportes.size != 1) "s" else ""}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.85f),
                        textAlign = TextAlign.Center,
                        modifier  = Modifier.padding(top = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --- Lista de reportes o estado vacío ---
            if (reportes.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .shadow(
                            elevation    = 6.dp,
                            shape        = RoundedCornerShape(18.dp),
                            ambientColor = VerdePrincipal.copy(alpha = 0.1f)
                        ),
                    shape  = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp)
                    ) {
                        items(reportes) { reporte ->
                            ReporteEnLista(
                                titulo      = (reporte["tipo"]        ?: "Sin tipo").toString(),
                                descripcion = (reporte["descripcion"] ?: "Sin descripción").toString(),
                                ubicacion   = (reporte["ubicacion"]   ?: "Sin ubicación").toString(),
                                usuario     = (reporte["usuario"]     ?: "Anónimo").toString(),
                                fecha       = (reporte["fecha"]       ?: "").toString(),
                                onClick     = { onClickReporte(reporte["id"].toString()) }
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
            } else {
                // Estado vacío
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .shadow(
                            elevation = 4.dp,
                            shape     = RoundedCornerShape(18.dp)
                        ),
                    shape  = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text     = "📋",
                            fontSize = 60.sp
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text  = "Sin reportes aún",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            fontWeight = FontWeight.SemiBold
                        )

                        Text(
                            text  = "Los reportes aparecerán aquí cuando sean creados",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.45f),
                            textAlign = TextAlign.Center,
                            modifier  = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Reportes — Modo Claro")
@Composable
fun PreviewReportesScreen() {
    DenunciaTheme(darkTheme = false) { ReportesScreen() }
}

@Preview(showBackground = true, name = "Reportes — Modo Oscuro",
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewReportesScreenDark() {
    DenunciaTheme(darkTheme = true) { ReportesScreen() }
}
