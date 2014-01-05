package com.android.camera_project1;

import java.io.File;
import java.io.IOException;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.*;

public class MainActivity extends Activity implements View.OnClickListener {

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0;
	Button btn;
	ImageView mImgView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		btn = (Button)findViewById(R.id.take_picture_button);
		mImgView = (ImageView)findViewById(R.id.imageView1);
		btn.setOnClickListener(this);
		
	}

	public void onClick(View view){
		
		takePicture();
	}
	public void takePicture(){

		// create Intent to take a picture and return control to the calling application
	    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    deleteTempFile();
	    intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoUri());
	    // start the image capture Intent
	    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}
	
	public void deleteTempFile(){
		
		File rootFolder = Environment.getExternalStorageDirectory();
		File temFile = new File(rootFolder.getAbsolutePath()+"/tmp.jpg");
		if(temFile.exists()){
			
			temFile.delete();
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(resultCode!= RESULT_OK){
			return;
		}	
		String imgPath = Environment.getExternalStorageDirectory()+ File.separator + "tmp.jpg"; // tra ve duong dan SDcard
		Bitmap bmp = BitmapFactory.decodeFile(imgPath);
		if(bmp != null){
			mImgView.setImageBitmap(bmp);
			
		}
		
	}

	public Uri getPhotoUri(){
	
		File rootFolder = Environment.getExternalStorageDirectory();
		File tempPhoto = new File(rootFolder.getAbsoluteFile() + "/tmp.jpg");
		try{
		if(!tempPhoto.exists())	{
			tempPhoto.createNewFile();
		}
		Uri tempPhotoUri = Uri.fromFile(tempPhoto);
		return tempPhotoUri;
		
		}catch(IOException e){
			e.printStackTrace();
			return Uri.EMPTY;
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}