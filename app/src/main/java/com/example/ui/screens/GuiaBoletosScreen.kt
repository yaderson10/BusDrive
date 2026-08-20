package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// inDrive Lime Green Color Palette
private val BgSurface = Color(0xFFF8FAFC)
private val LimeBrand = Color(0xFFA2E000)
private val LimeBrandDark = Color(0xFF527A00)
private val OnPrimary = Color(0xFF111827)
private val SurfaceContainerLowest = Color(0xFFFFFFFF)
private val SurfaceContainerLow = Color(0xFFF1F5F9)
private val SurfaceContainer = Color(0xFFF1F5F9)
private val SurfaceVariant = Color(0xFFE2E8F0)
private val OnSurface = Color(0xFF111827)
private val OnSurfaceVariant = Color(0xFF475569)
private val SuccessGreen = Color(0xFF16A34A)
private val ErrorRed = Color(0xFFEF4444)
private val TertiaryBlue = Color(0xFF0284C7)
private val TertiaryContainerBg = Color(0xFFE0F2FE)
private val TertiaryContainerBorder = Color(0xFFBAE6FD)

data class StepGuideItem(
    val stepNumber: Int,
    val title: String,
    val description: String,
    val icon: ImageVector
)

data class FaqGuideItem(
    val question: String,
    val answer: String
)

