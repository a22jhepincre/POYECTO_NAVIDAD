package proyecto.navidad;

import java.util.Scanner;
import java.util.Random;

public class ProyectoNavidad {
    
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static Scanner s = new Scanner(System.in);
    public static Random rnd = new Random();
    public static final int MAX_PREMIOSGORDOS = 13;
    public static final int MAX_1000 = 1794;

    public static void main(String[] args) {
        int[] premiosGordos = ganadores();
        int[] premios1000 = ganadores1000();
        menu(premiosGordos, premios1000);
    }

    static void menu(int[] premiosGordos, int[] premios1000) {
        boolean salir = false;
        do {

            System.out.println("1. Realizar sorteo.\n2. Comprobar mi numero.\n3. Salir\n");
            int opcion = escanearEntero("Selecciona una opcion: ");
            switch (opcion) {
                case 1 ->
                    sorteig(premiosGordos, premios1000);
                case 2 ->
                    comprobarNumero(premiosGordos, premios1000);
                case 3 ->
                    salir = true;
                default ->
                    System.out.println("Opcion invalida. Prueba de nuevo");
            }
        } while (!salir);
    }

    static int escanearEntero(String mtj) {
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

    static int[] ganadores() {
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
    
    static int[] ganadores1000() {
        int[] premiosGordos = new int[MAX_1000];

        for (int i = 0; i < MAX_1000; i++) {
            premiosGordos[i] = rnd.nextInt(100000);
            for (int j = 0; j < i; j++) {
                while (premiosGordos[i] == premiosGordos[j]) {
                    premiosGordos[i] = rnd.nextInt(100000);
                }
            }
        }
        return premiosGordos;
    }
    
    static void sorteig(int[] premiosGordos, int[] premios1000) {

        int[] quintoPremio = new int[8];
        int aux = 0;

        for (int i = 0; i < MAX_PREMIOSGORDOS; i++) {
            if (i == 0) {
                int primerPremio = premiosGordos[i];
                System.out.println(ANSI_GREEN+ "Premio Gordo: 4.000.000 --> " + premiosGordos[i]+ ANSI_RESET);
            }
            if (i == 1) {
                int segundoPremio = premiosGordos[i];
                System.out.println( ANSI_GREEN+ "Segundo premio: 1.250.000 --> " + premiosGordos[i]+ ANSI_RESET);
            }
            if (i == 2) {
                int tercerPremio = premiosGordos[i];
                System.out.println(ANSI_GREEN+ "Tercer Premio: 500.000 --> " + premiosGordos[i]+ ANSI_RESET);
            }
            if (i == 3) {
                int[] cuartoPremio = new int[2];
                cuartoPremio[0] = premiosGordos[i];
                cuartoPremio[1] = premiosGordos[i + 1];
                System.out.println(ANSI_GREEN+ "Cuarto premio : 200.000 --> " + cuartoPremio[0] + ", " + cuartoPremio[1]+ ANSI_RESET);
            }
            if (i > 4) {
                {
                    quintoPremio[aux] = premiosGordos[i];
                    aux++;
                }

            }
        }
        System.out.print(ANSI_GREEN+ "Quinto premio : 60.000 --> "+ ANSI_RESET);
        for (int k = 0; k < quintoPremio.length - 1; k++) {
            System.out.print(ANSI_GREEN+ quintoPremio[k] + ", "+ ANSI_RESET);
        }
        System.out.println(ANSI_GREEN+ quintoPremio[quintoPremio.length - 1]+ ANSI_RESET);
        System.out.print("\n");
    }

    static void comprobarNumero(int[] premiosGordos, int[] premios1000) {
        int cantidad = 0;
        int numero = escanearEntero("Introduce el número a comprobar: ");
        for (int i = 0; i < premiosGordos.length; i++) {
            if (numero == premiosGordos[i]) {
                if (i == 0) {
                    cantidad += 4000000;
                }
                if (i == 1) {
                    cantidad += 1250000;
                }
                if (i == 2) {
                    cantidad += 500000;
                }
                if (i == 3 || i == 4) {
                    cantidad += 200000;
                }
                if (i > 4) {
                    cantidad += 60000;
                }
            }

        }
        
        //COMPROBAR LAS APROXIMACIONES DEL 1R, 2N Y 3R PREMIO GORDO
        if(premiosGordos[0]+1 == numero || premiosGordos[0]-1 == numero)
            cantidad += 20000;
        if(premiosGordos[1]+1 == numero ||premiosGordos[1]-1 == numero)
            cantidad += 12500;
        if(premiosGordos[2]+1 == numero ||premiosGordos[2]-1 == numero)
            cantidad += 9600;
        
        //COMPROBAR LAS CENTENAS DEL 1R, 2N, 3, Y 4R PREMIO
        boolean centena = false;

        if (premiosGordos[0] / 100 == numero / 100) {
            cantidad += 1000;
            centena = true;
        } else if (numero == premiosGordos[0]) {
            cantidad += 0;
        }

        if (premiosGordos[1] / 100 == numero / 100) {
            cantidad += 1000;
            centena = true;
        } else if (numero == premiosGordos[1]) {
            cantidad += 0;
        }

        if (premiosGordos[2] / 100 == numero / 100) {
            cantidad += 1000;
            centena = true;
        } else if (numero == premiosGordos[2]) {
            cantidad += 0;
        }

        if (premiosGordos[3] / 100 == numero / 100) {
            cantidad += 1000;
            centena = true;
        } else if (numero == premiosGordos[3]) {
            cantidad += 0;
        }

        if (premiosGordos[4] / 100 == numero / 100) {
            cantidad += 1000;
            centena = true;
        } else if (numero == premiosGordos[4]) {
            cantidad += 0;
        }

        //COMPROBAR LOS DOS ULTIMOS NUMEROS DEL 1R, 2N i 3R PREMIO
        //Comprobem que si ja ha obtingut el premi de la centena no pot obtenir aquest
        boolean dosultims = false;

        if (premiosGordos[0] % 100 == numero % 100 && !centena) {
            cantidad += 1000;
            dosultims = true;
        } else if (numero == premiosGordos[0]) {
            cantidad += 0;
        }
        if (premiosGordos[1] % 100 == numero % 100 && !centena) {
            cantidad += 1000;
            dosultims = true;
        } else if (numero == premiosGordos[1]) {
            cantidad += 0;

        }
        if (premiosGordos[2] % 100 == numero % 100 && !centena) {
            cantidad += 1000;
            dosultims = true;
        } else if (numero == premiosGordos[2]) {
            cantidad += 0;

        }

        //Comprobem si l'ultim numero es igual al del primer premi
        if ((premiosGordos[0] % 10 == numero % 10) && (!centena && !dosultims)) {
            cantidad += 200;
        }
        else if (numero == premiosGordos[0]) {
            cantidad += 0;

        }

        //COMPROBAR GANADOR DE 1000 1794 GANADORES
        for (int i = 0; i < MAX_1000; i++) {
            if (numero == premios1000[i]) {
                cantidad += 1000;
            }
        }
        if(cantidad==0){
            System.err.println("El numero "  + numero + " ha ganado " + cantidad + " EUROS \n");
        }
        else {
            System.out.println(ANSI_GREEN+ "El numero " + numero + " ha ganado " + cantidad + " EUROS \n" + ANSI_RESET);
        }
        
    }

}
