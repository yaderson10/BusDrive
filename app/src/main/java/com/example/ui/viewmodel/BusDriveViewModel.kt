package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.BusDriveDatabase
import com.example.data.model.AppNotification
import com.example.data.model.BusRoute
import com.example.data.model.MandaditoCourier
import com.example.data.model.ProviderProfile
import com.example.data.model.Review
import com.example.data.model.TrustLevel
import com.example.data.model.VerificationState
import com.example.data.model.Ride
import com.example.data.model.Stay
import com.example.data.model.Ticket
import com.example.data.model.UserProfile
import com.example.data.repository.BusDriveRepository
import com.example.util.LocalNotificationHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

enum class MainTab {
    EXPLORAR, VIAJES, MANDADITOS, CONFIG
}

enum class ViajeSubTab {
    BOLETO, RIDE, STAY
}

class BusDriveViewModel(application: Application) : AndroidViewModel(application) {

    private val db = BusDriveDatabase.getInstance(application)
    val repository = BusDriveRepository(db.dao())
    private val prefs = application.getSharedPreferences("busdrive_prefs", android.content.Context.MODE_PRIVATE)

    val currentUser: StateFlow<UserProfile?> = repository.currentUser

    val allRoutes: StateFlow<List<BusRoute>> = repository.allRoutes.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Favorite bus routes
    val favoriteRouteIds = MutableStateFlow<Set<Long>>(emptySet())
    val showOnlyFavorites = MutableStateFlow(false)

    val allTickets: StateFlow<List<Ticket>> = repository.allTickets.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val allRides: StateFlow<List<Ride>> = repository.allRides.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val allStays: StateFlow<List<Stay>> = repository.allStays.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val allReviews: StateFlow<List<Review>> = repository.allReviews.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // UI state
    val selectedTab = MutableStateFlow(MainTab.EXPLORAR)
    val selectedViajeSubTab = MutableStateFlow(ViajeSubTab.BOLETO)

    val origenVal = MutableStateFlow("")
    val destinoVal = MutableStateFlow("")

    val selectedDate = MutableStateFlow(Date())

    val sortAscending = MutableStateFlow(true)

    // Active modals/sheets
    val selectedRouteDetail = MutableStateFlow<BusRoute?>(null)
    val selectedTicketDetail = MutableStateFlow<Ticket?>(null)
    val selectedRideDetail = MutableStateFlow<Ride?>(null)
    val selectedStayDetail = MutableStateFlow<Stay?>(null)

    // Reviews & Safety states
    val showReviewModal = MutableStateFlow<ReviewTarget?>(null)
    val showSafetyKitModal = MutableStateFlow<Ride?>(null)
    val showSosEmergencyDialog = MutableStateFlow(false)

    val showPicker = MutableStateFlow<String?>(null) // "origen" or "destino" or null
    val pickerQuery = MutableStateFlow("")

    val showAuthModal = MutableStateFlow(false)
    val authMessage = MutableStateFlow("")
    val pendingAction = MutableStateFlow<String?>(null)

    val showPublishTicketModal = MutableStateFlow(false)
    val showPublishRideModal = MutableStateFlow(false)
    val showPublishStayModal = MutableStateFlow<Stay?>(null) // if editing, holds stay
    val showSolicitarRideModal = MutableStateFlow<Ride?>(null)

    // P2P Trust & Provider Profile System
    val currentProviderProfile = MutableStateFlow(
        ProviderProfile(
            userId = "demo_user",
            fullName = "Yader Castellón",
            commercialName = "Transportes Yader & Asoc.",
            phone = "505 8888-9999",
            email = "yader@busdrive.ni",
            municipality = "Puerto Cabezas (Bilwi)",
            community = "Barrio El Muelle",
            userType = "Conductor",
            profilePhotoUrl = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=400",
            trustLevel = TrustLevel.VERIFIED,
            verificationState = VerificationState.VERIFIED,
            phoneVerified = true,
            identityVerified = true,
            serviceVerified = true,
            rating = 4.9f,
            reviewsCount = 38,
            tripsCount = 42,
            memberSinceYear = "2026"
        )
    )

    val showAdminPanelModal = MutableStateFlow(false)
    val showProviderProfileModal = MutableStateFlow(false)
    val showP2PPublishWizardModal = MutableStateFlow<String?>(null) // "boleto", "ride", "stay" or null

    val showNotifsScreen = MutableStateFlow(false)
    val showAccountDetailsScreen = MutableStateFlow(false)
    val showMyTicketsScreen = MutableStateFlow(false)
    val showTermsScreen = MutableStateFlow(false)
    val showSettingsScreen = MutableStateFlow(false)
    val showTicketGuideScreen = MutableStateFlow(false)
    val showChangePasswordScreen = MutableStateFlow(false)
    val showHelpScreen = MutableStateFlow(false)
    val showPublishCourierModal = MutableStateFlow(false)

