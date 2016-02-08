package motion.model;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;


public class DynamicCar extends Motion {
	
	public float orientation = 0;
	public float theta = 0;
	public float length = 2.5F;

    public Point2D.Float v = new Point2D.Float(0f, 0f);

    public DynamicCar(String robot) {
        super(robot);
    }

    @Override
    public void move(Point2D.Float[] path) {

    }
    
    
    
    public Point2D.Float getFront(Point2D.Float current){
    	float x = current.x +  (length *(float) Math.sin(orientation));
    	float y = current.y +  (length *(float) Math.cos(orientation));
    	return new Point2D.Float(x, y);
    	
    }
    
    
    
    public Point2D.Float calculate(Point2D.Float s, Point2D.Float e, float orientation, Point2D.Float vel){
    	this.orientation = orientation;
    	v = vel;
    	Point2D.Float front = getFront(s);
    	
    	theta = (float) -angle(getFront(front), front, e);
    	double Max = 1;
    	if(theta>Max)
    		theta =(float) Max;
    	if(theta<-Max)
    		theta =(float) -Max;

    	theta *= (0.3+(Math.random()*0.7));
    	
    	if(Math.random()<0.1)
    		theta = 0;
    	
        float Vx = (e.x - s.x) / dt;
        float Vy = (e.y - s.y) / dt;
        
        float ax = (Vx - v.x) /dt;
        float ay = (Vy - v.y) /dt;

        float at = (float) Math.sqrt(ax * ax + ay * ay);
        if (at > MAX_ACCELERATION) {
            ax = (ax / at) * MAX_ACCELERATION;
            ay = (ay / at) * MAX_ACCELERATION;
        }
        

        Vx = v.x + ax * dt;
        Vy = v.y + ay * dt ;
        
        float V = (float) Math.sqrt(Vx * Vx + Vy * Vy);

        float newX = (float) (V*Math.cos(orientation));
        
        float newY = (float) (V*Math.sin(orientation));
        
        this.orientation += (float) ((V/length)* Math.tan(theta));
        v.x = newX;
        v.y = newY;
        
		return new Point2D.Float(s.x+newX, s.y+newY);
    	
    }

	public Float calculateToGoal(Point2D.Float s, Point2D.Float e, float orientation, Point2D.Float vel) {
		this.orientation = orientation;
    	v = vel;
    	Point2D.Float front = getFront(s);
    	
    	theta = (float) -angle(getFront(front), front, e);
    	double Max = 1;
    	if(theta>Max)
    		theta =(float) Max;
    	if(theta<-Max)
    		theta =(float) -Max;
    	

        float Vx = (e.x - s.x) / dt;
        float Vy = (e.y - s.y) / dt;
        
        float ax = (Vx - v.x) /dt;
        float ay = (Vy - v.y) /dt;
        

        float at = (float) Math.sqrt(ax * ax + ay * ay);
        
        if (at > MAX_ACCELERATION) {
            ax = (ax / at) * MAX_ACCELERATION;
            ay = (ay / at) * MAX_ACCELERATION;
        }

        Vx = v.x + ax * dt;
        Vy = v.y + ay * dt ;
        float V = (float) Math.sqrt(Vx * Vx + Vy * Vy);

        float newX = (float) (V*Math.cos(orientation));
        
        float newY = (float) (V*Math.sin(orientation));
        
        this.orientation += (float) ((V/length)* Math.tan(theta));

        Point2D.Float target = new Point2D.Float(s.x+ (newX * dt), s.y+(newY * dt));
        v.x = newX;
        v.y = newY;
        
		return  target;
	}
    
    
}

