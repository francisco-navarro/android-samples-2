package es.albandroid.feria11;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Vibrator;
import android.widget.Toast;

public class OnAlarmReceiver extends BroadcastReceiver {
	private static final String PERFIL="perfil";
	SharedPreferences settings;
    @Override
    public void onReceive(Context context, Intent intent) {
    	String param=intent.getExtras().getString("parametro");
         if(context!=null){
        	 settings = context.getSharedPreferences(PERFIL, Context.MODE_PRIVATE);
        	 Boolean vibrar = settings.getBoolean("Vibracion", true);
        	 if (vibrar) {
        	 Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
             v.vibrate(300);
        	 }
             int icono=R.drawable.icon;
             int NOTIFICATION_ID=1;
	         Notification notificacion=new Notification(icono, 
	        		 "Alarma de evento Feria 2011", System.currentTimeMillis());
	         
	         NotificationManager notificationManager =
	        	 (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
	         
	         
	         String expandedText = "";
	         String expandedTitle = "Alarma de evento Feria 2011";
	         Intent startActivityIntent = new Intent(context, DetalleEventoActivity.class);
	         PendingIntent launchIntent =
	         PendingIntent.getActivity(context, 0, startActivityIntent, 0);
	         notificacion.setLatestEventInfo(context,expandedTitle,expandedText,launchIntent);
	         
	         //Aqui muestro el led
	         notificacion.ledARGB = Color.GREEN;
	         notificacion.ledOffMS = 0;
	         notificacion.ledOnMS = 1;
	         notificacion.flags = notificacion.flags | Notification.FLAG_SHOW_LIGHTS;
	         
	         //La lanzo
	         Boolean notificar = settings.getBoolean("Notificacion", true);
        	 if (notificar) {
	         notificacion.when =
		         java.lang.System.currentTimeMillis();
		         notificationManager.notify(NOTIFICATION_ID,
		         notificacion);
        	 }
         }
    }

	
 }