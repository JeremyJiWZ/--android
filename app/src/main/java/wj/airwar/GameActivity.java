package wj.airwar;

import android.content.pm.ActivityInfo;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class GameActivity extends AppCompatActivity {

    private int choosenPlane=0;//1 for plane 1, 2 for plane 2, 3 for plane 3
    private int lastX,lastY;
    private int screenWidth,screenHeight;
    private Airplane player1;
    private Airplane player2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels - 50;

        ImageButton spinLeft= (ImageButton)findViewById(R.id.spinLeft);
        ImageButton spinRight = (ImageButton)findViewById(R.id.spinRight);

        ImageView plane1 = (ImageView)findViewById(R.id.plane1);
        ImageView plane2 = (ImageView)findViewById(R.id.plane2);
        ImageView plane3 = (ImageView)findViewById(R.id.plane3);

        plane1.setOnTouchListener(new MovePlaneListener());
        plane2.setOnTouchListener(new MovePlaneListener());
        plane3.setOnTouchListener(new MovePlaneListener());

        spinLeft.setOnClickListener(new LeftSpinListener());
        spinLeft.setOnClickListener(new RightSpinListener());

        Button start = (Button)findViewById(R.id.button);
    }

    class MovePlaneListener implements ImageView.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
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
                    int left = v.getLeft() + dx;
                    int top = v.getTop() + dy;
                    int right = v.getRight() + dx;
                    int bottom = v.getBottom() + dy;
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
                    break;
            }
            if (v.getId()==R.id.plane1)
                choosenPlane=1;
            if (v.getId()==R.id.plane2)
                choosenPlane=2;
            if (v.getId()==R.id.plane3)
                choosenPlane=3;
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
            int dir = player1.getDir();
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
            int dir = player1.getDir();
            dir +=1;//turn right
            if(dir>3)//left
                dir=0;//up
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
}
