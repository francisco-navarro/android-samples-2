package es.albandroid.feria11;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import es.albandroid.feria11.bbdd.DBAdapter;
import es.albandroid.feria11.beans.Evento;
import es.albandroid.feria11.util.Constants;

import es.albandroid.feria11.util.images.Iconos;
import es.albandroid.feria11.util.maps.MapaPreferences;
import es.albandroid.feria11.util.maps.MiPosicionOverlay;
import es.albandroid.feria11.util.maps.MyItemizedOverlay;
import es.albandroid.feria11.util.maps.MyOverlay;
import es.albandroid.feria11.util.maps.MyPathOverLay;
import es.albandroid.feria11.util.maps.autobuses.LineaF1;
import es.albandroid.feria11.util.maps.autobuses.LineaF2;
import es.albandroid.feria11.util.maps.autobuses.LineaF3;
import es.albandroid.feria11.util.maps.autobuses.LineasPreferences;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ZoomControls;

public class MapaFeriaActivity extends MapActivity {

	//	private LinearLayout linearLayout;
	public MapView mapView;
	List<Overlay> mapOverlays;
	private MiPosicionOverlay myLocationOverlay;
	private MapController mc;
	private MapaPreferences preferencias;
	private LineasPreferences lineas;
	private boolean single=false;
	private static boolean askGPS=true;
	private boolean Satelite = true;
	Button mapmode;
	Evento evento;
	int maxicon = Iconos.getMaxIcons();
	int maxtipo = MapaPreferences.CLAVES_TIPOS.length;
	DBAdapter db = new DBAdapter(this);	

	private boolean located=false;

