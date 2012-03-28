package es.albandroid.feria11.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.widget.HorizontalScrollView;

public class HorariosScroll extends HorizontalScrollView {
    public HorariosScroll(Context context) {
		super(context);
		
	}

	private static final int SWIPE_MIN_DISTANCE = 5;
    private static final int SWIPE_THRESHOLD_VELOCITY = 300;
 
    private GestureDetector mGestureDetector;
    private int mActiveFeature = 0;
 
    public HorariosScroll(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
 
    public HorariosScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
 

}
