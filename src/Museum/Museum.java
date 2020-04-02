package Museum;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Museum {
    // Each Vertex is stored at the same index as their ID
    protected ArrayList<Vertex> vertices;
    private ArrayList<Edge> edges;
    private LinkedList<Vertex> cover;
    private Edge[] edg;
    private String inputFile;
    protected Graph g;
    protected Random rand;
//    private static int rollBackCount = 0;
    private LinkedList<Vertex> finalCover;

    private ArrayList<Vertex> L;
    private ArrayList<Vertex> R;

    public Museum(String inputDirectory){
        inputFile = inputDirectory;
        vertices = new ArrayList<Vertex>();
        cover = new LinkedList<Vertex>();
        edges = new ArrayList<Edge>();
        rand = new Random();
        L = new ArrayList<Vertex>();
        R = new ArrayList<Vertex>();

        finalCover = new LinkedList<Vertex>();
        g = initializeMuseum();
        find_min_cover();
//        test();
    }

//    // TRUE is LEFT
//    public void partition(ArrayList<Vertex> vertices){
//        color(vertices);
//        for(Vertex v : vertices){
//            if(v.color == 1){
//               L.add(v);
//            }
//            else if (v.color == 0){
//                R.add(v);
//            }
//            else{
//                System.out.println("UNLABELED: " + v);
//            }
//        }
//        Random rand = new Random();
//        Vertex v = L.get(rand.nextInt(L.size()));
////        System.out.println(v.color + ": " + v);
////        for(Vertex t : v.adj){
////            System.out.println(t.color);
////        }
//    }
//
//    public void color(ArrayList<Vertex> vertices) {
//        Vertex temp = vertices.get(0);
//        temp.visited = true;
//        temp.color = 0;
//
//        Queue<Vertex> q = new LinkedList<Vertex>();
//        q.add(temp);
//        while (!q.isEmpty()) {
//            temp = q.poll();
//            for (Vertex adj : temp.adj) {
//                if (!adj.visited) {
//                    if(temp.color == 1){
//                        adj.color = 0;
//                    }
//                    else if (temp.color == 0){
//                        adj.color = 1;
//                    }
//                    adj.visited = true;
//                }
//                q.add(adj);
//            }
//        }
//    }


    private boolean edge_exists(Edge e){
        for(Edge x : edges){
            if(x.equals(e)) { return true; } // There is already an existing edge between these two vertices
        }
        return false;
    }

    private boolean edge_exists(Edge e, ArrayList<Edge> edges){
        for(Edge x : edges){
            if(x.equals(e)) { return true; } // There is already an existing edge between these two vertices
        }
        return false;
    }

    // Creates all the undirected edges, skipping over duplicates
    private void createEdges(){
        Vertex temp, tempNeighbor;
        Edge e;
        for(int i = 0; i < vertices.size(); i++){
            temp = vertices.get(i);
            for(int j = 0; j < temp.adj.size(); j++){
                tempNeighbor = temp.adj.get(j);
                e = new Edge(temp, tempNeighbor);
                if(!(edge_exists(e))){ edges.add(e); }
            }
        }
    }

    private ArrayList<Edge> createEdges(ArrayList<Vertex> vertices){
        Vertex temp, tempNeighbor;
        Edge e;
        ArrayList<Edge> edges = new ArrayList<Edge>();
        for(int i = 0; i < vertices.size(); i++){
            temp = vertices.get(i);
            for(int j = 0; j < temp.adj.size(); j++){
                tempNeighbor = temp.adj.get(j);
                e = new Edge(temp, tempNeighbor);
                if(!(edge_exists(e,edges))){ edges.add(e);
                    System.out.println("ADDED: " + e);}
                else{
                    System.out.println("NOT ADDING: " + e);
                }
            }
        }
        return edges;
    }

    public void test() {
        ArrayList<Vertex> tVert = new ArrayList<Vertex>();
        for (int i = 0; i < 6; i++) {
            tVert.add(new Vertex(i));
        }
        Vertex temp;

        temp = tVert.get(0);
        temp.addEdge(tVert.get(1));
        temp.addEdge(tVert.get(3));

        temp = tVert.get(1);
        temp.addEdge(tVert.get(0));
        temp.addEdge(tVert.get(2));
        temp.addEdge(tVert.get(3));
        temp.addEdge(tVert.get(4));

        temp = tVert.get(2);
        temp.addEdge(tVert.get(1));
        temp.addEdge(tVert.get(5));
        temp.addEdge(tVert.get(4));

        temp = tVert.get(3);
        temp.addEdge(tVert.get(0));
        temp.addEdge(tVert.get(4));
        temp.addEdge(tVert.get(1));

        temp = tVert.get(4);
        temp.addEdge(tVert.get(3));
        temp.addEdge(tVert.get(5));
        temp.addEdge(tVert.get(1));
        temp.addEdge(tVert.get(2));

        temp = tVert.get(5);
        temp.addEdge(tVert.get(2));
        temp.addEdge(tVert.get(4));

        ArrayList<Edge> tEdges = createEdges(tVert);
        System.out.println(tEdges.size());
        System.out.println(tEdges);
        LinkedList<Vertex> tCover = new LinkedList<Vertex>();
        Graph tGraph = new Graph(tVert, tEdges.toArray(new Edge[0]));

        this.vertices = tVert;
        this.edges = tEdges;
        tCover = find_vertex_cover(tCover, 4);

        System.out.println(tCover.size());
        System.out.println(tCover);

        Traversal t = new Traversal(tVert, tEdges.toArray(new Edge[0]), finalCover);
//        writeSolution(tCover);
    }

    /**
     * Parses input file to create all the edges
     * between the vertices it specifies
     */
    private Graph initializeMuseum(){
        initializeVertices();
        try{
            Scanner reader = new Scanner(new File(inputFile));
            String currentLine, vertexID;
            String[] edges;
            reader.useDelimiter("x");

            while(reader.hasNext()){
                currentLine = reader.next();
                vertexID = currentLine.substring(0,(currentLine.indexOf(":")));
                edges = (currentLine.substring(currentLine.indexOf(":")+1)).split(",");
                for(String s : edges){
                    vertices.get(Integer.valueOf(vertexID)).addEdge(
                            vertices.get(Integer.valueOf(s)));
                }
            }
            reader.close();
        }
        catch(IOException io){
            io.printStackTrace();
        }
        createEdges();
        edg = edges.toArray(new Edge[0]);
        return new Graph(vertices, edg);
    }

    /**
     * Finds the number of vertices needed by identifying the largest Vertex ID
     * within graph.txt, then populates ArrayList of vertices with this number
     */
    private void initializeVertices(){
        String currentLine;
        int numVertices = 0;
        try{
            Scanner reader = new Scanner(new File(inputFile));
            reader.useDelimiter(":");

            while(reader.hasNext()){
                currentLine = reader.next();
                if(currentLine.length() > 2){
                    currentLine = currentLine.substring(currentLine.indexOf('x') + 1);
                }
                try{
                    if(Integer.valueOf(currentLine) > numVertices){
                        numVertices = Integer.valueOf(currentLine);
                    }
                }
                // Only throws when currentLine is a blank, simply move on
                catch(NumberFormatException nfe){}
            }
            reader.close();
        }
        catch(IOException io){
            io.printStackTrace();
        }
        numVertices += 1; // Accounts for Vertex #0
        for(int i = 0; i < numVertices; i++){
            vertices.add(new Vertex(i));
        }
    }

    public void loadCover(){
        try{
            String vertexID;
            Scanner reader = new Scanner(new File("cover.txt"));
            reader.useDelimiter("x");
            while(reader.hasNext()){
                vertexID = reader.next();
                cover.add(vertices.get(Integer.valueOf(vertexID)));
            }
            reader.close();
            for(Vertex v : cover){
                System.out.println(v);
            }
        }
        catch(IOException io){ io.printStackTrace(); }

    }

    public LinkedList<Vertex> find_vertex_cover(LinkedList<Vertex> cover, int maxGuards){
        if((cover.size() == maxGuards) && (Graph.endIndex > 0)){ // Constraint
            Graph.q.remove(); // Prepares for rollback of edges
            cover.removeLast();
            return new LinkedList<Vertex>(); // Returns size 0
        }
        if(Graph.endIndex == 0){ // Base case
            for(Vertex v : cover){ finalCover.addLast(v); } // Copies over solution before exiting
            writeSolution(finalCover);
            Traversal t = new Traversal(vertices, edges.toArray(new Edge[0]), finalCover);
            System.exit(0);
            return cover;
        }
        Edge randEdge = Graph.edges[rand.nextInt(Graph.endIndex)];

        Graph.remove_incident_edges(randEdge.a.ID);
        cover.addLast(randEdge.a);
        if(find_vertex_cover(cover, maxGuards).size() != 0){
            return find_vertex_cover(cover, maxGuards);
        }

        // If .A failed, undo changes made
        Graph.rollBack();

        // Removes edges incident to the other vertex of our edge this time
        Graph.remove_incident_edges(randEdge.b.ID);
        cover.addLast(randEdge.b);
        if(find_vertex_cover(cover, maxGuards).size() != 0){
            return find_vertex_cover(cover, maxGuards);
        }

        Graph.rollBack();
        if(!cover.isEmpty()){ cover.removeLast(); }

        return new LinkedList<Vertex>(); // Returns size 0
    }

    public void find_min_cover(){
        for(int i = 29; i < vertices.size(); i++){
            find_vertex_cover(cover, i);
            if(finalCover.size() != 0){
                System.out.println("PASS! MIN #GUARDS IS: " + i);
                System.out.println(cover);
                writeSolution(finalCover);
                Traversal t = new Traversal(vertices, edg, cover);
                break;
            }
            System.out.println("FAIL WITH #GUARDS @" + i);
        }
    }

    public void writeSolution(LinkedList<Vertex> cover){
        FileWriter fw;
        Collections.sort(cover, new vertexComp());
        try{
            fw = new FileWriter(new File("cover.txt"));
            System.out.println(cover.size());
            for(Vertex v : cover){
                fw.write(Integer.toString(v.ID));
                fw.write('x');
            }
            fw.close();
        }
        catch(IOException io){
            io.printStackTrace();
        }
    }
}

class vertexComp implements Comparator<Vertex>{

    @Override
    public int compare(Vertex a, Vertex b) {
        if(a.ID < b.ID){
            return -1;
        } else {
            return 1;
        }
    }
}

