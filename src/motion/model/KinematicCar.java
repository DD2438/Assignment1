package motion.model;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;


public class KinematicCar extends Motion {
	
	public float orientation = 0;
	public float theta = 0;
	public float length = 3F;

    public KinematicCar(String robot) {
        super(robot);
    }

    @Override
    public void move(Point2D.Float[] path) {

    }
    
    public static double angle(Point2D.Float p1, Point2D.Float p2, Point2D.Float p3){
    	Line2D line1 = new Line2D.Double(p1,p2);
    	Line2D line2 = new Line2D.Double(p2,p3);
    	
    	 double angle1 = Math.atan2(line1.getY1() - line1.getY2(),
                 line1.getX1() - line1.getX2());
    	 
    	 double angle2 = Math.atan2(line2.getY1() - line2.getY2(),
                 line2.getX1() - line2.getX2());
    	 
    	 return ((angle1-angle2)>Math.PI? -((2*Math.PI)-(angle1-angle2)) : (angle1-angle2));
    }
    
    
    
    public Point2D.Float getFront(Point2D.Float current){
    	float x = current.x +  (length *(float) Math.sin(orientation));
    	float y = current.y +  (length *(float) Math.cos(orientation));
    	return new Point2D.Float(x, y);
    	
    }
    
    
    
    public Point2D.Float calculate(Point2D.Float s, Point2D.Float e, float orientation){
    	this.orientation = orientation;
    	
    	Point2D.Float front = getFront(s);
    	
    	theta = (float) angle(getFront(front), front, e);
    	double Max = 1;
    	if(theta>Max)
    		theta =(float) Max;
    	if(theta<-Max)
    		theta =(float) -Max;

   	theta *= (0.3+(Math.random()*0.7));
    	
    	if(Math.random()<0.1)
    		theta = 0;
    	
    //	theta = (float) (Math.random()*(Math.random()>0.5? -1:1));
    	
    	//theta = 0.5F;
    	
    	
    	float Vx = (e.x - s.x) /dt ;
        float Vy = (e.y - s.y) /dt;
                
        float V = (float) Math.sqrt(((Vx * dt)*( Vx * dt))+ ((Vy*dt) * (Vy * dt)));
        if (V > MAX_VELOCITY) {
            Vx = (Vx / V) * MAX_VELOCITY;
            Vy = (Vy / V) * MAX_VELOCITY;
        }
        
       // V=1;
        
        V = (float) (Math.sqrt(((Vx * dt)*( Vx * dt))+ ((Vy*dt) * (Vy * dt)))*(0.5+Math.random()*0.5));

        float newX = (float) (V*Math.cos(orientation));
        
        float newY = (float) (V*Math.sin(orientation));
        
        this.orientation += (float) ((V/length)* Math.tan(theta));
        
		return new Point2D.Float(s.x+newX, s.y+newY);
    	
    }

	public Float calculateToGoal(Point2D.Float s, Point2D.Float e, float orientation) {
    	this.orientation = orientation;
    	
    	Point2D.Float front = getFront(s);
    	
    	theta = (float) angle(getFront(front), front, e);
    	double Max = 1;
    	if(theta>Max)
    		theta =(float) Max;
    	if(theta<-Max)
    		theta =(float) -Max;

   /* 	theta *= (0.5+(Math.random()*0.5));
    	
    	if(Math.random()<0.1)
    		theta = 0;*/
    	
    //	theta = (float) (Math.random()*(Math.random()>0.5? -1:1));
    	
    	//theta = 0.5F;
    	
    	
    	float Vx = (e.x - s.x) /dt ;
        float Vy = (e.y - s.y) /dt;
                
        float V = (float) Math.sqrt(((Vx * dt)*( Vx * dt))+ ((Vy*dt) * (Vy * dt)));
        if (V > MAX_VELOCITY) {
            Vx = (Vx / V) * MAX_VELOCITY;
            Vy = (Vy / V) * MAX_VELOCITY;
        }
        
       // V=1;
        
        V = (float) (Math.sqrt(((Vx * dt)*( Vx * dt))+ ((Vy*dt) * (Vy * dt))));

        float newX = (float) (V*Math.cos(orientation));
        
        float newY = (float) (V*Math.sin(orientation));
        
        this.orientation += (float) ((V/length)* Math.tan(theta));
        
		return new Point2D.Float(s.x+newX, s.y+newY);
	}
    
    
}

