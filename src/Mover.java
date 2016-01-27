
import coppelia.CharWA;
import coppelia.FloatWA;
import coppelia.IntW;
import coppelia.IntWA;
import coppelia.remoteApi;

// Make sure to have the server side running in V-REP: 
// in a child script of a V-REP scene, add following command
// to be executed just once, at simulation start:
//
// simExtRemoteApiStart(19999)
//
// then start simulation, and run this program.
//
// IMPORTANT: for each successful call to simxStart, there
// should be a corresponding call to simxFinish at the end!


public class Mover {
	
	public  int mapsizeX;
	public  int mapsizeY;
	public  float[] mover = new float[3];
	public  FloatWA pointer;
	int playerHandle;
	int clientID;
	remoteApi vrep ;
	Remote remote;

	public Mover(int mapsizeX, int mapsizeY, Remote remote){
		
		this.remote = remote;
		this.mapsizeX = mapsizeX;
		this.mapsizeY = mapsizeY;		
		mover[2]=0.5f;
		
		clientID = remote.clientID;
		vrep = remote.vrep;		
		
		playerHandle = remote.getHandle("Player");					
		

	}
	
	public void move(int x, int y){	
		
		mover[0] = (float) x-(mapsizeX/2)+ 0.5f;
		mover[1] = (float) y-(mapsizeY/2)- 0.5f;
		pointer = new FloatWA(mover);
				
		vrep.simxSetObjectPosition(clientID, playerHandle, -1, pointer, remoteApi.simx_opmode_oneshot);
		wait(300);
		
	}
	
	public void move(int x, int y,  int handle){	
		
		mover[0] = (float) x-(mapsizeX/2)+ 0.5f;
		mover[1] = (float) y-(mapsizeY/2)- 0.5f;
		pointer = new FloatWA(mover);
				
		vrep.simxSetObjectPosition(clientID, handle, -1, pointer, remoteApi.simx_opmode_oneshot);		
	}
	
	public  void wait(int dur){
		try
		{
			Thread.sleep(dur);
		}
		catch(InterruptedException ex)
		{
			Thread.currentThread().interrupt();
		}
	}
}
