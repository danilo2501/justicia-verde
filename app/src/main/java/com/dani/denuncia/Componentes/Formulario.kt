package com.dani.denuncia.Componentes

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dani.denuncia.ui.theme.DenunciaTheme
import com.dani.denuncia.ui.theme.VerdePrincipal
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

// Modelo de datos
data class Formulario(
    val tipo: String,
    val descripcion: String,
    val ubicacion: String,
    val imagenesUrls: List<String>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioReporteMaltrato(
    nombreUsuario: String,
    anonimo: Boolean,
    Atras: (String) -> Unit
) {
    val opciones = listOf(
        "Contaminación de recursos hídricos",
        "Contaminación del aire",
        "Afectación a la biodiversidad",
        "Daños al suelo y ecosistemas",
        "Contaminación sonora"
    )

    val coroutineScope = rememberCoroutineScope()

    var expanded            by remember { mutableStateOf(false) }
    var opcionSeleccionada  by remember { mutableStateOf("") }
    var descripcion         by remember { mutableStateOf("") }
    var ubicacion           by remember { mutableStateOf("") }
    var showConfirmation    by remember { mutableStateOf(false) }
    var isUploading         by remember { mutableStateOf(false) }
    val imagenesSeleccionadas = remember { mutableStateListOf<Uri>() }

    // Selector de imágenes (hasta 5)
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 5)
    ) { uris ->
        val disponibles = 5 - imagenesSeleccionadas.size
        val nuevas = uris.take(disponibles)
        imagenesSeleccionadas.addAll(nuevas)
    }

    val isFormValid = opcionSeleccionada.isNotBlank() &&
            descripcion.isNotBlank() &&
            ubicacion.isNotBlank()

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
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
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
                                MaterialTheme.colorScheme.error,
                                MaterialTheme.colorScheme.primary
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
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Reportar",
                            tint     = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text  = "Reportar Caso Ambiental",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign  = TextAlign.Center
                    )

                    Text(
                        text  = "Tu reporte puede marcar la diferencia",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.85f),
                        textAlign = TextAlign.Center,
                        modifier  = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // --- Card de campos ---
            Card(
                modifier = Modifier
                    .fillMaxWidth()
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {

                    // Tipo de caso
                    Column {
                        LabelCampo("Tipo de Caso Ambiental *")
                        Spacer(modifier = Modifier.height(8.dp))
                        ExposedDropdownMenuBox(
                            expanded      = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            TextField(
                                value       = opcionSeleccionada,
                                onValueChange = {},
                                readOnly    = true,
                                placeholder = { Text("Selecciona el tipo de denuncia") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                                },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth(),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor   = MaterialTheme.colorScheme.surfaceVariant,
                                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                                    focusedIndicatorColor   = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedTextColor        = MaterialTheme.colorScheme.onSurface,
                                    unfocusedTextColor      = MaterialTheme.colorScheme.onSurface
                                ),
                                shape = RoundedCornerShape(12.dp)
                            )

                            ExposedDropdownMenu(
                                expanded      = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                opciones.forEach { opcion ->
                                    DropdownMenuItem(
                                        text  = { Text(opcion, style = MaterialTheme.typography.bodyMedium) },
                                        onClick = {
                                            opcionSeleccionada = opcion
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    // Descripción
                    Column {
                        LabelCampo("Descripción del Caso *")
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value         = descripcion,
                            onValueChange = { descripcion = it },
                            placeholder   = { Text("Describe los detalles del caso...") },
                            modifier      = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            shape    = RoundedCornerShape(12.dp),
                            singleLine = false,
                            maxLines = 5,
                            colors   = campoColores()
                        )
                    }

                    // Ubicación
                    Column {
                        LabelCampo("Ubicación *")
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value         = ubicacion,
                            onValueChange = { ubicacion = it },
                            placeholder   = { Text("Barrio, ciudad, dirección...") },
                            modifier      = Modifier.fillMaxWidth(),
                            shape  = RoundedCornerShape(12.dp),
                            singleLine = true,
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            colors = campoColores()
                        )
                    }

                    // Selector de imágenes
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            LabelCampo("Imágenes (opcional, máx. 5)")
                            Text(
                                text  = "${imagenesSeleccionadas.size}/5",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            itemsIndexed(imagenesSeleccionadas) { index, uri ->
                                Box(
                                    modifier = Modifier.size(90.dp)
                                ) {
                                    AsyncImage(
                                        model = uri,
                                        contentDescription = "Imagen $index",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(90.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .border(
                                                1.dp,
                                                MaterialTheme.colorScheme.outline,
                                                RoundedCornerShape(10.dp)
                                            )
                                    )
                                    // Botón eliminar
                                    Box(
                                        modifier = Modifier
                                            .size(22.dp)
                                            .align(Alignment.TopEnd)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.error)
                                            .clickable { imagenesSeleccionadas.removeAt(index) },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Eliminar",
                                            tint     = Color.White,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                            }

                            // Botón agregar (solo si hay menos de 5)
                            if (imagenesSeleccionadas.size < 5) {
                                item {
                                    Box(
                                        modifier = Modifier
                                            .size(90.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .border(
                                                1.5.dp,
                                                MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                                                RoundedCornerShape(10.dp)
                                            )
                                            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f))
                                            .clickable {
                                                imagePicker.launch(
                                                    PickVisualMediaRequest(
                                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                                    )
                                                )
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Add,
                                                contentDescription = "Agregar imagen",
                                                tint     = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.size(28.dp)
                                            )
                                            Text(
                                                text  = "Agregar",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.primary,
                                                fontSize = 10.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // --- Botón Enviar con degradado ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(
                        elevation = if (isFormValid) 8.dp else 2.dp,
                        shape     = RoundedCornerShape(14.dp),
                        spotColor = VerdePrincipal.copy(alpha = 0.4f)
                    )
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        brush = if (isFormValid && !isUploading) {
                            Brush.horizontalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.tertiary
                                )
                            )
                        } else {
                            Brush.horizontalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.surfaceVariant,
                                    MaterialTheme.colorScheme.surfaceVariant
                                )
                            )
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        if (!isUploading) {
                            coroutineScope.launch {
                                isUploading = true
                                try {
                                    val db = FirebaseFirestore.getInstance()
                                    val docRef = db.collection("reportes").document()
                                    val reporteId = docRef.id

                                    // Subir imágenes a Storage
                                    val urls = imagenesSeleccionadas.mapIndexed { index, uri ->
                                        val ref = FirebaseStorage.getInstance().reference
                                            .child("reportes/$reporteId/imagen_$index.jpg")
                                        ref.putFile(uri).await()
                                        ref.downloadUrl.await().toString()
                                    }

                                    // Guardar reporte en Firestore
                                    val reporte = hashMapOf(
                                        "id"           to reporteId,
                                        "usuario"      to if (anonimo) "Anónimo" else nombreUsuario,
                                        "tipo"         to opcionSeleccionada,
                                        "descripcion"  to descripcion,
                                        "ubicacion"    to ubicacion,
                                        "imagenesUrls" to urls,
                                        "fecha"        to System.currentTimeMillis()
                                    )
                                    docRef.set(reporte).await()

                                    showConfirmation = true
                                } finally {
                                    isUploading = false
                                }
                            }
                        }
                    },
                    modifier  = Modifier.fillMaxSize(),
                    shape     = RoundedCornerShape(14.dp),
                    enabled   = isFormValid && !isUploading,
                    colors    = ButtonDefaults.buttonColors(
                        containerColor         = Color.Transparent,
                        contentColor           = Color.White,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor   = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    ),
                    elevation = ButtonDefaults.buttonElevation(0.dp)
                ) {
                    if (isUploading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(22.dp),
                            color    = Color.White,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text       = "Enviando...",
                            fontWeight = FontWeight.Bold,
                            fontSize   = 16.sp
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Enviar",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text       = "Enviar Denuncia",
                            fontWeight = FontWeight.Bold,
                            fontSize   = 16.sp,
                            letterSpacing = 0.3.sp
                        )
                    }
                }
            }

            Text(
                text  = "* Campos obligatorios",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                textAlign = TextAlign.Center,
                modifier  = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        // --- Diálogo de confirmación ---
        if (showConfirmation) {
            AlertDialog(
                onDismissRequest = { showConfirmation = false },
                containerColor   = MaterialTheme.colorScheme.surface,
                title = {
                    Text(
                        text  = "Denuncia Enviada",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Text(
                        text  = "Tu denuncia fue enviada exitosamente. Te contactaremos si necesitamos más información.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showConfirmation = false
                            Atras("home/$nombreUsuario")
                        },
                        colors  = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor   = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Aceptar", fontWeight = FontWeight.SemiBold)
                    }
                }
            )
        }
    }
}

// --- Helpers de UI ---

@Composable
private fun LabelCampo(texto: String) {
    Text(
        text  = texto,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
private fun campoColores() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor   = MaterialTheme.colorScheme.primary,
    focusedLabelColor    = MaterialTheme.colorScheme.primary,
    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
    cursorColor          = MaterialTheme.colorScheme.primary,
    focusedTextColor     = MaterialTheme.colorScheme.onSurface,
    unfocusedTextColor   = MaterialTheme.colorScheme.onSurface
)

@Preview(showBackground = true, name = "Formulario — Modo Claro")
@Composable
fun PreviewFormulario() {
    DenunciaTheme(darkTheme = false) {
        FormularioReporteMaltrato(
            nombreUsuario = "María",
            anonimo       = false,
            Atras         = {}
        )
    }
}

@Preview(showBackground = true, name = "Formulario — Modo Oscuro",
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewFormularioDark() {
    DenunciaTheme(darkTheme = true) {
        FormularioReporteMaltrato(
            nombreUsuario = "María",
            anonimo       = false,
            Atras         = {}
        )
    }
}
