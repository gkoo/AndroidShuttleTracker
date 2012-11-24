package com.example.hellogooglemaps;

import java.util.Date;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.Toast;

public class HelloGoogleMaps extends MapActivity {
	
	private MapView mapview;
	private Date lastTouchTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mapview = (MapView) findViewById(R.id.mapview);
		mapview.setBuiltInZoomControls(true);
		
		// Build overlays
		List<Overlay> mapOverlays = mapview.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.androidmarker);
		HelloItemizedOverlay itemizedOverlay = new HelloItemizedOverlay(drawable, this);
		
		// Create GeoPoint
		GeoPoint point = new GeoPoint(19240000,-99120000);
		OverlayItem overlayItem = new OverlayItem(point, "Hola, Mundo!", "I'm in Mexico City!");
		
		GeoPoint point2 = new GeoPoint(35410000, 139460000);
		OverlayItem overlayItem2 = new OverlayItem(point2, "Sekai, konichiwa!", "I'm in Japan!");
		
		itemizedOverlay.addOverlay(overlayItem);
		itemizedOverlay.addOverlay(overlayItem2);
		mapOverlays.add(itemizedOverlay);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_hello_google_maps, menu);
		return true;
	}
	
	@Override
	public boolean isRouteDisplayed() {
		return false;
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent evt) {
		int actionType = evt.getAction();
		switch(actionType) {
		case MotionEvent.ACTION_UP:
			Date touchTime = new Date();
			int MAX_DOUBLE_TAP_DURATION = 300;
			if (lastTouchTime != null && touchTime.getTime() - lastTouchTime.getTime() < MAX_DOUBLE_TAP_DURATION) {
				// We've got a double tap!
	            Projection proj = mapview.getProjection();
	            MapController mController = mapview.getController();
	            mController.zoomInFixing((int)evt.getX(), (int)evt.getY());
			}
			else {
				lastTouchTime = touchTime;
			}
		}
		return super.dispatchTouchEvent(evt);
	}
	
	private void zoomInOnPoint(GeoPoint point) {
		MapController mController = mapview.getController();
		mController.setCenter(point);
		mController.zoomIn();
	}

}
