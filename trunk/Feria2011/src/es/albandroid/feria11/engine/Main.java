package es.albandroid.feria11.engine;

public class Main {

	 private static Main INSTANCE=null;
	 static{
		 INSTANCE=new Main();
	 }
	 
	 //Constructor por defecto
	 private Main(){
		 
	 }
	 
	 @Override
	public Object clone() throws CloneNotSupportedException {
	        throw new CloneNotSupportedException(); 
	}
	 
	 public static Main getInstance(){
		 return INSTANCE;
	 }
}
