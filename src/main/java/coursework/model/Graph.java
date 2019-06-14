package coursework.model;

import java.util.*;

public class Graph {
    private int V;
    private int[][] matrix;
    private int[] vertices;
    private boolean[] visited;
    private int[] distances;
    private int[] predecessor;
    private Stack stack;
    private int count = 0;
    private List<Integer> longestPath = new ArrayList<>();
    private List<Integer> pathFromItoJ = new ArrayList<>();
    private int endPoint;

    public List<Integer> getLongestPath() {
        return longestPath;
    }




    public Graph(int V) {
        this.V = V;
        vertices = new int[V];
        visited = new boolean[V];
        predecessor = new int[V];
        distances = new int[V];
        matrix = new int[V][V];
        stack = new Stack();
        count = 0;
        for (int i = 0; i < V; i++) {
            addVertex(i);
            distances[i] = Integer.MIN_VALUE;
            predecessor[i] = -1;
        }
    }

    private void addVertex(int name) {
        vertices[name] = name;

    }


    public void addEdge(int source, int destination, int weight) {
        matrix[source][destination] = weight;
    }

    public List<Integer> findLongestPath(int source, boolean end) {

        pathFromItoJ.clear();

        if(end){
            invokeTopologicalSort();
            distances[source] = 0; // Initialize source with 0
            updateMaxDistanceForAllAdjVertices(); // for all nodes connected,
            printDistances(source, true);
            printPath(source, true);
            for (int i = 0; i < V; i++) {
                distances[i] = Integer.MIN_VALUE;
                predecessor[i] = -1;
                visited[i] = false;
            }


        }

        else   {
            invokeTopologicalSort();
            distances[source] = 0; // Initialize source with 0
            updateMaxDistanceForAllAdjVertices(); // for all nodes connected,
            printDistances(source, false);
            printPath(source, false);
            for (int i = 0; i < V; i++) {
                distances[i] = Integer.MIN_VALUE;
                predecessor[i] = -1;
                visited[i] = false;
            }
        }
        return this.longestPath;
    }





    private void printDistances(int source, boolean end) {


        System.out.println("Distances from source " + source + " are as follows: ");
        for (int to = 0; to < V; to++) {
            int distance = distances[to];
            System.out.print("from " + source + " to " + to + ": ");
            if (distance == Integer.MIN_VALUE) {
                System.out.println(" -Infinity ");
            } else {
                System.out.println(distance + " ");
            }
        }


    }

    public int[] getVertices() {
        return vertices;
    }

    private void printPath(int source, boolean end) {
        List<Integer> fromItoJ = new ArrayList<>();

        fromItoJ.add(source);
        List<Integer> list = new ArrayList<>();
        List<Integer> tempList = new ArrayList<>();
        int maxIndex = max(distances);
        int maxDistance = distances[maxIndex];
        for (int i = 0; i < V; i++) {
            if (distances[i] == Integer.MIN_VALUE) {
                System.out.println("No Path from " + source + " to " + i);
            } else if (i != source) {
                int from = predecessor[i];
                System.out.print("Path from " + source + " to " + i + ": ");


                while (from != source) {
                    System.out.print(from + " ");
                    if (maxIndex==i){
                        list.add(from);
                        System.out.println(" i = "+i+", endPoint = "+endPoint);
                    }


                    from = predecessor[from];
                }
                System.out.print(i + " ");
                if (maxIndex==i)
                    list.add(i);
                if(i==endPoint&&end){
                    fromItoJ.add(i);
                    System.out.println("end = "+endPoint);
                    System.out.println("мали добавити: (2) "+i);
                }
                System.out.println();

            }
        }


        if(list.size()>0) tempList.add(source);

        System.out.println("Маршрут: ");


        for (int i = list.size()-1; i >0; i--) {
            if(list.size()>0)
                tempList.add(list.get(i-1));
        }
        if(list.size()>0)
            tempList.add(list.get(list.size()-1));
        if (tempList.size()>count){
            longestPath = tempList;
            count = longestPath.size();
        }









    }

    public List<Integer> getPathFromItoJ() {
        return this.pathFromItoJ;
    }

    private void updateMaxDistanceForAllAdjVertices() {
        while (!stack.isEmpty()) {
            int from = (int)stack.pop();
            if (distances[from] != Integer.MIN_VALUE) {
                for (int adjacent = 0; adjacent < V; adjacent++) {
                    if (matrix[from][adjacent] != 0) {
                        if (distances[adjacent] < distances[from] + matrix[from][adjacent]) {
                            predecessor[adjacent] = from;
                            distances[adjacent] = distances[from] + matrix[from][adjacent];
                        }
                    }
                }
            }
        }


    }

    private void invokeTopologicalSort() {
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                dfs(i);
            }
        }
    }

    private void dfs(int source) {
        visited[source] = true;
        for (int adjacent = 0; adjacent < V; adjacent++) {
            if (matrix[source][adjacent] != 0 && !visited[adjacent]) {
                dfs(adjacent);
            }
        }

        stack.push(source);
    }


    public static int max(int[] array) {

        int maximum = array[0];
        int maxIndex = 0;

        for (int i = 0; i < array.length; i++) {
            if (maximum < array[i]){
                maximum = array[i];
                maxIndex = i;
            }

        }
        return maxIndex;
    }

    public int getEndPoint() {
        return this.endPoint;
    }

    public  void setEndPoint(int endPoint) {
        this.endPoint = endPoint;
    }

    public int[][] getMatrix() {
        return matrix;
    }
}