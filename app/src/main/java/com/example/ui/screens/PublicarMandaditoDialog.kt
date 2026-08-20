package com.example.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.LocalPharmacy
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.MandaditoCourier
import com.example.data.model.ProviderProfile
import com.example.data.model.UserProfile

// Exact color palette matching the community theme
private val PrimaryGreen = Color(0xFF006E2A)
private val PrimaryContainerGreen = Color(0xFF8CF695)
private val OnPrimaryContainerGreen = Color(0xFF002107)
private val LimeBrand = Color(0xFFCCFF00)
private val BackgroundSurface = Color(0xFFF7F9FF)
private val LineBorder = Color(0xFFE5E7EB)
private val SurfaceContainerLow = Color(0xFFEEF4FD)
private val OutlineVariantColor = Color(0xFFC4C9AE)
private val OutlineColor = Color(0xFF747A62)
private val OnSurfaceColor = Color(0xFF161C23)
private val OnSurfaceVariantColor = Color(0xFF5D5E61)
private val ActiveChipBg = Color(0xFFE8F5E9)

private data class MandaditoServiceOption(
    val id: String,
    val name: String,
    val icon: ImageVector
)

private fun Modifier.dashedBorder(
    width: Dp,
    color: Color,
    cornerRadius: Dp = 12.dp,
    dashLength: Dp = 8.dp,
    gapLength: Dp = 4.dp
) = this.drawBehind {
    val stroke = Stroke(
        width = width.toPx(),
        pathEffect = PathEffect.dashPathEffect(
            floatArrayOf(dashLength.toPx(), gapLength.toPx()),
            0f
        )
    )
    drawRoundRect(
        color = color,
        cornerRadius = CornerRadius(cornerRadius.toPx()),
        style = stroke
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PublicarMandaditoDialog(
    profile: ProviderProfile,
    currentUser: UserProfile? = null,
    allLocations: List<String> = listOf("Managua", "Siuna", "Puerto Cabezas (Bilwi)", "Bluefields", "Matagalpa", "Juigalpa", "Estelí", "Waslala", "Rosita", "Bonanza", "Waspam", "Chinandega", "León", "Rivas"),
    onDismiss: () -> Unit,
    onPublish: (MandaditoCourier) -> Unit
) {
    val context = LocalContext.current

    val courierName = profile.fullName.ifBlank { currentUser?.name ?: "Repartidor" }
    val defaultPhone = profile.phone.ifBlank { currentUser?.phone ?: "" }
    val userPhotoUrl = profile.profilePhotoUrl.ifBlank { currentUser?.photoUrl ?: "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=400" }
    val userInitial = courierName.firstOrNull()?.uppercase() ?: "R"

    // Form states
    var name by remember { mutableStateOf(courierName) }
    var phone by remember { mutableStateOf(defaultPhone) }
    var photoUrl by remember { mutableStateOf(userPhotoUrl) }
    var selectedVehicleType by remember { mutableStateOf("Motocicleta 150cc") }
    var customVehicleModel by remember { mutableStateOf("Yamaha FZ 150cc") }
    var vehiclePlate by remember { mutableStateOf("M-") }
    var selectedCity by remember { mutableStateOf(profile.city.ifBlank { "Managua" }) }
    var zoneCoverage by remember { mutableStateOf(if (profile.city.isNotBlank()) "${profile.city} Centro, Comercio y Zonas Aledañas" else "Managua Centro, Metrocentro, Altamira y Alrededores") }
    var workingHours by remember { mutableStateOf("7:00 AM - 9:00 PM") }
    var description by remember { mutableStateOf("Repartidor puntual, honesto y con experiencia. Garantizo el cuidado y rapidez en todas tus entregas.") }
    var validationError by remember { mutableStateOf("") }
    var showHelpDialog by remember { mutableStateOf(false) }

    var expandedCityDropdown by remember { mutableStateOf(false) }

    // Multi-select services
    val availableServices = remember {
        listOf(
            MandaditoServiceOption("express", "Envíos Express", Icons.Default.Speed),
            MandaditoServiceOption("food", "Comida & Restaurantes", Icons.Default.Fastfood),
            MandaditoServiceOption("pharmacy", "Farmacias & Medicinas", Icons.Default.LocalPharmacy),
            MandaditoServiceOption("super", "Compras en Súper & Mercados", Icons.Default.ShoppingCart),
            MandaditoServiceOption("packages", "Paquetes & Encomiendas", Icons.Default.Inventory2),
            MandaditoServiceOption("bills", "Trámites & Pagos", Icons.Default.ReceiptLong),
            MandaditoServiceOption("urgent", "Llaves & Urgencias", Icons.Default.Key)
        )
    }

    var selectedServices by remember {
        mutableStateOf(
            setOf("Envíos Express", "Comida & Restaurantes", "Farmacias & Medicinas", "Paquetes & Encomiendas")
        )
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { photoUrl = it.toString() }
    }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = BackgroundSurface,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundSurface)
        ) {
            // TOP APP BAR
            Surface(
                color = BackgroundSurface.copy(alpha = 0.95f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar",
                            tint = PrimaryGreen
                        )
                    }

                    Text(
                        text = "Publicar Mandadito",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryGreen
                    )

                    IconButton(
                        onClick = { showHelpDialog = true },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.HelpOutline,
                            contentDescription = "Ayuda",
                            tint = PrimaryGreen
                        )
                    }
                }
            }

            // MAIN SCROLLABLE CONTENT
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 30.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                // ==========================================
                // USER VERIFIED IDENTITY CARD
                // ==========================================
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, LineBorder)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .border(2.dp, PrimaryContainerGreen, CircleShape)
                                .background(PrimaryContainerGreen.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            if (photoUrl.isNotBlank()) {
                                AsyncImage(
                                    model = photoUrl,
                                    contentDescription = name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            } else {
                                Text(
                                    text = userInitial,
                                    color = OnSurfaceColor,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 20.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Text(
                                text = "PUBLICANDO COMO REPARTIDOR",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF6B7280),
                                letterSpacing = 0.5.sp
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = name.ifBlank { "Repartidor" },
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = OnSurfaceColor
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Verificado",
                                    tint = PrimaryGreen,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Text(
                                text = "Servicio verificado • Trato directo",
                                fontSize = 12.sp,
                                color = Color(0xFF6B7280)
                            )
                        }
                    }
                }

                // ==========================================
                // 1. FOTO DEL REPARTIDOR / MOTO
                // ==========================================
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, LineBorder)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "Foto del Repartidor o Motocicleta",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurfaceColor
                        )
                        Text(
                            text = "Una foto real de tu moto o perfil genera mayor confianza en los clientes.",
                            fontSize = 12.5.sp,
                            color = OnSurfaceVariantColor
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(SurfaceContainerLow)
                                .dashedBorder(
                                    width = 1.5.dp,
                                    color = OutlineVariantColor,
                                    cornerRadius = 14.dp
                                )
                                .clickable { photoPickerLauncher.launch("image/*") },
                            contentAlignment = Alignment.Center
                        ) {
                            if (photoUrl.isNotBlank()) {
                                AsyncImage(
                                    model = photoUrl,
                                    contentDescription = "Foto del repartidor",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopStart)
                                        .padding(10.dp)
                                        .background(PrimaryContainerGreen, RoundedCornerShape(8.dp))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = "★ Foto de Perfil / Moto",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = OnPrimaryContainerGreen
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(10.dp)
                                        .background(Color.Black.copy(alpha = 0.65f), RoundedCornerShape(16.dp))
                                        .padding(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = "Tocar para cambiar",
                                        fontSize = 11.5.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.White
                                    )
                                }
                            } else {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AddPhotoAlternate,
                                        contentDescription = "Elegir foto",
                                        tint = OutlineColor,
                                        modifier = Modifier.size(36.dp)
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "Seleccionar foto de galería",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = OnSurfaceColor
                                    )
                                }
                            }
                        }

                        // Preset photos for easy testing
                        TextButton(
                            onClick = {
                                val presets = listOf(
                                    "https://images.unsplash.com/photo-1558981403-c5f9899a28bc?w=600",
                                    "https://images.unsplash.com/photo-1568772585407-9361f9bf3a87?w=600",
                                    "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=400"
                                )
                                photoUrl = presets.random()
                            },
                            modifier = Modifier.align(Alignment.Start),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = "✨ Usar foto de muestra para pruebas",
                                fontSize = 12.sp,
                                color = PrimaryGreen,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                // ==========================================
                // 2. VEHÍCULO & TRANSPORTE
                // ==========================================
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, LineBorder)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(
                            text = "Vehículo & Medio de Transporte",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurfaceColor
                        )

                        // Vehicle type quick chips
                        val vehicleOptions = listOf(
                            "Motocicleta 150cc",
                            "Scooter automática",
                            "Moto Mensajera 200cc",
                            "Bicicleta / A pie"
                        )
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            vehicleOptions.forEach { opt ->
                                val isSelected = selectedVehicleType == opt
                                FilterChip(
                                    selected = isSelected,
                                    onClick = {
                                        selectedVehicleType = opt
                                        customVehicleModel = opt
                                    },
                                    label = { Text(opt, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = ActiveChipBg,
                                        selectedLabelColor = PrimaryGreen
                                    )
                                )
                            }
                        }

                        OutlinedTextField(
                            value = customVehicleModel,
                            onValueChange = { customVehicleModel = it },
                            label = { Text("Modelo / Color del Vehículo") },
                            placeholder = { Text("Ej. Yamaha FZ 150cc Roja / Honda Navi") },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryGreen,
                                unfocusedBorderColor = LineBorder
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = vehiclePlate,
                            onValueChange = { vehiclePlate = it },
                            label = { Text("Número de Placa") },
                            placeholder = { Text("Ej. M-14829") },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryGreen,
                                unfocusedBorderColor = LineBorder
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                // ==========================================
                // 3. ZONA DE COBERTURA & HORARIO
                // ==========================================
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, LineBorder)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(
                            text = "Zona de Cobertura & Horario",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurfaceColor
                        )

                        // City selector with dropdown
                        Box(modifier = Modifier.fillMaxWidth()) {
                            OutlinedTextField(
                                value = selectedCity,
                                onValueChange = { selectedCity = it },
                                label = { Text("Ciudad / Municipio Principal") },
                                trailingIcon = {
                                    IconButton(onClick = { expandedCityDropdown = true }) {
                                        Icon(imageVector = Icons.Default.ExpandMore, contentDescription = "Seleccionar ciudad")
                                    }
                                },
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = PrimaryGreen,
                                    unfocusedBorderColor = LineBorder
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )

                            DropdownMenu(
                                expanded = expandedCityDropdown,
                                onDismissRequest = { expandedCityDropdown = false }
                            ) {
                                allLocations.forEach { loc ->
                                    DropdownMenuItem(
                                        text = { Text(loc) },
                                        onClick = {
                                            selectedCity = loc
                                            zoneCoverage = "$loc Centro, Comercios y Zonas Aledañas"
                                            expandedCityDropdown = false
                                        }
                                    )
                                }
                            }
                        }

                        OutlinedTextField(
                            value = zoneCoverage,
                            onValueChange = { zoneCoverage = it },
                            label = { Text("Barrios y Sectores que Cubres") },
                            placeholder = { Text("Ej. Metrocentro, Altamira, Carretera a Masaya, Galerías") },
                            minLines = 2,
                            maxLines = 3,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryGreen,
                                unfocusedBorderColor = LineBorder
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Working hours
                        OutlinedTextField(
                            value = workingHours,
                            onValueChange = { workingHours = it },
                            label = { Text("Horario de Disponibilidad") },
                            placeholder = { Text("Ej. 7:00 AM - 9:00 PM • Lunes a Domingo") },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryGreen,
                                unfocusedBorderColor = LineBorder
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                // ==========================================
                // 4. SERVICIOS Y ESPECIALIDADES (CHIPS CON ÍCONOS)
                // ==========================================
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, LineBorder)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Servicios y Mandados que Realizas",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurfaceColor
                        )
                        Text(
                            text = "Selecciona todos los tipos de mandados que estás disponible para atender:",
                            fontSize = 12.5.sp,
                            color = OnSurfaceVariantColor
                        )

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            availableServices.forEach { service ->
                                val isSelected = selectedServices.contains(service.name)
                                FilterChip(
                                    selected = isSelected,
                                    onClick = {
                                        selectedServices = if (isSelected) {
                                            selectedServices - service.name
                                        } else {
                                            selectedServices + service.name
                                        }
                                    },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = service.icon,
                                            contentDescription = null,
                                            tint = if (isSelected) PrimaryGreen else OnSurfaceVariantColor,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    },
                                    label = {
                                        Text(
                                            text = service.name,
                                            fontSize = 12.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                        )
                                    },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = ActiveChipBg,
                                        selectedLabelColor = PrimaryGreen
                                    )
                                )
                            }
                        }
                    }
                }

                // ==========================================
                // 5. BANNER DESTACADO DE TRATO DIRECTO
                // ==========================================
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFE8F5E9),
                    border = BorderStroke(1.dp, Color(0xFFA5D6A7)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFC8E6C9)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Handshake,
                                contentDescription = null,
                                tint = Color(0xFF1B5E20),
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(
                                text = "100% Trato Directo sin Comisiones",
                                fontSize = 13.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1B5E20)
                            )
                            Text(
                                text = "En BusDrive no cobramos comisiones por tus carreras. Tú acuerdas libremente con cada cliente el costo del mandado según distancia, tiempo y encargo.",
                                fontSize = 12.sp,
                                color = Color(0xFF2E7D32),
                                lineHeight = 16.sp
                            )
                        }
                    }
                }

                // ==========================================
                // 6. CONTACTO & PRESENTACIÓN
                // ==========================================
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, LineBorder)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(
                            text = "Contacto & Presentación",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurfaceColor
                        )

                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Tu Nombre Completo / Apodo de Trabajo") },
                            placeholder = { Text("Ej. Carlos Mendoza") },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryGreen,
                                unfocusedBorderColor = LineBorder
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = phone,
                            onValueChange = { phone = it },
                            label = { Text("Teléfono & WhatsApp de Contacto") },
                            placeholder = { Text("Ej. 8888 8888") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryGreen,
                                unfocusedBorderColor = LineBorder
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = { Text("Mensaje de Presentación para Clientes") },
                            placeholder = { Text("Describe tu experiencia, puntualidad, caja de delivery, etc.") },
                            minLines = 3,
                            maxLines = 5,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryGreen,
                                unfocusedBorderColor = LineBorder
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                if (validationError.isNotBlank()) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFFFFEBEE),
                        border = BorderStroke(1.dp, Color(0xFFFFCDD2)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = validationError,
                            color = Color(0xFFC62828),
                            fontSize = 12.5.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // ==========================================
                // BOTÓN PUBLICAR MANDADITO (CALL TO ACTION)
                // ==========================================
                Button(
                    onClick = {
                        if (name.isBlank() || phone.isBlank() || zoneCoverage.isBlank()) {
                            validationError = "⚠️ Por favor completa tu nombre, teléfono y zona de cobertura."
                        } else {
                            val newCourier = MandaditoCourier(
                                id = "courier_${System.currentTimeMillis()}",
                                name = name.trim(),
                                phone = phone.trim(),
                                vehicleType = customVehicleModel.ifBlank { selectedVehicleType },
                                vehiclePlate = vehiclePlate.trim().ifBlank { "M-Particular" },
                                photoUrl = photoUrl.ifBlank { "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=400" },
                                zoneCoverage = if (selectedCity.isNotBlank() && !zoneCoverage.contains(selectedCity)) "$selectedCity • $zoneCoverage" else zoneCoverage,
                                baseRate = "A convenir",
                                rating = 5.0f,
                                reviewsCount = 1,
                                completedDeliveries = 10,
                                isAvailable = true,
                                isVerified = true,
                                services = if (selectedServices.isNotEmpty()) selectedServices.toList() else listOf("Envíos Express", "Paquetería"),
                                workingHours = workingHours.ifBlank { "7:00 AM - 9:00 PM" },
                                description = description.ifBlank { "Repartidor puntual y responsable. Trato directo y seguro." }
                            )
                            onPublish(newCourier)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LimeBrand,
                        contentColor = Color(0xFF111827)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.TwoWheeler,
                        contentDescription = null,
                        tint = Color(0xFF111827),
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Publicar Mandadito",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // HELP MODAL DIALOG
    if (showHelpDialog) {
        AlertDialog(
            onDismissRequest = { showHelpDialog = false },
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
                    Text("Guía para Repartidores", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        "• Tu publicación aparecerá visible para todos los usuarios de tu zona en la pestaña Mandaditos.",
                        fontSize = 13.sp,
                        color = OnSurfaceColor
                    )
                    Text(
                        "• Los clientes te contactarán directamente vía WhatsApp o llamada telefónica.",
                        fontSize = 13.sp,
                        color = OnSurfaceColor
                    )
                    Text(
                        "• Trato directo: acuerda con el cliente el monto exacto antes de iniciar el recorrido.",
                        fontSize = 13.sp,
                        color = OnSurfaceColor
                    )
                    Text(
                        "• Mantén tu WhatsApp activo para responder rápido a los nuevos pedidos.",
                        fontSize = 13.sp,
                        color = OnSurfaceColor
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { showHelpDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                ) {
                    Text("Entendido", color = Color.White)
                }
            }
        )
    }
}
