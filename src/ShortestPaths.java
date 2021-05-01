import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

public class ShortestPaths {
    private static final int V = 12500;
    private static final int NO_PARENT = -1;

    public static class Edge {
        public int vertex;
        public double weight;

        public Edge(int vertex, double weight) {
            this.vertex = vertex;
            this.weight = weight;
        }
    }

    public static String[] getRoute(HashMap<Integer, ArrayList<Edge>> graph, int start, int end) {
        if (start == end)
            return new String[] { "" + start };
        if (start < 0 || end < 0 || start >= V || end >= V)
            return new String[] { "Stop Id doesn't exist" };
        if (graph.get(start) == null || graph.get(end) == null)
            return new String[] { "Stop Id doesn't exist" };
        var list = dikjstra(graph, start);
        ArrayList<Integer> arrayList = new ArrayList<>();
        getPath(end, list, arrayList);
        String[] arr = new String[arrayList.size()];
        Integer[] b = arrayList.toArray(new Integer[arr.length]);
        for (int i = 0; i < b.length; i++)
            arr[i] = b[i] + "";
        return arr;
    }

    public static void getPath(int currentVertex, int[] parents, ArrayList<Integer> result) {
        if (currentVertex == NO_PARENT || currentVertex == parents[currentVertex])
            return;
        getPath(parents[currentVertex], parents, result);
        result.add(currentVertex);
    }

    public static HashMap<Integer, ArrayList<Edge>> generateGraph() {
        HashMap<Integer, ArrayList<Edge>> graph = new HashMap<>();
        int[] busStops = getStops();
        for (var busStop : busStops)
            graph.put(busStop, new ArrayList<Edge>());
        File transfers_txt = new File("../inputs/transfers.txt");
        try {
            Scanner sc = new Scanner(transfers_txt);
            sc.nextLine();
            while (sc.hasNext()) {
                String[] values = sc.nextLine().split(",");
                int startId = Integer.parseInt(values[0]);
                int endId = Integer.parseInt(values[1]);
                int transferType = Integer.parseInt(values[2]);

                double weight = transferType == 0 ? 2.0 : Integer.parseInt(values[3]) / 100.0;
                Edge e = new Edge(endId, weight);
                var edges = graph.get(startId);
                edges.add(e);
                graph.put(startId, edges);
            }
            sc.close();
            File stops_txt = new File("../inputs/stop_times.txt");
            sc = new Scanner(stops_txt);
            sc.nextLine();
            String values[] = sc.nextLine().split(",");
            int current_trip_id = Integer.parseInt(values[0]);
            int current_stop_id = Integer.parseInt(values[3]);
            while (sc.hasNext()) {
                values = sc.nextLine().split(",");
                int trip_id = Integer.parseInt(values[0]);
                int stop_id = Integer.parseInt(values[3]);
                if (current_trip_id == trip_id) {
                    Edge e = new Edge(stop_id, 1);
                    var list = graph.get(current_stop_id);
                    list.add(e);
                    graph.put(current_stop_id, list);
                }
                current_trip_id = trip_id;
                current_stop_id = stop_id;
            }
            sc.close();
        } catch (Exception e) {
            System.out.println(e);
            System.exit(1);
        }
        return graph;
    }

    public static int minDistance(double[] dist, boolean[] sptSet) {
        double min = Integer.MAX_VALUE;
        int min_index = -1;
        for (int v = 0; v < V; v++)
            if (sptSet[v] == false && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }
        return min_index;
    }

    private static double getweight(ArrayList<Edge> list, int vertex) {
        for (var e : list)
            if (e.vertex == vertex)
                return e.weight;
        return 0;
    }

    public static int[] dikjstra(HashMap<Integer, ArrayList<Edge>> graph, int startVertex) {
        int nVertices = V;

        // shortestDistances[i] will hold the
        // shortest distance from src to i
        double[] shortestDistances = new double[nVertices];

        // added[i] will true if vertex i is
        // included / in shortest path tree
        // or shortest distance from src to
        // i is finalized
        boolean[] added = new boolean[nVertices];

        // Initialize all distances as
        // INFINITE and added[] as false
        for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
            shortestDistances[vertexIndex] = Integer.MAX_VALUE;
            added[vertexIndex] = false;
        }

        // Distance of source vertex from
        // itself is always 0
        shortestDistances[startVertex] = 0;

        // Parent array to store shortest
        // path tree
        int[] parents = new int[nVertices];

        // The starting vertex does not
        // have a parent
        parents[startVertex] = NO_PARENT;

        // Find shortest path for all
        // vertices
        for (int i = 1; i < nVertices; i++) {

            // Pick the minimum distance vertex
            // from the set of vertices not yet
            // processed. nearestVertex is
            // always equal to startNode in
            // first iteration.
            int nearestVertex = -1;
            double shortestDistance = Double.MAX_VALUE;
            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
                if (!added[vertexIndex] && shortestDistances[vertexIndex] < shortestDistance) {
                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];
                }
            }
            // Mark the picked vertex as
            // processed
            added[nearestVertex] = true;

            // Update dist value of the
            // adjacent vertices of the
            // picked vertex.
            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
                var list = graph.get(nearestVertex);
                double edgeDistance = list == null ? 0 : getweight(graph.get(nearestVertex), vertexIndex);
                if (edgeDistance > 0.0 && ((shortestDistance + edgeDistance) < shortestDistances[vertexIndex])) {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = shortestDistance + edgeDistance;
                }
            }
        }
        return parents;
    }

    public static int[] getStops() {
        ArrayList<Integer> result = new ArrayList<>();
        File file = new File("../inputs/stops.txt");
        try {
            Scanner sc = new Scanner(file);
            sc.nextLine();
            int max = -1;
            while (sc.hasNext()) {
                int id = Integer.parseInt(sc.nextLine().split(",")[0]);
                if (id > max)
                    max = id;
                result.add(id);
            }
            sc.close();
        } catch (Exception e) {
            System.out.println(e);
            System.exit(1);
        }
        Integer[] a = result.toArray(new Integer[result.size()]);
        int[] b = new int[a.length];
        for (int i = 0; i < a.length; i++)
            b[i] = a[i];
        return b;
    }

    public static void main(String... args) {
        // 646, 381
        // 646, 378,379,381
        var graph = generateGraph();
        String[] res = getRoute(graph, 646, 381);
        for (var num : res)
            System.out.print(num + " ");
        System.out.println();
    }
}