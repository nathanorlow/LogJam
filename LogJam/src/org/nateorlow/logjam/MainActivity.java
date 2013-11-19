package org.nateorlow.logjam;

import android.os.Bundle;

import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Random;

import junit.framework.Assert;
//import static java.lang.System.out;
  
public class MainActivity extends Activity {
    Button b1, b2;
    int i;
    Random r = new Random();
    int[] count=new int[5];
    
    int score=0;
    int currentlog=0;
    int lastchosen=0;
    public static final int CAPACITY=5;
    public static final int MAXLOG=3;
    TextView current_log_text_view;
    LinearLayout[] stream_row = new LinearLayout[5];
    Button[] stream_btn = new Button[5];

    ImageView[] log_samples = new ImageView[3];
    
    private static final int[] BUTTON_IDS = {
        R.id.stream_one,
        R.id.stream_two, 
        R.id.stream_three,
        R.id.stream_four,
        R.id.stream_five
    };
    private static final int[] TABLE_ROWS={
    	R.id.row1,
    	R.id.row2,
    	R.id.row3,
    	R.id.row4,
    	R.id.row5
    };
    private static final int[] LOG_IMAGES={
    	R.drawable.log1,
    	R.drawable.log2,
    	R.drawable.log3
    };//size should equal MAXLOG
    
    @Override
   	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	   b2 = (Button) findViewById(R.id.exit_button);
	   current_log_text_view= (TextView) findViewById(R.id.current_log_id);
	   currentlog=r.nextInt(MAXLOG)+1;
	   
	   b2.setOnClickListener(new OnClickListener()
	        {
	        	public void onClick(View arg0){
	        		finish();//exit
	        	}
	        });
	   
	   for(i=0; i<=4; i++){
		   stream_btn[i]= (Button) findViewById(BUTTON_IDS[i]);
		   count[i]= 0;
		   stream_btn[i].setTag(i);
		   stream_row[i]=(LinearLayout) findViewById(TABLE_ROWS[i]);
		   }
	   
	   for(i=0; i<5; i++){
	   stream_btn[i].setOnClickListener(new OnClickListener(){
		   public void onClick(View v){
			   //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
			   i=(Integer) v.getTag();
			   //System.out.println("Counting with i="+i);
			   count[i]+=currentlog;
			   if (count[lastchosen]==0){
				   stream_row[lastchosen].removeAllViews();
			   }
			   stream_btn[i].setText(Integer.toString(count[i]));
			   ImageView newImage=new ImageView(getBaseContext());
			   Assert.assertTrue("Log to place must be positive", currentlog>0);
			   Assert.assertTrue("Log to place is too large", currentlog<=MAXLOG);
			   System.out.println("Placing log with size "+Integer.toString(currentlog));
			   newImage.setImageResource(LOG_IMAGES[currentlog-1]);
			   //newImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			   stream_row[i].addView(newImage);
			   if (count[i]>CAPACITY){
				   stream_btn[i].setText("Bust!");
				   count[i]=0;
			   }
			   else if(count[i]==CAPACITY){
				   stream_btn[i].setText("Score!");
				   count[i]=0;
			   }

			   currentlog=r.nextInt(MAXLOG)+1;
			   current_log_text_view.setText(Integer.toString(currentlog));
			   lastchosen=i;

		   }
	});
	   }
	}
	   
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

   }
