package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.data.model.Stay
import com.example.ui.screens.AccountDetailsScreen
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.AyudaScreen
import com.example.ui.screens.CambiarPasswordScreen
import com.example.ui.screens.ConfiguracionScreen
import com.example.ui.screens.CuentaScreen
import com.example.ui.screens.ExplorarScreen
import com.example.ui.screens.GuiaBoletosScreen
import com.example.ui.screens.LocationPickerSheet
import com.example.ui.screens.MandaditosScreen
import com.example.ui.screens.MisPublicacionesScreen
import com.example.ui.screens.NotificacionesScreen
import com.example.ui.screens.ResultadosScreen
import com.example.ui.screens.TerminosPrivacidadScreen
import com.example.ui.screens.ViajesScreen
import com.example.ui.theme.BusDriveTheme
import com.example.ui.viewmodel.BusDriveViewModel
import com.example.ui.viewmodel.MainTab
import com.example.ui.viewmodel.ViajeSubTab

class MainActivity : ComponentActivity() {

    private val viewModel: BusDriveViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        handleIncomingIntent(intent)
        setContent {
            BusDriveTheme {
                BusDriveMainApp(viewModel)
            }
        }
    }

    override fun onNewIntent(intent: android.content.Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIncomingIntent(intent)
    }

    private fun handleIncomingIntent(intent: android.content.Intent?) {
        if (intent == null) return
        val openScreen = intent.getStringExtra("OPEN_SCREEN")
        if (openScreen == "PUBLICATION") {
            val targetType = intent.getStringExtra("TARGET_TYPE") ?: ""
            val targetId = intent.getLongExtra("TARGET_ID", 0L)
            viewModel.openPublicationDirectly(targetType, targetId)
        } else if (openScreen == "NOTIFICATIONS") {
            viewModel.showNotifsScreen.value = true
        }
    }
}

