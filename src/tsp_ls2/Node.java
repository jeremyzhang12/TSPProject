package tsp_ls2;

public class Node{
    private double x;
    private double y;
    private int label;
    private Node pre = null;
    private Node post = null;
    public Node(double x, double y, int label) {
        this.x = x;
        this.y = y;
        this.label = label;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public Node getPre(){
        return pre;
    }

    public Node getPos(){
        return post;
    }

    public void setPre(Node n){
        pre = n;
    }

    public void setPos(Node n) {
        post = n;
    }
    public int getLabel(){
        return label;
    }
}