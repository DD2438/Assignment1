package motion.model;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;

public class DifferentialDrive extends Motion {

	public float orientation =0;
	public float theta = 0;
	public float length =3F;
	public float v = 0;

    public DifferentialDrive(String robot) {
        super(robot);
    }

    @Override
    public void move(Point2D.Float[] path) {

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
    	
    	if(Math.random()<0.6)
    		theta = 0;
    	
    	float Vx = (e.x - s.x) /dt ;
        float Vy = (e.y - s.y) /dt;
                
        float V = (float) Math.sqrt(((Vx * dt)*( Vx * dt))+ ((Vy*dt) * (Vy * dt)));
        if (V > MAX_VELOCITY) {
            Vx = (Vx / V) * MAX_VELOCITY;
            Vy = (Vy / V) * MAX_VELOCITY;
        }
        
        V = (float) (Math.sqrt(((Vx * dt)*( Vx * dt))+ ((Vy*dt) * (Vy * dt))));

        float newX = (float) (V*Math.cos(orientation));
        
        float newY = (float) (V*Math.sin(orientation));
        
        this.orientation += (float) ((V/length)* Math.tan(theta));
        this.v = V;
        
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
    	
    	float Vx = (e.x - s.x) /dt ;
        float Vy = (e.y - s.y) /dt;
                
        float V = (float) Math.sqrt(((Vx * dt)*( Vx * dt))+ ((Vy*dt) * (Vy * dt)));
        if (V > MAX_VELOCITY) {
            Vx = (Vx / V) * MAX_VELOCITY;
            Vy = (Vy / V) * MAX_VELOCITY;
        }
        
        V = (float) (Math.sqrt(((Vx * dt)*( Vx * dt))+ ((Vy*dt) * (Vy * dt)))*(0.5+Math.random()*0.5));

        float newX = (float) (V*Math.cos(orientation));
        
        float newY = (float) (V*Math.sin(orientation));
        
        this.orientation += (float) ((V/length)* Math.tan(theta));
        this.v = V;
		return new Point2D.Float(s.x+newX, s.y+newY);
	}

}
