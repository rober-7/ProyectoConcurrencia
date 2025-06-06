import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class TestQuickSort {

    // Metodo para generar un arreglo aleatorio
    public static int[] generarArregloAleatorio(int tamaño) {
        Random rand = new Random();
        int[] arreglo = new int[tamaño];
        for (int i = 0; i < tamaño; i++) {
            arreglo[i] = rand.nextInt(100_000); // Números entre 0 y 99.999
        }
        return arreglo;
    }

    //Metodo para clonar un arreglo (para asegurar que ambos usen el mismo)
    public static int[] clonarArreglo(int[] original) {
        return original.clone();
    }

    public static void main(String[] args) {
        int[] tamaños = {10_000, 50_000, 100_000, 500_000, 1_000_000, 10_000_000};

        for (int tamaño : tamaños) {
            System.out.println("---- Tamaño del arreglo: " + tamaño + " ----");

            //Se genera un arreglo aleatorio
            int[] arregloOriginal = generarArregloAleatorio(tamaño);

            //############################################Secuencial###################################################

            int[] arregloSecuencial = clonarArreglo(arregloOriginal);
            QuickSortSecuencial qss = new QuickSortSecuencial();
            long inicioSec = System.nanoTime();
            qss.quickSort(arregloSecuencial, 0, arregloSecuencial.length - 1);
            long finSec = System.nanoTime();
            long tiempoSec = (finSec - inicioSec) / 1_000_000;
            System.out.println("Tiempo QuickSort Secuencial: " + tiempoSec + " ms");


            //################################Fork/Join Concurrente####################################################

            int[] arregloConcurrente = clonarArreglo(arregloOriginal);
            ForkJoinPool pool = new ForkJoinPool(); // Usa todos los núcleos disponibles, EN MI CASO 6 NUCLEOS
            QuickSortConcurrente tarea = new QuickSortConcurrente(arregloConcurrente, 0, arregloConcurrente.length - 1);
            long inicioConc = System.nanoTime(); //se toma un tiempo inicial para medir el algortimo concurrente
            pool.invoke(tarea); // Ejecuta la tarea
            long finConc = System.nanoTime(); //tiempo donde finalizo la concurrencia
            long tiempoConc = (finConc - inicioConc) / 1_000_000; //resultado final del tiempo medido
            System.out.println("Tiempo QuickSort Concurrente: " + tiempoConc + " ms");

            System.out.println();
        }
    }
}
