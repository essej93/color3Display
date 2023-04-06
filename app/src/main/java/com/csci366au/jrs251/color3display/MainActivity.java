package com.csci366au.jrs251.color3display;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    // variables to hold views/bitmaps
    ImageView imageLeft, imageMiddle, imageRight;
    RadioGroup radioGroup;
    LinearLayout linearImageView;
    Bitmap bitmapLeftImg, bitmapRightImg, scaledImgLeft, scaledImgRight;
    Bitmap bitmapR, bitmapG, bitmapB;
    Bitmap bitmapY, bitmapCb, bitmapCr;
    Bitmap anaglyphBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // assigns views to variables using id's
        linearImageView = findViewById(R.id.imageLinearLayout);
        imageLeft = findViewById(R.id.imageViewLeft);
        imageMiddle = findViewById(R.id.imageViewMiddle);
        imageRight = findViewById(R.id.imageViewRight);
        radioGroup = findViewById(R.id.imageRadioGroup);

        scaleImage(); // calls scale image to scale down left and right images on create

        // sets radio group listener
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.leftRGBRadioButton:
                        setRGB();
                        break;
                    case R.id.leftYCbCrRadioButton:
                        setYCbCr();
                        break;
                    case R.id.leftrightRadioButton:
                        setLeftRight();
                        break;
                    case R.id.anaglyphRadioButton:
                        setAnaglyph();
                        break;
                }
            }
        });
    }


    // scale image method to scale down both left and right images when the app starts and stores them in
    // bitmaps for later use
    private void scaleImage(){
        // decodes both images
        bitmapLeftImg = BitmapFactory.decodeResource(getResources(), R.drawable.left);
        bitmapRightImg = BitmapFactory.decodeResource(getResources(), R.drawable.right);

        // divides pixel width/height by 2
        int width, height;
        width = bitmapLeftImg.getWidth()/2;
        height = bitmapLeftImg.getHeight()/2;

        // creates new scaled down bitmap and stores it for later use
        scaledImgLeft = Bitmap.createScaledBitmap(bitmapLeftImg, width, height, true);

        width = bitmapRightImg.getWidth()/2;
        height = bitmapRightImg.getHeight()/2;

        scaledImgRight = Bitmap.createScaledBitmap(bitmapRightImg, width, height, true);

    }

    // method to set left,middle and right image views to respective RGB values
    private void setRGB(){

        // makes image views visible and changes scale type to fit correctly
        imageMiddle.setVisibility(View.VISIBLE);
        imageRight.setVisibility(View.VISIBLE);
        imageLeft.setVisibility(View.VISIBLE);
        imageLeft.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageMiddle.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageRight.setScaleType(ImageView.ScaleType.CENTER_CROP);

        setLeftRGB();
        setMiddleRGB();
        setRightRGB();

    }

    // method used to set left/middle/right images to YCbCr
    private void setYCbCr(){

        // makes image views visible and changes scale type to fit correctly
        imageMiddle.setVisibility(View.VISIBLE);
        imageRight.setVisibility(View.VISIBLE);
        imageLeft.setVisibility(View.VISIBLE);
        imageLeft.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageMiddle.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageRight.setScaleType(ImageView.ScaleType.CENTER_CROP);

        setLeftYCbCr();
        setMiddleYCbCr();
        setRightYCbCr();
    }

    // method used to set left/right images
    private void setLeftRight(){
        // hides middle image view and sets left and right images
        imageMiddle.setVisibility(View.GONE);
        imageRight.setVisibility(View.VISIBLE);
        imageLeft.setVisibility(View.VISIBLE);
        imageLeft.setImageBitmap(scaledImgLeft);
        imageRight.setImageBitmap(scaledImgRight);

    }

    // method used to generate anaglyph image
    private void setAnaglyph(){


        // makes image views visible and changes scale type to fit correctly
        imageMiddle.setVisibility(View.VISIBLE);
        imageRight.setVisibility(View.GONE);
        imageLeft.setVisibility(View.GONE);
        imageMiddle.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        // checks if anaglyphBitmap already exists
        if(anaglyphBitmap != null){
            imageMiddle.setImageBitmap(anaglyphBitmap);
        }


        int leftWidth = scaledImgLeft.getWidth();
        int leftHeight = scaledImgLeft.getHeight();
        int rightWidth = scaledImgLeft.getWidth();
        int rightHeight = scaledImgLeft.getHeight();

        // creates arrays to hold pixel values
        int[] leftPixels = new int[leftWidth*leftHeight];
        int[] rightPixels = new int[rightWidth*rightHeight];
        int[] anaglyphPixels = new int[leftWidth*leftHeight];

        // loads pixel values into arrays from both images
        scaledImgLeft.getPixels(leftPixels, 0, leftWidth, 0, 0, leftWidth, leftHeight);
        scaledImgRight.getPixels(rightPixels, 0, rightWidth, 0, 0, rightWidth, rightHeight);

        // for loop to access both left and right pixel values
        // calculates their luminance value and uses those to then generate a new pixel
        // for the anaglyph image accordingly
        int r, g, b, pixel;
        for(int i = 0; i < leftPixels.length; i++){
            // gets pixel values for left image calculates luminance and holds the new value
            pixel = leftPixels[i];
            r = Color.red(pixel);
            g = Color.green(pixel);
            b = Color.blue(pixel);

            // uses luminance formula to calculate pixel for the left image
            int newLeftPixel = (int) (r * 0.2126 + g * 0.7152 + b * 0.0722); //int newLeftPixel = (int) (r * 0.299 + g * 0.578 + b * 0.114); other formula can be used but not as accurate

            // gets pixel values for right image calculates luminance and holds the new value
            pixel = rightPixels[i];
            r = Color.red(pixel);
            g = Color.green(pixel);
            b = Color.blue(pixel);

            // uses luminance formula to calculate pixel for the right image
            int newRightPixel = (int) (r * 0.2126 + g * 0.7152 + b * 0.0722); // int newRightPixel = (int) (r * 0.299 + g * 0.578 + b * 0.114); other formula can be used but not as accurate

            // sets the new pixel value for the anaglyph using left and right generated pixels. R and B using right image pixel and G using left image pixel
            anaglyphPixels[i] = Color.rgb(newRightPixel, newLeftPixel, newRightPixel);
        }

        anaglyphBitmap = scaledImgLeft.copy(Bitmap.Config.ARGB_8888, true);

        anaglyphBitmap.setPixels(anaglyphPixels, 0, leftWidth, 0, 0, leftWidth, leftHeight);
        imageMiddle.setImageBitmap(anaglyphBitmap);

    }

    // method used to set left image to R value of left image
    private void setLeftRGB(){

        // checks if bitmap has already been generated so it doesnt remake it
        // if it has already been created just sets the image view to that bitmap
        if(bitmapR != null){
            imageLeft.setImageBitmap(bitmapR);
            return;
        }

        // copies scaled img to bitmapR
        bitmapR = scaledImgLeft.copy(Bitmap.Config.ARGB_8888, true);
        int width = scaledImgLeft.getWidth();
        int height = scaledImgLeft.getHeight();

        // creates array to hold pixel values uses getPixels to copy values to the array.
        int[] pixels = new int[width*height];
        scaledImgLeft.getPixels(pixels, 0, width, 0, 0, width, height);

        // for loop to iterate through each pixel and change rgb values accordingly
        int pixel, a,r,g,b;
        for(int i = 0; i < pixels.length; i++){
            pixel = pixels[i];
            a = Color.alpha(pixel);
            r = Color.red(pixel);
            g = 0;
            b = 0;

            // sets the new rgb values for the current pixel
            pixels[i] = Color.argb(a,r,g,b);

        }

        // sets the new pixels to the bitmap
        bitmapR.setPixels(pixels, 0, width, 0, 0, width, height);


        // sets the image view to the bitmap
        imageLeft.setImageBitmap(bitmapR);


    }

    // method used to set middle image to G value of left image
    private void setMiddleRGB(){

        // checks if bitmap has already been generated so it doesnt remake it
        // if it has already been created just sets the image view to that bitmap
        if(bitmapG != null){
            imageMiddle.setImageBitmap(bitmapG);
            return;
        }

        // copies scaled img to bitmapR
        bitmapG = scaledImgLeft.copy(Bitmap.Config.ARGB_8888, true);
        int width = scaledImgLeft.getWidth();
        int height = scaledImgLeft.getHeight();

        // creates array to hold pixel values uses getPixels to copy values to the array.
        int[] pixels = new int[width*height];
        scaledImgLeft.getPixels(pixels, 0, width, 0, 0, width, height);

        // for loop to iterate through each pixel and change rgb values accordingly
        int pixel, a,r,g,b;
        for(int i = 0; i < pixels.length; i++){
            pixel = pixels[i];
            a = Color.alpha(pixel);
            r = 0;
            g = Color.green(pixel);
            b = 0;

            // sets the new rgb values for the current pixel
            pixels[i] = Color.argb(a,r,g,b);

        }

        // sets the new pixels to the bitmap
        bitmapG.setPixels(pixels, 0, width, 0, 0, width, height);


        // sets the image view to the bitmap
        imageMiddle.setImageBitmap(bitmapG);

    }

    // method used to set right image to B value of left image
    private void setRightRGB() {

        // checks if bitmap has already been generated so it doesnt remake it
        // if it has already been created just sets the image view to that bitmap
        if(bitmapB != null){
            imageRight.setImageBitmap(bitmapB);
            return;
        }

        // copies scaled img to bitmapR
        bitmapB = scaledImgLeft.copy(Bitmap.Config.ARGB_8888, true);
        int width = scaledImgLeft.getWidth();
        int height = scaledImgLeft.getHeight();

        // creates array to hold pixel values uses getPixels to copy values to the array.
        int[] pixels = new int[width*height];
        scaledImgLeft.getPixels(pixels, 0, width, 0, 0, width, height);

        // for loop to iterate through each pixel and change rgb values accordingly
        int pixel, a,r,g,b;
        for(int i = 0; i < pixels.length; i++){
            pixel = pixels[i];
            a = Color.alpha(pixel);
            r = 0;
            g = 0;
            b = Color.blue(pixel);

            // sets the new rgb values for the current pixel
            pixels[i] = Color.argb(a,r,g,b);

        }

        // sets the new pixels to the bitmap
        bitmapB.setPixels(pixels, 0, width, 0, 0, width, height);

        // sets image view to modified bitmap
        imageRight.setImageBitmap(bitmapB);

    }

    // method used to set left image to Y value of left image using YCbCr
    private void setLeftYCbCr() {

        // checks if bitmap already exists
        if(bitmapY != null){
            imageLeft.setImageBitmap(bitmapY);
            return;
        }

        // copies scaled img to bitmapY
        bitmapY = scaledImgLeft.copy(Bitmap.Config.ARGB_8888, true);
        int width = scaledImgLeft.getWidth();
        int height = scaledImgLeft.getHeight();

        // creates array to hold pixel values uses getPixels to copy values to the array.
        int[] pixels = new int[width*height];
        scaledImgLeft.getPixels(pixels, 0, width, 0, 0, width, height);

        // for loop to go through each pixel
        int a,r,g,b, pixel, Y, Cb, Cr;
        for(int i = 0; i < pixels.length; i++){
            // gets current pixel then gets ARGB values
            pixel = pixels[i];
            a = Color.alpha(pixel);
            r = Color.red(pixel);
            g = Color.green(pixel);
            b = Color.blue(pixel);

            // calculates the Y value
            Y = (int) (0.299 * r + 0.587 * g + 0.114 * b);

            // sets other values to same as Y value
            Cb = Y;
            Cr = Y;

            // calculates new pixel value and assigns it to current pixel
            pixels[i] = Color.argb(a,Y,Cb,Cr);

        }

        // sets pixels to bitmap then sets the image view
        bitmapY.setPixels(pixels, 0, width, 0, 0, width, height);
        imageLeft.setImageBitmap(bitmapY);

    }

    // method used to set middle image to Cb value of left image using YCbCr
    private void setMiddleYCbCr() {

        // checks if bitmap already exists
        if(bitmapCb != null){
            imageMiddle.setImageBitmap(bitmapCb);
            return;
        }

        // copies scaled img to bitmapCb
        bitmapCb = scaledImgLeft.copy(Bitmap.Config.ARGB_8888, true);
        int width = scaledImgLeft.getWidth();
        int height = scaledImgLeft.getHeight();

        // creates array to hold pixel values uses getPixels to copy values to the array.
        int[] pixels = new int[width*height];
        scaledImgLeft.getPixels(pixels, 0, width, 0, 0, width, height);

        // for loop to go through each pixel
        int a,r,g,b, pixel, Y, Cb, Cr;
        for(int i = 0; i < pixels.length; i++){
            // gets current pixel then gets ARGB values
            pixel = pixels[i];
            a = Color.alpha(pixel);
            r = Color.red(pixel);
            g = Color.green(pixel);
            b = Color.blue(pixel);

            // calculates the Cb value
            Cb =  (int) (128-0.169 *   r-0.331   * g + 0.500 * b); // + 128 offset not needed

            // sets other values to same as Cb value
            Y = Cb;
            Cr = Cb;

            // calculates new pixel value and assigns it to current pixel
            pixels[i] = Color.argb(a,Y,Cb,Cr);

        }

        // sets pixels to bitmap then sets the image view
        bitmapCb.setPixels(pixels, 0, width, 0, 0, width, height);
        imageMiddle.setImageBitmap(bitmapCb);

    }

    // method used to set right image to Cr value of left image using YCbCr
    private void setRightYCbCr(){

        // checks if bitmap already exists
        if(bitmapCr != null){
            imageRight.setImageBitmap(bitmapCr);
            return;
        }

        // copies scaled img to bitmapCr
        bitmapCr = scaledImgLeft.copy(Bitmap.Config.ARGB_8888, true);
        int width = scaledImgLeft.getWidth();
        int height = scaledImgLeft.getHeight();

        // creates array to hold pixel values uses getPixels to copy values to the array.
        int[] pixels = new int[width*height];
        scaledImgLeft.getPixels(pixels, 0, width, 0, 0, width, height);

        // for loop to go through each pixel
        int a,r,g,b, pixel, Y, Cb, Cr;
        for(int i = 0; i < pixels.length; i++){
            // gets current pixel then gets ARGB values
            pixel = pixels[i];
            a = Color.alpha(pixel);
            r = Color.red(pixel);
            g = Color.green(pixel);
            b = Color.blue(pixel);

            // calculates the Cr value
            Cr =  (int) (128+0.500 *   r - 0.419 * g - 0.081 * b);// + 128 offset not needed

            // sets other values to same as Cr value
            Y = Cr;
            Cb = Cr;

            // calculates new pixel value and assigns it to current pixel
            pixels[i] = Color.argb(a,Y,Cb,Cr);

        }

        // sets pixels to bitmap then sets the image view
        bitmapCr.setPixels(pixels, 0, width, 0, 0, width, height);
        imageRight.setImageBitmap(bitmapCr);

    }
}