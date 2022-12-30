package proyecto.navidad;

import java.util.Scanner;
import java.util.Random;

public class ProyectoNavidad {

    public static Scanner s = new Scanner(System.in);
    public static Random rnd = new Random();
    public static final int MAX = 13;

    public static void main(String[] args) {
        int[] premiosGordos = ganadores();
        menu(premiosGordos);
    }

    static void menu(int[] premiosGordos) {
        boolean salir = false;
        do {

            System.out.println("1. Realizar sorteo.\n2. Comprobar mi numero.\n3. Salir\n");
            int opcion = escanearEntero("Selecciona una opcion: ");
            switch (opcion) {
                case 1 ->
                    sorteig(premiosGordos);
                case 2 ->
                    comprobarNumero(premiosGordos);
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
        int[] premiosGordos = new int[MAX];

        for (int i = 0; i < MAX; i++) {
            premiosGordos[i] = rnd.nextInt(100000);
            for (int j = 0; j < i; j++) {
                while (premiosGordos[i] == premiosGordos[j]) {
                    premiosGordos[i] = rnd.nextInt(100000);
                }
            }
        }
        return premiosGordos;
    }

    static void sorteig(int[] premiosGordos) {

        int[] quintoPremio = new int[8];
        int aux = 0;

        for (int i = 0; i < MAX; i++) {
            if (i == 0) {
                int primerPremio = premiosGordos[i];
                System.out.println("Premio Gordo: 4.000.000 --> " + premiosGordos[i]);
            }
            if (i == 1) {
                int segundoPremio = premiosGordos[i];
                System.out.println("Segundo premio: 1.250.000 --> " + premiosGordos[i]);
            }
            if (i == 2) {
                int tercerPremio = premiosGordos[i];
                System.out.println("Tercer Premio: 500.000 --> " + premiosGordos[i]);
            }
            if (i == 3) {
                int[] cuartoPremio = new int[2];
                cuartoPremio[0] = premiosGordos[i];
                cuartoPremio[1] = premiosGordos[i + 1];
                System.out.println("Cuarto premio : 200.000 --> " + cuartoPremio[0] + ", " + cuartoPremio[1]);
            }
            if (i > 4) {
                {
                    quintoPremio[aux] = premiosGordos[i];
                    aux++;
                }

            }
        }
        System.out.print("Quinto premio : 60.000 --> ");
        for (int k = 0; k < quintoPremio.length - 1; k++) {
            System.out.print(quintoPremio[k] + ", ");
        }
        System.out.println(quintoPremio[quintoPremio.length - 1]);
        System.out.print("\n");
    }

    static void comprobarNumero(int[] premiosGordos) {
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

        System.out.println("El numero " + numero + " ha ganado " + cantidad + " EUROS \n");
    }

}
