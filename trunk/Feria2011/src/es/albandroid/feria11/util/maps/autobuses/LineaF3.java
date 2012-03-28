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

public class LineaF3 extends Overlay{

	public static final Integer TIPO=new Integer(23);
	
	private GeoPoint[] puntos;
	private int color;
	private static final int ancho_linea=3;
	
	public LineaF3(){
		super();
		color=Color.BLUE;
		puntos = new  GeoPoint[]{
				crearPunto(-1.858830,38.986410     ),
				crearPunto(-1.8588459491729736,38.98729168009711),
				crearPunto( -1.858663558959961,38.98758244551566   ),
				crearPunto( -1.8567538261413574,38.98976731749565   ),
				/*crearPunto( -1.858040,38.986160    ),
				crearPunto( -1.857910,38.986100    ),
				crearPunto( -1.857770,38.986190    ),
				crearPunto( -1.857800,38.986290    ),
				crearPunto( -1.857890,38.986320    ),
				crearPunto( -1.856730,38.989260    ),*/
				crearPunto(-1.856150,38.989700     ),
				crearPunto( -1.854370,38.987100    ),
				crearPunto( -1.854310,38.986910    ),
				crearPunto( -1.854380,38.986860    ),
				crearPunto( -1.854400,38.986740    ),
				crearPunto( -1.854260,38.986650    ),
				crearPunto( -1.854220,38.986360    ),
				
				crearPunto(-1.854220,38.986360     ),
				crearPunto( -1.853870,38.984270    ),
				
				crearPunto(-1.853870,38.984270     ),
				crearPunto( -1.853800,38.983830    ),
				crearPunto( -1.853820,38.983630    ),
				crearPunto( -1.853940,38.983540    ),
				crearPunto( -1.853970,38.983420    ),
				crearPunto( -1.853970,38.983420    ),
				crearPunto( -1.857130,38.983010    ),
				crearPunto( -1.857760,38.983080    ),
				crearPunto( -1.858700,38.983380    ),
				crearPunto(-1.858700,38.983380     ),
				crearPunto( -1.859020,38.983490    ),
				crearPunto( -1.859040,38.983610    ),
				crearPunto( -1.859140,38.983680    ),
				crearPunto( -1.859280,38.983680    ),
				crearPunto( -1.859390,38.983620    ),
				crearPunto( -1.862940,38.984900    ),
				
				crearPunto(-1.862940,38.984900     ),
				crearPunto( -1.863320,38.985030    ),
				crearPunto( -1.863430,38.985110    ),
				crearPunto( -1.863600,38.985120    ),
				crearPunto( -1.864640,38.985480    ),
				crearPunto( -1.865190,38.985780    ),
				crearPunto( -1.865590,38.986180    ),
				crearPunto( -1.866140,38.987070    ),
				crearPunto( -1.866140,38.987070    ),
				crearPunto(-1.866460,38.987490     ),
				crearPunto( -1.871400,38.994560    ),
				crearPunto( -1.871400,38.994700    ),
				crearPunto( -1.871500,38.994740    ),
				crearPunto( -1.871870,38.995380    ),
				crearPunto( -1.871970,38.995740    ),
				crearPunto( -1.871950,38.995970    ),
				crearPunto( -1.871510,38.998200    ),
				crearPunto( -1.871510,38.998200    )

				};
	}
	
	public GeoPoint crearPunto(Double lngi, Double lati){
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
