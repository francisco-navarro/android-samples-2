/***
 * Copyright (c) 2010 readyState Software Ltd
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package es.albandroid.feria11.util.maps;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import es.albandroid.feria11.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import es.albandroid.feria11.MapaFeriaActivity;

/**
 * A view representing a MapView marker information balloon.
 * <p>
 * This class has a number of Android resource dependencies:
 * <ul>
 * <li>drawable/balloon_overlay_bg_selector.xml</li>
 * <li>drawable/balloon_overlay_close.png</li>
 * <li>drawable/balloon_overlay_focused.9.png</li>
 * <li>drawable/balloon_overlay_unfocused.9.png</li>
 * <li>layout/balloon_map_overlay.xml</li>
 * </ul>
 * </p>
 * 
 * @author Jeff Gilfelt
 *
 */
public class BalloonOverlayView<Item extends OverlayItem> extends FrameLayout {

	private LinearLayout layout;
	private TextView title;
	private TextView snippet;
	private Button go;
	private Context context;
	private GeoPoint point;
	private MapView mapView;
	/**
	 * Create a new BalloonOverlayView.
	 * 
	 * @param context - The activity context.
	 * @param balloonBottomOffset - The bottom padding (in pixels) to be applied
	 * when rendering this view.
	 */
	public BalloonOverlayView(final Context context, final int balloonBottomOffset, MapView mView) {

		super(context);
		this.context=context;
		setPadding(10, 0, 10, balloonBottomOffset);
		layout = new LinearLayout(context);
		layout.setVisibility(VISIBLE);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.balloon_overlay, layout);
		title = (TextView) v.findViewById(R.id.balloon_item_title);
		snippet = (TextView) v.findViewById(R.id.balloon_item_snippet);
		go = (Button) v.findViewById(R.id.balloon_item_goto);
		mapView = mView;
		ImageView close = (ImageView) v.findViewById(R.id.close_img_button);
		close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				layout.setVisibility(GONE);
			}
		});

		go.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				layout.setVisibility(GONE);
				DrawPath(point, Color.GREEN, mapView);
			}
		});
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.NO_GRAVITY;

		addView(layout, params);

	}
	
	/**
	 * Sets the view data from a given overlay item.
	 * 
	 * @param item - The overlay item containing the relevant view data 
	 * (title and snippet). 
	 */
	public void setData(Item item) {
		
		layout.setVisibility(VISIBLE);
		if (item.getTitle() != null) {
			title.setVisibility(VISIBLE);
			title.setText(item.getTitle());
		} else {
			title.setVisibility(GONE);
		}
		if (item.getSnippet() != null) {
			snippet.setVisibility(VISIBLE);
			snippet.setText(item.getSnippet());
		} else {
			snippet.setVisibility(GONE);
		}
		if (((MyOverlay)item).getPoint() != null) {
			go.setVisibility(VISIBLE);
			point = ((MyOverlay)item).getPoint();
		} else {
			go.setVisibility(GONE);
		}
		
	}
	
	public void DrawPath(GeoPoint dest, int color, MapView mMapView01) 
	{ 
		
		ProgressDialog dialog = ProgressDialog.show(context, "", 
                "Cargando Ruta ...", true);
		GeoPoint src = ((MapaFeriaActivity)context).getMyLocation();
		((MapaFeriaActivity)context).mostrarEventosDia();
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


	
	
}
