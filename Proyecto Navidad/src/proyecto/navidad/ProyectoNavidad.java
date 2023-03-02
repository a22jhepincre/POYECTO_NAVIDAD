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
        eleccionIdioma();
        menu(premiosGordos, premios1000);
    }
    /**
     * ELECCIÓN DE IDIOMA
     *
     * ESTA FUNCIÓN NOS MUESTRA TRES IDIOMAS A ELEGIR DEPENDIENTO CUAL ELIJA EL PROGRAMA SE MOSTRARA EN EL IDIOMA INDICADO 
     * POR EL USUARIO
     */
    public static String eleccionIdioma() {
        Scanner scanner = new Scanner(System.in);
        String idioma;
        boolean seleccionado = false;

        do {
            System.out.println("1. Español");
            System.out.println("2. Català");
            System.out.println("3. English");

            idioma = scanner.nextLine();

            switch(idioma) {
                case "Español" -> {
                    System.out.println("Ha seleccionado Español.\n");
                    seleccionado = true;
                }
                case "Català" -> {
                    System.out.println("Ha seleccionat Català.\n");
                    seleccionado = true;
                }
                case "English" -> {
                    System.out.println("You have selected English.\n");
                    seleccionado = true;
                }
                default -> System.out.println("Selección incorrecta, por favor intente de nuevo.\nSelecció incorrecta, per favor intente de nou.\nWrong selection, please try again.");
            }
        } while (!seleccionado);
        return idioma;
    }
    /**
     * MENU COMPUESTO DE TRES OPCIONES
     *
     * @param premiosGordos VARIABLE QUE ALMACENARA LOS 13 PREMIOS MAYORES
     * @param premios1000 VARIABLE QUE ALMACENARA LOS 1794 PREMIOS DE 1000
     */
    public static void menu(int[] premiosGordos, int[] premios1000) throws IOException {
        boolean salir = false;
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
                        escribirFicheroTexto(linea, String.valueOf(anyo));
                    } else {
                        System.out.println("El año ya existe brother");
                        actualizarPremiosGordos(String.valueOf(anyo), premiosGordos);
                        actualizarPremios1000(String.valueOf(anyo), premios1000);
                    }
                }
                case 2 -> {
                    if (anyosArchivados()) {
                        System.out.println("No hay sorteos archivados.");
                    } else {
                        Persona[][] matriuCollas = null;
                        String[] nombreCollas = null;
                        menuCollasSolitario(matriuCollas, nombreCollas, premiosGordos, premios1000);
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
    }
    /**
     * DATOS DE LAS PESONAS
     *
     * PROCEDIMIENTO QUE SOLICITA LOS DATOS DE LAS PERSONAS Y COMPRUEBA SI LOS DATOS INTRODUCIDOS SON CORRECTOS
     * @return, DEVEULEVE EL OBJETO PERSONA
     */
    public static Persona pedirPersona() {
        Persona p = new Persona();
        boolean salir = false;
        do {
            System.out.print("Nombre: ");
            p.nombre = s.next();
            if (p.nombre.length() < 15) {
                salir = true;
                for (int i = 15 - p.nombre.length(); i > 0; i--) {
                    p.nombre += " ";
                }
            } else {
                System.out.println("Tiene que tener menos de quince caracteres.");
            }
        } while (!salir);
        p.numero = escanearEntero("Escribe tu numero de loteria: ");
        salir = false;
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
    /**
     * COMPRUEBA EL NUMERO INTRODUCIDO
     *
     * SI EL NUMERO INGRESADO COINCIDE CON ALGUN NUMERO GAANDOR DEL PREMIO
     * SE ACTULIZA LA VARIABLE "cantidad" CON EL VALOR CORRESPONDIENTE
     * @param premiosGordos
     * @param premios1000
     */
    public static void comprobarNumero(int[] premiosGordos, int[] premios1000) {
        int cantidad = 0;
        int numero = escanearEntero("Introduce tu numero para comprobarlo: ");

        cantidad += comprobarPremioGordo(premiosGordos, numero);
        if (cantidad == 0) {
            cantidad += comprobarAproximaciones(premiosGordos, numero);
            if (cantidad == 0) {
                cantidad += comprobarDosUltimos(premiosGordos, numero);
                if (cantidad == 0) {
                    cantidad += comprobarUltimo(premiosGordos, numero);
                    if (cantidad == 0) {
                        cantidad += comprobarCentena(premiosGordos, numero);
                    }
                }
            }
        }
        cantidad += comprobarPremioMil(premios1000, numero);

        if (cantidad == 0) {
            System.out.println(ANSI_RED + "No has ganado nada" + ANSI_RESET);
        } else {
            System.out.println(ANSI_GREEN + "Cantidad: " + cantidad + ANSI_RESET);
        }
    }
    /**
     * COMPROBAR NUMERO DE LAS COLLAS
     *
     * LA FUNCIÓN RECCORE LA MATRIZ DE PERSONAS DE LA C0LLA Y POR CADA PERSONA COMPUEBA
     * SI HA GANDO ALGÚN PERMIO
     * @param premiosGordos, RECIBE PARAMETROS DE PREMIOS GORDOS PARA COMPROBAR
     * @param premios1000, RECIBE PARAMETROS DE PREMIOS 1000 PARA COMPROBAR
     * @param colla, RECIBE LAS COLLAS
     * @param matriuCollas, UNA MATRIZ DE PERSONAS QUE REPRESENTA A LOS MIEMBROS DE LA COLLA
     * @param anyoComprobacion, RECIBE EL AÑO DE REALIZACIÓN DEL SORTEO
     */
    public static void comprobarNumeroColla(int[] premiosGordos, int[] premios1000, int colla, Persona[][] matriuCollas, int anyoComprobacion) {
        int cantidad = 0;

        for (int i = 0; i < matriuCollas[colla - 1].length; i++) {
            cantidad += comprobarPremioGordo(premiosGordos, matriuCollas[colla - 1][i].numero);
            if (cantidad == 0) {
                cantidad += comprobarAproximaciones(premiosGordos, matriuCollas[colla - 1][i].numero);
                if (cantidad == 0) {
                    cantidad += comprobarDosUltimos(premiosGordos, matriuCollas[colla - 1][i].numero);
                    if (cantidad == 0) {
                        cantidad += comprobarUltimo(premiosGordos, matriuCollas[colla - 1][i].numero);
                        if (cantidad == 0) {
                            cantidad += comprobarCentena(premiosGordos, matriuCollas[colla - 1][i].numero);
                        }
                    }
                }
            }
            cantidad += comprobarPremioMil(premios1000, matriuCollas[colla - 1][i].numero);

        }

        if (cantidad == 0) {
            System.out.println(ANSI_RED + "No has ganado nada" + ANSI_RESET);
            /*SE MUESTRA UNA TABLA CON LA INFORMACIÓN DE LOS MIEMBROS DE LA COLLA Y LOS PREMIOS, 
          ADEMAS DEL AÑO, EL NUMERO DE CADA MIEMBRO Y EL DINERO APORTADO*/
        } else {
            System.out.println("| ANY | MEMBRES | DINERS | PREMI |");
            int dineroTot = 0;
            for (int i = 0; i < matriuCollas[colla - 1].length; i++) {
                dineroTot += matriuCollas[colla - 1][i].dinero;
            }
            System.out.println("| " + anyoComprobacion + " | " + matriuCollas[colla - 1].length + " | " + dineroTot + " | " + cantidad + " |");
            System.out.println("| NOMBRE | NUMERO | DINERO | PREMI |");
            for (int i = 0; i < matriuCollas[colla - 1].length; i++) {
                System.out.println("| " + matriuCollas[colla - 1][i].nombre + " | " + matriuCollas[colla - 1][i].numero + " | " + matriuCollas[colla - 1][i].dinero + " | " + ((cantidad / 100) * matriuCollas[colla - 1][i].dinero));
            }
        }
    }
    /**
     * INTRODUCIR DATOS EN UN FICHERO TXT
     *
     * FUNCIÓN QUE SE ENCARGA DE ESCIRBIR UNA LINEA EN UN FICHERO DE TEXTO ESPECIFICANDO LA RUTA, EL NOMBRE Y LA EXTENSIÓN
     * @param linea,RECIBE UNA CADENA DE TEXTO A ESCRIBIR
     * @param nombreFichero, NOMBRE DEL FICHERO EN EL QUE SE DEBE ESCRIBIR LA LINEA
     */
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
            Logger.getLogger(ProyectoNavidad.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();

            } catch (IOException ex) {
                Logger.getLogger(ProyectoNavidad.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /**
     * INTRODUCIR DATOS EN UN FICHERO BINARIO
     *
     * FUNCIÓN QUE SE ENCARGA DE ESCIRBIR UNA LINEA EN UN FICHERO BINARIO ESPECIFICANDO LA RUTA, EL NOMBRE Y LA EXTENSIÓN
     * @param linea,RECIBE UNA CADENA DE TEXTO A ESCRIBIR
     * @param nombreFichero, NOMBRE DEL FICHERO EN EL QUE SE DEBE ESCRIBIR LA LINEA
     */
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
            Logger.getLogger(ProyectoNavidad.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();

            } catch (IOException ex) {
                Logger.getLogger(ProyectoNavidad.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /**
     * INTRODUCIR OBJETOS PERSONA EN UN FICHERO BINARIO
     *
     * FUNCIÓN QUE SE ENCARGA DE ESCRIBIR UNA MATRIZ DE OBJETOS DE LA CLASE PERSONA EN UN FICHERO BINARIO
     * @param matriuCollas, RECIBE LA MATRIZ PARA INTRODUCIR LOS DATOS
     */
    public static void escribirCollasBinario(Persona[][] matriuCollas) {
        FileOutputStream fos = null;
        try {
            File f = new File(RUTA + "collas" + EXTENSION_BIN);
            //SE CREA EL FICHERO POR SI NO EXISTE
            if (!f.exists()) {
                f.createNewFile();
            }
            fos = new FileOutputStream(f, false);
            DataOutputStream dos = new DataOutputStream(fos);
            //SI EL OBJETO DE LA POSICION DE LA MATRIZ NO ES NULL, SE ESCRIBEN LOS DATOS DE LAS PERSONAS
            for (int i = 0; i < matriuCollas.length; i++) {
                for (int j = 0; j < matriuCollas[0].length; j++) {
                    if (matriuCollas[i][j] != null) {
                        dos.writeUTF(matriuCollas[i][j].nombre);
                        dos.writeInt(matriuCollas[i][j].numero);
                        dos.writeInt(matriuCollas[i][j].dinero);
                    }

                }
                    // SE REALIZA UN SALTO DE LINEA PARA SEPARLAS EN EL FICHERO
                    dos.writeUTF("\n");
                }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProyectoNavidad.class.getName()).log(Level.SEVERE, null, ex);
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
    /**
     * FORMATEAR GRUPOS
     *
     * FUNCION QUE SE ENCARGA DE FORMATEAR LA MATRIZ PARA MOSTRAR DE UNA MANERA LEGIBLE EN FORMA DE CADENA DE TEXTO
     * @param p, RECIBE LA MATRIZ DE LAS PERSONAS
     * @return
     */
    public static String formatearGrupo(Persona[][] p) {
        String result = "";
        for (int i = 0; i < p.length; i++) {
            for (int j = 0; j < p[0].length; j++) {
                if (p[i][j] != null) {
                    result += p[i][j].nombre + " " + p[i][j].numero + " " + p[i][j].dinero + "\n";
                }
            }
            //AÑADE UN SALTO DE LINEA PARA SEPARA LOS GRUPOS EN LA CADENA RESULTANTE
            result += "\n";
        }
        return result;
    }
    /**
     * FORMATEAR SORTEO
     *
     * ESTA FUNCIÓN FORMATEA UN VECTOR DE ENTEROS EN UNA CADENA DE TEXTO PARA SU POSTERIOR VISUALIZACIÓN
     * @param enteros, RECIBE UN VECTOR DE ENTEROS
     * @return DEVUELVE UNA CADENA FORMATEADA
     */
    public static String formatearSorteo(int[] enteros) {
        String result = "";
        for (int i = 0; i < enteros.length; i++) {
            result += enteros[i] + " ";
        }

        return result;
    }
    /**
     * COMPROBAR EL FICHERO DE LAS COLLAS 
     *
     * LA FUNCIÓN TIENE COMO OBJETIVO COMPROBAR SI EXISTE O NO UN FICHERO BINARIO DE NOMBRE "COLLAS" EN LA RUTA ESPECIFICADA
     * @return vacio, DEVULEVE UN VALOR QUE INDICA SI EL ARCHIVO ESTA VACÍO O NO
     * @throws IOException
     */
    public static boolean comprobarFicheroColla() throws IOException {
        File f = new File(RUTA + "collas" + EXTENSION_BIN);
        boolean vacio = false;
        if (!f.exists() || f.length() == 0) {
            f.createNewFile();
            vacio = true;
        }
        return vacio;
    }
    /**
     * CONTADOR DE NOMBRES
     *
     * ESTA FUNCIÓN CUENTA LA CANTIDAD DE NOMBRES QUE HAY EN EL ARCHIVO BINARIO LLAMADO "COLLA2"
     * @return resultado, DEVUELVE LA DIVICIÓN QUE ES LA CANTIDAD DE NOMBRES EN EL ARCHIVO
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static int ContadorNombres () throws FileNotFoundException, IOException{
        RandomAccessFile raf = new RandomAccessFile(RUTA + "colla2"+ EXTENSION_BIN,"rw");
        /*DIVIDIMOS LA LONGITUD POR 12, YA QUE CADA REGISTRO EN EL ARCHIVO OCUPA 12 BYTES 
        (4 bytes para el nombre y 2 bytes para cada uno de los enteros)*/
        int resultado= (int) raf.length()/12;
        return resultado;
    }
    /**
     * MENU JUGADOR SOLITARIO O EN UNA COLLA
     *
     * MOSTRAMOS TRES OPCIONES A ELEGIR DE MANERA SOLITARIA, EN COLLA O SI DESEA SALIR DEL JUEGO
     *
     * @param matriuCollas, RECIBE LA MATRIZ DE LA COLLAS
     * @param nombreCollas, RECIBE EL NOMBRE DE LAS COLLAS PARA LA COMPROBACIÓN
     * @param premiosGordos, RECIBE EL VECTOS DE PREMIOSGORDOS PARA COMPROBAR LOS NUMEROS GANADORES
     * @param premios1000,RECIBE EL VECTOS DE PREMIOS1000 PARA COMPROBAR LOS NUMEROS GANADORES
     * @throws IOException
     */
    public static void menuCollasSolitario(Persona[][] matriuCollas, String[] nombreCollas, int[] premiosGordos, int[] premios1000) throws IOException {

        boolean salir = false;
        while (!salir) {
            System.out.print("\nMenu:\n1. Solitario\n2. Colla\n3. Salir\n");
            int opcion = escanearEntero("Elige una opcion: ");
            switch (opcion) {
                /*SI ELIGE ESTA OPCIÓN, SE PIDE EL AÑO DEL SORTEO Y SE COMPRUEBA SI EXISTE 
                SI ES ASI, SE ACTUALIZAN LOS PRERMIOS Y SE COMPRUEBA SI EL NUMERO COINCIDE CON ALGUN PREMIO*/
                case 1 -> {
                    int anyo = escanearEntero("Dime que anyo de loteria quieres: ");
                    if (ExisteAnyo(String.valueOf(anyo))) {
                        actualizarPremiosGordos(String.valueOf(anyo), premiosGordos);
                        actualizarPremios1000(String.valueOf(anyo), premios1000);
                        comprobarNumero(premiosGordos, premios1000);
                    } else {
                        System.out.println("Anyo no existe tt");
                    }
                }
                /*SI ELIGE ESTA OPCIÓN, SE COMPRUEBA SI HAY UN FICHERO DE COLLAS EXISTENTE, SI NO EXISTE SE PIDE EL NOMBRE DE LA COLLA
                Y SE CREA UN NUEVO FICHERO DE COLLAS CON UNA MATRIZ VACIA SI EXISTE SE ACTUALIZA LA MATRIZ CON LOS DATOS EXISTENTES
                A CONTINUACIÓN MUESTRA EL MENU DE LAS COLLAS QUE PERMITE REALIZAR VARIAS OPERACIONES COMO AÑADIR O ELIMINAR MIEMBRO DE LA COLLA 
                ADEMAS DE COMPROBAR SI LA COLLA HA GANDO ALGUN PREMIO*/
                case 2 -> {
                    if (comprobarFicheroColla()) {
                        nombreCollas = new String[1];
                        nombreCollas[0] = pedirNombre();
                        matriuCollas = crearMatriuColla();
                        escribirCollasBinario(matriuCollas);
                        escribirNombresFichero(nombreCollas);
                    } else {
                        matriuCollas = actualizarMatriuCollas(contadFilas());
                        nombreCollas = nCollas(ContadorNombres());
                    }
                    menuCollas(matriuCollas, nombreCollas, premiosGordos, premios1000);
                }
                case 3 ->
                    salir = true;
                default ->
                    System.out.println("Escoge una opcion");
            }
        }
    }
    /**
     * ESCRIBIR LOS NOMBRES EN EL FICHERO
     *
     * ESTA FUNCIÓN SE ENCARGA DE ESCRIBUR LOS NOMBRES EN UN ARCHIVO BINARIOS
     * @param nCollas, RECIBE LOS NOMBRES DE LAS COLLAS
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void escribirNombresFichero(String [] nCollas) throws FileNotFoundException, IOException{

        File f = new File(RUTA + "colla2" + EXTENSION_BIN);
        if(!f.exists()){
            f.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(f);
        DataOutputStream dos = new DataOutputStream(fos);
        for (int i = 0; i < nCollas.length; i++) {
            dos.writeUTF(nCollas[i]);

        }

    }
    /**
     * MENU DE COLLAS
     *
     * ESTA FUNCIÓN NOS MUESTRA LAS DIFERENTES OPCIONES PARA LAS COLLAS
     * @param matriuCollas, RECIBE LA MATRIZ DE LA COLLAS
     * @param nombreCollas, RECIBE EL NOMBRE DE LAS COLLAS PARA LA COMPROBACIÓN
     * @param premiosGordos, RECIBE EL VECTOS DE PREMIOSGORDOS PARA COMPROBAR LOS NUMEROS GANADORES
     * @param premios1000,RECIBE EL VECTOS DE PREMIOS1000 PARA COMPROBAR LOS NUMEROS GANADORES
     * @throws IOException
     */
    public static void menuCollas(Persona[][] matriuCollas, String[] nombreCollas, int[] premiosGordos, int[] premios1000) throws IOException {

        boolean salir = false;
        while (!salir) {
            System.out.print("\nMenu collas:\n1. Crear colla\n2. Añadir persona a colla\n3. Comprobar numeros colla\n4. Printear collas\n5. Salir\n");
            int opcion = escanearEntero("Elige una opcion: ");
            switch (opcion) {
                /*ESTA OPCIÓN PERMITE AL USUARIO CREAR UNA COLLA NUEVA, AÑADIENDO UNA MATRIZ NUEVA
                Y UN NOMBRE DE LA COLLA AL ARCHIVO BINARIO*/
                case 1 -> {
                    matriuCollas = añadirColla(matriuCollas);
                    nombreCollas = añadirNombreCollas(nombreCollas);
                    escribirCollasBinario(matriuCollas);
                    escribirNombresFichero(nombreCollas);
                }
                /*ESTA OPCIÓN PERMITE AL USUARIO AÑADOR UNA NUEVA PERSONA A UNA COLLA EXISTENTE,
                SELECCIONANDO LA COLLA POR SU NOMBRE*/
                case 2 -> {
                    matriuCollas = añadirPersonaAColla(matriuCollas, menuCollasNoms(nombreCollas) - 1);
                    escribirCollasBinario(matriuCollas);
                }
                /*ESTA OPCIÓN PERMITE AL USUARIO COMPROBAR SI LA COLLA SELECCIONADA HA GANADO ALGÚN PREMIO EN UN AÑO DETERMINADO*/
                case 3 -> {
                    anyosExistentes();
                    int anyo = escanearEntero("Dime que anyo de loteria quieres: ");
                    if (ExisteAnyo(String.valueOf(anyo))) {
                        actualizarPremiosGordos(String.valueOf(anyo), premiosGordos);
                        actualizarPremios1000(String.valueOf(anyo), premios1000);
                        comprobarNumeroColla(premiosGordos, premios1000, menuCollasNoms(nombreCollas), matriuCollas, anyo);
                    } else {
                        System.out.println("Anyo no existe tt");
                    }
                }
                /*ESTA OPCIÓN PERMITE AL USUARIO IMPRIMIR TODAS LAS COLLAS EXISTENTES EN EL ARCHIVO BINARIOS*/
                case 4 ->
                    System.out.println(formatearGrupo(matriuCollas));
                /*ESTA OPCIÓN PERMITE AL USUARIO SALIR DEL MENU*/
                case 5 ->
                    salir = true;
                default ->
                    System.out.println("Escoge una opcion");
            }
        }
    }
    /**
     * PEDIR NOMBRE
     *
     * FUNCIÓN QUE PIDE EL NOMBRE DE LA COLLA
     * @return nombreColla, DEVUELVE EL NOMBRE INTRODUCIDO
     */
    public static String pedirNombre() {
        String nombreColla;
        boolean salir = false;

        do {
            System.out.print("Nombre colla: ");
            nombreColla = s.next();

            if (nombreColla.length() < 10) {
                salir = true;
                for (int i = 10 - nombreColla.length(); i > 0; i--) {
                    nombreColla += " ";
                }
            } else {
                System.out.println("Debe de ser de menos de diez caraceteres.");
            }
        } while (!salir);

        return nombreColla;
    }
    /**
     *
     * @param numNombres
     * @return
     * @throws IOException
     */
    public static String[] nCollas(int numNombres) throws IOException {
        String[] nombres = new String[numNombres];
        try {

            RandomAccessFile raf = new RandomAccessFile(RUTA + "colla2" + EXTENSION_BIN, "r");
            int aux = 0;
            for (int pos = 0; pos < raf.length(); pos += 12) {
                raf.seek(pos);
                String nombre = raf.readUTF();
                nombres[aux] = nombre;
                aux++;
            }
            for (int i = 0; i < nombres.length; i++) {
                System.out.println(nombres[i]);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProyectoNavidad.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nombres;

    }

    public static String[] añadirNombreCollas(String[] nCollas) {
        String[] nuevoNomCollas = new String[nCollas.length + 1];

        for (int i = 0; i < nCollas.length; i++) {
            nuevoNomCollas[i] = nCollas[i];
        }
        nuevoNomCollas[nCollas.length] = pedirNombre();

        return nuevoNomCollas;
    }

    public static int menuCollasNoms(String[] nombreCollas) {
        for (int i = 0; i < nombreCollas.length; i++) {
            System.out.println((i + 1) + ". " + nombreCollas[i]);
        }
        int opcion = escanearEntero("Elige una opcion: ");
        return opcion;
    }

    public static Persona[][] añadirPersonaAColla(Persona[][] colla, int fila) {
        Persona p = pedirPersona();
        Persona[][] collaNueva;
        boolean salir = false;
        boolean encontrado = false;
        int columna = 0;
        int col = 0;
        while (!salir) {
            if (colla[fila][col] == null) {
                columna = col;
                encontrado = true;
                salir = true;
            } else {
                col++;
            }

            if (col > colla[fila].length - 1) {
                salir = true;
            }
        }

        if (!encontrado) {
            collaNueva = new Persona[colla.length][colla[fila].length + 1];

            for (int i = 0; i < colla.length; i++) {
                for (int j = 0; j < colla[0].length; j++) {
                    collaNueva[i][j] = colla[i][j];
                }
            }

            collaNueva[fila][colla[fila].length] = p;
        } else {
            collaNueva = colla;
            collaNueva[fila][columna] = p;
        }

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

    public static void actualizarPremiosGordos(String anyo, int[] premiosGordos) throws IOException {
        FileReader reader = null;
        try {
            File f = new File(RUTA + anyo + EXTENSION_TXT);
            reader = new FileReader(f);
            BufferedReader buffer = new BufferedReader(reader);
            String linea = buffer.readLine();
            String numero = "";
            int aux = 0;
            for (int i = 0; i < linea.length(); i++) {
                if (linea.charAt(i) != ' ') {
                    numero += linea.charAt(i);
                } else {
                    premiosGordos[aux] = Integer.parseInt(numero);
                    aux++;
                    numero = "";

                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProyectoNavidad.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();

            } catch (IOException ex) {
                Logger.getLogger(ProyectoNavidad.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static void actualizarPremios1000(String anyo, int[] premios1000) {
        FileReader reader = null;
        try {
            File f = new File(RUTA + anyo + EXTENSION_TXT);
            reader = new FileReader(f);
            BufferedReader buffer = new BufferedReader(reader);
            String linea = "";
            try {
                linea = buffer.readLine();

            } catch (IOException ex) {
                Logger.getLogger(ProyectoNavidad.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

            while (linea != null) {
                try {
                    linea = buffer.readLine();

                } catch (IOException ex) {
                    Logger.getLogger(ProyectoNavidad.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                String numero = "";
                int aux = 0;
                if (linea != null) {
                    for (int i = 0; i < linea.length(); i++) {
                        if (linea.charAt(i) != ' ') {
                            numero += linea.charAt(i);
                        } else {
                            premios1000[aux] = Integer.parseInt(numero);
                            aux++;
                            numero = "";

                        }
                    }
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProyectoNavidad.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();

            } catch (IOException ex) {
                Logger.getLogger(ProyectoNavidad.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void anyosExistentes() {
        FileReader reader = null;
        try {
            File f = new File(RUTA + "anyo" + EXTENSION_TXT);
            String linea;
            reader = new FileReader(f);
            BufferedReader buffer = new BufferedReader(reader);
            linea = buffer.readLine();
            System.out.println("Anyos: ");
            while (linea != null) {
                System.out.println(linea);
                linea = buffer.readLine();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProyectoNavidad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProyectoNavidad.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(ProyectoNavidad.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static boolean anyosArchivados() {
        FileReader reader = null;
        boolean vacio = false;
        try {

            File f = new File(RUTA + "anyo" + EXTENSION_TXT);
            String linea;
            reader = new FileReader(f);
            BufferedReader buffer = new BufferedReader(reader);
            linea = buffer.readLine();
            if (linea == null) {
                vacio = true;
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProyectoNavidad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProyectoNavidad.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(ProyectoNavidad.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return vacio;
    }

    public static int contadFilas() {
        int filas = 0;
        try {
            int pos = 0;
            boolean salir = false;
            RandomAccessFile raf = new RandomAccessFile(RUTA + "collas" + EXTENSION_BIN, "r");

            while (!salir) {
                raf.seek(pos);
                if (raf.readUTF().equals("\n")) {
                    filas++;
                }

                pos += 25;

                if (pos > raf.length()) {
                    salir = true;
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProyectoNavidad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProyectoNavidad.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filas;
    }

    public static Persona[][] añadirPersonaFAColla(Persona[][] colla, int fila, Persona p) {
        Persona[][] collaNueva;
        boolean salir = false;
        boolean encontrado = false;
        int columna = 0;
        int col = 0;
        while (!salir) {
            if (colla[fila][col] == null) {
                columna = col;
                encontrado = true;
                salir = true;
            } else {
                col++;
            }

            if (col > colla[fila].length - 1) {
                salir = true;
            }
        }

        if (!encontrado) {
            collaNueva = new Persona[colla.length][colla[fila].length + 1];

            for (int i = 0; i < colla.length; i++) {
                for (int j = 0; j < colla[0].length; j++) {
                    collaNueva[i][j] = colla[i][j];
                }
            }

            collaNueva[fila][colla[fila].length] = p;
        } else {
            collaNueva = colla;
            collaNueva[fila][columna] = p;
        }

        return collaNueva;
    }

    public static Persona[][] actualizarMatriuCollas(int filas) {
        Persona[][] matriuCollas = new Persona[filas + 1][1];
        try {
            RandomAccessFile raf = new RandomAccessFile(RUTA + "collas" + EXTENSION_BIN, "r");
            int fila = 0;
            int pos = 0;
            boolean salir = false;

            raf.seek(pos);
            while (!salir) {
                boolean salto = false;
                Persona p = new Persona();
                String nom = raf.readUTF();
                if (!nom.equals("\n")) {
                    int numero = raf.readInt();
                    int dinero = raf.readInt();
                    p.nombre = nom;
                    p.numero = numero;
                    p.dinero = dinero;

                    matriuCollas = añadirPersonaFAColla(matriuCollas, fila, p);

                } else {
                    pos += 3;
                    fila++;
                    salto = true;
                }
                if (!salto) {
                    pos += 25;
                }

                if (pos > raf.length() - 25) {
                    salir = true;
                }

                raf.seek(pos);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProyectoNavidad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProyectoNavidad.class.getName()).log(Level.SEVERE, null, ex);
        }

        return matriuCollas;
    }

}
