package es.albandroid.feria11.util.maps;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import es.albandroid.feria11.MapaFeriaActivity;

public class MapaView extends MapView {
	
	private final float r=75;
	
	
	private MapaFeriaActivity mainAct;
	private int centerX;
	private int centerY;

	private boolean curDown;
	private int lastdownX;
	private int lastdownY;

	public MapaView(Context context,String apiKey) {
		super(context,apiKey);

	}

	public MapaView(Context context,AttributeSet aset){
		super(context,aset);
	}


	@Override
	public void draw(Canvas canvas) {
/*

		int py=canvas.getClipBounds().bottom/4;
		int px=canvas.getClipBounds().right/4;
		canvas.rotate(r,px,py);
*/
		List<Overlay> overlays = this.getOverlays();

//		MiPosicionOverlay myOverlay = new MiPosicionOverlay();

//		overlays.add(myOverlay);
		
		super.draw(canvas);


	 }
	

}
