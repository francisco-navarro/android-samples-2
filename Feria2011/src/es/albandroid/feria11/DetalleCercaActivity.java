package es.albandroid.feria11;

import java.util.ArrayList;

import es.albandroid.feria11.beans.Noticia;
import es.albandroid.feria11.util.Constants;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetalleCercaActivity extends Activity {

	int idNoticia;
	int idPagina;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    idNoticia=this.getIntent().getExtras().getInt(Constants.PARAM_ID_NOTICIA);
	    idPagina=this.getIntent().getExtras().getInt(Constants.PARAM_PAGINA);
	    
	    ArrayList<Noticia> lista = new ArrayList<Noticia>();

		lista = Noticia.getNoticias(idPagina);
		
		Noticia noticia = new Noticia(
		lista.get(idNoticia).getTitulo(),
		lista.get(idNoticia).getResumen(),
		lista.get(idNoticia).getSubtitulo(),
		lista.get(idNoticia).getCuerpo(),
		lista.get(idNoticia).getImagen(),
		lista.get(idNoticia).getVideo(),
		lista.get(idNoticia).getURL()
		);
		
		TextView titulo = (TextView)findViewById(R.id.noticia_titulo);
		TextView subtitulo = (TextView)findViewById(R.id.noticia_subtitulo);
		TextView cuerpo = (TextView) findViewById(R.id.noticia_cuerpo);
		ImageView imagen = (ImageView) findViewById(R.id.noticia_imagen);
		titulo.setText(noticia.getTitulo());
		subtitulo.setText(noticia.getTitulo());
		cuerpo.setText(noticia.getTitulo());
		
	}

}
