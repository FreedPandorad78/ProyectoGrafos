import java.util.*;

// Dijkstra para encontrar el camino mas corto desde un nodo origen hasta un destino
public class Dijkstra {

    private Grafo grafo;

    public Dijkstra(Grafo grafo) {
        this.grafo = grafo;
    }

    // Ejecuta el algoritmo y devuelve el camino, la distancia total y las victimas
    public Object[] ejecutar(int origen, int destino) {
        int n = grafo.getNumNodos();

        // Arreglo de distancias, al inicio en infinito
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[origen] = 0;

        // Para saber de donde venimos y poder armar el camino al final
        int[] predecesor = new int[n];
        Arrays.fill(predecesor, -1);

        // Para no visitar el mismo nodo dos veces
        boolean[] visitado = new boolean[n];

        // Cola de prioridad que ordena por distancia menor
        PriorityQueue<int[]> cola = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        cola.offer(new int[]{0, origen});

        while (!cola.isEmpty()) {
            int[] actual = cola.poll();
            int distActual = actual[0];
            int nodoActual = actual[1];

            // Si ya lo visitamos lo saltamos
            if (visitado[nodoActual]) continue;
            visitado[nodoActual] = true;

            // Si ya llegamos al destino paramos
            if (nodoActual == destino) break;

            // Recorrer los vecinos del nodo actual
            for (Grafo.Arista arista : grafo.getAdyacentes(nodoActual)) {
                int vecino = arista.destino;
                int nuevaDist = distActual + arista.distancia;

                // Si encontramos una distancia menor, actualizamos
                if (!visitado[vecino] && nuevaDist < dist[vecino]) {
                    dist[vecino] = nuevaDist;
                    predecesor[vecino] = nodoActual;
                    cola.offer(new int[]{nuevaDist, vecino});
                }
            }
        }

        // Armar el camino recorriendo los predecesores de atras para adelante
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
        Collections.reverse(camino);

        // Contar las victimas del camino, solo se cuentan una vez por nodo
        int totalVictimas = 0;
        boolean[] visitadosVictimas = new boolean[n];
        for (int n2 : camino) {
            if (!visitadosVictimas[n2]) {
                totalVictimas += grafo.getVictimasEnNodo(n2);
                visitadosVictimas[n2] = true;
            }
        }

        return new Object[]{camino, dist[destino], totalVictimas};
    }
}