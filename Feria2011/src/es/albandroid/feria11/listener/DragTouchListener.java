package es.albandroid.feria11.listener;

import es.albandroid.feria11.HorariosActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

public class DragTouchListener implements View.OnTouchListener{

	private static final int offset=120;
	private int X,Y;
	private int altura,maxAltura;
	private HorariosActivity h;
	private android.widget.ListView lista ;
	 
	public static boolean pulsado=true;

	public DragTouchListener(HorariosActivity h){		
		super();
		X=-1;
		Y=-1;
		this.h=h;
		lista=(ListView) h.findViewById(android.R.id.list);
		altura= h.getResources().getDisplayMetrics().heightPixels;
		
	}

	@Override
	public synchronized boolean onTouch(View v, MotionEvent event) {


		int eventaction = event.getAction(); 

		int X = (int)event.getX(); 
		int Y = (int)event.getY(); 

		try{
		switch (eventaction ) { 

		case MotionEvent.ACTION_DOWN: 

			this.X=X;
			this.Y=Y;
//			lista=(ListView) h.findViewById(android.R.id.list);
//			lista.onTouchEvent(event);
			break; 
		case MotionEvent.ACTION_MOVE:   
			//Se calcula si mueves a izqda o derecha	        	
			if(X-this.X>offset){
				if(pulsado){
//					h.mostrarEventos(h.getFecha(),-1);h.izquierda();
//					h.botonAtras(new View(h));
					pulsado=false;
					lista.scrollTo(0,0);
				}
			}else if(this.X-X>offset){
					if(pulsado){
//						h.mostrarEventos(h.getFecha(),1);
//						h.botonSiguiente(new View(h));
						pulsado=false;
						lista.scrollTo(0,0);
					}
			}else {
//				
//				if(lista.getScrollY()+(this.Y-Y)>0 )
//					lista.scrollBy(0,(this.Y-Y));	
				
//				this.Y=Y;
				lista=(ListView) h.findViewById(android.R.id.list);
				lista.onTouchEvent(event);
			}
			break; 
		case MotionEvent.ACTION_UP: 
			// touch drop - just do things here after dropping
			this.X=-1;
			this.Y=-1;
			pulsado=true;
			lista=(ListView) h.findViewById(android.R.id.list);
			lista.onTouchEvent(event);
			break; 
			
		default:
			lista=(ListView) h.findViewById(android.R.id.list);
			lista.onTouchEvent(event);
			break;
		} 
		// redraw the canvas

		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return true; 
	}

}
