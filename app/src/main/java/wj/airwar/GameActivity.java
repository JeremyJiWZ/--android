package wj.airwar;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private int choosenPlane=0;//1 for plane 1, 2 for plane 2, 3 for plane 3
    private int player=1; //player 1 or player 2
    private int lastX,lastY;
    private int screenWidth,screenHeight,length;
    private Airplane player1 = new Airplane();
    private Airplane player2 = new Airplane();
    ImageView plane1;
    ImageView plane2;
    ImageView plane3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels - 50;
        length = screenHeight/14;
        Log.i("test", "screenWidth = "+screenWidth);
        Log.i("test", "screenHeight = "+screenHeight);
        Log.i("test", "length = "+length);

        ImageButton spinLeft= (ImageButton)findViewById(R.id.spinLeft);
        ImageButton spinRight = (ImageButton)findViewById(R.id.spinRight);

        plane1 = (ImageView)findViewById(R.id.plane1);
        plane2 = (ImageView)findViewById(R.id.plane2);
        plane3 = (ImageView)findViewById(R.id.plane3);

        RelativeLayout.LayoutParams lp =
                new RelativeLayout.LayoutParams(length * 5, length * 5);
        lp.leftMargin = length * 12 + length / 2;
        lp.topMargin = length * 2 + length / 2;
        plane1.setLayoutParams(lp);
        plane2.setLayoutParams(lp);
        plane3.setLayoutParams(lp);
//
//        plane1.setX(length * 12 + length / 2);
//        plane1.setY(length * 2 + length / 2);
//        plane2.setX(length * 12 + length / 2);
//        plane2.setY(length * 2 + length / 2);
//        plane3.setX(length * 12 + length / 2);
//        plane3.setY(length * 2 + length / 2);

        plane1.setOnTouchListener(new MovePlaneListener());
        plane2.setOnTouchListener(new MovePlaneListener());
        plane3.setOnTouchListener(new MovePlaneListener());

        spinLeft.setOnClickListener(new LeftSpinListener());
        spinRight.setOnClickListener(new RightSpinListener());

        Button start = (Button)findViewById(R.id.button);
        start.setOnClickListener(new StartListener());

        //initialize
        plane1.setImageResource(R.drawable.plane_up);
        plane2.setImageResource(R.drawable.plane_up);
        plane3.setImageResource(R.drawable.plane_up);

    }

    class MovePlaneListener implements ImageView.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            int left;
            int top;
            int right;
            int bottom;
            if (v.getId()==R.id.plane1)
                choosenPlane=1;
            if (v.getId()==R.id.plane2)
                choosenPlane=2;
            if (v.getId()==R.id.plane3)
                choosenPlane=3;
//            Log.e("choosen plane",""+choosenPlane);
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
//                    Log.e("Notice:x",lastX+"\n");
//                    Log.e("Notice:y",lastY+"\n");
                    break;
                case MotionEvent.ACTION_MOVE:
                    int dx = (int) event.getRawX() - lastX;
                    int dy = (int) event.getRawY() - lastY;
                    left = v.getLeft() + dx;
                    top = v.getTop() + dy;
                    right = v.getRight() + dx;
                    bottom = v.getBottom() + dy;
                    //test
//                    Log.e("Notice:x",event.getRawX()+"\n");
//                    Log.e("Notice:y",event.getRawY()+"\n");
//                    Log.e("Notice:dx",dx+"\n");
//                    Log.e("Notice:dy",dy+"\n");
                    if (left < 0) {
                        left = 0;
                        right = left + v.getWidth();
                    }
                    if (right > screenWidth) {
                        right = screenWidth;
                        left = right - v.getWidth();
                    }
                    if (top < 0) {
                        top = 0;
                        bottom = top + v.getHeight();
                    }
                    if (bottom > screenHeight) {
                        bottom = screenHeight;
                        top = bottom - v.getHeight();
                    }
                    v.layout(left, top, right, bottom);
                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_UP:
                    left = v.getLeft();
                    top = v.getTop();
                    right = v.getRight();
                    bottom = v.getBottom();
                    int row=0,col=0;
                    v.layout(length/2+left/length*length, length/2+top/length*length,
                            length/2+right/length*length, length/2+bottom/length*length);
                    if (player == 1) {
                        player1.placePlane(choosenPlane - 1, row, col, player1.getDirection(choosenPlane - 1));
                    }
                    if (player == 2)
                        player2.placePlane(choosenPlane-1,row,col,player2.getDirection(choosenPlane-1));
                    break;
            }
        return false;
        }
    }

    //set direction 0:up,1:right,2:down,3:left
    private void setDir(ImageView plane,int dir){

        if(dir==0)  //up
            plane.setImageResource(R.drawable.plane_up);
        if(dir==1)
            plane.setImageResource(R.drawable.plane_right);
        if(dir==2)
            plane.setImageResource(R.drawable.plane_down);
        if(dir==3)
            plane.setImageResource(R.drawable.plane_left);
    }



    class LeftSpinListener implements ImageButton.OnClickListener{
        @Override
        public void onClick(View v) {
            int dir = player1.getDirection(choosenPlane - 1);
            dir -=1;//turn left
            if(dir<0)//up
                dir=3;//left
            ImageView plane=null;
            if (choosenPlane==1)
                plane = (ImageView)findViewById(R.id.plane1);
            if (choosenPlane==2)
                plane = (ImageView)findViewById(R.id.plane2);
            if (choosenPlane==3)
                plane = (ImageView)findViewById(R.id.plane3);
            if (plane !=null)
                setDir(plane,dir);

        }
    }
    class RightSpinListener implements ImageButton.OnClickListener{
        @Override
        public void onClick(View v) {
            int dir = player1.getDirection(choosenPlane - 1);
            dir +=1;//turn right
            if(dir>3)//left
                dir=0;//up
            ImageView plane=null;
            Log.e("right spin","onclick");
            if (choosenPlane==1)
                plane = (ImageView)findViewById(R.id.plane1);
            if (choosenPlane==2)
                plane = (ImageView)findViewById(R.id.plane2);
            if (choosenPlane==3)
                plane = (ImageView)findViewById(R.id.plane3);
            if (plane !=null)
                setDir(plane,dir);

        }
    }
    private void resetPlane(){
//        pla
        RelativeLayout.LayoutParams lp =
                new RelativeLayout.LayoutParams(length * 5, length * 5);
        lp.leftMargin = length * 12 + length / 2;
        lp.topMargin = length * 2 + length / 2;
        plane1.setLayoutParams(lp);
        plane2.setLayoutParams(lp);
        plane3.setLayoutParams(lp);
        plane1.setImageResource(R.drawable.plane_up);
        plane2.setImageResource(R.drawable.plane_up);
        plane3.setImageResource(R.drawable.plane_up);
    }
    class StartListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if (player==1) {
                //if(player1 planes are placed correctly)
                player = 2;
                resetPlane();
                TextView textView = (TextView)findViewById(R.id.player);
                textView.setText("Player2");
                return;
            }
            if (player==2){
                //if(player2 planes are placed correctly)

                Intent intent = new Intent();
                intent.setClass(GameActivity.this,Double_Activity.class);
                startActivity(intent);
                return;
            }
        }
    }

}