    // Mandaditos & Delivery Couriers system
    val allCouriers = MutableStateFlow<List<MandaditoCourier>>(
        listOf(
            MandaditoCourier(
                id = "courier_1",
                name = "Carlos Hernández",
                phone = "505 8890-1234",
                vehicleType = "Yamaha FZ 150cc",
                vehiclePlate = "M-14829",
                photoUrl = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=400",
                zoneCoverage = "Managua Centro, Metrocentro, UCA & Alrededores",
                baseRate = "A convenir",
                rating = 4.9f,
                reviewsCount = 52,
                completedDeliveries = 148,
                isAvailable = true,
                isVerified = true,
                services = listOf("Envíos Express", "Compras en Farmacia/Súper", "Comida & Restaurantes", "Documentos"),
                workingHours = "7:00 AM - 10:00 PM",
                description = "Repartidor puntual y responsable. Llevo tus compras, medicinas, comida y paquetes con máxima rapidez."
            ),
            MandaditoCourier(
                id = "courier_2",
                name = "Marcos Gutiérrez",
                phone = "505 8456-7890",
                vehicleType = "Honda XR 150cc Todo Terreno",
                vehiclePlate = "RACN-4512",
                photoUrl = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=400",
                zoneCoverage = "Bilwi Centro, El Muelle, Peter Ferrera y Aeropuerto",
                baseRate = "A convenir",
                rating = 5.0f,
                reviewsCount = 68,
                completedDeliveries = 210,
                isAvailable = true,
                isVerified = true,
                services = listOf("Envíos Express", "Compras en Mercado", "Paquetería Liviana", "Supermercado"),
                workingHours = "6:30 AM - 9:30 PM",
                description = "Servicio de moto mandados confiable en todo Bilwi. Experiencia en todas las zonas urbanas y periféricas."
            ),
            MandaditoCourier(
                id = "courier_3",
                name = "Kevin Morales",
                phone = "505 8765-4321",
                vehicleType = "Suzuki Gixxer 150",
                vehiclePlate = "LE-9821",
                photoUrl = "https://images.unsplash.com/photo-1519085360753-af0119f7cbe7?w=400",
                zoneCoverage = "León Urbano, Campus UNAN, Sutiaba y San Felipe",
                baseRate = "A convenir",
                rating = 4.8f,
                reviewsCount = 41,
                completedDeliveries = 120,
                isAvailable = true,
                isVerified = true,
                services = listOf("Comida & Restaurantes", "Envíos Express", "Trámites / Fotocopias", "Farmacias"),
                workingHours = "8:00 AM - 11:00 PM",
                description = "Entrego comida caliente, medicina y tareas de inmediato. Tu mandado llega en minutos."
            ),
            MandaditoCourier(
                id = "courier_4",
                name = "Jairo Rivas",
                phone = "505 8923-4567",
                vehicleType = "Hero Eco 100",
                vehiclePlate = "ES-3341",
                photoUrl = "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=400",
                zoneCoverage = "Estelí Centro, Zonas Francas y Panamericana",
                baseRate = "A convenir",
                rating = 4.9f,
                reviewsCount = 34,
                completedDeliveries = 95,
                isAvailable = false, // En ruta
                isVerified = true,
                services = listOf("Compras de Súper", "Farmacia 24/7", "Paquetes Livianos"),
                workingHours = "7:00 AM - 9:00 PM",
                description = "Motomandados express en Estelí. Honestidad y rapidez garantizada en cada entrega."
            ),
            MandaditoCourier(
                id = "courier_5",
                name = "Denis Aráuz",
                phone = "505 8612-3456",
                vehicleType = "Genesis HJ 125",
                vehiclePlate = "MT-7712",
                photoUrl = "https://images.unsplash.com/photo-1522075469751-3a6694fb2f61?w=400",
                zoneCoverage = "Matagalpa Centro, Mercado Guanuca y Salida a Managua",
                baseRate = "A convenir",
                rating = 4.9f,
                reviewsCount = 29,
                completedDeliveries = 88,
                isAvailable = true,
                isVerified = true,
                services = listOf("Envíos Express", "Comida & Restaurantes", "Medicina Urgente"),
                workingHours = "7:30 AM - 10:00 PM",
                description = "Delivery seguro y rápido para negocios y particulares en Matagalpa."
            )
        )
    )

    fun registerMandaditoCourier(courier: MandaditoCourier) {
        val current = allCouriers.value.toMutableList()
        current.add(0, courier)
        allCouriers.value = current
    }

    fun toggleCourierAvailability(courierId: String) {
        allCouriers.value = allCouriers.value.map {
            if (it.id == courierId) it.copy(isAvailable = !it.isAvailable) else it
        }
    }

    // Ride request stepper count
    val solicitarRideCount = MutableStateFlow(1)

    // Ride filter
    val rideFilterOrigen = MutableStateFlow("")
    val rideFilterDestino = MutableStateFlow("")

    // Stay filter
    val stayFilterMunicipio = MutableStateFlow("")

    // User notifications
    val userNotifications: StateFlow<List<AppNotification>> = combine(
        currentUser,
        repository.allTickets
    ) { user, _ ->
        user?.id ?: ""
    }.combine(repository.allRides) { userId, _ ->
        userId
    }.let { userIdFlow ->
        MutableStateFlow(emptyList<AppNotification>())
    }

    private val _notifications = MutableStateFlow<List<AppNotification>>(emptyList())
    val notifications: StateFlow<List<AppNotification>> = _notifications.asStateFlow()

