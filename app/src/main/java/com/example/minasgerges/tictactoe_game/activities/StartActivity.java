package com.example.minasgerges.tictactoe_game.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.minasgerges.tictactoe_game.R;

public class StartActivity extends AppCompatActivity {

    Button play;

    TextView highestScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initialViews();
    }

    public void initialViews() {
        highestScore = (TextView) findViewById(R.id.highest_score);
        // get the highest score from SharedPreferences
        // first parameter is key
        // second parameter is default value, if the SharedPreferences did not find the value with key name
        SharedPreferences shared2 = getSharedPreferences("highScore", MODE_PRIVATE);
        int highest_Score = shared2.getInt("highestValue", 0);
        String highest_Score_player = shared2.getString("highestValue_Player", "player");
        boolean highest_Score_flag = shared2.getBoolean("Flag", false);
        if ( highest_Score_flag == true )
        {
            // in this case the highest score saved successfully on the SharedPreferences
            highestScore.setText( highest_Score_player + ": " + Integer.toString(highest_Score) );
        }
        else if ( highest_Score_flag == false )
        {
            // in this case the highest score not saved successfully on the SharedPreferences
            highestScore.setText("No score recorded");
            highestScore.setTextColor( getResources().getColor(R.color.white) );
        }

        play = (Button) findViewById(R.id.play_btn);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPlayersNameDialog();
            }
        });
    }

    public void setPlayersNameDialog() {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(StartActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.edit_player_name_dialog, null);

        final EditText player1_neme = (EditText) mView.findViewById(R.id.new_name1);
        final EditText player2_neme = (EditText) mView.findViewById(R.id.new_name2);

        Button cancel = (Button) mView.findViewById(R.id.cancel_btn);
        Button ok = (Button) mView.findViewById(R.id.ok_btn);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String player1_name_str = player1_neme.getText().toString().trim();
                String player2_name_str = player2_neme.getText().toString().trim();

                if ( player1_name_str.isEmpty() && player2_name_str.isEmpty() )
                {
                    player1_neme.setError("You must enter name");
                    player2_neme.setError("You must enter name");
                }
                else if ( player1_name_str.isEmpty() )
                {
                    player1_neme.setError("You must enter name");
                }
                else if ( player2_name_str.isEmpty() )
                {
                    player2_neme.setError("You must enter name");
                }
                else
                {
                    Intent i = new Intent(getApplicationContext(), GameActivity.class);
                    // send name from edit text to Main2Activity
                    i.putExtra("user_name1", player1_name_str);
                    i.putExtra("user_name2", player2_name_str);
                    startActivity(i);
                    finish();
                    dialog.cancel();
                }
            }
        });
    }
}
