package com.adapp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class AddPhotoActivity extends Activity {

	Button finishAd;
	Button addPicture;
	public static final int MEDIA_TYPE_IMAGE = 1;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private Uri fileUri;
	private Bitmap bitmap;
	
	private static Uri getUri(int type){
		
		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "CameraOutput");
		
		if (! mediaStorageDir.exists()) {
			if (! mediaStorageDir.mkdirs()) {
				Log.d("CameraOutput", "Nao foi possivel criar o diretorio");
				return null;
			}
		}
		
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File mediaFile;
		
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
		} else {
			return null;
		}
		return Uri.fromFile(mediaFile);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ad_photo);
		onClickAddPicture();
		onClickFinishAd();
	}

	private void onClickAddPicture(){
		
		addPicture = (Button)findViewById(R.id.add_photo_add_picture);
		addPicture.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				fileUri = getUri(MEDIA_TYPE_IMAGE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
				startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			}
		});

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				Toast.makeText(this, "imagem salva", Toast.LENGTH_SHORT).show();
				ImageView imageView = (ImageView)findViewById(R.id.add_photo_caught_picture);
				imageView.setImageURI(fileUri);
				
				BitmapFactory.Options o = new BitmapFactory.Options();
				o.inJustDecodeBounds = true;
				bitmap = BitmapFactory.decodeFile(fileUri.toString(), o);
				
			} else if(resultCode == RESULT_CANCELED) {
				
			} else {
				
			}
		}
	}
	
	private void onClickFinishAd() {
		
		final Context context = this;
		finishAd = (Button) findViewById(R.id.ad_photo_finish_button);
		finishAd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Thread t = new Thread() {

					public void run() {
			            	
						Intent i = getIntent();
						String adCategory = i.getStringExtra("categoryId");
						String adTitle = i.getStringExtra("title");
						String adPrice = i.getStringExtra("price");
						String adDescription = i.getStringExtra("description");
			            
						int catId = Integer.parseInt(adCategory);
						
			            Looper.prepare();
			            HttpClient client = new DefaultHttpClient();
			            HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
			            HttpContext context = new BasicHttpContext();
			            HttpResponse response;
			            JSONObject json = new JSONObject();

			            try {
			            	HttpPost post = new HttpPost("http://192.168.0.16:3000/ads");
			            	
			                json.put("category_id", catId);
			                json.put("title", adTitle);
			                json.put("price", adPrice);
			                json.put("description", adDescription);
			                
			                StringEntity se = new StringEntity(json.toString());  
			                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			                //post.setEntity(se);
			                //response = client.execute(post);
			                
			                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			                builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			                ByteArrayOutputStream bos = new ByteArrayOutputStream();
			                bitmap.compress(CompressFormat.JPEG, 100, bos);
			                byte[] data = bos.toByteArray();
			                String file = Base64.encodeBytes(data);
							builder.addPart("picture", new StringBody(file));
			                builder.addPart("json", new StringBody(json.toString()));
			                HttpEntity entity = builder.build();
			                
			                post.setEntity(entity);
			                response = client.execute(post);

			                if(response!=null){
			                	InputStream in = response.getEntity().getContent(); //Get the data in the entity
			                }

			            } catch(Exception e) {
			            	e.printStackTrace();
			            }

			            	Looper.loop();
			            }
			        };

			        t.start();      

				Intent intent = new Intent(context, NewAdActivity.class);
				startActivity(intent);
			}
		});
	}
	
}