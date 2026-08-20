package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.AirlineSeatReclineNormal
import androidx.compose.material.icons.filled.Bathtub
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.LocalParking
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shower
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import com.example.ui.components.ReviewModalDialog
import com.example.ui.components.SafetyKitModalDialog
import com.example.ui.components.SosEmergencyDialog
import com.example.ui.components.QuickRouteFilterChips
import com.example.ui.viewmodel.ReviewTarget
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Diamond
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.ProviderProfile
import com.example.data.model.Ride
import com.example.data.model.Stay
import com.example.data.model.Ticket
import com.example.data.model.TrustLevel
import com.example.data.model.VerificationState
import com.example.data.model.isProfileComplete
import com.example.ui.theme.InkBlack
import com.example.ui.theme.LimeBrand
import com.example.ui.theme.LimeBrandDark
import com.example.ui.theme.LimeBrandLight
import com.example.ui.theme.LineBorder
import com.example.ui.theme.MutedGray
import com.example.ui.theme.SoftGreenBg
import com.example.ui.theme.SurfaceGray
import com.example.ui.viewmodel.BusDriveViewModel
import com.example.ui.viewmodel.ViajeSubTab

@Composable
fun ViajesScreen(viewModel: BusDriveViewModel) {
    val selectedSubTab by viewModel.selectedViajeSubTab.collectAsState()
    val tickets by viewModel.allTickets.collectAsState()
    val rides by viewModel.allRides.collectAsState()
    val stays by viewModel.allStays.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()
    val providerProfile by viewModel.currentProviderProfile.collectAsState()

    val showPublishTicket by viewModel.showPublishTicketModal.collectAsState()
    val showPublishRide by viewModel.showPublishRideModal.collectAsState()
    val showPublishStay by viewModel.showPublishStayModal.collectAsState()
    val showSolicitarRide by viewModel.showSolicitarRideModal.collectAsState()
    val selectedTicketDetail by viewModel.selectedTicketDetail.collectAsState()
    val selectedRideDetail by viewModel.selectedRideDetail.collectAsState()
    val selectedStayDetail by viewModel.selectedStayDetail.collectAsState()

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F9FF))
    ) {
        // TOP FIXED HEADER
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            shadowElevation = 1.dp,
            border = BorderStroke(1.dp, Color(0xFFE2E8F0))
        ) {
            Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 14.dp, bottom = 0.dp)) {
                Text(
                    text = "Comunidad",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = (-0.5).sp,
                    color = InkBlack
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ViajeSubTabButton(
                        text = "Boletos",
                        isSelected = selectedSubTab == ViajeSubTab.BOLETO,
                        onClick = { viewModel.selectedViajeSubTab.value = ViajeSubTab.BOLETO },
                        modifier = Modifier.weight(1f)
                    )
                    ViajeSubTabButton(
                        text = "Rides",
                        isSelected = selectedSubTab == ViajeSubTab.RIDE,
                        onClick = { viewModel.selectedViajeSubTab.value = ViajeSubTab.RIDE },
                        modifier = Modifier.weight(1f)
                    )
                    ViajeSubTabButton(
                        text = "Estancia",
                        isSelected = selectedSubTab == ViajeSubTab.STAY,
                        onClick = { viewModel.selectedViajeSubTab.value = ViajeSubTab.STAY },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Box(modifier = Modifier.weight(1f)) {
            AnimatedContent(
                targetState = selectedSubTab,
                transitionSpec = {
                    (fadeIn(animationSpec = tween(220, easing = FastOutSlowInEasing))) togetherWith
                            (fadeOut(animationSpec = tween(150, easing = FastOutSlowInEasing)))
                },
                label = "ViajesSubTabTransition"
            ) { subTab ->
                when (subTab) {
                    ViajeSubTab.BOLETO -> {
                        TicketTabContent(
                            tickets = tickets.filter { it.estado == "disponible" },
                            onContactWhatsApp = { tel, o, d ->
                                val cleanNum = tel.replace(Regex("[^0-9]"), "")
                                val uri = Uri.parse("https://wa.me/$cleanNum?text=" + Uri.encode("Hola, vi tu boleto $o → $d en BusDrive. ¿Sigue disponible?"))
                                context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                            },
                            onContactCall = { tel ->
                                val cleanNum = tel.replace(Regex("[^0-9+]"), "")
                                val uri = Uri.parse("tel:$cleanNum")
                                context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                            },
                            onReviewClick = { target -> viewModel.showReviewModal.value = target }
                        )
                    }
                    ViajeSubTab.RIDE -> {
                        RideTabContent(
                            rides = rides,
                            filterOrigen = viewModel.rideFilterOrigen.collectAsState().value,
                            filterDestino = viewModel.rideFilterDestino.collectAsState().value,
                            onFilterOrigenChange = { viewModel.rideFilterOrigen.value = it },
                            onFilterDestinoChange = { viewModel.rideFilterDestino.value = it },
                            onSolicitarClick = { ride ->
                                if (currentUser == null) {
                                    viewModel.openAuthWithAction("Debes iniciar sesión para solicitar un ride.", null)
                                } else {
                                    viewModel.solicitarRideCount.value = 1
                                    viewModel.showSolicitarRideModal.value = ride
                                }
                            },
                            onReviewClick = { target -> viewModel.showReviewModal.value = target }
                        )
                    }
                    ViajeSubTab.STAY -> {
                        StayTabContent(
                            stays = stays.filter { it.estado != "pausado" && it.estado != "no disponible" },
                            filterMuni = viewModel.stayFilterMunicipio.collectAsState().value,
                            onFilterMuniChange = { viewModel.stayFilterMunicipio.value = it },
                            onSelectStay = { stay -> viewModel.selectedStayDetail.value = stay },
                            onReviewClick = { target -> viewModel.showReviewModal.value = target }
                        )
                    }
                }
            }
        }
    }

    // MODAL DIALOGS
    val routesLocationsViajes = viewModel.allRoutes.collectAsState().value.flatMap { listOf(it.origen, it.destino) }.distinct().sorted()
    val locationsListViajes = if (routesLocationsViajes.isNotEmpty()) routesLocationsViajes else listOf("Managua", "Siuna", "Puerto Cabezas (Bilwi)", "Bluefields", "Matagalpa", "Juigalpa", "Estelí", "Waslala", "Rosita", "Bonanza", "Waspam", "Chinandega", "León", "Rivas")

    if (showPublishTicket) {
        PublishTicketDialog(
            currentUser = currentUser,
            profile = providerProfile,
            allLocations = locationsListViajes,
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
            allLocations = locationsListViajes,
            onDismiss = { viewModel.showPublishRideModal.value = false },
            onPublish = { o, d, f, h, v, c, e, p, pe, info, photo, vPhoto ->
                viewModel.publishRide(o, d, f, h, v, c, e, p, pe, info, photo, vPhoto)
            }
        )
    }

    showSolicitarRide?.let { ride ->
        SolicitarRideDialog(
            ride = ride,
            count = viewModel.solicitarRideCount.collectAsState().value,
            onCountChange = { viewModel.solicitarRideCount.value = it },
            onDismiss = { viewModel.showSolicitarRideModal.value = null },
            onConfirm = { count -> viewModel.solicitarRide(ride, count) }
        )
    }

    showPublishStay?.let { editingStay ->
        PublishStayDialog(
            stay = editingStay,
            currentUser = currentUser,
            profile = providerProfile,
            allLocations = locationsListViajes,
            onDismiss = { viewModel.showPublishStayModal.value = null },
            onPublish = { idToEdit, n, t, m, dir, p, per, h, hab, c, b, svcs, desc, wa, tel, photos, photo ->
                viewModel.publishStay(idToEdit, n, t, m, dir, p, per, h, hab, c, b, svcs, desc, wa, tel, photos, photo)
            }
        )
    }

    selectedTicketDetail?.let { ticket ->
        TicketDetailModal(
            ticket = ticket,
            onDismiss = { viewModel.selectedTicketDetail.value = null },
            onContactWhatsApp = { tel, o, d ->
                val cleanNum = tel.replace(Regex("[^0-9]"), "")
                val uri = Uri.parse("https://wa.me/$cleanNum?text=" + Uri.encode("Hola, vi tu boleto $o → $d en BusDrive. ¿Sigue disponible?"))
                context.startActivity(Intent(Intent.ACTION_VIEW, uri))
            },
            onContactCall = { tel ->
                val cleanNum = tel.replace(Regex("[^0-9+]"), "")
                val uri = Uri.parse("tel:$cleanNum")
                context.startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        )
    }

    selectedRideDetail?.let { ride ->
        RideDetailModal(
            ride = ride,
            onDismiss = { viewModel.selectedRideDetail.value = null },
            onSolicitar = {
                viewModel.selectedRideDetail.value = null
                if (currentUser == null) {
                    viewModel.openAuthWithAction("Debes iniciar sesión para solicitar un ride.", null)
                } else {
                    viewModel.solicitarRideCount.value = 1
                    viewModel.showSolicitarRideModal.value = ride
                }
            }
        )
    }

    selectedStayDetail?.let { stay ->
        StayDetailModal(
            stay = stay,
            onDismiss = { viewModel.selectedStayDetail.value = null },
            onContactWhatsApp = { num ->
                val cleanNum = num.replace(Regex("[^0-9]"), "")
                val uri = Uri.parse("https://wa.me/$cleanNum?text=" + Uri.encode("Hola, vi tu estancia \"${stay.nombre}\" en BusDrive. ¿Está disponible?"))
                context.startActivity(Intent(Intent.ACTION_VIEW, uri))
            },
            onContactCall = { tel ->
                val cleanNum = tel.replace(Regex("[^0-9+]"), "")
                val uri = Uri.parse("tel:$cleanNum")
                context.startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        )
    }

    val showReviewModal by viewModel.showReviewModal.collectAsState()
    val showSafetyKitModal by viewModel.showSafetyKitModal.collectAsState()
    val showSosEmergencyDialog by viewModel.showSosEmergencyDialog.collectAsState()

    showReviewModal?.let { target ->
        ReviewModalDialog(
            target = target,
            onDismiss = { viewModel.showReviewModal.value = null },
            onSubmit = { rating, comment, tags ->
                viewModel.submitReview(target, rating, comment, tags)
            }
        )
    }

    showSafetyKitModal?.let { ride ->
        SafetyKitModalDialog(
            ride = ride,
            onDismiss = { viewModel.showSafetyKitModal.value = null },
            onShareTrip = {
                viewModel.shareRideDetails(context, ride)
            },
            onSosClick = {
                viewModel.showSosEmergencyDialog.value = true
            },
            onCallEmergency = { num ->
                viewModel.callEmergencyNumber(context, num)
            }
        )
    }

    if (showSosEmergencyDialog) {
        SosEmergencyDialog(
            onDismiss = { viewModel.showSosEmergencyDialog.value = false },
            onCallEmergency = { num ->
                viewModel.callEmergencyNumber(context, num)
            }
        )
    }
}

@Composable
fun ViajeSubTabButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val textColor by animateColorAsState(
        targetValue = if (isSelected) InkBlack else Color(0xFF94A3B8),
        label = "subtab_text_color"
    )
    val indicatorColor by animateColorAsState(
        targetValue = if (isSelected) LimeBrandDark else Color.Transparent,
        label = "subtab_indicator_color"
    )

    Column(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() }
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            fontSize = 15.sp,
            letterSpacing = 0.2.sp,
            maxLines = 1,
            softWrap = false,
            color = textColor
        )
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .width(if (isSelected) 36.dp else 0.dp)
                .height(3.dp)
                .clip(RoundedCornerShape(1.5.dp))
                .background(indicatorColor)
        )
    }
}

