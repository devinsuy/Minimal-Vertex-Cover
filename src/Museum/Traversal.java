package Museum;

import java.util.*;

public class Traversal {
    private ArrayList<Vertex> vertices;
    private Edge[] edges;
    private ArrayList<Edge> ed;
    private Edge[] srcEdges;
    private LinkedList<Vertex> cover;
    public Traversal(ArrayList<Vertex> v){
        vertices = v;
    }

    public Traversal(ArrayList<Vertex> v, Edge[] srcEdges, LinkedList<Vertex> c){
        vertices = v;
        this.srcEdges = srcEdges;
        this.cover = c;
        ed = new ArrayList<Edge>();
        generateEdges(c);
        if(checkCover()){
            System.out.println("IS A COVER!!");
        }
        else{
            System.out.println("NOT A COVER!!");
        }
    }

    public void generateEdges(LinkedList<Vertex> cover){
        Edge e;
        for(Vertex v : cover){
            for(Vertex adj : v.adj){
               e = new Edge(v,adj);
               if(!(edge_exists(e))){ ed.add(e); }
            }
        }
        edges = ed.toArray(new Edge[0]);
    }


    private boolean edge_exists(Edge e){
        for(Edge x : ed){
            if(x.equals(e)) { return true; } // There is already an existing edge between these two vertices
        }
        return false;
    }


    public boolean contains(Edge e){
        for(Edge a : edges){
            if(a.equals(e)){
                return true;
            }
        }
        return false;
    }

    public boolean checkCover(){
        System.out.println();
        System.out.println("NUMBER OF GUARDS: " + cover.size());
        if(edges.length == srcEdges.length){
            System.out.println("SAME LENGTH: " + edges.length);
            for(Edge e : srcEdges){
                if(!contains(e)){
                    return false;
                }
            }
        } else {
            System.out.println("EXPECTED: " + srcEdges.length + ", GOT: " + edges.length);
            System.out.println("NOT SAME LENGTH!!");
            return false;
        }
        return true;
    }

    public boolean checkVisited(){
        Vertex temp;
        boolean pass = true;
        for(int x = 0; x < vertices.size(); x++){
            temp = vertices.get(x);
            if(!temp.visited){
                System.out.println("VERTEX " + temp + " WAS NOT VISITED");
                pass = false;
                break;
            }
            else{
                temp.visited = false;
            }
        }
        //checkNum++;
        if(pass){
            //System.out.println("SUCCESSFUL CHECK STARTING @ Vertex " + checkNum);
            return true;
        }
        else{
            // System.out.println("CHECK FAILED @ Vertex " + checkNum);
            return false;
        }
    }

    public void explore(Vertex v){
        v.visited = true;
        if(v.adj.size() > 0) {
            for(Vertex e : v.adj){
                if(!e.visited){
                    explore(e);
                }
            }
        }
    }

    public void showVertices(){
        for(int t = 0; t < vertices.size(); t++) {
            System.out.println(vertices.get(t));
        }
    }

    public void checkRandom(int numChecks){
        int randIndex;
        Random rand = new Random();
        Vertex temp;
        boolean pass;
        for(int i = 0; i < numChecks; i++){
            randIndex = rand.nextInt(vertices.size());
            temp = vertices.get(randIndex);
            explore(temp);
            pass = checkVisited();
            if(pass){
                System.out.println("VERTEX " + temp + " PASS");
            }
            else{
                System.out.println("FAILED AT " + temp);
            }
        }
    }

//    public boolean traverseAll(Vertex v){
//        int vertexToCheck = 0;
//
//        while(vertexToCheck < 625){
//            if(vertexToCheck == v.vertexNumber){
//                vertexToCheck++;
//                System.out.println("INCREMENTING CHECK TO: " + vertexToCheck);
//            }
//            if(!explore(v,vertexToCheck)){
//                return false;
//            } else{
//                System.out.println("VERTEX #" + vertexToCheck + " REACHED FROM VERTEX " + v.vertexNumber);
//                vertexToCheck++;
//            }
//        }
//        return true;
//    }

    public void checkConnectivity(){
        Vertex temp;
        boolean passed = true;
        for(int x = 0; x < vertices.size(); x++){
            temp = vertices.get(x);
            //System.out.println("TEMP IS: " + temp);
            explore(temp);
            passed = checkVisited();
            if(!passed){
                break;
            }
            System.out.println("CHECKED " + temp);
            //System.out.println("END");
        }
        if(!passed){
            System.out.println("FAILED");
        }
        else{
            System.out.println("GRAPH IS CONNECTED");
        }
    }
}

//class edgeComp implements Comparator<Edge> {
//
//    @Override
//    public int compare(Edge a, Edge b) {
//        if(a.a.ID < b.a.ID){
//            return -1;
//        } else {
//            return 1;
//        }
//    }
//}
