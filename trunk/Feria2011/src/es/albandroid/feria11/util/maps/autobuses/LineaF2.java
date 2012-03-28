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

public class LineaF2 extends Overlay{

	public static final Integer TIPO=new Integer(22);
	
	private GeoPoint[] puntos;
	private int color;
	private static final int ancho_linea=5;
	
	public LineaF2(){
		super();
		color=Color.GREEN;
		puntos = new  GeoPoint[]{
				creaPunto(-1.847490,38.996410),
				creaPunto(-1.849080,38.996630),
				creaPunto(-1.849080,38.996630),
				creaPunto(-1.848910,38.997480),
				creaPunto(-1.848800,38.997790),
				creaPunto(-1.848610,38.997960),
				creaPunto(-1.847760,38.998470),
				creaPunto(-1.847760,38.998470),
				creaPunto(-1.848630,38.999340),
				creaPunto(-1.848630,38.999340),
				creaPunto(-1.850860,38.998030),
				creaPunto(-1.850860,38.998030),
				creaPunto(-1.851790,38.997510),
				creaPunto(-1.851790,38.997510),
				creaPunto(-1.852920,38.998710),
				creaPunto(-1.852920,38.998710),
				creaPunto(-1.853720,38.999550),
				creaPunto(-1.853720,38.999550),
				creaPunto(-1.855150,39.001030),
				creaPunto(-1.855150,39.001030),
				creaPunto(-1.854910,39.001060),
				creaPunto(-1.854910,39.001060),
				creaPunto(-1.853370,39.001210),
				creaPunto(-1.853370,39.001210),
				creaPunto(-1.854180,39.003690),
				creaPunto(-1.854180,39.003690),
				creaPunto(-1.854090,39.003730),
				creaPunto(-1.854080,39.003790),
				creaPunto(-1.854110,39.003890),
				creaPunto(-1.854210,39.003910),
				creaPunto(-1.856040,39.003570),
				creaPunto(-1.855970,39.003020),
				creaPunto(-1.855970,39.003020),
				creaPunto(-1.856040,39.002940),
				creaPunto(-1.856040,39.002940),
				creaPunto(-1.856110,39.002850),
				creaPunto(-1.856110,39.002850),
				creaPunto(-1.857580,39.004370),
				creaPunto(-1.857660,39.004410),
				creaPunto(-1.858660,39.005070),
				creaPunto(-1.859040,39.005000),
				creaPunto(-1.859040,39.005000),
				creaPunto(-1.859100,39.005090),
				creaPunto(-1.859190,39.005120),
				creaPunto(-1.859380,39.005050),
				creaPunto(-1.859460,39.005060),
				creaPunto(-1.859870,39.005230),
				creaPunto(-1.862620,39.008070),
				creaPunto(-1.862620,39.008070),
				creaPunto(-1.862820,39.008270),
				creaPunto(-1.862820,39.008270),
				creaPunto(-1.862760,39.008340),
				creaPunto(-1.862830,39.008480),
				creaPunto(-1.862950,39.008550),
				creaPunto(-1.863080,39.008510),
				creaPunto(-1.864690,39.010220),
				creaPunto(-1.864690,39.010220),
				creaPunto(-1.869760,39.006930),
				creaPunto(-1.869660,39.006780),
				creaPunto(-1.869520,39.006770),
				creaPunto(-1.869400,39.006820),
				creaPunto(-1.869400,39.006820),
				creaPunto(-1.868410,39.006070),
				creaPunto(-1.866640,39.004590),
				creaPunto(-1.866330,39.004300),
				creaPunto(-1.866290,39.004150),
				creaPunto(-1.866200,39.003400),
				creaPunto(-1.866120,39.003430),
				creaPunto(-1.866120,39.003430),
				creaPunto(-1.866070,39.003160),
				creaPunto(-1.866280,39.002770),
				creaPunto(-1.866370,39.002690),
				creaPunto(-1.869170,39.001080),
				creaPunto(-1.869870,39.000560),
				 creaPunto(-1.871360,38.998690    ),
				 creaPunto(-1.871520,38.998670    ),
				 creaPunto(-1.871690,38.998580    ),
				 creaPunto(-1.871790,38.998420    ),
				 creaPunto(-1.871750,38.998290    ),
				 creaPunto(-1.871630,38.998230    ),
				 creaPunto(-1.871510,38.998200    ),
				 creaPunto(-1.871320,38.998250    )
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
