package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    TextView presentStatus;
    ImageView section0, section1, section2, section3, section4, section5, section6, section7, section8, reload;
    boolean isGameActive = true;
    /*   Stats representation-
         0 = X
         1 = O
         2 = Null   */
    int activePlayerState = 0;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8},  //across conditions
                                {0,3,6}, {1,4,7}, {2,5,8},  //down conditions
                                {0,4,8}, {2,4,6}};          //cross conditions

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presentStatus = findViewById(R.id.status);

        section0 = findViewById(R.id.imageView11);
        section1 = findViewById(R.id.imageView12);
        section2 = findViewById(R.id.imageView13);
        section3 = findViewById(R.id.imageView21);
        section4 = findViewById(R.id.imageView22);
        section5 = findViewById(R.id.imageView23);
        section6 = findViewById(R.id.imageView31);
        section7 = findViewById(R.id.imageView32);
        section8 = findViewById(R.id.imageView33);

        reload = findViewById(R.id.reloadBtn);
        reload.setVisibility(View.INVISIBLE);
    }

    //when section inside screen is tapped screenTap function works, onClick-Listner in XML file
    public void screenTap(View view){
        reload.setVisibility(View.VISIBLE);
        ImageView imgView = (ImageView) view;
        int tappedImage = Integer.parseInt(imgView.getTag().toString());
        if(!isGameActive){
            gameReset(view);
        }
        if(gameState[tappedImage] == 2) {
            gameState[tappedImage] = activePlayerState;
            imgView.setTranslationY(-1000f);    // pre-work for animation
            if (activePlayerState == 0) {
                imgView.setImageResource(R.drawable.x);
                activePlayerState = 1;
                presentStatus.setText("O's Turn - Tap to play");
            } else {
                imgView.setImageResource(R.drawable.o);
                activePlayerState = 0;
                presentStatus.setText("X's Turn - Tap to play");
            }
            imgView.animate().translationYBy(1000f).setDuration(300);   //for actual animation
        }

        // Checking if any player has won
        for(int[] winPosition: winningPositions){
            if(gameState[winPosition[0]] == gameState[winPosition[1]] &&
                    gameState[winPosition[1]] == gameState[winPosition[2]] &&
                    gameState[winPosition[0]]!=2){
                // Somebody has won! - Find out who!
                String winnerStr;
                isGameActive = false;
                if(gameState[winPosition[0]] == 0){
                    winnerStr = "X has won";
                }
                else{
                    winnerStr = "O has won";
                }
                // Update the status bar for winner announcement
                presentStatus.setText(winnerStr);

            }
        }

        //Check for Draw condition
        boolean emptySection = false;
        for (int squareState : gameState) {
            if (squareState == 2) {
                emptySection = true;
                break;
            }
        }
        if (!emptySection && isGameActive) {
            // Game is a draw
            isGameActive = false;
            String winnerStr;
            winnerStr = "it's a tie, No one won";
            reload.setVisibility(View.VISIBLE);
            presentStatus.setText(winnerStr);
        }



    }

    public void gameReset(View view) {
        reload.setVisibility(View.INVISIBLE);
        isGameActive = true;
        activePlayerState = 0;
        Arrays.fill(gameState, 2);  //making all states 2(i.e. Null)

        section0.setImageResource(0);
        section1.setImageResource(0);
        section2.setImageResource(0);
        section3.setImageResource(0);
        section4.setImageResource(0);
        section5.setImageResource(0);
        section6.setImageResource(0);
        section7.setImageResource(0);
        section8.setImageResource(0);

        presentStatus.setText("X's Turn - Tap to play");

    }
}

