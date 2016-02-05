package visibility;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class VGraph {
    int n;
    boolean[][] adjG;
    float[] x,y;
    int[] pol;
    Point2D S;
    Point2D E;
    Point[] V;
    Polygon[] V1;
    int vertices;
    public ArrayList<Polygon> obstacles= new ArrayList<Polygon>();
    
    public VGraph(float[] x, float[] y, int[] pol, Point2D S, Point2D E) {
        this.x=x; 
        this.y=y;
        this.pol=pol;
        vertices = pol.length;
    	n = vertices+2;
    	this.S=S;
    	this.E=E;
    	
        adjG = new boolean[n][n];
        V= new Point[vertices];
        V1= new Polygon[vertices];
        drawPolys();     
        drawPaths();
    }
    


	private void drawPaths() {
		for(int i=0; i<V.length;i++){
			
			Point tmpP= new Point();			
			tmpP.setLocation(x[i], y[i]);
			
			//check if point visable from Start, if so create path
			Line2D tmp1 = new Line2D.Double(S,tmpP);
			if(!checkForCollison(tmp1)){
				adjG[i][n-2]=true;
				adjG[n-2][i]=true;
			}
			
			
			//check if point visable from end, if so create path
			Line2D tmp2 = new Line2D.Double(E,tmpP);
			if(!checkForCollison(tmp2)){
				adjG[i][n-1]=true;
				adjG[n-1][i]=true;				
			}
			
			
			//check if point visable from other points, if so create path
			for(int j=i+1; j<V.length; j++){
				//if already connected, skip
				if(adjG[i][j]) 
					continue;
				//check if points belong to same polygon, if so skip
				if(V1[i].contains(j))
					continue;
				
				Line2D tmp3= new Line2D.Double(V[i],V[j]);
				
				if(!checkForCollison(tmp3)){
					adjG[i][j] = true;
					adjG[j][i] = true;
				}
			}
			
		}
	}

	public boolean checkForCollison(Line2D tmp1) {
		Point2D t1,t2;
		
		t1=tmp1.getP1(); 
		t2=tmp1.getP2();
		
		for(Polygon p:obstacles){						
			for(Line2D l: p.edges){					
				Point2D l1,l2;
				l1=l.getP1(); l2=l.getP2();				
				if(l1.equals(t1) || l1.equals(t2) || l2.equals(t1) || l2.equals(t2))
					 continue;					
				if(l.intersectsLine(tmp1))	{
					return true;		}
			}
		}
		return false;	
	}

	public void drawPolys(){
    	Polygon current = new Polygon();
		obstacles.add(current);
    	int first =0;
    	Point tmp = new Point();
		tmp.setLocation(x[0], y[0]);
		V[0]=tmp;
		V1[0]=current;

    	for(int i=1; i<pol.length ; i++){
    		tmp = new Point();
    		tmp.setLocation(x[i], y[i]);
    		V[i]=tmp;
    		if(current==null){
    			current = new Polygon();
    			obstacles.add(current);
    			first =i;
    			V1[i]=current;
    		}
    		else if(pol[i]==1){
    			adjG[i][i-1] = adjG[i-1][i] =true;
    			current.addLine(new Line2D.Double(V[i],V[i-1]),i);
    			V1[i]=current;
    		}
    		else if(pol[i]==3){
    			adjG[i][i-1] = adjG[i-1][i] =true;
    			current.addLine(new Line2D.Double(V[i],V[i-1]),i);
    			adjG[i][first] = adjG[first][i] =true;
    			current.addLine(new Line2D.Double(V[i],V[first]),i);
    			V1[i]=current;
    			current = null;
    		}
    	}
    }
    

}
