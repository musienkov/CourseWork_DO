package coursework.service;


import coursework.model.Graph;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("graphService")
public class GraphService {
    private Graph graph;

    public Graph getGraph() {
        return graph;
    }
    public List<Integer> getVertices() {

        List<Integer> verticesList = new ArrayList<>();
        int[] vertices = graph.getVertices();
        for (int i = 0; i <vertices.length ; i++) {
            verticesList.add(vertices[i]);
        }
        return verticesList;
    }


    public void setEndPoint(int endPoint){
        this.graph.setEndPoint(endPoint);
    }
    public int getEndPoint(){
        return this.graph.getEndPoint();
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }
}
