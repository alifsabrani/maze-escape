public class Node{
    public int x;
    public int y;
    public boolean visited;
    public double level;
    public double heuristic;
    public Node child[];
    public Node parent;
    public Node next;
    public Node(int x, int y, double level, double heuristic){
        child = new Node[4];
        this.x = x;
        this.y = y;
        this.level = level;
        this.heuristic = heuristic;
        this.visited = false;
        for(int i = 0;i < 4;i++){
            this.child[i] = null;
        }
        this.parent = null;
        this.next = null;
    }

    public double getFn(){
        return heuristic+level;
    }
}