/*
This class implements a simple graph with a two level array with three main functions:
add and edage, get an edage and create min spanning tree. After creating spanning tree, 
the graph will converts to a spanning tree according to description.
@Gigi Xiaowan Guo
*/
public class GraphAdjMatrix implements Graph{
    int m[][];
    int n;
    public GraphAdjMatrix(int n){
        this.n = n;
        m = new int[n][n];
        for(int i = 0;i < n;++i)
            for(int j = 0;j < n;++j)
                m[i][j] = m[j][i] = -1;//-1 means MAX or not available.
        for(int i =  0;i < n;++i)
            m[i][i] = 0;
    }
    public void addEdge(int v1, int v2){};
    public void topologicalSort(){};
    public void addEdge(int s, int e, int w){
        m[s][e] = w;
        m[e][s] = w;
    }
    public int getEdge(int v1, int v2){
        return m[v1][v2];
    }
    public int createSpanningTree(){
        int res = 0;//total weight.
        int tree[][] = new int[n][n];//the spanning tree;
        for(int i = 0;i < n;++i)
            for(int j = 0;j < n;++j){
                tree[i][j] = tree[j][i] = -1;
                if(i == j)tree[i][j] = 0;
            }
        int cost[] = new int[n];//cost[i] means the current cost needed to add [i] into the tree
        int from[] = new int[n];//from[i] = k means the edge is from k to i to add [i];
        for(int i = 1;i < n;++i)
            cost[i] = m[0][i];
        for(int i = 0;i < n;++i)
            from[i] = 0;
            
        boolean added[]  = new boolean[n];//if the node is added into spanning tree
        for(int i = 1;i < n;++i)
            added[i] = false;
            
        added[0] = true;// start from 0.
        for(int i = 1;i < n;++i){
            int min = -1;//max.
            int target = -1;//to be found and added;
            int v = -1;//the node used to add target;
            for(int j = 1;j < n;++j){//find the shortest edge to be target. 
                if(cost[j] < 0 || added[j] == true)continue;
                if(min < 0 || cost[j] < min){
                    min = cost[j];
                    target = j;
                    v = from[target];
                }
            }//end for j
            if(target < 0){//target not found!
                System.out.println("***the graph is not connected!***");
                return -1;
            }
            //add the target (min cost) into the tree
            added[target] = true;
            res += cost[target];
            tree[v][target] = tree[target][v] = cost[target];
            //find if there is shorter path
            for(int j = 1;j < n;++j){
                if(target == j)continue;
                if(m[j][target] < 0)continue;
                if(cost[j] < 0 || m[j][target] < cost[j]){
                    cost[j] = m[j][target];
                    from[j] = target;
                }
            }
        }//end for i
        //System.out.println(res);
        //make m to tree;
        for(int i = 0;i < n;++i)
            for(int j = 0;j < n;++j)
                m[i][j] = tree[i][j];
        return res;
    }
}