@Composable
fun BusDriveMainApp(viewModel: BusDriveViewModel) {
    val selectedTab by viewModel.selectedTab.collectAsState()
    val showPicker by viewModel.showPicker.collectAsState()
    val showAuth by viewModel.showAuthModal.collectAsState()

    val showNotifsScreen by viewModel.showNotifsScreen.collectAsState()
    val showAccountDetailsScreen by viewModel.showAccountDetailsScreen.collectAsState()
    val showMyTicketsScreen by viewModel.showMyTicketsScreen.collectAsState()
    val showTermsScreen by viewModel.showTermsScreen.collectAsState()
    val showSettingsScreen by viewModel.showSettingsScreen.collectAsState()
    val showTicketGuideScreen by viewModel.showTicketGuideScreen.collectAsState()
    val showChangePasswordScreen by viewModel.showChangePasswordScreen.collectAsState()
    val showHelpScreen by viewModel.showHelpScreen.collectAsState()

    val currentUser by viewModel.currentUser.collectAsState()
    var showQuickPublishSheet by remember { mutableStateOf(false) }

    var isResultadosActive by remember { mutableStateOf(false) }

    val allRoutes by viewModel.allRoutes.collectAsState()
    val allLocations = remember(allRoutes) {
        allRoutes.flatMap { listOf(it.origen, it.destino) }.filter { it.isNotBlank() }.distinct().sorted()
    }

    val origen by viewModel.origenVal.collectAsState()
    val destino by viewModel.destinoVal.collectAsState()

    // Screen display logic
    val showBottomNav = !isResultadosActive && !showNotifsScreen && !showAccountDetailsScreen && !showMyTicketsScreen && !showTermsScreen && !showSettingsScreen && !showTicketGuideScreen && !showChangePasswordScreen && !showHelpScreen

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                showHelpScreen -> {
                    AyudaScreen(
                        onBack = { viewModel.showHelpScreen.value = false },
                        onOpenGuiaBoletos = { viewModel.showTicketGuideScreen.value = true }
                    )
                }
                showChangePasswordScreen -> {
                    CambiarPasswordScreen(
                        viewModel = viewModel,
                        onBack = { viewModel.showChangePasswordScreen.value = false }
                    )
                }
                showTicketGuideScreen -> {
                    GuiaBoletosScreen(
                        onBack = { viewModel.showTicketGuideScreen.value = false }
                    )
                }
                showNotifsScreen -> {
                    NotificacionesScreen(
                        viewModel = viewModel,
                        onBack = { viewModel.showNotifsScreen.value = false }
                    )
                }
                showAccountDetailsScreen -> {
                    AccountDetailsScreen(
                        viewModel = viewModel,
                        onBack = { viewModel.showAccountDetailsScreen.value = false }
                    )
                }
                showMyTicketsScreen -> {
                    MisPublicacionesScreen(
                        viewModel = viewModel,
                        onBack = { viewModel.showMyTicketsScreen.value = false }
                    )
                }
                showTermsScreen -> {
                    TerminosPrivacidadScreen(
                        onBack = { viewModel.showTermsScreen.value = false }
                    )
                }
                showSettingsScreen -> {
                    ConfiguracionScreen(
                        viewModel = viewModel,
                        onBack = { viewModel.showSettingsScreen.value = false }
                    )
                }
                isResultadosActive -> {
                    ResultadosScreen(
                        viewModel = viewModel,
                        onBack = { isResultadosActive = false }
                    )
                }
                else -> {
                    when (selectedTab) {
                        MainTab.EXPLORAR -> {
                            ExplorarScreen(
                                viewModel = viewModel,
                                onOpenSearch = { isResultadosActive = true }
                            )
                        }
                        MainTab.VIAJES -> {
                            ViajesScreen(viewModel = viewModel)
                        }
                        MainTab.MANDADITOS -> {
                            MandaditosScreen(viewModel = viewModel)
                        }
                        MainTab.CONFIG -> {
                            CuentaScreen(viewModel = viewModel)
                        }
                    }
                }
            }

            // FLOATING ACTION BUTTON (+) (En pantalla Comunidad y Mandaditos)
            if (showBottomNav && (selectedTab == MainTab.VIAJES || selectedTab == MainTab.MANDADITOS)) {
                FloatingActionButton(
                    onClick = {
                        showQuickPublishSheet = true
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .padding(end = 16.dp, bottom = 68.dp),
                    shape = CircleShape,
                    containerColor = Color(0xFFA2E000), // inDrive Lime Green
                    contentColor = Color(0xFF111827),  // Ink Black
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 6.dp,
                        pressedElevation = 10.dp
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Publicar",
                        tint = Color(0xFF111827),
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            // FLOATING BOTTOM NAVIGATION BAR (inDrive Style Floating Capsule)
            if (showBottomNav) {
                FloatingBottomNavBar(
                    selectedTab = selectedTab,
                    onTabSelected = { tab -> viewModel.selectedTab.value = tab },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .windowInsetsPadding(WindowInsets.navigationBars)
                )
            }
        }
    }

    // QUICK PUBLISH MODAL SHEET
    if (showQuickPublishSheet) {
        QuickPublishBottomSheet(
            onDismiss = { showQuickPublishSheet = false },
            onSelectOption = { option ->
                showQuickPublishSheet = false
                if (currentUser == null) {
                    val msg = when (option) {
                        QuickPublishType.BOLETO -> "Debes iniciar sesión para publicar un boleto."
                        QuickPublishType.RIDE -> "Debes iniciar sesión para ofrecer un ride."
                        QuickPublishType.STAY -> "Debes iniciar sesión para publicar una estancia."
                        QuickPublishType.MANDADITO -> "Debes iniciar sesión para ofrecer servicios de mandaditos."
                    }
                    viewModel.openAuthWithAction(msg, null)
                } else {
                    when (option) {
                        QuickPublishType.BOLETO -> {
                            viewModel.selectedTab.value = MainTab.VIAJES
                            viewModel.selectedViajeSubTab.value = ViajeSubTab.BOLETO
                            viewModel.showPublishTicketModal.value = true
                        }
                        QuickPublishType.RIDE -> {
                            viewModel.selectedTab.value = MainTab.VIAJES
                            viewModel.selectedViajeSubTab.value = ViajeSubTab.RIDE
                            viewModel.showPublishRideModal.value = true
                        }
                        QuickPublishType.STAY -> {
                            viewModel.selectedTab.value = MainTab.VIAJES
                            viewModel.selectedViajeSubTab.value = ViajeSubTab.STAY
                            viewModel.showPublishStayModal.value = Stay(
                                ownerId = currentUser?.id ?: "guest",
                                hostName = currentUser?.name ?: "Anfitrión",
                                verificado = false,
                                tipo = "Habitación privada",
                                nombre = "",
                                municipio = "Managua",
                                direccion = "",
                                precio = 300,
                                per = "noche",
                                huespedes = 2,
                                habitaciones = 1,
                                camas = 1,
                                banos = 1,
                                serviciosJson = "Wi-Fi,Agua potable",
                                descripcion = "",
                                whatsapp = currentUser?.phone ?: "",
                                telefono = currentUser?.phone ?: "",
                                photosJson = "",
                                rating = 5.0f,
                                reviewsCount = 0
                            )
                        }
                        QuickPublishType.MANDADITO -> {
                            viewModel.selectedTab.value = MainTab.MANDADITOS
                            viewModel.showPublishCourierModal.value = true
                        }
                    }
                }
            }
        )
    }

    // LOCATION PICKER BOTTOM SHEET
    showPicker?.let { mode ->
        LocationPickerSheet(
            mode = mode,
            currentOrigen = origen,
            currentDestino = destino,
            allLocations = allLocations,
            onSelectLocation = { location ->
                if (mode == "origen") viewModel.setOrigen(location)
                else viewModel.setDestino(location)
            },
            onDismiss = { viewModel.showPicker.value = null }
        )
    }

    // AUTH BOTTOM SHEET
    if (showAuth) {
        AuthScreen(
            viewModel = viewModel,
            onDismiss = { viewModel.showAuthModal.value = false }
        )
    }
}

