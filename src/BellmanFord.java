import java.util.*;

/**
 * Implementación adaptada del algoritmo de Bellman-Ford.
 * En lugar de buscar el camino más corto, busca el camino que
 * MAXIMIZA la cantidad de víctimas recolectadas desde origen hasta destino.
 *
 * Adaptación: Se niegan los pesos de víctimas para convertir el problema
 * de maximización en uno de minimización (Bellman-Ford estándar minimiza).
 * Las víctimas de un nodo solo se cuentan la primera vez que se visita.
 */
public class BellmanFord {

    private Grafo grafo;

    public BellmanFord(Grafo grafo) {
        this.grafo = grafo;
    }

    /**
     * Ejecuta Bellman-Ford adaptado para maximizar víctimas.
     * @param origen  Nodo de partida
     * @param destino Nodo de llegada (guarida)
     * @return Un arreglo con: [0] = lista de nodos del camino,
     *         [1] = distancia total, [2] = víctimas recolectadas
     */
    public Object[] ejecutar(int origen, int destino) {
        int n = grafo.getNumNodos();
        List<Grafo.Arista> aristas = grafo.getTodasLasAristas();

        // maxVictimas[i] = máxima cantidad de víctimas acumuladas para llegar al nodo i
        int[] maxVictimas = new int[n];
        Arrays.fill(maxVictimas, Integer.MIN_VALUE);
        // El nodo origen tiene sus propias víctimas como valor inicial
        maxVictimas[origen] = grafo.getVictimasEnNodo(origen);

        // predecesor[i] = nodo anterior en el mejor camino hacia i
        int[] predecesor = new int[n];
        Arrays.fill(predecesor, -1);

        // distanciaAcumulada[i] = distancia total del camino elegido hasta el nodo i
        int[] distanciaAcumulada = new int[n];
        Arrays.fill(distanciaAcumulada, 0);

        // Relajación de aristas (n-1 iteraciones, como Bellman-Ford estándar)
        for (int i = 0; i < n - 1; i++) {
            boolean huboCambio = false;
            for (Grafo.Arista arista : aristas) {
                int u = arista.origen;
                int v = arista.destino;

                // Solo procesamos si el nodo origen ya fue alcanzado
                if (maxVictimas[u] == Integer.MIN_VALUE) continue;

                // Calcular las víctimas que se obtendrían al ir a v desde u
                int victimasNuevas = maxVictimas[u] + arista.victimas;

                // Si obtenemos MÁS víctimas por este camino, actualizamos
                if (victimasNuevas > maxVictimas[v]) {
                    maxVictimas[v] = victimasNuevas;
                    predecesor[v] = u;
                    distanciaAcumulada[v] = distanciaAcumulada[u] + arista.distancia;
                    huboCambio = true;
                }
            }
            // Si no hubo cambios, terminamos antes
            if (!huboCambio) break;
        }

        // Reconstruir el camino
        List<Integer> camino = new ArrayList<>();

        // Si no se puede llegar al destino
        if (maxVictimas[destino] == Integer.MIN_VALUE) {
            return new Object[]{camino, -1, 0};
        }

        int nodo = destino;
        while (nodo != -1) {
            camino.add(nodo);
            nodo = predecesor[nodo];
        }
        Collections.reverse(camino);

        // Recalcular víctimas del camino (solo primera visita a cada nodo)
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