package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.model.AppNotification
import com.example.data.model.BusRoute
import com.example.data.model.Review
import com.example.data.model.Ride
import com.example.data.model.Stay
import com.example.data.model.Ticket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [BusRoute::class, Ticket::class, Ride::class, Stay::class, AppNotification::class, Review::class],
    version = 11,
    exportSchema = false
)
abstract class BusDriveDatabase : RoomDatabase() {

    abstract fun dao(): BusDriveDao

    companion object {
        @Volatile
        private var INSTANCE: BusDriveDatabase? = null

        fun getInstance(context: Context): BusDriveDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BusDriveDatabase::class.java,
                    "busdrive_db"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            INSTANCE?.let { database ->
                                CoroutineScope(Dispatchers.IO).launch {
                                    database.dao().insertRoutes(PrepopulatedData.INITIAL_ROUTES)
                                    PrepopulatedData.INITIAL_TICKETS.forEach { database.dao().insertTicket(it) }
                                    PrepopulatedData.INITIAL_RIDES.forEach { database.dao().insertRide(it) }
                                    PrepopulatedData.INITIAL_STAYS.forEach { database.dao().insertStay(it) }
                                    PrepopulatedData.INITIAL_REVIEWS.forEach { database.dao().insertReview(it) }
                                }
                            }
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
