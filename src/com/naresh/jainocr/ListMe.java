package com.naresh.jainocr;

import java.io.File;
import java.io.FilenameFilter;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListMe extends ListActivity{
 ArrayAdapter<String> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		adapter=new ArrayAdapter<String>(this,R.layout.list,gettexts());
		setListAdapter(adapter);
		
		ListView listView=getListView();
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1, int position,
					long arg3) {
				Intent newvala=new Intent("com.naresh.jainocr.EDITOR");
				startActivity(newvala);
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
			}});
	
	}
static String[] mFiles=null;
public static String[] gettexts(){
	File texts=Environment.getExternalStorageDirectory();
	File[] textlist=texts.listFiles(new FilenameFilter(){

		@Override
		public boolean accept(File dir , String name) {
			// TODO Auto-generated method stub
			return ((name.startsWith("ocr"))||(name.endsWith(".txt")));
		}
		
	});
	mFiles=new String[textlist.length];
	for(int i=0;i<textlist.length;i++){
		mFiles[i]=textlist[i].getAbsolutePath();
	}
	return mFiles;
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
        Toast.makeText(ListMe.this, "Showing files", Toast.LENGTH_SHORT).show();
    	Intent ishow = new Intent("com.naresh.jainocr.LISTVIEW");
    	startActivity(ishow);
        return true;

    case R.id.main:
        Toast.makeText(ListMe.this, "Loading Main page", Toast.LENGTH_SHORT).show();
        Intent ishowm = new Intent("com.naresh.jainocr.MAIN");
    	startActivity(ishowm);
        return true;

    case R.id.back:
        Toast.makeText(ListMe.this, "Back", Toast.LENGTH_SHORT).show();
        Intent ishowb = new Intent("com.naresh.jainocr.ListView");
    	startActivity(ishowb);
        return true;

    case R.id.help:
        Toast.makeText(ListMe.this, "Help", Toast.LENGTH_SHORT).show();
        return true;

    case R.id.aboutus:
        Toast.makeText(ListMe.this, "AboutUs", Toast.LENGTH_SHORT).show();
        return true;

    case R.id.exit:
        Toast.makeText(ListMe.this, "Exit", Toast.LENGTH_SHORT).show();
        finish();
        return true;

    default:
        return super.onOptionsItemSelected(item);
    }
}  
}
