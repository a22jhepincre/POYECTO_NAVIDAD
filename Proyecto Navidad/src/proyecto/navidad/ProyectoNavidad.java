package proyecto.navidad;

import java.util.Scanner;
import java.util.Random;

public class ProyectoNavidad {

    static final String ANSI_RED = "\033[0;31m";
    static final String ANSI_GREEN = "\u001B[32m";
    static final String ANSI_RESET = "\u001B[0m";
    static Scanner s = new Scanner(System.in);
    static Random rnd = new Random();
    static final int MAX_PREMIOSGORDOS = 13;
    static final int MAX_1000 = 1794;
    static final int PRIMER_PREMIO = 4000000;
    static final int SEGUNDO_PREMIO = 1250000;
    static final int TERCER_PREMIO = 500000;
    static final int CUARTO_PREMIO = 200000;
    static final int QUINTO_PREMIO = 60000;
    static final int PREMIO_1000 = 1000;

    public static void main(String[] args) {
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
    public static void menu(int[] premiosGordos, int[] premios1000) {
        boolean salir = false;
        boolean sorteig = false;
        // MEDIANTE UN BUCLE "DO-WHILE" CONTROLAMOS LAS OPCIONES INVALIDAS
        do {

            System.out.println("1. Realizar sorteo.\n2. Comprobar mi numero.\n3. Salir\n");
            int opcion = escanearEntero("Selecciona una opcion: ");
            // UN "switch" PARA TRATAR LAS DIFERENTES OPCIONES
            switch (opcion) {
                case 1 -> {
                    sorteig(premiosGordos, premios1000);
                    sorteig = true;
                }
                case 2 -> {
                    if (!sorteig) {
                        System.out.println("El sorteo todavia no se ha realizado");
                    } else {
                        comprobarNumero(premiosGordos, premios1000);
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
     * @param premiosGordos
     * @return premios1000, devolvera los 1794 numeros ganadores
     */
    public static int[] ganadores1000(int[] premiosGordos) {
        int[] premios1000 = new int[MAX_1000];

        for (int i = 0; i < MAX_1000; i++) {
            premios1000[i] = rnd.nextInt(100000);
            for (int j = 0; j < i; j++) {
                while (premios1000[i] == premios1000[j]) {
                    premios1000[i] = rnd.nextInt(100000);
                }
            }
        }

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
    public static int comprobarPremioMil(int[] premios1000, int numero) {
        int cantidad = 0;
        for (int i = 0; i < MAX_1000; i++) {
            if (numero == premios1000[i]) {
                cantidad += PREMIO_1000;
            }
        }

        return cantidad;
    }

    public static int comprobarAproximaciones(int[] premiosGordos, int numero) {
        int cantidad = 0;

        //COMPROBAR LAS APROXIMACIONES DEL 1R, 2N Y 3R PREMIO GORDO
        if (premiosGordos[0] + 1 == numero || premiosGordos[0] - 1 == numero) {
            cantidad += 20000;

        }
        if (premiosGordos[1] + 1 == numero || premiosGordos[1] - 1 == numero) {
            cantidad += 12500;

        }
        if (premiosGordos[2] + 1 == numero || premiosGordos[2] - 1 == numero) {
            cantidad += 9600;

        }

        return cantidad;
    }

    //COMPROBAR LAS CENTENAS DEL 1R, 2N, 3R, Y 4R PREMIO
    public static int comprobarCentena(int[] premiosGordos, int numero) {
        int cantidad = 0;

        if (premiosGordos[0] / 100 == numero / 100) {
            cantidad += 1000;

        } else if (numero == premiosGordos[0]) {
            cantidad += 0;
        }

        if (premiosGordos[1] / 100 == numero / 100) {
            cantidad += 1000;

        } else if (numero == premiosGordos[1]) {
            cantidad += 0;
        }

        if (premiosGordos[2] / 100 == numero / 100) {
            cantidad += 1000;

        } else if (numero == premiosGordos[2]) {
            cantidad += 0;
        }

        if (premiosGordos[3] / 100 == numero / 100) {
            cantidad += 1000;

        } else if (numero == premiosGordos[3]) {
            cantidad += 0;
        }

        if (premiosGordos[4] / 100 == numero / 100) {
            cantidad += 1000;

        } else if (numero == premiosGordos[4]) {
            cantidad += 0;
        }

        return cantidad;
    }

    //COMPROBAR LOS DOS ULTIMOS NUMEROS DEL 1R, 2N i 3R PREMIO
    public static int comprobarDosUltimos(int[] premiosGordos, int numero) {
        int cantidad = 0;

        if (premiosGordos[0] % 100 == numero % 100) {
            cantidad += 1000;

        } else if (numero == premiosGordos[0]) {
            cantidad += 0;
        }
        if (premiosGordos[1] % 100 == numero % 100) {
            cantidad += 1000;

        } else if (numero == premiosGordos[1]) {
            cantidad += 0;

        }
        if (premiosGordos[2] % 100 == numero % 100) {
            cantidad += 1000;

        } else if (numero == premiosGordos[2]) {
            cantidad += 0;

        }
        
        return cantidad;
    }

    public static int comprobarUltimo(int[] premiosGordos, int numero) {
        int cantidad = 0;
        //COMPROBAMOS SI EL ULTIMO NUMERO ES IGUAL DEL PRIMER PREMIO
        if ((premiosGordos[0] % 10 == numero % 10)) {
            cantidad += 200;
        } else if (numero == premiosGordos[0]) {
            cantidad += 0;

        }

        return cantidad;
    }

    /**
     * COMPROBAR NUMERO COMPRUEBA EL NUMERO DEL BOLETO INTRODUCIDO CON EL SORTEO
     * REALIZADO Y COMPRUEBA EL PREMIO GANADOR DEPENDIENDO LAS CONDICIONES
     * ESTABLECIDAS
     *
     * @param premiosGordos RECIBE EL VECTOR LLENO DE LOS "premiosGordos"
     * @param premios1000 RECIBE EL VECTOR LLENO DE LOS "premios100"
     */
    public static void comprobarNumero(int[] premiosGordos, int[] premios1000) {
        int cantidad = 0;
        int aux = 0;
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

            aux += comprobarPremioMil(premios1000, numero);
            if (aux != 0) {
                cantidad += aux;
            }
        }

        // MOSTRAMOS POR PANTALLA EL NUMERO CON LA CANTIDAD GANADA
        if (cantidad == 0) {
            System.out.println(ANSI_RED + "El numero " + String.format("%05d", numero) + " ha ganado " + cantidad + " EUROS \n" + ANSI_RESET);
        } else {
            System.out.println(ANSI_GREEN + "El numero " + String.format("%05d", numero) + " ha ganado " + cantidad + " EUROS \n" + ANSI_RESET);
        }

    }

}
