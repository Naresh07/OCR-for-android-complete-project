package com.naresh.jainocr;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;

	public class MainActivity extends ActionBarActivity {
		public static final String PACKAGE_NAME = "com.naresh.jainocr";
		public static final String DATA_PATH = Environment
				.getExternalStorageDirectory().toString() + "/NareshOCR/";
		
		public static final String lang = "eng";
		 public final static String EXTRA_MESSAGE = "com.naresh.jainocr.MESSAGE";
		private static final String TAG = "NareshOCR.java";

		public Button _button;
		public Button bb1;
		public ImageView _image;
		public EditText _field;
		public String _path;
		public boolean _taken;
		ProgressBar pg;
		int progress=0;
		Handler hnd= new Handler();
		

		public Bitmap bitmap;
		

		public static final String PHOTO_TAKEN = "photo_taken";

		@Override
		public void onCreate(Bundle savedInstanceState) {

			String[] paths = new String[] { DATA_PATH, DATA_PATH + "tessdata/" };

			for (String path : paths) {
				File dir = new File(path);
				if (!dir.exists()) {
					if (!dir.mkdirs()) {
						Log.v(TAG, "ERROR: Creation of directory " + path + " on sdcard failed");
						return;
					} else {
						Log.v(TAG, "Created directory " + path + " on sdcard");
					}
				}

			}
			
			if (!(new File(DATA_PATH + "tessdata/" + lang + ".traineddata")).exists()) {
				try {

					AssetManager assetManager = getAssets();
					InputStream in = assetManager.open("tessdata/" + lang + ".traineddata");
					
					OutputStream out = new FileOutputStream(DATA_PATH
							+ "tessdata/" + lang + ".traineddata");

					
					byte[] buf = new byte[1024];
					int len;
					
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					in.close();
					
					out.close();
					
					Log.v(TAG, "Copied " + lang + " traineddata");
				} catch (IOException e) {
					Log.e(TAG, "Was unable to copy " + lang + " traineddata " + e.toString());
				}
			}

			super.onCreate(savedInstanceState);

			setContentView(R.layout.activity_main);
			bb1 =(Button) findViewById(R.id.butt);
			_image = (ImageView) findViewById(R.id.image);
			_button = (Button) findViewById(R.id.button);
		    _button.setOnClickListener(new ButtonClickHandler());
			_path = DATA_PATH + "/njocr.jpg";
			_field = (EditText)findViewById(R.id.editText1);
		}

		public class ButtonClickHandler implements View.OnClickListener {
			public void onClick(View view) {
				Log.v(TAG, "Starting Camera app");
				startCameraActivity();
				
			}
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
	        	 Toast.makeText(MainActivity.this, "Showing files", Toast.LENGTH_SHORT).show();
	        	Intent ishow = new Intent("com.naresh.jainocr.LISTME");
	        	startActivity(ishow);
	           
	            return true;
	 
	        case R.id.main:
	        	
	            Toast.makeText(MainActivity.this, "It's Main Menu", Toast.LENGTH_SHORT).show();
	            return true;
	 
	        case R.id.back:
	        	
	            Toast.makeText(MainActivity.this, "Back", Toast.LENGTH_SHORT).show();
	            return true;
	 
	        case R.id.help:
	            Toast.makeText(MainActivity.this, "Help", Toast.LENGTH_SHORT).show();
	            return true;
	 
	        case R.id.aboutus:
	            Toast.makeText(MainActivity.this, "AboutUs", Toast.LENGTH_SHORT).show();
	            return true;
	            
	        case R.id.exit:
	            Toast.makeText(MainActivity.this, "Exit", Toast.LENGTH_SHORT).show();
	            finish();
	            return true;
	 
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	    }    
	     
	  
	 

		public void startCameraActivity() {
			File file = new File(_path);
			Uri outputFileUri = Uri.fromFile(file);

			final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

			startActivityForResult(intent, 0);
		}
		public  static final int CAMERA_REQUEST = 1888; 


		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {

			Log.i(TAG, "resultCode: " + resultCode);
			
		
			
			if (resultCode == -1) {
				onPhotoTaken();
			} else {
				Log.v(TAG, "User cancelled");
			}
			
		}

		@Override
		public void onSaveInstanceState(Bundle outState) {
			outState.putBoolean(MainActivity.PHOTO_TAKEN, _taken);
		}

		@Override
		public void onRestoreInstanceState(Bundle savedInstanceState) {
			Log.i(TAG, "onRestoreInstanceState()");
			if (savedInstanceState.getBoolean(MainActivity.PHOTO_TAKEN)) {
				onPhotoTaken();
			}
		}

		public void onPhotoTaken() {
			_taken = true;
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 4;
			bitmap = BitmapFactory.decodeFile(_path, options);
		
			try {
				ExifInterface exif = new ExifInterface(_path);
				int exifOrientation = exif.getAttributeInt(
						ExifInterface.TAG_ORIENTATION,
						ExifInterface.ORIENTATION_NORMAL);

				Log.v(TAG, "Orient: " + exifOrientation);

				int rotate = 0;

				switch (exifOrientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					rotate = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					rotate = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					rotate = 270;
					break;
				}

				Log.v(TAG, "Rotation: " + rotate);

				if (rotate != 0) {

					
					int w = bitmap.getWidth();
					int h = bitmap.getHeight();

				
					Matrix mtx = new Matrix();
					mtx.preRotate(rotate);

					// Rotating Bitmap
					bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, false);
				}

				// Convert to ARGB_8888, required by tess
				bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
				
			} catch (IOException e) {
				Log.e(TAG, "Couldn't correct orientation: " + e.toString());
			}
			
		
			  Log.v(TAG, "Before baseApi");

				TessBaseAPI baseApi = new TessBaseAPI();
				baseApi.setDebug(true);
				baseApi.init(DATA_PATH, lang);
				baseApi.setImage(bitmap);
				
				String recognizedText = baseApi.getUTF8Text();
				
		
		

				Log.v(TAG, "OCRED TEXT: " + recognizedText);

				if ( lang.equalsIgnoreCase("eng") ) {
					recognizedText = recognizedText.replaceAll("[^a-zA-Z0-9]+", " ");
				}
				
				recognizedText = recognizedText.trim();

				if ( recognizedText.length() != 0 ) {
					_field.setText(_field.getText().toString().length() == 0 ? recognizedText : _field.getText() + " " + recognizedText);
					_field.setSelection(_field.getText().toString().length());
					_field.setVisibility(View.GONE);
			}
			 
				_image.setImageBitmap(bitmap);
	
		bb1.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 // Perform action on click   
     			Intent p=new Intent("com.naresh.jainocr.NEXTACT");
     			 EditText editText = (EditText) findViewById(R.id.editText1);
     		    String message = editText.getText().toString();
     		    p.putExtra(EXTRA_MESSAGE, message);
    			startActivity(p);
                 
             }
		});
	}
	}
	

