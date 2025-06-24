import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.tpc.nudj.ui.theme.Typography

private val LightColorScheme = lightColorScheme(
    primary = Color( 0xFF3F1872),
    onPrimary = Color(0xFFFFF1E6),
    background = Color(0xFFFFFEFF),
    onBackground = Color(0xFF442C35),
    surface = Color(0xFFFFF1E6),
    onSurface = Color(0xFF3F1872 )
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFF5E00 ),
    onPrimary = Color.White,
    background = Color(0xFF3F1872),
    onBackground = Color.White,
    surface = Color(0xFF6929BE),
    onSurface =  Color( 0xFFFFFEFF)

)


@Composable
fun NudjTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }



    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
