package Astar;

import coppelia.FloatWA;
import coppelia.IntW;
import coppelia.IntWA;
import coppelia.remoteApi;

public class Remote {

    public remoteApi vrep;
    public int clientID;
    private static Remote remote;

    private Remote() {
        vrep = new remoteApi();
        vrep.simxFinish(-1); // just in case, close all opened connections
        clientID = vrep.simxStart("127.0.0.1", 19999, true, true, 5000, 5);
        if (clientID != -1) {
            System.out.println("Connection established");

        } else {
            System.out.println("Failed connecting to remote API server");
        }
    }

    public static Remote getRemote() {
        if (remote == null) {
            remote = new Remote();
        }
        return remote;
    }

    public int getHandle(String name) {
        IntW tmpIntW = new IntW(0);
        vrep.simxGetObjectHandle(clientID, name, tmpIntW, remoteApi.simx_opmode_oneshot_wait);
        return tmpIntW.getValue();
    }

    public int duplicate(int handle) {
        int[] tmp = {handle};
        IntWA tmpHandle = new IntWA(tmp);

        vrep.simxCopyPasteObjects(clientID, tmpHandle, tmpHandle, remoteApi.simx_opmode_oneshot_wait);
        return tmpHandle.getArray()[0];
    }

    public float[] getPosition(int robotHandle) {
        FloatWA curent_pos = new FloatWA(3);
        vrep.simxGetObjectPosition(remote.clientID, robotHandle,
                -1, curent_pos, remoteApi.simx_opmode_oneshot_wait);
        return curent_pos.getArray();
    }

    public void setPosition(int robotHandle, float[] position) {
        FloatWA new_pos = new FloatWA(position);
        remote.vrep.simxSetObjectPosition(remote.clientID, robotHandle,
                robotHandle, new_pos, remoteApi.simx_opmode_oneshot);
    }

    public void close() {
        System.out.println("Closing connection...");
        // Now close the connection to V-REP:	
        vrep.simxFinish(clientID);
    }
    
}
