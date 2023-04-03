package com.csci366au.jrs251.color3display;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    ImageView imageLeft, imageMiddle, imageRight;
    RadioButton leftRGBRadio, leftYCBRadio, leftrightRadio, anaglyphRadio;
    RadioGroup radioGroup;
    private Object BitmapDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        leftRGBRadio = findViewById(R.id.leftRGBRadioButton);
        leftYCBRadio = findViewById(R.id.leftYCbCrRadioButton);
        leftrightRadio = findViewById(R.id.leftrightRadioButton);
        anaglyphRadio = findViewById(R.id.anaglyphRadioButton);


        imageLeft = findViewById(R.id.imageViewLeft);
        imageMiddle = findViewById(R.id.imageViewMiddle);
        imageRight = findViewById(R.id.imageViewRight);

        radioGroup = findViewById(R.id.imageRadioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.leftRGBRadioButton:
                        Toast.makeText(getApplicationContext(), "leftRGBRadio", Toast.LENGTH_LONG).show();
                        setRGBLeft();
                        return;
                    case R.id.leftYCbCrRadioButton:
                        Toast.makeText(getApplicationContext(), "leftYCbRadio", Toast.LENGTH_LONG).show();
                        return;
                    case R.id.leftrightRadioButton:
                        Toast.makeText(getApplicationContext(), "leftrightRadio", Toast.LENGTH_LONG).show();
                        return;
                    case R.id.anaglyphRadioButton:
                        Toast.makeText(getApplicationContext(), "anaglyphRadio", Toast.LENGTH_LONG).show();
                        return;
                }
            }
        });
    }

    private void setRGBLeft(){

        /*

        Bitmap plantBmp = BitmapFactory.decodeResource(getResources(), R.drawable.left);
        int width = plantBmp.getWidth();
        int height = plantBmp.getHeight();
        int maxWidth = width/2;
        int maxHeight = height/2;
        float ratio = Math.min(
                (float) maxWidth / plantBmp.getWidth(),
                (float) maxHeight / plantBmp.getHeight()
        );
        width = Math.round((float) ratio * plantBmp.getWidth());
        height = Math.round((float) ratio * plantBmp.getHeight());
        Bitmap scaledBmp = Bitmap.createScaledBitmap(plantBmp, width, height, true);
        imageLeft.setImageBitmap(scaledBmp);
        imageMiddle.setImageBitmap(scaledBmp);
        imageRight.setImageBitmap(scaledBmp);*/


        //imageLeft.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
        //imageLeft.setImageResource(R.drawable.left);

        //imageMiddle.setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
        //imageMiddle.setImageResource(R.drawable.left);

        //imageRight.setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);
        //imageRight.setImageResource(R.drawable.left);

        setLeftRGB();
        setMiddleRGB();
        setRightRGB();
    }



    private void setLeftRGB(){
        // decodes image to bitmap
        Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.left);
        int width = bMap.getWidth();
        int height = bMap.getHeight();
        int[] pixels = new int[width*height];
        bMap.getPixels(pixels, 0, width, 0, 0, width, height);
        Bitmap rbitmap = bMap.copy(Bitmap.Config.ARGB_8888, true);

        //int r,g,b;
        for(int x = 0; x < rbitmap.getWidth(); x++){

            /*
            r = (pixels[x]>>16) & 0xFF;
            g = (pixels[x]>>8) &0xFF;
            b = pixels[x] & 0xFF;

            r &= 0xFFFF0000;
            pixels[x] = Color.rgb(r,g,b);*/


            for(int y = 0; y < rbitmap.getHeight(); y++){

                //rbitmap.setPixel(x,y, rbitmap.getPixel(x,y) & 0xFFFF0000);

                int pixel = rbitmap.getPixel(x, y);

                int a = Color.alpha(pixel);
                int r = 255;
                int g = Color.green(pixel);
                int b = Color.blue(pixel);

                g *= 0.5;
                b *= 0.5;
                g = Math.min(g, 255);
                b = Math.min(b, 255);
                int newColour = Color.argb(a,r,g,b);
                rbitmap.setPixel(x,y,newColour);

            }
        }
        //rbitmap.setPixels(pixels, 0, width, 0, 0, width, height);



        //Bitmap scaledBitmap = Bitmap.createScaledBitmap(rbitmap, width, height, false);

        imageLeft.setImageBitmap(rbitmap);
        //imageMiddle.setImageBitmap(scaledBitmap);
        //imageRight.setImageBitmap(scaledBitmap);

    }

    private void setMiddleRGB(){
        // decodes image to bitmap
        Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.left);
        int width = bMap.getWidth();
        int height = bMap.getHeight();
        int[] pixels = new int[width*height];
        bMap.getPixels(pixels, 0, width, 0, 0, width, height);
        Bitmap rbitmap = bMap.copy(Bitmap.Config.ARGB_8888, true);

        //int r,g,b;
        for(int x = 0; x < rbitmap.getWidth(); x++){

            /*
            r = (pixels[x]>>16) & 0xFF;
            g = (pixels[x]>>8) &0xFF;
            b = pixels[x] & 0xFF;

            r &= 0xFFFF0000;
            pixels[x] = Color.rgb(r,g,b);*/


            for(int y = 0; y < rbitmap.getHeight(); y++){

                //rbitmap.setPixel(x,y, rbitmap.getPixel(x,y) & 0xFFFF0000);

                int pixel = rbitmap.getPixel(x, y);

                int a = Color.alpha(pixel);
                int r = Color.red(pixel);
                int g = 255;
                int b = Color.blue(pixel);

                int newColour = Color.argb(a,r,g,b);
                rbitmap.setPixel(x,y,newColour);

            }
        }
        //rbitmap.setPixels(pixels, 0, width, 0, 0, width, height);



        //Bitmap scaledBitmap = Bitmap.createScaledBitmap(rbitmap, width, height, false);

        imageMiddle.setImageBitmap(rbitmap);

    }

    private void setRightRGB() {
        // decodes image to bitmap
        Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.left);
        int width = bMap.getWidth();
        int height = bMap.getHeight();
        int[] pixels = new int[width*height];
        bMap.getPixels(pixels, 0, width, 0, 0, width, height);
        Bitmap rbitmap = bMap.copy(Bitmap.Config.ARGB_8888, true);

        //int r,g,b;
        for(int x = 0; x < rbitmap.getWidth(); x++){

            /*
            r = (pixels[x]>>16) & 0xFF;
            g = (pixels[x]>>8) &0xFF;
            b = pixels[x] & 0xFF;

            r &= 0xFFFF0000;
            pixels[x] = Color.rgb(r,g,b);*/


            for(int y = 0; y < rbitmap.getHeight(); y++){

                //rbitmap.setPixel(x,y, rbitmap.getPixel(x,y) & 0xFFFF0000);

                int pixel = rbitmap.getPixel(x, y);

                int a = Color.alpha(pixel);
                int r = Color.red(pixel);
                int g = Color.green(pixel);
                int b = 255;

                int newColour = Color.argb(a,r,g,b);
                rbitmap.setPixel(x,y,newColour);

            }
        }
        //rbitmap.setPixels(pixels, 0, width, 0, 0, width, height);



        //Bitmap scaledBitmap = Bitmap.createScaledBitmap(rbitmap, width, height, false);

        imageRight.setImageBitmap(rbitmap);

    }
}