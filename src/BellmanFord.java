import java.util.*;

// Bellman-Ford adaptado para encontrar el camino que maximiza victimas
// La idea es que en vez de buscar la distancia minima, buscamos las victimas maximas
public class BellmanFord {

    private Grafo grafo;

    public BellmanFord(Grafo grafo) {
        this.grafo = grafo;
    }

    // Ejecuta el algoritmo y devuelve el camino, distancia y victimas
    public Object[] ejecutar(int origen, int destino) {
        int n = grafo.getNumNodos();
        List<Grafo.Arista> aristas = grafo.getTodasLasAristas();

        // En vez de distancia minima guardamos victimas maximas
        // Empieza en -infinito porque no hemos llegado a ningun nodo
        int[] maxVictimas = new int[n];
        Arrays.fill(maxVictimas, Integer.MIN_VALUE);
        // El nodo origen empieza con sus propias victimas
        maxVictimas[origen] = grafo.getVictimasEnNodo(origen);

        // Para reconstruir el camino al final
        int[] predecesor = new int[n];
        Arrays.fill(predecesor, -1);

        // Tambien guardamos la distancia acumulada del camino que elegimos
        int[] distanciaAcumulada = new int[n];
        Arrays.fill(distanciaAcumulada, 0);

        // Relajacion de aristas, se repite n-1 veces como dice el algoritmo
        for (int i = 0; i < n - 1; i++) {
            boolean huboCambio = false;
            for (Grafo.Arista arista : aristas) {
                int u = arista.origen;
                int v = arista.destino;

                // Solo si ya llegamos al nodo u
                if (maxVictimas[u] == Integer.MIN_VALUE) continue;

                // Ver cuantas victimas tendriamos si vamos a v por este camino
                int victimasNuevas = maxVictimas[u] + arista.victimas;

                // Si por este camino hay mas victimas, lo actualizamos
                if (victimasNuevas > maxVictimas[v]) {
                    maxVictimas[v] = victimasNuevas;
                    predecesor[v] = u;
                    distanciaAcumulada[v] = distanciaAcumulada[u] + arista.distancia;
                    huboCambio = true;
                }
            }
            // Si ya no cambia nada, no tiene sentido seguir
            if (!huboCambio) break;
        }

        // Armar el camino con los predecesores
        List<Integer> camino = new ArrayList<>();

        if (maxVictimas[destino] == Integer.MIN_VALUE) {
            return new Object[]{camino, -1, 0};
        }

        int nodo = destino;
        while (nodo != -1) {
            camino.add(nodo);
            nodo = predecesor[nodo];
        }
        Collections.reverse(camino);

        // Contar victimas reales del camino (solo la primera vez que visitamos cada nodo)
        int totalVictimas = 0;
        Set<Integer> visitados = new HashSet<>();
        for (int nodoVisitado : camino) {
            if (!visitados.contains(nodoVisitado)) {
                totalVictimas += grafo.getVictimasEnNodo(nodoVisitado);
                visitados.add(nodoVisitado);
            }
        }

        return new Object[]{camino, distanciaAcumulada[destino], totalVictimas};
    }
}