package com.example.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Bathtub
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.ProviderProfile
import com.example.data.model.Stay
import com.example.data.model.UserProfile

// Exact color palette matching the Tailwind theme
private val PrimaryGreen = Color(0xFF4D6700)
private val PrimaryContainerGreen = Color(0xFFA4D600)
private val OnPrimaryContainerGreen = Color(0xFF435900)
private val BackgroundSurface = Color(0xFFF7F9FF)
private val SurfaceCard = Color(0xFFFFFFFF)
private val SurfaceContainerLow = Color(0xFFEEF4FD)
private val SurfaceContainerHigh = Color(0xFFE3E8F2)
private val OutlineVariantColor = Color(0xFFC4C9AE)
private val OutlineColor = Color(0xFF747A62)
private val OnSurfaceColor = Color(0xFF161C23)
private val OnSurfaceVariantColor = Color(0xFF5D5E61)
private val ActiveChipBg = Color(0xFFEBF6D2)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PublicarEstanciaDialog(
    stay: Stay,
    profile: ProviderProfile,
    allLocations: List<String>,
    onDismiss: () -> Unit,
    onPublish: (Long?, String, String, String, String, Int, String, Int, Int, Int, Int, List<String>, String, String, String, String, String) -> Unit,
    currentUser: UserProfile? = null
) {
    val context = LocalContext.current

    val hostName = profile.fullName.ifBlank { currentUser?.name ?: stay.hostName.ifBlank { "Anfitrión" } }
    val telefonoHost = profile.phone.ifBlank { currentUser?.phone ?: stay.telefono.ifBlank { "" } }
    val whatsappHost = profile.phone.ifBlank { currentUser?.phone ?: stay.whatsapp.ifBlank { "" } }
    val userPhotoUrl = profile.profilePhotoUrl.ifBlank { currentUser?.photoUrl ?: stay.providerPhotoUrl }

    // Form fields
    var nombre by remember { mutableStateOf(stay.nombre) }
    var tipo by remember { mutableStateOf(if (stay.tipo.isNotBlank()) stay.tipo else "Casa entera") }
    var municipio by remember { mutableStateOf(if (stay.municipio.isNotBlank()) stay.municipio else profile.municipality.ifBlank { "Managua" }) }
    var direccion by remember { mutableStateOf(stay.direccion) }
    var precioStr by remember { mutableStateOf(if (stay.precio > 0) stay.precio.toString() else "500") }
    var per by remember { mutableStateOf(if (stay.per.isNotBlank()) stay.per else "noche") }

    var huespedes by remember { mutableIntStateOf(if (stay.huespedes > 0) stay.huespedes else 4) }
    var habitaciones by remember { mutableIntStateOf(if (stay.habitaciones > 0) stay.habitaciones else 2) }
    var banos by remember { mutableIntStateOf(if (stay.banos > 0) stay.banos else 1) }
    var camas by remember { mutableIntStateOf(if (stay.camas > 0) stay.camas else 2) }

    var descripcion by remember { mutableStateOf(stay.descripcion) }
    var validationError by remember { mutableStateOf("") }
    var showHelpDialog by remember { mutableStateOf(false) }

    // Photos management (main photo + secondary photos list)
    var photosList by remember {
        mutableStateOf(
            if (stay.photosJson.isNotBlank()) stay.photosJson.split("|").filter { it.isNotBlank() }
            else emptyList()
        )
    }

    var selectedAmenityKeys by remember {
        mutableStateOf(
            if (stay.serviciosJson.isNotBlank()) stay.serviciosJson.split(",").map { it.trim() }.toSet()
            else setOf("WiFi", "Cocina", "Aire Acond.", "Parking")
        )
    }

    val mainPhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val uriStr = it.toString()
            photosList = if (photosList.isEmpty()) listOf(uriStr) else listOf(uriStr) + photosList.drop(1)
        }
    }

    val secondaryPhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val uriStr = it.toString()
            photosList = photosList + uriStr
        }
    }

    val stayTypesList = remember {
        listOf(
            "Casa entera",
            "Apartamento",
            "Cabaña",
            "Habitación privada",
            "Hostal"
        )
    }

    val amenitiesList = remember {
        listOf(
            AmenityItem("WiFi", Icons.Default.Wifi),
            AmenityItem("Cocina", Icons.Default.Kitchen),
            AmenityItem("Aire Acond.", Icons.Default.AcUnit),
            AmenityItem("Parking", Icons.Default.DirectionsCar),
            AmenityItem("Piscina", Icons.Default.Pool),
            AmenityItem("Mascotas", Icons.Default.Pets),
            AmenityItem("TV", Icons.Default.Tv),
            AmenityItem("Agua Caliente", Icons.Default.WaterDrop)
        )
    }

    var expandedLocationDropdown by remember { mutableStateOf(false) }
    var expandedTypeDropdown by remember { mutableStateOf(false) }

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
            // TOP APP BAR (matching HTML Header)
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
                        text = if (stay.id > 0) "Editar Estancia" else "Publicar Estancia",
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
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // ==========================================
                // 1. PHOTO UPLOAD SECTION (FEATURED)
                // ==========================================
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Fotos de la propiedad",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = OnSurfaceColor
                    )
                    Text(
                        text = "Sube fotos claras y luminosas para atraer más huéspedes.",
                        fontSize = 14.sp,
                        color = OnSurfaceVariantColor
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Main Photo Upload Card
                    val mainPhoto = photosList.firstOrNull()
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(SurfaceContainerLow)
                            .dashedBorder(
                                width = 2.dp,
                                color = OutlineVariantColor,
                                cornerRadius = 16.dp
                            )
                            .clickable { mainPhotoPickerLauncher.launch("image/*") },
                        contentAlignment = Alignment.Center
                    ) {
                        if (!mainPhoto.isNullOrBlank()) {
                            AsyncImage(
                                model = mainPhoto,
                                contentDescription = "Foto principal",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            // Overlay badge and change button
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(12.dp)
                                    .background(PrimaryContainerGreen, RoundedCornerShape(8.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "★ Foto Principal",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = OnPrimaryContainerGreen
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(12.dp)
                                    .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(20.dp))
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "Tocar para cambiar",
                                    fontSize = 12.sp,
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
                                    contentDescription = "Añadir foto principal",
                                    tint = OutlineColor,
                                    modifier = Modifier.size(40.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Añadir foto principal",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = OnSurfaceColor
                                )
                            }
                        }
                    }

                    // Secondary Photo Uploads Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        for (i in 0 until 3) {
                            val secondaryPhoto = photosList.getOrNull(i + 1)
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(SurfaceContainerLow)
                                    .dashedBorder(
                                        width = 1.5.dp,
                                        color = OutlineVariantColor,
                                        cornerRadius = 12.dp
                                    )
                                    .clickable {
                                        if (secondaryPhoto == null) {
                                            secondaryPhotoPickerLauncher.launch("image/*")
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                if (!secondaryPhoto.isNullOrBlank()) {
                                    AsyncImage(
                                        model = secondaryPhoto,
                                        contentDescription = "Foto secundaria",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                    IconButton(
                                        onClick = {
                                            photosList = photosList.filterIndexed { index, _ -> index != i + 1 }
                                        },
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .size(28.dp)
                                            .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Eliminar foto",
                                            tint = Color.White,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Añadir foto adicional",
                                        tint = OutlineColor,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }
                    }

                    // Quick sample photo presets for instant testing
                    TextButton(
                        onClick = {
                            photosList = listOf(
                                "https://images.unsplash.com/photo-1582719478250-c89cae4dc85b?w=800",
                                "https://images.unsplash.com/photo-1590490360182-c33d57733427?w=800",
                                "https://images.unsplash.com/photo-1566073771259-6a8506099945?w=800"
                            )
                        },
                        modifier = Modifier.align(Alignment.Start)
                    ) {
                        Text(
                            text = "✨ Usar fotos de muestra para pruebas",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = PrimaryGreen
                        )
                    }
                }

                // ==========================================
                // 2. INFORMACIÓN BÁSICA SECTION
                // ==========================================
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                    border = BorderStroke(1.dp, SurfaceContainerHigh)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Información básica",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = OnSurfaceColor
                        )

                        // Property Title
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = "Título de la propiedad",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = OnSurfaceColor
                            )
                            OutlinedTextField(
                                value = nombre,
                                onValueChange = { nombre = it; validationError = "" },
                                placeholder = {
                                    Text(
                                        "Ej. Acogedora cabaña en el bosque",
                                        color = OnSurfaceVariantColor.copy(alpha = 0.6f),
                                        fontSize = 14.sp
                                    )
                                },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RoundedCornerShape(10.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = SurfaceContainerLow,
                                    unfocusedContainerColor = SurfaceContainerLow,
                                    focusedBorderColor = PrimaryGreen,
                                    unfocusedBorderColor = OutlineVariantColor
                                )
                            )
                        }

                        // Location Input with leading icon & dropdown suggestion
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = "Ubicación",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = OnSurfaceColor
                            )
                            Box(modifier = Modifier.fillMaxWidth()) {
                                OutlinedTextField(
                                    value = if (direccion.isNotBlank()) "$municipio, $direccion" else municipio,
                                    onValueChange = {
                                        direccion = it
                                        validationError = ""
                                    },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.LocationOn,
                                            contentDescription = "Ubicación",
                                            tint = OnSurfaceVariantColor
                                        )
                                    },
                                    trailingIcon = {
                                        IconButton(onClick = { expandedLocationDropdown = true }) {
                                            Icon(
                                                imageVector = Icons.Default.ExpandMore,
                                                contentDescription = "Elegir municipio",
                                                tint = OnSurfaceVariantColor
                                            )
                                        }
                                    },
                                    placeholder = {
                                        Text(
                                            "Dirección completa (ej. San Juan del Sur, Rivas)",
                                            color = OnSurfaceVariantColor.copy(alpha = 0.6f),
                                            fontSize = 14.sp
                                        )
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                    shape = RoundedCornerShape(10.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedContainerColor = SurfaceContainerLow,
                                        unfocusedContainerColor = SurfaceContainerLow,
                                        focusedBorderColor = PrimaryGreen,
                                        unfocusedBorderColor = OutlineVariantColor
                                    )
                                )

                                DropdownMenu(
                                    expanded = expandedLocationDropdown,
                                    onDismissRequest = { expandedLocationDropdown = false },
                                    modifier = Modifier.background(Color.White)
                                ) {
                                    val popularLocs = if (allLocations.isNotEmpty()) allLocations.take(15)
                                    else listOf("San Juan del Sur", "Granada", "Ometepe", "León", "Managua", "Matagalpa", "Tola", "Corn Island", "Puerto Cabezas")
                                    popularLocs.forEach { loc ->
                                        DropdownMenuItem(
                                            text = { Text(loc, fontSize = 14.sp) },
                                            onClick = {
                                                municipio = loc
                                                expandedLocationDropdown = false
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        // Accommodation Type Selector
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = "Tipo de alojamiento",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = OnSurfaceColor
                            )
                            Box(modifier = Modifier.fillMaxWidth()) {
                                OutlinedTextField(
                                    value = tipo,
                                    onValueChange = {},
                                    readOnly = true,
                                    trailingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.ExpandMore,
                                            contentDescription = "Expandir tipos",
                                            tint = OnSurfaceVariantColor
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { expandedTypeDropdown = true },
                                    shape = RoundedCornerShape(10.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedContainerColor = SurfaceContainerLow,
                                        unfocusedContainerColor = SurfaceContainerLow,
                                        focusedBorderColor = PrimaryGreen,
                                        unfocusedBorderColor = OutlineVariantColor
                                    ),
                                    interactionSource = remember { MutableInteractionSource() }
                                )

                                DropdownMenu(
                                    expanded = expandedTypeDropdown,
                                    onDismissRequest = { expandedTypeDropdown = false },
                                    modifier = Modifier.background(Color.White)
                                ) {
                                    stayTypesList.forEach { t ->
                                        DropdownMenuItem(
                                            text = { Text(t, fontSize = 14.sp, fontWeight = if (t == tipo) FontWeight.Bold else FontWeight.Normal) },
                                            onClick = {
                                                tipo = t
                                                expandedTypeDropdown = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // ==========================================
                // 3. CAPACIDAD Y ESPACIOS SECTION
                // ==========================================
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                    border = BorderStroke(1.dp, SurfaceContainerHigh)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Capacidad y Espacios",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = OnSurfaceColor
                        )

                        // Counters row (Huéspedes, Habitaciones, Baños)
                        CapacityCounterItem(
                            icon = Icons.Default.Groups,
                            label = "Huéspedes",
                            count = huespedes,
                            onMinus = { if (huespedes > 1) huespedes-- },
                            onPlus = { huespedes++ }
                        )

                        CapacityCounterItem(
                            icon = Icons.Default.Bed,
                            label = "Habitaciones",
                            count = habitaciones,
                            onMinus = { if (habitaciones > 1) habitaciones-- },
                            onPlus = { habitaciones++ }
                        )

                        CapacityCounterItem(
                            icon = Icons.Default.Bathtub,
                            label = "Baños",
                            count = banos,
                            onMinus = { if (banos > 1) banos-- },
                            onPlus = { banos++ }
                        )
                    }
                }

                // ==========================================
                // 4. AMENIDADES SECTION
                // ==========================================
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                    border = BorderStroke(1.dp, SurfaceContainerHigh)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Amenidades",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = OnSurfaceColor
                        )
                        Text(
                            text = "¿Qué incluye tu espacio?",
                            fontSize = 14.sp,
                            color = OnSurfaceVariantColor
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        // Grid of selectable amenity cards
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            maxItemsInEachRow = 2
                        ) {
                            amenitiesList.forEach { amenity ->
                                val isChecked = selectedAmenityKeys.contains(amenity.label)
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(if (isChecked) ActiveChipBg else SurfaceContainerLow)
                                        .border(
                                            1.dp,
                                            if (isChecked) PrimaryGreen else Color.Transparent,
                                            RoundedCornerShape(10.dp)
                                        )
                                        .clickable {
                                            selectedAmenityKeys = if (isChecked) {
                                                selectedAmenityKeys - amenity.label
                                            } else {
                                                selectedAmenityKeys + amenity.label
                                            }
                                        }
                                        .padding(horizontal = 14.dp, vertical = 12.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Icon(
                                            imageVector = amenity.icon,
                                            contentDescription = amenity.label,
                                            tint = if (isChecked) PrimaryGreen else OnSurfaceVariantColor,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Text(
                                            text = amenity.label,
                                            fontSize = 14.sp,
                                            fontWeight = if (isChecked) FontWeight.SemiBold else FontWeight.Normal,
                                            color = OnSurfaceColor
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // ==========================================
                // 5. PRECIO SECTION
                // ==========================================
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                    border = BorderStroke(1.dp, SurfaceContainerHigh)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Precio",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = OnSurfaceColor
                        )

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "Precio por noche",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = OnSurfaceColor
                            )

                            OutlinedTextField(
                                value = precioStr,
                                onValueChange = {
                                    if (it.all { char -> char.isDigit() }) {
                                        precioStr = it
                                    }
                                },
                                prefix = {
                                    Text(
                                        text = "$ ",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = OnSurfaceVariantColor
                                    )
                                },
                                suffix = {
                                    Text(
                                        text = "C$ / noche",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = OnSurfaceVariantColor
                                    )
                                },
                                placeholder = {
                                    Text("500", fontSize = 18.sp, color = OnSurfaceVariantColor.copy(alpha = 0.5f))
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                textStyle = TextStyle(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = OnSurfaceColor
                                ),
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = SurfaceContainerLow,
                                    unfocusedContainerColor = SurfaceContainerLow,
                                    focusedBorderColor = PrimaryGreen,
                                    unfocusedBorderColor = OutlineVariantColor
                                )
                            )
                        }
                    }
                }

                // Optional Description Area
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                    border = BorderStroke(1.dp, SurfaceContainerHigh)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Descripción adicional (Opcional)",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = OnSurfaceColor
                        )
                        OutlinedTextField(
                            value = descripcion,
                            onValueChange = { descripcion = it },
                            placeholder = {
                                Text(
                                    "Describe el ambiente, normas de la casa o detalles para los viajeros...",
                                    fontSize = 13.sp,
                                    color = OnSurfaceVariantColor.copy(alpha = 0.6f)
                                )
                            },
                            minLines = 2,
                            maxLines = 4,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = SurfaceContainerLow,
                                unfocusedContainerColor = SurfaceContainerLow,
                                focusedBorderColor = PrimaryGreen,
                                unfocusedBorderColor = OutlineVariantColor
                            )
                        )
                    }
                }

                // Validation error feedback
                if (validationError.isNotBlank()) {
                    Text(
                        text = validationError,
                        color = Color(0xFFBA1A1A),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }

                // ==========================================
                // 6. ACTION AREA (BUTTONS)
                // ==========================================
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Guardar borrador button
                    OutlinedButton(
                        onClick = {
                            Toast.makeText(context, "Borrador de estancia guardado.", Toast.LENGTH_SHORT).show()
                            onDismiss()
                        },
                        shape = RoundedCornerShape(9999.dp),
                        border = BorderStroke(1.dp, OutlineVariantColor),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = OnSurfaceColor),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = "Guardar borrador",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    // Publicar Estancia button
                    Button(
                        onClick = {
                            val pInt = precioStr.toIntOrNull() ?: 500
                            val finalPhotos = if (photosList.isNotEmpty()) {
                                photosList.joinToString("|")
                            } else {
                                "https://images.unsplash.com/photo-1590490360182-c33d57733427?w=800|https://images.unsplash.com/photo-1582719478250-c89cae4dc85b?w=800"
                            }

                            if (nombre.isBlank()) {
                                validationError = "⚠️ Ingresa el título o nombre de la propiedad."
                            } else if (municipio.isBlank()) {
                                validationError = "⚠️ Ingresa el municipio o ubicación."
                            } else {
                                onPublish(
                                    if (stay.id > 0) stay.id else null,
                                    nombre.trim(),
                                    tipo,
                                    municipio.trim(),
                                    direccion.trim(),
                                    pInt,
                                    per,
                                    huespedes,
                                    habitaciones,
                                    camas,
                                    banos,
                                    selectedAmenityKeys.toList(),
                                    descripcion.trim(),
                                    whatsappHost,
                                    telefonoHost,
                                    finalPhotos,
                                    userPhotoUrl
                                )
                                Toast.makeText(context, "¡Estancia publicada con éxito!", Toast.LENGTH_SHORT).show()
                            }
                        },
                        shape = RoundedCornerShape(9999.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryContainerGreen,
                            contentColor = OnPrimaryContainerGreen
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp),
                        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = if (stay.id > 0) "Guardar Cambios" else "Publicar Estancia",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }

    // Help Dialog
    if (showHelpDialog) {
        AlertDialog(
            onDismissRequest = { showHelpDialog = false },
            title = {
                Text("Publicar Estancia en BusDrive", fontWeight = FontWeight.Bold, color = PrimaryGreen)
            },
            text = {
                Text(
                    "BusDrive conecta a viajeros con anfitriones locales en toda Nicaragua.\n\n" +
                    "• Las fotos atractivas aumentan tus solicitudes de reserva.\n" +
                    "• Mantén tus precios transparentes por noche en Córdobas (C$) o Dólares.\n" +
                    "• Los viajeros te contactarán directamente a tu WhatsApp o llamada.",
                    fontSize = 14.sp,
                    color = OnSurfaceColor,
                    lineHeight = 20.sp
                )
            },
            confirmButton = {
                TextButton(onClick = { showHelpDialog = false }) {
                    Text("Entendido", fontWeight = FontWeight.Bold, color = PrimaryGreen)
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(16.dp)
        )
    }
}

@Composable
private fun CapacityCounterItem(
    icon: ImageVector,
    label: String,
    count: Int,
    onMinus: () -> Unit,
    onPlus: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceContainerLow)
            .border(1.dp, SurfaceContainerHigh, RoundedCornerShape(12.dp))
            .padding(horizontal = 14.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = OnSurfaceVariantColor,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = OnSurfaceColor
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(SurfaceCard)
                    .border(1.dp, OutlineVariantColor, CircleShape)
                    .clickable { onMinus() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = "Menos",
                    tint = OnSurfaceColor,
                    modifier = Modifier.size(16.dp)
                )
            }

            Text(
                text = count.toString(),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = OnSurfaceColor
            )

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(SurfaceCard)
                    .border(1.dp, OutlineVariantColor, CircleShape)
                    .clickable { onPlus() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Más",
                    tint = OnSurfaceColor,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

private data class AmenityItem(
    val label: String,
    val icon: ImageVector
)

/**
 * Extension modifier to draw dashed border around composables
 */
private fun Modifier.dashedBorder(
    width: Dp,
    color: Color,
    cornerRadius: Dp,
    dashLength: Dp = 8.dp,
    gapLength: Dp = 6.dp
): Modifier = this.drawBehind {
    val stroke = Stroke(
        width = width.toPx(),
        pathEffect = PathEffect.dashPathEffect(
            floatArrayOf(dashLength.toPx(), gapLength.toPx()),
            0f
        )
    )
    val r = cornerRadius.toPx()
    drawRoundRect(
        color = color,
        size = size,
        cornerRadius = CornerRadius(r, r),
        style = stroke
    )
}
