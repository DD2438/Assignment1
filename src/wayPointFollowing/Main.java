package wayPointFollowing;

import Astar.Remote;
import java.awt.geom.Point2D;
import motion.model.DifferentialDrive;
import motion.model.Discrete;
import motion.model.DynamicCar;
import motion.model.DynamicPoint;
import motion.model.KinematicCar;
import motion.model.KinematicPoint;

public class Main {

    public static void main(String[] args) {
        Remote remote = Remote.getRemote();
        Discrete d = new Discrete("AudiR8_Discrete");
        KinematicPoint k = new KinematicPoint("AudiR8_KinematicPoint");
        DynamicPoint dp = new DynamicPoint("AudiR8_DynamicPoint");
        KinematicCar kc = new KinematicCar("AudiR8_KinematicCar");
        DynamicCar dc = new DynamicCar("AudiR8_DynamicCar");
        DifferentialDrive dd = new DifferentialDrive("Tiger_DifferentialDrive");//
        int checkPoint = remote.getHandle("CheckPoint");
        final Point2D.Float[] path = new Point2D.Float[9];
//        for (int i = 0; i < path.length; i++) {
//            path[i] = new Point2D.Float(i, i);
//        }
        //(-4.2498e+00,-4.2703e+00)
//        path[0] = new Point2D.Float(-4.25f, -3.0f);
//        path[1] = new Point2D.Float(-5.5f, -3.0f);
//        path[2] = new Point2D.Float(-5.5f, -4.25f);
//        path[3] = new Point2D.Float(-5.5f, -5.5f);
//        path[4] = new Point2D.Float(-4.25f, -5.5f);
//        path[5] = new Point2D.Float(-3.0f, -5.5f);
//        path[6] = new Point2D.Float(-3.0f, -4.25f);
//        path[7] = new Point2D.Float(-3.0f, -3.0f);
//        path[0] = new Point2D.Float(1.5f, 1.5f);
//        path[1] = new Point2D.Float(0f, 1.5f);
//        path[2] = new Point2D.Float(-1.5f, 1.5f);
//        path[3] = new Point2D.Float(-1.5f, 0f);
//        path[4] = new Point2D.Float(-1.5f, -1.5f);
//        path[5] = new Point2D.Float(0f, -1.5f);
//        path[6] = new Point2D.Float(1.5f, -1.5f);
//        path[7] = new Point2D.Float(1.5f, 0f);
//        path[8] = new Point2D.Float(1.5f, 1.5f);
//        
        path[0] = new Point2D.Float(-6.5f, -6.5f);
        path[1] = new Point2D.Float(-5.5f, -4.0f);
        path[2] = new Point2D.Float(-5.0f, -3.0f);
        path[3] = new Point2D.Float(2.00f, 1.00f);
        path[4] = new Point2D.Float(6.0f, 4.0f);
        path[5] = new Point2D.Float(5.25f, 8.25f);
        path[6] = new Point2D.Float(7.25f, 8.75f);
        path[7] = new Point2D.Float(8.5f, 11.75f);
        path[8] = new Point2D.Float(12.00f, 10.00f);
       
        float[] position = new float[3];
        for (int i = 0; i < path.length; i++) {
            position[0] = path[i].x;
            position[1] = path[i].y;
            if (i == 0) {
                remote.setAbsPosition(checkPoint, position);
            } else {
                remote.setAbsPosition(remote.duplicate(checkPoint), position);
            }
        }
        // System.out.println("angle= " + dd.angle(new Point2D.Float(-1,1), new Point2D.Float(-1,-1)) * 180/Math.PI);
        d.move(path);
        //d.restPosition();
        k.move(path);
        //k.restPosition();
        dp.move(path);
        //dp.restPosition();
        kc.move(path);
        //kc.restPosition();
        dc.move(path);
        //dc.restPosition();
        dd.move(path);
        //dd.restPosition();
        Remote.getRemote().close();
    }
}
