package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Ride
import com.example.data.model.Stay
import com.example.data.model.Ticket
import com.example.ui.viewmodel.BusDriveViewModel

// Color palette exactly matching HTML Tailwind specification
private val BgSurface = Color(0xFFF3FCEE)
private val PrimaryGreen = Color(0xFF006E2A)
private val PrimaryContainer = Color(0xFF00C853)
private val OnPrimaryContainer = Color(0xFF004C1B)
private val SurfaceContainerLowest = Color(0xFFFFFFFF)
private val SurfaceContainer = Color(0xFFE7F1E3)
private val SurfaceContainerHigh = Color(0xFFE2EBDD)
private val SurfaceContainerHighest = Color(0xFFDCE5D8)
private val OnSurface = Color(0xFF161E15)
private val OnSurfaceVariant = Color(0xFF3C4A3C)
private val Outline = Color(0xFF6C7B6A)
private val OutlineVariant = Color(0xFFBBCBB8)
private val ErrorColor = Color(0xFFBA1A1A)

enum class PublicacionTab(val title: String) {
    BOLETOS("Boletos"),
    RIDES("Rides"),
    ESTANCIAS("Estancias")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisPublicacionesScreen(
    viewModel: BusDriveViewModel,
    onBack: () -> Unit
) {
    val currentUser by viewModel.currentUser.collectAsState()
    val providerProfile by viewModel.currentProviderProfile.collectAsState()
    val tickets by viewModel.allTickets.collectAsState()
    val rides by viewModel.allRides.collectAsState()
    val stays by viewModel.allStays.collectAsState()

    val showPublishTicket by viewModel.showPublishTicketModal.collectAsState()
    val showPublishRide by viewModel.showPublishRideModal.collectAsState()
    val showPublishStay by viewModel.showPublishStayModal.collectAsState()

    var selectedTab by remember { mutableStateOf(PublicacionTab.RIDES) }
    var showTypeSelector by remember { mutableStateOf(false) }

    // User's publications (or demo items if none yet)
    val userTickets = tickets.filter { currentUser == null || it.ownerId == currentUser?.id || it.ownerId.isBlank() }
    val userRides = rides.filter { currentUser == null || it.ownerId == currentUser?.id || it.ownerId.isBlank() }
    val userStays = stays.filter { currentUser == null || it.ownerId == currentUser?.id || it.ownerId.isBlank() }

    val onStartPublish = {
        if (currentUser == null) {
            viewModel.showAuthModal.value = true
        } else {
            showTypeSelector = true
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
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
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

                    Text(
                        text = "Mis Publicaciones",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryGreen
                    )

                    // Spacer to center the title perfectly
                    Spacer(modifier = Modifier.size(40.dp))
                }
            }

            // MAIN CONTENT CANVAS
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 20.dp,
                    end = 20.dp,
                    top = 8.dp,
                    bottom = 100.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // SEGMENTED CONTROL / TABS
                item {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        color = SurfaceContainer
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            PublicacionTab.values().forEach { tab ->
                                val isSelected = tab == selectedTab
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(40.dp)
                                        .shadow(
                                            elevation = if (isSelected) 3.dp else 0.dp,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isSelected) SurfaceContainerLowest else Color.Transparent)
                                        .clickable { selectedTab = tab },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = tab.title,
                                        fontSize = 14.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) PrimaryGreen else OnSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }

