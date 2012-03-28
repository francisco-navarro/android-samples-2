package es.albandroid.feria11.util.maps;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class MyOverlay extends OverlayItem {
	
	public MyOverlay(GeoPoint point, String title, String snippet) {
		super(point, title, snippet);
		
	}
	public MyOverlay(GeoPoint point, String title, String snippet, Integer id, Integer tip) {
		super(point, title, snippet);
		this.point = point;
		eventid=id;
		this.tipo=tip;
	}
	public GeoPoint point;
	public static String title;
	public Integer eventid;
	public Integer tipo;
	
	public Integer getId() {
		return eventid;
	}
	
	public Integer getTipo() {
		return tipo;
	}
	
	@Override
	public GeoPoint getPoint() {
		return point;
	}

//	public MyOverlay (Double lat, Double lng, String ttl, Integer id){
//		latitude=lat;
//		longitude=lng;
//		title=ttl;
//		eventid=id;
//	}
//	public boolean onTouchEvent(MotionEvent e, MapView mapView) {
//		// Aqui debería crearse un intent de la actividad de detalle evento, ponerle la id del evento, y abrirlo
//		return super.onTouchEvent(e, mapView);
//	}
//	
//	@Override
//	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
//		
//		Projection projection = mapView.getProjection();
//		Double lat = latitude; 
//	    Double lng = longitude;
//	    GeoPoint geoPoint = new GeoPoint(lat.intValue(), lng.intValue());
//	    
//		if (shadow == false) {
//			Point myPoint = new Point();
//			projection.toPixels(geoPoint, myPoint);
//			// Create and setup your paint brush
//			Paint paint = new Paint();
//			paint.setARGB(250, 255, 126, 0);
//			paint.setAntiAlias(true);
//			paint.setFakeBoldText(true);			
//			paint.setTextSize(20.0f);
//			paint.setShadowLayer(3,3,3,0xff000000);
//			// Create the circle
//			int rad = 8;
//			RectF oval = new RectF(myPoint.x-rad, myPoint.y-rad,
//			myPoint.x+rad, myPoint.y+rad);
//			// Draw on the canvas
//			canvas.drawOval(oval, paint);
//			canvas.drawText(title, myPoint.x+rad, myPoint.y, paint);
//			canvas.setDensity(4);
//		}
//		else {
//
//		}
//	}
//	@Override
//	public boolean onTap(GeoPoint point, MapView mapView) {
//		return false;
//	}
}