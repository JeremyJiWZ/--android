package wj.airwar;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

    private int choosenPlane = 0;//1 for plane 1, 2 for plane 2, 3 for plane 3
    private int player = 1; //player 1 or player 2
    private int lastX, lastY;
    private RelativeLayout.LayoutParams lp1,lp2,lp3;//layout params for plane1,2,3
    static public Airplane player1 ;
    static public Airplane player2 ;
    ImageView plane1;
    ImageView plane2;
    ImageView plane3;
    static public int screenWidth, screenHeight, length;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels - 50;
        length = screenHeight / 14;
//        Log.i("test", "screenWidth = " + screenWidth);
//        Log.i("test", "screenHeight = " + screenHeight);
//        Log.i("test", "length = " + length);

        player1=new Airplane();
        player2=new Airplane();

        ImageButton spinLeft = (ImageButton) findViewById(R.id.spinLeft);
        ImageButton spinRight = (ImageButton) findViewById(R.id.spinRight);

        plane1 = (ImageView) findViewById(R.id.plane1);
        plane2 = (ImageView) findViewById(R.id.plane2);
        plane3 = (ImageView) findViewById(R.id.plane3);

        lp1 = new RelativeLayout.LayoutParams(length * 5, length * 5);
        lp2 = new RelativeLayout.LayoutParams(length * 5, length * 5);
        lp3 = new RelativeLayout.LayoutParams(length * 5, length * 5);
        lp1.leftMargin = lp2.leftMargin = lp3.leftMargin = length * 12 + length / 2;
        lp1.topMargin = lp2.topMargin = lp3.topMargin = length * 2 + length / 2;
        plane1.setLayoutParams(lp1);
        plane2.setLayoutParams(lp2);
        plane3.setLayoutParams(lp3);
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

        Button start = (Button) findViewById(R.id.button);
        start.setOnClickListener(new StartListener());

        //initialize
        plane1.setImageResource(R.drawable.plane_up);
        plane2.setImageResource(R.drawable.plane_up);
        plane3.setImageResource(R.drawable.plane_up);

        createBox();
    }
    private void createBox(){
        //放置棋盘，做好布局
        ImageView box = (ImageView)findViewById(R.id.box);
        RelativeLayout.LayoutParams bp = new RelativeLayout.LayoutParams(length*10,length*10);
        bp.leftMargin = length*2;
        bp.topMargin = length*2;
        box.setLayoutParams(bp);
        //art number 行
        ImageView one = (ImageView)findViewById(R.id.one);
        ImageView two = (ImageView)findViewById(R.id.two);
        ImageView three = (ImageView)findViewById(R.id.three);
        ImageView four = (ImageView)findViewById(R.id.four);
        ImageView five = (ImageView)findViewById(R.id.five);
        ImageView six = (ImageView)findViewById(R.id.six);
        ImageView seven = (ImageView)findViewById(R.id.seven);
        ImageView eight = (ImageView)findViewById(R.id.eight);
        ImageView nine = (ImageView)findViewById(R.id.nine);
        ImageView ten = (ImageView)findViewById(R.id.ten);

        RelativeLayout.LayoutParams ly_1 = new RelativeLayout.LayoutParams(length,length);
        ly_1.leftMargin = length*2; ly_1.topMargin=length;
        one.setLayoutParams(ly_1);
        RelativeLayout.LayoutParams ly_2 = new RelativeLayout.LayoutParams(length,length);
        ly_2.leftMargin = length*3; ly_2.topMargin=length;
        two.setLayoutParams(ly_2);
        RelativeLayout.LayoutParams ly_3 = new RelativeLayout.LayoutParams(length,length);
        ly_3.leftMargin = length*4; ly_3.topMargin=length;
        three.setLayoutParams(ly_3);
        RelativeLayout.LayoutParams ly_4 = new RelativeLayout.LayoutParams(length,length);
        ly_4.leftMargin = length*5; ly_4.topMargin=length;
        four.setLayoutParams(ly_4);
        RelativeLayout.LayoutParams ly_5 = new RelativeLayout.LayoutParams(length,length);
        ly_5.leftMargin = length*6; ly_5.topMargin=length;
        five.setLayoutParams(ly_5);
        RelativeLayout.LayoutParams ly_6 = new RelativeLayout.LayoutParams(length,length);
        ly_6.leftMargin = length*7; ly_6.topMargin=length;
        six.setLayoutParams(ly_6);
        RelativeLayout.LayoutParams ly_7 = new RelativeLayout.LayoutParams(length,length);
        ly_7.leftMargin = length*8; ly_7.topMargin=length;
        seven.setLayoutParams(ly_7);
        RelativeLayout.LayoutParams ly_8 = new RelativeLayout.LayoutParams(length,length);
        ly_8.leftMargin = length*9; ly_8.topMargin=length;
        eight.setLayoutParams(ly_8);
        RelativeLayout.LayoutParams ly_9 = new RelativeLayout.LayoutParams(length,length);
        ly_9.leftMargin = length*10; ly_9.topMargin=length;
        nine.setLayoutParams(ly_9);
        RelativeLayout.LayoutParams ly_10 = new RelativeLayout.LayoutParams(length,length);
        ly_10.leftMargin = length*11; ly_10.topMargin=length;
        ten.setLayoutParams(ly_10);

        //art number 列
        ImageView one_col = (ImageView)findViewById(R.id.one_col);
        ImageView two_col = (ImageView)findViewById(R.id.two_col);
        ImageView three_col = (ImageView)findViewById(R.id.three_col);
        ImageView four_col = (ImageView)findViewById(R.id.four_col);
        ImageView five_col = (ImageView)findViewById(R.id.five_col);
        ImageView six_col = (ImageView)findViewById(R.id.six_col);
        ImageView seven_col = (ImageView)findViewById(R.id.seven_col);
        ImageView eight_col = (ImageView)findViewById(R.id.eight_col);
        ImageView nine_col = (ImageView)findViewById(R.id.nine_col);
        ImageView ten_col = (ImageView)findViewById(R.id.ten_col);

        RelativeLayout.LayoutParams ly_1_col = new RelativeLayout.LayoutParams(length,length);
        ly_1_col.leftMargin = length; ly_1_col.topMargin=length*2;
        one_col.setLayoutParams(ly_1_col);
        RelativeLayout.LayoutParams ly_2_col = new RelativeLayout.LayoutParams(length,length);
        ly_2_col.leftMargin = length; ly_2_col.topMargin=length*3;
        two_col.setLayoutParams(ly_2_col);
        RelativeLayout.LayoutParams ly_3_col = new RelativeLayout.LayoutParams(length,length);
        ly_3_col.leftMargin = length; ly_3_col.topMargin=length*4;
        three_col.setLayoutParams(ly_3_col);
        RelativeLayout.LayoutParams ly_4_col = new RelativeLayout.LayoutParams(length,length);
        ly_4_col.leftMargin = length; ly_4_col.topMargin=length*5;
        four_col.setLayoutParams(ly_4_col);
        RelativeLayout.LayoutParams ly_5_col = new RelativeLayout.LayoutParams(length,length);
        ly_5_col.leftMargin = length; ly_5_col.topMargin=length*6;
        five_col.setLayoutParams(ly_5_col);
        RelativeLayout.LayoutParams ly_6_col = new RelativeLayout.LayoutParams(length,length);
        ly_6_col.leftMargin = length; ly_6_col.topMargin=length*7;
        six_col.setLayoutParams(ly_6_col);
        RelativeLayout.LayoutParams ly_7_col = new RelativeLayout.LayoutParams(length,length);
        ly_7_col.leftMargin = length; ly_7_col.topMargin=length*8;
        seven_col.setLayoutParams(ly_7_col);
        RelativeLayout.LayoutParams ly_8_col = new RelativeLayout.LayoutParams(length,length);
        ly_8_col.leftMargin = length; ly_8_col.topMargin=length*9;
        eight_col.setLayoutParams(ly_8_col);
        RelativeLayout.LayoutParams ly_9_col = new RelativeLayout.LayoutParams(length,length);
        ly_9_col.leftMargin = length; ly_9_col.topMargin=length*10;
        nine_col.setLayoutParams(ly_9_col);
        RelativeLayout.LayoutParams ly_10_col = new RelativeLayout.LayoutParams(length,length);
        ly_10_col.leftMargin = length; ly_10_col.topMargin=length*11;
        ten_col.setLayoutParams(ly_10_col);

    }

    class MovePlaneListener implements ImageView.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            int left;
            int top;
            int right;
            int bottom;
            if (v.getId() == R.id.plane1) choosenPlane = 1;
            if (v.getId() == R.id.plane2) choosenPlane = 2;
            if (v.getId() == R.id.plane3) choosenPlane = 3;
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
                    int row = 0, col = 0;
                    row = top/length-2;
                    col = (int) (left/length-2);
                    //校正
                    v.layout((int) ((col+2)*length+0.65*length), (int) ((row+2)*length+0.65*length),
                            (int) ((col+7)*length+0.65*length), (int) ((row+7)*length+0.65*length));
