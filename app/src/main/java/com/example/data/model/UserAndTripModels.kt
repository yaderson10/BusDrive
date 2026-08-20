package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class TrustLevel(val displayName: String, val badgeColorHex: Long, val symbol: String) {
    BASIC("⚪ Básico", 0xFF6B7280, "⚪"),
    IDENTIFIED("🔵 Identificado", 0xFF2563EB, "🔵"),
    VERIFIED("🟢 Verificado", 0xFF16A34A, "🟢"),
    PROFESSIONAL("🟣 Profesional", 0xFF9333EA, "🟣")
}

enum class VerificationState(val displayName: String) {
    PENDING("Pendiente"),
    IN_REVIEW("En revisión"),
    VERIFIED("Verificado"),
    REJECTED("Rechazado"),
    CORRECTION_REQUIRED("Requiere corrección")
}

data class ProviderProfile(
    val userId: String = "demo_user",
    val firstName: String = "Yader",
    val lastName: String = "Castellón",
    val fullName: String = "Yader Castellón",
    val cedula: String = "001-110895-0002A",
    val commercialName: String = "",
    val phone: String = "505 8888-9999",
    val email: String = "yader@busdrive.ni",
    val city: String = "Puerto Cabezas (Bilwi)",
    val municipality: String = "Puerto Cabezas (Bilwi)",
    val community: String = "Barrio El Muelle",
    val userType: String = "",
    val profilePhotoUrl: String = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=400",
    val trustLevel: TrustLevel = TrustLevel.VERIFIED,
    val verificationState: VerificationState = VerificationState.VERIFIED,
    val rejectionReason: String = "",
    val rating: Float = 4.9f,
    val reviewsCount: Int = 38,
    val tripsCount: Int = 42,
    val memberSinceYear: String = "2026",
    val phoneVerified: Boolean = true,
    val identityVerified: Boolean = true,
    val serviceVerified: Boolean = true,
    // Private documents (Not shown publicly)
    val docFrontUrl: String = "",
    val docBackUrl: String = "",
    val selfieUrl: String = "",
    val driverLicenseUrl: String = "",
    val vehicleDocUrl: String = "",
    val businessDocUrl: String = ""
)

val ProviderProfile.isProfileComplete: Boolean
    get() = fullName.isNotBlank() && phone.isNotBlank() && (city.isNotBlank() || municipality.isNotBlank())

@Entity(tableName = "tickets")
data class Ticket(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val ownerId: String,
    val nombreContacto: String,
    val telefonoContacto: String,
    val origen: String,
    val destino: String,
    val fecha: String,
    val hora: String,
    val operador: String = "",
    val asiento: String,
    val precio: String = "",
    val precioOriginal: String = "",
    val cantidadBoletos: Int = 1,
    val esTransferible: Boolean = true,
    val evidenciaFotoUrl: String = "",
    val nota: String = "",
    val estado: String = "disponible", // disponible, tomado
    val vendidoEn: String = "",
    val trustLevel: String = "🟢 Verificado",
    val providerPhotoUrl: String = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=400",
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "rides")
data class Ride(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val ownerId: String,
    val driverName: String,
    val origen: String,
    val destino: String,
    val fecha: String,
    val hora: String,
    val horaLlegada: String = "",
    val paradasIntermedias: String = "",
    val vehiculo: String,
    val tipoVehiculo: String = "Automóvil", // Automóvil, SUV, Pickup, Microbús, Van, Bus, Motocicleta
    val marcaModeloAnno: String = "",
    val placa: String = "",
    val color: String = "",
    val espaciosDisponibles: Int = 1,
    val precioPorPersona: String = "",
    val tipoTarifa: String = "Por pasajero", // Por pasajero, Viaje completo, Compartido
    val puntoEncuentro: String = "",
    val amenitiesJson: String = "Aire acondicionado,Wi-Fi,Equipaje", // comma separated
    val infoAdicional: String = "",
    val estado: String = "disponible", // disponible, pendiente, confirmado, rechazado, completado, cancelado
    val espaciosSolicitados: Int = 0,
    val trustLevel: String = "🟢 Verificado",
    val providerPhotoUrl: String = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=400",
    val vehiclePhotoUrl: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "stays")
