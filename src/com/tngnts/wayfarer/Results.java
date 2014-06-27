package com.tngnts.wayfarer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol.STYLE;


public class Results extends Activity {
	
	//Attributes
	boolean byCar;
	String dateOfDepart;
	String dateOfArrival;
	int numAdults;
	int numChildren;
	double minHotelRating;
	
	MapView mMap;
	
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.results);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    byCar = extras.getBoolean("byCar");
		    dateOfDepart = extras.getString("dateOfDepart");
		    dateOfArrival = extras.getString("dateOfArrival");
		    numAdults = extras.getInt("numAdults");
		    numChildren = extras.getInt("numChildren");
		    minHotelRating = extras.getDouble("minHotelRating");
		}

		String deviceId =  Secure.getString(getApplicationContext().getContentResolver(),Secure.ANDROID_ID);
		
		final HttpClient httpclient = new DefaultHttpClient();  
		final HttpGet request = new HttpGet("http://api.ean.com/ean-services/rs/hotel/v3/list?apiKey=gycbxfpz9fsy8jrjrg5pshcu&city=sanfrancisco");  
		request.addHeader("deviceId", deviceId);  
		final BasicResponseHandler handler = new BasicResponseHandler();  

		ArrayList<Hotel> hotelList = new ArrayList<Hotel>();
		
		Thread thread = new Thread(new Runnable() {
			public void run() {
				try {
					String result = httpclient.execute(request, handler);
					Log.w("result", result);  //TO PRINT OUT RAW JSON DATA
					final JSONObject obj = new JSONObject(result);
					final JSONObject reallyouterdata = obj.getJSONObject("HotelListResponse");
					final JSONObject outerdata = reallyouterdata.getJSONObject("HotelList");
					final JSONArray geodata = outerdata.getJSONArray("HotelSummary");
					final int n = geodata.length();
                    for (int i = 0; i < n; ++i) {
                            final JSONObject loc = geodata.getJSONObject(i);
                            String name = loc.getString("name");
                            double rating = loc.getDouble("hotelRating");
                            int lowRate = loc.getInt("lowRate");
                            int highRate = loc.getInt("highRate");
                            double lat = loc.getDouble("latitude");
                            double lon = loc.getDouble("longitude");
                            if(rating > 0.0){
                            	Hotel hTemp = new Hotel(name, rating, lowRate, highRate, lat, lon);
	                            Log.w("sigh", hTemp.toString());
	                            Log.w("sigh", "---------------------------------");
                            }
                    }
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} 
				catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		
		thread.start();
		
		Log.w("sigh", "ARRAY SIZE: " + hotelList.size());
//		
//		for(Hotel h : hotelList){
//			Log.w("sigh", h.toString());
//			Log.w("sigh", "---------------------------------");
//		}
//		
		// Retrieve the map and initial extent from XML layout
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		final int width = metrics.widthPixels;
		final int height = metrics.heightPixels;
		
		mMap = (MapView)findViewById(R.id.map);
		// Add dynamic layer to MapView
		mMap.setLayoutParams(new LinearLayout.LayoutParams(width,height/2));
		mMap.addLayer(new ArcGISTiledMapServiceLayer("" +
		"http://services.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer"));
		
		
        //mMapView.addLayer(gLayer);
		
//		mMapView.zoomTo(centerPt, factor)
		
		LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
		
		Location location = (Location)locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		final double lat = location.getLatitude();
		final double lng = location.getLongitude();
		Log.w("lat",""+lat);
		Log.w("lng",""+lng);
		Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());
		List<Address> addresses;
		String city;
		try {
			addresses = gcd.getFromLocation(lat, lng, 1);
			if (addresses.size() > 0) {
			    Log.w("address",addresses.get(0).getLocality());
			    city = addresses.get(0).getLocality().replaceAll("\\s","");
			    Log.w("nospace address",city);
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		SpatialReference webSR = SpatialReference.create(102100);
		Point myPoint = GeometryEngine.project(lng, lat, webSR);

		mMap.zoomTo(myPoint, (float) 19.0);
		GraphicsLayer gLayer = new GraphicsLayer();
		SimpleMarkerSymbol marker = new SimpleMarkerSymbol(Color.RED, 10, STYLE.DIAMOND);
		gLayer.addGraphic(new Graphic(myPoint,marker));
		
		mMap.addLayer(gLayer);
	}
}