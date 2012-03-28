package com.alopec.rpg.client;



import com.alopec.rpg.client.utils.Constants;
import com.alopec.rpg.reflection.Tasks;
import com.alopec.rpg.reflection.util.ExecuteResponse;
import com.alopec.rpg.udp.UDPClient;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private TextView TextUser,TextPass,textError;
	private ProgressBar barra;
	private String user,pass;
	private UDPClient cliente ;
	private MainActivity instance;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        cliente = new UDPClient(Constants.HOST);
        instance=this;
        //Intent intent = new Intent(this,WorldActivity.class);
		//startActivity(intent);
    }
    
    public void doClick(View v){
    	TextUser=(TextView) findViewById(R.id.TextUser);
    	TextPass=(TextView) findViewById(R.id.TextPass);    
    	barra=(ProgressBar)findViewById(R.id.progressBar1);
    	textError=(TextView) findViewById(R.id.textError);
    	textError.setText("");
    	
    	user=TextUser.getText().toString();
    	pass=TextPass.getText().toString();
    	    	 
    	barra.setVisibility(View.VISIBLE);
    	
    	new LoginTask().execute();
    	
    }
    
    public void loginFailed(){
		barra.setVisibility(View.INVISIBLE);
		textError.setText(R.string.error_login);
    }
    public void loginOK(){
    	WorldActivity.login=user;
		Intent intent = new Intent(instance,WorldActivity.class);
		startActivity(intent);
    }
    
    class LoginTask extends AsyncTask<Void, Void, Void> {
    	ExecuteResponse respuesta;
		@Override
		protected Void doInBackground(Void... arg0) {
			respuesta=cliente.run(
					Tasks.doLogin(user, pass));
			
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			if(respuesta!=null && respuesta.getMsg().equals(Tasks.RETURN_LOGIN_OK) ){
				instance.loginOK();
			}else{
				instance.loginFailed();
			}
		}
		
	}
}