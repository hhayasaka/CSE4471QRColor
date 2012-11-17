package edu.osu.cse4471.zxingpoc;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	Button button, button2;
	
	/* ID for activity passing from MainActivity */
	public final static String DISPLAY_MESSAGE = "edu.osu.cse4471.zxingpoc.MainActivity";

	/* a String of all the valid letters used in random password generation */
	public final static String ALPHABET = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		addListenerOnButton();
	}
	
 
	public void addListenerOnButton() {
 
		final Context context = this;
 
		button = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.scanQRCode);
		button.setOnClickListener(new OnClickListener() {
 
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, EncryptActivity.class);
                            startActivity(intent);   
 
			}
 
		});
		button2.setOnClickListener(new OnClickListener() {
			 
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, ScanCode.class);
                            startActivity(intent);   
 
			}
 
		});
 
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	


}
