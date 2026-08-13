package id.masteraktivitas.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColors = darkColorScheme(
    primary = Color(0xFF6EA8FF),
    secondary = Color(0xFF8B5CF6),
    tertiary = Color(0xFF34D399),
    background = Color(0xFF0B1220),
    surface = Color(0xFF111B2D),
    surfaceVariant = Color(0xFF182338),
    onBackground = Color(0xFFE5E7EB),
    onSurface = Color(0xFFE5E7EB),
    onSurfaceVariant = Color(0xFF9CA3AF)
)

@Composable
fun MasterTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColors,
        content = content
    )
} 