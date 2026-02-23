import java.util.*;

/**
 *
 * Lee un grafo desde la consola con el formato:
 *   n m
 *   u v d c  (m líneas)
 *
 * Donde:
 *   n = número de nodos (aldeas)
 *   m = número de aristas (caminos)
 *   u = nodo origen
 *   v = nodo destino
 *   d = distancia del camino
 *   c = víctimas en el nodo destino
 *
 * Ejecuta:
 *   1. Dijkstra: Camino más corto del nodo 0 al nodo n-1
 *   2. Bellman-Ford adaptado: Camino que maximiza víctimas del nodo 0 al nodo n-1
 *
 * Muestra resultados en consola y abre una GUI para visualizar el grafo.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // === LECTURA DE DATOS ===
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║   EL RITUAL DE JOHLODEJVE                ║");
        System.out.println("║   Ingrese los datos del grafo:           ║");
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.println("Formato: primera línea -> n m");
        System.out.println("Siguientes m líneas -> u v d c");
        System.out.println();

        // Leer número de nodos y aristas
        int n = scanner.nextInt(); // Número de nodos (aldeas)
        int m = scanner.nextInt(); // Número de aristas (caminos)

        // Crear el grafo
        Grafo grafo = new Grafo(n);

        // Leer cada arista
        for (int i = 0; i < m; i++) {
            int u = scanner.nextInt(); // Origen
            int v = scanner.nextInt(); // Destino
            int d = scanner.nextInt(); // Distancia
            int c = scanner.nextInt(); // Víctimas en v
            grafo.agregarArista(u, v, d, c);
        }

        // Nodo origen = 0, nodo destino (guarida) = n-1
        int origen = 0;
        int destino = n - 1;

        // EJECUTAR DIJKSTRA
        Dijkstra dijkstra = new Dijkstra(grafo);
        Object[] resultadoDijkstra = dijkstra.ejecutar(origen, destino);
        List<Integer> caminoDijkstra = (List<Integer>) resultadoDijkstra[0];
        int distanciaDijkstra = (int) resultadoDijkstra[1];
        int victimasDijkstra = (int) resultadoDijkstra[2];

        // EJECUTAR BELLMAN-FORD ADAPTADO
        BellmanFord bellmanFord = new BellmanFord(grafo);
        Object[] resultadoBellman = bellmanFord.ejecutar(origen, destino);
        List<Integer> caminoBellman = (List<Integer>) resultadoBellman[0];
        int distanciaBellman = (int) resultadoBellman[1];
        int victimasBellman = (int) resultadoBellman[2];

        // MOSTRAR RESULTADOS EN CONSOLA
        System.out.println();
        System.out.println("=== RESULTADOS DEL RITUAL DE JOHLODEJVE ===");
        System.out.println();

        // Resultado Dijkstra
        System.out.println("1. CAMINO MÁS CORTO (DIJKSTRA)");
        System.out.println("--------------------------------------------");
        System.out.println("   Nodos:     " + caminoDijkstra);
        System.out.println("   Distancia: " + distanciaDijkstra);
        System.out.println("   Víctimas:  " + victimasDijkstra);
        System.out.println();

        // Resultado Bellman-Ford
        System.out.println("2. CAMINO DE MÁS VÍCTIMAS (BELLMAN-FORD)");
        System.out.println("--------------------------------------------");
        System.out.println("   Nodos:     " + caminoBellman);
        System.out.println("   Distancia: " + distanciaBellman);
        System.out.println("   Víctimas:  " + victimasBellman);
        System.out.println();

        // Tabla resumen
        System.out.println("============================================");
        System.out.println("              TABLA RESUMEN                 ");
        System.out.println("============================================");
        System.out.printf("%-15s | %-20s | %-9s | %-8s%n",
                "Algoritmo", "Camino", "Distancia", "Víctimas");
        System.out.println("----------------|----------------------|-----------|----------");
        System.out.printf("%-15s | %-20s | %-9d | %-8d%n",
                "Dijkstra", caminoDijkstra.toString(), distanciaDijkstra, victimasDijkstra);
        System.out.printf("%-15s | %-20s | %-9d | %-8d%n",
                "Bellman-Ford", caminoBellman.toString(), distanciaBellman, victimasBellman);
        System.out.println("============================================");

        // === ABRIR LA INTERFAZ GRÁFICA ===
        javax.swing.SwingUtilities.invokeLater(() -> {
            GrafoGUI gui = new GrafoGUI(grafo, caminoDijkstra, caminoBellman,
                    distanciaDijkstra, victimasDijkstra,
                    distanciaBellman, victimasBellman);
            gui.setVisible(true);
        });

        scanner.close();
    }
}