//                    v.layout(length / 2 + left / length * length, length / 2 + top / length * length, length / 2 + right / length * length, length / 2 + bottom / length * length);
//                    row = top/length-2;
//                    col = left/length-2;
                    Log.e("row",""+row);
                    Log.e("col",""+col);

                    if (player == 1) {
                        boolean test;//for test
                        test=player1.placePlane(choosenPlane - 1, row, col, player1.getDirection(choosenPlane - 1));
                        Log.e("is placed?",""+test);
                        Log.e("d", ""+player1.getDirection(choosenPlane-1));
                    }
                    if (player == 2) {
                        player2.placePlane(choosenPlane - 1, row, col, player2.getDirection(choosenPlane - 1));
//                        Log.e("is placed?", "" + test);
                        Log.e("d", "" + player1.getDirection(choosenPlane - 1));
                    }
                    break;
            }
            return false;
        }
    }

    //set direction 0:up,1:right,2:down,3:left
    private void setDir(ImageView plane, int dir) {

        if (dir == 0)  //up
            plane.setImageResource(R.drawable.plane_up);
        if (dir == 1) plane.setImageResource(R.drawable.plane_right);
        if (dir == 2) plane.setImageResource(R.drawable.plane_down);
        if (dir == 3) plane.setImageResource(R.drawable.plane_left);
    }

    class LeftSpinListener implements ImageButton.OnClickListener {
        @Override
        public void onClick(View v) {
            int dir;
            if (player==1) dir=player1.getDirection(choosenPlane-1);
            else dir=player2.getDirection(choosenPlane-1);
            dir -= 1;//turn left
            if (dir < 0)//up
                dir = 3;//left
            ImageView plane = null;
            if (choosenPlane == 1) plane = (ImageView) findViewById(R.id.plane1);
            if (choosenPlane == 2) plane = (ImageView) findViewById(R.id.plane2);
            if (choosenPlane == 3) plane = (ImageView) findViewById(R.id.plane3);
            if (plane != null) {
                if (player == 1) {
                    boolean test;
                    test = player1.setDirection(choosenPlane-1,dir);
                    if (test==false) {
                        plane1.layout(2*length,14*length,7*length,19*length);
                        return;
                    }
                }
                else if (player == 2) {
                    player2.setDirection(choosenPlane-1,dir);
                }
                setDir(plane, dir);
            }
        }
    }

    class RightSpinListener implements ImageButton.OnClickListener {
        @Override
        public void onClick(View v) {
            int dir;
            if (player==1) dir=player1.getDirection(choosenPlane-1);
            else dir=player2.getDirection(choosenPlane-1);
            dir += 1;//turn right
            if (dir > 3)//left
                dir = 0;//up
            ImageView plane = null;
//            Log.e("right spin", "onclick");
            if (choosenPlane == 1) plane = (ImageView) findViewById(R.id.plane1);
            if (choosenPlane == 2) plane = (ImageView) findViewById(R.id.plane2);
            if (choosenPlane == 3) plane = (ImageView) findViewById(R.id.plane3);
            if (plane != null) {
                if (player == 1) {
                    Log.e("set dir",""+dir);
                    player1.setDirection(choosenPlane-1,dir);
                }
                else if (player == 2) {
                    player2.setDirection(choosenPlane-1,dir);
                }
                //show the rotate plane
                setDir(plane, dir);
            }
        }
    }

    private void resetPlane() {
        //reset the plane  for player 2 to get start
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(length * 5, length * 5);
        lp.leftMargin = length * 12 + length / 2;
        lp.topMargin = length * 2 + length / 2;
        plane1.setLayoutParams(lp);
        plane2.setLayoutParams(lp);
        plane3.setLayoutParams(lp);
        plane1.setImageResource(R.drawable.plane_up);
        plane2.setImageResource(R.drawable.plane_up);
        plane3.setImageResource(R.drawable.plane_up);
    }
    private void setPlane(int num, int row, int col){

    }

    class StartListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (player == 1) {
                //if all placed, move on
                if (player1.isAllPlaced()) {
                    player = 2;
                    resetPlane();
                    TextView textView = (TextView) findViewById(R.id.player);
                    textView.setText("Player2");
                    TextView tips = (TextView)findViewById(R.id.tips);
                    tips.setText("请交给玩家2摆放");
                    return;
                }
                //not all placed, not move on
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                    //set title
                    builder.setTitle("提示");
                    //set message
                    builder.setMessage("请将飞机放在格子内，不允许重叠");
                    //set neutral button
                    builder.setNeutralButton("好的", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    });
                    builder.show();
                }
            }
            if (player == 2) {
                if(player2.isAllPlaced()) {
                    Intent intent = new Intent();
                    intent.setClass(GameActivity.this, Double_Activity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                    //set title
                    builder.setTitle("提示");
                    //set message
                    builder.setMessage("请将飞机放在格子内，不允许重叠");
                    //set neutral button
                    builder.setNeutralButton("好的", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    });
                    builder.show();
                }
            }
        }
    }

}
