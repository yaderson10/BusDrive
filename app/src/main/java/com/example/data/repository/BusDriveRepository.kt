package com.example.data.repository

import com.example.data.local.BusDriveDao
import com.example.data.local.PrepopulatedData
import com.example.data.model.AppNotification
import com.example.data.model.BusRoute
import com.example.data.model.Ride
import com.example.data.model.Stay
import com.example.data.model.Ticket
import com.example.data.model.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BusDriveRepository(private val dao: BusDriveDao) {

    private val _currentUser = MutableStateFlow<UserProfile?>(null)
    val currentUser: StateFlow<UserProfile?> = _currentUser.asStateFlow()

    fun setCurrentUser(user: UserProfile?) {
        _currentUser.value = user
    }

    val allRoutes: Flow<List<BusRoute>> = dao.getAllRoutes()
    val allTickets: Flow<List<Ticket>> = dao.getAllTickets()
    val allRides: Flow<List<Ride>> = dao.getAllRides()
    val allStays: Flow<List<Stay>> = dao.getAllStays()
    val allReviews: Flow<List<com.example.data.model.Review>> = dao.getAllReviews()

    fun getReviewsForOwner(ownerId: String): Flow<List<com.example.data.model.Review>> =
        dao.getReviewsForOwner(ownerId)

    suspend fun insertReview(review: com.example.data.model.Review): Long = dao.insertReview(review)

    suspend fun prepopulateIfNeeded() {
        if (dao.getRoutesCount() == 0) {
            dao.insertRoutes(PrepopulatedData.INITIAL_ROUTES)
        }
        if (dao.getTicketsCount() == 0) {
            PrepopulatedData.INITIAL_TICKETS.forEach { dao.insertTicket(it) }
        }
        if (dao.getRidesCount() == 0) {
            PrepopulatedData.INITIAL_RIDES.forEach { dao.insertRide(it) }
        }
        if (dao.getStaysCount() == 0) {
            PrepopulatedData.INITIAL_STAYS.forEach { dao.insertStay(it) }
        }
        if (dao.getReviewsCount() == 0) {
            PrepopulatedData.INITIAL_REVIEWS.forEach { dao.insertReview(it) }
        }
    }

    fun getNotificationsForUser(userId: String): Flow<List<AppNotification>> =
        dao.getNotificationsForUser(userId)

    suspend fun insertTicket(ticket: Ticket): Long = dao.insertTicket(ticket)
    suspend fun updateTicket(ticket: Ticket) = dao.updateTicket(ticket)
    suspend fun deleteTicket(id: Long) = dao.deleteTicketById(id)

    suspend fun insertRide(ride: Ride): Long = dao.insertRide(ride)
    suspend fun updateRide(ride: Ride) = dao.updateRide(ride)
    suspend fun deleteRide(id: Long) = dao.deleteRideById(id)

    suspend fun insertStay(stay: Stay): Long = dao.insertStay(stay)
    suspend fun updateStay(stay: Stay) = dao.updateStay(stay)
    suspend fun deleteStay(id: Long) = dao.deleteStayById(id)

    suspend fun insertNotification(notification: AppNotification): Long = dao.insertNotification(notification)
    suspend fun updateNotification(notification: AppNotification) = dao.updateNotification(notification)
    suspend fun markNotificationsRead(userId: String) = dao.markAllNotificationsAsRead(userId)

    suspend fun syncOwnerPublications(ownerId: String, name: String, phone: String, photoUrl: String) {
        dao.updateTicketsOwnerInfo(ownerId, name, phone, photoUrl)
        dao.updateRidesOwnerInfo(ownerId, name, photoUrl)
        dao.updateStaysOwnerInfo(ownerId, name, phone, photoUrl)
    }
}
