import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class NReinas {
    public static void main(String[] args) {
        int NIND = 10; //Numero de individuos (cromosomas)
        int NREINAS = 8; //Numero de reinas
        final int MAXGEN = 10; //Maximo numero de generaciones
        algoritmoGeneticoNReinas(MAXGEN, NIND, NREINAS);
    }

    public static void algoritmoGeneticoNReinas(int MAXGEN, int NIND, int NREINAS){
        int valorObjetivo = 0; //Numero de colisiones objetivo
        int generacionActual = 0;
        int porcentajeDeNuevosIndividuos = 70; //70 ya que se escogen 3 los tres mejores individuos y hay un maximo de 10
        int porcentajeDeMutacion = 30; //???????????????

        int fitnessMedio = 0;
        Cromosoma mejorIndividuo = null;

        ArrayList<Cromosoma> poblacion = generaPoblacionInicial(NIND, NREINAS);
        ArrayList<Cromosoma> mejoresPoblacionInicial = evaluaPoblacion(poblacion);
        Cromosoma mejorFitness = mejoresPoblacionInicial.get(0); //Mejor cromosoma

        while(generacionActual < MAXGEN && mejorFitness.getColisiones() != valorObjetivo){
            poblacion = formaNuevaPoblacion(poblacion, mejorFitness, porcentajeDeNuevosIndividuos, porcentajeDeMutacion);
            generacionActual++;
        }
        
    }

    public static ArrayList<Cromosoma> formaNuevaPoblacion(ArrayList<Cromosoma> poblacion, Cromosoma ObjV, int pIndividuos, int pMutacion){
        
        return null;
    }

    // /**
    //  * Evalua toda la poblacion y decide cuales cromosomas iran a la siguiente generacion
    //  * @param poblacion
    //  * @return candidatos Regresa los mejores tres candidatos a solucion
    //  */
    // public static ArrayList<Cromosoma> evaluaPoblacion(ArrayList<Cromosoma> poblacion){
    //     ArrayList<Cromosoma> candidatos = new ArrayList<>();
    //     for(Cromosoma cromosoma : poblacion) evaluaCromosoma(cromosoma);
    //     Collections.sort(poblacion); //Ordenamos la poblacion de menor a mayor numero de colisiones
    //     //TODO Hacer dinamico, regresa a los tres mejores candidatos a solucion
    //     candidatos.add(poblacion.get(0));
    //     candidatos.add(poblacion.get(1));
    //     candidatos.add(poblacion.get(2));
    //     return candidatos;
    // }

    /**
     * Evalua toda la poblacion y ordena de menor a mayor numero de colisiones la poblacion
     * @param poblacion
     * @return candidatos Regresa los mejores tres candidatos a solucion
     */
    public static void evaluaPoblacion(ArrayList<Cromosoma> poblacion){
        for(Cromosoma cromosoma : poblacion) evaluaCromosoma(cromosoma);
        Collections.sort(poblacion); //Ordenamos la poblacion de menor a mayor numero de colisiones
    }

    /**
     * Metodo que se encarga de contar y actualizar las colisiones en un cromosoma
     * @param cromosoma
     */
    public static void evaluaCromosoma(Cromosoma cromosoma){
        int colisionesTotales = 0, longitud = cromosoma.getTablero().length-1;
        for(int i = 0; i<longitud; i++){
            for(int j = 0; j<longitud; j++){
                boolean colision = (i-j) == (cromosoma.getTablero()[i]-cromosoma.getTablero()[j]); //True si existe una colision
                if(colision) colisionesTotales++;
            }
        }
        cromosoma.setColisiones(colisionesTotales);
    }

    /**
     * Metodo que genera la poblacion inicial de forma aleatoria 
     * restringe que no haya valores repetidos
     * @param NIND Numero de individuos por generacion
     * @param NREINAS Numero de reinas 
     * @return Poblacion inicial
     */
    public static ArrayList<Cromosoma> generaPoblacionInicial(int NIND, int NREINAS){
        ArrayList<Cromosoma> poblacion = new ArrayList<>();
        for(int i = 0; i<NIND; i++){
            Cromosoma cromosoma = new Cromosoma(new int[NREINAS]);
            llenaCromosoma(cromosoma, NREINAS);
            poblacion.add(cromosoma);
            // cromosoma.imprime();
        }
        return poblacion;
    }

    /**
     * Metodo que llena un cromosoma (le asigna filas a las reinas)
     * @param cromosoma
     * @param NREINAS
     * @return
     */
    public static void llenaCromosoma(Cromosoma cromosoma, int NREINAS){
        Random rand = new Random();
        int fila;
        int[] resultado = new int[cromosoma.getTablero().length];
        for(int i = 0; i<cromosoma.getTablero().length-1; i++){
            fila = rand.nextInt(NREINAS);
            while(contains(resultado, fila)) fila = rand.nextInt(NREINAS);
            resultado[i] = fila;
        }
        cromosoma.setTablero(resultado);
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

    public static void imprimePoblacion(ArrayList<Cromosoma> poblacion){
        for(Cromosoma a : poblacion){
            a.imprime();
        }
    }

}