package com.llavender.tictactoe;

import android.app.FragmentManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageButton button1, button2, button3, button4, button5, button6, button7, button8, button9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (ImageButton)findViewById(R.id.box1);
        button2 = (ImageButton)findViewById(R.id.box2);
        button3 = (ImageButton)findViewById(R.id.box3);
        button4 = (ImageButton)findViewById(R.id.box4);
        button5 = (ImageButton)findViewById(R.id.box5);
        button6 = (ImageButton)findViewById(R.id.box6);
        button7 = (ImageButton)findViewById(R.id.box7);
        button8 = (ImageButton)findViewById(R.id.box8);
        button9 = (ImageButton)findViewById(R.id.box9);

        ArrayList<ImageButton> buttonList = new ArrayList<ImageButton>();

        buttonList.add(button1);
        buttonList.add(button2);
        buttonList.add(button3);
        buttonList.add(button4);
        buttonList.add(button5);
        buttonList.add(button6);
        buttonList.add(button7);
        buttonList.add(button8);
        buttonList.add(button9);

        for(final ImageButton button : buttonList){

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    button.setEnabled(false);
                    button.setBackgroundColor(Color.RED);

                    didSomeoneWin();
                }
            });
        }

    }

    public void didSomeoneWin(){
        ColorDrawable box1Color = (ColorDrawable)button1.getBackground();
        ColorDrawable box2Color = (ColorDrawable)button2.getBackground();
        ColorDrawable box3Color = (ColorDrawable)button3.getBackground();
        ColorDrawable box4Color = (ColorDrawable)button4.getBackground();
        ColorDrawable box5Color = (ColorDrawable)button5.getBackground();
        ColorDrawable box6Color = (ColorDrawable)button6.getBackground();
        ColorDrawable box7Color = (ColorDrawable)button7.getBackground();
        ColorDrawable box8Color = (ColorDrawable)button8.getBackground();
        ColorDrawable box9Color = (ColorDrawable)button9.getBackground();

        System.out.print("hello");

        if((box1Color.getColor() == Color.RED) && (box2Color == box1Color) && (box2Color ==
                box3Color)){
            Toast.makeText(getApplicationContext(), "Someone Won!!", Toast.LENGTH_LONG).show();

        }

    }
}
