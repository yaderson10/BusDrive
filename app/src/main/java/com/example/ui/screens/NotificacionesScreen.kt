package com.example.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalActivity
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.AppNotification
import com.example.ui.viewmodel.BusDriveViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Exact Tailwind Color Scheme from HTML Specification
private val BgSurface = Color(0xFFF3FCEE)
private val PrimaryGreen = Color(0xFF006E2A)
private val PrimaryContainer = Color(0xFF00C853)
private val OnPrimaryContainer = Color(0xFF004C1B)
private val SurfaceContainerLowest = Color(0xFFFFFFFF)
private val SurfaceContainerLow = Color(0xFFEDF6E8)
private val SurfaceContainer = Color(0xFFE8F1E3)
private val SurfaceContainerHigh = Color(0xFFE2EBDD)
private val SurfaceContainerHighest = Color(0xFFDCE5D8)
private val SurfaceVariant = Color(0xFFDCE5D8)
private val OnSurface = Color(0xFF161E15)
private val OnSurfaceVariant = Color(0xFF3C4A3C)
private val Outline = Color(0xFF6C7B6A)
private val OutlineVariant = Color(0xFFBBCBB8)
private val SuccessGreen = Color(0xFF006B2D)
private val TertiaryContainer = Color(0xFF6FB2F5)
private val OnTertiaryContainer = Color(0xFF004471)
private val WhatsAppGreen = Color(0xFF25D366)

@Composable
fun NotificacionesScreen(
    viewModel: BusDriveViewModel,
    onBack: () -> Unit
) {
    val notifications by viewModel.notifications.collectAsState()
    val timeFormat = remember { SimpleDateFormat("h:mm a · d MMM", Locale("es", "NI")) }

    // Separate notifications into new (unread or recent) and older
    val newNotifications = notifications.filter { !it.isRead }
    val olderNotifications = notifications.filter { it.isRead }

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
                            contentDescription = "Atrás",
                            tint = PrimaryGreen
                        )
                    }

                    Text(
                        text = "Notificaciones",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryGreen,
                        letterSpacing = (-0.5).sp
                    )

                    if (notifications.any { !it.isRead }) {
                        TextButton(
                            onClick = { viewModel.markAllNotificationsRead() },
                            contentPadding = PaddingValues(horizontal = 8.dp)
                        ) {
                            Text(
                                text = "Leídas",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryGreen
                            )
                        }
                    } else {
                        // Placeholder for visual balance
                        Spacer(modifier = Modifier.size(40.dp))
                    }
                }
            }

            // MAIN CONTENT
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 20.dp,
                    end = 20.dp,
                    top = 12.dp,
                    bottom = 90.dp
                ),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // SECTION: NUEVAS
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "Nuevas",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface,
                            modifier = Modifier.padding(bottom = 2.dp)
                        )

                        // 1. Static Showcase Ride Request (matches HTML spec)
                        NotificationRideRequestCard(
                            userName = "Carlos M.",
                            message = "ha solicitado un espacio en tu viaje a León.",
                            timeAgo = "hace 5 min",
                            avatarUrl = "https://images.unsplash.com/photo-1539571696357-5a69c17a67c6?w=400",
                            onAccept = { /* Handled via viewModel if tied to real data */ },
                            onReject = { /* Handled via viewModel */ }
                        )

                        // 2. Static Showcase WhatsApp Message (matches HTML spec)
                        NotificationMessageCard(
                            userName = "Ana R.",
                            message = "te ha enviado un mensaje de WhatsApp por tu estancia.",
                            timeAgo = "hace 12 min",
                            avatarUrl = "https://images.unsplash.com/photo-1544005313-94ddf0286df2?w=400",
                            onClick = { }
                        )

                        // Dynamic unread notifications from database
                        newNotifications.forEach { notif ->
                            DynamicNotificationCard(
                                notif = notif,
                                timeFormat = timeFormat,
                                onClick = { viewModel.handleNotificationClick(notif) },
                                onAccept = { viewModel.respondRideRequest(notif, true) },
                                onReject = { viewModel.respondRideRequest(notif, false) }
                            )
                        }
                    }
                }

                // SECTION: ANTERIORES
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "Anteriores",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface,
                            modifier = Modifier.padding(bottom = 2.dp)
                        )

                        // 1. Static Showcase Paused Ticket Update (matches HTML spec)
                        NotificationStatusCard(
                            icon = Icons.Default.PauseCircle,
                            iconBg = SurfaceVariant,
                            iconTint = OnSurfaceVariant,
                            badgeIcon = Icons.Default.Info,
                            badgeBg = TertiaryContainer,
                            badgeTint = OnTertiaryContainer,
                            text = "Tu publicación de boleto a Managua ha sido pausada.",
                            timeAgo = "ayer"
                        )

                        // 2. Static Showcase Community Event Alert (matches HTML spec)
                        NotificationSimpleCard(
                            icon = Icons.Default.LocalActivity,
                            iconBg = SurfaceContainer,
                            iconTint = PrimaryGreen,
                            text = "¡Nuevo evento en tu comunidad! Descubre las actividades de este fin de semana.",
                            timeAgo = "hace 2 días"
                        )

                        // Dynamic older/read notifications from database
                        olderNotifications.forEach { notif ->
                            DynamicNotificationCard(
                                notif = notif,
                                timeFormat = timeFormat,
                                onClick = { viewModel.handleNotificationClick(notif) },
                                onAccept = { viewModel.respondRideRequest(notif, true) },
                                onReject = { viewModel.respondRideRequest(notif, false) }
                            )
                        }
                    }
                }
            }
        }
    }
}

