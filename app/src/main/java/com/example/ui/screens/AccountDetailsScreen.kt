package com.example.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.viewmodel.BusDriveViewModel

// Color Palette matching HTML Tailwind Design System
private val BgCanvas = Color(0xFFFFFFFF)
private val PrimaryGreen = Color(0xFF006E2A)
private val PrimaryContainer = Color(0xFF00C853)
private val OnPrimary = Color(0xFFFFFFFF)
private val SurfaceContainer = Color(0xFFF8FCF5)
private val SurfaceVariant = Color(0xFFDCE5D8)
private val OnSurface = Color(0xFF161E15)
private val OnSurfaceVariant = Color(0xFF3C4A3C)
private val OutlineColor = Color(0xFF6C7B6A)
private val OutlineVariant = Color(0xFFBBCBB8)

@Composable
fun AccountDetailsScreen(
    viewModel: BusDriveViewModel,
    onBack: () -> Unit
) {
    val currentUser by viewModel.currentUser.collectAsState()
    val providerProfile by viewModel.currentProviderProfile.collectAsState()

    var fullName by remember(currentUser, providerProfile) {
        mutableStateOf(
            providerProfile.fullName.ifBlank {
                currentUser?.name ?: "Carlos Mendoza"
            }
        )
    }

    val email = remember(currentUser, providerProfile) {
        providerProfile.email.ifBlank {
            currentUser?.email ?: "carlos.m@busdrive.com"
        }
    }

    var phone by remember(currentUser, providerProfile) {
        mutableStateOf(
            providerProfile.phone.ifBlank {
                currentUser?.phone ?: "+52 55 1234 5678"
            }
        )
    }

    var photoUrl by remember(currentUser, providerProfile) {
        mutableStateOf(
            providerProfile.profilePhotoUrl.ifBlank {
                currentUser?.photoUrl ?: "https://lh3.googleusercontent.com/aida-public/AB6AXuCznSnSNByXgndOdQ3OATvDFGxhwqzPCpUEvMxizAP-vX4AIPU0zMhZTzxoW2aQWKndjj4_6hG4Hv0C8ymsWfO1ySy8fhLdX7l4yGy2WmH5TPpVQzELJExv4rhsf3T5RU22nmANJ1ipsLfdQfV_DGViEsutv2lQ1QfE7vIemUUeYocz59d-ynBm-bpTLbJQsAl1tZnzUPWJIyNK_RL6ffXM_IfmvM1s8D0sHzgfnV_jxB8ARDq4GZKefA"
            }
        )
    }

    var savedSuccessFeedback by remember { mutableStateOf(false) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            photoUrl = it.toString()
        }
    }

    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BgCanvas
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            // TOP APP BAR
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = BgCanvas,
                shadowElevation = 0.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = OnSurface
                        )
                    }

                    Text(
                        text = "Editar Perfil",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurface,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )

                    // Spacer for centering
                    Spacer(modifier = Modifier.size(40.dp))
                }
            }

            // MAIN SCROLLABLE CONTENT
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 20.dp, vertical = 12.dp)
                    .padding(bottom = 60.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // PROFILE PICTURE SECTION
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(bottom = 24.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(112.dp)
                            .clickable { photoPickerLauncher.launch("image/*") }
                    ) {
                        // Profile Avatar
                        Box(
                            modifier = Modifier
                                .size(112.dp)
                                .clip(CircleShape)
                                .background(SurfaceContainer)
                                .border(4.dp, Color.White, CircleShape)
                                .shadow(elevation = 6.dp, shape = CircleShape)
                        ) {
                            AsyncImage(
                                model = photoUrl,
                                contentDescription = "Foto de perfil",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        // Edit Badge Button (bottom-right)
                        Surface(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .size(32.dp)
                                .shadow(2.dp, CircleShape)
                                .clickable { photoPickerLauncher.launch("image/*") },
                            shape = CircleShape,
                            color = PrimaryContainer,
                            border = BorderStroke(2.dp, Color.White)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Editar foto",
                                    tint = OnPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Cambiar foto",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryContainer,
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .clickable { photoPickerLauncher.launch("image/*") }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                // SUCCESS FEEDBACK BANNER
                AnimatedVisibility(
                    visible = savedSuccessFeedback,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFFE8F5E9),
                        border = BorderStroke(1.dp, Color(0xFF81C784)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Color(0xFF2E7D32),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "¡Cambios guardados con éxito!",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1B5E20)
                            )
                        }
                    }
                }

                // FORM SECTION
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Field 1: Full Name
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "Nombre completo",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                        ProfileCustomTextField(
                            value = fullName,
                            onValueChange = {
                                fullName = it
                                savedSuccessFeedback = false
                            },
                            placeholder = "Ingresa tu nombre",
                            icon = Icons.Default.Person
                        )
                    }

                    // Field 2: WhatsApp Phone Number
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "Número de WhatsApp",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                        ProfileCustomTextField(
                            value = phone,
                            onValueChange = {
                                phone = it
                                savedSuccessFeedback = false
                            },
                            placeholder = "+52 55 XXXX XXXX",
                            icon = Icons.Default.Call,
                            keyboardType = KeyboardType.Phone
                        )
                    }

                    // Field 3: Read-only Email
                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.alpha(0.8f)
                    ) {
                        Text(
                            text = "Correo electrónico",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                        ProfileCustomTextField(
                            value = email,
                            onValueChange = {},
                            placeholder = "correo@ejemplo.com",
                            icon = Icons.Default.Lock,
                            readOnly = true,
                            containerColor = SurfaceVariant
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = OutlineColor,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "El correo no se puede cambiar directamente",
                                fontSize = 12.sp,
                                color = OutlineColor
                            )
                        }
                    }

                    // SECTION: CONFIGURACIÓN DE CUENTA
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "Configuración de cuenta",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )

                        // Option 1: Cambiar contraseña
                        AccountSettingRow(
                            icon = Icons.Default.Key,
                            title = "Cambiar contraseña",
                            onClick = { viewModel.showChangePasswordScreen.value = true }
                        )

                        // Option 2: Notificaciones
                        AccountSettingRow(
                            icon = Icons.Default.Notifications,
                            title = "Notificaciones",
                            onClick = { viewModel.showNotifsScreen.value = true }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // ACTION BUTTONS
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Button 1: Guardar Cambios
                        Button(
                            onClick = {
                                val nameParts = fullName.trim().split(" ")
                                val fName = nameParts.firstOrNull() ?: fullName
                                val lName = if (nameParts.size > 1) nameParts.drop(1).joinToString(" ") else ""

                                val updated = providerProfile.copy(
                                    fullName = fullName,
                                    firstName = fName,
                                    lastName = lName,
                                    phone = phone,
                                    email = email,
                                    city = providerProfile.city.ifBlank { "Nicaragua" },
                                    municipality = providerProfile.municipality.ifBlank { "Nicaragua" },
                                    profilePhotoUrl = photoUrl
                                )
                                viewModel.updateProviderProfile(updated)
                                savedSuccessFeedback = true
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PrimaryContainer,
                                contentColor = OnPrimary
                            )
                        ) {
                            Text(
                                text = "Guardar Cambios",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnPrimary
                            )
                        }

                        // Button 2: Cancelar
                        TextButton(
                            onClick = onBack,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "Cancelar",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryContainer
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileCustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: ImageVector,
    readOnly: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    containerColor: Color = SurfaceContainer
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(8.dp),
        color = containerColor,
        border = BorderStroke(1.dp, OutlineVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = OutlineColor,
                modifier = Modifier.size(20.dp)
            )

            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                readOnly = readOnly,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                textStyle = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (readOnly) OnSurfaceVariant else OnSurface
                ),
                cursorBrush = SolidColor(PrimaryGreen),
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            fontSize = 15.sp,
                            color = OutlineColor
                        )
                    }
                    innerTextField()
                }
            )
        }
    }
}

@Composable
private fun AccountSettingRow(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        color = SurfaceContainer,
        border = BorderStroke(1.dp, OutlineVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = OutlineColor,
                    modifier = Modifier.size(22.dp)
                )

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
                tint = OutlineColor,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
