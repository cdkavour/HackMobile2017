package com.example.hackmobile.imageclassifier;

import android.content.Intent;
import android.content.Context;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.graphics.Bitmap;
import android.net.Uri;
//import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
//import android.widget.TextView;
import android.database.Cursor;

public class MainActivity extends AppCompatActivity {

    // Global Variables
    private static final int CAMERA_REQUEST = 100;
    private static final int PICK_IMAGE = 1;
    private ImageView imageView;

    // Main
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.imageView = (ImageView)this.findViewById(R.id.my_image_view);

        // Event Listener for Image Select Button
        Button photo_button = (Button)findViewById(R.id.select_image_button_id);
        photo_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Select an image from Camera Roll
                Intent photo_intent = new Intent();
                        //Intent.ACTION_PICK,
                        //MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                photo_intent.setType("image/*");
                photo_intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser( photo_intent, "Select Picture"), PICK_IMAGE);
            }

        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == AppCompatActivity.RESULT_OK) {
            Uri mySelectedImage = data.getData();
            if(mySelectedImage != null) {
                String image_path = getPathFromURI(mySelectedImage);
            }
            //Bitmap photo = (Bitmap) data.getExtras().get("data");
            //imageView.setImageBitmap(photo);
        }
    }

    public String getPathFromURI(Uri contentUri) {
        String result = null;
        String[] int_data = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, int_data, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            result = cursor.getString(column_index);
        }
        cursor.close();
        return result;
    }
}
