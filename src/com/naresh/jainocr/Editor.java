package com.naresh.jainocr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Editor extends Activity {
	Button btnReadSDFile;
	EditText txtData;
	Button savevala;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editor);
		btnReadSDFile = (Button) findViewById(R.id.editorbtn);
		txtData=(EditText) findViewById(R.id.editorbox);
		savevala=(Button) findViewById(R.id.savekar);
		
		
	
	btnReadSDFile.setOnClickListener(new OnClickListener() {

	public void onClick(View v) {
		// write on SD card file data in the text box
	try {
		File myFile = new File("/sdcard/ocr.txt");
		FileInputStream fIn = new FileInputStream(myFile);
		BufferedReader myReader = new BufferedReader(
				new InputStreamReader(fIn));
		String aDataRow = "";
		String aBuffer = "";
		while ((aDataRow = myReader.readLine()) != null) {
			aBuffer += aDataRow + "\n";
		}
		txtData.setText(aBuffer);
		myReader.close();
		Toast.makeText(getBaseContext(),
				"Done reading SD 'mysdfile.txt'",
				Toast.LENGTH_SHORT).show();
	} catch (Exception e) {
		Toast.makeText(getBaseContext(), e.getMessage(),
				Toast.LENGTH_SHORT).show();
	}
	}// onClick
	}); // btnReadSDFile
	 savevala.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					String mediaState = Environment.getExternalStorageState();
			        if(mediaState.equalsIgnoreCase(Environment.MEDIA_MOUNTED))
			        {    
		            File myFile = new File(Environment.getExternalStorageDirectory() +"/ocr.txt");
		            myFile.createNewFile();
		            FileOutputStream fOut = new FileOutputStream(myFile);
		            OutputStreamWriter myOutWriter = 
		                                    new OutputStreamWriter(fOut);
		            myOutWriter.append(txtData.getText());
		            myOutWriter.close();
		            fOut.close();
		            Toast.makeText(getBaseContext(),
		                    "Done writing SD 'ocr.txt'",
		                    Toast.LENGTH_SHORT).show();
			        }
			        else
			        {}
		        } catch (Exception e) {
		            Toast.makeText(getBaseContext(), e.getMessage(),
		                    Toast.LENGTH_SHORT).show();
		        }
			}
		});

}
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		 super.onCreateOptionsMenu(menu);
		MenuInflater blowup = getMenuInflater();
		blowup.inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
	     
	    switch (item.getItemId())
	    {
	    case R.id.files:
	        // Single menu item is selected do something
	        // Ex: launching new activity/screen or show alert message
	        Toast.makeText(Editor.this, "Showing files", Toast.LENGTH_SHORT).show();
	    	Intent ishow = new Intent("com.naresh.jainocr.LISTVIEW");
	    	startActivity(ishow);
	        return true;

	    case R.id.main:
	        Toast.makeText(Editor.this, "Loading Main page", Toast.LENGTH_SHORT).show();
	        Intent ishowm = new Intent("com.naresh.jainocr.MAIN");
	    	startActivity(ishowm);
	        return true;

	    case R.id.back:
	        Toast.makeText(Editor.this, "Back", Toast.LENGTH_SHORT).show();
	        Intent ishowb = new Intent("com.naresh.jainocr.ListView");
	    	startActivity(ishowb);
	        return true;

	    case R.id.help:
	        Toast.makeText(Editor.this, "Help", Toast.LENGTH_SHORT).show();
	        return true;

	    case R.id.aboutus:
	        Toast.makeText(Editor.this, "AboutUs", Toast.LENGTH_SHORT).show();
	        return true;

	    case R.id.exit:
	        Toast.makeText(Editor.this, "Exit", Toast.LENGTH_SHORT).show();
	        finish();
	        return true;

	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}  
}
