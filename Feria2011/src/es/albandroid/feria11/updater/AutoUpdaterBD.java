package es.albandroid.feria11.updater;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import es.albandroid.feria11.bbdd.DBAdapter;
import es.albandroid.feria11.util.Constants;

public class AutoUpdaterBD extends AsyncTask<String, Float, Integer> {

	private Context context;
	private AutoUpdaterPreferences preferences;
	private String FILENAME;
	
	private final int maxBuffer=512;
	
	private final static ArrayList<Mirror> listaMirror= new ArrayList<Mirror>();
	static{
		listaMirror.add(new Mirror(Constants.URL_VERSION_DB_MIRROR_1,Constants.URL_DB_MIRROR_1));
		listaMirror.add(new Mirror(Constants.URL_VERSION_DB_MIRROR_2,Constants.URL_DB_MIRROR_2));
	}


	public AutoUpdaterBD(Context context){
		
		this.context=context;
		preferences=new AutoUpdaterPreferences(this.context);
		FILENAME=DBAdapter.DATABASE_PATH+DBAdapter.DATABASE_NAME;
		
	}

	private void update (String rutaVersion,String rutaFichero) throws IOException,Exception{
		
		String version=leerVersionBD(rutaVersion);

		if(!version.equals(preferences.getDB_Version())){



			boolean saved=false;	

			File nuevoFile=new File(FILENAME+".last");

			if(nuevoFile.canWrite()){

				if(nuevoFile.exists())
					nuevoFile.delete();
				if(!nuevoFile.createNewFile())
					throw new Exception("No se pudo crear el fichero");					

				saved=leerFichero(rutaFichero,nuevoFile);					

				if(saved){

					preferences.setDB_Version(version);
				}
			}

		}
	}
	
	private String leerVersionBD(String ruta) throws MalformedURLException,IOException  {
		

		URL url = new URL(ruta);
		URLConnection con;
		
		
		con = url.openConnection();

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));

		String linea;
		while ((linea = in.readLine()) != null) {
			try{
				if(linea.startsWith("VERSION"))
					return linea;
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	private boolean leerFichero(String ruta,File nuevoFile) throws Exception {
		
		FileOutputStream fOut=new FileOutputStream(nuevoFile);
		
		URL url = new URL(ruta);
		URLConnection con;
		
		try {
			con = url.openConnection();

			InputStream in =(con.getInputStream());


			int len;
			long total=0;			
			byte[] buffer=new byte[maxBuffer];
			
			while ((len = in.read(buffer)) != -1) {
				fOut.write(buffer, 0, len);
				total+=len;
			}
			
			Log.i("Saved "+total, "Saved "+total+" bytes.");
				
			fOut.close();
			 
		} catch (IOException e1) {			
			return false;
		}
		return true;
	
	}

	@Override
	protected Integer doInBackground(String... arg0) {
		
		boolean reintento=false;
		for(int i=0;i<listaMirror.size() && reintento;i++){
			Mirror sitio=listaMirror.get(i);
			try {
				 update(sitio.getURL_VERSION(),sitio.getURL_FICHERO());
				 reintento=false;
			} catch(IOException e) {
				 //Intentamos otro mirror
				reintento=true;
			} catch(Exception e) {
				Log.e("AutoUpdater", "Error I/O");
				return new Integer(-1);
			}
		}	
		
		
		return new Integer(0);
	}

}
