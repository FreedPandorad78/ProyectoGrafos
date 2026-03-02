import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== EL RITUAL DE JOHLODEJVE ===");
        System.out.println("Ingrese los datos del grafo");
        System.out.println("Primera linea: n m");
        System.out.println("Siguientes m lineas: u v d c");
        System.out.println();

        // Leer cantidad de nodos y aristas
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        Grafo grafo = new Grafo(n);

        // Leer cada arista
        for (int i = 0; i < m; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            int d = scanner.nextInt();
            int c = scanner.nextInt();
            grafo.agregarArista(u, v, d, c);
        }

        // El brujo sale del nodo 0 y tiene que llegar al nodo n-1 (su guarida)
        int origen = 0;
        int destino = n - 1;

        // Ejecutar dijkstra para el camino mas corto
        Dijkstra dijkstra = new Dijkstra(grafo);
        Object[] resultadoDijkstra = dijkstra.ejecutar(origen, destino);
        List<Integer> caminoDijkstra = (List<Integer>) resultadoDijkstra[0];
        int distanciaDijkstra = (int) resultadoDijkstra[1];
        int victimasDijkstra = (int) resultadoDijkstra[2];

        // Ejecutar bellman-ford para el camino con mas victimas
        BellmanFord bellmanFord = new BellmanFord(grafo);
        Object[] resultadoBellman = bellmanFord.ejecutar(origen, destino);
        List<Integer> caminoBellman = (List<Integer>) resultadoBellman[0];
        int distanciaBellman = (int) resultadoBellman[1];
        int victimasBellman = (int) resultadoBellman[2];

        // Mostrar resultados
        System.out.println();
        System.out.println("=== RESULTADOS DEL RITUAL DE JOHLODEJVE ===");
        System.out.println();

        System.out.println("1. CAMINO MAS CORTO (DIJKSTRA)");
        System.out.println("--------------------------------------------");
        System.out.println("   Nodos:     " + caminoDijkstra);
        System.out.println("   Distancia: " + distanciaDijkstra);
        System.out.println("   Victimas:  " + victimasDijkstra);
        System.out.println();

        System.out.println("2. CAMINO DE MAS VICTIMAS (BELLMAN-FORD)");
        System.out.println("--------------------------------------------");
        System.out.println("   Nodos:     " + caminoBellman);
        System.out.println("   Distancia: " + distanciaBellman);
        System.out.println("   Victimas:  " + victimasBellman);
        System.out.println();

        // Tabla resumen
        System.out.println("============================================");
        System.out.println("              TABLA RESUMEN                 ");
        System.out.println("============================================");
        System.out.printf("%-15s | %-20s | %-9s | %-8s%n",
                "Algoritmo", "Camino", "Distancia", "Victimas");
        System.out.println("----------------|----------------------|-----------|----------");
        System.out.printf("%-15s | %-20s | %-9d | %-8d%n",
                "Dijkstra", caminoDijkstra.toString(), distanciaDijkstra, victimasDijkstra);
        System.out.printf("%-15s | %-20s | %-9d | %-8d%n",
                "Bellman-Ford", caminoBellman.toString(), distanciaBellman, victimasBellman);
        System.out.println("============================================");

        // Abrir la ventana con el grafo
        GrafoGUI gui = new GrafoGUI(grafo, caminoDijkstra, caminoBellman,
                distanciaDijkstra, victimasDijkstra,
                distanciaBellman, victimasBellman);
        gui.setVisible(true);

        scanner.close();
    }
}