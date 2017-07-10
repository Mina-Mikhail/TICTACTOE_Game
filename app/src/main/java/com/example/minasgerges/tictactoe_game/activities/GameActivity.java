package com.example.minasgerges.tictactoe_game.activities;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minasgerges.tictactoe_game.R;
import com.sdsmdg.tastytoast.TastyToast;

public class GameActivity extends AppCompatActivity{

    TextView player1_name, player2_name;

    TextView player1_score, player2_score;

    int player1_score_valuve = 0, player2_score_valuve = 0, turnsCount = 0, winner = 0;

    boolean player_one_Turn = true, playFlag = true;

    ImageView[] checkPlace = new ImageView[9];

    Button newGameBTN, resetBTN;

    // the state of check place (0 -> (initial)empty) (1 -> X) (2 -> O)
    int XOArray[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // to initialize all views which in our layout
        initialViews();
        // to set click listeners for all views
        setViewsListeners();

    }


    public void initialViews() {
        player1_name = (TextView) findViewById(R.id.user1_name);
        player2_name = (TextView) findViewById(R.id.user2_name);

        player1_score = (TextView) findViewById(R.id.user1_score);
        player2_score = (TextView) findViewById(R.id.user2_score);

        checkPlace[0] = (ImageView) findViewById(R.id.place1);
        checkPlace[1] = (ImageView) findViewById(R.id.place2);
        checkPlace[2] = (ImageView) findViewById(R.id.place3);
        checkPlace[3] = (ImageView) findViewById(R.id.place4);
        checkPlace[4] = (ImageView) findViewById(R.id.place5);
        checkPlace[5] = (ImageView) findViewById(R.id.place6);
        checkPlace[6] = (ImageView) findViewById(R.id.place7);
        checkPlace[7] = (ImageView) findViewById(R.id.place8);
        checkPlace[8] = (ImageView) findViewById(R.id.place9);

        newGameBTN = (Button) findViewById(R.id.new_game_btn);
        resetBTN = (Button) findViewById(R.id.reset_btn);

        // receive the players names from StartActivity
        String user1_name = getIntent().getExtras().getString("user_name1");
        String user2_name = getIntent().getExtras().getString("user_name2");
        // show the text in the text view
        player1_name.setText(user1_name);
        player2_name.setText(user2_name);
    }


    private void setViewsListeners() {
        newGameBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to check and save the highest value
                saveHighScore();

                // to show toast with player turn
                if (  XOArray[0] == 0 && XOArray[1] == 0 && XOArray[2] == 0 && XOArray[3] == 0 && XOArray[4] == 0 && XOArray[5] == 0 && XOArray[6] == 0 && XOArray[7] == 0 && XOArray[8] == 0 )
                {
                    // all places empty
                    // do nothing
                }
                else
                {
                    if ( winner == 1 )
                    {
                        TastyToast.makeText(getApplicationContext(), player1_name.getText().toString() + "'s turn", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    }
                    else if ( winner == 2 )
                    {
                        TastyToast.makeText(getApplicationContext(), player2_name.getText().toString() + "'s turn", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    }
                }

                // to create new game
                playFlag = true;
                turnsCount = 0;
                // to remove X,O signs from all places and set all places ass empty
                for ( int i=0; i < 9; i++ )
                {
                    checkPlace[i].setImageDrawable( new ColorDrawable(getResources().getColor(R.color.white)) );
                    XOArray[i] = 0;
                }
            }



        });

        resetBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to check and save the highest value
                saveHighScore();

                // to reset the game
                player_one_Turn = true;
                playFlag = true;
                turnsCount = 0;

                player1_score_valuve = 0;
                player1_score.setText( Integer.toString(player1_score_valuve) );

                player2_score_valuve = 0;
                player2_score.setText( Integer.toString(player2_score_valuve) );

