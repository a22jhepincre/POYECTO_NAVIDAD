package proyecto.navidad;

import java.io.*;
import java.util.Scanner;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProyectoNavidad {

    static final String RUTA = ("sorteos/");
    static final String ANSI_RED = "\033[0;31m";
    static final String ANSI_GREEN = "\u001B[32m";
    static final String ANSI_RESET = "\u001B[0m";
    static final String EXTENSION_TXT = ".txt";
    static final String EXTENSION_BIN = ".dat";
    static final int MAX_PREMIOSGORDOS = 13;
    static final int MAX_1000 = 1794;
    static final int PRIMER_PREMIO = 4000000;
    static final int SEGUNDO_PREMIO = 1250000;
    static final int TERCER_PREMIO = 500000;
    static final int CUARTO_PREMIO = 200000;
    static final int QUINTO_PREMIO = 60000;
    static final int PREMIO_1000 = 1000;
    static final int PREMIO_CENTENA = 1000;
    static final int PREMIO_DOSULTIMOS = 1000;
    static final int PREMIO_ULTIMONUM = 200;
    static final int PREMIO_APROXPRIMER = 20000;
    static final int PREMIO_APROXSEGUNDO = 12500;
    static final int PREMIO_APROXTERCERO = 9600;

    static Scanner s = new Scanner(System.in);
    static Random rnd = new Random();

    public static void main(String[] args) throws IOException {
        int[] premiosGordos = ganadores();
        int[] premios1000 = ganadores1000(premiosGordos);
        menu(premiosGordos, premios1000);
    }

    /**
     * MENU COMPUESTO DE TRES OPCIONES
     *
     * @param premiosGordos VARIABLE QUE ALMACENARA LOS 13 PREMIOS MAYORES
     * @param premios1000 VARIABLE QUE ALMACENARA LOS 1794 PREMIOS DE 1000
     */
    public static void menu(int[] premiosGordos, int[] premios1000) throws IOException {

        boolean salir = false;
        boolean sorteig = false;
        // MEDIANTE UN BUCLE "DO-WHILE" CONTROLAMOS LAS OPCIONES INVALIDAS
        do {

            System.out.println("1. Realizar sorteo.\n2. Comprobar mi numero.\n3. Salir\n");
            int opcion = escanearEntero("Selecciona una opcion: ");
            // UN "switch" PARA TRATAR LAS DIFERENTES OPCIONES
            switch (opcion) {
                case 1 -> {
                    int anyo = escanearEntero("Que año?");
                    if (!ExisteAnyo(String.valueOf(anyo))) {
                        sorteig(premiosGordos, premios1000);
                        String linea = formatearSorteo(premiosGordos);
                        linea += "\n" + formatearSorteo(premios1000);
                        escribirFicheroTexto(String.valueOf(anyo), "anyo");
                        escribirFicheroBinario(linea, String.valueOf(anyo));
                        sorteig = true;
                    } else {
                        System.out.println("El año ya existe brother");
                        LeerFicheroAnyo(String.valueOf(anyo),premiosGordos,premios1000);
                    }
                }
                case 2 -> {
                    if (!sorteig) {
                        System.out.println("El sorteo todavia no se ha realizado");
                    } else {

                    }
                }
                case 3 -> {
                    salir = true;
                }
                default -> {
                    System.out.println("Opcion invalida. Prueba de nuevo");
                }
            }
        } while (!salir);
    }

    /**
     * VALIDADOR DE ENTRADA DE UN ENTERO
     *
     * @param mtj MENSAJE QUE DEVOLVEMOS EN CASO DE NO INTRODUCIR UN ENTERO
     * (NUMERO)
     * @return num, DEVOLVEMOS EL NUMERO INTRODUCIDO
     */
    public static int escanearEntero(String mtj) {
        int num;
        System.out.print(mtj);
        while (!s.hasNextInt()) {
            s.next();
            System.out.println("Ha de ser enter.");
            System.out.print(mtj);
        }
        num = s.nextInt();
        return num;
    }

    /**
     * 13 PREMIOS GANADORES BUCLE "FOR" QUE LLENARA EL VECTOR "premiosGordos"
     * MEDIANTE UN RANDOM
     *
     * @return premiosGordos,devolvera los 13 numeros ganadores
     */
    public static int[] ganadores() {
        int[] premiosGordos = new int[MAX_PREMIOSGORDOS];

        for (int i = 0; i < MAX_PREMIOSGORDOS; i++) {
            premiosGordos[i] = rnd.nextInt(100000);
            for (int j = 0; j < i; j++) {
                while (premiosGordos[i] == premiosGordos[j]) {
                    premiosGordos[i] = rnd.nextInt(100000);
                }
            }
        }
        return premiosGordos;
    }

    /**
     * 1794 PREMIOS GANADORES BUCLE "FOR" QUE LLENARA EL VECTOR "premios1000"
     * MEDIANTE UN RANDOM
     *
     * @param premiosGordos PASAMOS POR PARAMETRO EL ARRAY DE PREMIOS GORDOS
     * PARA VERIFICAR QUE NO SE REPITAN ENTRE ELLOS
     * @return premios1000, devolvera los 1794 numeros ganadores
     */
    public static int[] ganadores1000(int[] premiosGordos) {
        int[] premios1000 = new int[MAX_1000];

        //BUCLE FOR PARA ASIGNAR LOS GANADORES DE 1000 EUROS Y VERIFICAR QUE NO SE REPITAN
        for (int i = 0; i < MAX_1000; i++) {
            premios1000[i] = rnd.nextInt(100000);
            for (int j = 0; j < i; j++) {
                while (premios1000[i] == premios1000[j]) {
                    premios1000[i] = rnd.nextInt(100000);
                }
            }
        }

        //BUCLES FOR PARA VERIFICAR QUE NO SE REPITAN LOS NUMEROS DE LOS PREMIOS GORDOS CON LOS GANADORES DE LOS PREMIOS DE 1000 EUROS
        for (int i = 0; i < MAX_1000; i++) {
            for (int j = 0; j < MAX_PREMIOSGORDOS; j++) {
                if (premiosGordos[j] == premios1000[i]) {
                    premios1000[i] = rnd.nextInt(100000);
                }
            }
        }
        return premios1000;
    }

    /**
     * SORTEO DE LOS 5 PRIMEROS PREMIOS MAYORES MEDIANTE UN BUCLE "FOR"
     * MOSTRAREMOS LOS NUMEROS GANADORES CON SUS PREMIOS
     *
     * @param premiosGordos RECIBE EL VECTOR LLENO DE LOS "premiosGordos"
     * @param premios1000 RECIBE EL VECTOR LLENO DE LOS "premios100"
     */
    public static void sorteig(int[] premiosGordos, int[] premios1000) {
        // VECTOR QUE CONTENDRA 8 NUMEROS GANADORES DEL QUINTO PREMIO
        int[] quintoPremio = new int[8];
        int aux = 0;

        for (int i = 0; i < MAX_PREMIOSGORDOS; i++) {
            if (i == 0) {
                System.out.println(ANSI_GREEN + "Premio Gordo: 4.000.000 --> " + String.format("%05d", premiosGordos[i]) + ANSI_RESET);
            }
            if (i == 1) {
                System.out.println(ANSI_GREEN + "Segundo premio: 1.250.000 --> " + String.format("%05d", premiosGordos[i]) + ANSI_RESET);
            }
            if (i == 2) {
                System.out.println(ANSI_GREEN + "Tercer Premio: 500.000 --> " + String.format("%05d", premiosGordos[i]) + ANSI_RESET);
            }
            if (i == 3) {
                // VECTOR QUE CONTENDRA LOS DOS NUMEROS GANADORES DEL CUARTO PREMIO
                int[] cuartoPremio = new int[2];

                cuartoPremio[0] = premiosGordos[i];
                cuartoPremio[1] = premiosGordos[i + 1];
                System.out.println(ANSI_GREEN + "Cuarto premio : 200.000 --> " + String.format("%05d", cuartoPremio[0]) + ", " + String.format("%05d", cuartoPremio[1]) + ANSI_RESET);
            }
            if (i > 4) {
                {
                    quintoPremio[aux] = premiosGordos[i];
                    aux++;
                }

            }
        }
        System.out.print(ANSI_GREEN + "Quinto premio : 60.000 --> " + ANSI_RESET);
        for (int k = 0; k < quintoPremio.length - 1; k++) {
            System.out.print(ANSI_GREEN + String.format("%05d", quintoPremio[k]) + ", " + ANSI_RESET);
        }
        System.out.println(ANSI_GREEN + String.format("%05d", quintoPremio[quintoPremio.length - 1]) + ANSI_RESET);
        System.out.print("\n");
    }

    /**
     * FUNCION QUE COMPRUEBA SI HAS GANADO ALGUN PREMIO GORDO
     *
     * @param premiosGordos PASAMOS EL ARRAY QUE CONTENGAN LOS GANADORES DE
     * PREMIOS GORDOS
     * @param numero PASAMOS EL NUMERO QUE QUEREMOS COMPROBAR
     * @return DEVOLVEREMOS LA CANTIDAD QUE HAYA GANADO O 0 EN CASO DE QUE NO
     * HAYA GANADO NADA
     */
    public static int comprobarPremioGordo(int[] premiosGordos, int numero) {
        int cantidad = 0;

        for (int i = 0; i < premiosGordos.length; i++) {
            if (numero == premiosGordos[i]) {
                if (i == 0) {
                    cantidad += PRIMER_PREMIO;
                }
                if (i == 1) {
                    cantidad += SEGUNDO_PREMIO;
                }
                if (i == 2) {
                    cantidad += TERCER_PREMIO;
                }
                if (i == 3 || i == 4) {
                    cantidad += CUARTO_PREMIO;
                }
                if (i > 4) {
                    cantidad += QUINTO_PREMIO;
                }
            }

        }

        return cantidad;
    }

    //COMPROBAR GANADOR DE 1000 1794 GANADORES
    /**
     * FUNCION QUE COMPRUEBA SI ERES GANADOR DE 1000 EUROS (1794 GANADORES)
     *
     * @param premios1000 PASAMOS POR PARAMETRO EL ARRAY QUE CONTENGA LOS
     * GANADORES DE 1000 EUROS
     * @param numero PASAMOS EL NUMERO QUE QUEREMOS COMPROBAR
     * @return DEVOLVEREMOS LA CANTIDAD QUE HAYA GANADO O 0 EN CASO DE QUE NO
     * HAYA GANADO NADA
     */
    public static int comprobarPremioMil(int[] premios1000, int numero) {
        int cantidad = 0;
        for (int i = 0; i < MAX_1000; i++) {
            if (numero == premios1000[i]) {
                cantidad += PREMIO_1000;
            }
        }

        return cantidad;
    }

    /**
     * FUNCION QUE COMPRUEBA SI ERES GANADOR DE ALGUNA APROXIMACION, ES DECIR,
     * SI TIENES EL SIGUIENTE O EL ANTERIOR DE ALGUN PREMIO GORDO
     *
     * @param premiosGordos PASAMOS EL ARRAY QUE CONTENGAN LOS GANADORES DE
     * PREMIOS GORDOS
     * @param numero PASAMOS EL NUMERO QUE QUEREMOS COMPROBAR
     * @return DEVOLVEREMOS LA CANTIDAD QUE HAYA GANADO O 0 EN CASO DE QUE NO
     * HAYA GANADO NADA
     */
    public static int comprobarAproximaciones(int[] premiosGordos, int numero) {
        int cantidad = 0;

        //COMPROBAR LAS APROXIMACIONES DEL 1R, 2N Y 3R PREMIO GORDO
        if (premiosGordos[0] + 1 == numero || premiosGordos[0] - 1 == numero) {
            cantidad += PREMIO_APROXPRIMER;

        }
        if (premiosGordos[1] + 1 == numero || premiosGordos[1] - 1 == numero) {
            cantidad += PREMIO_APROXSEGUNDO;

        }
        if (premiosGordos[2] + 1 == numero || premiosGordos[2] - 1 == numero) {
            cantidad += PREMIO_APROXTERCERO;

        }

        return cantidad;
    }

    //COMPROBAR LAS CENTENAS DEL 1R, 2N, 3R, Y 4R PREMIO
    /**
     * FUNCION QUE COMPRUEBA SI ERES GANADOR DE CENTENA, ES DECIR, SI TIENES AL
     * CENTENA(3 PRIMEROS DIGITOS) DE ALGUN PREMIO GORDO
     *
     * @param premiosGordos PASAMOS EL ARRAY QUE CONTENGAN LOS GANADORES DE
     * PREMIOS GORDOS
     * @param numero PASAMOS EL NUMERO QUE QUEREMOS COMPROBAR
     * @return DEVOLVEREMOS LA CANTIDAD QUE HAYA GANADO O 0 EN CASO DE QUE NO
     * HAYA GANADO NADA
     */
    public static int comprobarCentena(int[] premiosGordos, int numero) {
        int cantidad = 0;

        if (premiosGordos[0] / 100 == numero / 100) {
            cantidad += PREMIO_CENTENA;

        } else if (numero == premiosGordos[0]) {
            cantidad += 0;
        }

        if (premiosGordos[1] / 100 == numero / 100) {
            cantidad += PREMIO_CENTENA;

        } else if (numero == premiosGordos[1]) {
            cantidad += 0;
        }

        if (premiosGordos[2] / 100 == numero / 100) {
            cantidad += PREMIO_CENTENA;

        } else if (numero == premiosGordos[2]) {
            cantidad += 0;
        }

        if (premiosGordos[3] / 100 == numero / 100) {
            cantidad += PREMIO_CENTENA;

        } else if (numero == premiosGordos[3]) {
            cantidad += 0;
        }

        if (premiosGordos[4] / 100 == numero / 100) {
            cantidad += PREMIO_CENTENA;

        } else if (numero == premiosGordos[4]) {
            cantidad += 0;
        }

        return cantidad;
    }

    //COMPROBAR LOS DOS ULTIMOS NUMEROS DEL 1R, 2N i 3R PREMIO
    /**
     * FUNCION QUE COMPRUEBA SI ERES GANADOR DE LOS 2 ULTIMOS DIGITOS, ES DECIR,
     * SI TIENES LOS 2 ULTIMOS DIGITOS DE ALGUN PREMIO GORDO
     *
     * @param premiosGordos PASAMOS EL ARRAY QUE CONTENGAN LOS GANADORES DE
     * PREMIOS GORDOS
     * @param numero PASAMOS EL NUMERO QUE QUEREMOS COMPROBAR
     * @return DEVOLVEREMOS LA CANTIDAD QUE HAYA GANADO O 0 EN CASO DE QUE NO
     * HAYA GANADO NADA
     */
    public static int comprobarDosUltimos(int[] premiosGordos, int numero) {
        int cantidad = 0;

        if (premiosGordos[0] % 100 == numero % 100) {
            cantidad += PREMIO_DOSULTIMOS;

        } else if (numero == premiosGordos[0]) {
            cantidad += 0;
        }
        if (premiosGordos[1] % 100 == numero % 100) {
            cantidad += PREMIO_DOSULTIMOS;

        } else if (numero == premiosGordos[1]) {
            cantidad += 0;

        }
        if (premiosGordos[2] % 100 == numero % 100) {
            cantidad += PREMIO_DOSULTIMOS;

        } else if (numero == premiosGordos[2]) {
            cantidad += 0;

        }

        return cantidad;
    }

    /**
     * FUNCION QUE COMPRUEBA SI ERES GANADOR DEL ULTIMO DIGITO, ES DECIR, SI
     * TIENES EL ULTIMO DIGITO DEL 1R PREMIO GORDO
     *
     * @param premiosGordos PASAMOS EL ARRAY QUE CONTENGAN LOS GANADORES DE
     * PREMIOS GORDOS
     * @param numero PASAMOS EL NUMERO QUE QUEREMOS COMPROBAR
     * @return DEVOLVEREMOS LA CANTIDAD QUE HAYA GANADO O 0 EN CASO DE QUE NO
     * HAYA GANADO NADA
     */
    public static int comprobarUltimo(int[] premiosGordos, int numero) {
        int cantidad = 0;
        //COMPROBAMOS SI EL ULTIMO NUMERO ES IGUAL DEL PRIMER PREMIO
        if ((premiosGordos[0] % 10 == numero % 10)) {
            cantidad += PREMIO_ULTIMONUM;
        } else if (numero == premiosGordos[0]) {
            cantidad += 0;

        }

        return cantidad;
    }

    public static class Persona {

        String nombre;
        int numero;
        int dinero;
        String grupo;
    }

    public static Persona pedirPersona() {
        Persona p = new Persona();
        System.out.print("Nombre: ");
        p.nombre = s.next();
        p.numero = escanearEntero("Escribe tu numero de loteria: ");
        boolean salir = false;
        do {
            p.dinero = escanearEntero("Escribe la cantidad de dinero que quieres aportar [5-60]: ");
            if (p.dinero >= 5 && p.dinero <= 60 && p.dinero % 5 == 0) {
                salir = true;
            } else {
                System.out.println("¡Tiene que estar entre 5-60 y ser multiplo de 5!");
            }
        } while (!salir);

        return p;
    }

    public static void comprobarNumero(int[] premiosGordos, int[] premios1000, Persona[][] p) {
        int cantidad = 0;
        int fila = escanearEntero("Que colla quieres comprobar?");
        for (int i = 0; i < p[fila].length; i++) {
            cantidad += comprobarPremioGordo(premiosGordos, p[fila][i].numero);

            if (cantidad == 0) {
                cantidad += comprobarAproximaciones(premiosGordos, p[fila][i].numero);
                if (cantidad == 0) {
                    cantidad += comprobarCentena(premiosGordos, p[fila][i].numero);
                    if (cantidad == 0) {
                        cantidad += comprobarDosUltimos(premiosGordos, p[fila][i].numero);
                        if (cantidad == 0) {
                            cantidad += comprobarUltimo(premiosGordos, p[fila][i].numero);
                        }
                    }
                }

                cantidad += comprobarPremioMil(premios1000, p[fila][i].numero);

            }
        }

        String result = formatearGrupo(p);
        //escribirFicheroTexto(result);
        System.out.println(result);
    }

    public static void comprobarNumeroSolitario(int[] premiosGordos, int[] premios1000) {
        int numPersonas = 1;
        Persona p = pedirPersona();

        int cantidad = 0;

        for (int i = 0; i < numPersonas; i++) {
            cantidad += comprobarPremioGordo(premiosGordos, p.numero);

            if (cantidad == 0) {
                cantidad += comprobarAproximaciones(premiosGordos, p.numero);
                if (cantidad == 0) {
                    cantidad += comprobarCentena(premiosGordos, p.numero);
                    if (cantidad == 0) {
                        cantidad += comprobarDosUltimos(premiosGordos, p.numero);
                        if (cantidad == 0) {
                            cantidad += comprobarUltimo(premiosGordos, p.numero);
                        }
                    }
                }

                cantidad += comprobarPremioMil(premios1000, p.numero);

            }
        }

    }

    public static void escribirFicheroTexto(String linea, String nombreFichero) {
        FileWriter fw = null;
        try {
            File f = new File(RUTA + nombreFichero + EXTENSION_TXT);
            fw = new FileWriter(f, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(linea);
            pw.flush();
            pw.close();
        } catch (IOException ex) {
            Logger.getLogger(ProyectoNavidad.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(ProyectoNavidad.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void escribirFicheroBinario(String linea, String nombreFichero) {
        FileOutputStream fos = null;
        try {
            File f = new File(RUTA + nombreFichero + EXTENSION_BIN);
            fos = new FileOutputStream(f, true);
            DataOutputStream dos = new DataOutputStream(fos);
            dos.writeUTF(linea);
            dos.flush();
            dos.close();
        } catch (IOException ex) {
            Logger.getLogger(ProyectoNavidad.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(ProyectoNavidad.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void menuComprobarNumero(int[] premiosGordos, int[] premios1000) {
        boolean salir = false;
        Persona[][] matriuColla = crearMatriuColla();
        do {
            System.out.println("¿Cómo juegas?");
            System.out.println("1. Solitario\n2. Colla\n");
            int opcion = escanearEntero("Elige una opción: ");
            switch (opcion) {
                case 1 ->
                    comprobarNumeroSolitario(premiosGordos, premios1000);
                case 2 ->
                    comprobarNumero(premiosGordos, premios1000, matriuColla);
                default ->
                    System.out.println("¡Escoge una de las opciones!");
            }

        } while (!salir);
    }

    public static String formatearGrupo(Persona[][] p) {
        String result = "";
        for (int i = 0; i < p.length; i++) {
            for (int j = 0; j < p[0].length; j++) {
                if (p[i][j] != null) {
                    result += p[i][j].nombre + " " + p[i][j].numero + " " + p[i][j].dinero + "\n";
                }
            }
            result += "\n";
        }
        return result;
    }

    public static String formatearSorteo(int[] enteros) {
        String result = "";
        for (int i = 0; i < enteros.length; i++) {
            result += enteros[i] + " ";
        }

        return result;
    }

    public static Persona[][] añadirPersonaAColla(Persona[][] colla, int fila) {
        Persona p = pedirPersona();
        Persona[][] collaNueva = new Persona[colla.length][colla[0].length + 1];

        for (int i = 0; i < colla.length; i++) {
            for (int j = 0; j < colla[0].length; j++) {
                collaNueva[i][j] = colla[i][j];
            }
        }

        collaNueva[fila][colla[fila].length] = p;

        return collaNueva;
    }

    public static Persona[][] añadirColla(Persona[][] colla) {
        Persona[][] matriuCollaNueva = new Persona[colla.length + 1][colla[0].length];

        for (int i = 0; i < colla.length; i++) {
            for (int j = 0; j < colla[0].length; j++) {
                matriuCollaNueva[i][j] = colla[i][j];
            }
        }

        return matriuCollaNueva;
    }

    public static Persona[][] crearMatriuColla() {

        int personas = escanearEntero("Cuantas personas sois en tu colla?");
        Persona[][] matrizCollas = new Persona[1][personas];

        for (int i = 0; i < matrizCollas.length; i++) {
            for (int j = 0; j < matrizCollas[0].length; j++) {
                Persona p = pedirPersona();
                matrizCollas[i][j] = p;
            }
        }

        return matrizCollas;
    }

    public static boolean ExisteAnyo(String anyo) throws FileNotFoundException, IOException {
        boolean result = false;
        File f = new File(RUTA + "anyo" + EXTENSION_TXT);
        String linea;
        FileReader reader = new FileReader(f);
        BufferedReader buffer = new BufferedReader(reader);
        linea = buffer.readLine();
        while (linea != null) {
            if (linea.equals(anyo)) {
                result = true;
            }
            linea = buffer.readLine();
        }
        return result;
    }
    
    public static void LeerFicheroAnyo(String anyo, int[] premiosGordos, int[] premios1000) throws FileNotFoundException, IOException {
        File f = new File(RUTA + anyo + EXTENSION_TXT);
        String linea;
        FileReader reader = new FileReader(f);
        BufferedReader buffer = new BufferedReader(reader);
        linea = buffer.readLine();
        int aux = 0;
        for (int i = 0; i < linea.length(); i++) {
            String numero = "";
            if(linea.substring(i).equals(" ")){
                premiosGordos[aux] = Integer.parseInt(numero);
                aux++;
                numero = "";
            } else {
                numero += linea.substring(i);
            }
            
            
        }
        while (linea != null) {

            linea = buffer.readLine();
        }

    } 
}