@Composable
fun FloatingBottomNavBar(
    selectedTab: MainTab,
    onTabSelected: (MainTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 2.dp)
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(32.dp),
                ambientColor = Color(0x20000000),
                spotColor = Color(0x30A2E000)
            ),
        shape = RoundedCornerShape(32.dp),
        color = Color.White.copy(alpha = 0.98f),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FloatingNavItem(
                title = "Explorar",
                icon = Icons.Default.Explore,
                selected = selectedTab == MainTab.EXPLORAR,
                onClick = { onTabSelected(MainTab.EXPLORAR) }
            )

            FloatingNavItem(
                title = "Comunidad",
                icon = Icons.Default.Groups,
                selected = selectedTab == MainTab.VIAJES,
                onClick = { onTabSelected(MainTab.VIAJES) }
            )

            FloatingNavItem(
                title = "Mandaditos",
                icon = Icons.Default.TwoWheeler,
                selected = selectedTab == MainTab.MANDADITOS,
                onClick = { onTabSelected(MainTab.MANDADITOS) }
            )

            FloatingNavItem(
                title = "Cuenta",
                icon = Icons.Default.Person,
                selected = selectedTab == MainTab.CONFIG,
                onClick = { onTabSelected(MainTab.CONFIG) }
            )
        }
    }
}

@Composable
private fun FloatingNavItem(
    title: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    val animatedBgColor by animateColorAsState(
        targetValue = if (selected) Color(0xFFA2E000) else Color.Transparent, // inDrive Lime Green
        label = "nav_item_bg"
    )
    val contentColor by animateColorAsState(
        targetValue = if (selected) Color(0xFF111827) else Color(0xFF64748B), // Dark Ink vs Slate Gray
        label = "nav_item_content"
    )

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(animatedBgColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 5.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = contentColor,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = title,
                fontSize = 10.5.sp,
                fontWeight = if (selected) FontWeight.ExtraBold else FontWeight.SemiBold,
                color = contentColor,
                maxLines = 1
            )
        }
    }
}

enum class QuickPublishType {
    BOLETO,
    RIDE,
    STAY,
    MANDADITO
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickPublishBottomSheet(
    onDismiss: () -> Unit,
    onSelectOption: (QuickPublishType) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp)
                .padding(bottom = 24.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Publicar en BusDrive",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF161E15)
                    )
                    Text(
                        text = "Selecciona qué deseas compartir con la comunidad",
                        fontSize = 13.sp,
                        color = Color(0xFF6C7B6A)
                    )
                }
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar",
                        tint = Color(0xFF6C7B6A)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Option 1: Boleto
            PublishOptionCard(
                icon = Icons.Default.ConfirmationNumber,
                iconBg = Color(0xFFDCFCE7),
                iconTint = Color(0xFF15803D),
                title = "Publicar Boleto",
                subtitle = "Vende o transfiere un boleto de autobús disponible",
                onClick = { onSelectOption(QuickPublishType.BOLETO) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Option 2: Ride
            PublishOptionCard(
                icon = Icons.Default.DirectionsCar,
                iconBg = Color(0xFFE0F2FE),
                iconTint = Color(0xFF0369A1),
                title = "Publicar Ride",
                subtitle = "Ofrece asientos en tu vehículo y comparte gastos",
                onClick = { onSelectOption(QuickPublishType.RIDE) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Option 3: Estancia
            PublishOptionCard(
                icon = Icons.Default.Hotel,
                iconBg = Color(0xFFFEF3C7),
                iconTint = Color(0xFFB45309),
                title = "Publicar Estancia",
                subtitle = "Hospeda a viajeros en tu habitación, casa o posada",
                onClick = { onSelectOption(QuickPublishType.STAY) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Option 4: Mandadito
            PublishOptionCard(
                icon = Icons.Default.TwoWheeler,
                iconBg = Color(0xFFE8F5E9),
                iconTint = Color(0xFF006E2A),
                title = "Publicar Mandadito",
                subtitle = "Ofrece tu servicio de repartidor en moto y envíos express",
                onClick = { onSelectOption(QuickPublishType.MANDADITO) }
            )
        }
    }
}

@Composable
private fun PublishOptionCard(
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7FAF6)),
        border = BorderStroke(1.dp, Color(0xFFE2EBDD))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconTint,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF161E15)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = Color(0xFF6C7B6A),
                    lineHeight = 16.sp
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color(0xFFBBCBB8),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
