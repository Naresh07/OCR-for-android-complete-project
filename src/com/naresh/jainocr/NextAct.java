package com.naresh.jainocr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;


public class NextAct extends Activity {
	public EditText field;
	public Button butt;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nextact);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        butt =(Button) findViewById(R.id.butto);
        field = (EditText)findViewById(R.id.edit1);
        field.setText(message);
    	
     

        butt.setOnClickListener(new View.OnClickListener() {
			
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
		            myOutWriter.append(field.getText());
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
            Toast.makeText(NextAct.this, "Showing files", Toast.LENGTH_SHORT).show();
        	Intent ishow = new Intent("com.naresh.jainocr.LISTME");
        	startActivity(ishow);
            return true;
 
        case R.id.main:
            Toast.makeText(NextAct.this, "Loading Main page", Toast.LENGTH_SHORT).show();
            Intent ishowm = new Intent("com.naresh.jainocr.MAIN");
        	startActivity(ishowm);
            return true;
 
        case R.id.back:
            Toast.makeText(NextAct.this, "Back", Toast.LENGTH_SHORT).show();
            Intent ishowb = new Intent("com.naresh.jainocr.NEXTACT");
        	startActivity(ishowb);
            return true;
 
        case R.id.help:
            Toast.makeText(NextAct.this, "Help", Toast.LENGTH_SHORT).show();
            return true;
 
        case R.id.aboutus:
            Toast.makeText(NextAct.this, "AboutUs", Toast.LENGTH_SHORT).show();
            return true;
 
        case R.id.exit:
            Toast.makeText(NextAct.this, "Exit", Toast.LENGTH_SHORT).show();
            finish();
            return true;
 
        default:
            return super.onOptionsItemSelected(item);
        }
    }    
}