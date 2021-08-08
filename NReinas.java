import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class NReinas {
    public static void main(String[] args) {
        int NIND = 10;
        int NREINAS = 8;
        System.out.println(generaPoblacionInicial(NIND, NREINAS));


    }

    /**
     * Metodo que genera la poblacion inicial de forma aleatoria 
     * restringe que no haya valores repetidos
     * @param NIND Numero de individuos por generacion
     * @param NREINAS Numero de reinas 
     * @return Poblacion inicial
     */
    public static ArrayList<int[]> generaPoblacionInicial(int NIND, int NREINAS){
        ArrayList<int[]> poblacion = new ArrayList<>();
        for(int i = 0; i<NIND; i++){
            int[] cromosoma = new int[NREINAS];
            cromosoma = llenaCromosoma(cromosoma, NREINAS);
            poblacion.add(cromosoma);
        }
        return poblacion;
    }

    /**
     * Metodo que llena un cromosoma (le asigna filas a las reinas)
     * @param cromosoma
     * @param NREINAS
     * @return
     */
    public static int[] llenaCromosoma(int[] cromosoma, int NREINAS){
        Random rand = new Random();
        int fila;
        int[] resultado = new int[cromosoma.length];
        for(int i = 0; i<cromosoma.length-1; i++){
            fila = rand.nextInt(NREINAS);
            while(contains(resultado, fila)) fila = rand.nextInt(NREINAS);
            resultado[i] = fila;
        }
        System.out.println(Arrays.toString(resultado));
        return resultado;
    }

    
    /**
     * Metodo que verifica si un numero ya esta en un arreglo dado
     * @param arr
     * @param key
     * @return
     */
    public static boolean contains(final int[] arr, final int key) {
        return Arrays.stream(arr).anyMatch(i -> i == key);
    }


}