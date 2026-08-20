package com.example.ui.components

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.LocalPolice
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.data.model.Ride
import com.example.ui.theme.InkBlack
import com.example.ui.theme.LimeBrand
import com.example.ui.theme.LimeBrandDark
import com.example.ui.theme.LimeBrandGreen
import com.example.ui.theme.LineBorder
import com.example.ui.theme.MutedGray
import com.example.ui.viewmodel.ReviewTarget

// ==========================================
// 1. MODAL DE CALIFICACIÓN Y RESEÑAS
// ==========================================

@Composable
fun ReviewModalDialog(
    target: ReviewTarget,
    onDismiss: () -> Unit,
    onSubmit: (rating: Float, comment: String, tags: List<String>) -> Unit
) {
    var rating by remember { mutableFloatStateOf(5.0f) }
    var comment by remember { mutableStateOf("") }
    val selectedTags = remember { mutableStateListOf<String>() }

    val availableTags = remember(target.targetType) {
        when (target.targetType) {
            "STAY" -> listOf(
                "⭐ Excelente anfitrión",
                "🧼 Muy limpio y ordenado",
                "📍 Gran ubicación",
                "📶 Wi-Fi rápido",
                "❄️ Clima / A/C excelente",
                "🤝 Hospitalidad 10/10"
            )
            "BOLETO", "TICKET" -> listOf(
                "⭐ Vendedor confiable",
                "⚡ Entrega rápida",
                "💬 Excelente comunicación",
                "🎟️ Boleto legítimo",
                "🤝 100% Recomendado"
            )
            else -> listOf(
                "⭐ Muy puntual",
                "🛡️ Manejo seguro",
                "🧼 Vehículo impecable",
                "💬 Buena comunicación",
                "🎶 Buen ambiente",
                "🤝 100% Recomendado"
            )
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .clip(RoundedCornerShape(24.dp)),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header with close icon
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "⭐ Calificar Experiencia",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = InkBlack
                    )
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar",
                            tint = MutedGray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Target profile banner
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF7F9FF), RoundedCornerShape(16.dp))
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (target.photoUrl.isNotBlank()) {
                        AsyncImage(
                            model = target.photoUrl,
                            contentDescription = target.title,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF006D44)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = target.title.take(1).uppercase(),
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = target.title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = InkBlack
                        )
                        Text(
                            text = target.subtitle,
                            fontSize = 13.sp,
                            color = MutedGray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Interactive Star Rating Selector
                Text(
                    text = "¿Cómo fue tu experiencia?",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = InkBlack
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    (1..5).forEach { starIndex ->
                        val isSelected = starIndex <= rating
                        Icon(
                            imageVector = if (isSelected) Icons.Filled.Star else Icons.Filled.StarBorder,
                            contentDescription = "Estrella $starIndex",
                            tint = if (isSelected) Color(0xFFFFB300) else Color(0xFFD1D5DB),
                            modifier = Modifier
                                .size(36.dp)
                                .clickable { rating = starIndex.toFloat() }
                        )
                    }
                }

                Text(
                    text = when (rating.toInt()) {
                        5 -> "¡Excelente servicio! 🎉"
                        4 -> "Muy buen viaje 👍"
                        3 -> "Estuvo bien 🙂"
                        2 -> "Pudo ser mejor 😐"
                        else -> "Mala experiencia 🙁"
                    },
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF006D44),
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Quick praise chips
                Text(
                    text = "Aspectos destacados (opcional):",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = InkBlack,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    availableTags.chunked(2).forEach { rowTags ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            rowTags.forEach { tag ->
                                val isTagSelected = selectedTags.contains(tag)
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(
                                            if (isTagSelected) Color(0xFFE8F5E9) else Color(0xFFF3F4F6)
                                        )
                                        .border(
                                            1.dp,
                                            if (isTagSelected) Color(0xFF006D44) else Color(0xFFE5E7EB),
                                            RoundedCornerShape(20.dp)
                                        )
                                        .clickable {
                                            if (isTagSelected) selectedTags.remove(tag)
                                            else selectedTags.add(tag)
                                        }
                                        .padding(horizontal = 10.dp, vertical = 6.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = tag,
                                        fontSize = 12.sp,
                                        fontWeight = if (isTagSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isTagSelected) Color(0xFF006D44) else Color(0xFF374151),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                            if (rowTags.size == 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Text comment field
                OutlinedTextField(
                    value = comment,
                    onValueChange = { comment = it },
                    label = { Text("Escribe una reseña o comentario...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF006D44),
                        unfocusedBorderColor = Color(0xFFD1D5DB)
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Action Buttons
                Button(
                    onClick = {
                        onSubmit(rating, comment.trim(), selectedTags.toList())
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF006D44),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Publicar Calificación",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}

// ==========================================
// 2. MODAL DE KIT DE SEGURIDAD Y COMPARTIR VIAJE
// ==========================================

@Composable
fun SafetyKitModalDialog(
    ride: Ride,
    onDismiss: () -> Unit,
    onShareTrip: () -> Unit,
    onSosClick: () -> Unit,
    onCallEmergency: (String) -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .clip(RoundedCornerShape(24.dp)),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE8F5E9)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Security,
                                contentDescription = null,
                                tint = Color(0xFF006D44),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Text(
                            text = "Kit de Seguridad",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = InkBlack
                        )
                    }
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar",
                            tint = MutedGray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Trip Card Info
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F9FF)),
                    border = BorderStroke(1.dp, Color(0xFFE5E7EB))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Viaje Activo",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF006D44)
                            )
                            Text(
                                text = "${ride.fecha} · ${ride.hora}",
                                fontSize = 12.sp,
                                color = MutedGray
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "${ride.origen} ➔ ${ride.destino}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = InkBlack
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Conductor: ${ride.driverName} (${ride.trustLevel})",
                            fontSize = 13.sp,
                            color = Color(0xFF374151)
                        )

                        Text(
                            text = "Vehículo: ${ride.vehiculo} · Color: ${ride.color.ifBlank { "No especificado" }}",
                            fontSize = 13.sp,
                            color = Color(0xFF374151)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 1. SHARE TRIP IN REAL TIME BUTTON
                Button(
                    onClick = onShareTrip,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF25D366),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Compartir Trayecto con Familiares",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 2. SOS EMERGENCY BUTTON
                Button(
                    onClick = onSosClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFDC2626),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "🚨 Botón de Auxilio / SOS",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Líneas de Emergencia Nacional:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = InkBlack
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Emergency Numbers Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    EmergencyQuickCard(
                        title = "Policía",
                        number = "118",
                        icon = Icons.Default.LocalPolice,
                        color = Color(0xFF1E3A8A),
                        onClick = { onCallEmergency("118") },
                        modifier = Modifier.weight(1f)
                    )
                    EmergencyQuickCard(
                        title = "Cruz Blanca",
                        number = "128",
                        icon = Icons.Default.MedicalServices,
                        color = Color(0xFFDC2626),
                        onClick = { onCallEmergency("128") },
                        modifier = Modifier.weight(1f)
                    )
                    EmergencyQuickCard(
                        title = "Bomberos",
                        number = "115",
                        icon = Icons.Default.Phone,
                        color = Color(0xFFEA580C),
                        onClick = { onCallEmergency("115") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Safety recommendations
                Text(
                    text = "Consejos de seguridad:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4B5563)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "• Verifica que el vehículo y el conductor coincidan con el perfil.\n• Acuerda puntos de encuentro públicos e iluminados.\n• Mantén a tu contacto de confianza informado de tus paradas.",
                    fontSize = 12.sp,
                    color = MutedGray,
                    lineHeight = 17.sp
                )
            }
        }
    }
}

@Composable
fun EmergencyQuickCard(
    title: String,
    number: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB)),
        border = BorderStroke(1.dp, Color(0xFFE5E7EB))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = color,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = title,
                fontSize = 11.sp,
                color = InkBlack,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = number,
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold,
                color = color
            )
        }
    }
}

