import java.util.*;

public class DijkstraAlgorithm {

    static class Node implements Comparable<Node> {
        String city;
        int cost;

        Node(String city, int cost) {
            this.city = city;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.cost, other.cost);
        }
    }

    public static void main(String[] args) {
       
        Map<String, List<Node>> graph = new HashMap<>();
        graph.put("A", Arrays.asList(new Node("B", 4), new Node("C", 2)));
        graph.put("B", Arrays.asList(new Node("A", 4), new Node("C", 5), new Node("D", 10)));
        graph.put("C", Arrays.asList(new Node("A", 2), new Node("B", 5), new Node("D", 3)));
        graph.put("D", Arrays.asList(new Node("B", 10), new Node("C", 3)));

        Scanner scanner = new Scanner(System.in);

       
        System.out.print("Ciudad de inicio (en mayusculas): ");
        String start = scanner.nextLine();
        System.out.print("Ciudad de destino (en mayusculas): ");
        String end = scanner.nextLine();

        
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();

        for (String city : graph.keySet()) {
            distances.put(city, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        priorityQueue.add(new Node(start, 0));

        while (!priorityQueue.isEmpty()) {
            Node current = priorityQueue.poll();
            String currentCity = current.city;

            if (!graph.containsKey(currentCity)) continue;

            for (Node neighbor : graph.get(currentCity)) {
                int newDist = distances.get(currentCity) + neighbor.cost;
                if (newDist < distances.get(neighbor.city)) {
                    distances.put(neighbor.city, newDist);
                    previous.put(neighbor.city, currentCity);
                    priorityQueue.add(new Node(neighbor.city, newDist));
                }
            }
        }

        
        if (distances.get(end) == Integer.MAX_VALUE) {
            System.out.println("No hay conexión posible entre " + start + " y " + end);
        } else {
            System.out.println("Distancia mas corta: " + distances.get(end));
            System.out.print("Ruta: ");
            printPath(previous, end);
        }

        scanner.close();
    }

    private static void printPath(Map<String, String> previous, String city) {
        if (previous.get(city) != null) {
            printPath(previous, previous.get(city));
        }
        System.out.print(city + " ");
    }
}
