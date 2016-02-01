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
        Discrete d = new Discrete("AudiR8_Discrete");
        KinematicPoint k = new KinematicPoint("AudiR8_KinematicPoint");
        DynamicPoint dp = new DynamicPoint("AudiR8_DynamicPoint");
//        KinematicCar kc = new KinematicCar("Kinematic_Car");
//        DynamicCar dc = new DynamicCar("Dynamic_Car");
//        DifferentialDrive dd = new DifferentialDrive("DifferentialDrive");
        final Point2D.Float[] path = new Point2D.Float[15];
        for (int i = 0; i < path.length; i++) {
            path[i] = new Point2D.Float(i, i);
        }

        d.move(path);
        d.restPosition();
        k.move(path);
        k.restPosition();
        dp.move(path);
        dp.restPosition();
        //kc.move(path);
        //dc.move(path);
        //dd.move(path);
        Remote.getRemote().close();
    }
}
