package es.albandroid.feria11.util;

import android.content.Context;
import android.widget.Button;

public class TogButton extends Button{
	private Context cntxt;
	Button button;
	private boolean checked;

	public TogButton(Context context, Button btn) {
		super(context);
		cntxt=context;
		button=btn;
//		button.setText(button.getText() + (new StringBuffer().append((char)10)).toString());
	}

	

	@Override
	public void setOnClickListener(OnClickListener l){
		super.setOnClickListener(l);
	}
	
}
