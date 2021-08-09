import java.util.Arrays;

public class Cromosoma implements Comparable<Cromosoma>{
    private int[] tablero;
    private int colisiones, longituTablero;
    
    public Cromosoma(int[] tablero){
        this.tablero = tablero;
        this.longituTablero = tablero.length;
        this.colisiones = 0;
    }

    public int getLongitud(){
        return this.longituTablero;
    }

    public int[] getTablero(){
        return this.tablero;
    }

    public void setTablero(int[] tablero){
        this.tablero = tablero;
    }

    public int getColisiones(){
        return this.colisiones;
    }

    public void setColisiones(int n){
        this.colisiones = n;
    }
    
    public void imprime(){
        System.out.println(Arrays.toString(tablero) + " colisiones = " + this.colisiones);
    }

    @Override
    public int compareTo(Cromosoma o) {
        if(this.colisiones == o.colisiones) return 0; //Iguales
        if(this.colisiones > o.colisiones) return 1; //Mayor
        return -1; //Menor
    }

}