fun formatTicketElapsedTime(timestamp: Long): String {
    val diff = System.currentTimeMillis() - timestamp
    val hours = diff / (1000 * 60 * 60)
    val days = hours / 24
    return when {
        diff < 60 * 1000L -> "Hace un momento"
        diff < 60 * 60 * 1000L -> "Hace ${(diff / (60 * 1000)).coerceAtLeast(1)} min"
        hours < 24 -> "Hace ${hours.coerceAtLeast(1)} h"
        days == 1L -> "Hace 1 d"
        days < 7 -> "Hace $days d"
        else -> "Hace ${days / 7} sem"
    }
}

@Composable
fun TicketTabContent(
    tickets: List<Ticket>,
    onContactWhatsApp: (String, String, String) -> Unit,
    onContactCall: (String) -> Unit,
    onReviewClick: (ReviewTarget) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        if (tickets.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Aún no hay boletos disponibles en este momento.",
                    fontSize = 15.sp,
                    color = MutedGray,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(start = 14.dp, end = 14.dp, top = 12.dp, bottom = 140.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(tickets) { ticket ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                        border = BorderStroke(1.dp, Color(0xFFE5E7EB))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            // 1. HEADER (Avatar + Name) + Elapsed time
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    // Avatar
                                    Box(
                                        modifier = Modifier
                                            .size(46.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFFE5E7EB)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (ticket.providerPhotoUrl.isNotBlank()) {
                                            AsyncImage(
                                                model = ticket.providerPhotoUrl,
                                                contentDescription = ticket.nombreContacto,
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier.fillMaxSize()
                                            )
                                        } else {
                                            Text(
                                                text = ticket.nombreContacto.take(1).uppercase(),
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF374151)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(10.dp))

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.weight(1f, fill = false)
                                    ) {
                                        Text(
                                            text = ticket.nombreContacto,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            color = Color(0xFF111827),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(
                                            imageVector = Icons.Filled.Verified,
                                            contentDescription = "Verificado",
                                            tint = Color(0xFF1877F2),
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                // Elapsed time from publication
                                val elapsedStr = formatTicketElapsedTime(ticket.timestamp)
                                Text(
                                    text = elapsedStr,
                                    fontSize = 12.sp,
                                    color = Color(0xFF6B7280)
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // 2. ROUTE TITLE & PRICE
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${ticket.origen} ➔ ${ticket.destino}",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFF111827),
                                    modifier = Modifier.weight(1f)
                                )

                                if (ticket.precio.isNotBlank()) {
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = Color(0xFFDCFCE7),
                                        modifier = Modifier.padding(start = 8.dp)
                                    ) {
                                        Text(
                                            text = ticket.precio,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp,
                                            color = Color(0xFF15803D),
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // 3. BADGES: DISPONIBLE & ASIENTO
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = Color(0xFFDCFCE7)
                                ) {
                                    Text(
                                        text = "Disponible",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.5.sp,
                                        color = Color(0xFF15803D),
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }

                                if (ticket.asiento.isNotBlank()) {
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = Color(0xFFF1F5F9)
                                    ) {
                                        Text(
                                            text = "Asiento ${ticket.asiento}",
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 11.5.sp,
                                            color = Color(0xFF475569),
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // 4. DETAILS SECTION
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFF8FAFC), RoundedCornerShape(10.dp))
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = "📅", fontSize = 12.sp)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(text = ticket.fecha, fontSize = 13.sp, color = Color(0xFF334155), fontWeight = FontWeight.Medium)
                                    }
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = "🕒", fontSize = 12.sp)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(text = ticket.hora, fontSize = 13.sp, color = Color(0xFF334155), fontWeight = FontWeight.Medium)
                                    }
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = "🚌", fontSize = 12.sp)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = ticket.operador.ifBlank { "Transporte Directo" },
                                        fontSize = 13.sp,
                                        color = Color(0xFF334155),
                                        fontWeight = FontWeight.Medium,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }

                            // 5. MOTIVO / NOTA DEL VIAJERO
                            if (ticket.nota.isNotBlank()) {
                                Spacer(modifier = Modifier.height(10.dp))
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = Color(0xFFF8FAFC),
                                    border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 9.dp),
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Text(text = "📝", fontSize = 13.sp)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Column {
                                            Text(
                                                text = "Motivo / Detalle:",
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF64748B)
                                            )
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(
                                                text = ticket.nota,
                                                fontSize = 13.sp,
                                                color = Color(0xFF334155),
                                                lineHeight = 18.sp
                                            )
                                        }
                                    }
                                }
                            }

                            // NO Social Reactions (likes, comments, share, bookmark removed)

                            Spacer(modifier = Modifier.height(14.dp))

                            // 6. ACTION BUTTONS: WhatsApp & Llamar + Adquirir Offline
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = { onContactWhatsApp(ticket.telefonoContacto, ticket.origen, ticket.destino) },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(44.dp),
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF0F9D58),
                                        contentColor = Color.White
                                    )
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Phone,
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp),
                                            tint = Color.White
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("WhatsApp", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    }
                                }

                                OutlinedButton(
                                    onClick = { onContactCall(ticket.telefonoContacto) },
                                    modifier = Modifier
                                        .weight(0.9f)
                                        .height(44.dp),
                                    shape = RoundedCornerShape(10.dp),
                                    border = BorderStroke(1.5.dp, Color(0xFF0F9D58))
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Call,
                                            contentDescription = null,
                                            tint = Color(0xFF0F9D58),
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Llamar", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF0F9D58))
                                    }
                                }

                                // Rate seller button
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Color(0xFFFFF8E1))
                                        .border(1.dp, Color(0xFFFFD54F), RoundedCornerShape(10.dp))
                                        .clickable {
                                            onReviewClick(
                                                ReviewTarget(
                                                    targetType = "BOLETO",
                                                    targetId = ticket.id,
                                                    targetOwnerId = ticket.ownerId,
                                                    title = "Calificar a ${ticket.nombreContacto}",
                                                    subtitle = "Boleto: ${ticket.origen} → ${ticket.destino}",
                                                    photoUrl = ticket.providerPhotoUrl
                                                )
                                            )
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Calificar Vendedor",
                                        tint = Color(0xFFFFA000),
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RideTabContent(
    rides: List<Ride>,
    filterOrigen: String,
    filterDestino: String,
    onFilterOrigenChange: (String) -> Unit,
    onFilterDestinoChange: (String) -> Unit,
    onSolicitarClick: (Ride) -> Unit,
    onReviewClick: (ReviewTarget) -> Unit
) {
    val filteredRides = remember(rides, filterOrigen, filterDestino) {
        rides.filter { r ->
            (filterOrigen.isBlank() || r.origen.equals(filterOrigen, ignoreCase = true)) &&
            (filterDestino.isBlank() || r.destino.equals(filterDestino, ignoreCase = true))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F9FF))
    ) {
        if (filteredRides.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "Rides",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF181C20)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Viajes compartidos en auto particular por miembros de la comunidad",
                    fontSize = 14.sp,
                    color = Color(0xFF3E4946)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "🚗",
                    fontSize = 48.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "No hay rides disponibles en este momento.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF181C20),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Sé el primero en ofrecer un viaje compartido y gana dinero extra.",
                    fontSize = 14.sp,
                    color = Color(0xFF6E7976),
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        onFilterOrigenChange("")
                        onFilterDestinoChange("")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LimeBrand,
                        contentColor = InkBlack
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Ver todos los rides", fontWeight = FontWeight.Bold)
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 140.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp)
                    ) {
                        Text(
                            text = "Rides",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF181C20)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Viajes compartidos en auto particular por miembros de la comunidad",
                            fontSize = 14.sp,
                            color = Color(0xFF3E4946)
                        )
                    }
                }
                items(filteredRides) { ride ->
                    RideCard(
                        ride = ride,
                        onSolicitarClick = onSolicitarClick,
                        onReviewClick = onReviewClick
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
    }
}

