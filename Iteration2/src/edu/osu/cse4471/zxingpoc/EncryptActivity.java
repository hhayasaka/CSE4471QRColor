package edu.osu.cse4471.zxingpoc;
import java.util.Random;

import edu.osu.cse4471.encryption.Crypto;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class EncryptActivity extends Activity{
	private Spinner spinner1, spinner2, spinner3;
	Button button;
	String[] color_array = {"  Red", "  Green", "  Blue",
			  "  Black", "  Violet", "  Brown", "  Fuschia", "  Orange", " Turquoise"};
	
	/* ID for activity passing from MainActivity */
	public final static String DISPLAY_MESSAGE = "edu.osu.cse4471.zxingpoc.MainActivity";

	/* a String of all the valid letters used in random password generation */
	public final static String ALPHABET = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	
	
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	
	       
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_encrypt);
	        addListenerOnSpinnerItemSelection();
	       
	    }
	
	public void addListenerOnSpinnerItemSelection() {
	    	spinner1 = (Spinner) findViewById(R.id.Spinner04);
	    	spinner1.setAdapter(new MyCustomAdapter(EncryptActivity.this, R.layout.row, color_array));
	    	spinner2 = (Spinner) findViewById(R.id.Spinner05);
	    	spinner2.setAdapter(new MyCustomAdapter(EncryptActivity.this, R.layout.row, color_array));
	    	spinner3 = (Spinner) findViewById(R.id.Spinner06);
	    	spinner3.setAdapter(new MyCustomAdapter(EncryptActivity.this, R.layout.row, color_array));
	      }
	
	
	
	
	
	/**
	 * This method encrypts the text that is in the edit_message EditText field.
	 * 
	 * @param view
	 *            the current View object
	 */
	public void encryptMessage(View view) {
		/* acquire password and plain text fields */
		EditText plainText = (EditText) findViewById(R.id.edit_message);
		EditText password = (EditText) findViewById(R.id.password);

		/* handle null plain text exception */
		if (plainText.getText().toString().equals("")) {
			plainText.setText(generateRandomPassword(ALPHABET, 8));
		}

		/* handle null password exception */
		if (password.getText().toString().equals("")) {
			password.setText(generateRandomPassword(ALPHABET, 8));
		}
		
		
		String color1 =String.valueOf(spinner1.getSelectedItem());
		String color2 =String.valueOf(spinner2.getSelectedItem());
		String color3 = String.valueOf(spinner3.getSelectedItem());
		
		

		/* generate salt values for symmetric key encryption */
		byte[] salt = Crypto.saltShaker(password.getText().toString(),
				ConvertToColor(color1), ConvertToColor(color2), ConvertToColor(color3));

		/* generate cipher text */
		String cipherText = Crypto
				.encrypt(salt, plainText.getText().toString());

		/* clears password after encrypting */
		password.setText("");

		/*
		 * TODO [DELETEME before due] use this for copy/paste ecrypted QR code
		 * generator
		 */
		Log.d("ENCRYPT", cipherText);

		/* launches DisplayScannedQRCode activity to display cipher text */
		Intent dipslayMessage = new Intent(this, DisplayScannedQRCode.class);
		dipslayMessage.putExtra(DISPLAY_MESSAGE, cipherText);
		startActivity(dipslayMessage);
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