                // POST LIST DEPENDING ON SELECTED TAB
                when (selectedTab) {
                    PublicacionTab.BOLETOS -> {
                        if (userTickets.isEmpty()) {
                            item {
                                EmptyPublicationCard(
                                    icon = Icons.Default.ConfirmationNumber,
                                    title = "No tienes boletos publicados",
                                    subtitle = "Publica boletos de autobús que no vayas a utilizar para que otros viajeros los adquieran.",
                                    buttonText = "Publicar Boleto",
                                    onAction = {
                                        if (currentUser == null) viewModel.showAuthModal.value = true
                                        else viewModel.showPublishTicketModal.value = true
                                    }
                                )
                            }
                        } else {
                            items(userTickets, key = { it.id }) { ticket ->
                                val isActive = ticket.estado == "disponible"
                                TicketPublicationCard(
                                    ticket = ticket,
                                    isActive = isActive,
                                    onToggle = { isChecked ->
                                        if (isChecked) {
                                            viewModel.repostTicket(ticket)
                                        } else {
                                            viewModel.markTicketSold(ticket)
                                        }
                                    },
                                    onEdit = {
                                        viewModel.showPublishTicketModal.value = true
                                    },
                                    onDelete = {
                                        viewModel.deleteTicket(ticket.id)
                                    }
                                )
                            }
                        }
                    }

                    PublicacionTab.RIDES -> {
                        if (userRides.isEmpty()) {
                            item {
                                EmptyPublicationCard(
                                    icon = Icons.Default.DirectionsCar,
                                    title = "No tienes viajes (rides) publicados",
                                    subtitle = "Ofrece asientos en tu vehículo para compartir gastos con la comunidad en tus rutas frecuentes.",
                                    buttonText = "Publicar Ride",
                                    onAction = {
                                        if (currentUser == null) viewModel.showAuthModal.value = true
                                        else viewModel.showPublishRideModal.value = true
                                    }
                                )
                            }
                        } else {
                            items(userRides, key = { it.id }) { ride ->
                                val isActive = ride.estado == "disponible" || ride.estado == "confirmado"
                                RidePublicationCard(
                                    ride = ride,
                                    isActive = isActive,
                                    onToggle = { isChecked ->
                                        val newState = if (isChecked) "disponible" else "pausado"
                                        viewModel.updateRideState(ride, newState)
                                    },
                                    onEdit = {
                                        viewModel.showPublishRideModal.value = true
                                    },
                                    onDelete = {
                                        viewModel.deleteRide(ride.id)
                                    }
                                )
                            }
                        }
                    }

                    PublicacionTab.ESTANCIAS -> {
                        if (userStays.isEmpty()) {
                            item {
                                EmptyPublicationCard(
                                    icon = Icons.Default.Home,
                                    title = "No tienes estancias registradas",
                                    subtitle = "Publica habitaciones, hostales o alojamientos para recibir a viajeros y turistas.",
                                    buttonText = "Publicar Estancia",
                                    onAction = {
                                        if (currentUser == null) {
                                            viewModel.showAuthModal.value = true
                                        } else {
                                            viewModel.showPublishStayModal.value = Stay(
                                                ownerId = currentUser?.id ?: "",
                                                hostName = providerProfile.fullName.ifBlank { currentUser?.name ?: "Anfitrión" },
                                                tipo = "Habitación privada",
                                                nombre = "",
                                                municipio = providerProfile.municipality.ifBlank { "Puerto Cabezas" },
                                                precio = 500
                                            )
                                        }
                                    }
                                )
                            }
                        } else {
                            items(userStays, key = { it.id }) { stay ->
                                val isActive = stay.estado == "disponible"
                                StayPublicationCard(
                                    stay = stay,
                                    isActive = isActive,
                                    onToggle = { isChecked ->
                                        val newState = if (isChecked) "disponible" else "pausado"
                                        viewModel.updateStayState(stay, newState)
                                    },
                                    onEdit = {
                                        viewModel.showPublishStayModal.value = stay
                                    },
                                    onDelete = {
                                        viewModel.deleteStay(stay.id)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        // FLOATING ACTION BUTTON (Nueva Publicación con solo +)
        FloatingActionButton(
            onClick = onStartPublish,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 24.dp, end = 20.dp),
            containerColor = Color(0xFF006E2A),
            contentColor = Color.White,
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 6.dp,
                pressedElevation = 10.dp
            )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Nueva Publicación",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
    }

    // TYPE SELECTOR BOTTOM SHEET
    if (showTypeSelector) {
        PublishTypeSelectorModal(
            onDismiss = { showTypeSelector = false },
            onSelectTicket = {
                showTypeSelector = false
                viewModel.showPublishTicketModal.value = true
            },
            onSelectRide = {
                showTypeSelector = false
                viewModel.showPublishRideModal.value = true
            },
            onSelectStay = {
                showTypeSelector = false
                viewModel.showPublishStayModal.value = Stay(
                    ownerId = currentUser?.id ?: "",
                    hostName = providerProfile.fullName.ifBlank { currentUser?.name ?: "Anfitrión" },
                    tipo = "Habitación privada",
                    nombre = "",
                    municipio = providerProfile.municipality.ifBlank { "Puerto Cabezas" },
                    precio = 500
                )
            }
        )
    }

    // MODAL DIALOGS
    val routesLocations = viewModel.allRoutes.collectAsState().value.flatMap { listOf(it.origen, it.destino) }.distinct().sorted()
    val locationsList = if (routesLocations.isNotEmpty()) routesLocations else listOf("Managua", "Siuna", "Puerto Cabezas (Bilwi)", "Bluefields", "Matagalpa", "Juigalpa", "Estelí", "Waslala", "Rosita", "Bonanza", "Waspam", "Chinandega", "León", "Rivas")

    if (showPublishTicket) {
        PublishTicketDialog(
            currentUser = currentUser,
            profile = providerProfile,
            allLocations = locationsList,
            onDismiss = { viewModel.showPublishTicketModal.value = false },
            onPublish = { n, c, o, d, f, h, op, a, p, nota, photo ->
                viewModel.publishTicket(n, c, o, d, f, h, op, a, p, nota, photo)
            }
        )
    }

    if (showPublishRide) {
        PublishRideDialog(
            currentUser = currentUser,
            profile = providerProfile,
            allLocations = locationsList,
            onDismiss = { viewModel.showPublishRideModal.value = false },
            onPublish = { o, d, f, h, v, c, e, p, pe, info, photo, vPhoto ->
                viewModel.publishRide(o, d, f, h, v, c, e, p, pe, info, photo, vPhoto)
            }
        )
    }

    showPublishStay?.let { editingStay ->
        PublishStayDialog(
            stay = editingStay,
            currentUser = currentUser,
            profile = providerProfile,
            allLocations = locationsList,
            onDismiss = { viewModel.showPublishStayModal.value = null },
            onPublish = { idToEdit, n, t, m, dir, p, per, h, hab, c, b, svcs, desc, wa, tel, photos, photo ->
                viewModel.publishStay(idToEdit, n, t, m, dir, p, per, h, hab, c, b, svcs, desc, wa, tel, photos, photo)
            }
        )
    }
}

// RIDE PUBLICATION CARD
@Composable
fun RidePublicationCard(
    ride: Ride,
    isActive: Boolean,
    onToggle: (Boolean) -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var showDeleteConfirm by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = if (isActive) 3.dp else 1.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color(0x0A000000),
                spotColor = Color(0x0A000000)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isActive) SurfaceContainerLowest else BgSurface
        ),
        border = BorderStroke(
            1.dp,
            if (isActive) SurfaceContainerHighest else SurfaceContainerHigh
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // HEADER ROW: Icon + Badge + Route + Toggle Switch
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    // Category & Status Badge
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(bottom = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.DirectionsCar,
                            contentDescription = null,
                            tint = if (isActive) PrimaryGreen else Outline,
                            modifier = Modifier.size(20.dp)
                        )
                        Surface(
                            shape = CircleShape,
                            color = if (isActive) SurfaceContainer else SurfaceContainerHigh
                        ) {
                            Text(
                                text = if (isActive) "Activo" else "Pausado",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isActive) PrimaryGreen else Outline,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }

                    // Route Title
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = ride.origen,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isActive) OnSurface else Outline
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = OutlineVariant,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = ride.destino,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isActive) OnSurface else Outline
                        )
                    }
                }

                // Interactive Switch
                Switch(
                    checked = isActive,
                    onCheckedChange = onToggle,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = PrimaryContainer,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = SurfaceContainerHigh,
                        uncheckedBorderColor = OutlineVariant
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // DETAILS ROW: Date / Time + Price
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = null,
                        tint = if (isActive) OnSurfaceVariant else Outline,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "${ride.fecha}, ${ride.hora}",
                        fontSize = 14.sp,
                        color = if (isActive) OnSurfaceVariant else Outline
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Payments,
                        contentDescription = null,
                        tint = if (isActive) PrimaryGreen else Outline,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "C$ ${ride.precioPorPersona}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isActive) OnSurface else Outline
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = OutlineVariant.copy(alpha = 0.3f))
            Spacer(modifier = Modifier.height(8.dp))

            // FOOTER ROW: Seats Available + Edit / Delete Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${ride.espaciosDisponibles} asientos disponibles",
                    fontSize = 13.sp,
                    color = if (isActive) OnSurfaceVariant else Outline
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Editar",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isActive) PrimaryGreen else Outline,
                        modifier = Modifier
                            .clickable { onEdit() }
                            .padding(4.dp)
                    )

                    IconButton(
                        onClick = { showDeleteConfirm = true },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            tint = ErrorColor.copy(alpha = 0.7f),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }

    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            title = { Text("Eliminar publicación", fontWeight = FontWeight.Bold) },
            text = { Text("¿Estás seguro de que deseas eliminar este ride de la comunidad?") },
            confirmButton = {
                Button(
                    onClick = {
                        showDeleteConfirm = false
                        onDelete()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ErrorColor, contentColor = Color.White)
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = false }) {
                    Text("Cancelar", color = Outline)
                }
            }
        )
    }
}

