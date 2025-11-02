package graph.utils;

public class Metrics {
    public long dfsCalls = 0;
    public long relaxations = 0;
    public long topoPushes = 0;
    private long startTime, endTime;

    public void start() { startTime = System.nanoTime(); }

    public void stop() { endTime = System.nanoTime(); }

    public double getElapsedMs() {
        return (endTime - startTime) / 1_000_000.0;
    }

    public void printSummary(String name) {
        System.out.println("--- Metrics for " + name + " ---");
        System.out.println("DFS calls: " + dfsCalls);
        System.out.println("Relaxations: " + relaxations);
        System.out.println("Topo pushes: " + topoPushes);
        System.out.printf("Elapsed time: %.3f ms%n", getElapsedMs());
        System.out.println();
    }
}
