package visibility;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Polygon {

	ArrayList<Line2D> edges = new ArrayList<Line2D>();
	ArrayList<Integer> vIndex = new ArrayList<Integer>();
	
	public Polygon(){
			
	}
	
	public void addLine(Line2D l,int index){
		System.out.println("line added "+l.getX1()+"," +l.getY1()+"-"+l.getX2()+","+l.getY2());
		edges.add(l);
		vIndex.add(index);
	}
	
	
	public boolean contains(Integer i){
		for(Integer I:vIndex){
			if(I.equals(i))
				return true;
		}
		return false;
	}

	public boolean contains(Line2D tmp1) {
		for(Line2D l : edges){
				 if((l.getP1().equals(tmp1.getP1()) && l.getP2().equals(tmp1.getP2()) )||
					(l.getP1().equals(tmp1.getP2()) && l.getP2().equals(tmp1.getP1()) ))
				return true;
		}
		return false;
	}
}
