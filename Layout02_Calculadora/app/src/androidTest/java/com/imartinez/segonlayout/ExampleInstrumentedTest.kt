package com.imartinez.segonlayout

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Test d'exemple que s'executa en un dispositiu o emulador Android i comprova el nom del paquet
 *
 * Més informació a la [documentació de tests](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Agafem el context de l'app que estem provant
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.imartinez.segonlayout", appContext.packageName)
    }
}