// ==========================================
// 3. DIÁLOGO SOS DE EMERGENCIA INMEDIATA
// ==========================================

@Composable
fun SosEmergencyDialog(
    onDismiss: () -> Unit,
    onCallEmergency: (String) -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .clip(RoundedCornerShape(24.dp)),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFEE2E2)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Alerta",
                        tint = Color(0xFFDC2626),
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Llamada de Auxilio / SOS",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF991B1B)
                )

                Text(
                    text = "Selecciona el servicio de emergencia al que deseas enlazar de inmediato:",
                    fontSize = 13.sp,
                    color = MutedGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Police 118
                Button(
                    onClick = { onCallEmergency("118") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1E3A8A),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Llamar a Policía Nacional (118)", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Cruz Blanca 128
                Button(
                    onClick = { onCallEmergency("128") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFDC2626),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Llamar a Cruz Blanca (128)", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Bomberos 115
                Button(
                    onClick = { onCallEmergency("115") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEA580C),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Llamar a Bomberos (115)", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Cancelar / Regresar", color = InkBlack)
                }
            }
        }
    }
}

// ==========================================
// 4. CHIPS DE FILTRO RÁPIDO DE RUTAS POPULARES
// ==========================================

data class PopularRoute(
    val origen: String,
    val destino: String,
    val label: String
)

