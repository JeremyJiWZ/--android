package wj.airwar;

/**
* Created by jiwentadashi on 15/11/25.
*/

public class Airplane
{
	Plane[] plane = new Plane[3];

//place the planes
	public boolean isAllPlaced()
	{
		for (int i=0; i<3; i++)
			if (plane[i].isPlace == false) return false;
		return true;
	}
	
	public boolean placePlane(int num,int row,int col,int d)
	{
		return airs[num].place(row, col, d);
	}
	
	public void reset()
	{
		plane = new Plane[3];
	}
	
//boom the planes
//0:miss,1:injured,2:dead
	public int boomPlane(int row, int col)
	{
		return 0;
	}
	
//tell if all dead
	public boolean isAllDead()
	{
		for (int i=0; i<3; i++)
			if (plane[i].isAlive == true) return false;
		return true;
	}
}

class Plane
{
	int row,col,d;//0:up,1:right,2:down,3:left; 0:tail upwards
	boolean isPlace;
	boolean isAlive;
	static final int shape[][][] = new int[4][10][2] = 
			{
					
			};
	
	void Plane()
	{
		isPlace=false;
		isalive=false;
	}
	
	boolean place(int row,int col,int d)
	{
		if (row < 0 || row > 9) return false;
		if (col < 0 || col > 9) return false;
		if ()
		
		
		isPlace = true;
		isalive = true;
		return true;
	}
}
