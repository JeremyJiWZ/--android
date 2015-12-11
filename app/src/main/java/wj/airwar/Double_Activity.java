package wj.airwar;import android.content.Intent;import android.content.pm.ActivityInfo;import android.net.Uri;import android.os.Bundle;import android.support.v7.app.AppCompatActivity;import android.util.DisplayMetrics;import android.util.Log;import android.view.MotionEvent;import android.view.View;import android.widget.ImageView;import android.widget.RelativeLayout;import com.google.android.gms.appindexing.Action;import com.google.android.gms.appindexing.AppIndex;import com.google.android.gms.common.api.GoogleApiClient;public class Double_Activity extends AppCompatActivity {    private int length, screenWidth, screenHeight;    private ImageView box1, box2;//the board    private int player_turn = 1;//player 1 play first    private Airplane player1,player2;//the player    private ImageView shadow_hor, shadow_ver, indicator;//shadow    //UI controller    private int row = 0, col = 0;    private int lastX = 0, lastY = 0;    RelativeLayout.LayoutParams hor_lay;    RelativeLayout.LayoutParams ver_lay;    /**     * ATTENTION: This was auto-generated to implement the App Indexing API.     * See https://g.co/AppIndexing/AndroidStudio for more information.     */    private GoogleApiClient client;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_double_);        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);        //test        DisplayMetrics dm = getResources().getDisplayMetrics();        screenWidth = dm.widthPixels;        screenHeight = dm.heightPixels - 50;        length = screenHeight / 14;        box_init();        image_init();        Intent intent = getIntent();        player1 = intent.getParcelableExtra("")        box1.setOnTouchListener(new Player_BoomListener());        box2.setOnTouchListener(new Player_BoomListener());        // ATTENTION: This was auto-generated to implement the App Indexing API.        // See https://g.co/AppIndexing/AndroidStudio for more information.        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();    }    private void box_init() {        box1 = (ImageView) findViewById(R.id.box1);        box2 = (ImageView) findViewById(R.id.box2);        RelativeLayout.LayoutParams bp1 = new RelativeLayout.LayoutParams(length * 11, length * 11);        bp1.leftMargin = 0;        bp1.topMargin = length;        box1.setLayoutParams(bp1);        RelativeLayout.LayoutParams bp2 = new RelativeLayout.LayoutParams(length * 11, length * 11);        bp2.leftMargin = length * 12;        bp2.topMargin = length;        box2.setLayoutParams(bp2);    }    private void image_init() {        //initialize the text player1        RelativeLayout.LayoutParams pl1 = new RelativeLayout.LayoutParams(length * 7, length);        pl1.leftMargin = (int) (2.5 * length);        pl1.topMargin = 0;        ImageView p1 = (ImageView) findViewById(R.id.player1);        p1.setLayoutParams(pl1);        //initialize the text player2        RelativeLayout.LayoutParams pl2 = new RelativeLayout.LayoutParams(length * 7, length);        pl2.leftMargin = (int) (14.5 * length);        pl2.topMargin = 0;        ImageView p2 = (ImageView) findViewById(R.id.player2);        p2.setLayoutParams(pl2);        //initialize the indicator        RelativeLayout.LayoutParams idl = new RelativeLayout.LayoutParams(length, length);        idl.leftMargin = 11 * length;        idl.topMargin = 6 * length;        indicator = (ImageView) findViewById(R.id.indicator);        indicator.setLayoutParams(idl);        indicator.setImageResource(R.drawable.indicator_right);        //initialize the shadow        shadow_hor = (ImageView) findViewById(R.id.shadow_horizontal);        shadow_ver = (ImageView) findViewById(R.id.shadow_vertical);        hor_lay = new RelativeLayout.LayoutParams(length * 10, length);        ver_lay = new RelativeLayout.LayoutParams(length, length * 10);        shadow_ver.setLayoutParams(ver_lay);        shadow_hor.setLayoutParams(hor_lay);    }    private void fill_shadow(int player, int row, int col) {        if (player == 1) {            hor_lay.leftMargin = 13 * length;            hor_lay.topMargin = (row + 2) * length;            ver_lay.topMargin = length * 2;            ver_lay.leftMargin = (col + 13) * length;//            Log.e("Inside fill","reset layout");//            Log.e("row",""+row);//            Log.e("col",""+col);        } else {            hor_lay.leftMargin = length;            hor_lay.topMargin = (row + 2) * length;            ver_lay.leftMargin = (col + 1) * length;            ver_lay.topMargin = length * 2;        }//        Log.e("Inside fill","reset layout");//        Log.e("row",""+row);//        Log.e("col",""+col);        shadow_hor.setImageResource(0);        shadow_ver.setImageResource(0);        shadow_hor.setImageResource(R.drawable.fill_horizontal);        shadow_ver.setImageResource(R.drawable.fill_vertical);    }    @Override    public void onStart() {        super.onStart();        // ATTENTION: This was auto-generated to implement the App Indexing API.        // See https://g.co/AppIndexing/AndroidStudio for more information.        client.connect();        Action viewAction = Action.newAction(Action.TYPE_VIEW, // TODO: choose an action type.                "Double_ Page", // TODO: Define a title for the content shown.                // TODO: If you have web page content that matches this app activity's content,                // make sure this auto-generated web page URL is correct.                // Otherwise, set the URL to null.                Uri.parse("http://host/path"),                // TODO: Make sure this auto-generated app deep link URI is correct.                Uri.parse("android-app://wj.airwar/http/host/path"));        AppIndex.AppIndexApi.start(client, viewAction);    }    @Override    public void onStop() {        super.onStop();        // ATTENTION: This was auto-generated to implement the App Indexing API.        // See https://g.co/AppIndexing/AndroidStudio for more information.        Action viewAction = Action.newAction(Action.TYPE_VIEW, // TODO: choose an action type.                "Double_ Page", // TODO: Define a title for the content shown.                // TODO: If you have web page content that matches this app activity's content,                // make sure this auto-generated web page URL is correct.                // Otherwise, set the URL to null.                Uri.parse("http://host/path"),                // TODO: Make sure this auto-generated app deep link URI is correct.                Uri.parse("android-app://wj.airwar/http/host/path"));        AppIndex.AppIndexApi.end(client, viewAction);        client.disconnect();    }    class Player_BoomListener implements ImageView.OnTouchListener {        @Override        public boolean onTouch(View v, MotionEvent event) {            int action = event.getAction();            switch (action) {                case MotionEvent.ACTION_DOWN:                    lastX = (int) event.getRawX();                    lastY = (int) event.getRawY();                    if (player_turn == 1) {                        row = (int) (((lastY - (length * 0.5)) / length) - 3);                        col = (int) ((lastX - length * 0.5) / length - 13);                    } else {                        row = (int) (lastY - length * 0.5)/length - 3;                        col = (int) (lastX - length * 0.5)/length - 1;                    }                    //out of bound                    if (row < 0 || col < 0 || row >=10 || col >= 10) break;                    fill_shadow(player_turn, row, col);//                    Log.e("row",""+row);//                    Log.e("col",""+col);                    break;                case MotionEvent.ACTION_MOVE:                    lastX = (int) event.getRawX();                    lastY = (int) event.getRawY();                    if (player_turn == 1) {                        row = (int) (((lastY - (length * 0.5)) / length) - 3);                        col = (int) ((lastX - length * 0.5) / length - 13);                    } else {                        row = (int) (lastY - length * 0.5)/length - 3;                        col = (int) (lastX - length * 0.5)/length - 1;                    }                    //out of bound                    if (row < 0 || col < 0 || row >= 10 || col >= 10) break;                    fill_shadow(player_turn, row, col);                    Log.e("row", "" + row);                    Log.e("col", "" + col);                    break;                case MotionEvent.ACTION_UP:                    shadow_hor.setImageResource(0);                    shadow_ver.setImageResource(0);                    //out of bound                    if (row < 0 || col < 0 || row >= 10 || col >= 10) break;                    //begin to boom the plane                    if (player_turn == 1) {                        player_turn = 2;                        indicator.setImageResource(R.drawable.indicator_left);                    } else {                        player_turn = 1;                        indicator.setImageResource(R.drawable.indicator_right);                    }                    break;            } return false;        }    }    private void boom(int player, int row,int col){        if (player==1)        {            player1.boomPlane(row,col);        }    }}