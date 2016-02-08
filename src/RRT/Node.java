package RRT;

import java.awt.geom.Point2D;


public  class Node {
    Point2D.Float data;
    Node parent;
    float speed;
    Point2D.Float v;

    float orientation;
    
    float cost;
    
    public Node(Node parent, Point2D.Float data, Tree t){
    	if(parent!=null)
    		this.cost = parent.cost+(float) data.distance(parent.data);
    	this.parent = parent;
    	this.data = data;

    	t.allNodes.add(this);
    	t.count++;
    }
    
    
}