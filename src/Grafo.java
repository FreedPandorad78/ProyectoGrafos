import java.util.ArrayList;
import java.util.List;

// Clase para representar el grafo con lista de adyacencia
public class Grafo {

    // Cada arista tiene origen, destino, distancia y victimas
    public static class Arista {
        int origen;
        int destino;
        int distancia;
        int victimas;

        public Arista(int origen, int destino, int distancia, int victimas) {
            this.origen = origen;
            this.destino = destino;
            this.distancia = distancia;
            this.victimas = victimas;
        }
    }

    private int numNodos;
    private List<List<Arista>> listaAdyacencia;
    private List<Arista> todasLasAristas; // esta lista la usa bellman-ford porque necesita recorrer todas las aristas
    private int[] victimasEnNodo;

    // Constructor, recibe cuantos nodos tiene el grafo
    public Grafo(int numNodos) {
        this.numNodos = numNodos;
        this.listaAdyacencia = new ArrayList<>();
        this.todasLasAristas = new ArrayList<>();
        this.victimasEnNodo = new int[numNodos];

        for (int i = 0; i < numNodos; i++) {
            listaAdyacencia.add(new ArrayList<>());
        }
    }

    // Agregar una arista al grafo con los datos u, v, d, c
    public void agregarArista(int u, int v, int d, int c) {
        Arista arista = new Arista(u, v, d, c);
        listaAdyacencia.get(u).add(arista);
        todasLasAristas.add(arista);
        // Guardamos las victimas del nodo destino
        victimasEnNodo[v] = c;
    }

    // Getters para acceder a los datos desde las otras clases

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