@Composable
fun RideCard(
    ride: Ride,
    onSolicitarClick: (Ride) -> Unit,
    onReviewClick: (ReviewTarget) -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, Color(0xFFBEC9C5).copy(alpha = 0.8f))
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Driver Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF1F4FA))
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Driver Avatar
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color(0xFF006B5F), CircleShape)
                            .background(Color(0xFFE2E8F0)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (ride.providerPhotoUrl.isNotBlank()) {
                            AsyncImage(
                                model = ride.providerPhotoUrl,
                                contentDescription = ride.driverName,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            Text(
                                text = ride.driverName.take(1).uppercase(),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF181C20)
                            )
                        }
                    }

                    // Driver Name & Verified badge
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = ride.driverName,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF181C20)
                        )
                        Icon(
                            imageVector = Icons.Filled.Verified,
                            contentDescription = "Verificado",
                            tint = Color(0xFF0061A4),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            HorizontalDivider(color = Color(0xFFBEC9C5).copy(alpha = 0.3f), thickness = 1.dp)

            // Card Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Top Row: Status badge & Price
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Status Badge
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFA0F399), RoundedCornerShape(50))
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = when (ride.estado) {
                                "pendiente" -> "Pendiente"
                                "confirmado" -> "Confirmado"
                                else -> "Disponible"
                            },
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF217128),
                            letterSpacing = 0.5.sp
                        )
                    }

                    // Price with payments icon
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Payments,
                            contentDescription = null,
                            tint = Color(0xFF005147),
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = if (ride.precioPorPersona.startsWith("C$")) ride.precioPorPersona else "C$${ride.precioPorPersona}",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF005147),
                            letterSpacing = 0.5.sp
                        )
                    }
                }

                // Route (Origin ➔ Destination)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = ride.origen,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF181C20)
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = Color(0xFF6E7976),
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = ride.destino,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF181C20)
                    )
                }

                // Details Grid (2 columns)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Row 1: Date & Time
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CalendarToday,
                                contentDescription = null,
                                tint = Color(0xFF6E7976),
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = ride.fecha,
                                fontSize = 14.sp,
                                color = Color(0xFF3E4946)
                            )
                        }

                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Schedule,
                                contentDescription = null,
                                tint = Color(0xFF6E7976),
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = ride.hora,
                                fontSize = 14.sp,
                                color = Color(0xFF3E4946)
                            )
                        }
                    }

                    // Row 2: Vehicle
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.DirectionsCar,
                            contentDescription = null,
                            tint = Color(0xFF6E7976),
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "${ride.vehiculo} ${if (ride.color.isNotBlank()) "(${ride.color})" else ""}".trim(),
                            fontSize = 14.sp,
                            color = Color(0xFF3E4946)
                        )
                    }

                    // Row 3: Seats
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AirlineSeatReclineNormal,
                            contentDescription = null,
                            tint = Color(0xFF6E7976),
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "${ride.espaciosDisponibles} espacio(s)",
                            fontSize = 14.sp,
                            color = Color(0xFF3E4946)
                        )
                    }
                }

                // Meeting Point Box
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF1F4FA), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = Color(0xFF005147),
                        modifier = Modifier
                            .padding(top = 1.dp)
                            .size(18.dp)
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = Color(0xFF181C20))) {
                                append("Punto de encuentro: ")
                            }
                            withStyle(SpanStyle(color = Color(0xFF181C20))) {
                                append(ride.puntoEncuentro.ifBlank { "Metrocentro, Managua" })
                            }
                        },
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                }

                // Driver Note (Italic quote with left border accent)
                if (ride.infoAdicional.isNotBlank()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .width(2.5.dp)
                                .height(20.dp)
                                .background(Color(0xFFBEC9C5), RoundedCornerShape(1.dp))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "\"${ride.infoAdicional}\"",
                            fontStyle = FontStyle.Italic,
                            fontSize = 14.sp,
                            color = Color(0xFF3E4946)
                        )
                    }
                }

                // Action Footer Row: "Solicitar Espacio" + "Calificar"
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { onSolicitarClick(ride) },
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LimeBrand,
                            contentColor = InkBlack
                        )
                    ) {
                        Text(
                            text = "Solicitar espacio",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }

                    // Rating button
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFFFF8E1))
                            .border(1.dp, Color(0xFFFFD54F), RoundedCornerShape(10.dp))
                            .clickable {
                                onReviewClick(
                                    ReviewTarget(
                                        targetType = "RIDE",
                                        targetId = ride.id,
                                        targetOwnerId = ride.ownerId,
                                        title = "Calificar a ${ride.driverName}",
                                        subtitle = "Ruta: ${ride.origen} → ${ride.destino}",
                                        photoUrl = ride.providerPhotoUrl
                                    )
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Calificar Conductor",
                            tint = Color(0xFFFFA000),
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StayTabContent(
    stays: List<Stay>,
    filterMuni: String,
    onFilterMuniChange: (String) -> Unit,
    onSelectStay: (Stay) -> Unit,
    onReviewClick: (ReviewTarget) -> Unit = {}
) {
    var searchMuniInput by remember(filterMuni) { mutableStateOf(filterMuni) }
    var showFilterModal by remember { mutableStateOf(false) }
    var selectedSortOption by remember { mutableStateOf("Recomendados") }
    var showSortMenu by remember { mutableStateOf(false) }
    var selectedDatesText by remember { mutableStateOf("12 Oct - 15 Oct") }
    var guestCountFilter by remember { mutableIntStateOf(2) }

    val filteredStays = remember(stays, filterMuni, selectedSortOption) {
        val baseList = if (filterMuni.isBlank()) stays
        else stays.filter {
            it.municipio.contains(filterMuni.trim(), ignoreCase = true) ||
            it.comunidad.contains(filterMuni.trim(), ignoreCase = true) ||
            it.nombre.contains(filterMuni.trim(), ignoreCase = true)
        }

        when (selectedSortOption) {
            "Menor precio" -> baseList.sortedBy { it.precio }
            "Mayor precio" -> baseList.sortedByDescending { it.precio }
            "Mejor valorados" -> baseList.sortedByDescending { it.rating }
            else -> baseList
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 140.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // TOP SEARCH PILL CARD (Matching Screenshot / Mockup)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showFilterModal = true },
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                border = BorderStroke(1.dp, LineBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = InkBlack,
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (filterMuni.isNotBlank()) filterMuni else "Puerto Cabezas",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = InkBlack
                        )
                        Text(
                            text = "$selectedDatesText • $guestCountFilter Huéspedes",
                            fontSize = 13.sp,
                            color = Color(0xFF6B7280),
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, LineBorder, RoundedCornerShape(12.dp))
                            .clickable { showFilterModal = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = "Filtros",
                            tint = InkBlack,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        // QUICK FILTER CHIPS (PRECIO, TIPO DE LUGAR, RECÁMARAS, SERVICIOS, MÁS FILTROS)
        item {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 2.dp)
            ) {
                item {
                    StayFilterChip(
                        label = "Precio",
                        hasDropdown = true,
                        onClick = { showFilterModal = true }
                    )
                }
                item {
                    StayFilterChip(
                        label = "Tipo de lugar",
                        hasDropdown = false,
                        onClick = { showFilterModal = true }
                    )
                }
                item {
                    StayFilterChip(
                        label = "Recámaras",
                        hasDropdown = false,
                        onClick = { showFilterModal = true }
                    )
                }
                item {
                    StayFilterChip(
                        label = "Servicios",
                        hasDropdown = false,
                        onClick = { showFilterModal = true }
                    )
                }
                item {
                    StayFilterChip(
                        label = "Más filtros",
                        icon = Icons.Default.Tune,
                        onClick = { showFilterModal = true }
                    )
                }
            }
        }

        // SUB-HEADER ROW: COUNT OF ALOJAMIENTOS + RECOMENDADOS DROPDOWN
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${filteredStays.size} Alojamientos",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = InkBlack
                )

                Box {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { showSortMenu = true }
                            .padding(horizontal = 6.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedSortOption,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF6B7280)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Ordenar",
                            tint = Color(0xFF6B7280),
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = showSortMenu,
                        onDismissRequest = { showSortMenu = false }
                    ) {
                        for (option in listOf("Recomendados", "Menor precio", "Mayor precio", "Mejor valorados")) {
                            DropdownMenuItem(
                                text = { Text(option, fontWeight = if (option == selectedSortOption) FontWeight.Bold else FontWeight.Normal) },
                                onClick = {
                                    selectedSortOption = option
                                    showSortMenu = false
                                }
                            )
                        }
                    }
                }
            }
        }

        // STAYS LIST CARDS
        if (filteredStays.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No se encontraron estancias con los filtros actuales.\nPrueba buscando otro municipio o limpia el filtro.",
                        fontSize = 15.sp,
                        color = MutedGray,
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp
                    )
                }
            }
        } else {
            items(filteredStays) { stay ->
                val imageUrl = remember(stay.photosJson) {
                    val firstPhoto = stay.photosJson.split("|").firstOrNull { it.isNotBlank() }
                    if (!firstPhoto.isNullOrBlank() && (firstPhoto.startsWith("http://") || firstPhoto.startsWith("https://"))) {
                        firstPhoto
                    } else if (stay.id % 2L == 0L) {
                        "https://images.unsplash.com/photo-1590490360182-c33d57733427?w=800"
                    } else {
                        "https://images.unsplash.com/photo-1582719478250-c89cae4dc85b?w=800"
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectStay(stay) },
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    border = BorderStroke(1.dp, LineBorder)
                ) {
                    Column {
                        // TOP HOST STRIP (Matching HTML Card Header)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, LineBorder, CircleShape)
                                    .background(SurfaceGray),
                                contentAlignment = Alignment.Center
                            ) {
                                if (stay.providerPhotoUrl.isNotBlank()) {
                                    AsyncImage(
                                        model = stay.providerPhotoUrl,
                                        contentDescription = stay.hostName,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                } else {
                                    Text(
                                        text = (stay.hostName.ifBlank { "Host" }).take(2).uppercase(),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = InkBlack
                                    )
                                }
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = stay.hostName.ifBlank { "Anfitrión" },
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = InkBlack
                                )
                                if (stay.verificado) {
                                    Icon(
                                        imageVector = Icons.Filled.Verified,
                                        contentDescription = "Verificado",
                                        tint = Color(0xFF1877F2),
                                        modifier = Modifier.size(15.dp)
                                    )
                                }
                            }
                        }

                        HorizontalDivider(color = LineBorder, thickness = 1.dp)

                        // HERO PHOTO CONTAINER WITH PAGINATION DOTS
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(190.dp)
                        ) {
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = stay.nombre,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )

                            // Pagination dots at bottom center
                            Row(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(bottom = 10.dp),
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Box(modifier = Modifier.size(7.dp).clip(CircleShape).background(Color.White))
                                Box(modifier = Modifier.size(7.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.5f)))
                                Box(modifier = Modifier.size(7.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.5f)))
                            }
                        }

                        // DETAILS BELOW PHOTO
                        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)) {
                            // ROW 1: TITLE & RATING
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stay.nombre.ifBlank { "Casa Caribe" },
                                    fontSize = 19.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = InkBlack,
                                    modifier = Modifier.weight(1f, fill = false),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Surface(
                                    color = Color(0xFFF9FAFB),
                                    shape = RoundedCornerShape(8.dp),
                                    border = BorderStroke(1.dp, Color(0xFFE5E7EB))
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = "Rating",
                                            tint = Color(0xFFF59E0B),
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(3.dp))
                                        Text(
                                            text = "${stay.rating}",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = InkBlack
                                        )
                                        Text(
                                            text = " (${stay.reviewsCount})",
                                            fontSize = 12.sp,
                                            color = Color(0xFF9CA3AF),
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            // ROW 2: SHORT DESCRIPTION
                            Text(
                                text = if (stay.descripcion.isNotBlank()) stay.descripcion.lines().first()
                                else "Exclusiva propiedad con diseño moderno, espacios amplios y confort de vanguardia.",
                                fontSize = 13.sp,
                                color = Color(0xFF6B7280),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                lineHeight = 18.sp
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // ROW 3: AMENITIES CHIPS (WI-FI, A/C, PARQUEO, ETC)
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                StayChipItem(icon = Icons.Default.Wifi, label = "Wi-Fi")
                                if (stay.serviciosJson.contains("Aire", ignoreCase = true) || stay.serviciosJson.isBlank()) {
                                    StayChipItem(icon = Icons.Default.AcUnit, label = "A/C")
                                }
                                if (stay.serviciosJson.contains("Estacionamiento", ignoreCase = true) || stay.serviciosJson.isBlank()) {
                                    StayChipItem(icon = Icons.Default.LocalParking, label = "Parqueo")
                                } else if (stay.serviciosJson.contains("Cocina", ignoreCase = true)) {
                                    StayChipItem(icon = Icons.Default.Kitchen, label = "Cocina")
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            HorizontalDivider(color = LineBorder, thickness = 1.dp)

                            Spacer(modifier = Modifier.height(12.dp))

                            // ROW 4: PRICE & "VER DETALLES" BUTTON
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.Bottom) {
                                    Text(
                                        text = "C$ ${stay.precio}",
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = InkBlack
                                    )
                                    Text(
                                        text = " / noche",
                                        fontSize = 14.sp,
                                        color = Color(0xFF6B7280),
                                        modifier = Modifier.padding(bottom = 2.dp)
                                    )
                                }

                                Button(
                                    onClick = { onSelectStay(stay) },
                                    modifier = Modifier.height(42.dp),
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = LimeBrand, contentColor = InkBlack),
                                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp)
                                ) {
                                    Text(
                                        text = "Ver detalles",
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // SEARCH & FILTER MODAL DIALOG
    if (showFilterModal) {
        StaySearchFilterDialog(
            currentMuni = filterMuni,
            currentDates = selectedDatesText,
            currentGuests = guestCountFilter,
            onDismiss = { showFilterModal = false },
            onApply = { newMuni, newDates, newGuests ->
                searchMuniInput = newMuni
                onFilterMuniChange(newMuni)
                selectedDatesText = newDates
                guestCountFilter = newGuests
                showFilterModal = false
            }
        )
    }
}

@Composable
fun StayChipItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFFF9FAFB),
        border = BorderStroke(1.dp, Color(0xFFE5E7EB))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF6B7280),
                modifier = Modifier.size(14.dp)
            )
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color(0xFF4B5563),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun StayFilterChip(
    label: String,
    hasDropdown: Boolean = false,
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        border = BorderStroke(1.dp, LineBorder)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = InkBlack,
                    modifier = Modifier.size(15.dp)
                )
            }
            Text(
                text = label,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = InkBlack
            )
            if (hasDropdown) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color(0xFF6B7280),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StaySearchFilterDialog(
    currentMuni: String,
    currentDates: String,
    currentGuests: Int,
    onDismiss: () -> Unit,
    onApply: (String, String, Int) -> Unit
) {
    var muni by remember { mutableStateOf(currentMuni) }
    var dates by remember { mutableStateOf(currentDates) }
    var guests by remember { mutableIntStateOf(currentGuests) }

    val locations = listOf("Puerto Cabezas (Bilwi)", "Managua", "Bluefields", "Siuna", "Matagalpa", "Estelí", "Waslala", "Rosita", "Bonanza", "Waspam", "Chinandega", "León", "Rivas")

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Buscar estancia",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = InkBlack
                )
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Cerrar")
                }
            }

            // LOCATION INPUT
            Column {
                Text("DESTINO / MUNICIPIO", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6B7280))
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = muni,
                    onValueChange = { muni = it },
                    placeholder = { Text("Ej. Puerto Cabezas, Managua...") },
                    leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = LimeBrandDark) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = LimeBrandDark, unfocusedBorderColor = LineBorder)
                )
            }

            // POPULAR LOCATIONS QUICK SELECT
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(locations) { loc ->
                    val isSelected = muni.equals(loc, ignoreCase = true)
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSelected) LimeBrand else SurfaceGray,
                        border = BorderStroke(1.dp, if (isSelected) LimeBrandDark else LineBorder),
                        modifier = Modifier.clickable { muni = loc }
                    ) {
                        Text(
                            text = loc,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                            color = InkBlack,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            // DATES INPUT
            Column {
                Text("FECHAS DE ESTADÍA", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6B7280))
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = dates,
                    onValueChange = { dates = it },
                    placeholder = { Text("Ej. 12 Oct - 15 Oct") },
                    leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null, tint = LimeBrandDark) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = LimeBrandDark, unfocusedBorderColor = LineBorder)
                )
            }

            // GUESTS SELECTOR
            Column {
                Text("CANTIDAD DE HUÉSPEDES", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6B7280))
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceGray)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("$guests Huéspedes", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = InkBlack)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { if (guests > 1) guests-- }) {
                            Text("−", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                        IconButton(onClick = { if (guests < 10) guests++ }) {
                            Text("+", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { onApply(muni.trim(), dates.trim(), guests) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = LimeBrand, contentColor = InkBlack)
            ) {
                Text("Aplicar filtros", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

// PUBLISH TICKET DIALOG
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublishTicketDialog(
    currentUser: com.example.data.model.UserProfile?,
    profile: ProviderProfile,
    allLocations: List<String> = listOf("Managua", "Siuna", "Puerto Cabezas (Bilwi)", "Bluefields", "Matagalpa", "Juigalpa", "Estelí", "Waslala", "Rosita", "Bonanza", "Waspam", "Chinandega", "León", "Rivas"),
    onDismiss: () -> Unit,
    onPublish: (String, String, String, String, String, String, String, String, String, String, String) -> Unit
) {
    val hostName = profile.fullName.ifBlank { currentUser?.name ?: "Yader Castellón" }
    val telefonoHost = profile.phone.ifBlank { currentUser?.phone ?: "" }
    val userPhotoUrl = profile.profilePhotoUrl.ifBlank { currentUser?.photoUrl ?: "" }
    val userInitial = hostName.firstOrNull()?.uppercase() ?: "U"

    var fotoUrl by remember { mutableStateOf("") }
    var origen by remember { mutableStateOf("Managua") }
    var destino by remember { mutableStateOf("Siuna") }
    var fecha by remember { mutableStateOf("20/11/2023") }
    var hora by remember { mutableStateOf("8:00 a.m.") }
    var operador by remember { mutableStateOf("") }
    var asiento by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var nota by remember { mutableStateOf("") }
    var validationError by remember { mutableStateOf("") }

    var expandedOrigen by remember { mutableStateOf(false) }
    var expandedDestino by remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { fotoUrl = it.toString() }
    }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = SurfaceGray
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // TOP BAR WITH BACK ARROW & TITLE
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Volver",
                        tint = InkBlack
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Publicar Boleto",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = InkBlack
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // USER VERIFIED CARD
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
                            .border(2.dp, LimeBrand, CircleShape)
                            .background(LimeBrand.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (userPhotoUrl.isNotBlank()) {
                            AsyncImage(
                                model = userPhotoUrl,
                                contentDescription = hostName,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            Text(
                                text = userInitial,
                                color = InkBlack,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 20.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {
                        Text(
                            text = "PUBLICANDO COMO",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6B7280),
                            letterSpacing = 0.5.sp
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = hostName,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = InkBlack
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Verificado",
                                tint = Color(0xFF84CC16),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Text(
                            text = "Cuenta verificada",
                            fontSize = 12.sp,
                            color = Color(0xFF6B7280)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // MAIN FORM CONTAINER CARD
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
                    // ROW 1: ORIGEN & DESTINO
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // ORIGEN
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "ORIGEN",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF6B7280),
                                letterSpacing = 0.5.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            ExposedDropdownMenuBox(
                                expanded = expandedOrigen,
                                onExpandedChange = { expandedOrigen = !expandedOrigen }
                            ) {
                                OutlinedTextField(
                                    value = origen,
                                    onValueChange = { origen = it; validationError = "" },
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedOrigen) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .menuAnchor(),
                                    singleLine = true,
                                    shape = RoundedCornerShape(12.dp),
                                    textStyle = androidx.compose.ui.text.TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = InkBlack
                                    ),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = LimeBrandDark,
                                        unfocusedBorderColor = LineBorder
                                    )
                                )
                                ExposedDropdownMenu(
                                    expanded = expandedOrigen,
                                    onDismissRequest = { expandedOrigen = false }
                                ) {
                                    allLocations.forEach { loc ->
                                        DropdownMenuItem(
                                            text = { Text(loc, fontWeight = FontWeight.Bold, fontSize = 14.sp) },
                                            onClick = {
                                                origen = loc
                                                expandedOrigen = false
                                                validationError = ""
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        // DESTINO
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "DESTINO",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF6B7280),
                                letterSpacing = 0.5.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            ExposedDropdownMenuBox(
                                expanded = expandedDestino,
                                onExpandedChange = { expandedDestino = !expandedDestino }
                            ) {
                                OutlinedTextField(
                                    value = destino,
                                    onValueChange = { destino = it; validationError = "" },
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDestino) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .menuAnchor(),
                                    singleLine = true,
                                    shape = RoundedCornerShape(12.dp),
                                    textStyle = androidx.compose.ui.text.TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = InkBlack
                                    ),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = LimeBrandDark,
                                        unfocusedBorderColor = LineBorder
                                    )
                                )
                                ExposedDropdownMenu(
                                    expanded = expandedDestino,
                                    onDismissRequest = { expandedDestino = false }
                                ) {
                                    allLocations.forEach { loc ->
                                        DropdownMenuItem(
                                            text = { Text(loc, fontWeight = FontWeight.Bold, fontSize = 14.sp) },
                                            onClick = {
                                                destino = loc
                                                expandedDestino = false
                                                validationError = ""
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // ROW 2: FECHA & HORA
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "FECHA",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF6B7280),
                                letterSpacing = 0.5.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            OutlinedTextField(
                                value = fecha,
                                onValueChange = { fecha = it; validationError = "" },
                                placeholder = { Text("20/11/2023", color = MutedGray) },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                textStyle = androidx.compose.ui.text.TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = InkBlack
                                ),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = LimeBrandDark,
                                    unfocusedBorderColor = LineBorder
                                )
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "HORA",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF6B7280),
                                letterSpacing = 0.5.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            OutlinedTextField(
                                value = hora,
                                onValueChange = { hora = it },
                                placeholder = { Text("8:00 a.m.", color = MutedGray) },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                textStyle = androidx.compose.ui.text.TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = InkBlack
                                ),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = LimeBrandDark,
                                    unfocusedBorderColor = LineBorder
                                )
                            )
                        }
                    }

                    // ROW 3: TRANSPORTE (OPCIONAL)
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "TRANSPORTE (OPCIONAL)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF6B7280),
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            value = operador,
                            onValueChange = { operador = it },
                            placeholder = { Text("Ej. Expreso, Transporte de pasajeros", color = MutedGray, fontSize = 14.sp) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = LimeBrandDark,
                                unfocusedBorderColor = LineBorder
                            )
                        )
                    }

                    // ROW 4: N° ASIENTO * & PRECIO (OPC)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "N° ASIENTO *",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF6B7280),
                                letterSpacing = 0.5.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            OutlinedTextField(
                                value = asiento,
                                onValueChange = { asiento = it; validationError = "" },
                                placeholder = { Text("Obligatorio", color = MutedGray, fontSize = 14.sp) },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                textStyle = androidx.compose.ui.text.TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = InkBlack
                                ),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = LimeBrandDark,
                                    unfocusedBorderColor = LineBorder
                                )
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "PRECIO (OPC)",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF6B7280),
                                letterSpacing = 0.5.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            OutlinedTextField(
                                value = precio,
                                onValueChange = { precio = it },
                                placeholder = { Text("C$", color = MutedGray, fontSize = 14.sp) },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                textStyle = androidx.compose.ui.text.TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = InkBlack
                                ),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = LimeBrandDark,
                                    unfocusedBorderColor = LineBorder
                                )
                            )
                        }
                    }

                    // ROW 5: DETALLES / MOTIVO (OPCIONAL)
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "MOTIVO O DETALLES DEL BOLETO (OPCIONAL)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF6B7280),
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            value = nota,
                            onValueChange = { nota = it },
                            placeholder = { Text("Ej. Asiento de ventana, disponible por cambio de fecha, entrega en terminal...", color = MutedGray, fontSize = 14.sp) },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 2,
                            maxLines = 4,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = LimeBrandDark,
                                unfocusedBorderColor = LineBorder
                            )
                        )
                    }

                    // FOTO SELECTOR (OPCIONAL)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceGray)
                            .padding(10.dp)
                    ) {
                        Text(text = "FOTO DEL BOLETO (OPCIONAL)", fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF6B7280))
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            OutlinedButton(
                                onClick = { imagePickerLauncher.launch("image/*") },
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(imageVector = Icons.Default.AddAPhoto, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = "Elegir foto", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            if (fotoUrl.isNotBlank()) {
                                Text(text = "✓ Foto lista", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF16A34A))
                            }
                        }
                        if (fotoUrl.isNotBlank()) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(90.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            ) {
                                AsyncImage(
                                    model = fotoUrl,
                                    contentDescription = "Boleto",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }

                    if (validationError.isNotBlank()) {
                        Text(text = validationError, color = Color.Red, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // PUBLICAR BOLETO BUTTON
                    Button(
                        onClick = {
                            if (origen.isBlank() || destino.isBlank() || asiento.isBlank() || fecha.isBlank()) {
                                validationError = "⚠️ Es obligatorio ingresar Origen, Destino, Fecha y N° de Asiento."
                            } else {
                                onPublish(hostName, telefonoHost, origen, destino, fecha, hora, operador, asiento, precio, nota, userPhotoUrl)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = LimeBrand, contentColor = InkBlack)
                    ) {
                        Text("Publicar Boleto", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

// PUBLISH RIDE DIALOG
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublishRideDialog(
    profile: ProviderProfile,
    allLocations: List<String>,
    onDismiss: () -> Unit,
    onPublish: (String, String, String, String, String, String, Int, String, String, String, String, String) -> Unit,
    currentUser: com.example.data.model.UserProfile? = null
) {
    val conductorNombre = profile.fullName.ifBlank { currentUser?.name ?: "Yader Castellón" }
    val telefono = profile.phone.ifBlank { currentUser?.phone ?: "" }
    val userPhotoUrl = profile.profilePhotoUrl.ifBlank { currentUser?.photoUrl ?: "" }
    val userInitial = conductorNombre.firstOrNull()?.uppercase() ?: "C"

    var fotoVehiculoUrl by remember { mutableStateOf("") }
    var origen by remember { mutableStateOf(if (allLocations.isNotEmpty()) allLocations.first() else "Managua") }
    var destino by remember { mutableStateOf(if (allLocations.size > 1) allLocations[1] else "Siuna") }
    var fecha by remember { mutableStateOf("20/11/2023") }
    var hora by remember { mutableStateOf("6:00 a.m.") }
    var vehiculo by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    var espacios by remember { mutableIntStateOf(3) }
    var precio by remember { mutableStateOf("") }
    var puntoEncuentro by remember { mutableStateOf("") }
    var info by remember { mutableStateOf("") }
    var validationError by remember { mutableStateOf("") }

    var expandedOrigen by remember { mutableStateOf(false) }
    var expandedDestino by remember { mutableStateOf(false) }

    val vehiclePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { fotoVehiculoUrl = it.toString() }
    }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = SurfaceGray
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // TOP BAR WITH BACK ARROW & TITLE
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Volver",
                        tint = InkBlack
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Publicar Ride",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = InkBlack
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // USER VERIFIED CARD
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
                            .border(2.dp, LimeBrand, CircleShape)
                            .background(LimeBrand.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (userPhotoUrl.isNotBlank()) {
                            AsyncImage(
                                model = userPhotoUrl,
                                contentDescription = conductorNombre,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            Text(
                                text = userInitial,
                                color = InkBlack,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 20.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {
                        Text(
                            text = "PUBLICANDO COMO",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6B7280),
                            letterSpacing = 0.5.sp
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = conductorNombre,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = InkBlack
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Verificado",
                                tint = Color(0xFF84CC16),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Text(
                            text = "Cuenta verificada",
                            fontSize = 12.sp,
                            color = Color(0xFF6B7280)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // MAIN FORM CONTAINER CARD
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
                    // ROW 1: ORIGEN & DESTINO
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // ORIGEN
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "ORIGEN",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF6B7280),
                                letterSpacing = 0.5.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            ExposedDropdownMenuBox(
                                expanded = expandedOrigen,
                                onExpandedChange = { expandedOrigen = !expandedOrigen }
                            ) {
                                OutlinedTextField(
                                    value = origen,
                                    onValueChange = { origen = it; validationError = "" },
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedOrigen) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .menuAnchor(),
                                    singleLine = true,
                                    shape = RoundedCornerShape(12.dp),
                                    textStyle = androidx.compose.ui.text.TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = InkBlack
                                    ),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = LimeBrandDark,
                                        unfocusedBorderColor = LineBorder
                                    )
                                )
                                ExposedDropdownMenu(
                                    expanded = expandedOrigen,
                                    onDismissRequest = { expandedOrigen = false }
                                ) {
                                    allLocations.forEach { loc ->
                                        DropdownMenuItem(
                                            text = { Text(loc, fontWeight = FontWeight.Bold, fontSize = 14.sp) },
                                            onClick = {
                                                origen = loc
                                                expandedOrigen = false
                                                validationError = ""
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        // DESTINO
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "DESTINO",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF6B7280),
                                letterSpacing = 0.5.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            ExposedDropdownMenuBox(
                                expanded = expandedDestino,
                                onExpandedChange = { expandedDestino = !expandedDestino }
                            ) {
                                OutlinedTextField(
                                    value = destino,
                                    onValueChange = { destino = it; validationError = "" },
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDestino) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .menuAnchor(),
                                    singleLine = true,
                                    shape = RoundedCornerShape(12.dp),
                                    textStyle = androidx.compose.ui.text.TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = InkBlack
                                    ),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = LimeBrandDark,
                                        unfocusedBorderColor = LineBorder
                                    )
                                )
                                ExposedDropdownMenu(
                                    expanded = expandedDestino,
                                    onDismissRequest = { expandedDestino = false }
                                ) {
                                    allLocations.forEach { loc ->
                                        DropdownMenuItem(
                                            text = { Text(loc, fontWeight = FontWeight.Bold, fontSize = 14.sp) },
                                            onClick = {
                                                destino = loc
                                                expandedDestino = false
                                                validationError = ""
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // ROW 2: FECHA & HORA
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "FECHA",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF6B7280),
                                letterSpacing = 0.5.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            OutlinedTextField(
                                value = fecha,
                                onValueChange = { fecha = it; validationError = "" },
                                placeholder = { Text("20/11/2023", color = MutedGray) },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                textStyle = androidx.compose.ui.text.TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = InkBlack
                                ),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = LimeBrandDark,
                                    unfocusedBorderColor = LineBorder
                                )
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "HORA",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF6B7280),
                                letterSpacing = 0.5.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            OutlinedTextField(
                                value = hora,
                                onValueChange = { hora = it },
                                placeholder = { Text("6:00 a.m.", color = MutedGray) },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                textStyle = androidx.compose.ui.text.TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = InkBlack
                                ),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = LimeBrandDark,
                                    unfocusedBorderColor = LineBorder
                                )
                            )
                        }
                    }

                    // ROW 3: VEHÍCULO (OPCIONAL)
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "VEHÍCULO (OPCIONAL)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF6B7280),
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            value = vehiculo,
                            onValueChange = { vehiculo = it },
                            placeholder = { Text("Ej. Toyota Corolla, Suzuki Swift", color = MutedGray, fontSize = 14.sp) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = LimeBrandDark,
                                unfocusedBorderColor = LineBorder
                            )
                        )
                    }

                    // ROW 4: ESPACIOS DISPONIBLES * & PRECIO (OPC)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "ESPACIOS *",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF6B7280),
                                letterSpacing = 0.5.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(SurfaceGray)
                                    .border(1.dp, LineBorder, RoundedCornerShape(12.dp))
                                    .padding(horizontal = 4.dp, vertical = 2.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                IconButton(
                                    onClick = { if (espacios > 1) espacios-- },
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Text("−", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = InkBlack)
                                }
                                Text(
                                    text = "$espacios asientos",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 13.sp,
                                    color = InkBlack
                                )
                                IconButton(
                                    onClick = { if (espacios < 8) espacios++ },
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Text("+", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = InkBlack)
                                }
                            }
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "PRECIO / PERSONA",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF6B7280),
                                letterSpacing = 0.5.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            OutlinedTextField(
                                value = precio,
                                onValueChange = { precio = it },
                                placeholder = { Text("C$", color = MutedGray, fontSize = 14.sp) },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                textStyle = androidx.compose.ui.text.TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = InkBlack
                                ),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = LimeBrandDark,
                                    unfocusedBorderColor = LineBorder
                                )
                            )
                        }
                    }

                    // ROW 5: PUNTO DE ENCUENTRO
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "PUNTO DE ENCUENTRO",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF6B7280),
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            value = puntoEncuentro,
                            onValueChange = { puntoEncuentro = it },
                            placeholder = { Text("Ej. Gasolinera Puma Metrocentro, Terminal...", color = MutedGray, fontSize = 14.sp) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = LimeBrandDark,
                                unfocusedBorderColor = LineBorder
                            )
                        )
                    }

                    // ROW 6: NOTA / INFO ADICIONAL
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "INFORMACIÓN ADICIONAL",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF6B7280),
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            value = info,
                            onValueChange = { info = it },
                            placeholder = { Text("Equipaje permitido, paradas en el camino...", color = MutedGray, fontSize = 14.sp) },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 2,
                            maxLines = 4,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = LimeBrandDark,
                                unfocusedBorderColor = LineBorder
                            )
                        )
                    }

                    // FOTO VEHÍCULO (OPCIONAL)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceGray)
                            .padding(10.dp)
                    ) {
                        Text(text = "FOTO DEL VEHÍCULO (OPCIONAL)", fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF6B7280))
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            OutlinedButton(
                                onClick = { vehiclePhotoLauncher.launch("image/*") },
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(imageVector = Icons.Default.AddAPhoto, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = "Elegir foto", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            if (fotoVehiculoUrl.isNotBlank()) {
                                Text(text = "✓ Foto lista", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF16A34A))
                            }
                        }
                    }

                    if (validationError.isNotBlank()) {
                        Text(text = validationError, color = Color.Red, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // PUBLICAR RIDE BUTTON
                    Button(
                        onClick = {
                            if (origen.isBlank() || destino.isBlank() || fecha.isBlank()) {
                                validationError = "⚠️ Es obligatorio ingresar Origen, Destino y Fecha del viaje."
                            } else {
                                onPublish(origen, destino, fecha, hora, vehiculo, color, espacios, precio, puntoEncuentro, info, userPhotoUrl, fotoVehiculoUrl)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = LimeBrand, contentColor = InkBlack)
                    ) {
                        Text("Publicar Ride", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

// SOLICITAR RIDE DIALOG
@Composable
fun SolicitarRideDialog(
    ride: Ride,
    count: Int,
    onCountChange: (Int) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    val context = LocalContext.current
    var mensaje by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF4FAF5)),
            color = Color(0xFFF4FAF5)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Top Header: Back Arrow + Centered Title
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Atrás",
                            tint = Color(0xFF005147),
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Text(
                        text = "Solicitar Espacio",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF005147),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 40.dp)
                    )
                }

                // Title and Subtitle Section
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Contactar al conductor",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF181C20)
                    )
                    Text(
                        text = "Elige un medio para coordinar tu viaje directamente.",
                        fontSize = 14.sp,
                        color = Color(0xFF4B5563),
                        lineHeight = 20.sp
                    )
                }

                // Text Input Section
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Tu mensaje (opcional)",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF181C20)
                    )

                    OutlinedTextField(
                        value = mensaje,
                        onValueChange = { mensaje = it },
                        placeholder = {
                            Text(
                                text = "Hola, me gustaría solicitar un espacio para el viaje...",
                                color = Color(0xFF6B7280),
                                fontSize = 14.sp
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(115.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFF0FDF4).copy(alpha = 0.6f),
                            unfocusedContainerColor = Color(0xFFF0FDF4).copy(alpha = 0.6f),
                            focusedBorderColor = Color(0xFF86EFAC),
                            unfocusedBorderColor = Color(0xFFBEC9C5)
                        ),
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontSize = 14.sp,
                            color = Color(0xFF181C20)
                        )
                    )
                }

                // Action Buttons
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // WhatsApp Button
                    Button(
                        onClick = {
                            val defaultMsg = mensaje.ifBlank {
                                "Hola, me gustaría solicitar un espacio para el viaje ${ride.origen} → ${ride.destino} del ${ride.fecha}."
                            }
                            val cleanNum = "50588888888"
                            val uri = Uri.parse("https://wa.me/$cleanNum?text=" + Uri.encode(defaultMsg))
                            try {
                                context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                            } catch (e: Exception) {
                                // Fallback
                            }
                            onConfirm(count)
                            onDismiss()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF22C55E),
                            contentColor = Color.White
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Chat,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Contactar por WhatsApp",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }
                    }

                    // Call Directly Button
                    OutlinedButton(
                        onClick = {
                            val cleanNum = "50588888888"
                            val uri = Uri.parse("tel:$cleanNum")
                            try {
                                context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                            } catch (e: Exception) {
                                // Fallback
                            }
                            onConfirm(count)
                            onDismiss()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.5.dp, Color(0xFF006B5F)),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color(0xFF005147)
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Call,
                                contentDescription = null,
                                tint = Color(0xFF005147),
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Llamar directamente",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Color(0xFF005147)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Recordatorios de la comunidad Box
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFF1F5F9)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Info,
                                contentDescription = null,
                                tint = Color(0xFF10B981),
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = "Recordatorios de la comunidad",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF181C20)
                            )
                        }

                        Spacer(modifier = Modifier.height(2.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.CheckCircle,
                                contentDescription = null,
                                tint = Color(0xFF4B5563),
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "Rinde no cobra comisión",
                                fontSize = 13.sp,
                                color = Color(0xFF374151)
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Payments,
                                contentDescription = null,
                                tint = Color(0xFF4B5563),
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "El pago se coordina directamente con el conductor",
                                fontSize = 13.sp,
                                color = Color(0xFF374151)
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Diamond,
                                contentDescription = null,
                                tint = Color(0xFF4B5563),
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "Mantén el respeto en todo momento",
                                fontSize = 13.sp,
                                color = Color(0xFF374151)
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Chat,
                                contentDescription = null,
                                tint = Color(0xFF4B5563),
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "La comunicación se realiza por WhatsApp o llamada",
                                fontSize = 13.sp,
                                color = Color(0xFF374151)
                            )
                        }
                    }
                }
            }
        }
    }
}

