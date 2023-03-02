package proyecto.navidad;

import java.io.*;
import java.util.Scanner;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProyectoNavidad {

    static final String RUTA = "sorteos/";
    static final String RUTA_IDIOMAS = "traducciones/";
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
        menu(premiosGordos, premios1000, eleccionIdioma());
    }

    public static void crearDirectorio() {
        File directorio = new File("sorteos");
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio creado");
            } else {
                System.out.println("Error al crear directorio");
            }
        }
    }

    /**
     * ELECCIÓN DE IDIOMA
     *
     * ESTA FUNCIÓN NOS MUESTRA TRES IDIOMAS A ELEGIR DEPENDIENTO CUAL ELIJA EL
     * PROGRAMA SE MOSTRARA EN EL IDIOMA INDICADO POR EL USUARIO
     *
     * @return Idioma, DEVUELVE EL IDIOMA EN EL CUAL SE ESCRIBIRAN LOS TEXTOS.
     * @throws java.io.IOException
     */
    public static String eleccionIdioma() throws IOException {
        int opcion;
        boolean seleccionado = false;
        String idioma = "";

        do {
            System.out.println("1. Spanish");
            System.out.println("2. Catalan");
            System.out.println("3. English");
            
            opcion = escanearEntero("Selecciona tu idioma: ", "english");

            switch (opcion) {
                case 1 -> {
                    System.out.println("Ha seleccionado Español.\n");
                    seleccionado = true;
                    idioma = "spanish";
                }
                case 2 -> {
                    System.out.println("Ha seleccionat Català.\n");
                    seleccionado = true;
                    idioma = "catalan";
                }
                case 3 -> {
                    System.out.println("You have selected English.\n");
                    seleccionado = true;
                    idioma = "english";
                }
                default ->
                    System.out.println("Selección incorrecta, por favor intente de nuevo.\nSelecció incorrecta, per favor intente de nou.\nWrong selection, please try again.");
            }
        } while (!seleccionado);
        return idioma;
    }

    /**
     * MENU COMPUESTO DE TRES OPCIONES
     *
     * @param premiosGordos VARIABLE QUE ALMACENARA LOS 13 PREMIOS MAYORES
     * @param premios1000 VARIABLE QUE ALMACENARA LOS 1794 PREMIOS DE 1000
     * @param idioma
     * @throws java.io.IOException
     */
    public static void menu(int[] premiosGordos, int[] premios1000, String idioma) throws IOException {
        crearDirectorio();
        boolean salir = false;
        // MEDIANTE UN BUCLE "DO-WHILE" CONTROLAMOS LAS OPCIONES INVALIDAS
        do {
            print(idioma, "1. Realizar Sorteo.");
            System.out.println();
            print(idioma, "2. Comprobar mi numero.");
            System.out.println();
            print(idioma, "3. Salir.");
            System.out.println();

            int opcion = escanearEntero("Selecciona una opcion: ", idioma);
            // UN "switch" PARA TRATAR LAS DIFERENTES OPCIONES
            switch (opcion) {
                case 1 -> {
                    int anyo = escanearEntero("Que año? ", idioma);
                    if (!ExisteAnyo(String.valueOf(anyo))) {
                        sorteig(premiosGordos, premios1000, idioma);
                        String linea = formatearSorteo(premiosGordos);
                        linea += "\n" + formatearSorteo(premios1000);
                        escribirFicheroTexto(String.valueOf(anyo), "anyo");
                        escribirFicheroBinario(linea, String.valueOf(anyo));
                        escribirFicheroTexto(linea, String.valueOf(anyo));
                    } else {
                        print(idioma, "El año ya existe brother");
                        System.out.println();
                        actualizarPremiosGordos(String.valueOf(anyo), premiosGordos);
                        actualizarPremios1000(String.valueOf(anyo), premios1000);
                    }
                }
                case 2 -> {
                    if (verificarFicheroAnyos()) {
                        print(idioma, "No hay sorteos archivados.");
                    } else {
                        Persona[][] matriuCollas = null;
                        String[] nombreCollas = null;
                        menuCollasSolitario(matriuCollas, nombreCollas, premiosGordos, premios1000, idioma);
                    }
                }
                case 3 -> {
                    salir = true;
                }
                default -> {
                    print(idioma, "Opcion invalida. Prueba de nuevo");
                }
            }
        } while (!salir);
    }

    /**
     * VALIDADOR DE ENTRADA DE UN ENTERO
     *
     * @param mtj MENSAJE QUE DEVOLVEMOS EN CASO DE NO INTRODUCIR UN ENTERO
     * (NUMERO)
     * @param idioma
     * @return num, DEVOLVEMOS EL NUMERO INTRODUCIDO
     * @throws java.io.IOException
     */
    public static int escanearEntero(String mtj, String idioma) throws IOException {
        int num;
        print(idioma, mtj);
        while (!s.hasNextInt()) {
            s.next();
            print(idioma, "Tiene que ser entero.");System.out.println();
            print(idioma, mtj);
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
     * @param idioma
     * @throws java.io.IOException
     */
    public static void sorteig(int[] premiosGordos, int[] premios1000, String idioma) throws IOException {
        // VECTOR QUE CONTENDRA 8 NUMEROS GANADORES DEL QUINTO PREMIO
        int[] quintoPremio = new int[8];
        int aux = 0;

        for (int i = 0; i < MAX_PREMIOSGORDOS; i++) {
            if (i == 0) {
                System.out.print(ANSI_GREEN);
                print(idioma, "Premio Gordo: 4.000.000 --> ");
                System.out.println(" " + String.format("%05d", premiosGordos[i]) + ANSI_RESET);
            }
            if (i == 1) {
                System.out.print(ANSI_GREEN);
                print(idioma, "Segundo premio: 1.250.000 --> ");
                System.out.println(" " + String.format("%05d", premiosGordos[i]) + ANSI_RESET);
            }
            if (i == 2) {
                System.out.print(ANSI_GREEN);
                print(idioma, "Tercer Premio: 500.000 -->  ");
                System.out.println(" " + String.format("%05d", premiosGordos[i]) + ANSI_RESET);
            }
            if (i == 3) {
                // VECTOR QUE CONTENDRA LOS DOS NUMEROS GANADORES DEL CUARTO PREMIO
                int[] cuartoPremio = new int[2];

                cuartoPremio[0] = premiosGordos[i];
                cuartoPremio[1] = premiosGordos[i + 1];

                System.out.print(ANSI_GREEN);
                print(idioma, "Cuarto premio : 200.000 --> ");
                System.out.println(" " + String.format("%05d", cuartoPremio[0]) + ", " + String.format("%05d", cuartoPremio[1]) + ANSI_RESET);
            }
            if (i > 4) {
                {
                    quintoPremio[aux] = premiosGordos[i];
                    aux++;
                }

            }
        }
        System.out.print(ANSI_GREEN);
        print(idioma, "Quinto premio : 60.000 --> ");
        System.out.print(ANSI_RESET);

        for (int k = 0; k < quintoPremio.length - 1; k++) {
            System.out.print(ANSI_GREEN + " " + String.format("%05d", quintoPremio[k]) + ", " + ANSI_RESET);
        }
        System.out.println(ANSI_GREEN + " " + String.format("%05d", quintoPremio[quintoPremio.length - 1]) + ANSI_RESET);
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
     * PROCEDIMIENTO QUE SOLICITA LOS DATOS DE LAS PERSONAS Y COMPRUEBA SI LOS
     * DATOS INTRODUCIDOS SON CORRECTOS
     *
     * @param idioma
     * @return, DEVEULEVE EL OBJETO PERSONA
     * @throws java.io.IOException
     */
    public static Persona pedirPersona(String idioma) throws IOException {
        Persona p = new Persona();
        boolean salir = false;
        do {
            print(idioma, "Nombre: ");
            p.nombre = s.next();
            if (p.nombre.length() < 15) {
                salir = true;
                for (int i = 15 - p.nombre.length(); i > 0; i--) {
                    p.nombre += " ";
                }
            } else {
                print(idioma, "Tiene que tener menos de quince caracteres.");
            }
        } while (!salir);
        p.numero = escanearEntero("Escribe tu numero de loteria: ", idioma);
        salir = false;
        do {
            p.dinero = escanearEntero("Escribe la cantidad de dinero que quieres aportar [5-60]: ", idioma);
            if (p.dinero >= 5 && p.dinero <= 60 && p.dinero % 5 == 0) {
                salir = true;
            } else {
                print(idioma, "¡Tiene que estar entre 5-60 y ser multiplo de 5!");
            }
        } while (!salir);

        return p;
    }

    /**
     * COMPRUEBA EL NUMERO INTRODUCIDO
     *
     * SI EL NUMERO INGRESADO COINCIDE CON ALGUN NUMERO GAANDOR DEL PREMIO SE
     * ACTULIZA LA VARIABLE "cantidad" CON EL VALOR CORRESPONDIENTE
     *
     * @param premiosGordos
     * @param premios1000
     * @param idioma
     * @throws java.io.IOException
     */
    public static void comprobarNumero(int[] premiosGordos, int[] premios1000, String idioma) throws IOException {
        int cantidad = 0;
        int numero = escanearEntero("Introduce tu numero para comprobarlo: ", idioma);

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
            System.out.print(ANSI_RED);
            print(idioma, "No has ganado nada");
            System.out.println(ANSI_RESET);
        } else {
            System.out.print(ANSI_GREEN);
            print(idioma, "Cantidad: ");
            System.out.println(cantidad + ANSI_RESET);
        }
    }

    /**
     * COMPROBAR NUMERO DE LAS COLLAS
     *
     * LA FUNCIÓN RECCORE LA MATRIZ DE PERSONAS DE LA C0LLA Y POR CADA PERSONA
     * COMPUEBA SI HA GANDO ALGÚN PERMIO
     *
     * @param premiosGordos, RECIBE PARAMETROS DE PREMIOS GORDOS PARA COMPROBAR
     * @param premios1000, RECIBE PARAMETROS DE PREMIOS 1000 PARA COMPROBAR
     * @param colla, RECIBE LAS COLLAS
     * @param matriuCollas, UNA MATRIZ DE PERSONAS QUE REPRESENTA A LOS MIEMBROS
     * DE LA COLLA
     * @param anyoComprobacion, RECIBE EL AÑO DE REALIZACIÓN DEL SORTEO
     * @param idioma
     * @throws java.io.IOException
     */
    public static void comprobarNumeroColla(int[] premiosGordos, int[] premios1000, int colla, Persona[][] matriuCollas, int anyoComprobacion, String idioma) throws IOException {
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
            System.out.print(ANSI_RED);
            print(idioma, "No has ganado nada");
            System.out.println(ANSI_RESET);
            /*SE MUESTRA UNA TABLA CON LA INFORMACIÓN DE LOS MIEMBROS DE LA COLLA Y LOS PREMIOS, 
          ADEMAS DEL AÑO, EL NUMERO DE CADA MIEMBRO Y EL DINERO APORTADO*/
        } else {
            print(idioma, "| ANY | MEMBRES | DINERS | PREMI |");
            int dineroTot = 0;
            for (int i = 0; i < matriuCollas[colla - 1].length; i++) {
                dineroTot += matriuCollas[colla - 1][i].dinero;
            }
            System.out.println("| " + anyoComprobacion + " | " + matriuCollas[colla - 1].length + " | " + dineroTot + " | " + cantidad + " |");
            print(idioma, "| NOMBRE         | NUMERO | DINERO | PREMI |");
            for (int i = 0; i < matriuCollas[colla - 1].length; i++) {
                double dineroRep = (double) matriuCollas[colla - 1][i].dinero / dineroTot;
                dineroRep *= cantidad;
                System.out.println("| " + matriuCollas[colla - 1][i].nombre + " | " + matriuCollas[colla - 1][i].numero + " | " + matriuCollas[colla - 1][i].dinero + " | " + (String.format("%.2f", dineroRep)));
            }
        }
    }

    /**
     * INTRODUCIR DATOS EN UN FICHERO TXT
     *
     * FUNCIÓN QUE SE ENCARGA DE ESCIRBIR UNA LINEA EN UN FICHERO DE TEXTO
     * ESPECIFICANDO LA RUTA, EL NOMBRE Y LA EXTENSIÓN
     *
     * @param linea,RECIBE UNA CADENA DE TEXTO A ESCRIBIR
     * @param nombreFichero, NOMBRE DEL FICHERO EN EL QUE SE DEBE ESCRIBIR LA
     * LINEA
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
     * FUNCIÓN QUE SE ENCARGA DE ESCIRBIR UNA LINEA EN UN FICHERO BINARIO
     * ESPECIFICANDO LA RUTA, EL NOMBRE Y LA EXTENSIÓN
     *
     * @param linea,RECIBE UNA CADENA DE TEXTO A ESCRIBIR
     * @param nombreFichero, NOMBRE DEL FICHERO EN EL QUE SE DEBE ESCRIBIR LA
     * LINEA
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
     * FUNCIÓN QUE SE ENCARGA DE ESCRIBIR UNA MATRIZ DE OBJETOS DE LA CLASE
     * PERSONA EN UN FICHERO BINARIO
     *
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
     * FORMATEAR COLLAS
     *
     * FUNCION QUE SE ENCARGA DE FORMATEAR LA MATRIZ PARA MOSTRAR DE UNA MANERA
     * LEGIBLE EN FORMA DE CADENA DE TEXTO
     *
     * @param p, RECIBE LA MATRIZ DE LAS PERSONAS
     * @param nombresCollas
     * @return
     */
    public static String formatearCollas(Persona[][] p, String[] nombresCollas) {
        String result = "";
        for (int i = 0; i < p.length; i++) {
            result += (nombresCollas[i].toUpperCase()) +"\n";
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
     * ESTA FUNCIÓN FORMATEA UN VECTOR DE ENTEROS EN UNA CADENA DE TEXTO PARA SU
     * POSTERIOR VISUALIZACIÓN
     *
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
     * LA FUNCIÓN TIENE COMO OBJETIVO COMPROBAR SI EXISTE O NO UN FICHERO
     * BINARIO DE NOMBRE "COLLAS" EN LA RUTA ESPECIFICADA
     *
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
     * ESTA FUNCIÓN CUENTA LA CANTIDAD DE NOMBRES QUE HAY EN EL ARCHIVO BINARIO
     * LLAMADO "COLLA2"
     *
     * @return resultado, DEVUELVE LA DIVICIÓN QUE ES LA CANTIDAD DE NOMBRES EN
     * EL ARCHIVO
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static int ContadorNombres() throws FileNotFoundException, IOException {
        RandomAccessFile raf = new RandomAccessFile(RUTA + "colla2" + EXTENSION_BIN, "rw");
        /*DIVIDIMOS LA LONGITUD POR 12, YA QUE CADA REGISTRO EN EL ARCHIVO OCUPA 12 BYTES 
        (4 bytes para el nombre y 2 bytes para cada uno de los enteros)*/
        int resultado = (int) raf.length() / 12;
        return resultado;
    }

    /**
     * MENU JUGADOR SOLITARIO O EN UNA COLLA
     *
     * MOSTRAMOS TRES OPCIONES A ELEGIR DE MANERA SOLITARIA, EN COLLA O SI DESEA
     * SALIR DEL JUEGO
     *
     * @param matriuCollas, RECIBE LA MATRIZ DE LA COLLAS
     * @param nombreCollas, RECIBE EL NOMBRE DE LAS COLLAS PARA LA COMPROBACIÓN
     * @param premiosGordos, RECIBE EL VECTOS DE PREMIOSGORDOS PARA COMPROBAR
     * LOS NUMEROS GANADORES
     * @param premios1000,RECIBE EL VECTOS DE PREMIOS1000 PARA COMPROBAR LOS
     * NUMEROS GANADORES
     * @param idioma
     * @throws IOException
     */
    public static void menuCollasSolitario(Persona[][] matriuCollas, String[] nombreCollas, int[] premiosGordos, int[] premios1000, String idioma) throws IOException {

        boolean salir = false;
        while (!salir) {
            print(idioma, "Menu:");
            System.out.println();
            print(idioma, "1. Solitario: ");
            System.out.println();
            print(idioma, "2. Colla: ");
            System.out.println();
            print(idioma, "3. Salir: ");
            System.out.println();

            int opcion = escanearEntero("Elige una opcion: ", idioma);
            switch (opcion) {
                /*SI ELIGE ESTA OPCIÓN, SE PIDE EL AÑO DEL SORTEO Y SE COMPRUEBA SI EXISTE 
                SI ES ASI, SE ACTUALIZAN LOS PRERMIOS Y SE COMPRUEBA SI EL NUMERO COINCIDE CON ALGUN PREMIO*/
                case 1 -> {
                    printearAnyos(idioma);
                    int anyo = escanearEntero("Dime que año de loteria quieres: ", idioma);
                    if (ExisteAnyo(String.valueOf(anyo))) {
                        actualizarPremiosGordos(String.valueOf(anyo), premiosGordos);
                        actualizarPremios1000(String.valueOf(anyo), premios1000);
                        comprobarNumero(premiosGordos, premios1000, idioma);
                    } else {
                        print(idioma, "Año no existe tt");
                        System.out.println();
                    }
                }
                /*SI ELIGE ESTA OPCIÓN, SE COMPRUEBA SI HAY UN FICHERO DE COLLAS EXISTENTE, SI NO EXISTE SE PIDE EL NOMBRE DE LA COLLA
                Y SE CREA UN NUEVO FICHERO DE COLLAS CON UNA MATRIZ VACIA SI EXISTE SE ACTUALIZA LA MATRIZ CON LOS DATOS EXISTENTES
                A CONTINUACIÓN MUESTRA EL MENU DE LAS COLLAS QUE PERMITE REALIZAR VARIAS OPERACIONES COMO AÑADIR O ELIMINAR MIEMBRO DE LA COLLA 
                ADEMAS DE COMPROBAR SI LA COLLA HA GANDO ALGUN PREMIO*/
                case 2 -> {
                    if (comprobarFicheroColla()) {
                        nombreCollas = new String[1];
                        nombreCollas[0] = pedirNombre(idioma);
                        matriuCollas = crearMatriuColla(idioma);
                        escribirCollasBinario(matriuCollas);
                        escribirNombresFichero(nombreCollas);
                    } else {
                        matriuCollas = actualizarMatriuCollas(contadFilas());
                        nombreCollas = actualizarNombreCollas(ContadorNombres());
                    }
                    menuCollas(matriuCollas, nombreCollas, premiosGordos, premios1000, idioma);
                }
                case 3 ->
                    salir = true;
                default ->
                    print(idioma, "Escoge una opcion");
            }
        }
    }

    /**
     * ESCRIBIR LOS NOMBRES EN EL FICHERO
     *
     * ESTA FUNCIÓN SE ENCARGA DE ESCRIBUR LOS NOMBRES EN UN ARCHIVO BINARIOS
     *
     * @param nCollas, RECIBE LOS NOMBRES DE LAS COLLAS
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void escribirNombresFichero(String[] nCollas) throws FileNotFoundException, IOException {

        File f = new File(RUTA + "colla2" + EXTENSION_BIN);
        if (!f.exists()) {
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
     *
     * @param matriuCollas, RECIBE LA MATRIZ DE LA COLLAS
     * @param nombreCollas, RECIBE EL NOMBRE DE LAS COLLAS PARA LA COMPROBACIÓN
     * @param premiosGordos, RECIBE EL VECTOS DE PREMIOSGORDOS PARA COMPROBAR
     * LOS NUMEROS GANADORES
     * @param premios1000,RECIBE EL VECTOS DE PREMIOS1000 PARA COMPROBAR LOS
     * NUMEROS GANADORES
     * @param idioma
     * @throws IOException
     */
    public static void menuCollas(Persona[][] matriuCollas, String[] nombreCollas, int[] premiosGordos, int[] premios1000, String idioma) throws IOException {

        boolean salir = false;
        while (!salir) {
            print(idioma, "Menu collas:");
            System.out.println();
            print(idioma, "1. Crear colla: ");
            System.out.println();
            print(idioma, "2. Añadir persona a colla: ");
            System.out.println();
            print(idioma, "3. Comprobar numeros colla: ");
            System.out.println();
            print(idioma, "4. Printear collas: ");
            System.out.println();
            print(idioma, "5. Salir");
            System.out.println();
            int opcion = escanearEntero("Elige una opcion: ", idioma);
            switch (opcion) {
                /*ESTA OPCIÓN PERMITE AL USUARIO CREAR UNA COLLA NUEVA, AÑADIENDO UNA MATRIZ NUEVA
                Y UN NOMBRE DE LA COLLA AL ARCHIVO BINARIO*/
                case 1 -> {
                    matriuCollas = añadirColla(matriuCollas);
                    nombreCollas = añadirNombreCollas(nombreCollas, idioma);
                    escribirCollasBinario(matriuCollas);
                    escribirNombresFichero(nombreCollas);
                }
                /*ESTA OPCIÓN PERMITE AL USUARIO AÑADOR UNA NUEVA PERSONA A UNA COLLA EXISTENTE,
                SELECCIONANDO LA COLLA POR SU NOMBRE*/
                case 2 -> {
                    matriuCollas = añadirPersonaAColla(matriuCollas, menuCollasNoms(nombreCollas, idioma) - 1, idioma);
                    escribirCollasBinario(matriuCollas);
                }
                /*ESTA OPCIÓN PERMITE AL USUARIO COMPROBAR SI LA COLLA SELECCIONADA HA GANADO ALGÚN PREMIO EN UN AÑO DETERMINADO*/
                case 3 -> {
                    printearAnyos(idioma);
                    int anyo = escanearEntero("Dime que año de loteria quieres: ", idioma);
                    if (ExisteAnyo(String.valueOf(anyo))) {
                        actualizarPremiosGordos(String.valueOf(anyo), premiosGordos);
                        actualizarPremios1000(String.valueOf(anyo), premios1000);
                        comprobarNumeroColla(premiosGordos, premios1000, menuCollasNoms(nombreCollas, idioma), matriuCollas, anyo, idioma);
                    } else {
                        print(idioma, "Anyo no existe tt");
                    }
                }
                /*ESTA OPCIÓN PERMITE AL USUARIO IMPRIMIR TODAS LAS COLLAS EXISTENTES EN EL ARCHIVO BINARIOS*/
                case 4 ->
                    System.out.println(formatearCollas(matriuCollas, nombreCollas));
                /*ESTA OPCIÓN PERMITE AL USUARIO SALIR DEL MENU*/
                case 5 ->
                    salir = true;
                default ->
                    print(idioma, "Escoge una opcion");
            }
        }
    }

    /**
     * PEDIR NOMBRE
     *
     * FUNCIÓN QUE PIDE EL NOMBRE DE LA COLLA
     * 
     * @param idioma
     * @return nombreColla, DEVUELVE EL NOMBRE INTRODUCIDO
     * @throws java.io.IOException
     */
    public static String pedirNombre(String idioma) throws IOException {
        String nombreColla;
        boolean salir = false;

        do {
            print(idioma, "Nombre colla: ");
            nombreColla = s.next();

            if (nombreColla.length() < 10) {
                salir = true;
                for (int i = 10 - nombreColla.length(); i > 0; i--) {
                    nombreColla += " ";
                }
            } else {
                print(idioma, "Debe de ser de menos de diez caraceteres.");
            }
        } while (!salir);

        return nombreColla;
    }

    /**
     *ACTUALIZAR EL NOMBRE DE LAS COLLAS
     * 
     * 
     * @param numNombres
     * @return
     * @throws IOException
     */
    public static String[] actualizarNombreCollas(int numNombres) throws FileNotFoundException, IOException {
        String[] nombres = new String[numNombres];

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

        return nombres;

    }

    /**
     * AÑADIR NOMBRE DE COLLAS
     * 
     * FUNCION QUE SOLICITA EL NUEVO NOMBRE DE LAS COLLAS
     * 
     * @param nCollas, RECIBE EL VECTOR DE LAS COLLAS
     * @param idioma, RECIBE EL IDIOMA SOLICITADO POR EL USUARIO
     * @return, DEVUELVE EL NUEVO NOMBRE DE LAS COLLAS
     * @throws IOException 
     */
    public static String[] añadirNombreCollas(String[] nCollas, String idioma) throws IOException {
        String[] nuevoNomCollas = new String[nCollas.length + 1];

        for (int i = 0; i < nCollas.length; i++) {
            nuevoNomCollas[i] = nCollas[i];
        }
        nuevoNomCollas[nCollas.length] = pedirNombre(idioma);

        return nuevoNomCollas;
    }

    /**
     * MENU COLLAS
     * 
     * ESTA FUNCIÓN MUESTRA EL NUMERO DE COLLAS PARA QUE EL USUARIO ELIJA CON CUAL CONTINUAR
     * 
     * @param nombreCollas, RECIBE EL NOMBRE DE LAS COLLAS PARA POSTERIORMENTE MOSTRARLAS
     * @param idioma, RECIBE EL IDIOMA SELECIONADO POR EL USUARIO
     * @return opcion, DEVUELVE LA OPCION SELCCIONADA POR EL USUAIRIO
     * @throws IOException 
     */
    public static int menuCollasNoms(String[] nombreCollas, String idioma) throws IOException {
        for (int i = 0; i < nombreCollas.length; i++) {
            System.out.println((i + 1) + ". " + nombreCollas[i]);
        }
        int opcion = escanearEntero("Elige una opcion: ", idioma);
        return opcion;
    }

    /**
     * AÑADIR PERSONA COLLA
     * 
     * FUNCIÓN QUE AÑADE A UNA PERSONA A UNA COLLA CREADA
     * 
     * @param colla
     * @param fila
     * @param idioma
     * @return
     * @throws IOException 
     */
    public static Persona[][] añadirPersonaAColla(Persona[][] colla, int fila, String idioma) throws IOException {
        Persona p = pedirPersona(idioma);
        Persona[][] collaNueva;
        boolean salir = false;
        boolean encontrado = false;
        int columna = 0;
        int col = 0;
        /*SE UTILIZA UN BUCLE WHILE PARA BUSCAR LA PRIMERA CELDA VACIA EN LA FILA DE LA MATRIZ,
        SI SE ENCUENTRA UNA CELDA VACÍA , SE ALMACENA SU INDICE EN LA VARIABLE COLUMNA,
        SI NO SE ENCUENTRA NINGUNA CELDA VACIA , SE SALE DEL BUCLE*/
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

    /**
     * AÑADIR COLLA
     * 
     * FUNCIÓN QUE SE UTILIZA PARA AÑADIR UNA FILA VACÍA A UNA MATRIZ DE OBJETOS DE LA CLASE PERSONA
     * 
     * @param colla, RECIBE LA MATRIZ
     * @return matriuCollaNueva, devuelve la nueva matriz de collas
     */
    public static Persona[][] añadirColla(Persona[][] colla) {
        Persona[][] matriuCollaNueva = new Persona[colla.length + 1][colla[0].length];

        for (int i = 0; i < colla.length; i++) {
            for (int j = 0; j < colla[0].length; j++) {
                matriuCollaNueva[i][j] = colla[i][j];
            }
        }

        return matriuCollaNueva;
    }
    
    /**
     * CREAR MATRIZ DE COLLAS
     * 
     * FUNCIÓN QUE CREA LA MATRIZ DE LAS COLLAS DE OBJETOS DE LA CLASE PERSONA
     * DONDE CADA COLUMNA REPRESENTA A UNA PERSONA DE LA COLLA Y CADA FILA REPRESENTA UNA POSICIÓN DE LA COLLA
     * 
     * @param idioma, RECIBE EL IDIOMA SELECCIONADO POR EL USUARIO
     * @return matrizCollas, DEVUELVE LA MATRIZ CON TODAS LAS PERSONAS INTRODUCIDAS POR EL USUARIO
     * @throws IOException 
     */
    public static Persona[][] crearMatriuColla(String idioma) throws IOException {

        int personas = escanearEntero("Cuantas personas sois en tu colla?", idioma);
        Persona[][] matrizCollas = new Persona[1][personas];

        for (int i = 0; i < matrizCollas.length; i++) {
            for (int j = 0; j < matrizCollas[0].length; j++) {
                Persona p = pedirPersona(idioma);
                matrizCollas[i][j] = p;
            }
        }

        return matrizCollas;
    }

    /**
     * AÑO EXISTENTE
     * 
     * FUNCIÓN QUE VERIFICA LA EXISTENCIA DE UN AÑO EN UN ARCHIVO DE TEXTO
     * 
     * @param anyo, RECIBE EL AÑO DEL SORTEO
     * @return result, DEVUELVE SI EL AÑO EXISTE O NO 
     * @throws FileNotFoundException
     * @throws IOException 
     */
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

    /**
     * ACTUALIZAR PREMIOS GORDOS
     * 
     * FUNCIÓN QUE SE ENCARGA DE LEER LOS PREMIOSGORDOS DE LA LOTERÍA DEL ARCHIVO DE TEXTO CORRESPONDIENTE 
     * A UN AÑO ESPECÍFICO Y LOS ALMACENA EN UN VECTOR DE ENTEROS
     * 
     * @param anyo, RECIBE EL AÑO DEL SORTEO
     * @param premiosGordos, RECIBE LOS PREMIOSGORDOS PARA ACTUALIZARLOS
     * @throws IOException 
     */
    public static void actualizarPremiosGordos(String anyo, int[] premiosGordos) throws IOException {
        FileReader reader = null;
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

    }
    
    /**
     * ACTUALIZAR PREMIOS GORDOS
     * 
     * FUNCIÓN QUE SE ENCARGA DE LEER LOS PREMIOS1000 DE LA LOTERÍA DEL ARCHIVO DE TEXTO CORRESPONDIENTE 
     * A UN AÑO ESPECÍFICO Y LOS ALMACENA EN UN VECTOR DE ENTEROS
     * 
     * @param anyo, RECIBE EL AÑO DEL SORTEO
     * @param premios1000, RECIBE LOS PREMIOS1000 PARA ACTUALIZARLOS
     * @throws IOException 
     */
    public static void actualizarPremios1000(String anyo, int[] premios1000) throws FileNotFoundException, IOException {
        FileReader reader = null;
        File f = new File(RUTA + anyo + EXTENSION_TXT);
        reader = new FileReader(f);
        BufferedReader buffer = new BufferedReader(reader);
        String linea;
        linea = buffer.readLine();

        while (linea != null) {
            linea = buffer.readLine();

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

    }

    /**
     * MOSTRAR AÑOS
     * 
     * ESTA FUNCIÓN NOS MUESTRA LOS AÑOS DE LOS SORTEOS REALIZADOS
     * 
     * @param idioma, RECIBE EL IDIOMA SELECCIONADO POR EL USUARIO
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void printearAnyos(String idioma) throws FileNotFoundException, IOException {
        FileReader reader = null;
        File f = new File(RUTA + "anyo" + EXTENSION_TXT);
        String linea;
        reader = new FileReader(f);
        BufferedReader buffer = new BufferedReader(reader);
        linea = buffer.readLine();
        print(idioma, "Años: ");System.out.println();
        while (linea != null) {
            System.out.println(linea);
            linea = buffer.readLine();
        }

    }
    
    /**
     * VERRIFICAR AÑOS
     * 
     * ESTA FUNCIÓN COMPRUEBA LOS AÑOS EXISTENTES Y SI ESTAN VACIOS, 
     * SI NO EXISTE EL AÑO INTRODUCIDO CREA UN NUEVO FICHERO CON ESE AÑO DE SORTEO
     * 
     * @return vacio, DEVUELVE SI EL FICHERO ESTA VACIO O NO
     * @throws IOException 
     */
    public static boolean verificarFicheroAnyos() throws IOException {
        FileReader reader = null;
        boolean vacio = false;

        File f = new File(RUTA + "anyo" + EXTENSION_TXT);
        if (!f.exists()) {
            f.createNewFile();
        }

        String linea;
        reader = new FileReader(f);
        BufferedReader buffer = new BufferedReader(reader);
        linea = buffer.readLine();
        if (linea == null) {
            vacio = true;
        }
        buffer.close();
        reader.close();
        return vacio;
    }

    /**
     * 
     * @return
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static int contadFilas() throws FileNotFoundException, IOException {
        int filas = 0;
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

        raf.close();
        return filas;
    }

    /**
     * AÑADIR PERSONA 
     * 
     * LA FUNCIÓN BUSCA LA PRIMERA POSICIÓN VACÍA EN LA FILA INDICADA Y AGREGA LA PERSONA EN ESA POSICIÓN.
     * SI NO ENCUENTRA UNA POSICIÓN VACÍA, CREA UNA NUEVA MATRIZ CON UNA COLUMNA ADICIONAL Y COPIA 
     * LOS DATOS DE LA MATRIZ ORIGINAL EN LA NUEVA MATRIZ ANTES DE AGREGAR LA PERSONA EN LA POSICIÓN VACÍA
     * 
     * @param colla, RECIBE LA MATRIZ
     * @param fila, RECIBE LA POSICION
     * @param p, RECIBE LAS PERSONAS
     * @return collaNueva, DEVUELVE LA COLLA ACTUALIZADA
     */
    public static Persona[][] añadirPersonaFAColla(Persona[][] colla, int fila, Persona p) {
        Persona[][] collaNueva;
        boolean salir = false, encontrado = false;
        int columna = 0, col = 0;
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

    /**
     * 
     * @param filas
     * @return
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static Persona[][] actualizarMatriuCollas(int filas) throws FileNotFoundException, IOException {
        Persona[][] matriuCollas = new Persona[filas + 1][1];
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

        return matriuCollas;
    }

    public static int contador(String idioma, String linea) throws FileNotFoundException, IOException {
        int cont = 0;
        FileReader reader = null;

        File f = new File(RUTA_IDIOMAS + idioma + EXTENSION_TXT);
        reader = new FileReader(f);
        BufferedReader buffer = new BufferedReader(reader);
        String palabra = buffer.readLine();
        while (palabra != null && !palabra.equals(linea)) {
            palabra = buffer.readLine();
            cont++;
        }

        return cont;
    }
    
    /**
     * MULTILENGUAJE
     * 
     * ESTA FUNCIÓN BUSCA LA LÍNEA EN EL ARCHIVO EN ESPAÑOL Y LUEGO SALTA LAS LÍNEAS NECESARIAS 
     * EN EL ARCHIVO DEL IDIOMA ESPECIFICADO ANTES DE IMPRIMIR LA LÍNEA TRADUCIDA
     * 
     * @param idioma, RECIBE EL IDIOMA ESPECIFICADO POR EL USUARIO
     * @param linea, RECIBE LA LINEA DE TEXTO
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void print(String idioma, String linea) throws FileNotFoundException, IOException {
        FileReader reader = null;

        File f = new File(RUTA_IDIOMAS + idioma + EXTENSION_TXT);
        reader = new FileReader(f);
        BufferedReader buffer = new BufferedReader(reader);

        if (idioma.equals("spanish")) {
            String palabra = buffer.readLine();
            while (palabra != null && !palabra.equals(linea)) {
                palabra = buffer.readLine();
            }
            System.out.print(palabra);
        } else {
            String palabra;
            for (int cont = contador("spanish", linea); cont > 0; cont--) {
                palabra = buffer.readLine();
            }
            System.out.print(buffer.readLine());
        }

    }
}
