
package motion.model;

import Astar.Remote;
import coppelia.FloatWA;
import coppelia.remoteApi;
import java.awt.geom.Point2D;

public abstract class Motion {
    public static final float MAX_VELOCITY = 1f;
   // public static final float MAX_DIS = 0.05f;
    public static final float MAX_ACCELERATION = 0.4f;
    protected float dt = 20f;
    protected int robotHandle;
    protected Remote remote;
    private final FloatWA curent_pos;
    
    
    public Motion(String robot) {
        remote = Remote.getRemote();
        robotHandle = remote.getHandle(robot);
        curent_pos = new FloatWA(3);
        remote.vrep.simxGetObjectPosition(remote.clientID, robotHandle, -1, 
                curent_pos, remoteApi.simx_opmode_oneshot_wait);
    }
    
    public void restPosition(){
        float[] position = new float[3];
        position[0] = curent_pos.getArray()[0];
        position[1] = curent_pos.getArray()[1];
        position[2] = curent_pos.getArray()[2];
        FloatWA new_pos = new FloatWA(position);
        remote.vrep.simxSetObjectPosition(remote.clientID, robotHandle, -1, 
                new_pos, remoteApi.simx_opmode_oneshot_wait);
    }
    public abstract void move(Point2D.Float[] path);
    
    public void wait(int dur) {
        try {
            Thread.sleep(dur);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
    
}
