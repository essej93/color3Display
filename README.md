# color3Display

This application was created as part of an assignment to understand how to collect and edit RGB values then regenerate the image with new values through Bitmaps in
android studio.

LeftRightRGB first collects the RGB values for the original image and sets the respective RGB values to 0 so we can view the R, G and B values seperately for the same
image.

YCbCr gets the RGB values, converts them over to their Y, Cb and Cr values respectively then sets all RGB values of the same pixel to the same Y, Cb or Cr values 
respectively. (Eg. Cb is set to Y value and Cr is set to Y value to then generate a greyscale image for the Y pixel value).

Left+Right is nothing special as it only shows 2 images next to eachother.

Anaglyph gets the pixel values of 2 different (but almost identical) images. It then collects the RGB value of each pixel for both images, converts it to their luminance
pixel value, then uses these to generate a new pixel value to create an anaglyph image.
It uses following values for the new RGB pixel Color.rgb(rightLuminancePixel, leftLuminancePixel, rightLuminancePixel).