// PUBLISH STAY DIALOG (Delegates to PublicarEstanciaDialog)
@Composable
fun PublishStayDialog(
    stay: Stay,
    profile: ProviderProfile,
    allLocations: List<String>,
    onDismiss: () -> Unit,
    onPublish: (Long?, String, String, String, String, Int, String, Int, Int, Int, Int, List<String>, String, String, String, String, String) -> Unit,
    currentUser: com.example.data.model.UserProfile? = null
) {
    PublicarEstanciaDialog(
        stay = stay,
        profile = profile,
        allLocations = allLocations,
        onDismiss = onDismiss,
        onPublish = onPublish,
        currentUser = currentUser
    )
}

@Composable
fun CounterBox(
    label: String,
    count: Int,
    onMinus: () -> Unit,
    onPlus: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceGray)
            .border(1.dp, LineBorder, RoundedCornerShape(12.dp))
            .padding(horizontal = 6.dp, vertical = 4.dp)
    ) {
        Column {
            Text(text = label, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6B7280))
            Text(text = "$count", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = InkBlack)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onMinus, modifier = Modifier.size(28.dp)) {
                Text("−", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = InkBlack)
            }
            IconButton(onClick = onPlus, modifier = Modifier.size(28.dp)) {
                Text("+", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = InkBlack)
            }
        }
    }
}

