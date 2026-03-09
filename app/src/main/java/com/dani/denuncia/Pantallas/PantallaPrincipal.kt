package com.dani.denuncia.Pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dani.denuncia.ui.theme.DenunciaTheme
import com.dani.denuncia.ui.theme.VerdePrincipal

@Composable
fun PantallaPrincipal(
    nombre: String,
    Atras: (String) -> Unit,
    IrAFormulario: () -> Unit,
    IrAReporte: () -> Unit
) {
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
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // --- Saludo personalizado ---
            Text(
                text  = "Hola, $nombre",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.ExtraBold,
                textAlign  = TextAlign.Center
            )
            Text(
                text  = "¿Qué deseas hacer hoy?",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                textAlign = TextAlign.Center,
                modifier  = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // --- Card encabezado con degradado interior ---
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation    = 12.dp,
                        shape        = RoundedCornerShape(20.dp),
                        ambientColor = VerdePrincipal.copy(alpha = 0.15f),
                        spotColor    = VerdePrincipal.copy(alpha = 0.2f)
                    ),
                shape  = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.tertiary
                                )
                            )
                        )
                        .padding(24.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                           modifier = Modifier.fillMaxWidth()) {

                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.White.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text     = "🌿",
                                fontSize = 36.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text  = "Centro de Reportes",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            textAlign  = TextAlign.Center
                        )

                        Text(
                            text  = "Tu ayuda marca la diferencia",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.85f),
                            textAlign = TextAlign.Center,
                            modifier  = Modifier.padding(top = 6.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // --- Botón: Hacer Nueva Denuncia ---
            AccionCard(
                titulo    = "Hacer Nueva Denuncia",
                subtitulo = "Reporta tu caso ambiental",
                icono     = Icons.Default.Warning,
                gradiente = Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.error,
                        MaterialTheme.colorScheme.errorContainer
                    )
                ),
                colorIcono = MaterialTheme.colorScheme.onError,
                colorTexto = MaterialTheme.colorScheme.onError,
                onClick    = IrAFormulario
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- Botón: Ver Reportes ---
            AccionCard(
                titulo    = "Ver Reportes",
                subtitulo = "Consulta las denuncias existentes",
                icono     = Icons.Default.List,
                gradiente = Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary
                    )
                ),
                colorIcono = Color.White,
                colorTexto = Color.White,
                onClick    = IrAReporte
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text  = "Todos los reportes son confidenciales",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.45f),
                textAlign = TextAlign.Center,
                modifier  = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // --- Botón Salir ---
            Button(
                onClick = { Atras("Login") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape  = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor   = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                elevation = ButtonDefaults.buttonElevation(0.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = "Salir",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Salir",
                    fontWeight = FontWeight.SemiBold,
                    fontSize   = 15.sp
                )
            }
        }
    }
}

// --- Componente reutilizable: Tarjeta de acción con degradado ---
@Composable
fun AccionCard(
    titulo: String,
    subtitulo: String,
    icono: ImageVector,
    gradiente: Brush,
    colorIcono: Color,
    colorTexto: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp)
            .shadow(
                elevation = 8.dp,
                shape     = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(gradiente)
    ) {
        Button(
            onClick   = onClick,
            modifier  = Modifier.fillMaxSize(),
            shape     = RoundedCornerShape(16.dp),
            colors    = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor   = colorTexto
            ),
            elevation = ButtonDefaults.buttonElevation(0.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icono,
                        contentDescription = titulo,
                        tint     = colorIcono,
                        modifier = Modifier.size(26.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        text  = titulo,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize   = 16.sp,
                        color      = colorTexto
                    )
                    Text(
                        text  = subtitulo,
                        style = MaterialTheme.typography.bodySmall,
                        color = colorTexto.copy(alpha = 0.85f),
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Principal — Modo Claro")
@Composable
fun PreviewPantallaPrincipal() {
    DenunciaTheme(darkTheme = false) {
        PantallaPrincipal(
            nombre      = "María",
            Atras       = {},
            IrAFormulario = {},
            IrAReporte  = {}
        )
    }
}

@Preview(showBackground = true, name = "Principal — Modo Oscuro",
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewPantallaPrincipalDark() {
    DenunciaTheme(darkTheme = true) {
        PantallaPrincipal(
            nombre      = "María",
            Atras       = {},
            IrAFormulario = {},
            IrAReporte  = {}
        )
    }
}
