package com.example.health_connect;
 
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import com.amazonaws.auth.BasicAWSCredentials;





import com.example.health_connect.Constants;
import com.parse.Parse;
import com.parse.ParseUser;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
 
public class Fourth extends Activity {
	
	private AmazonS3Client s3Client = new AmazonS3Client(
			new BasicAWSCredentials(Constants.ACCESS_KEY_ID,
					Constants.SECRET_KEY));
 
    ImageView viewImage;
    Button b;
    File f;
    Button ski;
    ParseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourth);
        Parse.initialize(this, "e0FVFRBMAWJi5shg4XF8zL3SIuRwDIufww3338so", "toTJmlHTEF43u7PoAFT4fedwqfhoWiSajj1Se7FT");
        currentUser = ParseUser.getCurrentUser();
        b=(Button)findViewById(R.id.btnSelectPhoto);
        viewImage=(ImageView)findViewById(R.id.ppic);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        ski = (Button) findViewById(R.id.skip);
        ski.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 currentUser.put("image_flag", "0");
				 currentUser.saveInBackground(new SaveCallback() {
					
					@Override
					public void done(ParseException e) {
						// TODO Auto-generated method stub
					
						if(e!=null)
						{
							Log.d("Saving flag0", "done");
						}
						else
						{
							Log.d("Saving flag0", "notdone");
						}
					}
				});
				Intent skip_int = new Intent(Fourth.this, SwipeHome.class);
				startActivity(skip_int);
			}
		});
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds options to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
 
      private void selectImage() {
 
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
 
        AlertDialog.Builder builder = new AlertDialog.Builder(Fourth.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
 
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
 
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
 
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions); 
                 //   new S3PutObjectTask().execute(Uri.fromFile(f));
                    viewImage.setImageBitmap(bitmap);
 
                    Button don = (Button) findViewById(R.id.done);
                    don.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
						
							
							new S3PutObjectTask().execute(Uri.fromFile(f));
							 currentUser.put("ïmage_flag", "1");
							 currentUser.saveInBackground(new SaveCallback() {
								
								@Override
								public void done(ParseException e) {
									// TODO Auto-generated method stub
								
									if(e!=null)
									{
										Log.d("Saving flag", "done");
									}
									else
									{
										Log.d("Saving flag", "notdone");
									}
								}
							});
							Intent intent = new Intent(Fourth.this,SwipeHome.class);
							startActivity(intent);
						}
					});
                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
 
                final Uri selectedImage = data.getData();
           //     new S3PutObjectTask().execute(selectedImage);
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                
                Log.w("path of image from gallery......******************.........", picturePath+"");
                viewImage.setImageBitmap(thumbnail);
                Button don = (Button) findViewById(R.id.done);
                don.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
					
						 new S3PutObjectTask().execute(selectedImage);
						 currentUser.put("ïmage_flag", "1");
						 currentUser.saveInBackground(new SaveCallback() {
							
							@Override
							public void done(ParseException e) {
								// TODO Auto-generated method stub
							
								if(e!=null)
								{
									Log.d("Saving flag", "done");
								}
								else
								{
									Log.d("Saving flag", "notdone");
								}
							}
						});
						Intent intent = new Intent(Fourth.this,SwipeHome.class);
						startActivity(intent);
					}
				});
            }
        }
    }   
    
    private class S3PutObjectTask extends AsyncTask<Uri, Void, S3TaskResult> {

		ProgressDialog dialog;

		protected void onPreExecute() {
			dialog = new ProgressDialog(Fourth.this);
			dialog.setMessage(Fourth.this
					.getString(R.string.uploading));
		  //  dialog.setMessage("Uploading..");
			dialog.setCancelable(false);
			dialog.show();
		}

		protected S3TaskResult doInBackground(Uri... uris) {

			if (uris == null || uris.length != 1) {
				return null;
			}

			// The file location of the image selected.
			Uri selectedImage = uris[0];

			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String filePath = cursor.getString(columnIndex);
			cursor.close();

			S3TaskResult result = new S3TaskResult();

			// Put the image data into S3.
			try {
				
				if (s3Client.doesBucketExist(Constants.getPictureBucket()))
				{
					s3Client.getBucketAcl(Constants.getPictureBucket());
				}
				else
				{
					s3Client.createBucket(Constants.getPictureBucket());
				}
				// Content type is determined by file extension.
				PutObjectRequest por = new PutObjectRequest(
						Constants.getPictureBucket(),currentUser.getObjectId(),
						new java.io.File(filePath));
				s3Client.putObject(por);
			} catch (Exception exception) {

				result.setErrorMessage(exception.getMessage());
			}

			return result;
		}

		protected void onPostExecute(S3TaskResult result) {

			dialog.dismiss();

			if (result.getErrorMessage() != null) {

				displayErrorAlert(
						Fourth.this
								.getString(R.string.upload_failure_title),
						result.getErrorMessage());
			}
		}
	}

	private class S3TaskResult {
		String errorMessage = null;
		Uri uri = null;

		public String getErrorMessage() {
			return errorMessage;
		}

		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}

		public Uri getUri() {
			return uri;
		}

		public void setUri(Uri uri) {
			this.uri = uri;
		}
	}
	// Display an Alert message for an error or failure.
		protected void displayAlert(String title, String message) {

			AlertDialog.Builder confirm = new AlertDialog.Builder(this);
			confirm.setTitle(title);
			confirm.setMessage(message);

			confirm.setNegativeButton(
					Fourth.this.getString(R.string.ok),
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {

							dialog.dismiss();
						}
					});

			confirm.show().show();
		}

		protected void displayErrorAlert(String title, String message) {

			AlertDialog.Builder confirm = new AlertDialog.Builder(this);
			confirm.setTitle(title);
			confirm.setMessage(message);

			confirm.setNegativeButton(
					Fourth.this.getString(R.string.ok),
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {

							Fourth.this.finish();
						}
					});

			confirm.show().show();
		}
}