// 1. Ride Request Card (Exact HTML styling)
@Composable
private fun NotificationRideRequestCard(
    userName: String,
    message: String,
    timeAgo: String,
    avatarUrl: String,
    onAccept: () -> Unit,
    onReject: () -> Unit
) {
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
        border = BorderStroke(1.dp, Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Avatar with overlay badge
            Box(
                modifier = Modifier.size(48.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                AsyncImage(
                    model = avatarUrl,
                    contentDescription = userName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(SurfaceContainer)
                )

                // Sub-badge icon
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .background(PrimaryContainer)
                        .border(BorderStroke(2.dp, SurfaceContainerLowest), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.DirectionsCar,
                        contentDescription = null,
                        tint = OnPrimaryContainer,
                        modifier = Modifier.size(13.dp)
                    )
                }
            }

            // Text & Buttons
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = OnSurface)) {
                            append(userName)
                        }
                        append(" ")
                        withStyle(SpanStyle(color = OnSurface)) {
                            append(message)
                        }
                    },
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )

                Text(
                    text = timeAgo,
                    fontSize = 12.sp,
                    color = OnSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onAccept,
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .shadow(2.dp, RoundedCornerShape(9999.dp)),
                        shape = RoundedCornerShape(9999.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryContainer,
                            contentColor = Color.White
                        ),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        Text(
                            text = "Aceptar",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    OutlinedButton(
                        onClick = onReject,
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp),
                        shape = RoundedCornerShape(9999.dp),
                        border = BorderStroke(1.dp, Outline),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = PrimaryGreen
                        ),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        Text(
                            text = "Rechazar",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryGreen
                        )
                    }
                }
            }
        }
    }
}

