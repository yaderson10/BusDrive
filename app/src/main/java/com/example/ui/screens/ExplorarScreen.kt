package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.ui.theme.CardBlueBg
import com.example.ui.theme.CardBlueBtn
import com.example.ui.theme.CardGreenBg
import com.example.ui.theme.CardGreenBtn
import com.example.ui.theme.CardPurpleBg
import com.example.ui.theme.CardPurpleBtn
import com.example.ui.theme.InkBlack
import com.example.ui.theme.LimeBrand
import com.example.ui.theme.LimeBrandDark
import com.example.ui.theme.LimeBrandGreen
import com.example.ui.theme.LineBorder
import com.example.ui.theme.MutedGray
import com.example.ui.theme.PageBackground
import com.example.ui.theme.SurfaceGray
import com.example.ui.viewmodel.BusDriveViewModel
import com.example.ui.viewmodel.MainTab
import com.example.ui.viewmodel.ViajeSubTab

@Composable
fun ExplorarScreen(
    viewModel: BusDriveViewModel,
    onOpenSearch: () -> Unit
) {
    val origen by viewModel.origenVal.collectAsState()
    val destino by viewModel.destinoVal.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()
    val providerProfile by viewModel.currentProviderProfile.collectAsState()
    val notifs by viewModel.notifications.collectAsState()
    val routes by viewModel.allRoutes.collectAsState()
    val tickets by viewModel.allTickets.collectAsState()
    val rides by viewModel.allRides.collectAsState()
    val stays by viewModel.allStays.collectAsState()
    val favoriteRouteIds by viewModel.favoriteRouteIds.collectAsState()

    val unreadNotifsCount = notifs.count { !it.isRead }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PageBackground)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
    ) {
        // TOP BAR
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // BRAND LOGO WITH SMILEY FACE
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .border(1.dp, LineBorder, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_busdrive_logo),
                        contentDescription = "BusDrive Logo",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "BusDrive",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    color = InkBlack,
                    letterSpacing = (-0.5).sp
                )
            }

            // USER / NOTIFS
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box {
                    IconButton(
                        onClick = { viewModel.showNotifsScreen.value = true },
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF1F4F8))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notificaciones",
                            tint = InkBlack,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    val notifDisplay = if (unreadNotifsCount > 0) unreadNotifsCount else 2
                    Box(
                        modifier = Modifier
                            .size(19.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE53935))
                            .align(Alignment.TopEnd),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = notifDisplay.toString(),
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                val activePhoto = currentUser?.photoUrl?.ifBlank { null } ?: providerProfile.profilePhotoUrl.ifBlank { null }
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF0B1320))
                        .border(2.dp, LimeBrand, CircleShape)
                        .clickable { viewModel.selectedTab.value = MainTab.CONFIG },
                    contentAlignment = Alignment.Center
                ) {
                    if (!activePhoto.isNullOrBlank()) {
                        AsyncImage(
                            model = activePhoto,
                            contentDescription = "Perfil",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        val initialLetter = (currentUser?.name?.firstOrNull() ?: providerProfile.fullName.firstOrNull() ?: 'U').uppercase()
                        Text(
                            text = initialLetter,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }

        // HERO BANNER ("PORTADA")
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(22.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(185.dp)
            ) {
                // BACKGROUND IMAGE
                Image(
                    painter = painterResource(id = R.drawable.img_hero_bus_banner),
                    contentDescription = "Hero Bus Banner",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // SOFT GRADIENT OVERLAY (Brighter and softer, gentle readability)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF0A1E33).copy(alpha = 0.65f),
                                    Color(0xFF0A1E33).copy(alpha = 0.40f),
                                    Color(0xFF0A1E33).copy(alpha = 0.10f),
                                    Color.Transparent
                                ),
                                startX = 0f,
                                endX = 650f
                            )
                        )
                )

                // BANNER CONTENT
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // TOP ROW: CAROUSEL DOTS / OPTIONS
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF8CE600))
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Box(
                                modifier = Modifier
                                    .size(5.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.5f))
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Box(
                                modifier = Modifier
                                    .size(5.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.5f))
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = null,
                                tint = Color.White.copy(alpha = 0.85f),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    // BOTTOM TEXT
                    Column {
                        Text(
                            text = "Viaja cómodo,\nviaja seguro",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                            lineHeight = 25.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Consulta horarios y\nllega a tu destino.",
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.9f),
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // SEARCH CARD (White rounded container enclosing "¿A dónde viajas?", Origen, Destino, Swap, and Búsqueda button)
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            shadowElevation = 3.dp,
            border = androidx.compose.foundation.BorderStroke(1.dp, LineBorder)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "¿A dónde viajas?",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    color = InkBlack
                )
                Text(
                    text = "Consulta horarios de buses",
                    fontSize = 13.sp,
                    color = MutedGray,
                    modifier = Modifier.padding(top = 2.dp, bottom = 14.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // ORIGEN & DESTINO INPUTS (Two separate boxes)
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        // ORIGEN BOX
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color(0xFFF3F5F8))
                                .clickable { viewModel.showPicker.value = "origen" }
                                .padding(horizontal = 14.dp, vertical = 11.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF65A30D))
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Origen",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MutedGray
                                )
                                Text(
                                    text = if (origen.isNotBlank()) origen else "Managua",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = InkBlack
                                )
                            }
                            IconButton(
                                onClick = { viewModel.clearOrigen() },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Borrar",
                                    tint = Color(0xFF6B7280),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // DESTINO BOX
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color(0xFFF3F5F8))
                                .clickable { viewModel.showPicker.value = "destino" }
                                .padding(horizontal = 14.dp, vertical = 11.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFF59E0B))
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Destino",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MutedGray
                                )
                                Text(
                                    text = if (destino.isNotBlank()) destino else "Puerto Cabezas",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = InkBlack
                                )
                            }
                            IconButton(
                                onClick = { viewModel.clearDestino() },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Borrar",
                                    tint = Color(0xFF6B7280),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // SWAP BUTTON
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White)
                            .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(16.dp))
                            .clickable { viewModel.swapOrigenDestino() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.SwapVert,
                            contentDescription = "Invertir",
                            tint = InkBlack,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // FULL WIDTH LIME GREEN BUTTON "Búsqueda"
                Button(
                    onClick = {
                        val effOrigen = if (origen.isBlank()) "Managua" else origen
                        val effDestino = if (destino.isBlank()) "Puerto Cabezas" else destino
                        viewModel.setOrigen(effOrigen)
                        viewModel.setDestino(effDestino)
                        onOpenSearch()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LimeBrand,
                        contentColor = InkBlack
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = InkBlack,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Búsqueda",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // FOOTNOTE INFO TEXT
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = Color(0xFF6B7280),
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Elige un origen para ver todas las salidas, o un par origen–destino para filtrar.",
                fontSize = 12.sp,
                color = Color(0xFF6B7280),
                lineHeight = 16.sp
            )
        }

        val favoriteRoutesList = remember(routes, favoriteRouteIds) {
            routes.filter { favoriteRouteIds.contains(it.id) }
        }

        if (favoriteRoutesList.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(20.dp),
                color = Color(0xFFFFFBEB),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFDE68A)),
                shadowElevation = 1.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "⭐", fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Rutas Favoritas Monitoreadas",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF92400E)
                                )
                                Text(
                                    text = "${favoriteRoutesList.size} rutas con alertas automáticas de retrasos",
                                    fontSize = 12.sp,
                                    color = Color(0xFFB45309)
                                )
                            }
                        }

                        Button(
                            onClick = {
                                viewModel.showOnlyFavorites.value = true
                                onOpenSearch()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFF59E0B),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Text("Ver todas ›", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(favoriteRoutesList) { route ->
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color.White,
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFDE68A)),
                                modifier = Modifier.clickable {
                                    viewModel.selectedRouteDetail.value = route
                                    onOpenSearch()
                                }
                            ) {
                                Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                                    Text(
                                        text = "${route.origen} → ${route.destino}",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = InkBlack
                                    )
                                    Text(
                                        text = "${route.operador} · ${route.salida}",
                                        fontSize = 11.sp,
                                        color = MutedGray
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ==========================================
        // LO QUE NECESITAS PARA TU VIAJE SECTION
        // ==========================================
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Lo que necesitas para tu viaje",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = InkBlack
                )
                Text(
                    text = "Dime a dónde vas y te ayudo: cómo llegar, un ride o dónde quedarte.",
                    fontSize = 12.sp,
                    color = MutedGray,
                    modifier = Modifier.padding(top = 2.dp),
                    lineHeight = 16.sp
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Ver más",
                color = Color(0xFF2563EB),
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                modifier = Modifier.clickable {
                    viewModel.selectedTab.value = MainTab.VIAJES
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 3 DISCOVERY VISUAL CARDS (Soft vibrant pastel/gradient styling)
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // CARD 1: BOLETOS
            item {
                Card(
                    modifier = Modifier
                        .width(260.dp)
                        .height(390.dp)
                        .clickable {
                            viewModel.selectedTab.value = MainTab.VIAJES
                            viewModel.selectedViajeSubTab.value = ViajeSubTab.BOLETO
                        },
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF2563EB)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFF3B82F6),
                                        Color(0xFF1D4ED8)
                                    )
                                )
                            )
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            // Badge Tag
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.White.copy(alpha = 0.20f))
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "🎟️", fontSize = 11.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "BOLETOS",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = "Encuentra\ntu boleto",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White,
                                    lineHeight = 26.sp
                                )
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color(0xFFFDE047),
                                    modifier = Modifier.padding(top = 4.dp)
                                ) {
                                    Text(
                                        text = "★ BOLETO",
                                        color = Color(0xFF713F12),
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Black,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Viaja al mejor precio",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE0F2FE)
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Boletos publicados por otros viajeros para tu destino.",
                                fontSize = 12.sp,
                                color = Color(0xFFEFF6FF).copy(alpha = 0.9f),
                                lineHeight = 16.sp
                            )
                        }

                        // Bus Card Visual at bottom
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                                .clip(RoundedCornerShape(16.dp))
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_card_ticket),
                                contentDescription = "Boletos de bus",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }

            // CARD 2: RAID / RIDE
            item {
                Card(
                    modifier = Modifier
                        .width(260.dp)
                        .height(390.dp)
                        .clickable {
                            viewModel.selectedTab.value = MainTab.VIAJES
                            viewModel.selectedViajeSubTab.value = ViajeSubTab.RIDE
                        },
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF059669)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFF10B981),
                                        Color(0xFF047857)
                                    )
                                )
                            )
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            // Badge Tag
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.White.copy(alpha = 0.20f))
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "🚗", fontSize = 11.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "RAID / RIDE",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "Encuentra\ntu ride",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White,
                                lineHeight = 26.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Comparte tu viaje\ny ahorra",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFD1FAE5),
                                lineHeight = 18.sp
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Personas que viajan y tienen espacios disponibles.",
                                fontSize = 12.sp,
                                color = Color(0xFFECFDF5).copy(alpha = 0.9f),
                                lineHeight = 16.sp
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // Action button inside card
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFF065F46),
                                modifier = Modifier.clickable {
                                    viewModel.selectedTab.value = MainTab.VIAJES
                                    viewModel.selectedViajeSubTab.value = ViajeSubTab.RIDE
                                }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Buscar ride  ➔",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }

                        // Car visual at bottom
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(125.dp)
                                .clip(RoundedCornerShape(16.dp))
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_card_ride),
                                contentDescription = "Ride compartido",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }

            // CARD 3: ESTANCIAS
            item {
                Card(
                    modifier = Modifier
                        .width(260.dp)
                        .height(390.dp)
                        .clickable {
                            viewModel.selectedTab.value = MainTab.VIAJES
                            viewModel.selectedViajeSubTab.value = ViajeSubTab.STAY
                        },
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF6366F1)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFF818CF8),
                                        Color(0xFF4F46E5)
                                    )
                                )
                            )
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            // Badge Tag
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.White.copy(alpha = 0.20f))
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "🏨", fontSize = 11.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "ESTANCIAS",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "Encuentra tu\nestancia ideal",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White,
                                lineHeight = 26.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Hoteles, hostales y alojamientos para tu comodidad.",
                                fontSize = 12.sp,
                                color = Color(0xFFEEF2FF),
                                lineHeight = 16.sp
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // Action button inside card
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFF4338CA),
                                modifier = Modifier.clickable {
                                    viewModel.selectedTab.value = MainTab.VIAJES
                                    viewModel.selectedViajeSubTab.value = ViajeSubTab.STAY
                                }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Ver estancias  ➔",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }

                        // Room visual at bottom
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(125.dp)
                                .clip(RoundedCornerShape(16.dp))
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_card_stay),
                                contentDescription = "Estancia ideal",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // ==========================================
        // 3. BANNER INFERIOR "VIAJA SEGURO. VIAJA INTELIGENTE" (BUSDRIVE)
        // ==========================================
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                // Background Coach Bus image on right
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_hero_bus_banner),
                            contentDescription = "BusDrive Banner",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        // Gradient from left to blend
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.horizontalGradient(
                                        colors = listOf(
                                            Color(0xFFF0FDF4),
                                            Color(0xFFF0FDF4).copy(alpha = 0.95f),
                                            Color(0xFFF0FDF4).copy(alpha = 0.5f),
                                            Color.Transparent
                                        ),
                                        startX = 0f,
                                        endX = 400f
                                    )
                                )
                        )
                    }
                }

                // Left Content: Title, subtitle, and dark green button
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Viaja seguro.\nViaja inteligente.",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black,
                            color = InkBlack,
                            lineHeight = 26.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Consulta horarios y disfruta\ndel camino.",
                            fontSize = 13.sp,
                            color = Color(0xFF334155),
                            lineHeight = 17.sp
                        )
                    }

                    Button(
                        onClick = {
                            val effOrigen = if (origen.isBlank()) "Managua" else origen
                            val effDestino = if (destino.isBlank()) "Puerto Cabezas" else destino
                            viewModel.setOrigen(effOrigen)
                            viewModel.setDestino(effDestino)
                            onOpenSearch()
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF15803D),
                            contentColor = Color.White
                        ),
                        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = "Ver horarios",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }

                // Brand mark on top right
                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .border(1.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_busdrive_logo),
                            contentDescription = "BusDrive",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "BusDrive",
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp,
                        color = InkBlack
                    )
                }
            }
        }
    }
}

private data class CarouselCardData(
    val title: String,
    val description: String,
    val buttonText: String,
    val icon: String,
    val cardBg: Color,
    val btnColor: Color,
    val busGraphic: Boolean = false,
    val carGraphic: Boolean = false,
    val stayGraphic: Boolean = false,
    val onClick: () -> Unit
)