	private static final int initZoom=18;

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapa) ;
		preferencias=new MapaPreferences(this);
		lineas = new LineasPreferences(this);
		mapmode = (Button)findViewById(R.id.mapButton);
		Satelite=preferencias.isSateliteEnabled();
		if(Satelite)
			mapmode.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_menu_mapmode_selected, 0, 0);
		else 
			mapmode.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_menu_mapmode_normal, 0, 0);
		mapView = (MapView) findViewById(R.id.vistaDeMapa);
		mapView.setSatellite(Satelite); 
		mapView.setStreetView(false);
		mapOverlays = mapView.getOverlays();
		myLocationOverlay = new MiPosicionOverlay(this, mapView);
		mc = mapView.getController();
		ZoomControls zoomControls = (ZoomControls) findViewById(R.id.zoomcontrols);
		zoomControls.setOnZoomInClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mc.zoomIn();
			}
		});
		zoomControls.setOnZoomOutClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mc.zoomOut();
			}
		});
		mapView.setBuiltInZoomControls(false);
		mapView.postInvalidate();
		//Variables de control y Extras
		//		int tipo=0;
		int id=0;
		Bundle extras = getIntent().getExtras(); 
		//Comprobacion de extras en intent
		if (extras != null) {
			//			tipo = extras.getInt("tipo");
			id = extras.getInt(Constants.PARAM_ID_EVENTO);
			if (id>0) {
				single = true;
			}
		}
		//Mostrar posición
		initMap();
		db.open();

		mostrarEventosDia();
		if(single) {
			evento = db.getEventoById(id);

			Drawable drawable = Iconos.get(this, evento.getIcono());
			MyItemizedOverlay itemizedOverlay = new MyItemizedOverlay(drawable, mapView);
			OverlayItem overlayItem = new MyOverlay(creaPunto(evento.getCoordenada_x(), evento.getCoordenada_y()), 
					evento.getNombre(), 
					evento.getDescripcion(), 
					evento.getId(), 
					evento.getTipo());
			itemizedOverlay.addOverlay(overlayItem);
			mapOverlays.add(itemizedOverlay);
			mc.animateTo(creaPunto(evento.getCoordenada_x(), evento.getCoordenada_y()));
			itemizedOverlay.onTap(creaPunto(evento.getCoordenada_x(), evento.getCoordenada_y()), mapView);
		}
		else {
			if(located)
				mc.animateTo(myLocationOverlay.getMyLocation());
			else {
				mc.animateTo(creaPunto(38.997274, -1.870422));
			}
		}


		mc.setZoom(initZoom);
		mapView.postInvalidate();
		if(askGPS)
			preguntaActivaGPS();



	}
	private void preguntaActivaGPS() {

		askGPS=false;
		if(!isGPSactive()){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("El GPS hace que nuestra posición en el mapa sea más precisa. ¿Desea abrir la configuración para activarlo?")
			.setCancelable(false)
			.setPositiveButton("Si", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					//MapaFeriaActivity.this.finish();
					//Mostrar para activar el gps
					startActivityForResult(new
							Intent(android.provider.Settings.ACTION_SECURITY_SETTINGS),0); 
				}
			})
			.setNegativeButton("No", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
			});
			AlertDialog alert = builder.create();
			alert.show();
		}
	}

	private void initMap() {

		mapView.getOverlays().add(myLocationOverlay);
		//		        myLocationOverlay.enableCompass();
		myLocationOverlay.enableMyLocation();
		myLocationOverlay.runOnFirstFix(new Runnable() {
			@Override
			public void run() {
				located=true;
			}
		});
	} 


	public void centerMap(View view){
		if(located){
			mc.animateTo(myLocationOverlay.getMyLocation());
			//			DrawPath(myLocationOverlay.getMyLocation(),creaPunto(38.997274, -1.870422),Color.GREEN, mapView);
		}
	}

	public void clickBuses(View view){
		lineas.mostrarAlert();	
	}

	private boolean isGPSactive(){

		LocationManager manager = (LocationManager) this.getSystemService( Context.LOCATION_SERVICE );

		if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
			return false;
		}

		return true;
	}

	public void clickTipos(View view){
		preferencias.mostrarAlert();
	}

	public GeoPoint creaPunto(Double lngi, Double lati){
		Double lat = lngi;
		lat=lat*1E6;
		Double lng = lati; 
		lng=lng*1E6;
		GeoPoint point = new GeoPoint(lat.intValue(), 
				lng.intValue());
		return point;
	}

	public void mostrarEventosDia(){
		mapOverlays.clear();
		mapView.postInvalidate();
		ArrayList<Evento> eventos = new ArrayList<Evento>();
		initMap();
//		GregorianCalendar dia = new GregorianCalendar();
//		dia.setTime(new Date(System.currentTimeMillis()));
		GregorianCalendar dia = new GregorianCalendar(2011, 8, 13);
		for (int i = 0; i<=maxicon;i++){
			//			db.open();
			eventos = (ArrayList<Evento>)
			//				db.getEventoDiaIcono(new Date(System.currentTimeMillis()), i);
			db.getEventoDiaIcono(new Date(dia.getTimeInMillis()), i);
			//			db.close();
			Drawable drawable = Iconos.get(this, i);
			MyItemizedOverlay itemizedOverlay = new MyItemizedOverlay(drawable, mapView);
			for (int j=0; j<eventos.size(); j++){
				if(preferencias.isEnabledTipo(eventos.get(j).getTipo())){
					OverlayItem overlayItem = new MyOverlay(
							creaPunto(eventos.get(j).getCoordenada_x(),eventos.get(j).getCoordenada_y()), 
							eventos.get(j).getNombre(), 
							eventos.get(j).getDescripcion(), 
							eventos.get(j).getId(), 
				 			eventos.get(j).getTipo());
					itemizedOverlay.addOverlay(overlayItem);
				}
			}
			

			if (itemizedOverlay.size()>0)
				mapOverlays.add(itemizedOverlay);
			mapView.postInvalidate();
		}

		if (lineas.isEnabledLinea(0)){
			mapOverlays.add(new LineaF1());
			Drawable icono = Iconos.getBus(this, 1);
			MyItemizedOverlay itemizedLineaBus = new MyItemizedOverlay(icono, mapView);
			//Saco las paradas
			eventos = (ArrayList<Evento>)db.getEventoTipo(LineaF1.TIPO);
			for (int j=0; j<eventos.size(); j++){					
					OverlayItem overlayItem = new MyOverlay(
							creaPunto(eventos.get(j).getCoordenada_x(),eventos.get(j).getCoordenada_y()), 
							eventos.get(j).getNombre(), 
							eventos.get(j).getDescripcion(), 
							eventos.get(j).getId(), 
							eventos.get(j).getTipo());
					itemizedLineaBus.addOverlay(overlayItem);
			}	
			mapOverlays.add(itemizedLineaBus);
		}
		if (lineas.isEnabledLinea(1)){
			mapOverlays.add(new LineaF2());
			Drawable icono = Iconos.getBus(this, 2);
			MyItemizedOverlay itemizedLineaBus = new MyItemizedOverlay(icono, mapView);
			//Saco las paradas
			eventos = (ArrayList<Evento>)db.getEventoTipo(LineaF2.TIPO);
			for (int j=0; j<eventos.size(); j++){					
					OverlayItem overlayItem = new MyOverlay(
							creaPunto(eventos.get(j).getCoordenada_x(),eventos.get(j).getCoordenada_y()), 
							eventos.get(j).getNombre(), 
							eventos.get(j).getDescripcion(), 
							eventos.get(j).getId(), 
							eventos.get(j).getTipo());
					itemizedLineaBus.addOverlay(overlayItem);
			}
			mapOverlays.add(itemizedLineaBus);
		}
		if (lineas.isEnabledLinea(2)){
			mapOverlays.add(new LineaF3());
			Drawable icono = Iconos.getBus(this, 3);
			MyItemizedOverlay itemizedLineaBus = new MyItemizedOverlay(icono, mapView);
			//Saco las paradas
			eventos = (ArrayList<Evento>)db.getEventoTipo(LineaF3.TIPO);
			for (int j=0; j<eventos.size(); j++){					
					OverlayItem overlayItem = new MyOverlay(
							creaPunto(eventos.get(j).getCoordenada_x(),eventos.get(j).getCoordenada_y()), 
							eventos.get(j).getNombre(), 
							eventos.get(j).getDescripcion(), 
							eventos.get(j).getId(), 
							eventos.get(j).getTipo());
					itemizedLineaBus.addOverlay(overlayItem);
			}
			mapOverlays.add(itemizedLineaBus);
		}
		
		
		
	}
	public void MapMode(View view){
		if(Satelite)
			mapmode.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_menu_mapmode_normal, 0, 0);
		else 
			mapmode.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_menu_mapmode_selected, 0, 0);
		Satelite=!Satelite;
		preferencias.editarSatelite(Satelite);
		mapView.setSatellite(Satelite);
		mapView.postInvalidate();
	}

	@Override
	public void onPause() {
		super.onPause();
		myLocationOverlay.disableMyLocation();
		db.close();
	}
	@Override
	public void onResume() {
		super.onResume();
		myLocationOverlay.enableMyLocation();
		db.open();
	}

	public void clearMap(){
		mapOverlays.clear();

	}

	//	GeoPoint geoPoint = new GeoPoint((int)(Double.parseDouble(latitude[0])*1E6),(int)(Double.parseDouble(longitude[0])*1E6));

	public String getUrl(GeoPoint src, GeoPoint dest){

		StringBuilder urlString = new StringBuilder();

		urlString.append("http://maps.google.com/maps?f=d&hl=en");
		urlString.append("&saddr="); //from
		urlString.append(Double.toString(src.getLatitudeE6() / 1.0E6));
		urlString.append(",");
		urlString.append(Double.toString(src.getLongitudeE6() / 1.0E6));
		urlString.append("&daddr=");// to
		urlString.append(Double.toString(dest.getLatitudeE6() / 1.0E6));
		urlString.append(",");
		urlString.append(Double.toString(dest.getLongitudeE6() / 1.0E6));
		urlString.append("&ie=UTF8&0&om=0&output=kml");

		return urlString.toString();
	} 

	public GeoPoint getMyLocation(){
		if (located)
			return myLocationOverlay.getMyLocation();
		else 
			return creaPunto(38.997274, -1.870422);
	}

	public void DrawPath(GeoPoint src, GeoPoint dest, int color, MapView mMapView01) 
	{ 
		ProgressDialog dialog = ProgressDialog.show(this, "", 
				"Cargando Ruta ...", true);
		// connect to map web service 
		StringBuilder urlString = new StringBuilder(); 
		urlString.append("http://maps.google.com/maps?f=d&hl=en"); 
		urlString.append("&saddr=");//from 
		urlString.append( Double.toString(src.getLatitudeE6()/1.0E6 )); 
		urlString.append(","); 
		urlString.append( Double.toString(src.getLongitudeE6()/1.0E6 )); 
		urlString.append("&daddr=");//to 
		urlString.append( Double.toString(dest.getLatitudeE6()/1.0E6 )); 
		urlString.append(","); 
		urlString.append( Double.toString(dest.getLongitudeE6()/1.0E6 )); 
		urlString.append("&ie=UTF8&0&om=0&output=kml"); 
		Log.d("xxx","URL="+urlString.toString()); 
		// get the kml (XML) doc. And parse it to get the coordinates(direction route). 
		Document doc = null; 
		HttpURLConnection urlConnection= null; 
		URL url = null; 
		try 
		{ 
			url = new URL(urlString.toString()); 
			urlConnection=(HttpURLConnection)url.openConnection(); 
			urlConnection.setRequestMethod("GET"); 
			urlConnection.setDoOutput(true); 
			urlConnection.setDoInput(true); 
			urlConnection.connect(); 

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
			DocumentBuilder db = dbf.newDocumentBuilder(); 
			doc = db.parse(urlConnection.getInputStream()); 

			if(doc.getElementsByTagName("GeometryCollection").getLength()>0) 
			{ 
				//String path = doc.getElementsByTagName("GeometryCollection").item(0).getFirstChild().getFirstChild().getNodeName(); 
				String path = doc.getElementsByTagName("GeometryCollection").item(0).getFirstChild().getFirstChild().getFirstChild().getNodeValue() ; 
				Log.d("xxx","path="+ path); 
				String [] pairs = path.split(" "); 
				String[] lngLat = pairs[0].split(","); // lngLat[0]=longitude lngLat[1]=latitude lngLat[2]=height 
				// src 
				GeoPoint startGP = new GeoPoint((int)(Double.parseDouble(lngLat[1])*1E6),(int)(Double.parseDouble(lngLat[0])*1E6)); 
				mMapView01.getOverlays().add(new MyPathOverLay(startGP,startGP,1)); 
				GeoPoint gp1; 
				GeoPoint gp2 = startGP; 
				for(int i=1;i<pairs.length;i++) // the last one would be crash 
				{ 
					lngLat = pairs[i].split(","); 
					gp1 = gp2; 
					// watch out! For GeoPoint, first:latitude, second:longitude 
					gp2 = new GeoPoint((int)(Double.parseDouble(lngLat[1])*1E6),(int)(Double.parseDouble(lngLat[0])*1E6)); 
					mMapView01.getOverlays().add(new MyPathOverLay(gp1,gp2,2,color)); 
					Log.d("xxx","pair:" + pairs[i]); 
				} 
				mMapView01.getOverlays().add(new MyPathOverLay(dest,dest, 3)); // use the default color 
			} 
		} 
		catch (MalformedURLException e) 
		{ 
			e.printStackTrace(); 
		} 
		catch (IOException e) 
		{ 
			e.printStackTrace(); 
		} 
		catch (ParserConfigurationException e) 
		{ 
			e.printStackTrace(); 
		} 
		catch (SAXException e) 
		{ 
			e.printStackTrace(); 
		} 
		dialog.cancel();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) { // Handle item selection 
		switch (item.getItemId()) { 
		case 1: 
			Intent a = new Intent (getApplicationContext(), MenuPrincipalActivity.class);
			a.putExtra("file", "");
			startActivity(a); 
			return true; 
		case 2: 
			Intent b = new Intent (getApplicationContext(), CreditosActivity.class);
			startActivity(b);
			return true;
		default: return super.onOptionsItemSelected(item); 
		} 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.add(1,1,0,"Principal").setIcon(android.R.drawable.ic_menu_revert);
		menu.add(1,2,0,"Creditos").setIcon(android.R.drawable.ic_menu_info_details);
		return true;
	}

}
