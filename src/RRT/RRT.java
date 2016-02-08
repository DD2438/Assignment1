package RRT;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import motion.model.DynamicCar;
import motion.model.DynamicPoint;
import motion.model.KinematicCar;
import motion.model.KinematicPoint;
import motion.model.Motion;
import visibility.Polygon;

public class RRT {
    int maxX = 300;
    int maxY = 300;
    Point2D.Float start;
    Point2D.Float goal;
    ArrayList<Polygon> polygons;
    CollisionCheck cc ;
    //KinematicPoint model ;
  //  DynamicPoint model ;
   KinematicCar model;
   // DynamicCar model;
    Node closest;
    boolean flag = false;
    boolean flag2 = false;
    Point2D.Float locked = null;

	Node last = null;

    Point2D.Float f = new Point2D.Float(maxY, maxX);
	long timer = System.currentTimeMillis();
    
    public  RRT(int maxX, int maxY, Point2D.Float start, Point2D.Float goal, ArrayList<Polygon> polygons, Motion model, CollisionCheck cc){
    	this.maxX = maxX;
    	this.maxY = maxY;
        this.cc = cc;
    // this.model = (DynamicCar) model;
        this.model = (KinematicCar) model;
    	//this.model = (DynamicPoint) model;
    	this.polygons = polygons;    	
    	this.start = start;
    	this.goal = goal;
    
    	/*
		visualizer v;
		new Thread(v =new visualizer(polygons, maxX, maxY)).start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
    	Tree tree = new Tree(start);
    	tree.root.speed = 0;
    //	tree.root.v=new Point2D.Float(this.model.v.x, this.model.v.y);
    	tree.root.orientation = (float) (Math.PI/2);
    	Point2D.Float target;
    	Point2D.Float random;
    	int counter;
    	System.out.print("vanilla");
    	long timer = System.currentTimeMillis();
    	do{
    		if(System.currentTimeMillis()-timer >1200000){
    			last = null;
    			break;}
    		do{
    			if(flag && Math.random()<0.5)
    				random = locked;
    			else
    				random = randomPos();    	
    			closest = tree.getClosest(random);
    		}    		
    		while(cc.checkForCollison(new Line2D.Float(closest.data, random)));
    		
	    	counter =0;	    	
    		do{		    
		    	if(flag && Math.random()<0.8)
		    		target = moveToGoal(closest,random);
		    	else
		    		target = move(closest, random);
		    	
		    	if(!cc.checkForCollison(new Line2D.Float(target, goal))){
		    		flag = true;
		    		locked = target;
		    	}
		    	
		    	if(cc.contains(target) || target.x>maxX||target.y>maxY || target.x <0 || target.y <0)
		    		break;
		    	last= new Node(closest, target, tree);    	
		    //	last.speed = this.model.v;
		  // 	last.v = new Point2D.Float(this.model.v.x, this.model.v.y);
		    	last.orientation = this.model.orientation;
		
		    	//v.drawLine(last);
		    	
		    	
		    	closest = last;
		    	counter++;
    		}
    		while(counter <5 && target.distance(goal)>10);
    		
	    }
    	while(target.distance(goal)>10);

    
    	//v.drawPath(last);
    
    }
    




	private Point2D.Float move(Node closest, Point2D.Float random) {
		
	//	return model.calculate(closest.data, random, closest.orientation, closest.v);
		return model.calculate(closest.data, random, closest.orientation);
	//	return model.calculate(closest.data, random, closest.v);
		//return model.calculate(closest.data, random);
	}
	
	private Point2D.Float moveToGoal(Node closest, Point2D.Float random) {
		
		//return model.calculateToGoal(closest.data, random, closest.orientation, closest.v);
		return model.calculate(closest.data, random, closest.orientation);
		//return model.calculate(closest.data, random, closest.v);
		//return model.calculate(closest.data, random);
	}


	
	public Point2D.Float randomPos(){
		if(flag && Math.random()<0.6 )
			return goal;
		Point2D.Float p = new Point2D.Float();
		float x;
		float y;
		do{
			x =(float)Math.random()* maxX;
			y =(float)Math.random()* maxY;
			p.setLocation(x, y);
		}
		while(cc.contains(p));
		return p;
	}
		
	
    
    
}
