package Museum;

import java.util.*;

public class Graph {
    private static ArrayList<Vertex> vertices;
    protected static Edge[] edges;
    protected static int endIndex; // The index of the first null element found, all indices after this are also null
    protected static Queue<Edge> q;

    public Graph(ArrayList<Vertex> v, Edge[] e){
        vertices = v;
        edges = e;
        endIndex = edges.length - 1;
        q = Collections.asLifoQueue(new LinkedList<Edge>());
    }

    public Graph(int vertexID) {
        remove_incident_edges(vertexID);
    }

    public static void rollBack(){
        Edge e;
        while( (!(q.isEmpty ())) && (!(q.peek().a.ID == -1)) ){
            e = q.remove();
            edges[endIndex] = e;
            endIndex++;
        }
        if(( (!(q.isEmpty ())) &&  q.peek().a.ID == -1) ) { q.remove(); }
        if(endIndex == edges.length){ endIndex--; }
//        System.out.println("\nAFTER ROLLBACK\n");
//        int counter = 0;
//        for(Edge a : edges){
//            System.out.println("   " + counter + ": " + a);
//            counter++;
//        }
    }

    public static void remove_incident_edges(int vertexID){
        Vertex v = vertices.get(vertexID);
        ArrayList<Edge> edges_to_remove = new ArrayList<Edge>();
        int counter = 0;
        for(Edge e : edges){
            if(e == null){
                endIndex = counter;
                break;
            }
            else {
                if(e.contains(v)){
                    edges_to_remove.add(e);
                    q.add(e.copy());
                }
            }
            counter++;
        }
        q.add(new Edge(new Vertex(-1), new Vertex(-1))); // Acts as a separator between each rollback

        int removeIndex = edges_to_remove.size();
//        System.out.println("REMOVE INDEX: " + removeIndex);
//        for(int i = 0; i < edges_to_remove.size(); i++){
//            System.out.println(edges_to_remove.get(i));
//        }
//
//        System.out.println("EDGES BEFORE: ");
//        for(int i = 0; i < edges.length; i++){
//            System.out.println("   " + i + ": " + edges[i]);
//        }

        if(removeIndex != 0){
            removeAll(edges_to_remove);
        }
        else{
            return;
        }

//        System.out.println("EDGES AFTER: ");
//        for(int i = 0; i < edges.length; i++){
//            System.out.println("   " + i + ": " + edges[i]);
//        }


//        Edge e;
//        for(int j = 0; j < edges_to_remove.size(); j++){
//            e = edges_to_remove.get(j);
//            for(int i = 0; i < edges.length; i++){
//                if(edges[i] != null){
//                    if(edges[i].equals(e)){
//                        System.out.println("FAIL, " + e + " SHOULD HAVE BEEN REMOVED FOUND @" + i);
//                    }
//                }
//
//            }
//        }

        while((endIndex > -1)){ // Moves endIndex to last not null element
            if(edges[endIndex] == null){
                endIndex--;
            } else { break; }
        }
        endIndex++; // Moves endIndex to first null element

//        System.out.println("PASS, ALL PROPERLY REMOVED");
//        System.out.println("REMOVE END INDEX: " + endIndex);

    }

    public static boolean removeAll(Collection c) {
        int oldHi, newHi = 0, top = 0;
        int size = edges.length;
        for (int i = 0; i < size; ++i) {
            if (c.contains(edges[i])) {
                oldHi = newHi;
                newHi = i;

                // at the end of this loop newHi will be the non-inclusive
                // upper limit of the range to delete.
                //
                while (++newHi < size && c.contains(edges[newHi])) ;

                final int length = i - oldHi;
                System.arraycopy(edges, oldHi, edges, top, length);
                i = newHi;
                top += length;
            }
        }
        if (newHi > 0) {
            final int k = size - newHi;
            System.arraycopy(edges, newHi, edges, top, k);
            final int n = top + k;
            Arrays.fill(edges, n, size, null);
            size = n;
            return true;
        } else {
            return false;
        }
    }
}