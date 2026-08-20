package com.example.ui.screens

import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.viewmodel.BusDriveViewModel
import com.example.ui.viewmodel.MainTab

// Colors matching HTML Tailwind configuration
private val BgSurface = Color(0xFFF3FCEE)
private val PrimaryGreen = Color(0xFF006E2A)
private val PrimaryContainer = Color(0xFF00C853)
private val SecondaryGreen = Color(0xFF29695B)
private val SurfaceContainerLowest = Color(0xFFFFFFFF)
private val SurfaceContainerHigh = Color(0xFFE2EBDD)
private val OnSurface = Color(0xFF161E15)
private val OnSurfaceVariant = Color(0xFF3C4A3C)
private val OutlineVariant = Color(0xFFBBCBB8)
private val OutlineColor = Color(0xFF6C7B6A)
private val ErrorRed = Color(0xFFBA1A1A)
private val ErrorContainer = Color(0xFFFFDAD6)

@Composable
fun ConfiguracionScreen(
    viewModel: BusDriveViewModel,
    onBack: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val currentUser by viewModel.currentUser.collectAsState()

    // Local toggle states
    var pushNotificationsEnabled by remember { mutableStateOf(true) }
    var emailNotificationsEnabled by remember { mutableStateOf(false) }
    var biometricEnabled by remember { mutableStateOf(true) }

    // Preference values
    var selectedLanguage by remember { mutableStateOf("Español") }
    var selectedCurrency by remember { mutableStateOf("NIO (C$)") }
    var selectedTheme by remember { mutableStateOf("Sistema") }

    // Dialog states
    var showChangePasswordDialog by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }
    var showCurrencyDialog by remember { mutableStateOf(false) }
    var showThemeDialog by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showDeleteAccountDialog by remember { mutableStateOf(false) }

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
                        onClick = {
                            if (onBack != null) {
                                onBack()
                            } else {
                                viewModel.showSettingsScreen.value = false
                            }
                        },
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
                        text = "Configuración",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryGreen
                    )
                }
            }

            // MAIN SCROLLABLE CONTENT
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 12.dp)
                    .padding(bottom = 96.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // SECTION: NOTIFICACIONES
                SettingsSection(
                    title = "Notificaciones"
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
                        border = BorderStroke(1.dp, SurfaceContainerHigh.copy(alpha = 0.6f))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            // Row 1: Push
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Notifications,
                                        contentDescription = null,
                                        tint = SecondaryGreen,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Text(
                                        text = "Push",
                                        fontSize = 16.sp,
                                        color = OnSurface,
                                        fontWeight = FontWeight.Normal
                                    )
                                }

                                Switch(
                                    checked = pushNotificationsEnabled,
                                    onCheckedChange = { pushNotificationsEnabled = it },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = Color.White,
                                        checkedTrackColor = PrimaryContainer,
                                        uncheckedThumbColor = Color.White,
                                        uncheckedTrackColor = SurfaceContainerHigh
                                    )
                                )
                            }

                            HorizontalDivider(color = OutlineVariant.copy(alpha = 0.3f))

                            // Row 2: Email
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Mail,
                                        contentDescription = null,
                                        tint = SecondaryGreen,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Text(
                                        text = "Email",
                                        fontSize = 16.sp,
                                        color = OnSurface,
                                        fontWeight = FontWeight.Normal
                                    )
                                }

                                Switch(
                                    checked = emailNotificationsEnabled,
                                    onCheckedChange = { emailNotificationsEnabled = it },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = Color.White,
                                        checkedTrackColor = PrimaryContainer,
                                        uncheckedThumbColor = Color.White,
                                        uncheckedTrackColor = SurfaceContainerHigh
                                    )
                                )
                            }
                        }
                    }
                }

                // SECTION: SEGURIDAD
                SettingsSection(
                    title = "Seguridad"
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
                        border = BorderStroke(1.dp, SurfaceContainerHigh.copy(alpha = 0.6f))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            // Row 1: Cambiar contraseña
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { viewModel.showChangePasswordScreen.value = true }
                                    .padding(vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = null,
                                        tint = SecondaryGreen,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Text(
                                        text = "Cambiar contraseña",
                                        fontSize = 16.sp,
                                        color = OnSurface,
                                        fontWeight = FontWeight.Normal
                                    )
                                }

                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = null,
                                    tint = OutlineColor,
                                    modifier = Modifier.size(22.dp)
                                )
                            }

                            HorizontalDivider(color = OutlineVariant.copy(alpha = 0.3f))

                            // Row 2: Seguridad biométrica
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Fingerprint,
                                        contentDescription = null,
                                        tint = SecondaryGreen,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Text(
                                        text = "Seguridad biométrica",
                                        fontSize = 16.sp,
                                        color = OnSurface,
                                        fontWeight = FontWeight.Normal
                                    )
                                }

                                Switch(
                                    checked = biometricEnabled,
                                    onCheckedChange = { biometricEnabled = it },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = Color.White,
                                        checkedTrackColor = PrimaryContainer,
                                        uncheckedThumbColor = Color.White,
                                        uncheckedTrackColor = SurfaceContainerHigh
                                    )
                                )
                            }
                        }
                    }
                }

                // SECTION: PREFERENCIAS
                SettingsSection(
                    title = "Preferencias"
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
                        border = BorderStroke(1.dp, SurfaceContainerHigh.copy(alpha = 0.6f))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            // Row 1: Idioma
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { showLanguageDialog = true }
                                    .padding(vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Language,
                                        contentDescription = null,
                                        tint = SecondaryGreen,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Text(
                                        text = "Idioma",
                                        fontSize = 16.sp,
                                        color = OnSurface,
                                        fontWeight = FontWeight.Normal
                                    )
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = selectedLanguage,
                                        fontSize = 14.sp,
                                        color = OnSurfaceVariant
                                    )
                                    Icon(
                                        imageVector = Icons.Default.ChevronRight,
                                        contentDescription = null,
                                        tint = OutlineColor,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }

                            HorizontalDivider(color = OutlineVariant.copy(alpha = 0.3f))

                            // Row 2: Moneda
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { showCurrencyDialog = true }
                                    .padding(vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Payments,
                                        contentDescription = null,
                                        tint = SecondaryGreen,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Text(
                                        text = "Moneda",
                                        fontSize = 16.sp,
                                        color = OnSurface,
                                        fontWeight = FontWeight.Normal
                                    )
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = selectedCurrency,
                                        fontSize = 14.sp,
                                        color = OnSurfaceVariant
                                    )
                                    Icon(
                                        imageVector = Icons.Default.ChevronRight,
                                        contentDescription = null,
                                        tint = OutlineColor,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // SECTION: APLICACIÓN
                SettingsSection(
                    title = "Aplicación"
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
                        border = BorderStroke(1.dp, SurfaceContainerHigh.copy(alpha = 0.6f))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            // Row 1: Tema
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { showThemeDialog = true }
                                    .padding(vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Palette,
                                        contentDescription = null,
                                        tint = SecondaryGreen,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Text(
                                        text = "Tema",
                                        fontSize = 16.sp,
                                        color = OnSurface,
                                        fontWeight = FontWeight.Normal
                                    )
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = selectedTheme,
                                        fontSize = 14.sp,
                                        color = OnSurfaceVariant
                                    )
                                    Icon(
                                        imageVector = Icons.Default.ChevronRight,
                                        contentDescription = null,
                                        tint = OutlineColor,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // ACTION BUTTONS (Logout & Delete Account)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Cerrar sesión
                    OutlinedButton(
                        onClick = {
                            if (currentUser != null) {
                                showLogoutDialog = true
                            } else {
                                Toast.makeText(context, "No hay una sesión activa", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(9999.dp),
                        border = BorderStroke(2.dp, ErrorRed),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = ErrorRed
                        ),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Logout,
                                contentDescription = null,
                                tint = ErrorRed,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Cerrar sesión",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = ErrorRed
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Borrar cuenta
                    TextButton(
                        onClick = { showDeleteAccountDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(9999.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.DeleteForever,
                                contentDescription = null,
                                tint = ErrorRed,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "BORRAR CUENTA",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = ErrorRed,
                                letterSpacing = 0.6.sp
                            )
                        }
                    }
                }
            }
        }

        // 1. DIALOG: CAMBIAR CONTRASEÑA
        if (showChangePasswordDialog) {
            var currentPass by remember { mutableStateOf("") }
            var newPass by remember { mutableStateOf("") }
            var confirmPass by remember { mutableStateOf("") }

            AlertDialog(
                onDismissRequest = { showChangePasswordDialog = false },
                shape = RoundedCornerShape(20.dp),
                containerColor = SurfaceContainerLowest,
                title = {
                    Text(
                        text = "Cambiar Contraseña",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurface
                    )
                },
                text = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = currentPass,
                            onValueChange = { currentPass = it },
                            label = { Text("Contraseña actual") },
                            visualTransformation = PasswordVisualTransformation(),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = newPass,
                            onValueChange = { newPass = it },
                            label = { Text("Nueva contraseña") },
                            visualTransformation = PasswordVisualTransformation(),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = confirmPass,
                            onValueChange = { confirmPass = it },
                            label = { Text("Confirmar nueva contraseña") },
                            visualTransformation = PasswordVisualTransformation(),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (newPass.isNotBlank() && newPass == confirmPass) {
                                showChangePasswordDialog = false
                                Toast.makeText(context, "Contraseña actualizada exitosamente", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                    ) {
                        Text("Guardar", color = Color.White)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showChangePasswordDialog = false }) {
                        Text("Cancelar", color = OnSurfaceVariant)
                    }
                }
            )
        }

        // 2. DIALOG: IDIOMA
        if (showLanguageDialog) {
            val languages = listOf("Español", "English", "Miskitu", "Mayangna")
            AlertDialog(
                onDismissRequest = { showLanguageDialog = false },
                shape = RoundedCornerShape(20.dp),
                containerColor = SurfaceContainerLowest,
                title = {
                    Text(
                        text = "Seleccionar Idioma",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurface
                    )
                },
                text = {
                    Column {
                        languages.forEach { lang ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedLanguage = lang
                                        showLanguageDialog = false
                                    }
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = selectedLanguage == lang,
                                    onClick = {
                                        selectedLanguage = lang
                                        showLanguageDialog = false
                                    },
                                    colors = RadioButtonDefaults.colors(selectedColor = PrimaryGreen)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = lang, fontSize = 16.sp, color = OnSurface)
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showLanguageDialog = false }) {
                        Text("Cerrar", color = PrimaryGreen)
                    }
                }
            )
        }

        // 3. DIALOG: MONEDA
        if (showCurrencyDialog) {
            val currencies = listOf("NIO (C$)" to "Córdoba nicaragüense", "USD ($)" to "Dólar estadounidense")
            AlertDialog(
                onDismissRequest = { showCurrencyDialog = false },
                shape = RoundedCornerShape(20.dp),
                containerColor = SurfaceContainerLowest,
                title = {
                    Text(
                        text = "Seleccionar Moneda",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurface
                    )
                },
                text = {
                    Column {
                        currencies.forEach { (curr, desc) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedCurrency = curr
                                        showCurrencyDialog = false
                                    }
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = selectedCurrency == curr,
                                    onClick = {
                                        selectedCurrency = curr
                                        showCurrencyDialog = false
                                    },
                                    colors = RadioButtonDefaults.colors(selectedColor = PrimaryGreen)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(text = curr, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = OnSurface)
                                    Text(text = desc, fontSize = 12.sp, color = OnSurfaceVariant)
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showCurrencyDialog = false }) {
                        Text("Cerrar", color = PrimaryGreen)
                    }
                }
            )
        }

        // 4. DIALOG: TEMA
        if (showThemeDialog) {
            val themes = listOf("Sistema", "Claro", "Oscuro")
            AlertDialog(
                onDismissRequest = { showThemeDialog = false },
                shape = RoundedCornerShape(20.dp),
                containerColor = SurfaceContainerLowest,
                title = {
                    Text(
                        text = "Tema de la Aplicación",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurface
                    )
                },
                text = {
                    Column {
                        themes.forEach { theme ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedTheme = theme
                                        showThemeDialog = false
                                    }
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = selectedTheme == theme,
                                    onClick = {
                                        selectedTheme = theme
                                        showThemeDialog = false
                                    },
                                    colors = RadioButtonDefaults.colors(selectedColor = PrimaryGreen)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = theme, fontSize = 16.sp, color = OnSurface)
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showThemeDialog = false }) {
                        Text("Cerrar", color = PrimaryGreen)
                    }
                }
            )
        }

        // 5. DIALOG: CERRAR SESIÓN
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
                        text = "¿Estás seguro de que deseas salir de tu cuenta en este dispositivo?",
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

        // 6. DIALOG: BORRAR CUENTA
        if (showDeleteAccountDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteAccountDialog = false },
                shape = RoundedCornerShape(20.dp),
                containerColor = SurfaceContainerLowest,
                title = {
                    Text(
                        text = "¿Eliminar cuenta definitivamente?",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = ErrorRed
                    )
                },
                text = {
                    Text(
                        text = "Esta acción es irreversible. Se eliminarán todas tus publicaciones de boletos, viajes compartidos, estancias y reseñas de la plataforma.",
                        fontSize = 14.sp,
                        color = OnSurfaceVariant,
                        lineHeight = 20.sp
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.logout()
                            showDeleteAccountDialog = false
                            Toast.makeText(context, "Cuenta eliminada de la plataforma", Toast.LENGTH_LONG).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ErrorRed)
                    ) {
                        Text("Eliminar definitivamente", color = Color.White)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteAccountDialog = false }) {
                        Text("Cancelar", color = OnSurfaceVariant)
                    }
                }
            )
        }
    }
}

@Composable
private fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = SecondaryGreen,
            letterSpacing = 0.8.sp,
            modifier = Modifier.padding(start = 8.dp)
        )
        content()
    }
}
