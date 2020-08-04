import java.util.*;

public class DisjointSet {

    int[] parent;

    public DisjointSet(int size){
        parent = new int[size];
        for(int i = 0; i < size; i++){
            parent[i] = -1;
        }
    }
    //find the index of the root
    private int find(int x)
    {
        // return index if x is root
        if (parent[x] < 0) {
            return x;
        }
        // recurse to find root
        return parent[x] = find(parent[x]);
    }

    // Perform join of two subsets
    public void join(int a, int b)
    {
        int rootA = find(a);
        int rootB = find(b);
        if(rootA == rootB){
            return;
        }

        if(parent[rootB] < parent[rootA]){
            int temp = rootA;
            rootA = rootB;
            rootB = temp;
        }
        parent[rootA] += parent[rootB];
        parent[rootB] = rootA;
    }

    public void printSets()
    {
        for (int i : parent)
            System.out.print(i + " ");

        System.out.println();
    }

    public void printFertile(boolean[] isFertileArray)
    {
        ArrayList<Integer> sortedFertileLands = new ArrayList<Integer>();
        for (int i = 0; i < parent.length; i++){
            if(isFertileArray[i] && parent[i] < 0){
                sortedFertileLands.add(-parent[i]);
            }
        }
            Collections.sort(sortedFertileLands);
        for(int i : sortedFertileLands){
            System.out.println(i + " ");
        }
    }
}
