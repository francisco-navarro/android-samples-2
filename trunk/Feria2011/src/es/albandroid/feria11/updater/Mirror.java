package es.albandroid.feria11.updater;

public class Mirror {
	
	public Mirror(String uRLVERSION, String uRLFICHERO) {
		super();
		URL_VERSION = uRLVERSION;
		URL_FICHERO = uRLFICHERO;
	}
	private String URL_VERSION;
	private String URL_FICHERO;
	
	public String getURL_VERSION() {
		return URL_VERSION;
	}
	public void setURL_VERSION(String uRLVERSION) {
		URL_VERSION = uRLVERSION;
	}
	public String getURL_FICHERO() {
		return URL_FICHERO;
	}
	public void setURL_FICHERO(String uRLFICHERO) {
		URL_FICHERO = uRLFICHERO;
	}
}
