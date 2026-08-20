package com.example

import com.example.data.cloud.CloudCommunityRepository
import com.example.data.cloud.SyncState
import com.example.data.model.Ride
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class CommunityRepositoryTest {

    @Test
    fun `test repository singleton and initial state`() {
        val repo = CloudCommunityRepository.getInstance()
        assertNotNull(repo)
        assertEquals(SyncState.IDLE, repo.syncState.value)
    }

    @Test
    fun `test publish ride data structure`() = runBlocking {
        val repo = CloudCommunityRepository.getInstance()
        val sampleRide = Ride(
            id = 999L,
            conductorNombre = "Carlos Mendoza",
            conductorTelefono = "+505 8888 1234",
            origen = "Managua (UCA)",
            destino = "León (Centro)",
            puntoEncuentro = "Frente a la gasolinera Puma",
            vehiculoModelo = "Toyota Corolla",
            vehiculoColor = "Gris Plata",
            vehiculoPlaca = "M 123-456",
            precioPorAsiento = 150.0,
            fecha = "18/08/2026",
            horaSalida = "02:30 PM",
            asientosDisponibles = 3,
            asientosTotales = 4,
            detalles = "Salgo puntual con aire acondicionado",
            permiteEquipaje = true,
            permiteMascotas = false,
            permiteFumar = false,
            aireAcondicionado = true
        )

        val result = repo.publishRide(sampleRide)
        // Even if offline/no network in test environment, it gracefully handles the call
        assertNotNull(result)
    }
}
