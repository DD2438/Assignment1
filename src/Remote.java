import coppelia.IntW;
import coppelia.IntWA;
import coppelia.remoteApi;

public class Remote {

	remoteApi vrep ;
	int clientID;
	
	
	public Remote(){
	
	
	vrep = new remoteApi();
	vrep.simxFinish(-1); // just in case, close all opened connections
	clientID = vrep.simxStart("127.0.0.1",19999,true,true,5000,5);
	if (clientID!=-1)
	{
		System.out.println("Connection established");
		
		
		
	}
	else
		System.out.println("Failed connecting to remote API server");
	}
	
	public int getHandle(String name){
		IntW tmpIntW =new IntW(0);				
		vrep.simxGetObjectHandle(clientID, name, tmpIntW,remoteApi.simx_opmode_oneshot_wait);
		return tmpIntW.getValue();
	}
	
	public int duplicate(int handle){
		int[] tmp = {handle};
		IntWA tmpHandle = new IntWA(tmp); 
		
		vrep.simxCopyPasteObjects(clientID, tmpHandle, tmpHandle, vrep.simx_opmode_oneshot_wait);
		return tmpHandle.getArray()[0];
	}
}
