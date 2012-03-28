package es.albandroid.feria11.util.maps;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.location.Location;

import es.albandroid.feria11.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class MiPosicionOverlay extends MyLocationOverlay {
	
	Bitmap marker;
	public MiPosicionOverlay(Context context, MapView mapView) {
		super(context, mapView);
		marker=BitmapFactory.decodeResource(context.getResources(), R.drawable.compass_arrow);
	}
	@Override
	public synchronized void onLocationChanged(Location location) {
		super.onLocationChanged(location);
	}

	@Override
	protected void drawMyLocation(Canvas canvas, MapView mapView, Location lastFix, GeoPoint myLocation, long when) {
		super.drawMyLocation(canvas, mapView, this.getLastFix(), this.getMyLocation(), when);
	}
	
	@Override
	protected void drawCompass(Canvas canvas, float bearing) {
		super.drawCompass(canvas, bearing*(-1));
	}


}
