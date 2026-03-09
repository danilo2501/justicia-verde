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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dani.denuncia.Componentes.UserPreferencesDataStore
import com.dani.denuncia.ui.theme.DenunciaTheme
import com.dani.denuncia.ui.theme.VerdePrincipal
import kotlinx.coroutines.launch

@Composable
fun PantallaInicio(Siguiente: (String) -> Unit, userPrefs: UserPreferencesDataStore) {
    var nombre by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    // Fondo degradado vertical sutil
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.background
                    )
                )
            )
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // --- Icono circular con degradado radial ---
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .shadow(
                        elevation = 12.dp,
                        shape = CircleShape,
                        ambientColor = VerdePrincipal.copy(alpha = 0.3f),
                        spotColor   = VerdePrincipal.copy(alpha = 0.4f)
                    )
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.primaryContainer
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector    = Icons.Default.Person,
                    contentDescription = "Ícono de usuario",
                    tint   = Color.White,
                    modifier = Modifier.size(54.dp)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // --- Título ---
            Text(
                text  = "Justicia Verde",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight    = FontWeight.ExtraBold,
                textAlign     = TextAlign.Center,
                letterSpacing = (-0.5).sp
            )

            Text(
                text  = "Reporta y protege el medio ambiente",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.65f),
                textAlign = TextAlign.Center,
                modifier  = Modifier.padding(top = 6.dp, bottom = 36.dp)
            )

            // --- Card de login ---
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation    = 14.dp,
                        shape        = RoundedCornerShape(24.dp),
                        ambientColor = VerdePrincipal.copy(alpha = 0.12f),
                        spotColor    = VerdePrincipal.copy(alpha = 0.18f)
                    ),
                shape  = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {

                    Text(
                        text  = "Bienvenido",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    // Campo de nombre
                    OutlinedTextField(
                        value       = nombre,
                        onValueChange = { nombre = it },
                        label       = { Text("Tu nombre") },
                        placeholder = { Text("Ej. María García") },
                        modifier    = Modifier.fillMaxWidth(),
                        singleLine  = true,
                        shape  = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor   = MaterialTheme.colorScheme.primary,
                            focusedLabelColor    = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            cursorColor          = MaterialTheme.colorScheme.primary
                        )
                    )

                    // Botón "Ingresar" con degradado horizontal
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .shadow(
                                elevation  = 6.dp,
                                shape      = RoundedCornerShape(14.dp),
                                spotColor  = VerdePrincipal.copy(alpha = 0.35f)
                            )
                            .clip(RoundedCornerShape(14.dp))
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primary,
                                        MaterialTheme.colorScheme.tertiary
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = {
                                val nombreFinal = nombre.trim().ifBlank { "Anónimo" }
                                coroutineScope.launch {
                                    userPrefs.saveUserData(nombreFinal, false)
                                }
                                Siguiente(nombreFinal)
                            },
                            modifier  = Modifier.fillMaxSize(),
                            shape     = RoundedCornerShape(14.dp),
                            colors    = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor   = Color.White
                            ),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 0.dp,
                                pressedElevation = 0.dp
                            )
                        ) {
                            Text(
                                text          = "Ingresar",
                                fontWeight    = FontWeight.Bold,
                                fontSize      = 16.sp,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            // --- Separador "o continúa como" ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.35f))
                )
                Text(
                    text  = "o continúa como",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.35f))
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- Botón Cuenta Anónima (outlined) ---
            OutlinedButton(
                onClick = {
                    coroutineScope.launch {
                        userPrefs.saveUserData("Anónimo", true)
                    }
                    Siguiente("Anónimo")
                },
                modifier  = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape     = RoundedCornerShape(14.dp),
                colors    = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text       = "Cuenta Anónima",
                    fontWeight = FontWeight.SemiBold,
                    fontSize   = 15.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text  = "Tus reportes son confidenciales y seguros",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true, name = "Inicio — Modo Claro")
@Composable
fun PantallaInicioPreview() {
    DenunciaTheme(darkTheme = false) {
        PantallaInicio(
            Siguiente = {},
            userPrefs = UserPreferencesDataStore(LocalContext.current)
        )
    }
}

@Preview(showBackground = true, name = "Inicio — Modo Oscuro",
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PantallaInicioPreviewDark() {
    DenunciaTheme(darkTheme = true) {
        PantallaInicio(
            Siguiente = {},
            userPrefs = UserPreferencesDataStore(LocalContext.current)
        )
    }
}
