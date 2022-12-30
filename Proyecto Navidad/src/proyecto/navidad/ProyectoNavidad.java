package proyecto.navidad;

import java.util.Scanner;
import java.util.Random;

public class ProyectoNavidad {

    public static Scanner s = new Scanner(System.in);
    public static Random rnd = new Random();

    public static void main(String[] args) {

        menu();
    }

    static void menu() {
        boolean salir = false;
        do {

            System.out.println("1. Realizar sorteo.\n2. Comprobar mi numero.\n3. Salir\n");
            int opcion = escanearEntero("Selecciona una opcion: ");
            switch (opcion) {
                case 1 ->
                    sorteig();
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

    static void sorteig() {

        final int MAX = 13;
        int[] premiosGordos = new int[MAX];
        int[] quintoPremio = new int[8];
        int aux = 0;
        
        for (int i = 0; i < MAX; i++) {
            premiosGordos[i] = rnd.nextInt(100000);
            for (int j = 0; j < i; j++) {
                while (premiosGordos[i] == premiosGordos[j]) {
                    premiosGordos[i] = rnd.nextInt(100000);
                }
            }
        }

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
        for (int k = 0; k < quintoPremio.length-1; k++) {
            System.out.print(quintoPremio[k] + ", ");
        }
        System.out.println(quintoPremio[quintoPremio.length-1]);
    }

}
