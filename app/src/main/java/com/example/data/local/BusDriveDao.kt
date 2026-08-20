package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.AppNotification
import com.example.data.model.BusRoute
import com.example.data.model.Ride
import com.example.data.model.Stay
import com.example.data.model.Ticket
import kotlinx.coroutines.flow.Flow

@Dao
interface BusDriveDao {

    // Routes
    @Query("SELECT * FROM bus_routes ORDER BY origen, destino")
    fun getAllRoutes(): Flow<List<BusRoute>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutes(routes: List<BusRoute>)

    @Query("DELETE FROM bus_routes")
    suspend fun deleteAllRoutes()

    @Query("SELECT COUNT(*) FROM bus_routes")
    suspend fun getRoutesCount(): Int

    // Tickets
    @Query("SELECT * FROM tickets ORDER BY timestamp DESC")
    fun getAllTickets(): Flow<List<Ticket>>

    @Query("SELECT COUNT(*) FROM tickets")
    suspend fun getTicketsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicket(ticket: Ticket): Long

    @Update
    suspend fun updateTicket(ticket: Ticket)

    @Query("DELETE FROM tickets WHERE id = :id")
    suspend fun deleteTicketById(id: Long)

    // Rides
    @Query("SELECT * FROM rides ORDER BY timestamp DESC")
    fun getAllRides(): Flow<List<Ride>>

    @Query("SELECT COUNT(*) FROM rides")
    suspend fun getRidesCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRide(ride: Ride): Long

    @Update
    suspend fun updateRide(ride: Ride)

    @Query("DELETE FROM rides WHERE id = :id")
    suspend fun deleteRideById(id: Long)

    // Stays
    @Query("SELECT * FROM stays ORDER BY timestamp DESC")
    fun getAllStays(): Flow<List<Stay>>

    @Query("SELECT COUNT(*) FROM stays")
    suspend fun getStaysCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStay(stay: Stay): Long

    @Update
    suspend fun updateStay(stay: Stay)

    @Query("DELETE FROM stays WHERE id = :id")
    suspend fun deleteStayById(id: Long)

    // Sincronización de publicaciones del usuario
    @Query("UPDATE tickets SET nombreContacto = :name, telefonoContacto = :phone, providerPhotoUrl = :photoUrl WHERE ownerId = :ownerId")
    suspend fun updateTicketsOwnerInfo(ownerId: String, name: String, phone: String, photoUrl: String)

    @Query("UPDATE rides SET driverName = :name, providerPhotoUrl = :photoUrl WHERE ownerId = :ownerId")
    suspend fun updateRidesOwnerInfo(ownerId: String, name: String, photoUrl: String)

    @Query("UPDATE stays SET hostName = :name, telefono = :phone, whatsapp = :phone, providerPhotoUrl = :photoUrl WHERE ownerId = :ownerId")
    suspend fun updateStaysOwnerInfo(ownerId: String, name: String, phone: String, photoUrl: String)

    // Notifications
    @Query("SELECT * FROM notifications WHERE ownerId = :ownerId OR ownerId = 'ALL' OR ownerId = 'BROADCAST' ORDER BY timestamp DESC")
    fun getNotificationsForUser(ownerId: String): Flow<List<AppNotification>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: AppNotification): Long

    @Update
    suspend fun updateNotification(notification: AppNotification)

    @Query("UPDATE notifications SET isRead = 1 WHERE ownerId = :ownerId OR ownerId = 'ALL' OR ownerId = 'BROADCAST'")
    suspend fun markAllNotificationsAsRead(ownerId: String)

    // Reviews
    @Query("SELECT * FROM reviews WHERE targetOwnerId = :ownerId ORDER BY timestamp DESC")
    fun getReviewsForOwner(ownerId: String): Flow<List<com.example.data.model.Review>>

    @Query("SELECT * FROM reviews ORDER BY timestamp DESC")
    fun getAllReviews(): Flow<List<com.example.data.model.Review>>

    @Query("SELECT COUNT(*) FROM reviews")
    suspend fun getReviewsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReview(review: com.example.data.model.Review): Long
}
