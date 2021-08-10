import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class NReinas {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int NIND = 10; //Numero de individuos (cromosomas)
        int NREINAS = 8; //Numero de reinas
        int MAXGEN = 5; //Maximo numero de generaciones
        int seleccion;
        System.out.print("Ingresa el numero de reinas o ingresa 0 para correr el programa con los parametros por defecto: ");
        seleccion = sc.nextInt();
        if (seleccion <= 0) algoritmoGeneticoNReinas(MAXGEN, NIND, NREINAS);
        else {
            NREINAS = seleccion;
            System.out.print("Ingresa el numero maximo de generaciones: ");
            MAXGEN = sc.nextInt();
            System.out.print("Ingresa el tamaño de la poblacion inicial (se generan aleatoriamente): ");
            NIND = sc.nextInt();
            algoritmoGeneticoNReinas(MAXGEN, NIND, NREINAS);
        }
    }

    public static void algoritmoGeneticoNReinas(int MAXGEN, int NIND, int NREINAS){
        int valorObjetivo = 0; //Numero de colisiones objetivo
        int generacionActual = 1;
        int porcentajeDeNuevosIndividuos = 70; //70 ya que se escogen 3 los tres mejores individuos y hay un maximo de 10
        int porcentajeDeMutacion = 2; //

        ArrayList<Cromosoma> poblacion = generaPoblacionInicial(NIND, NREINAS);
        evaluaPoblacion(poblacion);
        Cromosoma mejorFitness = poblacion.get(0); //Mejor cromosoma
        System.out.println("Población inicial");
        imprimePoblacion(poblacion);

        while(generacionActual < MAXGEN && mejorFitness.getColisiones() != valorObjetivo){
            System.out.println("------------Creando generacion # " + (generacionActual + 1) + "----------------------------");
            poblacion = formaNuevaPoblacion(poblacion, mejorFitness, porcentajeDeNuevosIndividuos, porcentajeDeMutacion, NREINAS);
            evaluaPoblacion(poblacion);
            mejorFitness = poblacion.get(0);
            System.out.print("Mejor individuo de la generación: ");
            mejorFitness.imprime();
            generacionActual++;
        }

        System.out.print("Mejor solucion encontrada en la generación #" + generacionActual + " es: ");
        mejorFitness.imprime();
        
    }

    /**
     * Metodo que genera una nueva poblacion
     * @param poblacion
     * @param ObjV
     * @param pIndividuos
     * @param pMutacion
     * @param NREINAS
     * @return
     */
    public static ArrayList<Cromosoma> formaNuevaPoblacion(ArrayList<Cromosoma> poblacion, Cromosoma ObjV, int pIndividuos, int pMutacion, int NREINAS){
        int agregaNMejores = 3; //Agrega los mejores n cromosomas a la siguiente generacion
        int numeroDeNuevosCromosomas = poblacion.size() * pIndividuos / 100; //Se creara este porcentaje de nuevos cromosomas
        int porcentajeDeMutacion = pMutacion; //TODO Preguntar como se debe hacer el porcentaje, de momento tomo pMutacion
        ArrayList<Cromosoma> mejoresCromosomas = agregaNMejores(poblacion, agregaNMejores);
        ArrayList<Cromosoma> hijos = cruce(mejoresCromosomas, numeroDeNuevosCromosomas);
        muta(hijos, porcentajeDeMutacion, NREINAS);
        mejoresCromosomas.addAll(hijos); //Unimos las dos listas
        return mejoresCromosomas;
    }

    /**
     * Metodo que muta los valores de las filas
     * @param poblacion
     * @param pMutacion
     * @param NREINAS
     */
    public static void muta(ArrayList<Cromosoma> poblacion, int pMutacion, int NREINAS){
        Random ran = new Random();
        for(Cromosoma cromosoma : poblacion){
            for(int i = 0; i<cromosoma.getTablero().length; i++)    
                if(ran.nextInt(pMutacion)==1) cromosoma.getTablero()[i] = ran.nextInt(NREINAS); //da 1/pMutacion probabilidades de ser cierto

        }
    }

    /**
     * Metodo que se encarga de cruzar los cromosomas y agregarlos a la poblacion para su posterior mutacion
     * @param poblacion
     * @param numeroDeNuevosCromosomas
     * @return arraylist de los hijos creados listos para su mutacion
     */
    public static ArrayList<Cromosoma> cruce(ArrayList<Cromosoma> poblacion, int numeroDeNuevosCromosomas){
        ArrayList<Cromosoma> resultado = new ArrayList<>();
        Random rand = new Random();
        Cromosoma progenitor1, progenitor2, hijo;
        for(int i=0; i<numeroDeNuevosCromosomas; i++){
            progenitor1 = poblacion.get(rand.nextInt(poblacion.size()));
            progenitor2 = poblacion.get(rand.nextInt(poblacion.size()));
            int[] mezcla = new int[progenitor1.getTablero().length];
            for(int j = 0; j<mezcla.length-1; j++){
                if(j == mezcla.length/2-1) mezcla[j] = progenitor2.getTablero()[j];
                else mezcla[j] = progenitor1.getTablero()[j];
            }
            hijo = new Cromosoma(mezcla);
            resultado.add(hijo);
        }
        return resultado;
    }

    /**
     * Metodo que agrega los primeros n cromosomas a la nueva poblacion
     * @param poblacion
     * @param nuevaPoblacion
     * @param n
     */
    public static ArrayList<Cromosoma> agregaNMejores(ArrayList<Cromosoma> poblacion, int n){
        ArrayList<Cromosoma> nuevaPoblacion = new ArrayList<>();
        if(n > poblacion.size() || n <= 0) return poblacion;
        for(int i = 0; i<n; i++){
            nuevaPoblacion.add(poblacion.get(i));
        }
        return nuevaPoblacion;
    }

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
            for(int j = i+1; j<longitud; j++){
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