@Composable
private fun CounterItem(
    label: String,
    count: Int,
    onMinus: () -> Unit,
    onPlus: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(SurfaceGray)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text = "$label: ", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = InkBlack)
        IconButton(onClick = onMinus, modifier = Modifier.size(24.dp)) {
            Text("-", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
        Text(text = "$count", fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(horizontal = 4.dp))
        IconButton(onClick = onPlus, modifier = Modifier.size(24.dp)) {
            Text("+", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// STAY DETAIL MODAL (Matching requested screenshot)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StayDetailModal(
    stay: Stay,
    onDismiss: () -> Unit,
    onContactWhatsApp: (String) -> Unit,
    onContactCall: (String) -> Unit
) {
    var showContactOptions by remember { mutableStateOf(false) }
    var showHostProfile by remember { mutableStateOf(false) }
    var showPriceBreakdown by remember { mutableStateOf(false) }
    var showAllAmenitiesDialog by remember { mutableStateOf(false) }
    var showAllReviewsDialog by remember { mutableStateOf(false) }
    var isFavorite by remember { mutableStateOf(false) }
    var isDescriptionExpanded by remember { mutableStateOf(false) }

    // Sample images array or user uploaded photos
    val photos = remember(stay.photosJson) {
        if (stay.photosJson.isNotBlank()) {
            val list = stay.photosJson.split("|").filter { it.isNotBlank() }
            if (list.size >= 2) list
            else list + listOf(
                "https://images.unsplash.com/photo-1582719478250-c89cae4dc85b?w=800",
                "https://images.unsplash.com/photo-1618773928121-c32242e63f39?w=800",
                "https://images.unsplash.com/photo-1590490360182-c33d57733427?w=800"
            )
        } else {
            listOf(
                "https://images.unsplash.com/photo-1582719478250-c89cae4dc85b?w=800",
                "https://images.unsplash.com/photo-1618773928121-c32242e63f39?w=800",
                "https://images.unsplash.com/photo-1590490360182-c33d57733427?w=800",
                "https://images.unsplash.com/photo-1566073771259-6a8506099945?w=800"
            )
        }
    }

    val pagerState = rememberPagerState(pageCount = { photos.size })

    val defaultAmenities = listOf(
        Pair(Icons.Default.Wifi, "Wi-Fi de alta velocidad"),
        Pair(Icons.Default.Pool, "Piscina privada"),
        Pair(Icons.Default.Kitchen, "Cocina totalmente equipada"),
        Pair(Icons.Default.AcUnit, "Aire acondicionado (A/C)"),
        Pair(Icons.Default.LocalParking, "Estacionamiento gratuito"),
        Pair(Icons.Default.Pets, "Se admiten mascotas")
    )

    androidx.compose.ui.window.Dialog(
        onDismissRequest = onDismiss,
        properties = androidx.compose.ui.window.DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // 1. HERO PHOTO CAROUSEL WITH OVERLAY BUTTONS & BADGES
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(330.dp)
                ) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize()
                    ) { page ->
                        AsyncImage(
                            model = photos[page],
                            contentDescription = "${stay.nombre} - Foto ${page + 1}",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    // TOP OVERLAY BAR: BACK BUTTON (LEFT) + SHARE & FAVORITE (RIGHT)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // BACK BUTTON
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.9f))
                                .clickable { onDismiss() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Regresar",
                                tint = InkBlack,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        // RIGHT ACTION BUTTONS: SHARE + FAVORITE
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.9f))
                                    .clickable { /* Share */ },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = "Compartir",
                                    tint = InkBlack,
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.9f))
                                    .clickable { isFavorite = !isFavorite },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "Favorito",
                                    tint = if (isFavorite) Color(0xFFEF4444) else InkBlack,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                    // BOTTOM CAROUSEL INDICATORS: DOTS (CENTER) & COUNT BADGE (RIGHT)
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 14.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        repeat(photos.size.coerceAtMost(5)) { index ->
                            val isSelected = pagerState.currentPage == index
                            Box(
                                modifier = Modifier
                                    .size(if (isSelected) 8.dp else 6.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) Color.White else Color.White.copy(alpha = 0.5f))
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(14.dp),
                        shape = RoundedCornerShape(8.dp),
                        color = Color.Black.copy(alpha = 0.65f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.GridView,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(13.dp)
                            )
                            Text(
                                text = "${pagerState.currentPage + 1}/${photos.size} Fotos",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }

                // 2. MAIN BODY CONTENT
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // TITLE & SUBTITLES
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = stay.nombre.ifBlank { "Villa de Lujo con Piscina" },
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = InkBlack,
                            lineHeight = 30.sp
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = Color(0xFF6B7280),
                                modifier = Modifier.size(17.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${stay.municipio}${if (stay.direccion.isNotBlank()) ", ${stay.direccion}" else ", Nicaragua"}",
                                fontSize = 14.sp,
                                color = Color(0xFF6B7280),
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { showAllReviewsDialog = true }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = LimeBrandDark,
                                modifier = Modifier.size(17.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${stay.rating}",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = InkBlack
                            )
                            Text(
                                text = " (${stay.reviewsCount} reseñas)",
                                fontSize = 14.sp,
                                color = Color(0xFF6B7280),
                                textDecoration = TextDecoration.Underline,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(start = 2.dp)
                            )
                            Text(
                                text = " • ${stay.tipo.ifBlank { "Estancia" }}",
                                fontSize = 14.sp,
                                color = Color(0xFF6B7280),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    // 4-COLUMN QUICK FACTS CARD
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, LineBorder),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 14.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            FactItem(icon = Icons.Default.People, label = "Huéspedes", value = "${stay.huespedes.coerceAtLeast(1)}")
                            Box(modifier = Modifier.width(1.dp).height(36.dp).background(LineBorder))
                            FactItem(icon = Icons.Default.MeetingRoom, label = "Habitación", value = "${stay.habitaciones.coerceAtLeast(1)}")
                            Box(modifier = Modifier.width(1.dp).height(36.dp).background(LineBorder))
                            FactItem(icon = Icons.Default.Bed, label = "Cama", value = "${stay.camas.coerceAtLeast(1)}")
                            Box(modifier = Modifier.width(1.dp).height(36.dp).background(LineBorder))
                            FactItem(icon = Icons.Default.Shower, label = "Baño", value = "${stay.banos.coerceAtLeast(1)}")
                        }
                    }

                    // HOST / ANFITRION INFO CONTAINER (Matching HTML Mockup)
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFF3F3F4),
                        border = BorderStroke(1.dp, LineBorder)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                // HOST AVATAR WITH BADGE
                                Box(
                                    modifier = Modifier.size(56.dp),
                                    contentAlignment = Alignment.BottomEnd
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(CircleShape)
                                            .border(2.dp, Color.White, CircleShape)
                                            .background(LimeBrand.copy(alpha = 0.2f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (stay.providerPhotoUrl.isNotBlank()) {
                                            AsyncImage(
                                                model = stay.providerPhotoUrl,
                                                contentDescription = stay.hostName,
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier.fillMaxSize()
                                            )
                                        } else {
                                            Text(
                                                text = (stay.hostName.ifBlank { "María López" }).take(1).uppercase(),
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.ExtraBold,
                                                color = InkBlack
                                            )
                                        }
                                    }
                                    if (stay.verificado) {
                                        Icon(
                                            imageVector = Icons.Filled.Verified,
                                            contentDescription = "Verificado",
                                            tint = LimeBrandDark,
                                            modifier = Modifier
                                                .size(18.dp)
                                                .background(Color.White, CircleShape)
                                        )
                                    }
                                }

                                Column {
                                    Text(
                                        text = "Anfitrión: ${stay.hostName.ifBlank { "María López" }}",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = InkBlack,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = "Anfitrión verificado · 5 años de experiencia",
                                        fontSize = 12.sp,
                                        color = Color(0xFF6B7280),
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }

                            IconButton(
                                onClick = { showHostProfile = true },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = "Ver perfil",
                                    tint = InkBlack,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                    HorizontalDivider(color = LineBorder, thickness = 1.dp)

                    // ACERCA DE ESTE LUGAR
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "Acerca de este lugar",
                            fontSize = 19.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = InkBlack
                        )

                        val desc = if (stay.descripcion.isNotBlank()) stay.descripcion
                        else "Escapa a esta magnífica estancia de diseño contemporáneo. Disfruta de espacios abiertos, luz natural abundante y comodidades pensadas para tu descanso.\n\nPerfecta para familias o profesionales que buscan tranquilidad y confort. Ubicada en una zona privilegiada con rápido acceso a transporte."

                        Text(
                            text = desc,
                            fontSize = 14.sp,
                            color = Color(0xFF4B5563),
                            lineHeight = 22.sp,
                            maxLines = if (isDescriptionExpanded) Int.MAX_VALUE else 4,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = if (isDescriptionExpanded) "Mostrar menos ▲" else "Mostrar más ▼",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = LimeBrandDark,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier
                                .clickable { isDescriptionExpanded = !isDescriptionExpanded }
                                .padding(vertical = 2.dp)
                        )
                    }

                    HorizontalDivider(color = LineBorder, thickness = 1.dp)

                    // ¿QUÉ OFRECE ESTE LUGAR? (2-COLUMN GRID MATCHING MOCKUP)
                    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                        Text(
                            text = "¿Qué ofrece este lugar?",
                            fontSize = 19.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = InkBlack
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            defaultAmenities.chunked(2).forEach { rowPair ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    rowPair.forEach { (icon, name) ->
                                        Surface(
                                            modifier = Modifier
                                                .weight(1f)
                                                .height(52.dp),
                                            shape = RoundedCornerShape(12.dp),
                                            color = Color(0xFFF9FAFB),
                                            border = BorderStroke(1.dp, LineBorder)
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(horizontal = 12.dp),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                                            ) {
                                                Icon(
                                                    imageVector = icon,
                                                    contentDescription = null,
                                                    tint = Color(0xFF555F6F),
                                                    modifier = Modifier.size(20.dp)
                                                )
                                                Text(
                                                    text = name,
                                                    fontSize = 12.sp,
                                                    color = InkBlack,
                                                    fontWeight = FontWeight.SemiBold,
                                                    maxLines = 2,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            }
                                        }
                                    }
                                    if (rowPair.size == 1) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }

                        OutlinedButton(
                            onClick = { showAllAmenitiesDialog = true },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp),
                            shape = RoundedCornerShape(24.dp),
                            border = BorderStroke(1.dp, LineBorder)
                        ) {
                            Text(
                                text = "Mostrar las 32 comodidades",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = InkBlack
                            )
                        }
                    }

                    HorizontalDivider(color = LineBorder, thickness = 1.dp)

                    // RESEÑAS SECTION (Matching Mockup)
                    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "Reseñas",
                                    fontSize = 19.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = InkBlack
                                )
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = LimeBrandDark,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = "${stay.rating}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = InkBlack
                                )
                            }
                        }

                        // REVIEW 1
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = BorderStroke(1.dp, LineBorder)
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(38.dp)
                                            .clip(CircleShape)
                                            .background(SurfaceGray),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(text = "JP", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = InkBlack)
                                    }
                                    Column {
                                        Text(text = "Juan Pérez", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = InkBlack)
                                        Text(text = "hace 2 meses", fontSize = 12.sp, color = Color(0xFF6B7280))
                                    }
                                }

                                Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                    repeat(5) {
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = null,
                                            tint = LimeBrandDark,
                                            modifier = Modifier.size(15.dp)
                                        )
                                    }
                                }

                                Text(
                                    text = "Increíble lugar, la atención fue excelente y todo estaba súper limpio. Muy recomendado para descansar y desconectarse.",
                                    fontSize = 13.sp,
                                    color = Color(0xFF4B5563),
                                    lineHeight = 19.sp
                                )
                            }
                        }

                        // REVIEW 2
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = BorderStroke(1.dp, LineBorder)
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(38.dp)
                                            .clip(CircleShape)
                                            .background(SurfaceGray),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(text = "EG", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = InkBlack)
                                    }
                                    Column {
                                        Text(text = "Elena García", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = InkBlack)
                                        Text(text = "hace 3 semanas", fontSize = 12.sp, color = Color(0xFF6B7280))
                                    }
                                }

                                Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                    repeat(5) {
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = null,
                                            tint = LimeBrandDark,
                                            modifier = Modifier.size(15.dp)
                                        )
                                    }
                                }

                                Text(
                                    text = "El alojamiento es aún más bonito que en las fotos. El ambiente es un sueño y la cocina tiene todo lo necesario para sentirse en casa.",
                                    fontSize = 13.sp,
                                    color = Color(0xFF4B5563),
                                    lineHeight = 19.sp
                                )
                            }
                        }

                        OutlinedButton(
                            onClick = { showAllReviewsDialog = true },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp),
                            shape = RoundedCornerShape(24.dp),
                            border = BorderStroke(1.dp, LineBorder)
                        ) {
                            Text(
                                text = "Mostrar las ${stay.reviewsCount} reseñas",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = InkBlack
                            )
                        }
                    }

                    HorizontalDivider(color = LineBorder, thickness = 1.dp)

                    // DÓNDE ESTARÁS (MAP PREVIEW CARD WITH GLOWING RADAR PIN)
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "Dónde estarás",
                            fontSize = 19.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = InkBlack
                        )

                        Text(
                            text = "${stay.municipio}, ${stay.direccion.ifBlank { "Nicaragua" }}",
                            fontSize = 14.sp,
                            color = Color(0xFF6B7280),
                            fontWeight = FontWeight.Medium
                        )

                        // STYLIZED MAP CARD
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFE5E7EB)),
                            border = BorderStroke(1.dp, LineBorder)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color(0xFFE8ECEF)),
                                contentAlignment = Alignment.Center
                            ) {
                                // MAP GRID LINES SIMULATION
                                Canvas(modifier = Modifier.fillMaxSize()) {
                                    val strokeWidth = 2.dp.toPx()
                                    val lineColor = Color.White.copy(alpha = 0.7f)
                                    // Horizontal street lines
                                    drawLine(lineColor, start = androidx.compose.ui.geometry.Offset(0f, size.height * 0.35f), end = androidx.compose.ui.geometry.Offset(size.width, size.height * 0.35f), strokeWidth = strokeWidth)
                                    drawLine(lineColor, start = androidx.compose.ui.geometry.Offset(0f, size.height * 0.7f), end = androidx.compose.ui.geometry.Offset(size.width, size.height * 0.7f), strokeWidth = strokeWidth)
                                    // Vertical street lines
                                    drawLine(lineColor, start = androidx.compose.ui.geometry.Offset(size.width * 0.3f, 0f), end = androidx.compose.ui.geometry.Offset(size.width * 0.3f, size.height), strokeWidth = strokeWidth)
                                    drawLine(lineColor, start = androidx.compose.ui.geometry.Offset(size.width * 0.65f, 0f), end = androidx.compose.ui.geometry.Offset(size.width * 0.65f, size.height), strokeWidth = strokeWidth)
                                }

                                // RADAR PULSING PIN MARKER
                                Box(
                                    modifier = Modifier
                                        .size(64.dp)
                                        .clip(CircleShape)
                                        .background(LimeBrand.copy(alpha = 0.25f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(42.dp)
                                            .clip(CircleShape)
                                            .background(LimeBrand.copy(alpha = 0.45f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(24.dp)
                                                .clip(CircleShape)
                                                .background(LimeBrandDark)
                                                .border(2.dp, Color.White, CircleShape),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.LocationOn,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier.size(14.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Text(
                            text = "La ubicación exacta se proporcionará después de la reserva.",
                            fontSize = 12.sp,
                            color = Color(0xFF9CA3AF),
                            lineHeight = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(110.dp))
                }
            }

            // 3. STICKY BOTTOM BAR (PRICE + DIRECT ACTIONS MATCHING MOCKUP)
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                color = Color.White.copy(alpha = 0.95f),
                shadowElevation = 16.dp,
                border = BorderStroke(1.dp, LineBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "C$ ${stay.precio}",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = InkBlack
                            )
                            Text(
                                text = " / noche",
                                fontSize = 13.sp,
                                color = Color(0xFF6B7280),
                                modifier = Modifier.padding(bottom = 2.dp)
                            )
                        }
                        Text(
                            text = "Ver desglose",
                            fontSize = 13.sp,
                            color = Color(0xFF6B7280),
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier.clickable { showPriceBreakdown = true }
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        // DIRECT CALL BUTTON (CIRCULAR 48x48 IN BLUE)
                        Surface(
                            onClick = { onContactCall(stay.telefono) },
                            modifier = Modifier.size(48.dp),
                            shape = CircleShape,
                            color = Color(0xFF2563EB),
                            shadowElevation = 2.dp
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Phone,
                                    contentDescription = "Llamar",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        // WHATSAPP BUTTON (CIRCULAR 48x48 IN WHATSAPP GREEN)
                        Surface(
                            onClick = { onContactWhatsApp(stay.whatsapp) },
                            modifier = Modifier.size(48.dp),
                            shape = CircleShape,
                            color = Color(0xFF25D366),
                            shadowElevation = 2.dp
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Call,
                                    contentDescription = "WhatsApp",
                                    tint = Color.White,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }

                        // CONTACTAR / RESERVAR PILL BUTTON
                        Button(
                            onClick = { showContactOptions = true },
                            modifier = Modifier.height(48.dp),
                            shape = RoundedCornerShape(24.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = LimeBrand, contentColor = InkBlack),
                            contentPadding = PaddingValues(horizontal = 20.dp)
                        ) {
                            Text(
                                text = "Reservar",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }

    // CONTACT OPTIONS BOTTOM SHEET
    if (showContactOptions) {
        ModalBottomSheet(
            onDismissRequest = { showContactOptions = false },
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "Contactar anfitrión",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = InkBlack
                )

                Text(
                    text = "Comunícate con ${stay.hostName.ifBlank { "el anfitrión" }} para consultar disponibilidad o resolver dudas de tu estadía.",
                    fontSize = 13.sp,
                    color = Color(0xFF6B7280)
                )

                Button(
                    onClick = {
                        showContactOptions = false
                        onContactWhatsApp(stay.whatsapp)
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366), contentColor = Color.White)
                ) {
                    Text("WhatsApp", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }

                OutlinedButton(
                    onClick = {
                        showContactOptions = false
                        onContactCall(stay.telefono)
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.5.dp, Color(0xFF128C4A))
                ) {
                    Icon(imageVector = Icons.Default.Call, contentDescription = null, tint = Color(0xFF128C4A), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Llamar al anfitrión", fontWeight = FontWeight.Bold, color = Color(0xFF128C4A), fontSize = 15.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // HOST PROFILE MODAL
    if (showHostProfile) {
        ModalBottomSheet(
            onDismissRequest = { showHostProfile = false },
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ProviderTrustCard(
                    providerName = stay.hostName.ifBlank { "Juan Rivera" },
                    providerPhotoUrl = stay.providerPhotoUrl,
                    trustLevel = stay.trustLevel,
                    rating = 4.9f,
                    tripsCount = 35,
                    reviewsCount = 30,
                    memberSinceYear = "2026"
                )

                Button(
                    onClick = { showHostProfile = false },
                    modifier = Modifier.fillMaxWidth().height(46.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = LimeBrand, contentColor = InkBlack)
                ) {
                    Text("Cerrar", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // PRICE BREAKDOWN SHEET
    if (showPriceBreakdown) {
        ModalBottomSheet(
            onDismissRequest = { showPriceBreakdown = false },
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Detalle de tarifa",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = InkBlack
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Tarifa base por noche", fontSize = 14.sp, color = Color(0xFF4B5563))
                    Text("C$ ${stay.precio}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = InkBlack)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Tarifa de servicio BusDrive", fontSize = 14.sp, color = Color(0xFF4B5563))
                    Text("C$ 0 (Gratis)", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF16A34A))
                }

                HorizontalDivider(color = LineBorder, thickness = 1.dp)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total por noche", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = InkBlack)
                    Text("C$ ${stay.precio}", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = InkBlack)
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // ALL AMENITIES DIALOG
    if (showAllAmenitiesDialog) {
        ModalBottomSheet(
            onDismissRequest = { showAllAmenitiesDialog = false },
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "Comodidades del alojamiento",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = InkBlack
                )

                defaultAmenities.forEach { (icon, name) ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(imageVector = icon, contentDescription = null, tint = InkBlack, modifier = Modifier.size(20.dp))
                        Text(text = name, fontSize = 14.sp, color = InkBlack, fontWeight = FontWeight.Medium)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // ALL REVIEWS DIALOG
    if (showAllReviewsDialog) {
        ModalBottomSheet(
            onDismissRequest = { showAllReviewsDialog = false },
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Reseñas del alojamiento",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = InkBlack
                    )
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = LimeBrandDark,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "${stay.rating} (${stay.reviewsCount})",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = InkBlack
                    )
                }

                // Review items
                val reviewList = listOf(
                    Triple("Juan Pérez", "hace 2 meses", "Increíble lugar, la atención fue excelente y todo estaba súper limpio. Muy recomendado para descansar y desconectarse."),
                    Triple("Elena García", "hace 3 semanas", "El alojamiento es aún más bonito que en las fotos. El ambiente es un sueño y la cocina tiene todo lo necesario."),
                    Triple("Carlos Mendoza", "hace 1 mes", "Excelente ubicación, segura y tranquila. El anfitrión respondió de inmediato todas nuestras consultas."),
                    Triple("Sofía Ramírez", "hace 4 meses", "La piscina y las habitaciones son de 10. Pasamos un fin de semana inolvidable con la familia.")
                )

                reviewList.forEach { (author, time, comment) ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF9FAFB), RoundedCornerShape(12.dp))
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(SurfaceGray),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = author.split(" ").mapNotNull { it.firstOrNull()?.toString() }.take(2).joinToString(""),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = InkBlack
                                )
                            }
                            Column {
                                Text(text = author, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = InkBlack)
                                Text(text = time, fontSize = 11.sp, color = Color(0xFF6B7280))
                            }
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                            repeat(5) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = LimeBrandDark,
                                    modifier = Modifier.size(13.dp)
                                )
                            }
                        }
                        Text(text = comment, fontSize = 12.sp, color = Color(0xFF4B5563), lineHeight = 17.sp)
                    }
                }

                Button(
                    onClick = { showAllReviewsDialog = false },
                    modifier = Modifier.fillMaxWidth().height(46.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = LimeBrand, contentColor = InkBlack)
                ) {
                    Text("Cerrar", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun FactItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF6B7280),
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = label,
            fontSize = 11.sp,
            color = Color(0xFF6B7280),
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold,
            color = InkBlack
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalDialogLayout(
    title: String,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = InkBlack
                )
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Cerrar")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            content()

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ProviderTrustCard(
    providerName: String,
    providerPhotoUrl: String = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=400",
    trustLevel: String = "Comunidad",
    rating: Float = 4.9f,
    tripsCount: Int = 42,
    reviewsCount: Int = 38,
    memberSinceYear: String = "2026",
    phoneVerified: Boolean = true,
    identityVerified: Boolean = true,
    serviceVerified: Boolean = true,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceGray),
        border = androidx.compose.foundation.BorderStroke(1.dp, LineBorder)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .border(2.dp, LimeBrand, CircleShape)
                            .background(LimeBrand.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (providerPhotoUrl.isNotBlank()) {
                            AsyncImage(
                                model = providerPhotoUrl,
                                contentDescription = providerName,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            Text(
                                text = providerName.take(1).uppercase(),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = InkBlack
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = providerName,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 15.sp,
                            color = InkBlack,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Filled.Verified,
                            contentDescription = "Verificado",
                            tint = Color(0xFF1877F2),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TicketDetailModal(
    ticket: Ticket,
    onDismiss: () -> Unit,
    onContactWhatsApp: (String, String, String) -> Unit,
    onContactCall: (String) -> Unit
) {
    ModalDialogLayout(title = "Detalle del Boleto", onDismiss = onDismiss) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // ROUTE BANNER
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = InkBlack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "${ticket.origen} → ${ticket.destino}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = LimeBrand
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "📅 ${ticket.fecha} · 🕒 ${ticket.hora}",
                        fontSize = 13.sp,
                        color = Color.White
                    )
                }
            }

            // SELLER PROFILE
            ProviderTrustCard(
                providerName = ticket.nombreContacto,
                providerPhotoUrl = ticket.providerPhotoUrl,
                trustLevel = ticket.trustLevel
            )

            // DETAILS GRID
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceGray),
                border = BorderStroke(1.dp, LineBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Empresa / Operador:", fontSize = 13.sp, color = MutedGray)
                        Text(ticket.operador.ifBlank { "Cooperativa local" }, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = InkBlack)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Asiento:", fontSize = 13.sp, color = MutedGray)
                        Text(ticket.asiento.ifBlank { "General" }, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = InkBlack)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Precio:", fontSize = 13.sp, color = MutedGray)
                        Text("C$ ${ticket.precio}", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = InkBlack)
                    }
                    if (ticket.nota.isNotBlank()) {
                        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = LineBorder)
                        Text("Nota del vendedor:", fontSize = 12.sp, color = MutedGray)
                        Text(ticket.nota, fontSize = 13.sp, color = InkBlack)
                    }
                }
            }

            // ACTIONS
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = { onContactWhatsApp(ticket.telefonoContacto, ticket.origen, ticket.destino) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = LimeBrand, contentColor = InkBlack)
                ) {
                    Text("WhatsApp", fontWeight = FontWeight.Bold)
                }
                OutlinedButton(
                    onClick = { onContactCall(ticket.telefonoContacto) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Llamar", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun RideDetailModal(
    ride: Ride,
    onDismiss: () -> Unit,
    onSolicitar: () -> Unit
) {
    ModalDialogLayout(title = "Detalle del Ride", onDismiss = onDismiss) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // ROUTE BANNER
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = InkBlack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "${ride.origen} → ${ride.destino}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = LimeBrand
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "📅 ${ride.fecha} · 🕒 ${ride.hora}",
                        fontSize = 13.sp,
                        color = Color.White
                    )
                }
            }

            // DRIVER PROFILE
            ProviderTrustCard(
                providerName = ride.driverName,
                providerPhotoUrl = ride.providerPhotoUrl,
                trustLevel = ride.trustLevel
            )

            // DETAILS
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceGray),
                border = BorderStroke(1.dp, LineBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Vehículo:", fontSize = 13.sp, color = MutedGray)
                        Text("${ride.vehiculo} (${ride.color})", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = InkBlack)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Espacios disponibles:", fontSize = 13.sp, color = MutedGray)
                        Text("${ride.espaciosDisponibles}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = InkBlack)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Precio por persona:", fontSize = 13.sp, color = MutedGray)
                        Text("C$ ${ride.precioPorPersona}", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = InkBlack)
                    }
                    if (ride.puntoEncuentro.isNotBlank()) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Punto de encuentro:", fontSize = 13.sp, color = MutedGray)
                            Text(ride.puntoEncuentro, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = InkBlack)
                        }
                    }
                    if (ride.infoAdicional.isNotBlank()) {
                        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = LineBorder)
                        Text("Información adicional:", fontSize = 12.sp, color = MutedGray)
                        Text(ride.infoAdicional, fontSize = 13.sp, color = InkBlack)
                    }
                }
            }

            // SOLICITAR BUTTON
            Button(
                onClick = onSolicitar,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = LimeBrand, contentColor = InkBlack)
            ) {
                Text("Solicitar espacio en este Ride", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
        }
    }
}
