# GA-N-Reinas
Algoritmo genético para resolver el problema de las N-Reinas

Pasos para resolver el problema
1.- Cromosoma.
    Abtraer el tablero junto con las reinas en un arreglo de tamaño n.
    En este caso el tablero sera de nxn y las reinas seran distribuidas de forma lineal sobre 
    las columnas; Resultando así en acomodos de este estilo (para n = 8 por ejemplo):
    [0,1,2,3,4,5,6,7] donde los numeros representan la fila en la que esta situada la reina. 
    Los cromosomas se generaran de manera aleatoria evitando que dos columnas tengan el mismo valor.
    Por ejemplo esto esta bien [1,5,7,4,6,0,2,3] y esto esta mal [1,2,3,3,4,5,7,0].

2.- Funcion de fitness
    "Una función de fitness debe ser capaz de determinar lo cerca que una solución incorrecta está de una correcta"
    Implementamos una funcion de fitness que nos permita contabilizar el numero de conflictos en diagonal (ya que evitamos conflictos de filas y columnas en la restriccion de los cromosomas).
    Entre más conflictos haya más erronea es la solucion y, por lo tanto para una solucion correcta el resultado de la funcion de fitness debe ser 0 (cero conflictos).
    En este caso se utiliza esta función:
    Sea la n-tupla de reinas [0,1,...i,...,j...,n], las reinas las reinas i,j tendran un conflicto en la diagonal si: i-Ri = j-Rj o i+Ri = j+Rj que se reduce a ||Ri - Rj|| = ||i - j||.
    Sumamos todos los conflictos posibles de un n-tupla.
    Esto quiere decir que si la por ejemplo tenemos cromosoma = [5,6,4,0,3,7,2,1] y tomamos a
    Ri = cromosoma[0] -> i = 5
    Rj = cromosoma[1] -> j = 6
    nos preguntamos, ¿tienen conflicto? pues en este caso si, por que ||0 - 1|| = ||6 - 5|| => True

3.- Nueva generacion
    Se seleccionan los n cromosomas mas aptos (menor numero de colisiones) y se llevan a la siguiente generacion.
    Se seleccionan los cromosomas a cruzarse y posteriormente a mutarse

4.- Se repite hasta que se llegue a la n-tuplas con 0 conflictos o se llegue al limite de generaciones impuesto.
    