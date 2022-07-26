package com.example.simplecolorpicker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View; //View is the superclass for all GUI components, ie, TextView, ImageView, Button, etc
import android.widget.ImageView;
import android.widget.TextView;

public class ColorWheel extends AppCompatActivity
{
    //declare our views
    ImageView colorWheel; //image of color wheel
    TextView colorValues; //value of pixel on color wheel
    TextView header; //title instructing user to pick color
    TextView subheader; //beneath the header, provides instructions to user
    View extractColor; //color of pixel on color wheel

    /*
         What's a bitmap?
         A bitmap is a digital image composed of a matrix of dots; each dot (point on XY coordinate system)
         corresponds to a pixel.
     */
    Bitmap bitmap; //need bitmap to extract pixel

    @Override
    /*
        This method is called when activity is created and contains static set up.
        savedInstanceState is a Bundle which create activity's previously frozen state.
     */
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_wheel);

        colorWheel= findViewById(R.id.colorPicker);
        colorValues= findViewById(R.id.displayValues);
        extractColor= findViewById(R.id.displayColor);
        header=findViewById(R.id.Header);
        subheader=findViewById(R.id.Subheader);

        /*
            Note - R is a class that contains the ID's of all Views.
            Hence, R.id.viewName assigns our view to variable
         */

        //preparation to save ImageView into bitmap - create snapshot of ImageView
        colorWheel.setDrawingCacheEnabled(true);
        colorWheel.buildDrawingCache(true); //draws colorWheel view in bitmap

        //make images clickable/touchable

        /*
           A listener is an interface that provides functionality for a UI component with which you can interact (a view) - in this case,
           colorWheel (ImageView).

           In this case, we are using the OnTouchListener interface; remember, an interface is a group of related, undefined methods, so it's our job to define the methods.

           The OnTouchListener interface has an onTouch method.  The code for the functionality of the view, ie., what happens when the colorWheel is clicked, is written inside the method.

           The listener and its method which controls what happens when the wheel is touched is connected to the colorWheel view using the setOnTouchListener method.

           Listeners in Java are typically implemented using an anonymous class.
           An anonymous class is a local class without a name.
         */
        //since we need coordinate information to get the pixels, we will use OnTouchListener and not OnClickListener.
        //set OnTouchListener using anonymous class:
        colorWheel.setOnTouchListener(new View.OnTouchListener() //we are implementing the onTouchListener interface using an anonymous class
        {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) //here we define the onTouch method, which is in the OnTouchListener interface
            {//in the body of this method, we interpret MotionEvent data and base on interpretation, we handle the touch.
                if (event.getAction()==MotionEvent.ACTION_DOWN || event.getAction()==MotionEvent.ACTION_MOVE) //if we click on the view or drag our cursor across the view...
                {
                    bitmap = colorWheel.getDrawingCache(); //assign snapshot from ImageView into bitmap so that we can extract pixels
                    int pixel = bitmap.getPixel((int) event.getX(), (int) event.getY()); //get coordinates where colorWheel is touched
                    //bitmap.getPixel returns a color at the specified location based on the coordinates./
                    //the value returned is a hexadecimal value representing color.

                    //Now that we have our hexadecimal value, we need to extract our rgb values from it.
                    int r= Color.red(pixel); //returns red component value
                    int g=Color.green(pixel); //returns green component value
                    int b=Color.blue(pixel); //returns blue components value
                    //int opacity=Color.alpha(pixel); //returns opacity value, where 0 is fully transparent and 255 is fully opaque.

                    //get hex values of pixel value when touched
                    String hex="#"+Integer.toHexString(pixel);
                    //toHexString- built in function that returns hexadecimal

                    //display color of pixel on the View
                    extractColor.setBackgroundColor(Color.rgb(r,g,b));

                    //display the rgb and hex values in the TextView - values and header/subheader will take on color of selected pixel.
                    header.setTextColor(pixel);
                    subheader.setTextColor(pixel);
                    colorValues.setTextColor(pixel);
                    colorValues.setText("R: "+r+" G: "+g+" B: "+b+"\nHexadecimal: "+hex.toUpperCase());
                }

                return true;
            }
        }); //there is a semi-colon here because anonymous classes must be part of a statement.

    }
}