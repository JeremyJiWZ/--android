package wj.airwar;

/**
* Created by jiwentadashi on 15/11/25.
*/

public class Airplane
{
	Plane[] plane = new Plane[3];
	int alive;
	int[][] board;//0:nothing, 1:body, 2:head, 3:hit

	Airplane()
	{
		board = new int [10][10];
		reset();
	}

	public void reset()
	{
		plane[0] = new Plane();
		plane[1] = new Plane();
		plane[2] = new Plane();
		alive = 3;
		for (int i=0; i<10; i++)
			for (int j=0; j<10; j++)
				board[i][j] = 0;
	}

//place the planes
	//返回false表示要把该飞机放回棋盘外
	public boolean placePlane(int num,int row,int col,int d)
	{
		//get real row col
		Tran.in(row, col, d);
		row = Tran.realRow;
		col = Tran.realCol;
		return placePlanePv(num,row,col,d);
	}

	private boolean placePlanePv(int num,int row,int col,int d)
	{
		//is re-place
		unplace(num);

		//out of field
		if (d==0 && !(row >=3 && row <=9 && col >=2 && col <=7)) return false;
		if (d==1 && !(row >=2 && row <=7 && col >=0 && col <=6)) return false;
		if (d==2 && !(row >=0 && row <=6 && col >=2 && col <=7)) return false;
		if (d==3 && !(row >=2 && row <=7 && col >=3 && col <=9)) return false;

		//collision
		for (int i=0; i<10; i++)
			if ( board[ row + SHAPE[d][i][0] ][ col + SHAPE[d][i][1] ] != 0 ) return false;

		//place
		board[row][col] = 2;
		for (int i=1; i<10; i++)
			board[ row+SHAPE[d][i][0] ][ col+SHAPE[d][i][1] ] = 1;
		plane[num].place(row, col, d);
		plane[num].isPlace = true;

		return true;
	}

	public void unplace(int num)
	{
		if (plane[num].isPlace)
		{
			plane[num].unplace();
			for (int i = 0; i < 10; i++)
				board[ plane[num].row + SHAPE[plane[num].d][i][0] ][ plane[num].col + SHAPE[plane[num].d][i][1] ] = 0;
		}
	}

	public boolean isAllPlaced()
	{
		return plane[0].isPlace && plane[1].isPlace && plane[2].isPlace;
	}
	public int getRow(int num)
	{
		Tran.outRow(getRowPv(num), plane[num].d);
		return Tran.realRow;
	}

	public int getRowPv(int num)
	{
		return plane[num].row;
	}

	public int getCol(int num)
	{
		Tran.outCol(getColPv(num), plane[num].d);
		return Tran.realCol;
	}

	public int getColPv(int num)
	{
		return plane[num].col;
	}

	public int getDirection(int num)
	{
		return plane[num].d;
	}

	//返回false表示要把该飞机放回棋盘外
	public boolean setDirection(int num, int d)
	{
		if (plane[num].isPlace)
			return placePlanePv(num, getRowPv(num), getColPv(num), d);
		plane[num].d = d;
		return false;
	}
	
//boom the planes
//0:miss,1:injured,2:dead,3:has been boom
	public int boomPlane(int row, int col)
	{
		switch (board[row][col])
		{
			case 0:
				board[row][col] = 3;
				return 0;
			case 1:
				board[row][col] = 3;
				return 1;
			case 2:
				board[row][col] = 3;
				alive--;
				return 2;
			case 3:
				return 3;
		}
		return 0;
	}
	
//tell if all dead
	public boolean isAllDead()
	{
//		return !plane[0].isAlive && !plane[1].isAlive && !plane[2].isAlive;
		if (alive>0) return true;
		return false;
	}

	static final int SHAPE[][][] = {//new int[4][10][2];
			{{0, 0}, {-1, 2}, {-1, 1}, {-1, 0}, {-1, -1}, {-1, -2}, {-2, 0}, {-3, 1}, {-3, 0}, {-3, -1}},
			{{0, 0}, {2, 1}, {1, 1}, {0, 1}, {-1, 1}, {-2, 1}, {0, 2}, {1, 3}, {0, 3}, {-1, 3}},
			{{0, 0}, {1, -2}, {1, -1}, {1, 0}, {1, 1}, {1, 2}, {2, 0}, {3, -1}, {3, 0}, {3, 1}},
			{{0, 0}, {-2, -1}, {-1, -1}, {0, -1}, {1, -1}, {2, -1}, {0, -2}, {-1, -3}, {0, -3}, {1, -3}}
	};

	static class Tran
	{
		static int realRow;
		static int realCol;
		static void in(int row, int col, int d)
		{
			switch (d)
			{
				case 0:
					realRow = row+1;
					realCol = col+2;
					break;
				case 1:
					realRow = row+2;
					realCol = col+3;
					break;
				case 2:
					realRow = row+3;
					realCol = col+2;
					break;
				case 3:
					realRow = row+2;
					realCol = col+1;
					break;
			}
		}
		static void outRow(int row, int d)
		{
			switch (d)
			{
				case 0:
					realRow = row-1;
					break;
				case 1:
					realRow = row-2;
					break;
				case 2:
					realRow = row-3;
					break;
				case 3:
					realRow = row-2;
					break;
			}
		}
		static void outCol(int col, int d)
		{
			switch (d)
			{
				case 0:
					realCol = col-2;
					break;
				case 1:
					realCol = col-3;
					break;
				case 2:
					realCol = col-2;
					break;
				case 3:
					realCol = col-1;
					break;
			}
		}
	}
}

class Plane
{
	int row,col,d;//0:up,1:right,2:down,3:left; 0:tail upwards
	boolean isPlace;

	Plane()
	{
		row = col = -1;d = 0;
		isPlace=false;
	}
	
	void place(int row,int col,int d)
	{
		this.row = row;
		this.col = col;
		this.d = d;
		this.isPlace = true;
	}

	void unplace()
	{
		this.isPlace = false;
	}
}
