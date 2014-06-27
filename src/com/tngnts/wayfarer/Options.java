package com.tngnts.wayfarer;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.RatingBar;
import android.widget.TextView;

public class Options extends Activity {

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options);

		DisplayMetrics metrics = getResources().getDisplayMetrics();
		final int width = metrics.widthPixels;
		final int height = metrics.heightPixels;
		
		final TextView carTrip = (TextView)findViewById(R.id.carTrip);
		final TextView flightTrip = (TextView)findViewById(R.id.flightTrip);
		
		final NumberPicker numAdultsPicker = (NumberPicker)findViewById(R.id.numAdultPicker);
		final NumberPicker numChildrenPicker = (NumberPicker)findViewById(R.id.numChildrenPicker);
		
		final Button submitButton = (Button)findViewById(R.id.submitButton);
		
		final RatingBar hotelRating = (RatingBar)findViewById(R.id.hotelRating);
		
		final DatePicker departurePicker = (DatePicker)findViewById(R.id.leaveDate);
		final DatePicker arrivalPicker = (DatePicker)findViewById(R.id.returnDate);
		
		numAdultsPicker.setMaxValue(99);
		numChildrenPicker.setMaxValue(99);
		
		carTrip.setOnClickListener(new OnClickListener() {		
			public void onClick(View v) {
				// TODO Auto-generated method stub
				carTrip.setTextColor(Color.parseColor("#cccccc"));
				flightTrip.setTextColor(Color.parseColor("#565656"));
			}
		});
		
		flightTrip.setOnClickListener(new OnClickListener() {		
			public void onClick(View v) {
				// TODO Auto-generated method stub
				flightTrip.setTextColor(Color.parseColor("#cccccc"));
				carTrip.setTextColor(Color.parseColor("#565656"));
			}
		});
		
		submitButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean byCar;
				String dateOfDepart;
				String dateOfArrival;
				int numAdults;
				int numChildren;
				double minHotelRating;
				
				if(carTrip.getCurrentTextColor()==-3355444) {
					byCar = true;
				} else {
					byCar = false;
				}
				
				numAdults = numAdultsPicker.getValue();
				numChildren = numChildrenPicker.getValue();
				minHotelRating = (double)hotelRating.getRating();

				Calendar rightNow = Calendar.getInstance();
				String month=rightNow.get(Calendar.MONTH)+1+"";

			    int monthInt = Integer.parseInt(month);
			    
			    int departMonthInt =monthInt+departurePicker.getMonth()-1;
			    String departureMonthString = ""+departMonthInt;
			    
			    if(departureMonthString.length()<2){
			    	departureMonthString="0"+departureMonthString;   
			    } 
			    
			    int arriveMonthInt = monthInt+arrivalPicker.getMonth()-1;
			    
			    String arrivalMonthString = ""+arriveMonthInt;
			    
			    if(arrivalMonthString.length()<2){
			    	arrivalMonthString="0"+arrivalMonthString;   
			    } 
			    
			    String departDay = ""+departurePicker.getDayOfMonth();
			    if(departDay.length()<2){
			    	departDay="0"+departDay;   
			    } 
			    
			    String arrivalDay = ""+arrivalPicker.getDayOfMonth();
			    if(arrivalDay.length()<2){
			    	arrivalDay="0"+arrivalDay;   
			    } 
			    
			    dateOfDepart = ""+departurePicker.getYear()+"-"+departureMonthString+"-"+departDay;
				dateOfArrival = ""+arrivalPicker.getYear()+"-"+arrivalMonthString+"-"+arrivalDay;
				Log.wtf("please god let this work", "date of depart: "+dateOfDepart+", dateOFArrival: " + dateOfArrival);
				
				final double budget;
				
				Bundle extras = getIntent().getExtras();
				if(extras!=null)
					budget = extras.getDouble("budget");
				else
					budget=0.0;
				Intent i = new Intent(getApplicationContext(), Results.class);
				i.putExtra("byCar",byCar);
				i.putExtra("dateOfDepart",dateOfDepart);
				i.putExtra("dateOfArrival",dateOfArrival);
				i.putExtra("numAdults",numAdults);
				i.putExtra("numChildren",numChildren);
				i.putExtra("minHotelRating",minHotelRating);
				i.putExtra("budget", budget);
			    startActivity(i);
			    overridePendingTransition(R.anim.slide_in_results, R.anim.slide_out_results);
			}
		});

	}
	
}