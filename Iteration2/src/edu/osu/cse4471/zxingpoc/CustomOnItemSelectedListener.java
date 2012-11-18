package edu.osu.cse4471.zxingpoc;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
 
public class CustomOnItemSelectedListener implements OnItemSelectedListener {
 
  public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
	//Toast.makeText(parent.getContext(), 
	//	"OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
	//	Toast.LENGTH_SHORT).show();
  }
 
  public void onNothingSelected(AdapterView<?> arg0) {
	// TODO Auto-generated method stub
  }
 
}

