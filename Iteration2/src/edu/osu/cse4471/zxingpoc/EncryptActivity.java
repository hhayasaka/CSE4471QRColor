package edu.osu.cse4471.zxingpoc;
import java.util.Random;

import edu.osu.cse4471.encryption.Crypto;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EncryptActivity extends Activity implements OnClickListener{
 
	Button button;
	
	/* ID for activity passing from MainActivity */
	public final static String DISPLAY_MESSAGE = "edu.osu.cse4471.zxingpoc.MainActivity";

	/* a String of all the valid letters used in random password generation */
	public final static String ALPHABET = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	
	int selected = -1; // Selected at zero.
	static int value1=-1;
	static int value2=-1;
	static int value3=-1;
	    private static final String TAG = "DialogDemo";
	    private Button showDialogButton1, showDialogButton2, showDialogButton3;
	    private Context mContext;
	
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	
	        Log.i(TAG, "Activity start");
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_encrypt);
	        mContext = this; // to use all around this class
	        initView();
	        initViewAction();
	    }
	
	    private void initView() {
	         showDialogButton1 = (Button) findViewById(R.id.Button04);
	         showDialogButton2= (Button) findViewById(R.id.Button05);
	         showDialogButton3= (Button) findViewById(R.id.Button06);
	    }
	
	    private void initViewAction() {
	        showDialogButton1.setOnClickListener(this);
	        showDialogButton2.setOnClickListener(this);
	        showDialogButton3.setOnClickListener(this);
	    } 
	
	    public void onClick(View view) {
	        if (view.equals(showDialogButton1)) {
	            showDialogButtonClick(1,value1);
	        }
	        if (view.equals(showDialogButton2)) {
	            showDialogButtonClick(2,value2);
	        }
	        if (view.equals(showDialogButton3)) {
	            showDialogButtonClick(3,value3);
	        }
	    }
	
	
	    private void showDialogButtonClick(final int id, int val) {
	        Log.i(TAG, "show Dialog ButtonClick");
	        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);	
	        builder.setTitle("Show dialog");        
	        final CharSequence[] choiceList = {"Red", "Green" , "Blue" , "Black", "Violet", "Brown", "Fuscia", "Orange", "Turquoise" };
	        selected = val;
	        builder.setSingleChoiceItems(choiceList,selected,
	        		new DialogInterface.OnClickListener() {             
	        			public void onClick(DialogInterface dialog, int which) {
	        				selected=which;
	        				}
	        		})
	        .setCancelable(false);
	        builder.setSingleChoiceItems(choiceList,selected,
	        		new DialogInterface.OnClickListener() {             
	        			public void onClick(DialogInterface dialog, int which) {
	        				selected=which;
	        				}
	        		})
	        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog,int which) {
	                    	if (id==1){value1=selected;}
	            	        else if (id==2){value2=selected;}
	            	        else if (id==3){value3=selected;}
	                        //not the same as 'which' above
	                        Log.d(TAG,"Which value="+which);
	                        Log.d(TAG,"Selected value="+selected);
	                        Toast.makeText(
	                                mContext,
	                                "Select "+choiceList[selected] ,
	                                Toast.LENGTH_SHORT
	                                )
	                                .show();
	                    }
	                });
	
	        AlertDialog alert = builder.create();
	        
	        alert.show();
	        
	
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
		
		int color1 = ConvertToColor(value1);
		int color2 = ConvertToColor(value2);
		int color3 = ConvertToColor(value3);


		/* generate salt values for symmetric key encryption */
		byte[] salt = Crypto.saltShaker(password.getText().toString(),
				color1, color2, color3);

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

	public int ConvertToColor(int val){
		int color=0;
		if (val==0){color=0xffff0000;}
		else if (val==1){color=0xff00ff00;}
		else if (val==2){color=0xff0000ff;}
		else if (val==3){color=0xff000000;}
		else if (val==4){color=0xff9900FF;}
		else if (val==5){color=0xff6C3306;}
		else if (val==6){color=0xffFF00FF;}
		else if (val==7){color=0xffFF6F00;}
		else if (val==8){color=0xff00CFC0;}
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
	
}