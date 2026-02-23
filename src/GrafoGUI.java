import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;

/**
 * Interfaz gráfica que visualiza el grafo y resalta los caminos calculados.
 * Usa Java Swing
 *
 * - Nodos se dibujan como círculos con su número y víctimas.
 * - Aristas se dibujan como flechas con la distancia.
 * - El camino de Dijkstra se resalta en AZUL.
 * - El camino de Bellman-Ford se resalta en ROJO.
 */
public class GrafoGUI extends JFrame {

    private Grafo grafo;
    private List<Integer> caminoDijkstra;
    private List<Integer> caminoBellmanFord;
    private int distDijkstra;
    private int victDijkstra;
    private int distBellman;
    private int victBellman;

    // Posiciones calculadas para cada nodo (para dibujar)
    private int[][] posiciones;

    /**
     * Constructor de la GUI.
     */
    public GrafoGUI(Grafo grafo, List<Integer> caminoDijkstra, List<Integer> caminoBellmanFord,
                    int distDijkstra, int victDijkstra, int distBellman, int victBellman) {
        this.grafo = grafo;
        this.caminoDijkstra = caminoDijkstra;
        this.caminoBellmanFord = caminoBellmanFord;
        this.distDijkstra = distDijkstra;
        this.victDijkstra = victDijkstra;
        this.distBellman = distBellman;
        this.victBellman = victBellman;

        calcularPosiciones();

        setTitle("El Ritual de JohlodejVe - Visualización del Grafo");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    /**
     * Distribuye los nodos en un círculo para visualización clara.
     */
    private void calcularPosiciones() {
        int n = grafo.getNumNodos();
        posiciones = new int[n][2];
        int centroX = 400;
        int centroY = 300;
        int radio = 200;

        for (int i = 0; i < n; i++) {
            double angulo = 2 * Math.PI * i / n - Math.PI / 2;
            posiciones[i][0] = (int) (centroX + radio * Math.cos(angulo));
            posiciones[i][1] = (int) (centroY + radio * Math.sin(angulo));
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int n = grafo.getNumNodos();

        // === 1. Dibujar todas las aristas (gris claro) ===
        for (Grafo.Arista arista : grafo.getTodasLasAristas()) {
            int x1 = posiciones[arista.origen][0];
            int y1 = posiciones[arista.origen][1];
            int x2 = posiciones[arista.destino][0];
            int y2 = posiciones[arista.destino][1];

            g2d.setColor(new Color(200, 200, 200));
            g2d.setStroke(new BasicStroke(1.5f));
            dibujarFlecha(g2d, x1, y1, x2, y2);

            int mx = (x1 + x2) / 2;
            int my = (y1 + y2) / 2;
            g2d.setColor(Color.DARK_GRAY);
            g2d.setFont(new Font("Arial", Font.PLAIN, 11));
            g2d.drawString("d=" + arista.distancia, mx - 10, my - 5);
        }

        // === 2. Dibujar camino Dijkstra (AZUL) ===
        if (caminoDijkstra != null && caminoDijkstra.size() > 1) {
            g2d.setColor(new Color(0, 100, 255));
            g2d.setStroke(new BasicStroke(3.0f));
            for (int i = 0; i < caminoDijkstra.size() - 1; i++) {
                int de = caminoDijkstra.get(i);
                int a = caminoDijkstra.get(i + 1);
                dibujarFlecha(g2d, posiciones[de][0] - 3, posiciones[de][1] - 3,
                        posiciones[a][0] - 3, posiciones[a][1] - 3);
            }
        }

        // === 3. Dibujar camino Bellman-Ford (ROJO punteado) ===
        if (caminoBellmanFord != null && caminoBellmanFord.size() > 1) {
            g2d.setColor(new Color(220, 50, 50));
            g2d.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                    10.0f, new float[]{10.0f, 5.0f}, 0.0f));
            for (int i = 0; i < caminoBellmanFord.size() - 1; i++) {
                int de = caminoBellmanFord.get(i);
                int a = caminoBellmanFord.get(i + 1);
                dibujarFlecha(g2d, posiciones[de][0] + 3, posiciones[de][1] + 3,
                        posiciones[a][0] + 3, posiciones[a][1] + 3);
            }
        }

        // === 4. Dibujar nodos ===
        g2d.setStroke(new BasicStroke(2.0f));
        int radioNodo = 25;
        for (int i = 0; i < n; i++) {
            int x = posiciones[i][0];
            int y = posiciones[i][1];

            boolean enDijkstra = caminoDijkstra != null && caminoDijkstra.contains(i);
            boolean enBellman = caminoBellmanFord != null && caminoBellmanFord.contains(i);

            if (enDijkstra && enBellman) {
                g2d.setColor(new Color(180, 100, 255));
            } else if (enDijkstra) {
                g2d.setColor(new Color(100, 180, 255));
            } else if (enBellman) {
                g2d.setColor(new Color(255, 150, 150));
            } else {
                g2d.setColor(new Color(240, 240, 240));
            }

            g2d.fillOval(x - radioNodo, y - radioNodo, radioNodo * 2, radioNodo * 2);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(x - radioNodo, y - radioNodo, radioNodo * 2, radioNodo * 2);

            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            FontMetrics fm = g2d.getFontMetrics();
            String label = String.valueOf(i);
            g2d.drawString(label, x - fm.stringWidth(label) / 2, y + 5);

            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            String vicLabel = "v=" + grafo.getVictimasEnNodo(i);
            g2d.setColor(new Color(150, 0, 0));
            g2d.drawString(vicLabel, x - fm.stringWidth(vicLabel) / 2 - 2, y + radioNodo + 15);
        }

        // === 5. Leyenda ===
        g2d.setStroke(new BasicStroke(1.0f));
        int leyendaY = 580;
        g2d.setFont(new Font("Arial", Font.BOLD, 13));
        g2d.setColor(Color.BLACK);
        g2d.drawString("=== LEYENDA ===", 50, leyendaY);

        g2d.setColor(new Color(0, 100, 255));
        g2d.setStroke(new BasicStroke(3.0f));
        g2d.drawLine(50, leyendaY + 20, 90, leyendaY + 20);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        g2d.drawString("Dijkstra (Camino más corto) — Dist: " + distDijkstra
                + " | Víctimas: " + victDijkstra, 100, leyendaY + 25);

        g2d.setColor(new Color(220, 50, 50));
        g2d.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                10.0f, new float[]{10.0f, 5.0f}, 0.0f));
        g2d.drawLine(50, leyendaY + 45, 90, leyendaY + 45);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1.0f));
        g2d.drawString("Bellman-Ford (Más víctimas) — Dist: " + distBellman
                + " | Víctimas: " + victBellman, 100, leyendaY + 50);
    }

    /**
     * Dibuja una flecha desde (x1,y1) hasta (x2,y2).
     */
    private void dibujarFlecha(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        int radioNodo = 25;
        double angulo = Math.atan2(y2 - y1, x2 - x1);

        int xFin = (int) (x2 - radioNodo * Math.cos(angulo));
        int yFin = (int) (y2 - radioNodo * Math.sin(angulo));
        int xInicio = (int) (x1 + radioNodo * Math.cos(angulo));
        int yInicio = (int) (y1 + radioNodo * Math.sin(angulo));

        g2d.drawLine(xInicio, yInicio, xFin, yFin);

        int flechaTam = 12;
        double angFlecha = Math.toRadians(25);
        int xFlecha1 = (int) (xFin - flechaTam * Math.cos(angulo - angFlecha));
        int yFlecha1 = (int) (yFin - flechaTam * Math.sin(angulo - angFlecha));
        int xFlecha2 = (int) (xFin - flechaTam * Math.cos(angulo + angFlecha));
        int yFlecha2 = (int) (yFin - flechaTam * Math.sin(angulo + angFlecha));

        Polygon punta = new Polygon();
        punta.addPoint(xFin, yFin);
        punta.addPoint(xFlecha1, yFlecha1);
        punta.addPoint(xFlecha2, yFlecha2);

        Stroke strokeActual = g2d.getStroke();
        g2d.setStroke(new BasicStroke(1.0f));
        g2d.fillPolygon(punta);
        g2d.setStroke(strokeActual);
    }
}