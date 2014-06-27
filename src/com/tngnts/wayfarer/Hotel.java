package com.tngnts.wayfarer;

public class Hotel {

	private String myName;
	private double myRating;
	private int myLowRate;
	private int myHighRate;
	private double myLatitude;
	private double myLongitude;
	
	public Hotel(String name, double rating, int lowRate, int highRate,
				 double latitude, double longitude){
		myName = name;
		myRating = rating;
		myLowRate = lowRate;
		myHighRate = highRate;
		myLatitude = latitude;
		myLongitude = longitude;
	}
	
	public String getName(){
		return myName;
	}
	
	public double getRating(){
		return myRating;
	}
	
	public int getLowRate(){
		return myLowRate;
	}
	
	public int getHighRate(){
		return myHighRate;
	}
	
	public double getLatitude(){
		return myLatitude;
	}
	
	public double getLongitude(){
		return myLongitude;
	}
	
	public String toString(){ //for debugging purposes
		return "HOTEL NAME: " + myName + "\n" +
			   "RATING: " + myRating + "\n" +
			   "LOW/HIGH RATE: " + myLowRate + "/" + myHighRate + "\n" +
			   "LAT,LONG: " + "(" + myLatitude + ", " + myLongitude + ")";
	}
}
