package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.MandaditoCourier
import com.example.ui.viewmodel.BusDriveViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

private val BgCanvas = Color(0xFFFFFFFF)
private val PrimaryGreen = Color(0xFF006E2A)
private val PrimaryContainer = Color(0xFF00C853)
private val AccentLime = Color(0xFFA2E000)
private val SurfaceContainer = Color(0xFFF8FCF5)
private val SurfaceVariant = Color(0xFFE8F1E3)
private val OnSurface = Color(0xFF161E15)
private val OnSurfaceVariant = Color(0xFF3C4A3C)
private val OutlineVariant = Color(0xFFBBCBB8)
private val OutlineColor = Color(0xFF6C7B6A)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MandaditosScreen(
    viewModel: BusDriveViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val couriers by viewModel.allCouriers.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()
    val providerProfile by viewModel.currentProviderProfile.collectAsState()
    val showPublishCourierModal by viewModel.showPublishCourierModal.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategoryFilter by remember { mutableStateOf("Todos") }
    var showRequestDialog by remember { mutableStateOf(false) }
    var showRegisterCourierDialog by remember { mutableStateOf(false) }
    var selectedCourierForOrder by remember { mutableStateOf<MandaditoCourier?>(null) }

    val categories = listOf(
        "Todos",
        "Disponibles Ya",
        "Envíos Express",
        "Compras & Farmacia",
        "Comida & Súper",
        "Paquetería"
    )

    val filteredCouriers = remember(couriers, searchQuery, selectedCategoryFilter) {
        couriers.filter { courier ->
            val matchesQuery = searchQuery.isBlank() ||
                    courier.name.contains(searchQuery, ignoreCase = true) ||
                    courier.zoneCoverage.contains(searchQuery, ignoreCase = true) ||
                    courier.vehicleType.contains(searchQuery, ignoreCase = true) ||
                    courier.services.any { it.contains(searchQuery, ignoreCase = true) }

            val matchesCategory = when (selectedCategoryFilter) {
                "Disponibles Ya" -> courier.isAvailable
                "Envíos Express" -> courier.services.any { it.contains("Express", ignoreCase = true) }
                "Compras & Farmacia" -> courier.services.any { it.contains("Farmacia", ignoreCase = true) || it.contains("Súper", ignoreCase = true) }
                "Comida & Súper" -> courier.services.any { it.contains("Comida", ignoreCase = true) || it.contains("Súper", ignoreCase = true) }
                "Paquetería" -> courier.services.any { it.contains("Paquet", ignoreCase = true) || it.contains("Documento", ignoreCase = true) }
                else -> true
            }

            matchesQuery && matchesCategory
        }
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = BgCanvas
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            contentPadding = PaddingValues(bottom = 96.dp)
        ) {
            // 1. TOP HEADER & BRAND BANNER
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(AccentLime.copy(alpha = 0.3f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.TwoWheeler,
                                contentDescription = null,
                                tint = PrimaryGreen,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Text(
                            text = "Mandaditos",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            color = OnSurface
                        )
                    }
                    Text(
                        text = "Envíos, compras y delivery en moto directo",
                        fontSize = 13.sp,
                        color = OnSurfaceVariant,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            // 2. HERO HIGHLIGHT BANNER: SOLICITAR MANDADITO EXPRESS
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .shadow(4.dp, RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0F2E1B))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFF0A2414),
                                        Color(0xFF0F3D20),
                                        Color(0xFF1B5E20)
                                    )
                                )
                            )
                            .padding(18.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = AccentLime,
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(
                                                imageVector = Icons.Default.DeliveryDining,
                                                contentDescription = null,
                                                tint = Color(0xFF111827),
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                    Text(
                                        text = "¿Necesitas un mandadito ya?",
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }

                            Text(
                                text = "Pide medicamentos, comida, pagos de servicios o entrega de paquetes a domicilio con repartidores confiables de tu zona.",
                                fontSize = 13.sp,
                                color = Color(0xFFD0E8D7),
                                lineHeight = 18.sp
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Button(
                                    onClick = {
                                        selectedCourierForOrder = null
                                        showRequestDialog = true
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = AccentLime,
                                        contentColor = Color(0xFF111827)
                                    ),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.NearMe,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Cotizar Mandado",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 3. SEARCH BAR
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(
                                text = "Buscar por zona, repartidor o servicio...",
                                fontSize = 14.sp,
                                color = OutlineColor
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = OutlineColor
                            )
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Limpiar",
                                        tint = OutlineColor
                                    )
                                }
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = SurfaceContainer,
                            unfocusedContainerColor = SurfaceContainer,
                            focusedBorderColor = PrimaryGreen,
                            unfocusedBorderColor = OutlineVariant
                        )
                    )
                }
            }

            // 4. CATEGORY FILTER CHIPS
            item {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { cat ->
                        val isSelected = selectedCategoryFilter == cat
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedCategoryFilter = cat },
                            label = {
                                Text(
                                    text = cat,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                            },
                            shape = RoundedCornerShape(20.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = AccentLime,
                                selectedLabelColor = Color(0xFF111827),
                                containerColor = SurfaceContainer,
                                labelColor = OnSurfaceVariant
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                borderColor = if (isSelected) PrimaryGreen else OutlineVariant
                            )
                        )
                    }
                }
            }

            // 5. SECTION TITLE & COUNTER
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Repartidores y Motomandados (${filteredCouriers.size})",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurface
                    )
                    Text(
                        text = "Trato directo sin comisión",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = PrimaryGreen
                    )
                }
            }

            // 6. EMPTY STATE OR COURIER LIST
            if (filteredCouriers.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 40.dp, horizontal = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.TwoWheeler,
                            contentDescription = null,
                            tint = OutlineColor,
                            modifier = Modifier.size(56.dp)
                        )
                        Text(
                            text = "No se encontraron repartidores",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface
                        )
                        Text(
                            text = "Intenta buscando por otra zona o cambia los filtros de categoría.",
                            fontSize = 13.sp,
                            color = OnSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                items(filteredCouriers, key = { it.id }) { courier ->
                    CourierCard(
                        courier = courier,
                        onOrderClick = {
                            selectedCourierForOrder = courier
                            showRequestDialog = true
                        },
                        onWhatsAppDirect = {
                            openWhatsAppDirect(
                                context = context,
                                phone = courier.phone,
                                message = "¡Hola ${courier.name}! Te contacto desde la app BusDrive. Necesito un mandadito express en ${courier.zoneCoverage}. ¿Estás disponible?"
                            )
                        },
                        onCallDirect = {
                            val intent = Intent(Intent.ACTION_DIAL).apply {
                                data = Uri.parse("tel:${courier.phone.replace(" ", "").replace("-", "")}")
                            }
                            try {
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                Toast.makeText(context, "No se pudo iniciar la llamada", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }
            }
        }
    }

    // DIALOG: SOLICITAR MANDADITO EXPRESS
    if (showRequestDialog) {
        RequestMandaditoDialog(
            targetCourier = selectedCourierForOrder,
            availableCouriers = couriers,
            onDismiss = { showRequestDialog = false },
            onSubmitOrder = { courier, taskType, pickup, dropoff, notes ->
                showRequestDialog = false
                val encodedMessage = buildString {
                    append("🛵 *SOLICITUD DE MANDADITO - BUSDRIVE*\n")
                    append("👤 *Cliente:* ${currentUser?.name ?: "Usuario BusDrive"}\n")
                    append("📦 *Tipo de encargo:* $taskType\n")
                    append("📍 *Punto de recogida:* $pickup\n")
                    append("🏁 *Punto de entrega:* $dropoff\n")
                    if (notes.isNotBlank()) {
                        append("📝 *Instrucciones:* $notes\n")
                    }
                    append("\n¿Me puedes confirmar disponibilidad y costo?")
                }
                openWhatsAppDirect(
                    context = context,
                    phone = courier.phone,
                    message = encodedMessage
                )
            }
        )
    }

    // DIALOG: PUBLICAR MANDADITO (Misma experiencia que boletos, rides y estancia)
    if (showRegisterCourierDialog || showPublishCourierModal) {
        PublicarMandaditoDialog(
            profile = providerProfile,
            currentUser = currentUser,
            onDismiss = {
                showRegisterCourierDialog = false
                viewModel.showPublishCourierModal.value = false
            },
            onPublish = { newCourier ->
                viewModel.registerMandaditoCourier(newCourier)
                showRegisterCourierDialog = false
                viewModel.showPublishCourierModal.value = false
                Toast.makeText(context, "¡Mandadito publicado exitosamente!", Toast.LENGTH_LONG).show()
            }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CourierCard(
    courier: MandaditoCourier,
    onOrderClick: () -> Unit,
    onWhatsAppDirect: () -> Unit,
    onCallDirect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color(0x0A000000),
                spotColor = Color(0x0A000000)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, OutlineVariant.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Row 1: Photo, Name, Vehicle & Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Courier Photo with status badge
                Box(modifier = Modifier.size(54.dp)) {
                    Surface(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape),
                        shape = CircleShape,
                        color = SurfaceContainer
                    ) {
                        AsyncImage(
                            model = courier.photoUrl,
                            contentDescription = courier.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    // Online indicator dot
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(14.dp)
                            .clip(CircleShape)
                            .background(if (courier.isAvailable) Color(0xFF00C853) else Color(0xFFFF9800))
                            .border(2.dp, Color.White, CircleShape)
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = courier.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        if (courier.isVerified) {
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = "Verificado",
                                tint = PrimaryGreen,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.TwoWheeler,
                            contentDescription = null,
                            tint = OutlineColor,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "${courier.vehicleType} • ${courier.vehiclePlate}",
                            fontSize = 12.sp,
                            color = OnSurfaceVariant,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    // Rating and orders count
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFB300),
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "${courier.rating}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface
                        )
                        Text(
                            text = "(${courier.completedDeliveries} entregas)",
                            fontSize = 11.sp,
                            color = OutlineColor
                        )
                    }
                }

                // Direct Agreement & Availability Status
                Column(horizontalAlignment = Alignment.End) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFE8F5E9),
                        border = BorderStroke(1.dp, Color(0xFF81C784))
                    ) {
                        Text(
                            text = "A convenir",
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1B5E20),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (courier.isAvailable) "🟢 Disponible" else "🟠 En ruta",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (courier.isAvailable) Color(0xFF2E7D32) else Color(0xFFE65100)
                    )
                }
            }

            // Row 2: Zone coverage
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(SurfaceContainer)
                    .padding(horizontal = 8.dp, vertical = 6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = PrimaryGreen,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = courier.zoneCoverage,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = OnSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Row 3: Service chips
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                courier.services.take(4).forEach { srv ->
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0xFFF1F5F9),
                        border = BorderStroke(1.dp, Color(0xFFE2E8F0))
                    ) {
                        Text(
                            text = srv,
                            fontSize = 11.sp,
                            color = Color(0xFF475569),
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            // Description
            if (courier.description.isNotBlank()) {
                Text(
                    text = courier.description,
                    fontSize = 12.sp,
                    color = OnSurfaceVariant,
                    lineHeight = 16.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Row 4: Direct Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Call button
                OutlinedButton(
                    onClick = onCallDirect,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, OutlineVariant),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = OnSurface),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                    modifier = Modifier.height(38.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "Llamar",
                        modifier = Modifier.size(16.dp)
                    )
                }

                // WhatsApp Mandadito CTA
                Button(
                    onClick = onWhatsAppDirect,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF25D366),
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(38.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DeliveryDining,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Pedir por WhatsApp",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Form order dialog
                Button(
                    onClick = onOrderClick,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF111827),
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                    modifier = Modifier.height(38.dp)
                ) {
                    Text(
                        text = "Detalle",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// DIALOG TO REQUEST A QUICK DELIVERY
@Composable
private fun RequestMandaditoDialog(
    targetCourier: MandaditoCourier?,
    availableCouriers: List<MandaditoCourier>,
    onDismiss: () -> Unit,
    onSubmitOrder: (courier: MandaditoCourier, taskType: String, pickup: String, dropoff: String, notes: String) -> Unit
) {
    var selectedCourier by remember {
        mutableStateOf(targetCourier ?: availableCouriers.firstOrNull() ?: MandaditoCourier(name = "Repartidor", phone = "505 8888 8888"))
    }
    var taskType by remember { mutableStateOf("Compra en Farmacia / Súper") }
    var pickupLocation by remember { mutableStateOf("") }
    var dropoffLocation by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    val taskTypes = listOf(
        "Compra en Farmacia / Súper",
        "Comida de Restaurante",
        "Entrega de Paquete / Documentos",
        "Mandado / Trámite Express",
        "Otro"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.TwoWheeler,
                    contentDescription = null,
                    tint = PrimaryGreen
                )
                Text(
                    text = "Solicitar Mandadito Express",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurface
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Courier Selection pill
                Text(
                    text = "Repartidor seleccionado:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurfaceVariant
                )
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = SurfaceContainer,
                    border = BorderStroke(1.dp, OutlineVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        AsyncImage(
                            model = selectedCourier.photoUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = selectedCourier.name,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnSurface
                            )
                            Text(
                                text = "${selectedCourier.vehicleType} • Trato directo",
                                fontSize = 12.sp,
                                color = OnSurfaceVariant
                            )
                        }
                    }
                }

                // Task Type
                Text(
                    text = "¿Qué necesitas?",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurfaceVariant
                )
                OutlinedTextField(
                    value = taskType,
                    onValueChange = { taskType = it },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                // Pickup
                Text(
                    text = "Punto de recogida (Dónde se compra o retira):",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurfaceVariant
                )
                OutlinedTextField(
                    value = pickupLocation,
                    onValueChange = { pickupLocation = it },
                    placeholder = { Text("Ej. Farmacia Saba, Mercado, Restaurante...") },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                // Dropoff
                Text(
                    text = "Punto de entrega (Destino):",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurfaceVariant
                )
                OutlinedTextField(
                    value = dropoffLocation,
                    onValueChange = { dropoffLocation = it },
                    placeholder = { Text("Ej. Mi casa, Barrio Central, Oficina...") },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                // Additional details
                Text(
                    text = "Detalles / Monto a pagar / Notas:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurfaceVariant
                )
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    placeholder = { Text("Ej. Llevar factura, pago con billete de C$ 500...") },
                    maxLines = 2,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (pickupLocation.isNotBlank() && dropoffLocation.isNotBlank()) {
                        onSubmitOrder(selectedCourier, taskType, pickupLocation, dropoffLocation, notes)
                    }
                },
                enabled = pickupLocation.isNotBlank() && dropoffLocation.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF25D366),
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Default.DeliveryDining,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Enviar a WhatsApp")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar", color = OutlineColor)
            }
        }
    )
}

private fun openWhatsAppDirect(context: Context, phone: String, message: String) {
    val cleanPhone = phone.replace("+", "").replace(" ", "").replace("-", "")
    val url = "https://api.whatsapp.com/send?phone=$cleanPhone&text=${URLEncoder.encode(message, StandardCharsets.UTF_8.toString())}"
    try {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "No se pudo abrir WhatsApp", Toast.LENGTH_SHORT).show()
    }
}
