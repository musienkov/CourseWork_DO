package coursework.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.copyValueOf;
import static java.lang.String.format;
@Service("serviceFW")
public class FloydWarshallService {
    final static int INF = 0;
    private  int V = 4;
    private static int maxDistance = 0;
    private static int maxDistanceIJ = 0;
    private static   List<Integer> longestPath = new ArrayList<>();
    private  List<Integer> longestPathIJ = new ArrayList<>();
    private  int startPoint = 0;
    private  int endPoint = 2;
    private static int count = 0;
    public List<Integer> floydWarshall(int graph[][], boolean end) {


        longestPath.clear();
        longestPathIJ.clear();
        maxDistance = 0;
        maxDistanceIJ = 0;
       // int[][] negativeGraph = createNegativeWeights(graph);
        int dist[][] = new int[V][V];

        int i, j, k;
        int[][] next = new int[V][V];
        for (int m = 0; m < next.length; m++) {
            for (int n = 0; n < next.length; n++)
                if (m != n)
                    next[m][n] = n + 1;
        }

        for (i = 0; i < V; i++){
            for (j = 0; j < V; j++){
               // dist[i][j] = negativeGraph[i][j];
                 dist[i][j] =graph[i][j];
            }

        }

        for (k = 0; k < V; k++)
        {
            for (i = 0; i < V; i++)
            {
                for (j = 0; j < V; j++)
                {
                    if(dist[i][k]!=0&&dist[k][j]!=0) {
                        if (dist[i][k] + dist[k][j] > dist[i][j]){
                            dist[i][j] = dist[i][k] + dist[k][j];
                            next[i][j] = next[i][k];
                        }
                    }
                }
            }

        }


        printSolution(dist);
        longestPath= printResult(dist,next, end);
        return longestPath;
    }

    public  List<Integer> getLongestPathIJ() {
        return longestPathIJ;
    }

    public void setLongestPathIJ(List<Integer> longestPathIJ) {
        this.longestPathIJ = longestPathIJ;
    }

    public  int getStart() {
        return startPoint;
    }

    public  void setStart(int start) {
        this.startPoint = start;
    }

    public int getEnd() {
        return this.endPoint;
    }

    public void setEnd(int end) {
        this.endPoint = end;
    }

    void printSolution(int dist[][])
    {
        System.out.println("The following matrix shows the shortest "+
                "distances between every pair of vertices");
        for (int i=0; i<V; ++i)
        {
            for (int j=0; j<V; ++j)
            {
                if (dist[i][j]==INF)
                    System.out.print("INF ");
                else
                    System.out.print(dist[i][j]+"   ");
                if(dist[i][j]>maxDistance){
                    maxDistance = dist[i][j];
                }

                if(i == startPoint && j == endPoint && dist[i][j] > maxDistanceIJ){
                    maxDistanceIJ = dist[i][j];
                }
            }
            System.out.println();

        }
    }

    public  int getStartPoint() {
        return this.startPoint;
    }

    public void setStartPoint(int startPoint) {
        this.startPoint = startPoint;
    }

    public  int getEndPoint() {
        return this.endPoint;
    }

    public  void setEndPoint(int endPoint) {
        this.endPoint = endPoint;
    }

    List<Integer> printResult(int[][] dist, int[][] next, boolean end) {
        List<Integer> tempList = new ArrayList<>();
        List<Integer> tempListIJ = new ArrayList<>();
        System.out.println("pair     dist    path");
        for (int i = 0; i < next.length; i++) {
            for (int j = 0; j < next.length; j++) {
                if (i != j &&dist[i][j]!=INF) {
                    int u = i+1;
                    int v = j+1;
                    String path = format("%d -> %d    %2d     %s", u-1, v-1,
                            dist[i][j], u-1);
                    if(dist[i][j]>=maxDistance) {
                        tempList.add(u - 1);

                    }

                    if(i == startPoint && j == endPoint && dist[i][j] >= maxDistanceIJ && end){
                        tempListIJ.add(u-1);
                    }

                    do {
                        u = next[u-1 ][v -1];
                        path += " -> " + (u-1);
                        if(dist[i][j]>=maxDistance) {
                            tempList.add(u - 1);
                        }

                        if(dist[i][j]>=maxDistanceIJ&&i == startPoint && j == endPoint && end) {
                            tempListIJ.add(u - 1);
                        }
                    } while (u != v);
                    System.out.print(path);
                    System.out.println();


                }

            }
        }

            for (int k = 0; k < maxDistanceIJ + 1; k++) {
                if(tempListIJ.size()>0)
                longestPathIJ.add(tempListIJ.get(k));
            }
            System.out.println("path from "+startPoint+" to "+endPoint+" = "+ longestPathIJ.toString());



            for (int k = 0; k < maxDistance + 1; k++) {
                if(tempList.size()>0)
                longestPath.add(tempList.get(k));
            }


        System.out.println(longestPath.toString());
        return longestPath;
    }


    public static List<Integer> getLongestPath() {
        return longestPath;
    }


    public  int getV() {
        return V;
    }

    public  void setV(int v) {
        V = v;
    }

    public static int[][] createNegativeWeights(int[][] matrix){

        for (int i = 0; i <matrix.length ; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j]!=0 && i!=j){
                    matrix[i][j]*=-1;
                }
            }
        }
        return matrix;
    }

}
