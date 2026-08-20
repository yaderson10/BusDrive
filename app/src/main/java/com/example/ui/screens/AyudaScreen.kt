package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Color Tokens from Tailwind configuration in HTML
private val BgSurface = Color(0xFFF3FCEE)
private val PrimaryGreen = Color(0xFF006E2A)
private val PrimaryContainer = Color(0xFF00C853)
private val OnPrimary = Color(0xFFFFFFFF)
private val SurfaceContainerLowest = Color(0xFFFFFFFF)
private val SurfaceContainerLow = Color(0xFFEDF6E8)
private val SurfaceContainer = Color(0xFFE8F1E3)
private val OnSurface = Color(0xFF161E15)
private val OnSurfaceVariant = Color(0xFF3C4A3C)
private val OutlineVariant = Color(0xFFBBCBB8)
private val WhatsAppGreen = Color(0xFF25D366)

data class FaqItem(
    val id: Int,
    val question: String,
    val answer: String
)

@Composable
fun AyudaScreen(
    onBack: (() -> Unit)? = null,
    onOpenGuiaBoletos: (() -> Unit)? = null
) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    // Default open accordion is index 3 or none, matching the active accordion in HTML mockup
    var expandedId by remember { mutableIntStateOf(4) }

    val faqItems = remember {
        listOf(
            FaqItem(
                id = 1,
                question = "¿Cómo compro un boleto de forma segura?",
                answer = "BusDrive verifica a los vendedores, pero siempre recomendamos revisar las calificaciones del vendedor antes de contactarlo. El pago se acuerda externamente entre tú y el vendedor para mayor flexibilidad."
            ),
            FaqItem(
                id = 2,
                question = "¿Qué pasa si el conductor no llega?",
                answer = "En caso de que el conductor de un Ride no se presente, puedes reportar al usuario desde su perfil. Esto ayuda a mantener la comunidad segura y confiable. Te sugerimos siempre confirmar unas horas antes."
            ),
            FaqItem(
                id = 3,
                question = "¿Cómo publico mi estancia?",
                answer = "Ve a la pestaña \"Comunidad\", selecciona el botón \"+\" y elige \"Publicar Estancia\". Llena los detalles como ubicación, precio y fotos. Una vez publicada, otros usuarios podrán contactarte."
            ),
            FaqItem(
                id = 4,
                question = "¿Los pagos se hacen dentro de la app?",
                answer = "No, la aplicación solo facilita el contacto entre usuarios a través de WhatsApp o llamadas telefónicas. Todos los pagos o acuerdos financieros se realizan fuera de la plataforma directamente entre los usuarios."
            ),
            FaqItem(
                id = 5,
                question = "¿Cómo busco horarios y tarifas de buses?",
                answer = "En la pestaña \"Explorar\" puedes buscar rutas intermunicipales en toda Nicaragua y la Costa Caribe. Podrás ver horarios de salida, terminales de abordaje, precios estimados y frecuencias diarias."
            )
        )
    }

    val filteredFaqs = remember(searchQuery, faqItems) {
        if (searchQuery.isBlank()) {
            faqItems
        } else {
            faqItems.filter {
                it.question.contains(searchQuery, ignoreCase = true) ||
                it.answer.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgSurface)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // TOP APP BAR
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
                    if (onBack != null) {
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
                        Spacer(modifier = Modifier.width(4.dp))
                    }

                    Text(
                        text = "Preguntas Frecuentes",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryGreen
                    )
                }
            }

            // MAIN SCROLLABLE BODY
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 6.dp)
                    .padding(bottom = 96.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // SEARCH BAR
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = {
                        Text(
                            text = "Buscar en preguntas frecuentes...",
                            color = OnSurfaceVariant,
                            fontSize = 14.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar",
                            tint = OnSurfaceVariant
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Limpiar",
                                    tint = OnSurfaceVariant
                                )
                            }
                        }
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SurfaceContainerLowest,
                        unfocusedContainerColor = SurfaceContainerLowest,
                        focusedBorderColor = PrimaryContainer,
                        unfocusedBorderColor = SurfaceContainer,
                        cursorColor = PrimaryGreen,
                        focusedTextColor = OnSurface,
                        unfocusedTextColor = OnSurface
                    )
                )

                // ACCORDION LIST
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    filteredFaqs.forEach { faq ->
                        val isExpanded = expandedId == faq.id
                        FaqAccordionItem(
                            faq = faq,
                            isExpanded = isExpanded,
                            onToggle = {
                                expandedId = if (isExpanded) -1 else faq.id
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // CONTACT SUPPORT CTA CARD
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 1.dp,
                            shape = RoundedCornerShape(16.dp),
                            ambientColor = Color(0x08000000),
                            spotColor = Color(0x08000000)
                        ),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
                    border = BorderStroke(1.dp, SurfaceContainer)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Agent Support Icon Badge
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(PrimaryContainer.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.SupportAgent,
                                contentDescription = "Soporte",
                                tint = PrimaryGreen,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "¿Aún tienes dudas?",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Nuestro equipo de soporte está listo para ayudarte con cualquier problema.",
                            fontSize = 14.sp,
                            color = OnSurfaceVariant,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            lineHeight = 20.sp
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        // WhatsApp Action Button
                        Button(
                            onClick = {
                                val uri = Uri.parse("https://wa.me/50588889999?text=" + Uri.encode("Hola equipo de soporte BusDrive, necesito asistencia con:"))
                                context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = WhatsAppGreen,
                                contentColor = OnPrimary
                            ),
                            contentPadding = PaddingValues(horizontal = 16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Chat,
                                    contentDescription = null,
                                    tint = OnPrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Contactar por WhatsApp",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = OnPrimary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FaqAccordionItem(
    faq: FaqItem,
    isExpanded: Boolean,
    onToggle: () -> Unit
) {
    val animatedBorderColor by animateColorAsState(
        targetValue = if (isExpanded) PrimaryContainer.copy(alpha = 0.5f) else SurfaceContainer,
        label = "faq_border"
    )
    val animatedTitleColor by animateColorAsState(
        targetValue = if (isExpanded) PrimaryGreen else OnSurface,
        label = "faq_title_color"
    )
    val rotationAngle by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        label = "faq_chevron_rotation"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = if (isExpanded) 3.dp else 1.dp,
                shape = RoundedCornerShape(14.dp),
                ambientColor = Color(0x0A000000),
                spotColor = Color(0x0A000000)
            ),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        border = BorderStroke(1.dp, animatedBorderColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onToggle() }
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = faq.question,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = animatedTitleColor,
                    lineHeight = 22.sp,
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                )

                Icon(
                    imageVector = Icons.Default.ExpandMore,
                    contentDescription = if (isExpanded) "Colapsar" else "Expandir",
                    tint = if (isExpanded) PrimaryGreen else OutlineVariant,
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(rotationAngle)
                )
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = faq.answer,
                        fontSize = 14.sp,
                        color = OnSurfaceVariant,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}