// TICKET PUBLICATION CARD
@Composable
fun TicketPublicationCard(
    ticket: Ticket,
    isActive: Boolean,
    onToggle: (Boolean) -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var showDeleteConfirm by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = if (isActive) 3.dp else 1.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color(0x0A000000),
                spotColor = Color(0x0A000000)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isActive) SurfaceContainerLowest else BgSurface
        ),
        border = BorderStroke(
            1.dp,
            if (isActive) SurfaceContainerHighest else SurfaceContainerHigh
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // HEADER ROW
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(bottom = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ConfirmationNumber,
                            contentDescription = null,
                            tint = if (isActive) PrimaryGreen else Outline,
                            modifier = Modifier.size(20.dp)
                        )
                        Surface(
                            shape = CircleShape,
                            color = if (isActive) SurfaceContainer else SurfaceContainerHigh
                        ) {
                            Text(
                                text = if (isActive) "Activo" else "Pausado",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isActive) PrimaryGreen else Outline,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = ticket.origen,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isActive) OnSurface else Outline
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = OutlineVariant,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = ticket.destino,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isActive) OnSurface else Outline
                        )
                    }
                }

                Switch(
                    checked = isActive,
                    onCheckedChange = onToggle,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = PrimaryContainer,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = SurfaceContainerHigh,
                        uncheckedBorderColor = OutlineVariant
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // DETAILS ROW
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = null,
                        tint = if (isActive) OnSurfaceVariant else Outline,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "${ticket.fecha}, ${ticket.hora}",
                        fontSize = 14.sp,
                        color = if (isActive) OnSurfaceVariant else Outline
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Payments,
                        contentDescription = null,
                        tint = if (isActive) PrimaryGreen else Outline,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "C$ ${ticket.precio}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isActive) OnSurface else Outline
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = OutlineVariant.copy(alpha = 0.3f))
            Spacer(modifier = Modifier.height(8.dp))

            // FOOTER ROW
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Asiento: ${ticket.asiento.ifBlank { "General" }} · ${ticket.operador.ifBlank { "Autobús" }}",
                    fontSize = 13.sp,
                    color = if (isActive) OnSurfaceVariant else Outline
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Editar",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isActive) PrimaryGreen else Outline,
                        modifier = Modifier
                            .clickable { onEdit() }
                            .padding(4.dp)
                    )

                    IconButton(
                        onClick = { showDeleteConfirm = true },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            tint = ErrorColor.copy(alpha = 0.7f),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }

    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            title = { Text("Eliminar boleto", fontWeight = FontWeight.Bold) },
            text = { Text("¿Deseas eliminar este boleto publicado de la plataforma?") },
            confirmButton = {
                Button(
                    onClick = {
                        showDeleteConfirm = false
                        onDelete()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ErrorColor, contentColor = Color.White)
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = false }) {
                    Text("Cancelar", color = Outline)
                }
            }
        )
    }
}

