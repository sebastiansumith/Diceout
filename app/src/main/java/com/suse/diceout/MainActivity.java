package com.suse.diceout;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int score;
    private TextView rollResult;
    private Random random;
    private int die1;
    private int die2;
    private int die3;
    private List<Integer> dices;
    private List<ImageView> imageViews;
    private TextView socreText;

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

        score = 0;
        rollResult = (TextView)findViewById(R.id.rollResult);
        random = new Random();
        dices = new ArrayList<>();
        loadDiceImages();
        socreText = (TextView)findViewById(R.id.scoreText);

    }

    private void loadDiceImages() {
        ImageView imageView1 = (ImageView)findViewById(R.id.die1Image);
        ImageView imageView2 = (ImageView)findViewById(R.id.die2Image);
        ImageView imageView3 = (ImageView)findViewById(R.id.die3Image);
        imageViews = new ArrayList<>();
        imageViews.add(imageView1);
        imageViews.add(imageView2);
        imageViews.add(imageView3);
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

    public void rollDice(View v){

        die1 = random.nextInt(6)+1;
        die2 = random.nextInt(6)+1;
        die3 = random.nextInt(6)+1;
        dices.clear();

        dices.add(die1);
        dices.add(die2);
        dices.add(die3);

        for(int dieOffset = 0; dieOffset<3; dieOffset++){
            String imageName = "die_"+dices.get(dieOffset)+".png";
            try{
                InputStream inputStream = getAssets().open(imageName);
                Drawable d = Drawable.createFromStream(inputStream, null);
                imageViews.get(dieOffset).setImageDrawable(d);
            }catch (IOException e){
                e.getStackTrace();
            }
        }

        String msg;
        if(die1 == die2 && die1 == die3){
            msg = "You scored triples.You get 100 points";
            score+= 100;
        }else if(die1 == die2 || die1 == die3 || die2==die3){
            msg = "You scored doubles.You get 50 points";
            score+= 50;
        }else{
            msg = "You diddn't score. Better luck next time!!!";
        }
        rollResult.setText(msg);
        socreText.setText("Score: "+score);
    }
}
