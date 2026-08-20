package com.example.ui.screens

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BusRoute
import com.example.ui.theme.InkBlack
import com.example.ui.theme.LimeBrand
import com.example.ui.theme.LimeBrandDark
import com.example.ui.theme.LineBorder
import com.example.ui.theme.MutedGray
import com.example.ui.theme.SoftBlueBg
import com.example.ui.theme.SoftGreenBg
import com.example.ui.theme.SoftYellow
import com.example.ui.theme.SurfaceGray
import com.example.ui.viewmodel.BusDriveViewModel
import com.example.util.LocalNotificationHelper
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultadosScreen(
    viewModel: BusDriveViewModel,
    onBack: () -> Unit
) {
    val origen by viewModel.origenVal.collectAsState()
    val destino by viewModel.destinoVal.collectAsState()
    val allRoutes by viewModel.allRoutes.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val sortAscending by viewModel.sortAscending.collectAsState()
    val selectedDetail by viewModel.selectedRouteDetail.collectAsState()

    val dateFormat = remember { SimpleDateFormat("EEE d MMM", Locale("es", "NI")) }
    val dayOfWeekName = remember(selectedDate) {
        SimpleDateFormat("EEEE", Locale("es", "NI")).format(selectedDate).lowercase(Locale("es", "NI"))
    }

    // Filter routes matching origin and destination, and matching selected day of week
    val filteredRoutes = remember(origen, destino, allRoutes, selectedDate, sortAscending, dayOfWeekName) {
        var list = allRoutes.filter { r ->
            val matchOrigin = origen.isBlank() || r.origen.equals(origen, ignoreCase = true)
            val matchDest = destino.isBlank() || r.destino.equals(destino, ignoreCase = true)
            val matchDay = matchesDayOfWeek(r.dias, dayOfWeekName)
            matchOrigin && matchDest && matchDay
        }

        // Sort
        list = if (sortAscending) {
            list.sortedBy { parseTimeSortKey(it.salida) }
        } else {
            list.sortedByDescending { parseTimeSortKey(it.salida) }
        }
        list
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceGray)
    ) {
        // TOP BAR
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            shadowElevation = 2.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Resultados",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = InkBlack
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = SurfaceGray,
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(
                        text = "🇳🇮 C$",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = InkBlack,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // DATE TABS (30 days)
        val datesList = remember {
            val list = mutableListOf<Date>()
            val cal = Calendar.getInstance()
            cal.set(2026, Calendar.AUGUST, 10)
            for (i in -1..28) {
                val tempCal = cal.clone() as Calendar
                tempCal.add(Calendar.DAY_OF_MONTH, i)
                list.add(tempCal.time)
            }
            list
        }

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(datesList) { date ->
                val isSelected = isSameDay(date, selectedDate)
                val dayStr = SimpleDateFormat("EEE", Locale("es", "NI")).format(date)
                val numStr = SimpleDateFormat("d MMM", Locale("es", "NI")).format(date)

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) LimeBrand else Color.Transparent)
                        .clickable { viewModel.selectedDate.value = date }
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = dayStr,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                            color = if (isSelected) InkBlack else MutedGray
                        )
                        Text(
                            text = numStr,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) InkBlack else MutedGray
                        )
                    }
                }
            }
        }

        // ROUTE PILL SUMMARY
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            border = androidx.compose.foundation.BorderStroke(1.dp, LineBorder)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${if (origen.isBlank()) "Cualquier origen" else origen} → ${if (destino.isBlank()) "Todos los destinos" else destino}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = InkBlack,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "📅 ${dateFormat.format(selectedDate)} · ${filteredRoutes.size} salidas encontradas",
                    fontSize = 13.sp,
                    color = MutedGray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        // SORT CONTROL
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Horarios disponibles",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = InkBlack
            )

            OutlinedButton(
                onClick = { viewModel.sortAscending.value = !sortAscending },
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, LineBorder)
            ) {
                Icon(
                    imageVector = Icons.Default.SwapVert,
                    contentDescription = null,
                    tint = InkBlack,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (sortAscending) "Más temprano" else "Más tarde",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = InkBlack
                )
            }
        }

        // LIST OF ROUTE CARDS
        if (filteredRoutes.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No hay salidas programadas para esta ruta en $dayOfWeekName.\nPrueba otra fecha u otro municipio.",
                    fontSize = 15.sp,
                    color = MutedGray,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredRoutes) { route ->
                    BusRouteCard(
                        route = route,
                        onClick = { viewModel.selectedRouteDetail.value = route }
                    )
                }
            }
        }
    }

    // ROUTE DETAIL MODAL SHEET
    selectedDetail?.let { route ->
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

        ModalBottomSheet(
            onDismissRequest = { viewModel.selectedRouteDetail.value = null },
            sheetState = sheetState,
            containerColor = Color.White
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
                        text = route.operador.ifBlank { "Transporte de Bus" },
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = InkBlack,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = { viewModel.selectedRouteDetail.value = null }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Cerrar")
                    }
                }

                Text(
                    text = "${route.origen} → ${route.destino}",
                    fontSize = 15.sp,
                    color = MutedGray,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                DetailRow("Precio", if (route.precio != null && route.precio > 0) "C$ ${route.precio}" else "Consultar en terminal")
                DetailRow("Salida", route.salida)
                if (route.llegada.isNotBlank()) DetailRow("Llegada aprox.", route.llegada)
                DetailRow("Nombre del bus", route.operador.ifBlank { "Transporte Directo" })
                if (route.cooperativa.isNotBlank()) DetailRow("Propietario / Titular", route.cooperativa)
                if (route.tipo.isNotBlank()) DetailRow("Tipo de unidad", route.tipo)
                if (route.capacidad.isNotBlank()) DetailRow("Capacidad", "${route.capacidad} pasajeros")
                if (route.placa.isNotBlank()) DetailRow("Placa", route.placa)
                if (route.clasificacion.isNotBlank()) DetailRow("Clasificación", route.clasificacion)
                if (route.modalidad.isNotBlank()) DetailRow("Modalidad", route.modalidad)
                if (route.dias.isNotBlank()) DetailRow("Días de operación", route.dias)
                if (route.tiempo.isNotBlank()) DetailRow("Tiempo de viaje", route.tiempo)
                if (route.terminal.isNotBlank()) DetailRow("Terminal", route.terminal)
                if (route.contacto.isNotBlank()) DetailRow("Teléfono contacto", route.contacto)

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { viewModel.selectedRouteDetail.value = null },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = LimeBrand, contentColor = InkBlack)
                ) {
                    Text("Cerrar detalles", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun BusRouteCard(
    route: BusRoute,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, LineBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = route.operador.ifBlank { "Transporte" },
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        color = InkBlack
                    )
                    if (route.cooperativa.isNotBlank()) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = route.cooperativa,
                            fontSize = 12.5.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF64748B),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Icon(imageVector = Icons.Default.DirectionsBus, contentDescription = "Bus", tint = InkBlack, modifier = Modifier.size(20.dp))
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = route.salida, fontSize = 24.sp, fontWeight = FontWeight.Black, color = InkBlack)
                    Text(text = route.terminal.ifBlank { route.origen }, fontSize = 12.sp, color = MutedGray, maxLines = 1)
                    Text(text = route.origen, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = InkBlack)
                }

                Text(
                    text = "→",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MutedGray,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(text = route.llegada.ifBlank { "·" }, fontSize = 24.sp, fontWeight = FontWeight.Black, color = InkBlack)
                    Text(text = route.destino, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = InkBlack)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // TAG
            val tagInfo = remember(route.fuente) {
                when (route.fuente) {
                    "informal" -> Pair("Informal · Viaje Especial", SoftYellow to Color(0xFF9A5A00))
                    "linea" -> Pair("Línea Interdepartamental", SoftGreenBg to Color(0xFF3A6B12))
                    "salida" -> Pair("Salida por destino", SoftBlueBg to Color(0xFF2A5B8A))
                    else -> Pair("Itinerario oficial", SurfaceGray to InkBlack)
                }
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(tagInfo.second.first)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = tagInfo.first,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = tagInfo.second.second
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.AccessTime, contentDescription = null, tint = MutedGray, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = route.tiempo.ifBlank { route.dias.ifBlank { "Diario" } }, fontSize = 13.sp, color = MutedGray, fontWeight = FontWeight.Medium)
                }

                Button(
                    onClick = onClick,
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = LimeBrand, contentColor = InkBlack),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = if (route.precio != null && route.precio > 0) "C$ ${route.precio} ›" else "Consultar ›",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 14.sp, color = MutedGray, fontWeight = FontWeight.Bold)
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = InkBlack,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f, fill = false).padding(start = 12.dp)
        )
    }
}

