package com.example.smart_coordinator;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

public class FragmentCloset_Data extends Fragment {
    private Activity mContext;
    private Context con;

    private String imageFilePath;
    private Uri photoUri;

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;

    private Button cameraBtn;
    private Button albumBtn;
    private ImageView dbImageView;
    private ImageView dbImageView2;
    private ImageView dbImageView3;
    private ImageView dbImageView4;
    private ImageView dbImageView5;
    private ImageView dbImageView6;

    private customDialog customdialog;
    private customDialog2 customdialog2;
    //private customDialogListener customdialogListener;
    DBHelper databaseHelper;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = (Activity) context;
        con = (Context) mContext;

        /*try {
            customdialogListener = (customDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BuyFoodBottomSheetListener");
        }*/
    }

    /*public interface customDialogListener {
        void onOKButtonClicked();
    }*/

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_closet_data, container, false);

        cameraBtn = (Button)view.findViewById(R.id.cameraButton);
        albumBtn = (Button)view.findViewById(R.id.albumButton);

        dbImageView = (ImageView)view.findViewById(R.id.dbRead);
        dbImageView2 = (ImageView)view.findViewById(R.id.dbRead2);
        dbImageView3 = (ImageView)view.findViewById(R.id.dbRead3);
        dbImageView4 = (ImageView)view.findViewById(R.id.dbRead4);
        dbImageView5 = (ImageView)view.findViewById(R.id.dbRead5);
        dbImageView6 = (ImageView)view.findViewById(R.id.dbRead6);

        databaseHelper = new DBHelper(getActivity());

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                if(cameraIntent.resolveActivity(mContext.getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        //Error handling
                    }
                    if (photoFile != null) {
                        photoUri = FileProvider.getUriForFile(con, mContext.getPackageName(), photoFile);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                        startActivityForResult(cameraIntent, PICK_FROM_CAMERA);
                    }

                    //connectServer();
                }
            }
        });

        albumBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, PICK_FROM_ALBUM);
            }
        });

        //이미지 누르면 다이얼로그 띄우기
        dbImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customdialog = new customDialog(mContext, OkListener, CancelListener);
                customdialog.show();
            }
        });

        return view;
    }

    private View.OnClickListener OkListener = new View.OnClickListener() {
        public void onClick(View v) {
            //customdialogListener.onOKButtonClicked();
            customdialog.dismiss();

            //Intent intent = new Intent(FragmentCloset_Data.this, ProgressDialogActivity.class);
            //startActivity(intent);

            ProgressDialog progressDialog = new ProgressDialog(con);
            progressDialog.setMessage("ProgressDialog running...");
            progressDialog.setCancelable(true);
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);

            progressDialog.show();
            try {
                Thread.sleep(2000); // 2초 지속

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            progressDialog.dismiss();

            customdialog2 = new customDialog2(mContext, OkListener2, CancelListener2);
            customdialog2.show();
        }
    };

    private View.OnClickListener CancelListener = new View.OnClickListener() {
        public void onClick(View v) {
            customdialog.dismiss();
        }
    };

    //두번째 dialog
    private View.OnClickListener OkListener2 = new View.OnClickListener() {
        public void onClick(View v) {
            //customdialogListener.onOKButtonClicked();
            customdialog2.dismiss();
        }
    };

    private View.OnClickListener CancelListener2 = new View.OnClickListener() {
        public void onClick(View v) {
            customdialog2.dismiss();
        }
    };

    void connectServer(){
        String ipv4Address = "192.168.219.106";
        String portNumber = "7001";

        String postUrl= "http://"+ipv4Address+":"+portNumber+"/";
        String htmlUrl = "http://"+ipv4Address+":"+portNumber+"/imgToHtml";

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        //Read Bitmap
        Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath, options);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        RequestBody postBodyImage = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "androidFlask.jpg", RequestBody.create(MediaType.parse("image/*jpg"), byteArray))
                .build();

        //TextView responseText = mContext.findViewById(R.id.responseText);
        //responseText.setText("Please wait ...");
        Toast.makeText(mContext, "Please wait ...", Toast.LENGTH_SHORT).show();

        postRequest(postUrl, postBodyImage);
        postRequestHtml(htmlUrl);
    }

    void postRequestHtml(String htmlUrl) {
        Request request = new Request.Builder()
                .url(htmlUrl)
                .build();
    }

    void postRequest(String postUrl, RequestBody postBody) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(postUrl)
                .post(postBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Cancel the post on failure.
                call.cancel();

                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //TextView responseText = mContext.findViewById(R.id.responseText);
                        //responseText.setText("Failed to Connect to Server");
                        Toast.makeText(mContext, "Failed to connect to server", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Toast.makeText(mContext, response.body().string(), Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_FROM_ALBUM && resultCode==RESULT_OK&&data!=null&&data.getData()!=null) {
            Uri selectedImageUri = data.getData();

            //input data to DB
            try {
                databaseHelper.open();
                InputStream iStream = mContext.getContentResolver().openInputStream(selectedImageUri);
                byte[] inputData = imageUtils.getBytes(iStream);
                databaseHelper.onCreateItem(inputData);
                databaseHelper.close();
            }catch (IOException ioe) {
                databaseHelper.close();
            }

            //read data from DB
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        databaseHelper.openRead();
                        final byte[] bytes = databaseHelper.onReadItem();
                        databaseHelper.close();
                        dbImageView.post(new Runnable() {
                            @Override
                            public void run() {
                                dbImageView.setImageBitmap(imageUtils.getImage(bytes));
                            }
                        });
                    } catch (Exception e) {
                        databaseHelper.close();
                    }
                }
            }).start();

            dbImageView.setImageURI(selectedImageUri);
            //imageView.setImageURI(data.getData());
        } else if(requestCode == PICK_FROM_CAMERA && resultCode==RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);


            ExifInterface exif = null;
            Log.d("~~~", imageFilePath);

            try {
                exif = new ExifInterface(imageFilePath);
            } catch(IOException e) {
                e.printStackTrace();
            }

            int exifOrientation;
            int exifDegree;

            if(exif != null) {
                exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                exifDegree = exifOrientationToDegrees(exifOrientation);
            } else {
                exifDegree = 0;
            }

            if(bitmap != null) {
                dbImageView.setImageBitmap(rotate(bitmap, exifDegree));

            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TEST_" + timeStamp + "_";
        File storageDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        imageFilePath = image.getAbsolutePath();
        return image;
    }

    private int exifOrientationToDegrees(int exifOrientation) {
        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    //Rotate photo
    private Bitmap rotate(Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}