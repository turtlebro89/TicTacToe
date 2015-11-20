package com.llavender.tictactoe;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageButton button1, button2, button3, button4, button5, button6, button7, button8, button9;
    ArrayList<ImageButton> buttonList;
    LinearLayout wholeView;
    int baseColor;

    Intent starterIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        starterIntent = getIntent();
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Tic Tac Toe");
        setSupportActionBar(myToolbar);

        initializeVariables();

        setButtonClickListeners();
        disableButtons();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // reset our drawing state
                        ImageButton currButton = isTouchWithinBounds(ev.getX(), ev.getY());
                        if(currButton != null){
                            currButton.callOnClick();
                        }
                        break;
                }
        return super.dispatchTouchEvent(ev);
    }


    public void setButtonClickListeners(){
        for(final ImageButton button : buttonList){

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    button.setEnabled(false);
                    button.setBackgroundColor(Color.RED);

                    if(didUserWin()){
                        logResults("user");
                        disableButtons();
                    } else if (allSquaresTaken()) {
                        Toast.makeText(getApplicationContext(), "Cat's game", Toast.LENGTH_SHORT).show();
                        logResults("cat");
                    } else {
                        generateComputerMove();

                        if(didComputerWin()){
                            logResults("computer");
                        }
                    }
                }
            });
        }
    }


    public boolean allSquaresTaken(){
        boolean sameColor = false;
        ArrayList<Integer> intValues = new ArrayList<>();

        for(ImageButton button : buttonList){
            intValues.add(((ColorDrawable)button.getBackground()).getColor());
        }

        if(!intValues.contains(baseColor)){
            sameColor = true;
        }

        return sameColor;
    }

    public void initializeVariables(){
        button1 = (ImageButton)findViewById(R.id.box1);
        button2 = (ImageButton)findViewById(R.id.box2);
        button3 = (ImageButton)findViewById(R.id.box3);
        button4 = (ImageButton)findViewById(R.id.box4);
        button5 = (ImageButton)findViewById(R.id.box5);
        button6 = (ImageButton)findViewById(R.id.box6);
        button7 = (ImageButton)findViewById(R.id.box7);
        button8 = (ImageButton)findViewById(R.id.box8);
        button9 = (ImageButton)findViewById(R.id.box9);
        wholeView = (LinearLayout)findViewById(R.id.wholeView);

        buttonList = new ArrayList<ImageButton>();

        buttonList.add(button1);
        buttonList.add(button2);
        buttonList.add(button3);
        buttonList.add(button4);
        buttonList.add(button5);
        buttonList.add(button6);
        buttonList.add(button7);
        buttonList.add(button8);
        buttonList.add(button9);

        baseColor = ((ColorDrawable)button1.getBackground()).getColor();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void generateComputerMove(){
        int boxToActivate = (int) (Math.random() * 8);

        if(((ColorDrawable)(buttonList.get(boxToActivate).getBackground())).getColor() == Color
                .RED || ((ColorDrawable)(buttonList.get(boxToActivate).getBackground())).getColor()
                == Color.BLUE){
            generateComputerMove();
        } else {
            buttonList.get(boxToActivate).setBackgroundColor(Color.BLUE);
            buttonList.get(boxToActivate).setEnabled(false);
        }
    }

    public boolean didUserWin(){
        int box1Color = ((ColorDrawable) button1.getBackground()).getColor();
        int box2Color = ((ColorDrawable) button2.getBackground()).getColor();
        int box3Color = ((ColorDrawable) button3.getBackground()).getColor();
        int box4Color = ((ColorDrawable) button4.getBackground()).getColor();
        int box5Color = ((ColorDrawable) button5.getBackground()).getColor();
        int box6Color = ((ColorDrawable) button6.getBackground()).getColor();
        int box7Color = ((ColorDrawable) button7.getBackground()).getColor();
        int box8Color = ((ColorDrawable) button8.getBackground()).getColor();
        int box9Color = ((ColorDrawable) button9.getBackground()).getColor();

        boolean userWon = false;

        if (box1Color == Color.RED) {
            if(box2Color == box1Color && box2Color == box3Color) {
                userWon = true;
            } else if (box1Color == box4Color && box4Color == box7Color) {
                userWon = true;
            } else if(box1Color == box5Color && box5Color == box9Color){
                userWon = true;
            }
        } else if (box2Color == Color.RED) {
            if (box2Color == box5Color && box5Color == box8Color) {
                userWon = true;
            }
        } else if (box3Color == Color.RED){
            if(box3Color == box6Color && box6Color == box9Color) {
                userWon = true;
            } else if(box3Color == box5Color && box5Color == box7Color){
                userWon = true;
            }
        } else if (box4Color == Color.RED){
            if(box4Color == box5Color && box5Color == box6Color){
                userWon = true;
            }
        } else if (box7Color == Color.RED){
            if(box7Color == box8Color && box8Color == box9Color){
                userWon = true;
            }
        }

        return userWon;
    }

    public boolean didComputerWin(){
        int box1Color = ((ColorDrawable) button1.getBackground()).getColor();
        int box2Color = ((ColorDrawable) button2.getBackground()).getColor();
        int box3Color = ((ColorDrawable) button3.getBackground()).getColor();
        int box4Color = ((ColorDrawable) button4.getBackground()).getColor();
        int box5Color = ((ColorDrawable) button5.getBackground()).getColor();
        int box6Color = ((ColorDrawable) button6.getBackground()).getColor();
        int box7Color = ((ColorDrawable) button7.getBackground()).getColor();
        int box8Color = ((ColorDrawable) button8.getBackground()).getColor();
        int box9Color = ((ColorDrawable) button9.getBackground()).getColor();
        boolean computerWon = false;

        if (box1Color == Color.BLUE) {
            if(box2Color == box1Color && box2Color == box3Color) {
                computerWon = true;
            } else if (box1Color == box4Color && box4Color == box7Color) {
                computerWon = true;
            } else if(box1Color == box5Color && box5Color == box9Color){
                computerWon = true;
            }
        } else if (box2Color == Color.BLUE) {
            if (box2Color == box5Color && box5Color == box8Color) {
                computerWon = true;
            }
        } else if (box3Color == Color.BLUE){
            if(box3Color == box6Color && box6Color == box9Color) {
                computerWon = true;
            } else if(box3Color == box5Color && box5Color == box7Color){
                computerWon = true;
            }
        } else if (box4Color == Color.BLUE){
            if(box4Color == box5Color && box5Color == box6Color){
                computerWon = true;
            }
        } else if (box7Color == Color.BLUE){
            if(box7Color == box8Color && box8Color == box9Color){
                computerWon = true;
            }
        }
        return computerWon;
    }

    public void logResults(String winner){
        Toast.makeText(getApplicationContext(), "someone won", Toast.LENGTH_LONG).show();
    }

    public void disableButtons() {
        for(ImageButton button : buttonList) {
            button.setEnabled(false);
        }
    }

    public ImageButton isTouchWithinBounds(float touchX, float touchY){
        ImageButton buttonToReturn = null;

        int[] bounds = new int[2];

        for(ImageButton button : buttonList){

            button.getLocationOnScreen(bounds);

            if(bounds[0] < touchX && (bounds[0] + button.getWidth()) > touchX
                    && bounds[1] < touchY && (bounds[1] + button.getHeight()) > touchY){
                buttonToReturn = button;
            }
        }

        return buttonToReturn;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.action_refresh:
                finish();
                startActivity(starterIntent);
                break;
            case R.id.action_show_stats:
                //Toast wins and losses
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
