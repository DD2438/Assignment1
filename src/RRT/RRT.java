package RRT;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import motion.model.DynamicPoint;
import motion.model.KinematicCar;
import motion.model.KinematicPoint;
import motion.model.Motion;
import visibility.Polygon;

public class RRT {
    int maxX = 100;
    int maxY = 100;
    Point2D.Float start;
    Point2D.Float goal;
    ArrayList<Polygon> polygons;
    CollisionCheck cc ;
    //KinematicPoint model ;
    //DynamicPoint model ;
    KinematicCar model;
    Node closest;

	Node last = null;

    Point2D.Float f = new Point2D.Float(maxY, maxX);
	long timer = System.currentTimeMillis();
    
    public  RRT(float maxX, float maxY, Point2D.Float start, Point2D.Float goal, ArrayList<Polygon> polygons, Motion model, CollisionCheck cc){

        this.cc = cc;
    	this.model = (KinematicCar) model;
    	//this.model = (DynamicPoint) model;
    	this.polygons = polygons;    	
    	this.start = start;
    	this.goal = goal;
    	
    	
		visualizer v;
		new Thread(v =new visualizer()).start();
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		drawMap(v);
		
    	Tree tree = new Tree(start);
    //	tree.root.v=new Point2D.Float(this.model.v.x, this.model.v.y);
    	tree.root.orientation = 0;
    	Point2D.Float target;
    	Point2D.Float random;
    	TimeUnit tu = TimeUnit.NANOSECONDS;
    	
    	do{
    	random = randomPos();
    	closest = tree.getClosest(random);
    	
    	target = move(closest, random);

    	if(cc.contains(target) || target.x>100 ||target.y>100 || target.x <0 || target.y <0)
    		continue;
    	last= new Node(closest, target, tree);    	
    //	last.v = new Point2D.Float(this.model.v.x, this.model.v.y);
    	last.orientation = this.model.orientation;

    		
    	v.add(new Line2D.Float(new Point2D.Float(last.data.x*10, (1000-(last.data.y*10)) ), new Point2D.Float(last.parent.data.x*10, (1000-(last.parent.data.y*10)) )));
    	try {
			tu.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
    	
    	}
    	while(target.distance(goal)>0.5 );


    	
    	v.kek();
    	while(last.parent!=null){
        	v.add(new Line2D.Float(new Point2D.Float(last.data.x*10, (1000-(last.data.y*10)) ), new Point2D.Float(last.parent.data.x*10, (1000-(last.parent.data.y*10)) )));
        	try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        	last=last.parent;
    	}
    }
    


    
    
	private void drawMap(visualizer v) {
		
		for(Polygon p: polygons){
			for(Line2D l : p.edges){
				v.add(new Line2D.Double((l.getP1().getX()*10),(1000-(l.getP1().getY()*10)),(l.getP2().getX()*10),(1000-(l.getP2().getY()*10))));
				//v.add(l);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}





	private Point2D.Float move(Node closest, Point2D.Float random) {
		
		return model.calculate(closest.data, random, closest.orientation);
		//return model.calculate(closest.data, random, closest.v);
		//return model.calculate(closest.data, random);
	}


	//Should implement psudo random strategy
	
	public Point2D.Float randomPos(){
		if(Math.random()<0.9)
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