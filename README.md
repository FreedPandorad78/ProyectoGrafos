# El Ritual de JohlodejVe - Proyecto de Grafos

## Miembros del grupo
- Juan Pablo Londoño
- David Orozco

## Descripcion
Programa en Java que resuelve el problema del Ritual de JohlodejVe usando dos algoritmos de grafos:

1. **Dijkstra** - Encuentra el camino mas corto (menor distancia) desde la aldea 0 hasta la guarida del brujo (nodo n-1)
2. **Bellman-Ford adaptado** - Encuentra el camino que maximiza la cantidad de victimas desde la aldea 0 hasta la guarida del brujo

El programa lee los datos desde consola y muestra los resultados tanto en texto como en una interfaz grafica que dibuja el grafo con los caminos resaltados.

## Como ejecutar
1. Abrir el proyecto en IntelliJ IDEA
2. Ejecutar la clase `Main.java`
3. Ingresar los datos en el formato:
```
n m
u v d c
u v d c
...
```
Donde:
- `n` = numero de nodos (aldeas)
- `m` = numero de aristas (caminos)
- `u` = nodo origen
- `v` = nodo destino
- `d` = distancia del camino
- `c` = victimas en el nodo destino

## Ejemplo de entrada
```
7 8
0 1 4 3
0 2 2 0
1 3 5 5
2 3 8 5
2 4 10 1
3 5 2 4
5 6 3 0
4 5 2 4
```

## Archivos del proyecto
- `Grafo.java` - Estructura del grafo con lista de adyacencia
- `Dijkstra.java` - Algoritmo de Dijkstra para camino mas corto
- `BellmanFord.java` - Algoritmo de Bellman-Ford adaptado para maximizar victimas
- `GrafoGUI.java` - Interfaz grafica que dibuja el grafo y los caminos
- `Main.java` - Clase principal que lee datos y ejecuta todo
