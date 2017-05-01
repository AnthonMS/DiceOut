package com.example.anthonsteiness.diceout;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Field to hold result
    TextView rollResult;

    // Field to hold score
    int score;

    // Field to hold Random generator
    Random ran;

    // Field to hold die values
    int die1, die2, die3;

    // Field to hold list if dice
    ArrayList<Integer> diceList;

    // Field to hold list of dice images
    ArrayList<ImageView> diceImageViews;

    // Field to Hold score text
    TextView scoreText;

    MediaPlayer diceSound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDice(view);
            }
        });

        // Set initial score
        score = 0;

        // Link instances to widgets in the activity view
        rollResult = (TextView) findViewById(R.id.rollResult);
        scoreText = (TextView) findViewById(R.id.scoreText);

        // Create sound logic here
        diceSound = MediaPlayer.create(this, R.raw.dice);



        // Create greeting
        Toast.makeText(getApplicationContext(),"Welcome to DiceOut!",Toast.LENGTH_SHORT).show();

        ran = new Random();

        diceList = new ArrayList<Integer>();

        ImageView die1Image = (ImageView) findViewById(R.id.die1Image);
        ImageView die2Image = (ImageView) findViewById(R.id.die2Image);
        ImageView die3Image = (ImageView) findViewById(R.id.die3Image);

        diceImageViews = new ArrayList<ImageView>();

        diceImageViews.add(die1Image);
        diceImageViews.add(die2Image);
        diceImageViews.add(die3Image);

    }

    public void rollDice(View v)
    {
        rollResult.setText("Clicked!");


            die1 = ran.nextInt(6)+1;
            die3 = ran.nextInt(6)+1;
            die2 = ran.nextInt(6)+1;

            // Set dice values into ArrayList
            diceList.clear();
            diceList.add(die1);
            diceList.add(die2);
            diceList.add(die3);

        // Add sound logic here for the Button
        diceSound.start();



            for (int dieOfSet = 0; dieOfSet < 3; dieOfSet++) {
                String imageName = "blackdie_" + diceList.get(dieOfSet) + ".png";

                try {
                    InputStream stream = getAssets().open(imageName);
                    Drawable d = Drawable.createFromStream(stream,null);
                    diceImageViews.get(dieOfSet).setImageDrawable(d);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        // Build messege with result
        String msg;

        if (die1 == die2 && die1 == die3)
        {
            // Triples
            int scoreDelta = die1 * 100;
            msg = "You rolled a triple " + die1 + "! You score " + scoreDelta + " points!";
            score += scoreDelta;
        }
        else if (die1 == die2 || die1 == die3 || die2 == die3)
        {
            // Doubles
            msg = "You rolled doubles for 50 points!";
            score += 50;
        }
        else {
            msg = "You did not score this round.. Try again!";
        }

        rollResult.setText(msg);
        scoreText.setText("Score: " + score);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
