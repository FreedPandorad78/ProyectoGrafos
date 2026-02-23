import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un grafo dirigido usando lista de adyacencia.
 * Cada nodo (aldea) tiene una lista de aristas que lo conectan a otros nodos.
 * Cada nodo también almacena la cantidad de víctimas disponibles.
 */
public class Grafo {

    // Clase interna que representa una arista del grafo
    public static class Arista {
        int origen;      // Nodo de origen
        int destino;     // Nodo de destino
        int distancia;   // Peso/distancia del camino
        int victimas;    // Víctimas en el nodo destino

        public Arista(int origen, int destino, int distancia, int victimas) {
            this.origen = origen;
            this.destino = destino;
            this.distancia = distancia;
            this.victimas = victimas;
        }
    }

    private int numNodos;                          // Número total de nodos (aldeas)
    private List<List<Arista>> listaAdyacencia;    // Lista de adyacencia
    private List<Arista> todasLasAristas;           // Lista plana de todas las aristas (para Bellman-Ford)
    private int[] victimasEnNodo;                   // Víctimas disponibles en cada nodo

    /**
     * Constructor: inicializa el grafo con n nodos.
     * @param numNodos Número total de aldeas.
     */
    public Grafo(int numNodos) {
        this.numNodos = numNodos;
        this.listaAdyacencia = new ArrayList<>();
        this.todasLasAristas = new ArrayList<>();
        this.victimasEnNodo = new int[numNodos];

        // Crear una lista vacía de adyacencia para cada nodo
        for (int i = 0; i < numNodos; i++) {
            listaAdyacencia.add(new ArrayList<>());
        }
    }

    /**
     * Agrega una arista dirigida al grafo.
     * @param u Nodo origen
     * @param v Nodo destino
     * @param d Distancia entre u y v
     * @param c Víctimas en el nodo v
     */
    public void agregarArista(int u, int v, int d, int c) {
        Arista arista = new Arista(u, v, d, c);
        listaAdyacencia.get(u).add(arista);
        todasLasAristas.add(arista);
        // Registrar las víctimas del nodo destino
        // (se actualiza con el último valor recibido, según el enunciado)
        victimasEnNodo[v] = c;
    }

    // === Getters ===

    public int getNumNodos() {
        return numNodos;
    }

    public List<Arista> getAdyacentes(int nodo) {
        return listaAdyacencia.get(nodo);
    }

    public List<Arista> getTodasLasAristas() {
        return todasLasAristas;
    }

    public int getVictimasEnNodo(int nodo) {
        return victimasEnNodo[nodo];
    }

    public List<List<Arista>> getListaAdyacencia() {
        return listaAdyacencia;
    }
}