package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Color Palette matching HTML Tailwind configuration
private val BgSurface = Color(0xFFF3FCEE)
private val PrimaryGreen = Color(0xFF006E2A)
private val PrimaryContainer = Color(0xFF00C853)
private val SurfaceContainerLowest = Color(0xFFFFFFFF)
private val SurfaceContainer = Color(0xFFE8F1E3)
private val SurfaceContainerHigh = Color(0xFFE2EBDD)
private val OnSurface = Color(0xFF161E15)
private val OnSurfaceVariant = Color(0xFF3C4A3C)

@Composable
fun TerminosPrivacidadScreen(
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgSurface)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // STICKY TOP APP BAR
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = BgSurface,
                shadowElevation = 0.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = PrimaryGreen
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "BusDrive",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = PrimaryGreen,
                        letterSpacing = (-0.5).sp
                    )
                }
            }

            // MAIN SCROLLABLE CONTENT
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 12.dp)
                    .padding(bottom = 90.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // HEADER SECTION (Title & Subtitle)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Términos y Privacidad",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = OnSurface,
                        letterSpacing = (-0.5).sp,
                        lineHeight = 36.sp
                    )

                    Text(
                        text = "En BusDrive, nos tomamos muy en serio la seguridad y privacidad de nuestra comunidad. Queremos que te sientas respaldado mientras reservas tus pasajes de bus, compartes viajes o encuentras estadías. Lee detenidamente las reglas de convivencia y cómo protegemos tus datos.",
                        fontSize = 15.sp,
                        color = OnSurfaceVariant,
                        lineHeight = 22.sp
                    )
                }

                // ARTICLES SECTION
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Article 1: Términos de Uso
                    PolicyArticleCard(
                        icon = Icons.Default.Gavel,
                        title = "Términos de Uso",
                        description = "BusDrive es una plataforma diseñada para facilitar el contacto directo entre usuarios para la gestión de pasajes de bus, viajes compartidos y estadías. Nuestro servicio es estrictamente de conexión; no realizamos cobros internos, comisiones por viaje, ni intermediamos en transacciones financieras. El acuerdo de viaje, costos compartidos y logística son responsabilidad exclusiva de las partes involucradas."
                    )

                    // Article 2: Política de Privacidad
                    PolicyArticleCard(
                        icon = Icons.Default.Shield,
                        title = "Política de Privacidad",
                        description = "Para garantizar la confianza en la comunidad, recopilamos datos personales básicos como tu nombre, correo electrónico y número de teléfono. Estos datos son utilizados exclusivamente para verificar tu identidad y permitir la comunicación con otros usuarios de la red. No vendemos ni compartimos tu información con terceros ajenos a la funcionalidad de la aplicación."
                    )

                    // Article 3: Contacto Directo
                    PolicyArticleCard(
                        icon = Icons.Default.Forum,
                        title = "Contacto Directo",
                        description = "Te recordamos que toda comunicación operativa o coordinación de viajes se realiza de forma externa a la aplicación, principalmente a través de WhatsApp o llamadas telefónicas directas, una vez que ambas partes han aceptado conectar. Mantén un trato respetuoso y seguro en todo momento."
                    )
                }
            }
        }
    }
}

@Composable
private fun PolicyArticleCard(
    icon: ImageVector,
    title: String,
    description: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color(0x0A000000),
                spotColor = Color(0x0A000000)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        border = BorderStroke(1.dp, SurfaceContainerHigh.copy(alpha = 0.6f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(SurfaceContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = PrimaryContainer,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Text(
                    text = title,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurface
                )
            }

            Text(
                text = description,
                fontSize = 14.sp,
                color = OnSurfaceVariant,
                lineHeight = 20.sp,
                modifier = Modifier.padding(start = 52.dp)
            )
        }
    }
}