                // to remove X,O signs from all places and set all places ass empty
                for ( int i = 0; i < 9; i++ )
                {
                    checkPlace[i].setImageDrawable( new ColorDrawable(getResources().getColor(R.color.white)) );
                    XOArray[i] = 0;
                }
            }
        });
    }

    public void playGame(View v) {
        if (playFlag) {
            // in this case the game is still running and players can play
            for ( int i = 0; i < 9; i++ )
            {
                if ( v == checkPlace[i] )
                {
                    if (  XOArray[i] == 0 )
                    {
                        // to check the current state of the clicked place
                        if ( player_one_Turn )
                        {
                            // this is player one turn ( X sign )
                            checkPlace[i].setImageResource(R.drawable.x);
                            XOArray[i] = 1;
                        }
                        else
                        {
                            // this is player two turn ( O sign )
                            checkPlace[i].setImageResource(R.drawable.o);
                            XOArray[i] = 2;
                        }
                        // change state of player one turn
                        player_one_Turn = !player_one_Turn;
                        // to increment the number of turns
                        turnsCount++;
                        // TO CHECK THE WINNER
                        checkWinner();

                        break;
                    }
                }
            }
        }

        else
        {
            // in this case the game is finished and players can not play
            TastyToast.makeText(getApplicationContext(), "The game is finished!!, Start new game", TastyToast.LENGTH_LONG, TastyToast.WARNING);
        }
    }

    public void checkWinner() {
        if (    (XOArray[0] == 1 && XOArray[1] == 1 && XOArray[2] == 1) ||
                (XOArray[3] == 1 && XOArray[4] == 1 && XOArray[5] == 1) ||
                (XOArray[6] == 1 && XOArray[7] == 1 && XOArray[8] == 1) ||

                (XOArray[0] == 1 && XOArray[3] == 1 && XOArray[6] == 1) ||
                (XOArray[1] == 1 && XOArray[4] == 1 && XOArray[7] == 1) ||
                (XOArray[2] == 1 && XOArray[5] == 1 && XOArray[8] == 1) ||

                (XOArray[0] == 1 && XOArray[4] == 1 && XOArray[8] == 1) ||
                (XOArray[2] == 1 && XOArray[4] == 1 && XOArray[6] == 1)   )
        {
            // in case of player one wins
            TastyToast.makeText(getApplicationContext(), player1_name.getText().toString() + " Wins!", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
            player1_score_valuve++;
            player1_score.setText(Integer.toString(player1_score_valuve));

            // set player one as winner
            winner = 1;

            // player one is win, then player one must play first in the next game, make player one turn to be true
            player_one_Turn = true;

            // to check and save the highest value
            saveHighScore();

            playFlag = false;
        }

        else if ( (XOArray[0] == 2 && XOArray[1] == 2 && XOArray[2] == 2) ||
                (XOArray[3] == 2 && XOArray[4] == 2 && XOArray[5] == 2) ||
                (XOArray[6] == 2 && XOArray[7] == 2 && XOArray[8] == 2) ||

                (XOArray[0] == 2 && XOArray[3] == 2 && XOArray[6] == 2) ||
                (XOArray[1] == 2 && XOArray[4] == 2 && XOArray[7] == 2) ||
                (XOArray[2] == 2 && XOArray[5] == 2 && XOArray[8] == 2) ||

                (XOArray[0] == 2 && XOArray[4] == 2 && XOArray[8] == 2) ||
                (XOArray[2] == 2 && XOArray[4] == 2 && XOArray[6] == 2)   )
        {
            // in case of player two wins
            TastyToast.makeText(getApplicationContext(), player2_name.getText().toString() + " Wins!", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
            player2_score_valuve++;
            player2_score.setText(Integer.toString(player2_score_valuve));

            // set player two as winner
            winner = 2;

            // player two is win, then player two must play first in the next game, make player one turn to be false
            player_one_Turn = false;

            // to check and save the highest value
            saveHighScore();

            playFlag = false;
        }

        else
        {
            // in case of no player wins
            if ( turnsCount == 9 )
            {
                TastyToast.makeText(getApplicationContext(), "The game is TIE!", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                playFlag = false;

                // to create new game
                player_one_Turn = true;
                playFlag = true;
                turnsCount = 0;
            }
        }
    }


    public void saveHighScore() {
        if ( player1_score_valuve > player2_score_valuve )
        {
            // we use private mode, because i did not want any other application see the data
            SharedPreferences shared1 = getSharedPreferences("highScore", MODE_PRIVATE);
            // to edit the data
            SharedPreferences.Editor editor = shared1.edit();

            // to get the flag
            boolean highest_Score_flag = shared1.getBoolean("Flag", false);
            if ( highest_Score_flag == false ) {
                // this means no score in the SharedPreferences
                // add data
                // first parameter is key
                // second parameter is value
                editor.putInt("highestValue", player1_score_valuve);
                // save the player name
                String high_score_player_name = player1_name.getText().toString();
                editor.putString("highestValue_Player", high_score_player_name);
                // (Flag -> true .... this means score saved successfully)
                editor.putBoolean("Flag", true);
                // use commit to save the data in the file
                editor.apply();
            }

            else if ( highest_Score_flag == true ) {
                // this means we have one score stored in the SharedPreferences, then compare the current user score with the stored score
                // to get the high score value
                int highest_Score = shared1.getInt("highestValue", 0);
                if ( highest_Score > player1_score_valuve )
                {
                    // do no thing
                }
                else if ( highest_Score < player1_score_valuve )
                {
                    // add data
                    // first parameter is key
                    // second parameter is value
                    editor.putInt("highestValue", player1_score_valuve);
                    // save the player name
                    String high_score_player_name = player1_name.getText().toString();
                    editor.putString("highestValue_Player", high_score_player_name);
                    // (Flag -> true .... this means score saved successfully)
                    editor.putBoolean("Flag", true);
                    // use commit to save the data in the file
                    editor.apply();
                }
            }
        }


        else if ( player2_score_valuve > player1_score_valuve )
        {
            // we use private mode, because i did not want any other application see the data
            SharedPreferences shared1 = getSharedPreferences("highScore", MODE_PRIVATE);
            // to edit the data
            SharedPreferences.Editor editor = shared1.edit();

            // to get the flag
            boolean highest_Score_flag = shared1.getBoolean("Flag", false);
            if ( highest_Score_flag == false ) {
                // this means no score in the SharedPreferences
                // add data
                // first parameter is key
                // second parameter is value
                editor.putInt("highestValue", player2_score_valuve);
                // save the player name
                String high_score_player_name = player2_name.getText().toString();
                editor.putString("highestValue_Player", high_score_player_name);
                // (Flag -> true .... this means score saved successfully)
                editor.putBoolean("Flag", true);
                // use commit to save the data in the file
                editor.apply();
            }

            else if ( highest_Score_flag == true ) {
                // this means we have one score stored in the SharedPreferences, then compare the current user score with the stored score
                // to get the high score value
                int highest_Score = shared1.getInt("highestValue", 0);
                if ( highest_Score > player2_score_valuve )
                {
                    // do no thing
                }
                else if ( highest_Score < player2_score_valuve )
                {
                    // add data
                    // first parameter is key
                    // second parameter is value
                    editor.putInt("highestValue", player2_score_valuve);
                    // save the player name
                    String high_score_player_name = player2_name.getText().toString();
                    editor.putString("highestValue_Player", high_score_player_name);
                    // (Flag -> true .... this means score saved successfully)
                    editor.putBoolean("Flag", true);
                    // use commit to save the data in the file
                    editor.apply();
                }
            }
        }
    }


}