// STAY PUBLICATION CARD
@Composable
fun StayPublicationCard(
    stay: Stay,
    isActive: Boolean,
    onToggle: (Boolean) -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var showDeleteConfirm by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = if (isActive) 3.dp else 1.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color(0x0A000000),
                spotColor = Color(0x0A000000)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isActive) SurfaceContainerLowest else BgSurface
        ),
        border = BorderStroke(
            1.dp,
            if (isActive) SurfaceContainerHighest else SurfaceContainerHigh
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // HEADER ROW
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(bottom = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = null,
                            tint = if (isActive) PrimaryGreen else Outline,
                            modifier = Modifier.size(20.dp)
                        )
                        Surface(
                            shape = CircleShape,
                            color = if (isActive) SurfaceContainer else SurfaceContainerHigh
                        ) {
                            Text(
                                text = if (isActive) "Activo" else "Pausado",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isActive) PrimaryGreen else Outline,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Text(
                        text = stay.nombre,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isActive) OnSurface else Outline
                    )
                }

                Switch(
                    checked = isActive,
                    onCheckedChange = onToggle,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = PrimaryContainer,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = SurfaceContainerHigh,
                        uncheckedBorderColor = OutlineVariant
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // DETAILS ROW
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "📍 ${stay.municipio}",
                        fontSize = 14.sp,
                        color = if (isActive) OnSurfaceVariant else Outline
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Payments,
                        contentDescription = null,
                        tint = if (isActive) PrimaryGreen else Outline,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "C$ ${stay.precio} / ${stay.per}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isActive) OnSurface else Outline
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = OutlineVariant.copy(alpha = 0.3f))
            Spacer(modifier = Modifier.height(8.dp))

            // FOOTER ROW
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${stay.tipo} · ${stay.habitaciones} hab · ${stay.huespedes} huéspedes",
                    fontSize = 13.sp,
                    color = if (isActive) OnSurfaceVariant else Outline
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Editar",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isActive) PrimaryGreen else Outline,
                        modifier = Modifier
                            .clickable { onEdit() }
                            .padding(4.dp)
                    )

                    IconButton(
                        onClick = { showDeleteConfirm = true },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            tint = ErrorColor.copy(alpha = 0.7f),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }

    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            title = { Text("Eliminar estancia", fontWeight = FontWeight.Bold) },
            text = { Text("¿Deseas eliminar este alojamiento publicado de la plataforma?") },
            confirmButton = {
                Button(
                    onClick = {
                        showDeleteConfirm = false
                        onDelete()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ErrorColor, contentColor = Color.White)
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = false }) {
                    Text("Cancelar", color = Outline)
                }
            }
        )
    }
}