@Composable
fun GuiaBoletosScreen(
    onBack: () -> Unit
) {
    val steps = remember {
        listOf(
            StepGuideItem(
                stepNumber = 1,
                title = "Busca tu destino",
                description = "Utiliza el buscador en 'Explorar' para encontrar viajes disponibles hacia tu destino deseado en las fechas que necesitas.",
                icon = Icons.Default.Search
            ),
            StepGuideItem(
                stepNumber = 2,
                title = "Selecciona un vendedor",
                description = "Revisa las opciones. Fíjate en la calificación del vendedor y los detalles del boleto (asiento, hora, tipo de servicio).",
                icon = Icons.Default.PersonSearch
            ),
            StepGuideItem(
                stepNumber = 3,
                title = "Contacta directamente",
                description = "Usa los botones de contacto (WhatsApp o Llamada) para confirmar disponibilidad y acordar detalles con el vendedor.",
                icon = Icons.Default.Chat
            ),
            StepGuideItem(
                stepNumber = 4,
                title = "Coordina la entrega",
                description = "Acuerden un lugar seguro para realizar el intercambio físico o digital del boleto y el pago correspondiente.",
                icon = Icons.Default.Handshake
            )
        )
    }

    val faqs = remember {
        listOf(
            FaqGuideItem(
                question = "¿Qué pasa si el boleto es falso?",
                answer = "BusDrive actúa como un tablón de anuncios y no se hace responsable por transacciones entre usuarios. Te recomendamos encarecidamente verificar la identidad del vendedor y la validez del boleto antes de pagar."
            ),
            FaqGuideItem(
                question = "¿Puedo cambiar el nombre en un boleto digital?",
                answer = "Depende de la compañía de autobuses. Algunas permiten cambios de nombre con un costo adicional, otras requieren transferencias oficiales. Consulta las políticas de la línea de autobuses emisora."
            ),
            FaqGuideItem(
                question = "¿Cómo publico un boleto que no usaré?",
                answer = "Toca el botón (+) flotante en cualquier pantalla o ve a 'Comunidad' → 'Publicar Boleto'. Ingresa origen, destino, fecha, hora, precio y tu número de contacto."
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgSurface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
        ) {
            // TOP APP BAR
            Surface(
                color = BgSurface,
                modifier = Modifier.fillMaxWidth()
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
                            tint = OnSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = "Guía de Boletos",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = LimeBrandDark
                    )
                }
            }

            // MAIN SCROLLABLE CONTENT
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 8.dp)
                    .padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 40.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // ==========================================
                // 1. HERO SECTION / INTRO
                // ==========================================
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
                    border = BorderStroke(1.dp, SurfaceVariant)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(LimeBrand.copy(alpha = 0.35f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ConfirmationNumber,
                                contentDescription = null,
                                tint = OnPrimary,
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Cómo funciona",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Aprende a encontrar, contactar y asegurar tus boletos de forma segura a través de nuestra comunidad.",
                            fontSize = 14.sp,
                            color = OnSurfaceVariant,
                            textAlign = TextAlign.Center,
                            lineHeight = 20.sp
                        )
                    }
                }

                // ==========================================
                // 2. PASO A PASO (TIMELINE GUIDE)
                // ==========================================
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Paso a Paso",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurface
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        steps.forEachIndexed { index, step ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(IntrinsicSize.Min),
                                horizontalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                // Step timeline badge & line
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.width(28.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(26.dp)
                                            .clip(CircleShape)
                                            .background(LimeBrand),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = step.stepNumber.toString(),
                                            color = OnPrimary,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    }

                                    if (index < steps.lastIndex) {
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Box(
                                            modifier = Modifier
                                                .width(2.dp)
                                                .fillMaxHeight()
                                                .background(SurfaceVariant)
                                        )
                                    }
                                }

                                // Step content card
                                Card(
                                    modifier = Modifier
                                        .weight(1f)
                                        .shadow(
                                            elevation = 2.dp,
                                            shape = RoundedCornerShape(12.dp),
                                            ambientColor = Color(0x0A000000),
                                            spotColor = Color(0x0A000000)
                                        ),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                                    border = BorderStroke(1.dp, SurfaceVariant)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        verticalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Icon(
                                                imageVector = step.icon,
                                                contentDescription = null,
                                                tint = LimeBrandDark,
                                                modifier = Modifier.size(18.dp)
                                            )
                                            Text(
                                                text = step.title,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = OnSurface
                                            )
                                        }

                                        Text(
                                            text = step.description,
                                            fontSize = 13.sp,
                                            color = OnSurfaceVariant,
                                            lineHeight = 19.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // ==========================================
                // 3. SAFETY TIPS (TIPS DE SEGURIDAD)
                // ==========================================
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = TertiaryContainerBg.copy(alpha = 0.5f)),
                    border = BorderStroke(1.dp, TertiaryContainerBorder)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Security,
                                contentDescription = null,
                                tint = TertiaryBlue,
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = "Tips de Seguridad",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = TertiaryBlue
                            )
                        }

                        // Tip 1
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = SuccessGreen,
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(top = 2.dp)
                            )
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = OnSurface)) {
                                        append("Verifica la autenticidad: ")
                                    }
                                    withStyle(SpanStyle(color = OnSurface)) {
                                        append("Revisa siempre que los boletos sean originales antes de realizar cualquier pago.")
                                    }
                                },
                                fontSize = 13.sp,
                                lineHeight = 19.sp
                            )
                        }

                        // Tip 2
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = SuccessGreen,
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(top = 2.dp)
                            )
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = OnSurface)) {
                                        append("Lugares públicos: ")
                                    }
                                    withStyle(SpanStyle(color = OnSurface)) {
                                        append("Si el intercambio es físico, hazlo en lugares concurridos y seguros (ej. la terminal de autobuses).")
                                    }
                                },
                                fontSize = 13.sp,
                                lineHeight = 19.sp
                            )
                        }

                        // Tip 3
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = Icons.Default.Cancel,
                                contentDescription = null,
                                tint = ErrorRed,
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(top = 2.dp)
                            )
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = OnSurface)) {
                                        append("Sin pagos internos: ")
                                    }
                                    withStyle(SpanStyle(color = OnSurface)) {
                                        append("BusDrive no procesa pagos. Desconfía de enlaces externos de cobro enviados por usuarios.")
                                    }
                                },
                                fontSize = 13.sp,
                                lineHeight = 19.sp
                            )
                        }
                    }
                }

                // ==========================================
                // 4. PREGUNTAS FRECUENTES (FAQ ACCORDION)
                // ==========================================
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Preguntas Frecuentes",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurface
                    )

                    faqs.forEach { faq ->
                        FaqAccordionItem(
                            question = faq.question,
                            answer = faq.answer
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FaqAccordionItem(
    question: String,
    answer: String
) {
    var expanded by remember { mutableStateOf(false) }
    val arrowRotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "arrow_rotation"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(12.dp),
                ambientColor = Color(0x0A000000),
                spotColor = Color(0x0A000000)
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        border = BorderStroke(1.dp, SurfaceVariant)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = question,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurface,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Default.ExpandMore,
                    contentDescription = if (expanded) "Colapsar" else "Expandir",
                    tint = OnSurfaceVariant,
                    modifier = Modifier
                        .size(22.dp)
                        .rotate(arrowRotation)
                )
            }

            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    HorizontalDivider(
                        color = SurfaceVariant.copy(alpha = 0.6f),
                        thickness = 0.8.dp
                    )
                    Text(
                        text = answer,
                        fontSize = 13.sp,
                        color = OnSurfaceVariant,
                        lineHeight = 20.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
