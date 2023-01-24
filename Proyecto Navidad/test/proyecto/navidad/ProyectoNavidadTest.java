/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package proyecto.navidad;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author ausias
 */
public class ProyectoNavidadTest {

    public ProyectoNavidadTest() {
    }

    //Verifica si l'array de guanyadors s'omple correctament i no hi ha numeros repetits
    @Test
    public void testGanadores() {
        int[] result = ProyectoNavidad.ganadores();
        boolean unic = true;
        for (int i = 0; i < result.length; i++) {
            for (int j = i + 1; j < result.length; j++) {
                if (result[i] == result[j]) {
                    unic = false;
                }
            }
            assertEquals(13, result.length);
            assertTrue(unic);
        }
    }

    //Verifica si l'array de guanyadors de 1000 s'omple correctament i no hi ha numeros repetits
    @Test
    public void testGanadores1000() {
        int[] result = ProyectoNavidad.ganadores1000();
        boolean unic = true;
        for (int i = 0; i < result.length; i++) {
            for (int j = i + 1; j < result.length; j++) {
                if (result[i] == result[j]) {
                    unic = false;
                }
            }
            assertEquals(1794, result.length);
            assertTrue(unic);
        }
    }
}
