package Museum;

public class Edge {
    protected Vertex a;
    protected Vertex b;

    // Represents an undirected edge between two vertices
    public Edge(Vertex aSrc, Vertex bSrc){
        a = aSrc;
        b = bSrc;
    }

    /**
     * Given a vertex on one end of the edge, return the other
     * vertex part of the edge
     * @param v The given vertex
     * @return The other vertex
     */
    public Vertex getVertex(Vertex v){
        if(v.equals(a)) {
            return b;
        }
        else {
            return a;
        }
    }

    @Override
    public String toString() {
        return "Edge from vertex #" + a.ID + " <-> #" + b.ID;
    }

    public boolean equals(Edge e) {
        return (
                ((this.a.ID == e.a.ID) && (this.b.ID == e.b.ID)) || ((this.a.ID == e.b.ID) && (this.b.ID == e.a.ID))
        );
    }

    public boolean equals(Vertex v, Vertex w){
        return (
                ((this.a.ID == v.ID) && (this.b.ID == w.ID)) || ((this.a.ID == w.ID) && (this.b.ID == v.ID))
        );
    }

    public boolean contains(Vertex v){
        return ((this.a.ID == v.ID) || (this.b.ID == v.ID));
    }

    public Edge copy(){
        return new Edge(this.a, this.b);
    }

}
