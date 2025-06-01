import java.util.concurrent.RecursiveAction;
import java.util.Random;




/*
 * Esta clase utiliza el framework Fork/Join de Java (java.util.concurrent)
 * para ejecutar QuickSort de manera concurrente.
 * La clase extiende RecursiveAction, lo que permite dividir recursivamente
 * el problema y ejecutarlo en paralelo mediante múltiples núcleos.
 * El metodo compute() es el núcleo de la concurrencia: cuando el subarreglo es grande,
 * divide el trabajo en dos tareas (izquierda y derecha del pivote)
 * y las ejecuta en paralelo usando invokeAll().
 */

public class QuickSortConcurrente extends RecursiveAction {
    private final int[] arr;
    private final int start, end;
    private static final int UMBRAL = 1000; // Umbral para evitar tareas pequeñas

    public QuickSortConcurrente(int[] arr, int start, int end) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    //El metodo compute hace que el algoritmo se divida recursivamente y se ejecute en paralelo
    @Override
    protected void compute() {
        if (start >= end) return;

        // Si el tamaño del subarreglo es pequeño, usar ordenamiento secuencial
        if (end - start < UMBRAL) {
            quickSortSecuencial(arr, start, end);
            return;
        }

        int pivote = partition(arr, start, end);

        // Crear tareas recursivas para los dos lados del pivote
        QuickSortConcurrente izquierda = new QuickSortConcurrente(arr, start, pivote - 1);
        QuickSortConcurrente derecha = new QuickSortConcurrente(arr, pivote + 1, end);

        // Ejecutar en paralelo
        invokeAll(izquierda, derecha);
    }

    private void quickSortSecuencial(int[] arr, int start, int end) {
        if (start >= end) return;

        int pivote = partition(arr, start, end);
        quickSortSecuencial(arr, start, pivote - 1);
        quickSortSecuencial(arr, pivote + 1, end);
    }


    //Reorganiza el arreglo alrededor del pivote aleatorio seleccionado.
    //Los elementos menorres o iguales al pivote quedan a su izquierda
    //y los mayores a su derecha
    private int partition(int[] arr, int start, int end) {
        int pivotIndex = new Random().nextInt(end - start + 1) + start;
        int pivotValue = arr[pivotIndex];

        swap(arr, pivotIndex, end);

        int i = start - 1;
        for (int j = start; j < end; j++) {
            if (arr[j] <= pivotValue) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, end);
        return i + 1;
    }

    //Intercambia el elemento pivoteado con el elemento final del array
    private void swap(int[] arr, int i, int j) {
        int aux = arr[i];
        arr[i] = arr[j];
        arr[j] = aux;
    }


}
