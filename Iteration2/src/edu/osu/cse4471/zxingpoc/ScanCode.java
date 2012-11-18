package edu.osu.cse4471.zxingpoc;

import java.util.Random;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import edu.osu.cse4471.encryption.Crypto;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ScanCode extends Activity{
	                        
	private Spinner spinner1, spinner2, spinner3;
	String[] color_array = {"  Red", "  Green", "  Blue",
			  "  Black", "  Violet", "  Brown", "  Fuschia", "  Orange", " Turquoise"};
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	
	       
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_scan);
	        addListenerOnSpinnerItemSelection();
	       
	    }
	
	public void addListenerOnSpinnerItemSelection() {
		spinner1 = (Spinner) findViewById(R.id.Spinner01);
    	spinner1.setAdapter(new MyCustomAdapter(ScanCode.this, R.layout.row, color_array));
    	spinner2 = (Spinner) findViewById(R.id.Spinner02);
    	spinner2.setAdapter(new MyCustomAdapter(ScanCode.this, R.layout.row, color_array));
    	spinner3 = (Spinner) findViewById(R.id.Spinner03);
    	spinner3.setAdapter(new MyCustomAdapter(ScanCode.this, R.layout.row, color_array));
	    }
	
	

	Button button;
	
	/* ID for activity passing from MainActivity */
	public final static String DISPLAY_MESSAGE = "edu.osu.cse4471.zxingpoc.MainActivity";

	/* a String of all the valid letters used in random password generation */
	public final static String ALPHABET = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";


	/* Called when the user clicks the Scan QR Code button */
	public void scanQRCode(View view) {
		// Do something in response to the button being clicked.
		IntentIntegrator integrator = new IntentIntegrator(this);
		integrator.initiateScan();
	}

	/**
	 * 
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {

		if (resultCode == RESULT_OK) {
			IntentResult scanResult = IntentIntegrator.parseActivityResult(
					requestCode, resultCode, intent);

			/* acquire cipher text from QR Code */
			String cipherText = scanResult.getContents();

			/* acquire user password */
			EditText password = (EditText) findViewById(R.id.password);

			/* handle null password field */
			if (password.getText().toString().equals("")) {
				password.setText(generateRandomPassword(ALPHABET, 8));
			}

			/* TODO prompt users for response colors */
			
			String color1 =String.valueOf(spinner1.getSelectedItem());
			String color2 =String.valueOf(spinner2.getSelectedItem());
			String color3 = String.valueOf(spinner3.getSelectedItem());

			
			/* generate salt values for symmetric key */
			byte[] salt = Crypto.saltShaker(password.getText().toString(),
					ConvertToColor(color1), ConvertToColor(color2), ConvertToColor(color3));

			/*
			 * TODO replace Color.BLACK,Color.RED,Color.GREEN with user choices
			 */

			/* clears password */
			password.setText("");

			/* generate the plain text */
			String plainText = Crypto.decrypt(salt, cipherText);

			/* launches DisplayScannedQRCode activity to display plain text */
			Intent dipslayMessage = new Intent(this, DisplayScannedQRCode.class);
			dipslayMessage.putExtra(DISPLAY_MESSAGE, plainText);
			startActivity(dipslayMessage);
		} else if (resultCode == RESULT_CANCELED) {
			/* clears password */
			EditText password = (EditText) findViewById(R.id.password);
			password.setText("");

			/* Toast scan cancelled */
			Toast toast = Toast.makeText(this, "Scan was Cancelled!",
					Toast.LENGTH_LONG);
			toast.setGravity(Gravity.TOP, 25, 400);
			toast.show();
		}
	}
	
	public int ConvertToColor(String val){
		int color=0;
		if (val.equals(" Red")){color=0xffff0000;}
		else if (val.equals("  Green")){color=0xff00ff00;}
		else if (val.equals("  Blue")){color=0xff0000ff;}
		else if (val.equals("  Black")){color=0xff000000;}
		else if (val.equals("  Violet")){color=0xff9900FF;}
		else if (val.equals("  Brown")){color=0xff6C3306;}
		else if (val.equals("  Fuschia")){color=0xffFF00FF;}
		else if (val.equals("  Orange")){color=0xffFF6F00;}
		else if (val.equals(" Turquoise")){color=0xff00CFC0;}
		return color;
	}

	/**
	 * @param alphabet
	 *            a String representing a list of valid chars to appear in the
	 *            String to be generated.
	 * @param length
	 *            an int representing the desired length of the String
	 * @return a random String with the specified length representing a default
	 *         password.
	 */
	private static String generateRandomPassword(String alphabet, int length) {
		Random rand = new Random();
		char[] text = new char[length];
		for (int i = 0; i < length; i++) {
			text[i] = alphabet.charAt(rand.nextInt(alphabet.length()));
		}
		return new String(text);
	}
	
	public class MyCustomAdapter extends ArrayAdapter<String>{

		public MyCustomAdapter(Context context, int textViewResourceId,
		String[] objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		}

		@Override
		public View getDropDownView(int position, View convertView,
		ViewGroup parent) {
		// TODO Auto-generated method stub
		return getCustomView(position, convertView, parent);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return getCustomView(position, convertView, parent);
		}

		public View getCustomView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//return super.getView(position, convertView, parent);

		LayoutInflater inflater=getLayoutInflater();
		View row=inflater.inflate(R.layout.row, parent, false);
		TextView label=(TextView)row.findViewById(R.id.color);
		label.setText(color_array[position]);

		ImageView icon=(ImageView)row.findViewById(R.id.color_icon);

		String c = color_array[position];
		if (c.equals("  Red")){
			icon.setImageResource(R.drawable.red_icon);
		}
		else if (c.equals("  Green")){
			icon.setImageResource(R.drawable.green_icon);
			}
		else if (c.equals("  Blue")){
			icon.setImageResource(R.drawable.blue_icon);
			}
		else if (c.equals("  Black")){
			icon.setImageResource(R.drawable.black_icon);
			}
		else if (c.equals("  Violet")){
			icon.setImageResource(R.drawable.violet_icon);
			}
		else if (c.equals("  Brown")){
			icon.setImageResource(R.drawable.brown_icon);
			}
		else if (c.equals("  Fuschia")){
			icon.setImageResource(R.drawable.fuschia_icon);
			}
		else if (c.equals("  Orange")){
			icon.setImageResource(R.drawable.orange_icon);
			}
		else{
			icon.setImageResource(R.drawable.turquois_icon);
		}

		return row;
		}
		}
}
