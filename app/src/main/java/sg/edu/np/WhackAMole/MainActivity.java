package sg.edu.np.WhackAMole;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 2.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The function doCheck() also decides if the user qualifies for the advance level and triggers for a dialog box to ask for user to decide.
        - The function nextLevelQuery() builds the dialog box and shows. It also triggers the nextLevel() if user selects Yes or return to normal state if user select No.
        - The function nextLevel() launches the new advanced page.
        - Feel free to modify the function to suit your program.
    */
    private static final String TAG = "message";
    private int Score = 0;
    private TextView scoreTrack;
    private Button Button1;
    private Button Button2;
    private Button Button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Capture buttons from layout
        Button1 = (Button) findViewById(R.id.Button1);
        Button2 = (Button) findViewById(R.id.Button2);
        Button3 = (Button) findViewById(R.id.Button3);
        scoreTrack = (TextView) findViewById(R.id.score);

        Log.v(TAG, "Whack-A-Mole!");
        Log.v(TAG, "Finished Pre-Initialisation!");
    }
    @Override
    protected void onStart(){
        super.onStart();
        setNewMole();
        Log.v(TAG, "Starting GUI!");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Paused Whack-A-Mole!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Whack-A-Mole!");
        finish();
    }

    public void onClickButton(View v){
        /* Checks for hit or miss and if user qualify for advanced page.
            Triggers nextLevelQuery().*/
        Button userButton = (Button) v;
        switch(v.getId()){
            case R.id.Button1:
                Log.v(TAG, "Button Left Clicked!");
                break;
            case R.id.Button2:
                Log.v(TAG, "Button Middle Clicked!");
                break;
            case R.id.Button3:
                Log.v(TAG, "Button Right Clicked!");
                break;
        }
        String buttonText = userButton.getText().toString();
        switch (buttonText){
            case "O":
                Log.v(TAG, "Missed point deducted!");
                Score = Score - 1;
                if (Score < 0){
                    Score = 0;
                    Log.v(TAG, "Come on! Try harder!");
                }
                setNewMole();
                break;
            case "*":
                Log.v(TAG, "Hit, score added!");
                Score = Score + 1;
                setNewMole();
                break;
        }
        String count = String.valueOf(Score);
        scoreTrack.setText(count);

        if (Score > 0 && Score % 10 == 0)
        {
            nextLevelQuery();
        }
    }

    private void nextLevelQuery(){
        /*
        Builds dialog box here.
        Log.v(TAG, "User accepts!");
        Log.v(TAG, "User decline!");
        Log.v(TAG, "Advance option given to user!");
        belongs here*/
        Log.v(TAG, "Advance option given to user!");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning! Insane Whack-A-Mole incoming!");
        builder.setMessage("Would you like to advance to advanced mode?").setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v(TAG, "User accepts!");
                nextLevel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v(TAG, "User decline!");
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void nextLevel(){
        /* Launch advanced page */
        Intent nextPage = new Intent(MainActivity.this, Main2Activity.class);
        Bundle extras = new Bundle();
        extras.putInt("Score", Score);
        nextPage.putExtras(extras);
        startActivity(nextPage);
    }

    private void setNewMole() {
        Button[] buttonArray = {Button1, Button2, Button3};
        Random ran = new Random();
        int randomLocation = ran.nextInt(3);
        //Android studio suggested me to change from for loop to foreach loop
        for (Button button : buttonArray) {
            Button moleButton = buttonArray[randomLocation];
            if (button == moleButton) {
                button.setText("*");
            } else {
                button.setText("O");
            }
        }
    }
}