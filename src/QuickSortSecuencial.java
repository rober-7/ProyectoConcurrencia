public class QuickSortSecuencial {

    // Método principal que ordena el arreglo
    public void quickSort(int[] arreglo, int inicio, int fin) {
        if (inicio < fin) {
            // Obtener el índice del pivote después de la partición
            int indicePivote = particionar(arreglo, inicio, fin);

            // Ordenar recursivamente las dos mitades
            quickSort(arreglo, inicio, indicePivote - 1);
            quickSort(arreglo, indicePivote + 1, fin);
        }
    }

    // Método para reorganizar el arreglo usando un pivote
    private int particionar(int[] arreglo, int inicio, int fin) {
        int pivote = arreglo[fin]; // Elegimos el último elemento como pivote
        int i = inicio - 1; // Índice del menor elemento

        for (int j = inicio; j < fin; j++) {
            // Si el elemento actual es menor o igual al pivote
            if (arreglo[j] <= pivote) {
                i++;
                // Intercambiar arreglo[i] y arreglo[j]
                intercambiar(arreglo, i, j);
            }
        }

        // Intercambiar el pivote con el siguiente elemento mayor
        intercambiar(arreglo, i + 1, fin);
        return i + 1; // Retornar el índice del pivote
    }

    // Método auxiliar para intercambiar dos elementos del arreglo
    private void intercambiar(int[] arreglo, int i, int j) {
        int temp = arreglo[i];
        arreglo[i] = arreglo[j];
        arreglo[j] = temp;
    }

    // Método para imprimir el arreglo (útil para pruebas)
    public void imprimirArreglo(int[] arreglo) {
        for (int num : arreglo) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    // Método principal para probar la implementación
    public static void main(String[] args) {
        QuickSortSecuencial qss = new QuickSortSecuencial();

        int[] arreglo = { 34, 7, 23, 32, 5, 62, 15 };

        System.out.println("Arreglo original:");
        qss.imprimirArreglo(arreglo);

        // Aplicar QuickSort
        qss.quickSort(arreglo, 0, arreglo.length - 1);

        System.out.println("Arreglo ordenado (QuickSort Secuencial):");
        qss.imprimirArreglo(arreglo);
    }
}
