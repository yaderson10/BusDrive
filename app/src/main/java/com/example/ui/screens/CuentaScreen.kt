package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.viewmodel.BusDriveViewModel
import com.example.ui.viewmodel.MainTab

private val BgSurface = Color(0xFFF3FCEE)
private val PrimaryGreen = Color(0xFF006E2A)
private val PrimaryContainer = Color(0xFF00C853)
private val OnPrimaryContainer = Color(0xFF004C1B)
private val SurfaceContainerLowest = Color(0xFFFFFFFF)
private val SurfaceContainer = Color(0xFFE8F1E3)
private val SurfaceContainerHigh = Color(0xFFE2EBDD)
private val OnSurface = Color(0xFF161E15)
private val OnSurfaceVariant = Color(0xFF3C4A3C)
private val OutlineVariant = Color(0xFFBBCBB8)
private val ErrorRed = Color(0xFFBA1A1A)

@Composable
fun CuentaScreen(viewModel: BusDriveViewModel) {
    val context = LocalContext.current
    val currentUser by viewModel.currentUser.collectAsState()
    val providerProfile by viewModel.currentProviderProfile.collectAsState()

    var showLogoutDialog by remember { mutableStateOf(false) }

    val displayName = if (currentUser != null) {
        providerProfile.fullName.ifBlank { currentUser?.name ?: "Carlos Mendoza" }
    } else {
        "Carlos Mendoza"
    }

    val displayEmail = currentUser?.email ?: "carlos.mendoza@email.com"
    val displayRole = currentUser?.userRole ?: "Pasajero Frecuente"

    val photoUrl = if (currentUser != null) {
        providerProfile.profilePhotoUrl.ifBlank {
            currentUser?.photoUrl ?: "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=400"
        }
    } else {
        "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=400"
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
                    IconButton(
                        onClick = { viewModel.selectedTab.value = MainTab.EXPLORAR },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = PrimaryGreen
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "Cuenta",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryGreen
                    )
                }
            }

            // SCROLLABLE CONTENT
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 12.dp)
                    .padding(bottom = 96.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // PROFILE HERO CARD
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 3.dp,
                            shape = RoundedCornerShape(18.dp),
                            ambientColor = Color(0x0A000000),
                            spotColor = Color(0x0A000000)
                        )
                        .clickable { viewModel.showAccountDetailsScreen.value = true },
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                    border = BorderStroke(1.dp, SurfaceContainerHigh.copy(alpha = 0.6f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Surface(
                            modifier = Modifier
                                .size(64.dp)
                                .shadow(2.dp, CircleShape),
                            shape = CircleShape,
                            color = SurfaceContainer,
                            border = BorderStroke(2.dp, PrimaryContainer.copy(alpha = 0.5f))
                        ) {
                            AsyncImage(
                                model = photoUrl,
                                contentDescription = "Foto de perfil",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = displayName,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnSurface
                            )
                            Text(
                                text = displayEmail,
                                fontSize = 13.sp,
                                color = OnSurfaceVariant
                            )
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = PrimaryContainer.copy(alpha = 0.15f),
                                modifier = Modifier.padding(top = 2.dp)
                            ) {
                                Text(
                                    text = displayRole,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = OnPrimaryContainer,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Ver perfil",
                            tint = OnSurfaceVariant,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                // MENU OPTIONS CARD
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 2.dp,
                            shape = RoundedCornerShape(18.dp),
                            ambientColor = Color(0x0A000000),
                            spotColor = Color(0x0A000000)
                        ),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                    border = BorderStroke(1.dp, SurfaceContainerHigh.copy(alpha = 0.6f))
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        // 1. Mi Perfil
                        MenuRowItem(
                            icon = Icons.Default.Person,
                            title = "Mi Perfil",
                            onClick = { viewModel.showAccountDetailsScreen.value = true }
                        )

                        HorizontalDivider(color = OutlineVariant.copy(alpha = 0.3f))

                        // 2. Mis Publicaciones
                        MenuRowItem(
                            icon = Icons.Default.HistoryEdu,
                            title = "Mis Publicaciones",
                            onClick = { viewModel.showMyTicketsScreen.value = true }
                        )

                        HorizontalDivider(color = OutlineVariant.copy(alpha = 0.3f))

                        // 3. Notificaciones
                        MenuRowItem(
                            icon = Icons.Default.Notifications,
                            title = "Notificaciones",
                            onClick = { viewModel.showNotifsScreen.value = true }
                        )

                        HorizontalDivider(color = OutlineVariant.copy(alpha = 0.3f))

                        // 4. Configuración
                        MenuRowItem(
                            icon = Icons.Default.Settings,
                            title = "Configuración",
                            onClick = { viewModel.showSettingsScreen.value = true }
                        )

                        HorizontalDivider(color = OutlineVariant.copy(alpha = 0.3f))

                        // 5. Términos y Privacidad
                        MenuRowItem(
                            icon = Icons.Default.Description,
                            title = "Términos y Privacidad",
                            onClick = { viewModel.showTermsScreen.value = true }
                        )

                        HorizontalDivider(color = OutlineVariant.copy(alpha = 0.3f))

                        // 6. Ayuda
                        MenuRowItem(
                            icon = Icons.AutoMirrored.Filled.HelpOutline,
                            title = "Ayuda",
                            onClick = { viewModel.showHelpScreen.value = true }
                        )
                    }
                }

                // LOGOUT BUTTON
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 2.dp,
                            shape = RoundedCornerShape(18.dp),
                            ambientColor = Color(0x0A000000),
                            spotColor = Color(0x0A000000)
                        )
                        .clickable { showLogoutDialog = true },
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                    border = BorderStroke(1.dp, ErrorRed.copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(ErrorRed.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Logout,
                                contentDescription = null,
                                tint = ErrorRed,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Text(
                            text = "Cerrar sesión",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = ErrorRed,
                            modifier = Modifier.weight(1f)
                        )

                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = ErrorRed.copy(alpha = 0.6f),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        // LOGOUT CONFIRMATION DIALOG
        if (showLogoutDialog) {
            AlertDialog(
                onDismissRequest = { showLogoutDialog = false },
                shape = RoundedCornerShape(20.dp),
                containerColor = SurfaceContainerLowest,
                title = {
                    Text(
                        text = "¿Cerrar sesión?",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurface
                    )
                },
                text = {
                    Text(
                        text = "¿Estás seguro de que deseas salir de tu cuenta en BusDrive?",
                        fontSize = 14.sp,
                        color = OnSurfaceVariant
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.logout()
                            showLogoutDialog = false
                            Toast.makeText(context, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ErrorRed)
                    ) {
                        Text("Cerrar sesión", color = Color.White)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showLogoutDialog = false }) {
                        Text("Cancelar", color = OnSurfaceVariant)
                    }
                }
            )
        }
    }
}

@Composable
private fun MenuRowItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(SurfaceContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = PrimaryGreen,
                    modifier = Modifier.size(20.dp)
                )
            }

            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = OnSurface
            )
        }

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = OnSurfaceVariant,
            modifier = Modifier.size(20.dp)
        )
    }
}
