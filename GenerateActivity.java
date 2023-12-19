package com.example.cs4433qr;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGSaver;

public class GenerateActivity extends AppCompatActivity {

    //variables
    TextView resultTextView;
    ImageView ViewQR;
    TextInputEditText StudentID;
    Button GenButton, Save;
    Bitmap bitmap;
    String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);
        resultTextView = findViewById(R.id.TEXT1);
        ViewQR = findViewById(R.id.ViewQR);
        StudentID = findViewById(R.id.StudentID);
        GenButton = findViewById(R.id.GenButton);
        FrameLayout fl = (FrameLayout) findViewById(R.id.idFLQrCode);
        Save = findViewById(R.id.Save);

        GenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = StudentID.getText().toString();

                if (data.length()!=9){
                    Toast.makeText(GenerateActivity.this, "Enter a Valid Student Number", Toast.LENGTH_SHORT).show();
                }else{

                    try {
                        //REMOVE TEXT "Your QR Code Will appear here"
                        fl.removeView(resultTextView);
                        // Encode the text as a QR code
                        BitMatrix bitMatrix = new QRCodeWriter().encode(data, BarcodeFormat.QR_CODE,200,200);
                        //convert the BitMatrix toa Bitmap
                        int width = bitMatrix.getWidth();
                        int height = bitMatrix.getHeight();
                        int[] pixels = new int[width*height];
                        for (int y=0; y<height;y++){
                            int offset = y*width;
                            for (int x=0;x<width;x++){
                                pixels[offset + x]= bitMatrix.get(x,y)? Color.BLACK : Color.WHITE;
                            }
                        }
                        Bitmap bitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
                        bitmap.setPixels(pixels,0,width,0,0,width,height);

                        ViewQR.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        //handler
                    }
                }
            }
        });
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean save;
                String result;
                try{
                    QRGSaver qrgSaver = new QRGSaver();
                    String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
                     qrgSaver.save(savePath,StudentID.getText().toString().trim(),
                            bitmap,QRGContents.ImageType.IMAGE_JPEG);
                    Toast.makeText(GenerateActivity.this, StudentID.getText().toString(), Toast.LENGTH_LONG).show();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}