private fun matchesDayOfWeek(dias: String, dayOfWeekName: String): Boolean {
    if (dias.isBlank()) return true
    val d = dias.lowercase(Locale("es", "NI"))
    if (d.contains("diario") || d.contains("todos los dias") || d.contains("diaria") || d.contains("lunes-domingo")) {
        return true
    }
    val normalize = { s: String ->
        s.replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u")
    }
    val normD = normalize(d)
    val normTarget = normalize(dayOfWeekName.lowercase(Locale("es", "NI")))
    return normD.contains(normTarget)
}

private fun parseTimeSortKey(salida: String): Int {
    val regex = Regex("""(\d{1,2}):(\d{2})\s*(a\.m\.|p\.m\.|AM|PM)?""", RegexOption.IGNORE_CASE)
    val match = regex.find(salida) ?: return 99999
    var hh = match.groupValues[1].toIntOrNull() ?: 0
    val mm = match.groupValues[2].toIntOrNull() ?: 0
    val ap = match.groupValues[3].lowercase()
    if (ap.contains("p") && hh < 12) hh += 12
    if (ap.contains("a") && hh == 12) hh = 0
    return hh * 60 + mm
}

private fun isSameDay(d1: Date, d2: Date): Boolean {
    val c1 = Calendar.getInstance().apply { time = d1 }
    val c2 = Calendar.getInstance().apply { time = d2 }
    return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) &&
            c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)
}
