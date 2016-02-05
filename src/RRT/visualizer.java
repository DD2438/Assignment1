package RRT;

import java.awt.*;
import java.awt.geom.Line2D;

import javax.swing.*;  
public class visualizer   extends JFrame implements Runnable {
	 panel panel ;
		   public visualizer() {
		      JFrame f = new JFrame("Line");
		       panel = new panel();
		      f.add(panel);
		      f.setSize(new Dimension(1000, 1000));
		      f.setVisible(true);
		      
		   }
		   
		   public void add(Line2D d){
			 panel.add(d);
		   }
		   public void kek(){
			 panel.kek();;
		   }

		@Override
		public void run() {
		}
} 