// EMPTY STATE CARD
@Composable
fun EmptyPublicationCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    buttonText: String,
    onAction: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        border = BorderStroke(1.dp, SurfaceContainerHighest)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(SurfaceContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = PrimaryGreen,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = OnSurface
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = subtitle,
                fontSize = 13.sp,
                color = OnSurfaceVariant,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                lineHeight = 18.sp,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onAction,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryContainer,
                    contentColor = OnPrimaryContainer
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = OnPrimaryContainer,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = buttonText,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnPrimaryContainer
                )
            }
        }
    }
}

// PUBLISH TYPE SELECTOR BOTTOM SHEET
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublishTypeSelectorModal(
    onDismiss: () -> Unit,
    onSelectTicket: () -> Unit,
    onSelectRide: () -> Unit,
    onSelectStay: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = SurfaceContainerLowest
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "¿Qué deseas publicar?",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurface
                )
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Cerrar", tint = OnSurfaceVariant)
                }
            }

            Text(
                text = "Selecciona el tipo de servicio que ofrecerás a la comunidad.",
                fontSize = 13.sp,
                color = OnSurfaceVariant,
                modifier = Modifier.padding(top = 2.dp, bottom = 16.dp)
            )

            // OPTION 1: BOLETO
            PublishOptionCard(
                icon = Icons.Default.ConfirmationNumber,
                title = "Boleto de Autobús",
                subtitle = "Vende o transfiere un boleto que no podrás utilizar",
                badge = "Boleto",
                onClick = onSelectTicket
            )

            Spacer(modifier = Modifier.height(10.dp))

            // OPTION 2: RIDE
            PublishOptionCard(
                icon = Icons.Default.DirectionsCar,
                title = "Ride / Viaje Compartido",
                subtitle = "Ofrece asientos en tu vehículo particular y divide gastos",
                badge = "Auto",
                onClick = onSelectRide
            )

            Spacer(modifier = Modifier.height(10.dp))

            // OPTION 3: STAY
            PublishOptionCard(
                icon = Icons.Default.Home,
                title = "Estancia / Hospedaje",
                subtitle = "Publica una habitación, casa, hostal o apartamento",
                badge = "Hospedaje",
                onClick = onSelectStay
            )

            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

@Composable
fun PublishOptionCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    badge: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainer),
        border = BorderStroke(1.dp, SurfaceContainerHighest)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(PrimaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = OnPrimaryContainer, modifier = Modifier.size(24.dp))
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurface
                )
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = OnSurfaceVariant,
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = OutlineVariant,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
