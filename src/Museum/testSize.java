package Museum;
import java.util.ArrayList;

// A Java program to find maximal
// Bipartite matching.
import java.util.*;
import java.lang.*;
import java.io.*;

public class testSize
{
    // M is number of applicants
    // and N is number of jobs
    static int M; // Size Left
    static int N ; // Size Right
    ArrayList<Vertex> L;
    ArrayList<Vertex> R;
    boolean bpGraph[][];

    public testSize(ArrayList<Vertex> L, ArrayList<Vertex> R){
        this.L = L; this.R = R;
        M = L.size();
        N = R.size();
        bpGraph = new boolean[M][N];
        generateGraph();
        System.out.println("MAX IS: " + maxBPM(bpGraph));
    }

                    // A DFS based recursive function that
                    // returns true if a matching for
                    // vertex u is possible
    boolean bpm(boolean bpGraph[][], int u,
                    boolean seen[], int matchR[])
    {
        // Try every job one by one
        for (int v = 0; v < N; v++)
        {
            // If applicant u is interested
            // in job v and v is not visited
            if (bpGraph[u][v] && !seen[v])
            {

                // Mark v as visited
                seen[v] = true;

                // If job 'v' is not assigned to
                // an applicant OR previously
                // assigned applicant for job v (which
                // is matchR[v]) has an alternate job available.
                // Since v is marked as visited in the
                // above line, matchR[v] in the following
                // recursive call will not get job 'v' again
                if (matchR[v] < 0 || bpm(bpGraph, matchR[v],
                        seen, matchR))
                {
                    matchR[v] = u;
                    return true;
                }
            }
        }
        return false;
    }

    // Returns maximum number
    // of matching from M to N
    int maxBPM(boolean bpGraph[][])
    {
        // An array to keep track of the
        // applicants assigned to jobs.
        // The value of matchR[i] is the
        // applicant number assigned to job i,
        // the value -1 indicates nobody is assigned.
        int matchR[] = new int[N];

        // Initially all jobs are available
        for(int i = 0; i < N; ++i)
            matchR[i] = -1;

        // Count of jobs assigned to applicants
        int result = 0;
        for (int u = 0; u < M; u++)
        {
            // Mark all jobs as not seen
            // for next applicant.
            boolean seen[] =new boolean[N] ;
            for(int i = 0; i < N; ++i)
                seen[i] = false;

            // Find if the applicant 'u' can get a job
            if (bpm(bpGraph, u, seen, matchR))
                result++;
        }
        return result;
    }

    public boolean hasEdge(Vertex src, Vertex check){
        for(Vertex v : src.adj){
            if(v.equals(check)){
                return true;
            }
        }
        return false;
    }

    public void generateGraph(){
        int index;
        Vertex temp, adj;

        System.out.println(L.size());
        System.out.println("LEFT\n----\n");
        for(Vertex v : L){
            System.out.println(v);
        }
        System.out.println(R.size());
        System.out.println("RIGHT\n-----\n");
        for(Vertex v : R){
            System.out.println(v);
        }
        for(int i = 0; i < L.size(); i++){
            temp = L.get(i);
            for(int j = 0; j < temp.adj.size(); j++){
                adj = temp.adj.get(j);
                index = R.indexOf(adj);
                System.out.println("I: " + i + "   INDEX: " + index);
                bpGraph[i][index] = true;
            }
        }
    }

}