import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GraphLoader {
    public static class Edge {
        public int to;
        public double w;
        public Edge(int to, double w) { this.to = to; this.w = w; }
    }

    private List<List<Edge>> graph;
    private int source;

    public GraphLoader(String filename) {
        graph = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null)
                lines.add(line.trim());
            br.close();

            // Ручное чтение n и source
            int n = 0;
            for (String l : lines)
                if (l.contains("\"n\""))
                    n = Integer.parseInt(l.replaceAll("[^0-9]", ""));
            for (int i = 0; i < n; i++)
                graph.add(new ArrayList<>());

            for (String l : lines) {
                if (l.contains("\"source\""))
                    source = Integer.parseInt(l.replaceAll("[^0-9]", ""));
                if (l.contains("\"u\"")) {
                    int u = Integer.parseInt(l.replaceAll(".*\"u\"\\s*:\\s*(\\d+).*", "$1"));
                    int v = Integer.parseInt(l.replaceAll(".*\"v\"\\s*:\\s*(\\d+).*", "$1"));
                    double w = Double.parseDouble(l.replaceAll(".*\"w\"\\s*:\\s*(\\d+).*", "$1"));
                    graph.get(u).add(new Edge(v, w));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<List<Edge>> getGraph() { return graph; }
    public int getSource() { return source; }
}
