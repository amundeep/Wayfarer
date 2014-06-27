package com.tngnts.wayfarer;

import java.text.NumberFormat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class Menu extends Activity {


	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);

		DisplayMetrics metrics = getResources().getDisplayMetrics();
//		final int width = metrics.widthPixels;
		final int height = metrics.heightPixels;
		
		ImageButton rightArrow = (ImageButton)findViewById(R.id.continueButton);
		SeekBar budgetSlider = (SeekBar)findViewById(R.id.budgetSlider);
		ImageButton downArrow = (ImageButton)findViewById(R.id.optionsButton);
	//	EditText budgetText = (EditText)findViewById(R.id.budgetText);
	//	Button button = (Button)findViewById(R.id.testButton);
		
		/*rightArrow.setX(width/2);*/
		Log.w("height",""+height);		
		
		budgetSlider.getThumb().mutate().setAlpha(0);
		
		budgetSlider.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				EditText budgetText = (EditText)findViewById(R.id.budgetText);
				// TODO Auto-generated method stub
				
				NumberFormat budget = NumberFormat.getCurrencyInstance();
				
				final double progressPercent = (double)progress/100.00;
				
				budgetText.setText(budget.format(progressPercent*999));
				
			}
			
			
		});
		
		rightArrow.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent(Menu.this, Results.class);
			    startActivity(i);
			    overridePendingTransition(R.anim.slide_in_results, R.anim.slide_out_results);
			}
		});
		
		downArrow.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Menu.this, Options.class);
				EditText budgetText = (EditText)findViewById(R.id.budgetText);
				double budget = Double.parseDouble(budgetText.getText().toString().substring(1));
				Log.w("budget",""+budget);
				i.putExtra("budget", budget);
			    startActivity(i);
			    overridePendingTransition(R.anim.slide_in_options, R.anim.slide_out_options);
			}
		});		
		
	}
	
}