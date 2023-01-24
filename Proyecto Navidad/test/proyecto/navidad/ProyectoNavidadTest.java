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
        assertEquals(13, result.length);
        assertNotEquals(result[0], result[1]);
    }
    
    //Verifica si l'array de guanyadors de 1000 s'omple correctament i no hi ha numeros repetits
    @Test
    public void testGanadores1000() {
        int[] result = ProyectoNavidad.ganadores1000();
        assertEquals(1794, result.length);
        assertNotEquals(result[0], result[1]);
    }

}
