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

    @Test
    public void testGanadores() {
        int[] result = ProyectoNavidad.ganadores();
        assertEquals(13, result.length);
        assertNotEquals(result[0], result[1]);
    }

}
