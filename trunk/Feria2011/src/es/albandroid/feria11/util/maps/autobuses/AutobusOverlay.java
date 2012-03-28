package es.albandroid.feria11.util.maps.autobuses;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class AutobusOverlay extends Overlay{

	 GeoPoint gP1 ;
     GeoPoint gP2 ;
     
     public AutobusOverlay(GeoPoint g1 , GeoPoint g2){
    	super();
    	 gP1=g1;
    	 gP2=g2;
     }
	
	 @Override
	public void draw(Canvas canvas, MapView mapv, boolean shadow){
	        super.draw(canvas, mapv, shadow);

	    Paint   mPaint = new Paint();
	        mPaint.setDither(true);
	        mPaint.setColor(Color.RED);
	        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
	        mPaint.setStrokeJoin(Paint.Join.ROUND);
	        mPaint.setStrokeCap(Paint.Cap.ROUND);
	        mPaint.setStrokeWidth(2);

	       

	        Point p1 = new Point();
	        Point p2 = new Point();

	    Path    path = new Path();

	    Projection projection =mapv.getProjection();

	    projection.toPixels(gP1, p1);
	        projection.toPixels(gP2, p2);

	        path.moveTo(p2.x, p2.y);
	        path.lineTo(p1.x,p1.y);

	        canvas.drawPath(path, mPaint);
	    }


}
