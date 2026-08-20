package com.example.data.local

import com.example.data.model.BusRoute
import com.example.data.model.Ride
import com.example.data.model.Stay
import com.example.data.model.Ticket

object PrepopulatedData {

    val INITIAL_ROUTES = listOf(
        BusRoute(origen = "Siuna", destino = "Managua", fuente = "formal", salida = "3:30 a.m.", llegada = "9:30 a.m.", operador = "Martha Elena Baltodano Reyes", cooperativa = "Cotlantico", tipo = "Autobus", capacidad = "63 / 66", placa = "M1661 / M2506", clasificacion = "Troncal Principal", modalidad = "Ordinario", dias = "Diario (Lunes-Domingos)", tiempo = "06:00", terminal = "Terminal Alejandro Castellon, frente A ENATREL, Siuna", contacto = "8888-0000", precio = 400),
        BusRoute(origen = "Siuna", destino = "Managua", fuente = "formal", salida = "12:00 p.m.", llegada = "9:10 a.m.", operador = "Julio Cesar Perez / Yassir Carter", cooperativa = "Cootrasgeposiu, R.L.", tipo = "Autobus", capacidad = "62 / 44 / 60", placa = "RN112 / RN170", clasificacion = "Troncal Principal", modalidad = "Ordinario", dias = "Diario (Lunes-Domingos)", tiempo = "06:00", terminal = "Terminal Alejandro Castellon, frente A ENATREL, Siuna", contacto = "", precio = 400),
        BusRoute(origen = "Siuna", destino = "Managua", fuente = "formal", salida = "3:00 p.m.", llegada = "9:30 a.m.", operador = "Eliel Francisco Monzon Ruiz", cooperativa = "Transmina", tipo = "Autobus", capacidad = "66", placa = "M 2696 / ES 22638", clasificacion = "Troncal Principal", modalidad = "Ordinario", dias = "Diario (Lunes-Domingos)", tiempo = "06:00", terminal = "Terminal Alejandro Castellon, frente A ENATREL, Siuna", contacto = "", precio = 400),
        BusRoute(origen = "Siuna", destino = "Managua", fuente = "formal", salida = "5:00 p.m.", llegada = "9:30 a.m.", operador = "Cesar Antonio Luquez Lopez", cooperativa = "Cotlantico", tipo = "Autobus", capacidad = "65 / 66", placa = "M0836 / M0502", clasificacion = "Troncal Principal", modalidad = "Ordinario", dias = "Diario (Lunes-Domingos)", tiempo = "06:00", terminal = "Terminal Alejandro Castellon, frente A ENATREL, Siuna", contacto = "", precio = 400),
        BusRoute(origen = "Siuna", destino = "Managua", fuente = "formal", salida = "5:30 a.m.", llegada = "6:45 a.m.", operador = "Cesar Antonio Luquez Lopez", cooperativa = "Cotlantico", tipo = "Autobus", capacidad = "66 / 45", placa = "M 0545 / M 0544", clasificacion = "Troncal Principal", modalidad = "Expreso", dias = "Diario (Lunes-viernes y Domingos)", tiempo = "06:45", terminal = "Terminal Alejandro Castellon frente a ENATREL, Siuna", contacto = "", precio = 450),
        BusRoute(origen = "Siuna", destino = "Managua", fuente = "formal", salida = "8:00 p.m.", llegada = "6:45 a.m.", operador = "Alejandro Joelvin Luquez Soza", cooperativa = "Cootracenic / Cotlantico", tipo = "Autobus", capacidad = "65 / 72", placa = "M1013 / M 0026", clasificacion = "Troncal Principal", modalidad = "Expreso", dias = "Diario (Lunes-viernes y Domingos)", tiempo = "06:45", terminal = "Terminal Alejandro Castellon frente a ENATREL, Siuna", contacto = "", precio = 450),
        
        BusRoute(origen = "Managua", destino = "Siuna", fuente = "formal", salida = "4:00 a.m.", llegada = "1:50 p.m.", operador = "Martha Elena Baltodano Reyes", cooperativa = "Cotlantico", tipo = "Autobus", capacidad = "66", placa = "M1661", clasificacion = "Troncal Principal", modalidad = "Ordinario", dias = "Diario (Lunes-Domingos)", tiempo = "06:00", terminal = "Mercado Mayoreo, Managua", contacto = "", precio = 400),
        BusRoute(origen = "Managua", destino = "Siuna", fuente = "formal", salida = "12:00 p.m.", llegada = "9:50 a.m.", operador = "Julio Cesar Perez / Yassir Carter", cooperativa = "Cootrasgeposiu, R.L.", tipo = "Autobus", capacidad = "60", placa = "RN112", clasificacion = "Troncal Principal", modalidad = "Ordinario", dias = "Diario (Lunes-Domingos)", tiempo = "06:00", terminal = "Mercado Mayoreo, Managua", contacto = "", precio = 400),
        BusRoute(origen = "Managua", destino = "Siuna", fuente = "formal", salida = "3:10 p.m.", llegada = "9:30 a.m.", operador = "Eliel Francisco Monzon Ruiz", cooperativa = "Transmina", tipo = "Autobus", capacidad = "66", placa = "M 2696", clasificacion = "Troncal Principal", modalidad = "Ordinario", dias = "Diario (Lunes-Domingos)", tiempo = "06:00", terminal = "Mercado Mayoreo, Managua", contacto = "", precio = 400),
        BusRoute(origen = "Managua", destino = "Siuna", fuente = "formal", salida = "5:00 p.m.", llegada = "9:30 a.m.", operador = "Cesar Antonio Luquez Lopez", cooperativa = "Cotlantico", tipo = "Autobus", capacidad = "65", placa = "M0836", clasificacion = "Troncal Principal", modalidad = "Ordinario", dias = "Diario (Lunes-Domingos)", tiempo = "06:00", terminal = "Mercado Mayoreo, Managua", contacto = "", precio = 400),

        BusRoute(origen = "Siuna", destino = "Rosita", fuente = "formal", salida = "5:30 a.m.", llegada = "3:30 a.m.", operador = "Pastor Aguilar Martinez", cooperativa = "Cristobal Colon", tipo = "Autobus", capacidad = "72", placa = "RN 126", clasificacion = "ALIMENTADORA PRINCIPAL", modalidad = "ORDINARIO", dias = "Todos los dias", tiempo = "03:30", terminal = "Terminal Alejandro Castellon, Frente a ENATREL Siuna", contacto = "", precio = 150),
        BusRoute(origen = "Siuna", destino = "Rosita", fuente = "formal", salida = "7:00 a.m.", llegada = "3:30 a.m.", operador = "Eduardo Castellon Sanchez", cooperativa = "Cootrasgeposiu R,L.", tipo = "Autobus", capacidad = "60", placa = "RN 26", clasificacion = "ALIMENTADORA PRINCIPAL", modalidad = "ORDINARIO", dias = "Todos los dias", tiempo = "03:30", terminal = "Terminal Alejandro Castellon, Frente a ENATREL Siuna", contacto = "", precio = 150),
        BusRoute(origen = "Siuna", destino = "Rosita", fuente = "formal", salida = "8:30 a.m.", llegada = "3:30 a.m.", operador = "Leonida Orozco Polanco", cooperativa = "Cootrasgeposiu R,L.", tipo = "Autobus", capacidad = "60", placa = "RN 162", clasificacion = "ALIMENTADORA PRINCIPAL", modalidad = "ORDINARIO", dias = "Todos los dias", tiempo = "03:30", terminal = "Terminal Alejandro Castellon, Frente a ENATREL Siuna", contacto = "", precio = 150),
        BusRoute(origen = "Siuna", destino = "Rosita", fuente = "formal", salida = "10:00 a.m.", llegada = "3:30 a.m.", operador = "Orlando Pablo Carther Rivera", cooperativa = "Cootrasgeposiu R,L.", tipo = "Autobus", capacidad = "50", placa = "RN 128", clasificacion = "ALIMENTADORA PRINCIPAL", modalidad = "ORDINARIO", dias = "Todos los dias", tiempo = "03:30", terminal = "Terminal Alejandro Castellon, Frente a ENATREL Siuna", contacto = "", precio = 150),
        BusRoute(origen = "Siuna", destino = "Rosita", fuente = "formal", salida = "1:00 p.m.", llegada = "3:30 a.m.", operador = "Jose Antonio Granado C", cooperativa = "Cootransmina R,L.", tipo = "Autobus", capacidad = "84", placa = "RN 121", clasificacion = "ALIMENTADORA PRINCIPAL", modalidad = "ORDINARIO", dias = "Todos los dias", tiempo = "03:30", terminal = "Terminal Alejandro Castellon, Frente a ENATREL Siuna", contacto = "", precio = 150),
        BusRoute(origen = "Siuna", destino = "Rosita", fuente = "formal", salida = "3:00 p.m.", llegada = "3:30 a.m.", operador = "Luis Orozco Jarquin", cooperativa = "Cootransmina R,L.", tipo = "Autobus", capacidad = "60", placa = "RN 177", clasificacion = "ALIMENTADORA PRINCIPAL", modalidad = "ORDINARIO", dias = "Todos los dias", tiempo = "03:30", terminal = "Terminal Alejandro Castellon, Frente a ENATREL Siuna", contacto = "", precio = 150),

        BusRoute(origen = "Rosita", destino = "Siuna", fuente = "formal", salida = "5:30 a.m.", llegada = "3:30 a.m.", operador = "Luis Armando Barrera Molina", cooperativa = "Cootransmina R,L.", tipo = "Autobus", capacidad = "72", placa = "RN 143", clasificacion = "ALIMENTADORA PRINCIPAL", modalidad = "ORDINARIO", dias = "TODOS LOS DIAS", tiempo = "03:30", terminal = "Terminal Jesus Antonio Granado, Contiguo al Mercado municipal de Rosita", contacto = "", precio = 150),
        BusRoute(origen = "Rosita", destino = "Siuna", fuente = "formal", salida = "7:00 a.m.", llegada = "3:30 a.m.", operador = "Jose Antonio Granado C", cooperativa = "Cootransmina R,L.", tipo = "Autobus", capacidad = "84", placa = "RN 121", clasificacion = "ALIMENTADORA PRINCIPAL", modalidad = "ORDINARIO", dias = "TODOS LOS DIAS", tiempo = "03:30", terminal = "Terminal Jesus Antonio Granado, Contiguo al Mercado municipal de Rosita", contacto = "", precio = 150),
        BusRoute(origen = "Rosita", destino = "Siuna", fuente = "formal", salida = "8:30 a.m.", llegada = "3:30 a.m.", operador = "Luis Orozco Jarquin", cooperativa = "Cootransmina R,L.", tipo = "Autobus", capacidad = "60", placa = "RN 177", clasificacion = "ALIMENTADORA PRINCIPAL", modalidad = "ORDINARIO", dias = "TODOS LOS DIAS", tiempo = "03:30", terminal = "Terminal Jesus Antonio Granado, Contiguo al Mercado municipal de Rosita", contacto = "", precio = 150),
        BusRoute(origen = "Rosita", destino = "Siuna", fuente = "formal", salida = "11:30 a.m.", llegada = "3:30 a.m.", operador = "Pastor Aguilar Martinez", cooperativa = "Cristobal Colon", tipo = "Autobus", capacidad = "72", placa = "RN 126", clasificacion = "ALIMENTADORA PRINCIPAL", modalidad = "ORDINARIO", dias = "TODOS LOS DIAS", tiempo = "03:30", terminal = "Terminal Jesus Antonio Granado, Contiguo al Mercado municipal de Rosita", contacto = "", precio = 150),

        BusRoute(origen = "Siuna", destino = "Puerto Cabezas", fuente = "formal", salida = "4:30 a.m.", llegada = "9:20 a.m.", operador = "Nestor Antonio Reyes Rugama", cooperativa = "Cootransmina R,L.", tipo = "Autobus", capacidad = "66", placa = "RN 187", clasificacion = "Alimentadora Principal", modalidad = "Ordinaria", dias = "diario", tiempo = "09:20", terminal = "Terminal Sur Alejandro Castellon, Siuna", contacto = "", precio = 350),
        BusRoute(origen = "Siuna", destino = "Puerto Cabezas", fuente = "formal", salida = "8:30 a.m.", llegada = "9:20 a.m.", operador = "Juan Ramon Urbina Jarquin", cooperativa = "Cootransmina R,L.", tipo = "Autobus", capacidad = "65", placa = "RN 137", clasificacion = "Alimentadora Principal", modalidad = "Ordinaria", dias = "diario", tiempo = "09:20", terminal = "Terminal Sur Alejandro Castellon, Siuna", contacto = "", precio = 350),

        // --- MANAGUA -> PUERTO CABEZAS (LINEA INTERDEPARTAMENTAL) ---
        // Lunes
        BusRoute(origen = "Managua", destino = "Puerto Cabezas", fuente = "linea", salida = "12:00 p.m.", llegada = "6:00 a.m.", operador = "MORENITO", cooperativa = "Iván Antonio Montano", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Lunes", tiempo = "14:00", terminal = "Cotrán de la Costa, Mayoreo (Managua)", contacto = "8821-8636 / 8647-2735 / 8725-3376", precio = 600),
        BusRoute(origen = "Managua", destino = "Puerto Cabezas", fuente = "linea", salida = "6:00 p.m.", llegada = "8:00 a.m.", operador = "CENTENO", cooperativa = "Carlos Raúl Rojas Centeno", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Lunes", tiempo = "14:00", terminal = "Cotrán de la Costa, Mayoreo (Managua)", contacto = "8632-7321 / 8875-7965", precio = 600),
        BusRoute(origen = "Managua", destino = "Puerto Cabezas", fuente = "linea", salida = "8:30 p.m.", llegada = "10:30 a.m.", operador = "SANTA MONICA", cooperativa = "Freddy Man Cruz Lazo", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Lunes", tiempo = "14:00", terminal = "Cotrán de la Costa, Mayoreo (Managua)", contacto = "8725-3376", precio = 600),
        // Martes
        BusRoute(origen = "Managua", destino = "Puerto Cabezas", fuente = "linea", salida = "12:00 p.m.", llegada = "6:00 a.m.", operador = "LILY MAE", cooperativa = "Lily mae Henríquez James", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Martes", tiempo = "14:00", terminal = "Cotrán de la Costa, Mayoreo (Managua)", contacto = "8657-5581 / 5853-0723", precio = 600),
        BusRoute(origen = "Managua", destino = "Puerto Cabezas", fuente = "linea", salida = "6:00 p.m.", llegada = "8:00 a.m.", operador = "LA TANGA", cooperativa = "Marcelino Amador García", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Martes", tiempo = "14:00", terminal = "Cotrán de la Costa, Mayoreo (Managua)", contacto = "8362-1710 / 7511-5400 / 8431-3939", precio = 600),
        BusRoute(origen = "Managua", destino = "Puerto Cabezas", fuente = "linea", salida = "8:30 p.m.", llegada = "10:30 a.m.", operador = "SANTA MONICA", cooperativa = "Freddy Man Cruz Lazo", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Martes", tiempo = "14:00", terminal = "Cotrán de la Costa, Mayoreo (Managua)", contacto = "8725-3376", precio = 600),
        // Miercoles
        BusRoute(origen = "Managua", destino = "Puerto Cabezas", fuente = "linea", salida = "12:00 p.m.", llegada = "6:00 a.m.", operador = "LILY MAE", cooperativa = "Lily mae Henríquez James", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Miércoles", tiempo = "14:00", terminal = "Cotrán de la Costa, Mayoreo (Managua)", contacto = "8657-5581 / 5853-0723", precio = 600),
        BusRoute(origen = "Managua", destino = "Puerto Cabezas", fuente = "linea", salida = "6:00 p.m.", llegada = "8:00 a.m.", operador = "CARVAJAL", cooperativa = "Audelia Del Rosario Acevedo Gutiérrez", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Miércoles", tiempo = "14:00", terminal = "Cotrán de la Costa, Mayoreo (Managua)", contacto = "8943-2782 / 5803-1483", precio = 600),
        BusRoute(origen = "Managua", destino = "Puerto Cabezas", fuente = "linea", salida = "8:30 p.m.", llegada = "10:30 a.m.", operador = "DUNKAN", cooperativa = "Betty Lan Ducan", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Miércoles", tiempo = "14:00", terminal = "Cotrán de la Costa, Mayoreo (Managua)", contacto = "8742-6965 / 8200-4997 / 7879-5366", precio = 600),
        // Jueves
        BusRoute(origen = "Managua", destino = "Puerto Cabezas", fuente = "linea", salida = "12:00 p.m.", llegada = "6:00 a.m.", operador = "QUINTANA", cooperativa = "Juan Bautista Quintana Solís", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Jueves", tiempo = "14:00", terminal = "Cotrán de la Costa, Mayoreo (Managua)", contacto = "5752-0817 / 8448-8955 / 8713-0083", precio = 600),
        BusRoute(origen = "Managua", destino = "Puerto Cabezas", fuente = "linea", salida = "6:00 p.m.", llegada = "8:00 a.m.", operador = "INGRAM", cooperativa = "Miguel Ángel Ingrand Gutiérrez", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Jueves", tiempo = "14:00", terminal = "Cotrán de la Costa, Mayoreo (Managua)", contacto = "8627-2624 / 8654-3048 / 5714-0172", precio = 600),
        BusRoute(origen = "Managua", destino = "Puerto Cabezas", fuente = "linea", salida = "8:00 p.m.", llegada = "10:00 a.m.", operador = "Chow Mendez", cooperativa = "Sheyla Osiris Diesen Chows", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Jueves", tiempo = "14:00", terminal = "Cotrán de la Costa, Mayoreo (Managua)", contacto = "5764-5439 / 5823-5066 / 8548-8821", precio = 600),
        // Viernes
        BusRoute(origen = "Managua", destino = "Puerto Cabezas", fuente = "linea", salida = "12:00 p.m.", llegada = "6:00 a.m.", operador = "Rapido y Furioso", cooperativa = "Fermín Leonardo Romero Olivero", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Viernes", tiempo = "14:00", terminal = "Cotrán de la Costa, Mayoreo (Managua)", contacto = "8362-1710 / 8650-0977 / 5761-9092 / 8401-4992 / 8725-3376", precio = 600),
        BusRoute(origen = "Managua", destino = "Puerto Cabezas", fuente = "linea", salida = "6:00 p.m.", llegada = "8:00 a.m.", operador = "Rapido y Furioso", cooperativa = "Fermín Leonardo Romero Olivero", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Viernes", tiempo = "14:00", terminal = "Cotrán de la Costa, Mayoreo (Managua)", contacto = "8362-1710 / 8650-0977 / 5761-9092 / 8401-4992 / 8725-3376", precio = 600),
        BusRoute(origen = "Managua", destino = "Puerto Cabezas", fuente = "linea", salida = "8:00 p.m.", llegada = "10:00 a.m.", operador = "MOZON", cooperativa = "Francisco Leonel Monzón Ruíz", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Viernes", tiempo = "14:00", terminal = "Cotrán de la Costa, Mayoreo (Managua)", contacto = "8713-0083 / 8821-8597 / 8352-5420 / 5712-9767", precio = 600),
        // Sabado
        BusRoute(origen = "Managua", destino = "Puerto Cabezas", fuente = "linea", salida = "12:00 p.m.", llegada = "6:00 a.m.", operador = "QUINTANA", cooperativa = "Juan Bautista Quintana Solís", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Sábado", tiempo = "14:00", terminal = "Cotrán de la Costa, Mayoreo (Managua)", contacto = "5752-0817 / 8448-8955 / 8713-0083", precio = 600),
        BusRoute(origen = "Managua", destino = "Puerto Cabezas", fuente = "linea", salida = "6:00 p.m.", llegada = "8:00 a.m.", operador = "ARAGON", cooperativa = "Eveling Eliette Aragón Robleto", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Sábado", tiempo = "14:00", terminal = "Cotrán de la Costa, Mayoreo (Managua)", contacto = "8854-6961 / 8537-7836 / 8362-1710", precio = 600),
        BusRoute(origen = "Managua", destino = "Puerto Cabezas", fuente = "linea", salida = "8:00 p.m.", llegada = "10:00 a.m.", operador = "MOZON", cooperativa = "Francisco Leonel Monzón Ruíz", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Sábado", tiempo = "14:00", terminal = "Cotrán de la Costa, Mayoreo (Managua)", contacto = "8713-0083 / 8821-8597 / 8352-5420 / 5712-9767", precio = 600),
        // Domingo
        BusRoute(origen = "Managua", destino = "Puerto Cabezas", fuente = "linea", salida = "12:00 p.m.", llegada = "6:00 a.m.", operador = "MONTOYA", cooperativa = "Geral Montoya", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Domingo", tiempo = "14:00", terminal = "Cotrán de la Costa, Mayoreo (Managua)", contacto = "8738-9444 / 8540-8310 / 8713-0083 / 8192-6081", precio = 600),
        BusRoute(origen = "Managua", destino = "Puerto Cabezas", fuente = "linea", salida = "6:00 p.m.", llegada = "8:00 a.m.", operador = "RAPIDO Y FURIOSO", cooperativa = "Fermín Romero Olivero", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Domingo", tiempo = "14:00", terminal = "Cotrán de la Costa, Mayoreo (Managua)", contacto = "8362-1710 / 8650-0977 / 5761-9092 / 8401-4992 / 8725-3376", precio = 600),
        BusRoute(origen = "Managua", destino = "Puerto Cabezas", fuente = "linea", salida = "8:00 p.m.", llegada = "10:00 a.m.", operador = "LILY MAE", cooperativa = "Lily mae Henríquez James", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Domingo", tiempo = "14:00", terminal = "Cotrán de la Costa, Mayoreo (Managua)", contacto = "8657-5581 / 5853-0723", precio = 600),

        // --- PUERTO CABEZAS -> MANAGUA (LINEA INTERDEPARTAMENTAL) ---
        // Lunes
        BusRoute(origen = "Puerto Cabezas", destino = "Managua", fuente = "linea", salida = "10:00 a.m.", llegada = "4:00 a.m.", operador = "RAPIDO Y FURIOSO #1", cooperativa = "Fermín Romero Olivero", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Lunes", tiempo = "14:00", terminal = "Parada de Buses de Bilwi, Pto. Cabezas", contacto = "8362-1710 / 8650-0977 / 5761-9092 / 8401-4992 / 8725-3376", precio = 600),
        BusRoute(origen = "Puerto Cabezas", destino = "Managua", fuente = "linea", salida = "1:00 p.m.", llegada = "7:00 a.m.", operador = "RAPIDO Y FURIOSO #2", cooperativa = "Fermín Romero Olivero", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Lunes", tiempo = "14:00", terminal = "Parada de Buses de Bilwi, Pto. Cabezas", contacto = "8362-1710 / 8650-0977 / 5761-9092 / 8401-4992 / 8725-3376", precio = 600),
        BusRoute(origen = "Puerto Cabezas", destino = "Managua", fuente = "linea", salida = "4:00 p.m.", llegada = "10:00 a.m.", operador = "Chow Mendez", cooperativa = "Sheyla Osiris Diesen Chows", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Lunes", tiempo = "14:00", terminal = "Parada de Buses de Bilwi, Pto. Cabezas", contacto = "5764-5439 / 5823-5066 / 8548-8821", precio = 600),
        // Martes
        BusRoute(origen = "Puerto Cabezas", destino = "Managua", fuente = "linea", salida = "10:00 a.m.", llegada = "4:00 a.m.", operador = "ARAGON", cooperativa = "Eveling Eliette Aragón Robleto", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Martes", tiempo = "14:00", terminal = "Parada de Buses de Bilwi, Pto. Cabezas", contacto = "8854-6961 / 8537-7836 / 8362-1710", precio = 600),
        BusRoute(origen = "Puerto Cabezas", destino = "Managua", fuente = "linea", salida = "1:00 p.m.", llegada = "7:00 a.m.", operador = "QUINTANA", cooperativa = "Juan Bautista Quintana Solís", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Martes", tiempo = "14:00", terminal = "Parada de Buses de Bilwi, Pto. Cabezas", contacto = "5752-0817 / 8448-8955 / 8713-0083", precio = 600),
        BusRoute(origen = "Puerto Cabezas", destino = "Managua", fuente = "linea", salida = "4:00 p.m.", llegada = "10:00 a.m.", operador = "MOZON", cooperativa = "Francisco Leonel Monzón Ruíz", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Martes", tiempo = "14:00", terminal = "Parada de Buses de Bilwi, Pto. Cabezas", contacto = "8713-0083 / 8821-8597 / 8352-5420 / 5712-9767", precio = 600),
        // Miercoles
        BusRoute(origen = "Puerto Cabezas", destino = "Managua", fuente = "linea", salida = "10:00 a.m.", llegada = "4:00 a.m.", operador = "RAPIDO Y FURIOSO #3", cooperativa = "Fermín Romero Olivero", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Miércoles", tiempo = "14:00", terminal = "Parada de Buses de Bilwi, Pto. Cabezas", contacto = "8362-1710 / 8650-0977 / 5761-9092 / 8401-4992 / 8725-3376", precio = 600),
        BusRoute(origen = "Puerto Cabezas", destino = "Managua", fuente = "linea", salida = "1:00 p.m.", llegada = "7:00 a.m.", operador = "MONTOYA", cooperativa = "Geral Montoya", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Miércoles", tiempo = "14:00", terminal = "Parada de Buses de Bilwi, Pto. Cabezas", contacto = "8738-9444 / 8540-8310 / 8713-0083 / 8192-6081", precio = 600),
        BusRoute(origen = "Puerto Cabezas", destino = "Managua", fuente = "linea", salida = "4:00 p.m.", llegada = "10:00 a.m.", operador = "MOZON", cooperativa = "Francisco Leonel Monzón Ruíz", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Miércoles", tiempo = "14:00", terminal = "Parada de Buses de Bilwi, Pto. Cabezas", contacto = "8713-0083 / 8821-8597 / 8352-5420 / 5712-9767", precio = 600),
        // Jueves
        BusRoute(origen = "Puerto Cabezas", destino = "Managua", fuente = "linea", salida = "10:00 a.m.", llegada = "4:00 a.m.", operador = "CENTENO", cooperativa = "Carlos Raúl Rojas Centeno", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Jueves", tiempo = "14:00", terminal = "Parada de Buses de Bilwi, Pto. Cabezas", contacto = "8632-7321 / 8875-7965", precio = 600),
        BusRoute(origen = "Puerto Cabezas", destino = "Managua", fuente = "linea", salida = "1:00 p.m.", llegada = "7:00 a.m.", operador = "MORENITO", cooperativa = "Iván Antonio Montano", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Jueves", tiempo = "14:00", terminal = "Parada de Buses de Bilwi, Pto. Cabezas", contacto = "8821-8636 / 8647-2735 / 8725-3376", precio = 600),
        BusRoute(origen = "Puerto Cabezas", destino = "Managua", fuente = "linea", salida = "4:00 p.m.", llegada = "10:00 a.m.", operador = "LILY MAE", cooperativa = "Lily mae Henríquez James", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Jueves", tiempo = "14:00", terminal = "Parada de Buses de Bilwi, Pto. Cabezas", contacto = "8657-5581 / 5853-0723", precio = 600),
        // Viernes
        BusRoute(origen = "Puerto Cabezas", destino = "Managua", fuente = "linea", salida = "10:00 a.m.", llegada = "4:00 a.m.", operador = "LA TANGA", cooperativa = "Marcelino Amador García", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Viernes", tiempo = "14:00", terminal = "Parada de Buses de Bilwi, Pto. Cabezas", contacto = "8362-1710 / 7511-5400 / 8431-3939", precio = 600),
        BusRoute(origen = "Puerto Cabezas", destino = "Managua", fuente = "linea", salida = "1:00 p.m.", llegada = "7:00 a.m.", operador = "LILY MAE", cooperativa = "Lily mae Henríquez James", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Viernes", tiempo = "14:00", terminal = "Parada de Buses de Bilwi, Pto. Cabezas", contacto = "8657-5581 / 5853-0723", precio = 600),
        BusRoute(origen = "Puerto Cabezas", destino = "Managua", fuente = "linea", salida = "4:00 p.m.", llegada = "10:00 a.m.", operador = "SANTA MONICA", cooperativa = "Freddy Man Cruz Lazo", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Viernes", tiempo = "14:00", terminal = "Parada de Buses de Bilwi, Pto. Cabezas", contacto = "8725-3376", precio = 600),
        // Sabado
        BusRoute(origen = "Puerto Cabezas", destino = "Managua", fuente = "linea", salida = "10:00 a.m.", llegada = "4:00 a.m.", operador = "CARVAJAL", cooperativa = "Audelia Del Rosario Acevedo Gutiérrez", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Sábado", tiempo = "14:00", terminal = "Parada de Buses de Bilwi, Pto. Cabezas", contacto = "8943-2782 / 5803-1483", precio = 600),
        BusRoute(origen = "Puerto Cabezas", destino = "Managua", fuente = "linea", salida = "1:00 p.m.", llegada = "7:00 a.m.", operador = "LILY MAE", cooperativa = "Lily mae Henríquez James", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Sábado", tiempo = "14:00", terminal = "Parada de Buses de Bilwi, Pto. Cabezas", contacto = "8657-5581 / 5853-0723", precio = 600),
        BusRoute(origen = "Puerto Cabezas", destino = "Managua", fuente = "linea", salida = "4:00 p.m.", llegada = "10:00 a.m.", operador = "SANTA MONICA", cooperativa = "Freddy Man Cruz Lazo", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Sábado", tiempo = "14:00", terminal = "Parada de Buses de Bilwi, Pto. Cabezas", contacto = "8725-3376", precio = 600),
        // Domingo
        BusRoute(origen = "Puerto Cabezas", destino = "Managua", fuente = "linea", salida = "10:00 a.m.", llegada = "4:00 a.m.", operador = "Ingram", cooperativa = "Miguel Ingram Guitierez", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Domingo", tiempo = "14:00", terminal = "Parada de Buses de Bilwi, Pto. Cabezas", contacto = "8627-2624 / 8654-3048 / 5714-0172", precio = 600),
        BusRoute(origen = "Puerto Cabezas", destino = "Managua", fuente = "linea", salida = "1:00 p.m.", llegada = "7:00 a.m.", operador = "Quintana", cooperativa = "Juan Bautista Quintana", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Domingo", tiempo = "14:00", terminal = "Parada de Buses de Bilwi, Pto. Cabezas", contacto = "5752-0817 / 8448-8955 / 8713-0083", precio = 600),
        BusRoute(origen = "Puerto Cabezas", destino = "Managua", fuente = "linea", salida = "4:00 p.m.", llegada = "10:00 a.m.", operador = "DUNKAN", cooperativa = "Betty Lan Ducan", tipo = "Autobús", capacidad = "60", placa = "", clasificacion = "Interdepartamental", modalidad = "Expreso", dias = "Domingo", tiempo = "14:00", terminal = "Parada de Buses de Bilwi, Pto. Cabezas", contacto = "8742-6965 / 8200-4997 / 7879-5366", precio = 600),

        BusRoute(origen = "Rosita", destino = "Bonanza", fuente = "formal", salida = "6:00 a.m.", llegada = "7:30 a.m.", operador = "Santos Andres Contreras Martinez", cooperativa = "Cotransmina R.L.", tipo = "Autobus", capacidad = "60", placa = "RN 190", clasificacion = "Alimentadora Principal", modalidad = "Ordinario", dias = "DIARIO", tiempo = "01:30", terminal = "Terminal Jesus Antonio Granado, Rosita", contacto = "", precio = 80),
        BusRoute(origen = "Rosita", destino = "Bonanza", fuente = "formal", salida = "9:30 a.m.", llegada = "11:00 a.m.", operador = "Yeral Antonio Jimenez", cooperativa = "Coosemtrabon", tipo = "Autobus", capacidad = "44", placa = "RN 202", clasificacion = "Alimentadora Principal", modalidad = "Ordinario", dias = "DIARIO", tiempo = "01:30", terminal = "Terminal Jesus Antonio Granado, Rosita", contacto = "", precio = 80),
        BusRoute(origen = "Bonanza", destino = "Rosita", fuente = "formal", salida = "6:00 a.m.", llegada = "7:30 a.m.", operador = "Yeral Antonio Jimenez", cooperativa = "Coosemtrabon", tipo = "Autobus", capacidad = "44", placa = "RN 202", clasificacion = "Alimentadora Principal", modalidad = "Ordinario", dias = "DIARIO", tiempo = "01:30", terminal = "Barrio 28 de Mayo, Bonanza", contacto = "", precio = 80),
        BusRoute(origen = "Bonanza", destino = "Rosita", fuente = "formal", salida = "11:00 a.m.", llegada = "12:30 p.m.", operador = "Leda Jarquin Manzanarez", cooperativa = "Cotransmina R.L.", tipo = "Autobus", capacidad = "69", placa = "RN 69", clasificacion = "Alimentadora Principal", modalidad = "Ordinario", dias = "DIARIO", tiempo = "01:30", terminal = "Barrio 28 de Mayo, Bonanza", contacto = "", precio = 80),

        BusRoute(origen = "Waspam", destino = "Puerto Cabezas", fuente = "formal", salida = "6:00 a.m.", llegada = "9:30 a.m.", operador = "Sidney Muller Barba", cooperativa = "Pinares", tipo = "Autobus", capacidad = "46", placa = "RN 73", clasificacion = "Alimentadora principal", modalidad = "ORDINARIO", dias = "Diario", tiempo = "03:30", terminal = "Terminal Bruno Gabriel, Waspam", contacto = "", precio = 200),
        BusRoute(origen = "Waspam", destino = "Puerto Cabezas", fuente = "formal", salida = "7:00 a.m.", llegada = "10:30 a.m.", operador = "Jerry Martinez Muller", cooperativa = "Cootrampic R,L.", tipo = "Autobus", capacidad = "60", placa = "RN 61", clasificacion = "Alimentadora principal", modalidad = "ORDINARIO", dias = "Diario", tiempo = "03:30", terminal = "Terminal Bruno Gabriel, Waspam", contacto = "", precio = 200),
        BusRoute(origen = "Puerto Cabezas", destino = "Waspam", fuente = "formal", salida = "6:00 a.m.", llegada = "9:30 a.m.", operador = "Sidney Muller Barba", cooperativa = "Pinares", tipo = "Autobus", capacidad = "46", placa = "RN 73", clasificacion = "Alimentadora principal", modalidad = "ORDINARIO", dias = "Diario", tiempo = "03:30", terminal = "BENIGNO MARTINEZ FEDRICK, Pto. Cabezas", contacto = "", precio = 200),

        BusRoute(origen = "Siuna", destino = "Río Blanco", fuente = "formal", salida = "4:20 a.m.", llegada = "8:00 a.m.", operador = "Francisco Javier Lopez Gonzalez", cooperativa = "Individual", tipo = "Autobus", capacidad = "66", placa = "MT 379", clasificacion = "Troncal Principal", modalidad = "Ordinario", dias = "Diario", tiempo = "03:40", terminal = "Alejandro Castellon, Siuna", contacto = "", precio = 180),
        BusRoute(origen = "Río Blanco", destino = "Siuna", fuente = "formal", salida = "6:15 a.m.", llegada = "9:55 a.m.", operador = "Roberto Lanzas Rivera", cooperativa = "El Porvenir", tipo = "Autobus", capacidad = "60", placa = "MT 150", clasificacion = "Troncal Principal", modalidad = "Ordinario", dias = "Diario", tiempo = "03:40", terminal = "Río Blanco Central", contacto = "", precio = 180),

        BusRoute(origen = "Siuna", destino = "Mulukukú", fuente = "formal", salida = "10:00 a.m.", llegada = "12:30 p.m.", operador = "Melvin Mejia Tinoco", cooperativa = "German Pomares", tipo = "Autobus", capacidad = "72", placa = "RN 174", clasificacion = "Troncal Principal", modalidad = "Ordinario", dias = "Diario", tiempo = "02:30", terminal = "Alejandro Castellon, Siuna", contacto = "", precio = 120),
        BusRoute(origen = "Mulukukú", destino = "Siuna", fuente = "formal", salida = "4:15 a.m.", llegada = "6:45 a.m.", operador = "Melvin Mejia Tinoco", cooperativa = "German Pomares", tipo = "Autobus", capacidad = "72", placa = "RN 174", clasificacion = "Troncal Principal", modalidad = "Ordinario", dias = "Diario", tiempo = "02:30", terminal = "Mulukukú Central", contacto = "", precio = 120),

        BusRoute(origen = "Siuna", destino = "Waslala", fuente = "formal", salida = "4:15 a.m.", llegada = "10:15 a.m.", operador = "Ingrid del Carmen Martinez", cooperativa = "Individual", tipo = "Autobus", capacidad = "65", placa = "MT 114", clasificacion = "Troncal Principal", modalidad = "Ordinario", dias = "Diario", tiempo = "06:00", terminal = "Alejandro Castellon, Siuna", contacto = "", precio = 220),
        BusRoute(origen = "Alamikamba", destino = "Rosita", fuente = "formal", salida = "8:00 a.m.", llegada = "11:30 a.m.", operador = "Daniel de Jesus Alaniz Ruiz", cooperativa = "Cootransmina R,L.", tipo = "Autobus", capacidad = "78", placa = "RN 192", clasificacion = "Alimentadora Secundaria", modalidad = "Ordinario", dias = "Diario", tiempo = "03:30", terminal = "Terminal El Muey, Alamikamba", contacto = "", precio = 150),
        BusRoute(origen = "Rosita", destino = "Alamikamba", fuente = "formal", salida = "8:00 a.m.", llegada = "11:30 a.m.", operador = "Marcelo Paiba Gonzalez", cooperativa = "Cootransmina R,L.", tipo = "Autobus", capacidad = "34", placa = "RN 22", clasificacion = "Alimentadora Secundaria", modalidad = "Ordinario", dias = "Diario", tiempo = "03:30", terminal = "Terminal Jesus Antonio Granado, Rosita", contacto = "", precio = 150),

        BusRoute(origen = "Puerto Cabezas", destino = "Wisconsin", fuente = "informal", salida = "11:00 a.m.", llegada = "5:00 p.m.", operador = "Ronaldo Castellano Ortiz", cooperativa = "Ruta Informal", tipo = "Camión", capacidad = "30", placa = "RN 5100", clasificacion = "Ruta informal", modalidad = "Viaje Especial", dias = "Miércoles", tiempo = "06:00", terminal = "Mercado Bilwi, Pto. Cabezas", contacto = "", precio = 250),
        BusRoute(origen = "Puerto Cabezas", destino = "Sahsa", fuente = "salida", salida = "1:30 p.m.", llegada = "4:30 p.m.", operador = "Salida Local Sahsa", cooperativa = "Cootransmina", tipo = "Autobús", capacidad = "50", placa = "RN 300", clasificacion = "Salida por destino", modalidad = "Ordinario", dias = "Diario", tiempo = "03:00", terminal = "Puerto Cabezas (Bilwi)", contacto = "", precio = 140),
        BusRoute(origen = "Puerto Cabezas", destino = "Yulu", fuente = "salida", salida = "2:00 p.m.", llegada = "4:00 p.m.", operador = "Salida Local Yulu", cooperativa = "Local", tipo = "Autobús", capacidad = "40", placa = "RN 310", clasificacion = "Salida por destino", modalidad = "Ordinario", dias = "Diario", tiempo = "02:00", terminal = "Puerto Cabezas (Bilwi)", contacto = "", precio = 100)
    )

    val INITIAL_TICKETS = listOf(
        Ticket(
            ownerId = "yader_demo",
            nombreContacto = "Yader Anderson",
            telefonoContacto = "505 8888-9999",
            origen = "Managua",
            destino = "Puerto Cabezas",
            fecha = "20/08/2026",
            hora = "8:00 a.m.",
            operador = "Transporte Chow Mendez",
            asiento = "12",
            precio = "C$ 500",
            nota = "Por motivos personales no podré viajar. Boleto físico. Interesados escribir.",
            estado = "disponible",
            trustLevel = "🟢 Verificado",
            providerPhotoUrl = "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=400",
            timestamp = System.currentTimeMillis() - (2 * 3600 * 1000L)
        ),
        Ticket(
            ownerId = "mariela_demo",
            nombreContacto = "Mariela Torres",
            telefonoContacto = "505 8765-4321",
            origen = "Managua",
            destino = "Bluefields",
            fecha = "21/08/2026",
            hora = "7:30 a.m.",
            operador = "Transporte Caribe Express",
            asiento = "07",
            precio = "C$ 450",
            nota = "Boleto extra por cambio de planes. Entrega en terminal.",
            estado = "disponible",
            trustLevel = "🟢 Verificado",
            providerPhotoUrl = "https://images.unsplash.com/photo-1544005313-94ddf0286df2?w=400",
            timestamp = System.currentTimeMillis() - (5 * 3600 * 1000L)
        ),
        Ticket(
            ownerId = "carlos_demo",
            nombreContacto = "Carlos Mejía",
            telefonoContacto = "505 8654-9080",
            origen = "Siuna",
            destino = "Managua",
            fecha = "22/08/2026",
            hora = "3:30 a.m.",
            operador = "Transporte Cotlantico",
            asiento = "15",
            precio = "C$ 400",
            nota = "Tengo que posponer el viaje por trabajo. Asiento ventana.",
            estado = "disponible",
            trustLevel = "🟢 Verificado",
            providerPhotoUrl = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=400",
            timestamp = System.currentTimeMillis() - (24 * 3600 * 1000L)
        )
    )

    val INITIAL_RIDES = listOf(
        Ride(
            ownerId = "carlos_demo",
            driverName = "Carlos M.",
            providerPhotoUrl = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=400",
            origen = "Managua",
            destino = "Puerto Cabezas",
            fecha = "15 ago",
            hora = "5:00 a.m.",
            vehiculo = "Toyota Corolla",
            color = "Gris",
            espaciosDisponibles = 3,
            precioPorPersona = "C$300",
            puntoEncuentro = "Metrocentro, Managua",
            infoAdicional = "Salgo puntual. Llevo maleta pequeña.",
            estado = "disponible"
        ),
        Ride(
            ownerId = "ana_demo",
            driverName = "Ana R.",
            providerPhotoUrl = "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?w=400",
            origen = "Managua",
            destino = "León",
            fecha = "16 ago",
            hora = "2:30 p.m.",
            vehiculo = "Hyundai Elantra",
            color = "Blanco",
            espaciosDisponibles = 2,
            precioPorPersona = "C$250",
            puntoEncuentro = "UCA, Managua",
            infoAdicional = "Viaje tranquilo, buena música.",
            estado = "disponible"
        ),
        Ride(
            ownerId = "luis_demo",
            driverName = "Luis A.",
            providerPhotoUrl = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=400",
            origen = "Rosita",
            destino = "Puerto Cabezas",
            fecha = "17 ago",
            hora = "8:00 a.m.",
            vehiculo = "Toyota Hilux",
            color = "Rojo",
            espaciosDisponibles = 2,
            precioPorPersona = "C$250",
            puntoEncuentro = "Gasolinera Rosita",
            infoAdicional = "Espacio para maletas grandes.",
            estado = "disponible"
        )
    )

    val INITIAL_STAYS = listOf(
        Stay(
            ownerId = "maria_demo",
            hostName = "María López",
            verificado = true,
            providerPhotoUrl = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=400",
            trustLevel = "🟢 Verificado",
            tipo = "Villa completa",
            nombre = "Villa de Lujo con Piscina",
            municipio = "Puerto Cabezas",
            direccion = "Zona Costera, Bilwi",
            precio = 350,
            per = "noche",
            huespedes = 4,
            habitaciones = 2,
            camas = 3,
            banos = 2,
            serviciosJson = "Wi-Fi de alta velocidad,Piscina infinita privada,Cocina totalmente equipada,Aire acondicionado (A/C),Estacionamiento gratuito,Se admiten mascotas",
            descripcion = "Escapa a esta magnífica villa de diseño contemporáneo, donde el confort se encuentra con la brisa del Caribe nicaragüense. Disfruta de espacios abiertos, luz natural abundante y una piscina infinita privada perfecta para descansar.\n\nIdeal para parejas o familias que buscan privacidad sin sacrificar comodidades de primer nivel. Ubicada a pocos minutos de las mejores zonas de Puerto Cabezas.",
            whatsapp = "505 8555-1234",
            telefono = "505 8555-1234",
            photosJson = "https://images.unsplash.com/photo-1580587771525-78b9dba3b914?w=800|https://images.unsplash.com/photo-1512917774080-9991f1c4c750?w=800|https://images.unsplash.com/photo-1613977257363-707ba9348227?w=800|https://images.unsplash.com/photo-1600596542815-ffad4c1539a9?w=800",
            rating = 5.0f,
            reviewsCount = 8,
            estado = "disponible",
            colorHue = 200,
            fotosCount = 4
        ),
        Stay(
            ownerId = "carlos_demo",
            hostName = "Carlos J.",
            verificado = true,
            providerPhotoUrl = "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=400",
            trustLevel = "🟢 Verificado",
            tipo = "Habitación frente al mar",
            nombre = "Casa Pelican — Hab. 2",
            municipio = "Puerto Cabezas",
            direccion = "Barrio El Muelle, frente a la playa",
            precio = 520,
            per = "noche",
            huespedes = 2,
            habitaciones = 1,
            camas = 1,
            banos = 1,
            serviciosJson = "Wi-Fi de alta velocidad,Cocina compartida,Aire acondicionado (A/C),Acceso a playa,Estacionamiento",
            descripcion = "Hermosa habitación frente al mar con vistas increíbles y acceso directo a la playa. Ambiente tranquilo, seguro y con brisa marina las 24 horas.",
            whatsapp = "505 8654-9080",
            telefono = "505 8654-9080",
            photosJson = "https://images.unsplash.com/photo-1590490360182-c33d57733427?w=800|https://images.unsplash.com/photo-1582719478250-c89cae4dc85b?w=800",
            rating = 4.8f,
            reviewsCount = 12,
            estado = "disponible",
            colorHue = 150,
            fotosCount = 2
        ),
        Stay(
            ownerId = "elena_demo",
            hostName = "Elena M.",
            verificado = true,
            providerPhotoUrl = "https://images.unsplash.com/photo-1544005313-94ddf0286df2?w=400",
            trustLevel = "🟢 Verificado",
            tipo = "Estudio completo",
            nombre = "Estudio Moderno Centro",
            municipio = "Siuna",
            direccion = "Frente al Parque Central, Siuna",
            precio = 280,
            per = "noche",
            huespedes = 2,
            habitaciones = 1,
            camas = 1,
            banos = 1,
            serviciosJson = "Wi-Fi de alta velocidad,Aire acondicionado (A/C),Cocina equipada,Se admiten mascotas,TV Smart",
            descripcion = "Acogedor estudio en el corazón de la ciudad de Siuna, ideal para viajes de negocios o turismo de conexión en la zona del Triángulo Minero.",
            whatsapp = "505 8700-0000",
            telefono = "505 8700-0000",
            photosJson = "https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?w=800|https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?w=800",
            rating = 4.9f,
            reviewsCount = 34,
            estado = "disponible",
            colorHue = 30,
            fotosCount = 3
        ),
        Stay(
            ownerId = "roberto_demo",
            hostName = "Roberto V.",
            verificado = true,
            providerPhotoUrl = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=400",
            trustLevel = "🟢 Verificado",
            tipo = "Cabaña campestre",
            nombre = "Cabaña de Montaña",
            municipio = "Rosita",
            direccion = "Sector Bambana, Rosita",
            precio = 1200,
            per = "noche",
            huespedes = 6,
            habitaciones = 3,
            camas = 4,
            banos = 2,
            serviciosJson = "Piscina al aire libre,Wi-Fi,Aire acondicionado (A/C),Estacionamiento gratuito,Área de barbacoa",
            descripcion = "Refugio rústico perfecto para desconectar, rodeado de naturaleza exuberante y aire puro en las inmediaciones de Rosita.",
            whatsapp = "505 8710-0000",
            telefono = "505 8710-0000",
            photosJson = "https://images.unsplash.com/photo-1518780664697-55e3ad937233?w=800|https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?w=800|https://images.unsplash.com/photo-1566073771259-6a8506099945?w=800",
            rating = 4.7f,
            reviewsCount = 21,
            estado = "disponible",
            colorHue = 280,
            fotosCount = 4
        )
    )

    val INITIAL_REVIEWS = listOf(
        com.example.data.model.Review(
            targetType = "RIDE",
            targetOwnerId = "driver_carlos",
            reviewerId = "rev_1",
            reviewerName = "Lucía Mendoza",
            reviewerPhotoUrl = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=400",
            rating = 5.0f,
            comment = "Excelente conductor, muy puntual y el vehículo impecable. Viaje súper cómodo desde Managua.",
            tags = "Puntual,Vehículo limpio,Manejo seguro,Excelente comunicación",
            timestamp = System.currentTimeMillis() - 86400000L * 2
        ),
        com.example.data.model.Review(
            targetType = "RIDE",
            targetOwnerId = "driver_erick",
            reviewerId = "rev_2",
            reviewerName = "Marcos Salgado",
            reviewerPhotoUrl = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=400",
            rating = 4.8f,
            comment = "Muy buen servicio en la Hilux, espacio suficiente para el equipaje y paradas convenientes.",
            tags = "Manejo seguro,Puntual,Recomendado",
            timestamp = System.currentTimeMillis() - 86400000L * 5
        ),
        com.example.data.model.Review(
            targetType = "STAY",
            targetOwnerId = "maria_demo",
            reviewerId = "rev_3",
            reviewerName = "Andrea Gómez",
            reviewerPhotoUrl = "https://images.unsplash.com/photo-1544005313-94ddf0286df2?w=400",
            rating = 5.0f,
            comment = "El hospedaje superó mis expectativas. Cerca de todo, agua fría y caliente, aire excelente.",
            tags = "Excelente ubicación,Hospitalidad,Limpio,Wi-Fi rápido",
            timestamp = System.currentTimeMillis() - 86400000L * 3
        )
    )
}