data class Stay(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val ownerId: String,
    val hostName: String,
    val verificado: Boolean = true,
    val tipo: String, // Habitación, Casa, Apartamento, Hotel, Hostal, Cabaña, Casa completa
    val nombre: String,
    val municipio: String,
    val comunidad: String = "Centro",
    val direccion: String = "",
    val precio: Int,
    val per: String = "noche", // noche, semana, mes
    val huespedes: Int = 2,
    val habitaciones: Int = 1,
    val camas: Int = 1,
    val banos: Int = 1,
    val serviciosJson: String = "", // comma-separated or json string
    val descripcion: String = "",
    val whatsapp: String = "",
    val telefono: String = "",
    val rating: Float = 4.8f,
    val reviewsCount: Int = 12,
    val estado: String = "disponible", // disponible, reservado, pausado, no disponible
    val colorHue: Int = 200,
    val fotosCount: Int = 3,
    val photosJson: String = "", // pipe separated base64 or photo URIs
    val trustLevel: String = "🟢 Verificado",
    val providerPhotoUrl: String = "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?w=400",
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "notifications")
data class AppNotification(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val ownerId: String = "ALL", // Recipient userId, or "ALL" / "BROADCAST"
    val type: String = "info", // "nuevo_boleto", "nuevo_ride", "nueva_estancia", "solicitud", "respuesta", "retraso_ruta", "cambio_horario", "alerta_favorito", "info"
    val targetType: String = "", // "BOLETO", "RIDE", "STAY", "RUTA"
    val targetItemId: Long = 0, // id of Ticket, Ride, Stay, or BusRoute
    val audienceType: String = "ALL", // "ALL", "BY_LOCATION", "BY_ROUTE", "BY_INTEREST"
    val targetLocation: String = "", // e.g. "Puerto Cabezas", "Managua"
    val targetRoute: String = "", // e.g. "Managua → Puerto Cabezas"
    val category: String = "GENERAL", // "BOLETO", "RIDE", "STAY", "RUTA", "GENERAL"
    val rideId: Long = 0,
    val fromName: String = "",
    val espacios: Int = 1,
    val ruta: String = "",
    val fecha: String = "",
    val title: String = "",
    val text: String = "",
    val ok: Boolean = true,
    val isRead: Boolean = false,
    val resuelta: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "reviews")
data class Review(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val targetType: String = "RIDE", // RIDE, STAY, TICKET, CONDUCTOR
    val targetId: Long = 0,
    val targetOwnerId: String = "",
    val reviewerId: String = "",
    val reviewerName: String = "",
    val reviewerPhotoUrl: String = "",
    val rating: Float = 5f,
    val comment: String = "",
    val tags: String = "", // comma-separated: "Puntual,Vehículo limpio,Manejo seguro"
    val timestamp: Long = System.currentTimeMillis()
)

data class UserProfile(
    val id: String = "demo_user",
    val firstName: String = "Yader",
    val lastName: String = "Castellón",
    val name: String = "Yader Castellón",
    val email: String = "yader@busdrive.ni",
    val phone: String = "505 8888-9999",
    val city: String = "Puerto Cabezas (Bilwi)",
    val userIdNumber: String = "88991234",
    val memberSince: String = "11 de agosto de 2026",
    val provider: String = "email",
    val photoUrl: String = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=400",
    val userRole: String = "Pasajero",
    val isVerified: Boolean = true
)

data class MandaditoCourier(
    val id: String = "courier_1",
    val name: String,
    val phone: String,
    val vehicleType: String = "Motocicleta 150cc",
    val vehiclePlate: String = "M-14285",
    val photoUrl: String = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=400",
    val zoneCoverage: String = "Centro y alrededores",
    val baseRate: String = "A convenir",
    val rating: Float = 4.9f,
    val reviewsCount: Int = 28,
    val completedDeliveries: Int = 114,
    val isAvailable: Boolean = true,
    val isVerified: Boolean = true,
    val services: List<String> = listOf("Envíos Express", "Compras en Farmacia/Súper", "Comida & Restaurantes", "Paquetería"),
    val workingHours: String = "7:00 AM - 9:00 PM",
    val description: String = "Servicio rápido y seguro en moto. Conozco todas las direcciones y garantizo el cuidado de tus pedidos."
)

