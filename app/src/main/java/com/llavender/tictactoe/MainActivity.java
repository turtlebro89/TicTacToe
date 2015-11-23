package com.llavender.tictactoe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

    SharedPreferences prefs;

    Intent starterIntent;

    boolean someoneWon;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
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
                Toast.makeText(this, "User Wins : " + prefs.getInt("USER_WINS", 0) + "\n Computer" +
                        " Wins: " + prefs.getInt("COMP_WINS", 0) + "\n Tie Games: " + prefs.getInt
                        ("CAT_WINS", 0), Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setButtonClickListeners(){
        for(final ImageButton button : buttonList){

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!someoneWon) {
                        button.setBackgroundColor(Color.RED);

                        if (didUserWin()) {
                            logResults("user");
                            someoneWon = true;
                            Toast.makeText(getApplicationContext(), "You Won!", Toast.LENGTH_SHORT).show();
                        }
                        else if (allSquaresTaken()) {
                            Toast.makeText(getApplicationContext(), "Cat's game", Toast.LENGTH_SHORT).show();
                            logResults("cat");
                            someoneWon = true;
                        }
                        else {
                            generateComputerMove();

                            if (didComputerWin()) {
                                Toast.makeText(getApplicationContext(), "You LOSE!", Toast.LENGTH_SHORT).show();
                                logResults("computer");
                                someoneWon = true;
                            }
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

        prefs = getSharedPreferences("TICTACTOE", 0);

        baseColor = ((ColorDrawable)button1.getBackground()).getColor();

        someoneWon = false;
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

        if (box1Color == Color.RED && box2Color == Color.RED && box3Color == Color.RED) {
            userWon = true;
        } else if (box1Color == Color.RED && box4Color == Color.RED && box7Color == Color.RED){
            userWon = true;
        } else if(box1Color == Color.RED && box5Color == Color.RED && box9Color == Color.RED){
            userWon = true;
        } else if (box2Color == Color.RED && box5Color == Color.RED && box8Color == Color.RED) {
            userWon = true;
        } else if (box3Color == Color.RED && box6Color == Color.RED && box9Color == Color.RED) {
            userWon = true;
        }else if(box3Color == Color.RED && box5Color == Color.RED && box7Color == Color.RED){
            userWon = true;
        } else if (box4Color == Color.RED && box5Color == Color.RED && box6Color == Color.RED){
            userWon = true;
        } else if (box7Color == Color.RED && box8Color == Color.RED && box9Color == Color.RED){
            userWon = true;
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

        if (box1Color == Color.BLUE && box2Color == Color.BLUE && box3Color == Color.BLUE) {
            computerWon = true;
        } else if (box1Color == Color.BLUE && box4Color == Color.BLUE && box7Color == Color.BLUE){
            computerWon = true;
        } else if(box1Color == Color.BLUE && box5Color == Color.BLUE && box9Color == Color.BLUE){
            computerWon = true;
        } else if (box2Color == Color.BLUE && box5Color == Color.BLUE && box8Color == Color.BLUE) {
            computerWon = true;
        } else if (box3Color == Color.BLUE && box6Color == Color.BLUE && box9Color == Color.BLUE) {
            computerWon = true;
        }else if(box3Color == Color.BLUE && box5Color == Color.BLUE && box7Color == Color.BLUE){
            computerWon = true;
        } else if (box4Color == Color.BLUE && box5Color == Color.BLUE && box6Color == Color.BLUE){
            computerWon = true;
        } else if (box7Color == Color.BLUE && box8Color == Color.BLUE && box9Color == Color.BLUE){
            computerWon = true;
        }
        return computerWon;
    }

    public void logResults(String winner){
        disableButtons();
        SharedPreferences.Editor editor = prefs.edit();
        if(winner.equals("user")){
            editor.putInt("USER_WINS", prefs.getInt("USER_WINS", 0) + 1);
        } else if (winner.equals("cat")){
            editor.putInt("CAT_WINS", prefs.getInt("CAT_WINS", 0) + 1);
        } else if (winner.equals("computer")){
            editor.putInt("COMP_WINS", prefs.getInt("COMP_WINS", 0) + 1);
        }
        editor.commit();
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

}
