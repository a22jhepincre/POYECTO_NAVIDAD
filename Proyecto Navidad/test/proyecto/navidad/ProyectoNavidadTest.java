package proyecto.navidad;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Empresaurios
 */
public class ProyectoNavidadTest {
    
    public ProyectoNavidadTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("Inicio del test de ProyectoNavidad");
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("Final del test de ProyectoNavidad");
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Test of ganadores method, of class ProyectoNavidad.
     */
    @Test
    public void testGanadores() {
        System.out.println("Probando que no se repitan los ganadores...");
        int[] result = ProyectoNavidad.ganadores();
        final int NUMEROS_GANADORES = 13;
        boolean unic = true;
        for (int i = 0; i < result.length; i++) {
            for (int j = i + 1; j < result.length; j++) {
                if (result[i] == result[j]) {
                    unic = false;
                }
            }
            assertEquals(NUMEROS_GANADORES, result.length);
            assertTrue(unic);
        }
    }

    /**
     * Test of ganadores1000 method, of class ProyectoNavidad.
     */
     @Test
    public void testGanadores1000() {
        System.out.println("Probando que no se repitan los ganadores de 1000 euros...");
        int[] premiosGordos = ProyectoNavidad.ganadores();
        int[] result = ProyectoNavidad.ganadores1000(premiosGordos);
        boolean unic = true;
        final int NUMEROS_GANADORES_1000 = 1794;
        for (int i = 0; i < result.length; i++) {
            for (int j = i + 1; j < result.length; j++) {
                if (result[i] == result[j]) {
                    unic = false;
                }
            }
            assertEquals(NUMEROS_GANADORES_1000, result.length);
            assertTrue(unic);
        }
    }

    /**
     * Test of sorteig method, of class ProyectoNavidad.
     */
    @Test
    public void testSorteig() {
        System.out.println("Probando que no se repitan los ganadores del sorteo...");
        int[] premiosGordos = ProyectoNavidad.ganadores();
        int[] premios1000 = ProyectoNavidad.ganadores1000(premiosGordos);
        boolean iguales = false;
        for (int i = 0; i < premiosGordos.length; i++) {
            for (int j = 0; j < premios1000.length; j++) {
                if(premiosGordos[i]==premios1000[j]){
                    iguales = true;
                }
            }
            assertFalse(iguales);
        }
        
        
    }

    /**
     * Test of comprobarNumero method, of class ProyectoNavidad.
     */
    
    @Test
    public void testComprobarNumero1000() {
        System.out.println("Probando que los premios con 1000 euros den el dinero correspondiente...");             
        int cantidad=0;
        final int PREMIO_1000 = 1000;
        int[] premiosGordos = ProyectoNavidad.ganadores();
        int[] premios1000 = ProyectoNavidad.ganadores1000(premiosGordos);
        int numero = premios1000[0];
        for (int i = 0; i < premios1000.length; i++) {
            if(numero==premios1000[i]){
                cantidad+=PREMIO_1000;
            }
        }
        assertEquals(PREMIO_1000, cantidad);
        
    }
    @Test
    public void testComprobarNumeroGordo() {
        System.out.println("Probando que el premio gordo reparta el dinero correspondiente...");             
        int cantidad=0;
        final int PREMIO_GORDO = 4000000;
        int[] premiosGordos = ProyectoNavidad.ganadores();
        int numero = premiosGordos[0];
        for (int i = 0; i < premiosGordos.length; i++) {
            if(numero==premiosGordos[i]){
                cantidad+=PREMIO_GORDO;
            }
        }
        assertEquals(PREMIO_GORDO, cantidad);
        
    }
    @Test
    public void testComprobarNumeroSegundoPremio() {
        System.out.println("Probando que el segundo premio reparta el dinero correspondiente....");             
        int cantidad=0;
        final int PREMIO_SEGUNDO = 1250000;
        int[] premiosGordos = ProyectoNavidad.ganadores();
        int numero = premiosGordos[1];
        for (int i = 0; i < premiosGordos.length; i++) {
            if(numero==premiosGordos[i]){
                cantidad+=PREMIO_SEGUNDO;
            }
        }
        assertEquals(PREMIO_SEGUNDO, cantidad);
        
    }
    @Test
    public void testComprobarNumeroTercerPremio() {
        System.out.println("Probando que el tercer premio reparta el dinero correspondiente....");             
        int cantidad=0;
        final int PREMIO_TERCERO = 500000;
        int[] premiosGordos = ProyectoNavidad.ganadores();
        int numero = premiosGordos[2];
        for (int i = 0; i < premiosGordos.length; i++) {
            if(numero==premiosGordos[i]){
                cantidad+=PREMIO_TERCERO;
            }
        }
        assertEquals(PREMIO_TERCERO, cantidad);
        
    }
    @Test
    public void testComprobarNumeroCuartoPremio() {
        System.out.println("Probando que los cuartos premios repartan el dinero correspondiente....");             
        int cantidad=0;
        final int PREMIO_CUARTO = 200000;
        int[] premiosGordos = ProyectoNavidad.ganadores(); 
        for (int i = 0; i < premiosGordos.length; i++) {
            if(premiosGordos[3]==premiosGordos[i] ||premiosGordos[4]==premiosGordos[i] ){
                cantidad+=PREMIO_CUARTO;
            }
        }
        cantidad/=2;//Como la suma de los 2 cuartos premios son 400000, entonces se divide entre 2 y cada uno tiene 200000
        assertEquals(PREMIO_CUARTO, cantidad);
        
    }
    @Test
    public void testComprobarNumeroQuintoPremio() {
        System.out.println("Probando que los quintos premios repartan el dinero correspondiente....");             
        int cantidad=0;
        final int PREMIO_QUINTO = 60000;
        int[] premiosGordos = ProyectoNavidad.ganadores();
        for (int i = 0; i < premiosGordos.length; i++) {
            if(i>4){
                cantidad+=PREMIO_QUINTO;
            }
        }
        cantidad/=8;//Como la suma de los 8 quintos premios son 480000, entonces se divide entre 8 y cada uno tiene 60000
        assertEquals(PREMIO_QUINTO, cantidad);
        
    }
    
}