// 2. Message Card with Green Left Accent Bar (Exact HTML styling)
@Composable
private fun NotificationMessageCard(
    userName: String,
    message: String,
    timeAgo: String,
    avatarUrl: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color(0x0A000000),
                spotColor = Color(0x0A000000)
            )
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        border = BorderStroke(1.dp, Color.Transparent)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // Left Accent Bar (w-1 bg-primary-container)
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(4.dp)
                    .background(PrimaryContainer)
                    .align(Alignment.CenterStart)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                // Avatar with WhatsApp badge
                Box(
                    modifier = Modifier.size(48.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    AsyncImage(
                        model = avatarUrl,
                        contentDescription = userName,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(SurfaceContainer)
                    )

                    // WhatsApp Badge
                    Box(
                        modifier = Modifier
                            .size(22.dp)
                            .clip(CircleShape)
                            .background(WhatsAppGreen)
                            .border(BorderStroke(2.dp, SurfaceContainerLowest), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Chat,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(13.dp)
                        )
                    }
                }

                // Text
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = OnSurface)) {
                                append(userName)
                            }
                            append(" ")
                            withStyle(SpanStyle(color = OnSurface)) {
                                append(message)
                            }
                        },
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )

                    Text(
                        text = timeAgo,
                        fontSize = 12.sp,
                        color = OnSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

// 3. Status Notification Card (Paused / Updated)
@Composable
private fun NotificationStatusCard(
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    badgeIcon: ImageVector,
    badgeBg: Color,
    badgeTint: Color,
    text: String,
    timeAgo: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color(0x0A000000),
                spotColor = Color(0x0A000000)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        border = BorderStroke(1.dp, Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier.size(48.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(iconBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .background(badgeBg)
                        .border(BorderStroke(2.dp, SurfaceContainerLowest), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = badgeIcon,
                        contentDescription = null,
                        tint = badgeTint,
                        modifier = Modifier.size(13.dp)
                    )
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = text,
                    fontSize = 14.sp,
                    color = OnSurface,
                    lineHeight = 20.sp
                )

                Text(
                    text = timeAgo,
                    fontSize = 12.sp,
                    color = OnSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

// 4. Simple Alert Card (Community / General Event)
@Composable
private fun NotificationSimpleCard(
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    text: String,
    timeAgo: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color(0x0A000000),
                spotColor = Color(0x0A000000)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        border = BorderStroke(1.dp, Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = text,
                    fontSize = 14.sp,
                    color = OnSurface,
                    lineHeight = 20.sp
                )

                Text(
                    text = timeAgo,
                    fontSize = 12.sp,
                    color = OnSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

// 5. Dynamic Card for Real App Notifications
@Composable
private fun DynamicNotificationCard(
    notif: AppNotification,
    timeFormat: SimpleDateFormat,
    onClick: () -> Unit,
    onAccept: () -> Unit,
    onReject: () -> Unit
) {
    val isRideRequest = notif.type == "solicitud"
    val isTicket = notif.type == "nuevo_boleto" || notif.category == "BOLETO"
    val isRide = notif.type == "nuevo_ride" || notif.category == "RIDE"
    val isStay = notif.type == "nueva_estancia" || notif.category == "STAY"

    val icon = when {
        isRideRequest || isRide -> Icons.Default.DirectionsCar
        isTicket -> Icons.Default.ConfirmationNumber
        isStay -> Icons.Default.Home
        else -> Icons.Default.DirectionsBus
    }

    val iconBg = if (notif.isRead) SurfaceContainerHigh else SurfaceContainer
    val iconTint = if (notif.isRead) Outline else PrimaryGreen

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = if (!notif.isRead) 2.dp else 1.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color(0x0A000000),
                spotColor = Color(0x0A000000)
            )
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        border = BorderStroke(1.dp, if (!notif.isRead) PrimaryContainer.copy(alpha = 0.4f) else Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                if (notif.fromName.isNotBlank()) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = OnSurface)) {
                                append(notif.fromName)
                            }
                            append(" ")
                            withStyle(SpanStyle(color = OnSurface)) {
                                append(notif.text)
                            }
                        },
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                } else {
                    Text(
                        text = notif.title.ifBlank { notif.text },
                        fontSize = 14.sp,
                        fontWeight = if (!notif.isRead) FontWeight.Bold else FontWeight.Medium,
                        color = OnSurface,
                        lineHeight = 20.sp
                    )
                    if (notif.title.isNotBlank() && notif.text.isNotBlank()) {
                        Text(
                            text = notif.text,
                            fontSize = 13.sp,
                            color = OnSurfaceVariant,
                            modifier = Modifier.padding(top = 2.dp),
                            lineHeight = 18.sp
                        )
                    }
                }

                Text(
                    text = timeFormat.format(Date(notif.timestamp)),
                    fontSize = 12.sp,
                    color = OnSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )

                if (isRideRequest && !notif.resuelta) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = onAccept,
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp),
                            shape = RoundedCornerShape(9999.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PrimaryContainer,
                                contentColor = Color.White
                            ),
                            contentPadding = PaddingValues(horizontal = 16.dp)
                        ) {
                            Text("Aceptar", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }

                        OutlinedButton(
                            onClick = onReject,
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp),
                            shape = RoundedCornerShape(9999.dp),
                            border = BorderStroke(1.dp, Outline),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = PrimaryGreen
                            ),
                            contentPadding = PaddingValues(horizontal = 16.dp)
                        ) {
                            Text("Rechazar", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PrimaryGreen)
                        }
                    }
                }
            }
        }
    }
}


