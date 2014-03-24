package org.nateorlow.logjam;

import android.os.Bundle;

import android.app.Activity;
import android.view.Menu;
import android.view.View;
//import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.graphics.Color;

import java.util.Random;

//import junit.framework.Assert;

//import static java.lang.System.out;

public class MainActivity extends Activity{
	Button b1, b2;
	int i;
	Random r = new Random();
	Stream[] logstream= new Stream[5];

	int score = 0;
	int current_log_size = 0;
	public static final int MAXCAPACITY = 5;
	public static final int MINCAPACITY=1;
	public static final int MAXLOG = 3;
	//TextView current_log_text_view;
	ImageView current_log_img;
	LinearLayout[] stream_row = new LinearLayout[5];
	Button[] stream_btn = new Button[5];
	TextView[] goal_text_view = new TextView[5];
	
	ImageView[] log_samples = new ImageView[3];

	private static final int[] BUTTON_IDS = { R.id.stream_one, R.id.stream_two,
			R.id.stream_three, R.id.stream_four, R.id.stream_five };
	private static final int[] TABLE_ROWS = { R.id.row1, R.id.row2, R.id.row3,
			R.id.row4, R.id.row5 };
	private static final int[] LOG_IMAGES = { R.drawable.log1, R.drawable.log2,
			R.drawable.log3 };// size should equal MAXLOG
	private static final int[] GOAL_IDS = {R.id.goal_one, R.id.goal_two, R.id.goal_three, R.id.goal_four, R.id.goal_five};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//current_log_text_view = (TextView) findViewById(R.id.current_log_id);
		current_log_img = (ImageView) findViewById(R.id.current_log_img_id);
		this.makeNewCurrentLog();
		
		for (i = 0; i <= 4; i++) {//initialize arrays
			stream_btn[i] = (Button) findViewById(BUTTON_IDS[i]);
			goal_text_view[i]= (TextView) findViewById(GOAL_IDS[i]);
			
			logstream[i]=new Stream(i,MINCAPACITY,MAXCAPACITY);
			stream_btn[i].setTag(i);
			stream_row[i] = (LinearLayout) findViewById(TABLE_ROWS[i]);
			goal_text_view[i].setText(Integer.toString(logstream[i].getCapacity()));
		}

		for (i = 0; i < 5; i++) {
			stream_btn[i].setOnTouchListener(new OnTouchListener() {
				public boolean onTouch(View v, MotionEvent me) {
					switch (me.getAction()){
						case MotionEvent.ACTION_DOWN:
							i = (Integer) v.getTag();// get the ID of the button
							addLogToStream(i);
							//end case
					}//end switch
				return false;
				}
			});
		}
	}

	public void addLogToStream(int i){
		if (logstream[i].isFull()){
			logstream[i].resetSelf();
			stream_btn[i].setText("0");
			stream_row[i].removeAllViews();//then clear it
			goal_text_view[i].setText(Integer.toString(logstream[i].getCapacity()));
			stream_row[i].setBackgroundColor(Color.TRANSPARENT);
		}
		else{
			logstream[i].addOn(current_log_size);
			stream_btn[i].setText(Integer.toString(logstream[i].getSum()));//add text (temporarily)
	
			ImageView newImage = new ImageView(getBaseContext());
			newImage.setImageResource(LOG_IMAGES[current_log_size - 1]);
			//newImage.setAdjustViewBounds(true);
			newImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
			stream_row[i].addView(newImage);
			
			if (logstream[i].isTooFull()) {//bust
				stream_row[i].setBackgroundColor(Color.RED);//these are just to replace bust and score, which don't fit
			} else if (logstream[i].isFull()) {//score
				stream_row[i].setBackgroundColor(Color.GREEN);
			}
			this.makeNewCurrentLog();
		}
	}
	
	public void makeNewCurrentLog(){
		current_log_size = r.nextInt(MAXLOG) + 1;//create random next log
		//current_log_text_view.setText(Integer.toString(current_log_size));//update text
		current_log_img.setImageResource(LOG_IMAGES[current_log_size-1]);		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
}
