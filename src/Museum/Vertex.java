package Museum;

import java.util.ArrayList;

public class Vertex {
    protected int ID;
    protected ArrayList<Vertex> adj;
    boolean visited;
    int color = -1;

    public Vertex(int ID){
        adj = new ArrayList<Vertex>();
        this.ID = ID;
    }

    public void addEdge(Vertex v){
        adj.add(v);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Vertex #" + ID + " has edge(s) to: #");
        Vertex temp;
        for(int i = 0; i < adj.size(); i++){
            temp = adj.get(i);
            str.append(temp.ID);
            if((i+1) < adj.size()){
                str.append(", ");
            }
        }
        return str.toString();
    }
}
