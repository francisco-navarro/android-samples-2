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

public class LineaF1 extends Overlay{
	
	public static final Integer TIPO=new Integer(21);

	
	private GeoPoint[] puntos;
	private int color;
	private static final int ancho_linea=5;
	
	public LineaF1(){
		super();
		color=Color.RED;
		puntos = new  GeoPoint[]{
				creaPunto(-1.846790,38.989410),
				creaPunto(-1.846210,38.990260),
				creaPunto(-1.845520,38.991150),
				creaPunto(-1.845520,38.991150),
				creaPunto(-1.845390,38.991120),
				creaPunto(-1.845260,38.991190),
				creaPunto(-1.844990,38.991630),
				creaPunto(-1.844990,38.991630),
				creaPunto(-1.844970,38.991810),
				creaPunto(-1.845030,38.991870),
				creaPunto(-1.845110,38.991860),
				creaPunto(-1.845110,38.991860),
				creaPunto(-1.845190,38.992050),
				creaPunto(-1.845340,38.992220),
				creaPunto(-1.847360,38.993630),
				creaPunto(-1.847360,38.993630),
				creaPunto(-1.847940,38.994060),
				creaPunto(-1.847940,38.994060),
				creaPunto(-1.848080,38.994160),
				creaPunto(-1.848200,38.994180),
				creaPunto(-1.848350,38.994130),
				creaPunto(-1.848430,38.994020),
				creaPunto(-1.848440,38.993880),
				creaPunto(-1.848370,38.993770),
				creaPunto(-1.847480,38.991920),
				creaPunto(-1.848770,38.989610),
				creaPunto(-1.848770,38.989610),
				creaPunto(-1.848960,38.989570),
				creaPunto(-1.849020,38.989420),
				creaPunto(-1.848950,38.989320),
				creaPunto(-1.848850,38.989290),
				creaPunto(-1.848740,38.988630),
				creaPunto(-1.848740,38.988630),
				creaPunto(-1.848110,38.985770),
				creaPunto(-1.848110,38.985770),
				creaPunto(-1.847820,38.984430),
				creaPunto(-1.847820,38.984430),
				creaPunto(-1.847970,38.984320),
				creaPunto(-1.848020,38.984220),
				creaPunto(-1.848020,38.984220),
				creaPunto(-1.849220,38.984060),
				creaPunto(-1.849220,38.984060),
				creaPunto(-1.852960,38.983570),
				creaPunto(-1.852960,38.983570),
				creaPunto(-1.853360,38.983520),
				creaPunto(-1.853360,38.983520),
				creaPunto(-1.853540,38.983640),
				creaPunto(-1.853540,38.983640),
				creaPunto(-1.853820,38.983630),
				creaPunto(-1.853940,38.983540),
				creaPunto(-1.853970,38.983420),
				creaPunto(-1.853970,38.983420),				
				 creaPunto(-1.856890,38.983020  ),
				 creaPunto(-1.857220,38.983010  ),
				 creaPunto(-1.857670,38.983060  ),
				 creaPunto(-1.859020,38.983490  ),
				 creaPunto(-1.859040,38.983610  ),
				 creaPunto(-1.859140,38.983680  ),
				 creaPunto(-1.859280,38.983680  ),
				 creaPunto(-1.859390,38.983620  ),
				 creaPunto(-1.863320,38.985030  ),
				 creaPunto(-1.863430,38.985110  ),
				 creaPunto(-1.863600,38.985120  ),
				 creaPunto(-1.864640,38.985480  ),
				 creaPunto(-1.865190,38.985780  ),
				 creaPunto(-1.865460,38.986020  ),
				 creaPunto(-1.865790,38.986480  ),
				 creaPunto(-1.866140,38.987070  ),
				 creaPunto(-1.866070,38.987190  ),
				 creaPunto(-1.866110,38.987320  ),
				 creaPunto(-1.866230,38.987400  ),
				 creaPunto(-1.866420,38.987440  ),
				 creaPunto(-1.871340,38.994480  )
				
				};
	}
	
	public GeoPoint creaPunto(Double lngi, Double lati){
		Double lat = lngi;
		lat=lat*1E6;
		Double lng = lati; 
		lng=lng*1E6;
		GeoPoint point = new GeoPoint( 
				lng.intValue(),lat.intValue());
		return point;
	}
	
	 @Override
	public void draw(Canvas canvas, MapView mapv, boolean shadow){
	        super.draw(canvas, mapv, shadow);

	        Paint   mPaint = new Paint();
	        mPaint.setDither(true);
	        mPaint.setColor(color);
	        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
	        mPaint.setStrokeJoin(Paint.Join.ROUND);
	        mPaint.setStrokeCap(Paint.Cap.ROUND);
	        mPaint.setStrokeWidth(ancho_linea);
       
	        Path    path = new Path();

	        Projection projection =mapv.getProjection();
	        
	        for(int i=0;i+1<puntos.length;i++){

	        	Point p1 = new Point();
	 	        Point p2 = new Point();
		        projection.toPixels(puntos[i], p1);
		        projection.toPixels(puntos[i+1],p2);
	
		        path.moveTo(p2.x, p2.y);
		        path.lineTo(p1.x,p1.y);
	        }

	        canvas.drawPath(path, mPaint);
	 }
}
