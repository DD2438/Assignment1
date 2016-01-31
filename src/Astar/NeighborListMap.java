package Astar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class NeighborListMap {
	
	private int start;
	private int end;
	private int V;
	private double[] x;
	private double[] y;

	public boolean[][] map;
	private double[][] Hmap;
	private double[] Gmap;
	private int[] parents;
	ArrayList<Integer> openList = new ArrayList<Integer>();
	ArrayList<Integer> closedList = new ArrayList<Integer>();
	ArrayList<Integer> path = new ArrayList<Integer>();
	


	public NeighborListMap(boolean[][] map, double[] x, double[] y){
		this.x=x;
		this.y=y;
		start = map.length-2;
		end = map.length-1;
		V=map.length;
		
		this.map = map;
		Hmap = new double[V][V];
		Gmap = new double[V];
		parents = new int[V];
		
		init();
	}
	public void init(){
		
		for(int i=0; i<Gmap.length ; i++)
			Gmap[i] = Double.MAX_VALUE;
		
		//calculate all distances to end
		for(int i=0; i<V-2 ; i++){
			for(int j=i; j<V-2; j++){
				if(map[i][j]){
					double dist = calcDist(i,j);
					Hmap[i][j] = dist;	
					Hmap[j][i] = dist;
				}
			}
		}	
		
		//set start as parent to neighboring nodes
		
		for(int i=0 ; i<=V-2; i++){
			if(map[start][i])			
				parents[i] = start;
		}
		
		
		//add start to open list, set its G-value to 0
		openList.add(start);
		Gmap[start] = 0;
		parents[start]= start;
	}
	
	
	//calculates oclidian distance
	private double calcDist(int s, int e) {
		return Math.sqrt(Math.pow(x[s]-x[e], 2)+Math.pow(y[s]-y[e], 2));
	}
	
	public ArrayList<Integer> getShortestPath(){
		int current;
		
		while(!openList.isEmpty()){
			current = openList.remove(0);
			closedList.add(current);
			if(getNeighbors(current)){
				backtrack(current);
				break;
			}
		}		
		return path;		
	}

	private void backtrack(int current) {
		path.add(end);
		path.add(current);
		while(current!=start){
			current = parents[current];
			path.add(current);
		}
		
		Collections.reverse(path);
	}

	private boolean getNeighbors(int current) {
		double newVal;
		int parent;

		//updates the Gvalues of neighbors
		for(int i=0 ; i<V; i++){
				
				if(!map[current][i])
					continue;
					
				
				//return success if end point found
				if(i == end){
					return true;
				}
				
				//if new point, add to openlist
				if(!contains(openList,i) && !contains(closedList,i)){
;
					openList.add(i);
				}
								
				
				//calculate new value to be parents value + cost
				parent = parents[i];	
				newVal = Gmap[parent] + Hmap[current][i];
					
				//Change values if new one is smaller
				if(newVal < Gmap[i] && i!=start){
					Gmap[i] = newVal;
					parents[i] = current;
				}
			
		}
		
		//Sort the openList
		openList.sort(
				new Comparator<Integer>(){			
					public int compare(Integer a, Integer b){
						return (int) (Gmap[a]-Gmap[b]);
					}
				});
		return false;
		
	}
		
	public boolean contains(ArrayList<Integer> a, int v){
		for(int i:a){
			if(i==v)
				return true;
		}
		return false;
	}
	public ArrayList<Integer> getPath(){
		return path;
	}




}
