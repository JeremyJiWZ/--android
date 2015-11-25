package wj.airwar;

/**
 * Created by jiwentadashi on 15/11/25.
 */
public class Airplane {
    Plane[] airs = new Plane[3];



    //place the planes
    public boolean isAllPlaced() {
//        if(isAllPlaced)
//           return true;
        return false;
    }
    public boolean placePlane(int num,int row,int col,int d){
        return airs[num].place(row, col, d);
    }
    public void reset(){

    }
    //boom the planes
    //0:miss,1:injured,2:dead
    public int boomPlane(int row, int col){


        return 0;
    }
    //tell if all dead
    public boolean isAllDead(){

        return false;
    }




}
class Plane{
    int row,col,d;//0:up,1:right,2:down,3:left
    boolean isPlace;
    boolean isalive;
    void Plane(){
        isPlace=false;
        isalive=false;
    }
    boolean place(int row,int col,int d){
        isPlace=true;
        isalive=true;

        //if(can place)
        // place and return true
        //else
         return false;
    }

}