val POPULAR_NICARAGUA_ROUTES = listOf(
    PopularRoute("Managua", "Puerto Cabezas", "Managua ↔ Bilwi"),
    PopularRoute("Managua", "Siuna", "Managua ↔ Siuna"),
    PopularRoute("Managua", "Matagalpa", "Managua ↔ Matagalpa"),
    PopularRoute("Managua", "Bluefields", "Managua ↔ Bluefields"),
    PopularRoute("Siuna", "Rosita", "Siuna ↔ Rosita"),
    PopularRoute("Estelí", "Managua", "Estelí ↔ Managua")
)

@Composable
fun QuickRouteFilterChips(
    selectedOrigen: String,
    selectedDestino: String,
    onSelectRoute: (origen: String, destino: String) -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val isAllSelected = selectedOrigen.isBlank() && selectedDestino.isBlank()
        item {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isAllSelected) Color(0xFF006D44) else Color(0xFFF3F4F6))
                    .border(
                        1.dp,
                        if (isAllSelected) Color(0xFF006D44) else Color(0xFFE5E7EB),
                        RoundedCornerShape(20.dp)
                    )
                    .clickable { onClear() }
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Todas las rutas",
                    fontSize = 13.sp,
                    fontWeight = if (isAllSelected) FontWeight.Bold else FontWeight.Medium,
                    color = if (isAllSelected) Color.White else Color(0xFF374151)
                )
            }
        }

        items(POPULAR_NICARAGUA_ROUTES) { route ->
            val isSelected = (selectedOrigen.equals(route.origen, ignoreCase = true) &&
                    selectedDestino.equals(route.destino, ignoreCase = true)) ||
                    (selectedOrigen.equals(route.destino, ignoreCase = true) &&
                    selectedDestino.equals(route.origen, ignoreCase = true))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isSelected) Color(0xFF006D44) else Color(0xFFF3F4F6))
                    .border(
                        1.dp,
                        if (isSelected) Color(0xFF006D44) else Color(0xFFE5E7EB),
                        RoundedCornerShape(20.dp)
                    )
                    .clickable { onSelectRoute(route.origen, route.destino) }
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = route.label,
                    fontSize = 13.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = if (isSelected) Color.White else Color(0xFF374151)
                )
            }
        }
    }
}
