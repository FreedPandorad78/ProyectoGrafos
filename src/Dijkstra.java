import java.util.*;

/**
 * Implementación del algoritmo de Dijkstra.
 * Encuentra el camino más corto (menor distancia total)
 * desde un nodo origen hasta un nodo destino.
 */
public class Dijkstra {

    private Grafo grafo;

    public Dijkstra(Grafo grafo) {
        this.grafo = grafo;
    }

    /**
     * Ejecuta Dijkstra desde el nodo origen hasta el nodo destino.
     * @param origen  Nodo de partida (aldea de inicio)
     * @param destino Nodo de llegada (guarida del brujo)
     * @return Un arreglo con: [0] = lista de nodos del camino,
     *         [1] = distancia total, [2] = víctimas recolectadas
     */
    public Object[] ejecutar(int origen, int destino) {
        int n = grafo.getNumNodos();

        // Arreglo de distancias mínimas; inicialmente infinito
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[origen] = 0;

        // Arreglo de predecesores para reconstruir el camino
        int[] predecesor = new int[n];
        Arrays.fill(predecesor, -1);

        // Arreglo para marcar nodos ya visitados
        boolean[] visitado = new boolean[n];

        // Cola de prioridad: [distancia, nodo] - ordena por distancia menor
        PriorityQueue<int[]> cola = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        cola.offer(new int[]{0, origen});

        // Bucle principal de Dijkstra
        while (!cola.isEmpty()) {
            int[] actual = cola.poll();
            int distActual = actual[0];
            int nodoActual = actual[1];

            // Si ya fue visitado, lo saltamos
            if (visitado[nodoActual]) continue;
            visitado[nodoActual] = true;

            // Si llegamos al destino, podemos parar
            if (nodoActual == destino) break;

            // Explorar todos los vecinos del nodo actual
            for (Grafo.Arista arista : grafo.getAdyacentes(nodoActual)) {
                int vecino = arista.destino;
                int nuevaDist = distActual + arista.distancia;

                // Si encontramos un camino más corto, actualizamos
                if (!visitado[vecino] && nuevaDist < dist[vecino]) {
                    dist[vecino] = nuevaDist;
                    predecesor[vecino] = nodoActual;
                    cola.offer(new int[]{nuevaDist, vecino});
                }
            }
        }

        // Reconstruir el camino desde destino hasta origen
        List<Integer> camino = new ArrayList<>();
        int nodo = destino;

        // Si no se puede llegar al destino
        if (dist[destino] == Integer.MAX_VALUE) {
            return new Object[]{camino, -1, 0};
        }

        while (nodo != -1) {
            camino.add(nodo);
            nodo = predecesor[nodo];
        }
        Collections.reverse(camino); // Invertir para tener origen -> destino

        // Calcular víctimas recolectadas en el camino
        // Las víctimas del nodo solo se suman la primera vez que se visita
        int totalVictimas = 0;
        Set<Integer> visitadosVictimas = new HashSet<>();
        for (int n2 : camino) {
            if (!visitadosVictimas.contains(n2)) {
                totalVictimas += grafo.getVictimasEnNodo(n2);
                visitadosVictimas.add(n2);
            }
        }

        return new Object[]{camino, dist[destino], totalVictimas};
    }
}