    init {
        LocalNotificationHelper.initNotificationChannel(application)
        val savedFavs = prefs.getStringSet("favorite_route_ids", emptySet()) ?: emptySet()
        favoriteRouteIds.value = savedFavs.mapNotNull { it.toLongOrNull() }.toSet()

        // Restore user session if logged in
        val isLoggedIn = prefs.getBoolean("user_is_logged_in", true)
        if (isLoggedIn) {
            val id = prefs.getString("user_id", "demo_user") ?: "demo_user"
            val name = prefs.getString("user_name", "Yader Castellón") ?: "Yader Castellón"
            val firstName = prefs.getString("user_first_name", "Yader") ?: "Yader"
            val lastName = prefs.getString("user_last_name", "Castellón") ?: "Castellón"
            val email = prefs.getString("user_email", "yader@busdrive.ni") ?: "yader@busdrive.ni"
            val phone = prefs.getString("user_phone", "505 8888-9999") ?: "505 8888-9999"
            val city = prefs.getString("user_city", "Puerto Cabezas (Bilwi)") ?: "Puerto Cabezas (Bilwi)"
            val idNum = prefs.getString("user_id_number", "88991234") ?: "88991234"
            val memberSince = prefs.getString("user_member_since", "11 de agosto de 2026") ?: "11 de agosto de 2026"
            val provider = prefs.getString("user_provider", "email") ?: "email"
            val photo = prefs.getString("user_photo_url", "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=400") ?: "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=400"
            val role = prefs.getString("user_role", "Pasajero") ?: "Pasajero"

            val user = UserProfile(
                id = id,
                firstName = firstName,
                lastName = lastName,
                name = name,
                email = email,
                phone = phone,
                city = city,
                userIdNumber = idNum,
                memberSince = memberSince,
                provider = provider,
                photoUrl = photo,
                userRole = role
            )
            repository.setCurrentUser(user)
            currentProviderProfile.value = currentProviderProfile.value.copy(
                userId = user.id,
                fullName = user.name,
                firstName = user.firstName,
                lastName = user.lastName,
                phone = user.phone,
                email = user.email,
                city = user.city,
                municipality = user.city,
                userType = user.userRole,
                profilePhotoUrl = user.photoUrl
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            repository.prepopulateIfNeeded()
        }

        viewModelScope.launch {
            currentUser.collect { user ->
                val ownerId = user?.id ?: "demo_user"
                repository.getNotificationsForUser(ownerId).collect { list ->
                    _notifications.value = list
                }
            }
        }
    }

    fun setOrigen(origen: String) {
        origenVal.value = origen
    }

    fun setDestino(destino: String) {
        destinoVal.value = destino
    }

    fun swapOrigenDestino() {
        val temp = origenVal.value
        origenVal.value = destinoVal.value
        destinoVal.value = temp
    }

    fun clearOrigen() { origenVal.value = "" }
    fun clearDestino() { destinoVal.value = "" }

    fun saveUserToPrefs(user: UserProfile) {
        prefs.edit()
            .putBoolean("user_is_logged_in", true)
            .putString("user_id", user.id)
            .putString("user_name", user.name)
            .putString("user_first_name", user.firstName)
            .putString("user_last_name", user.lastName)
            .putString("user_email", user.email)
            .putString("user_phone", user.phone)
            .putString("user_city", user.city)
            .putString("user_id_number", user.userIdNumber)
            .putString("user_member_since", user.memberSince)
            .putString("user_provider", user.provider)
            .putString("user_photo_url", user.photoUrl)
            .putString("user_role", user.userRole)
            .apply()
    }

    fun loginDemoUser() {
        val user = UserProfile(
            id = "demo_user",
            firstName = "Yader",
            lastName = "Castellón",
            name = "Yader Castellón",
            email = "yader@busdrive.ni",
            phone = "505 8888-9999",
            city = "Puerto Cabezas (Bilwi)",
            userIdNumber = "88991234",
            memberSince = "11 de agosto de 2026",
            provider = "email",
            photoUrl = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=400",
            userRole = "Conductor"
        )
        repository.setCurrentUser(user)
        saveUserToPrefs(user)
        currentProviderProfile.value = currentProviderProfile.value.copy(
            userId = user.id,
            fullName = user.name,
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            phone = user.phone,
            city = user.city,
            municipality = user.city,
            userType = user.userRole,
            profilePhotoUrl = user.photoUrl
        )
        finishAuth()
    }

    fun loginGoogleUser() {
        val user = UserProfile(
            id = "g_user_" + System.currentTimeMillis(),
            firstName = "Usuario",
            lastName = "Google",
            name = "Usuario Google BusDrive",
            email = "usuario.nicaragua@gmail.com",
            phone = "505 8555-0000",
            city = "Managua",
            userIdNumber = (10000000..99999999).random().toString(),
            memberSince = "Agosto 2026",
            provider = "google",
            photoUrl = "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=400",
            userRole = "Pasajero"
        )
        repository.setCurrentUser(user)
        saveUserToPrefs(user)
        currentProviderProfile.value = currentProviderProfile.value.copy(
            userId = user.id,
            fullName = user.name,
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            phone = user.phone,
            city = user.city,
            municipality = user.city,
            userType = user.userRole,
            profilePhotoUrl = user.photoUrl
        )
        finishAuth()
    }

    fun loginSimpleUser(
        email: String,
        phone: String
    ): Boolean {
        if (email.isBlank() && phone.isBlank()) return false
        val cleanEmail = if (email.isNotBlank()) email.trim() else "usuario@busdrive.ni"
        val cleanPhone = if (phone.isNotBlank()) phone.trim() else "505 8888-0000"
        val parts = cleanEmail.substringBefore("@").split(".")
        val fName = parts.firstOrNull()?.replaceFirstChar { it.uppercase() } ?: "Usuario"
        val lName = if (parts.size > 1) parts[1].replaceFirstChar { it.uppercase() } else ""
        val finalName = if (lName.isNotBlank()) "$fName $lName" else fName

        val user = UserProfile(
            id = "user_" + System.currentTimeMillis(),
            firstName = fName,
            lastName = lName,
            name = finalName,
            email = cleanEmail,
            phone = cleanPhone,
            city = "Nicaragua",
            userIdNumber = (10000000..99999999).random().toString(),
            memberSince = "Agosto 2026",
            provider = "email",
            photoUrl = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=400",
            userRole = "Usuario"
        )
        repository.setCurrentUser(user)
        saveUserToPrefs(user)
        currentProviderProfile.value = currentProviderProfile.value.copy(
            userId = user.id,
            fullName = user.name,
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            phone = user.phone,
            city = user.city,
            municipality = user.city,
            userType = user.userRole,
            profilePhotoUrl = user.photoUrl
        )
        finishAuth()
        return true
    }

    fun registerSimpleUser(
        fullName: String,
        email: String,
        phone: String
    ): Boolean {
        val cleanName = if (fullName.isNotBlank()) fullName.trim() else "Usuario BusDrive"
        val cleanEmail = if (email.isNotBlank()) email.trim() else "usuario@busdrive.ni"
        val cleanPhone = if (phone.isNotBlank()) phone.trim() else "505 8888-0000"
        val nameParts = cleanName.split(" ")
        val fName = nameParts.firstOrNull() ?: cleanName
        val lName = if (nameParts.size > 1) nameParts.drop(1).joinToString(" ") else ""

        val user = UserProfile(
            id = "user_" + System.currentTimeMillis(),
            firstName = fName,
            lastName = lName,
            name = cleanName,
            email = cleanEmail,
            phone = cleanPhone,
            city = "Nicaragua",
            userIdNumber = (10000000..99999999).random().toString(),
            memberSince = "Agosto 2026",
            provider = "email",
            photoUrl = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=400",
            userRole = "Usuario"
        )
        repository.setCurrentUser(user)
        saveUserToPrefs(user)
        currentProviderProfile.value = currentProviderProfile.value.copy(
            userId = user.id,
            fullName = user.name,
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            phone = user.phone,
            city = user.city,
            municipality = user.city,
            userType = user.userRole,
            profilePhotoUrl = user.photoUrl
        )
        finishAuth()
        return true
    }

    fun registerEmailUser(
        fullName: String,
        email: String,
        phone: String,
        city: String,
        role: String,
        pass: String
    ): Boolean {
        if (fullName.isBlank() || email.isBlank()) return false
        val nameParts = fullName.trim().split(" ")
        val fName = nameParts.firstOrNull() ?: fullName
        val lName = if (nameParts.size > 1) nameParts.drop(1).joinToString(" ") else ""
        
        val user = UserProfile(
            id = "u_" + System.currentTimeMillis(),
            firstName = fName,
            lastName = lName,
            name = fullName.trim(),
            email = email.trim(),
            phone = if (phone.isNotBlank()) phone.trim() else "505 8800-0000",
            city = if (city.isNotBlank()) city else "Managua",
            userIdNumber = (10000000..99999999).random().toString(),
            memberSince = "Agosto 2026",
            provider = "email",
            photoUrl = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=400",
            userRole = if (role.isNotBlank()) role else "Pasajero"
        )
        repository.setCurrentUser(user)
        saveUserToPrefs(user)
        currentProviderProfile.value = currentProviderProfile.value.copy(
            userId = user.id,
            fullName = user.name,
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            phone = user.phone,
            city = user.city,
            municipality = user.city,
            userType = user.userRole,
            profilePhotoUrl = user.photoUrl
        )
        finishAuth()
        return true
    }

    fun loginDemoPersona(
        name: String,
        email: String,
        phone: String,
        city: String,
        role: String,
        photoUrl: String
    ) {
        val nameParts = name.trim().split(" ")
        val fName = nameParts.firstOrNull() ?: name
        val lName = if (nameParts.size > 1) nameParts.drop(1).joinToString(" ") else ""

        val user = UserProfile(
            id = "persona_" + name.lowercase().replace(" ", "_"),
            firstName = fName,
            lastName = lName,
            name = name,
            email = email,
            phone = phone,
            city = city,
            userIdNumber = (10000000..99999999).random().toString(),
            memberSince = "2026",
            provider = "demo",
            photoUrl = photoUrl,
            userRole = role
        )
        repository.setCurrentUser(user)
        saveUserToPrefs(user)
        currentProviderProfile.value = currentProviderProfile.value.copy(
            userId = user.id,
            fullName = user.name,
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            phone = user.phone,
            city = user.city,
            municipality = user.city,
            userType = user.userRole,
            profilePhotoUrl = user.photoUrl
        )
        finishAuth()
    }

    fun requestPasswordReset(contact: String): String {
        return "842910"
    }

    fun logout() {
        prefs.edit().putBoolean("user_is_logged_in", false).apply()
        repository.setCurrentUser(null)
    }

    private fun finishAuth() {
        showAuthModal.value = false
        val action = pendingAction.value
        pendingAction.value = null
        when (action) {
            "ticket" -> showPublishTicketModal.value = true
            "ride" -> showPublishRideModal.value = true
            "stay" -> showPublishStayModal.value = Stay(ownerId = currentUser.value?.id ?: "", hostName = currentUser.value?.name ?: "", tipo = "Habitación privada", nombre = "", municipio = "Puerto Cabezas", precio = 500)
            "notifs" -> showNotifsScreen.value = true
        }
    }

    fun openAuthWithAction(msg: String, action: String?) {
        authMessage.value = msg
        pendingAction.value = action
        showAuthModal.value = true
    }

    fun publishTicket(
        nombre: String,
        telefono: String,
        origen: String,
        destino: String,
        fecha: String,
        hora: String,
        operador: String,
        asiento: String,
        precio: String,
        nota: String,
        providerPhotoUrl: String? = null
    ) {
        val user = currentUser.value ?: UserProfile(
            id = "user_${System.currentTimeMillis()}",
            name = nombre.ifBlank { "Vendedor" },
            email = "",
            phone = telefono.ifBlank { "8888-0000" },
            photoUrl = providerPhotoUrl ?: ""
        )
        val finalPhoto = if (!providerPhotoUrl.isNullOrBlank()) providerPhotoUrl
            else currentProviderProfile.value.profilePhotoUrl.ifBlank { user.photoUrl }
        val finalTrust = currentProviderProfile.value.trustLevel.displayName
        viewModelScope.launch {
            val ticketId = repository.insertTicket(
                Ticket(
                    ownerId = user.id,
                    nombreContacto = nombre.ifBlank { user.name },
                    telefonoContacto = telefono.ifBlank { user.phone },
                    origen = origen,
                    destino = destino,
                    fecha = fecha,
                    hora = hora,
                    operador = operador,
                    asiento = asiento,
                    precio = precio,
                    nota = nota,
                    estado = "disponible",
                    providerPhotoUrl = finalPhoto,
                    trustLevel = finalTrust
                )
            )

            // Automáticamente crear notificación en BusDrive con estructura segmentada
            val notif = AppNotification(
                ownerId = "ALL", // Segmentación: Todos / Por Ruta / Por Interés
                type = "nuevo_boleto",
                targetType = "BOLETO",
                targetItemId = ticketId,
                audienceType = "BY_ROUTE",
                targetLocation = destino,
                targetRoute = "$origen → $destino",
                category = "BOLETO",
                ruta = "$origen → $destino",
                fecha = fecha,
                title = "🎫 Nuevo boleto disponible",
                text = "$origen → $destino\nViaje: $fecha · C$$precio\nVendedor: $nombre",
                timestamp = System.currentTimeMillis()
            )
            repository.insertNotification(notif)

            // Enviar alerta del sistema local
            LocalNotificationHelper.sendPublicationNotification(
                context = getApplication(),
                title = "🎫 Nuevo boleto disponible",
                message = "$origen → $destino\nViaje: $fecha · C$$precio",
                targetType = "BOLETO",
                targetId = ticketId,
                category = "Boletos"
            )

            showPublishTicketModal.value = false
            selectedTab.value = MainTab.VIAJES
            selectedViajeSubTab.value = ViajeSubTab.BOLETO
        }
    }

    fun publishRide(
        origen: String,
        destino: String,
        fecha: String,
        hora: String,
        vehiculo: String,
        color: String,
        espacios: Int,
        precio: String,
        puntoEncuentro: String,
        info: String,
        providerPhotoUrl: String? = null,
        vehiclePhotoUrl: String = ""
    ) {
        val user = currentUser.value ?: UserProfile(
            id = "user_${System.currentTimeMillis()}",
            name = "Conductor",
            email = "",
            phone = "8888-0000",
            photoUrl = providerPhotoUrl ?: ""
        )
        val finalPhoto = if (!providerPhotoUrl.isNullOrBlank()) providerPhotoUrl
            else currentProviderProfile.value.profilePhotoUrl.ifBlank { user.photoUrl }
        val finalTrust = currentProviderProfile.value.trustLevel.displayName
        viewModelScope.launch {
            val rideId = repository.insertRide(
                Ride(
                    ownerId = user.id,
                    driverName = user.name,
                    origen = origen,
                    destino = destino,
                    fecha = fecha,
                    hora = hora,
                    vehiculo = vehiculo,
                    color = color,
                    espaciosDisponibles = espacios,
                    precioPorPersona = precio,
                    puntoEncuentro = puntoEncuentro,
                    infoAdicional = info,
                    estado = "disponible",
                    providerPhotoUrl = finalPhoto,
                    vehiclePhotoUrl = vehiclePhotoUrl,
                    trustLevel = finalTrust
                )
            )

            // Automáticamente crear notificación en BusDrive con estructura segmentada
            val notif = AppNotification(
                ownerId = "ALL", // Segmentación: Todos / Por Ruta
                type = "nuevo_ride",
                targetType = "RIDE",
                targetItemId = rideId,
                audienceType = "BY_ROUTE",
                targetLocation = destino,
                targetRoute = "$origen → $destino",
                category = "RIDE",
                ruta = "$origen → $destino",
                fecha = fecha,
                title = "🚗 Nuevo Ride disponible",
                text = "$origen → $destino\n$espacios asientos disponibles · C$$precio\nConductor: ${user.name}",
                timestamp = System.currentTimeMillis()
            )
            repository.insertNotification(notif)

            // Enviar alerta del sistema local
            LocalNotificationHelper.sendPublicationNotification(
                context = getApplication(),
                title = "🚗 Nuevo Ride disponible",
                message = "$origen → $destino\n$espacios asientos disponibles · C$$precio",
                targetType = "RIDE",
                targetId = rideId,
                category = "Rides"
            )

            showPublishRideModal.value = false
            selectedTab.value = MainTab.VIAJES
            selectedViajeSubTab.value = ViajeSubTab.RIDE

            // Demo incoming request after short delay
            simulatedIncomingRequest(rideId, user.id, origen, destino, fecha, hora, espacios)
        }
    }

    private fun simulatedIncomingRequest(
        rideId: Long,
        ownerId: String,
        origen: String,
        destino: String,
        fecha: String,
        hora: String,
        maxEspacios: Int
    ) {
        viewModelScope.launch {
            kotlinx.coroutines.delay(2000)
            val reqEspacios = (1..maxEspacios.coerceAtLeast(1)).random()
            repository.insertNotification(
                AppNotification(
                    ownerId = ownerId,
                    type = "solicitud",
                    rideId = rideId,
                    fromName = listOf("Ana R.", "Pedro G.", "Lucía M.", "José L.").random(),
                    espacios = reqEspacios,
                    ruta = "$origen → $destino",
                    fecha = "📅 $fecha · $hora",
                    title = "🚗 Nueva solicitud de espacio",
                    text = "Un pasajero solicitó $reqEspacios espacio(s) para la ruta $origen → $destino"
                )
            )
        }
    }

    fun solicitarRide(ride: Ride, espaciosCount: Int) {
        val user = currentUser.value
        viewModelScope.launch {
            val updated = ride.copy(estado = "pendiente", espaciosSolicitados = espaciosCount)
            repository.updateRide(updated)

            // Notify driver
            repository.insertNotification(
                AppNotification(
                    ownerId = ride.ownerId,
                    type = "solicitud",
                    rideId = ride.id,
                    fromName = user?.name ?: "Un pasajero",
                    espacios = espaciosCount,
                    ruta = "${ride.origen} → ${ride.destino}",
                    fecha = "📅 ${ride.fecha} · ${ride.hora}",
                    title = "🚗 Nueva solicitud de espacio",
                    text = "${user?.name ?: "Un pasajero"} solicitó $espaciosCount espacio(s)"
                )
            )

            // If user is requesting space from demo driver, simulate auto-confirm
            if (user != null && ride.ownerId != user.id) {
                kotlinx.coroutines.delay(1800)
                repository.updateRide(ride.copy(estado = "confirmado", espaciosSolicitados = espaciosCount))
                repository.insertNotification(
                    AppNotification(
                        ownerId = user.id,
                        type = "respuesta",
                        rideId = ride.id,
                        title = "✅ Solicitud aceptada",
                        text = "Tu solicitud de $espaciosCount espacio(s) en ${ride.origen} → ${ride.destino} fue ACEPTADA por ${ride.driverName}.",
                        ok = true
                    )
                )
            }
            showSolicitarRideModal.value = null
        }
    }

    fun updateRideState(ride: Ride, newEstado: String) {
        viewModelScope.launch {
            repository.updateRide(ride.copy(estado = newEstado))
        }
    }

    fun deleteRide(rideId: Long) {
        viewModelScope.launch {
            repository.deleteRide(rideId)
        }
    }

    fun publishStay(
        idToEdit: Long?,
        nombre: String,
        tipo: String,
        municipio: String,
        direccion: String,
        precio: Int,
        per: String,
        huespedes: Int,
        habitaciones: Int,
        camas: Int,
        banos: Int,
        servicios: List<String>,
        descripcion: String,
        whatsapp: String,
        telefono: String,
        photosJson: String,
        providerPhotoUrl: String? = null
    ) {
        val user = currentUser.value ?: UserProfile(
            id = "user_${System.currentTimeMillis()}",
            name = "Anfitrión",
            email = "",
            phone = telefono.ifBlank { "8888-0000" },
            photoUrl = providerPhotoUrl ?: ""
        )
        val finalPhoto = if (!providerPhotoUrl.isNullOrBlank()) providerPhotoUrl
            else currentProviderProfile.value.profilePhotoUrl.ifBlank { user.photoUrl }
        val finalTrust = currentProviderProfile.value.trustLevel.displayName
        viewModelScope.launch {
            val stayObj = Stay(
                id = idToEdit ?: 0,
                ownerId = user.id,
                hostName = user.name,
                verificado = true,
                tipo = tipo,
                nombre = nombre,
                municipio = municipio,
                direccion = direccion,
                precio = precio,
                per = per,
                huespedes = huespedes,
                habitaciones = habitaciones,
                camas = camas,
                banos = banos,
                serviciosJson = servicios.joinToString(","),
                descripcion = descripcion,
                whatsapp = whatsapp,
                telefono = telefono,
                rating = 4.9f,
                reviewsCount = 1,
                estado = "disponible",
                colorHue = (0..360).random(),
                fotosCount = 3,
                photosJson = photosJson,
                providerPhotoUrl = finalPhoto,
                trustLevel = finalTrust
            )
            val stayId = if (idToEdit != null && idToEdit > 0) {
                repository.updateStay(stayObj)
                idToEdit
            } else {
                val newId = repository.insertStay(stayObj)

                // Automáticamente crear notificación en BusDrive con estructura segmentada por ubicación
                val notif = AppNotification(
                    ownerId = "ALL", // Segmentación: Todos / Por Ubicación
                    type = "nueva_estancia",
                    targetType = "STAY",
                    targetItemId = newId,
                    audienceType = "BY_LOCATION",
                    targetLocation = municipio,
                    targetRoute = "",
                    category = "STAY",
                    ruta = municipio,
                    fecha = "Desde C$$precio por $per",
                    title = "🏠 Nueva estancia disponible",
                    text = "$nombre en $municipio\nDesde C$$precio por $per\nAnfitrión: ${user.name}",
                    timestamp = System.currentTimeMillis()
                )
                repository.insertNotification(notif)

                // Enviar alerta del sistema local
                LocalNotificationHelper.sendPublicationNotification(
                    context = getApplication(),
                    title = "🏠 Nueva estancia disponible",
                    message = "$nombre en $municipio\nDesde C$$precio por $per",
                    targetType = "STAY",
                    targetId = newId,
                    category = "Estancias"
                )

                newId
            }
            showPublishStayModal.value = null
            selectedTab.value = MainTab.VIAJES
            selectedViajeSubTab.value = ViajeSubTab.STAY
        }
    }

    fun handleNotificationClick(notification: AppNotification) {
        viewModelScope.launch {
            repository.updateNotification(notification.copy(isRead = true))
        }
        showNotifsScreen.value = false
        showAccountDetailsScreen.value = false
        showMyTicketsScreen.value = false

        val targetType = when {
            notification.targetType.isNotBlank() -> notification.targetType
            notification.type == "nuevo_boleto" -> "BOLETO"
            notification.type == "nuevo_ride" || notification.type == "solicitud" || notification.type == "respuesta" -> "RIDE"
            notification.type == "nueva_estancia" -> "STAY"
            else -> ""
        }

        if (targetType.isNotBlank()) {
            selectedTab.value = MainTab.VIAJES
            when (targetType.uppercase()) {
                "BOLETO" -> {
                    selectedViajeSubTab.value = ViajeSubTab.BOLETO
                    if (notification.targetItemId > 0) {
                        val ticket = allTickets.value.find { it.id == notification.targetItemId }
                        selectedTicketDetail.value = ticket
                    }
                }
                "RIDE" -> {
                    selectedViajeSubTab.value = ViajeSubTab.RIDE
                    val targetId = if (notification.targetItemId > 0) notification.targetItemId else notification.rideId
                    if (targetId > 0) {
                        val ride = allRides.value.find { it.id == targetId }
                        selectedRideDetail.value = ride
                    }
                }
                "STAY" -> {
                    selectedViajeSubTab.value = ViajeSubTab.STAY
                    if (notification.targetItemId > 0) {
                        val stay = allStays.value.find { it.id == notification.targetItemId }
                        selectedStayDetail.value = stay
                    }
                }
            }
        } else if (notification.type in listOf("retraso_ruta", "cambio_horario", "alerta_favorito")) {
            selectedTab.value = MainTab.EXPLORAR
        }
    }

    fun openPublicationDirectly(targetType: String, targetItemId: Long) {
        showNotifsScreen.value = false
        showAccountDetailsScreen.value = false
        showMyTicketsScreen.value = false
        selectedTab.value = MainTab.VIAJES
        when (targetType.uppercase()) {
            "BOLETO" -> {
                selectedViajeSubTab.value = ViajeSubTab.BOLETO
                if (targetItemId > 0) {
                    val ticket = allTickets.value.find { it.id == targetItemId }
                    selectedTicketDetail.value = ticket
                }
            }
            "RIDE" -> {
                selectedViajeSubTab.value = ViajeSubTab.RIDE
                if (targetItemId > 0) {
                    val ride = allRides.value.find { it.id == targetItemId }
                    selectedRideDetail.value = ride
                }
            }
            "STAY" -> {
                selectedViajeSubTab.value = ViajeSubTab.STAY
                if (targetItemId > 0) {
                    val stay = allStays.value.find { it.id == targetItemId }
                    selectedStayDetail.value = stay
                }
            }
        }
    }

    fun updateStayState(stay: Stay, newEstado: String) {
        viewModelScope.launch {
            repository.updateStay(stay.copy(estado = newEstado))
        }
    }

    fun deleteStay(stayId: Long) {
        viewModelScope.launch {
            repository.deleteStay(stayId)
        }
    }

    fun markTicketSold(ticket: Ticket) {
        val sdf = SimpleDateFormat("d MMM", Locale("es", "NI"))
        val dateStr = sdf.format(Date())
        viewModelScope.launch {
            repository.updateTicket(ticket.copy(estado = "tomado", vendidoEn = dateStr))
        }
    }

    fun repostTicket(ticket: Ticket) {
        viewModelScope.launch {
            repository.updateTicket(ticket.copy(estado = "disponible"))
        }
    }

    fun deleteTicket(id: Long) {
        viewModelScope.launch {
            repository.deleteTicket(id)
        }
    }

    fun respondRideRequest(notif: AppNotification, accept: Boolean) {
        viewModelScope.launch {
            repository.updateNotification(
                notif.copy(
                    resuelta = true,
                    isRead = true,
                    title = if (accept) "Solicitud aceptada" else "Solicitud rechazada",
                    text = if (accept) "Aceptaste a ${notif.fromName} (${notif.espacios} espacio(s)) en ${notif.ruta}"
                    else "Rechazaste la solicitud de ${notif.fromName} en ${notif.ruta}"
                )
            )
            val ridesList = repository.allRides
            // update corresponding ride if possible
        }
    }

    fun markNotificationsRead() {
        val ownerId = currentUser.value?.id ?: "demo_user"
        viewModelScope.launch {
            repository.markNotificationsRead(ownerId)
        }
    }

    fun markAllNotificationsRead() {
        markNotificationsRead()
    }

    fun updateProviderProfile(profile: ProviderProfile) {
        currentProviderProfile.value = profile
        val user = currentUser.value
        val ownerId = user?.id ?: profile.userId
        val updatedUser = (user ?: UserProfile(id = profile.userId)).copy(
            firstName = profile.firstName,
            lastName = profile.lastName,
            name = profile.fullName,
            phone = profile.phone,
            email = profile.email,
            city = profile.city.ifBlank { profile.municipality },
            photoUrl = profile.profilePhotoUrl
        )
        repository.setCurrentUser(updatedUser)

        viewModelScope.launch {
            repository.syncOwnerPublications(
                ownerId = ownerId,
                name = profile.fullName,
                phone = profile.phone,
                photoUrl = profile.profilePhotoUrl
            )
        }
    }

    fun adminApproveDocs() {
        val curr = currentProviderProfile.value
        currentProviderProfile.value = curr.copy(
            verificationState = VerificationState.VERIFIED,
            trustLevel = TrustLevel.VERIFIED,
            identityVerified = true,
            serviceVerified = true,
            rejectionReason = ""
        )
    }

    fun adminRejectDocs(reason: String) {
        val curr = currentProviderProfile.value
        currentProviderProfile.value = curr.copy(
            verificationState = VerificationState.REJECTED,
            trustLevel = TrustLevel.BASIC,
            identityVerified = false,
            serviceVerified = false,
            rejectionReason = reason.ifBlank { "La fotografía no es suficientemente clara." }
        )
    }

    fun adminRequestCorrection(reason: String) {
        val curr = currentProviderProfile.value
        currentProviderProfile.value = curr.copy(
            verificationState = VerificationState.CORRECTION_REQUIRED,
            rejectionReason = reason.ifBlank { "Por favor cargue una imagen frontal legible." }
        )
    }

    fun toggleFavoriteRoute(route: BusRoute) {
        val current = favoriteRouteIds.value.toMutableSet()
        val isFav = current.contains(route.id)
        if (isFav) {
            current.remove(route.id)
        } else {
            current.add(route.id)
            val routeDesc = "${route.origen} → ${route.destino} (${route.operador})"
            LocalNotificationHelper.sendRouteAlertNotification(
                context = getApplication(),
                title = "⭐ Ruta marcada como favorita",
                message = "Recibirás alertas de retrasos o cambios de horario para ${route.origen} → ${route.destino}.",
                routeSummary = routeDesc
            )
            val ownerId = currentUser.value?.id ?: "demo_user"
            viewModelScope.launch {
                repository.insertNotification(
                    AppNotification(
                        ownerId = ownerId,
                        type = "alerta_favorito",
                        ruta = "${route.origen} → ${route.destino}",
                        title = "⭐ Ruta añadida a favoritas",
                        text = "Monitoreando ${route.operador} (${route.origen} → ${route.destino}). Te alertaremos ante cualquier cambio.",
                        fecha = "Salida: ${route.salida}"
                    )
                )
            }
        }
        favoriteRouteIds.value = current
        prefs.edit().putStringSet("favorite_route_ids", current.map { it.toString() }.toSet()).apply()
    }

    fun notifyRouteDelay(route: BusRoute, delayMinutes: Int = 30, reason: String = "Mantenimiento y lluvia en la vía") {
        val routeDesc = "${route.origen} → ${route.destino} (${route.operador})"
        val title = "⚠️ Retraso de $delayMinutes min: ${route.origen} → ${route.destino}"
        val message = "La unidad de ${route.operador} con salida a las ${route.salida} presenta un retraso de $delayMinutes min por $reason."

        LocalNotificationHelper.sendRouteAlertNotification(
            context = getApplication(),
            title = title,
            message = message,
            routeSummary = routeDesc
        )

        val ownerId = currentUser.value?.id ?: "demo_user"
        viewModelScope.launch {
            repository.insertNotification(
                AppNotification(
                    ownerId = ownerId,
                    type = "retraso_ruta",
                    ruta = "${route.origen} → ${route.destino}",
                    title = title,
                    text = message,
                    fecha = "Salida programada: ${route.salida}"
                )
            )
        }
    }

    fun notifyScheduleChange(route: BusRoute, newTime: String = "08:45 a.m.", reason: String = "Ajuste de itinerario de temporada") {
        val routeDesc = "${route.origen} → ${route.destino} (${route.operador})"
        val title = "🕒 Cambio de horario: ${route.origen} → ${route.destino}"
        val message = "La salida de ${route.operador} ha cambiado de ${route.salida} a las $newTime. Motivo: $reason."

        LocalNotificationHelper.sendRouteAlertNotification(
            context = getApplication(),
            title = title,
            message = message,
            routeSummary = routeDesc
        )

        val ownerId = currentUser.value?.id ?: "demo_user"
        viewModelScope.launch {
            repository.insertNotification(
                AppNotification(
                    ownerId = ownerId,
                    type = "cambio_horario",
                    ruta = "${route.origen} → ${route.destino}",
                    title = title,
                    text = message,
                    fecha = "Nuevo horario: $newTime"
                )
            )
        }
    }

    fun submitReview(
        target: ReviewTarget,
        rating: Float,
        comment: String,
        tags: List<String>
    ) {
        val user = currentUser.value ?: UserProfile(
            id = "user_${System.currentTimeMillis()}",
            name = "Viajero BusDrive",
            email = "",
            phone = "+505 8888-0000"
        )
        viewModelScope.launch {
            repository.insertReview(
                Review(
                    targetType = target.targetType,
                    targetId = target.targetId,
                    targetOwnerId = target.targetOwnerId,
                    reviewerId = user.id,
                    reviewerName = user.name,
                    reviewerPhotoUrl = user.photoUrl,
                    rating = rating,
                    comment = comment,
                    tags = tags.joinToString(","),
                    timestamp = System.currentTimeMillis()
                )
            )
            // Notificar al dueño sobre la nueva reseña
            repository.insertNotification(
                AppNotification(
                    ownerId = target.targetOwnerId,
                    type = "info",
                    targetType = target.targetType,
                    targetItemId = target.targetId,
                    title = "⭐ Nueva calificación recibida",
                    text = "${user.name} te ha calificado con $rating estrellas: \"${comment.take(60)}\"",
                    timestamp = System.currentTimeMillis()
                )
            )
            showReviewModal.value = null
        }
    }

    fun shareRideDetails(context: Context, ride: Ride) {
        val text = """
            🛡️ *Detalles de Viaje Seguro - BusDrive Nicaragua*
            🚗 *Conductor:* ${ride.driverName} (${ride.trustLevel})
            📍 *Ruta:* ${ride.origen} ➔ ${ride.destino}
            📅 *Fecha:* ${ride.fecha} · 🕒 ${ride.hora}
            🚘 *Vehículo:* ${ride.vehiculo} · Color: ${ride.color}
            📍 *Punto de encuentro:* ${if (ride.puntoEncuentro.isNotBlank()) ride.puntoEncuentro else "A coordinar"}
            💰 *Aporte:* C$${ride.precioPorPersona} por persona
            
            _Compartido desde BusDrive - Plataforma de Transporte Comunitario Seguro._
        """.trimIndent()

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, "Compartir viaje seguro con")
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(shareIntent)
    }

    fun callEmergencyNumber(context: Context, number: String) {
        val cleanNum = number.replace(Regex("[^0-9+]"), "")
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$cleanNum"))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}

data class ReviewTarget(
    val targetType: String,
    val targetId: Long,
    val targetOwnerId: String,
    val title: String,
    val subtitle: String,
    val photoUrl: String = ""
)
