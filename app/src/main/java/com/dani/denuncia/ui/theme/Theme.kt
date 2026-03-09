package com.dani.denuncia.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// ===================================================================
//  ESQUEMA CLARO  —  Verde principal #0D7C46 sobre blanco/menta
// ===================================================================
private val LightColorScheme = lightColorScheme(
    // Primarios
    primary                = VerdePrincipal,
    onPrimary              = Color.White,
    primaryContainer       = VerdeContainer,
    onPrimaryContainer     = VerdeOscuro,

    // Secundarios (verde medio)
    secondary              = VerdeMedio,
    onSecondary            = Color.White,
    secondaryContainer     = VerdeMuyPalido,
    onSecondaryContainer   = VerdeMuyOscuro,

    // Terciario (verde suave para acentos)
    tertiary               = VerdeClaro,
    onTertiary             = Color.White,
    tertiaryContainer      = VerdePalido,
    onTertiaryContainer    = VerdeOscuro,

    // Superficies
    background             = FondoClaro,
    onBackground           = TextoPrimarioClaro,
    surface                = SuperficieClaro,
    onSurface              = TextoPrimarioClaro,
    surfaceVariant         = SuperficieVariante,
    onSurfaceVariant       = TextoSecundarioClaro,

    // Contorno y sombra
    outline                = GrisMedio,
    outlineVariant         = VerdeContainer,
    scrim                  = Color(0xFF000000),

    // Error
    error                  = RojoError,
    onError                = Color.White,
    errorContainer         = RojoErrorContainer,
    onErrorContainer       = OnRojoErrorContainer,

    // Inverse
    inverseSurface         = SuperficieOscuro,
    inverseOnSurface       = TextoPrimarioOscuro,
    inversePrimary         = VerdeSuave,
)

// ===================================================================
//  ESQUEMA OSCURO  —  Verde claro/suave sobre fondo profundo
// ===================================================================
private val DarkColorScheme = darkColorScheme(
    // Primarios
    primary                = VerdeSuave,
    onPrimary              = VerdeMuyOscuro,
    primaryContainer       = VerdeOscuro,
    onPrimaryContainer     = VerdeMuyPalido,

    // Secundarios
    secondary              = VerdePalido,
    onSecondary            = VerdeMuyOscuro,
    secondaryContainer     = VerdeMedio,
    onSecondaryContainer   = FondoOscuro,

    // Terciario
    tertiary               = VerdeClaro,
    onTertiary             = VerdeMuyOscuro,
    tertiaryContainer      = VerdeOscuro,
    onTertiaryContainer    = VerdePalido,

    // Superficies
    background             = FondoOscuro,
    onBackground           = TextoPrimarioOscuro,
    surface                = SuperficieOscuro,
    onSurface              = TextoPrimarioOscuro,
    surfaceVariant         = SuperficieVarianteOscuro,
    onSurfaceVariant       = TextoSecundarioOscuro,

    // Contorno
    outline                = GrisOscuroContraste,
    outlineVariant         = SuperficieVarianteOscuro,
    scrim                  = Color(0xFF000000),

    // Error
    error                  = Color(0xFFFFB4AB),
    onError                = Color(0xFF690005),
    errorContainer         = Color(0xFF93000A),
    onErrorContainer       = Color(0xFFFFDAD6),

    // Inverse
    inverseSurface         = VerdeMuyPalido,
    inverseOnSurface       = TextoPrimarioClaro,
    inversePrimary         = VerdePrincipal,
)

// ===================================================================
//  TEMA PRINCIPAL
// ===================================================================
@Composable
fun DenunciaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    // Actualiza colores de barra de estado según el tema
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            window?.let {
                it.statusBarColor = if (darkTheme) {
                    FondoOscuro.toArgb()
                } else {
                    VerdePrincipal.toArgb()
                }
                WindowCompat.getInsetsController(it, view)
                    .isAppearanceLightStatusBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = Typography,
        content     = content
    )
}
