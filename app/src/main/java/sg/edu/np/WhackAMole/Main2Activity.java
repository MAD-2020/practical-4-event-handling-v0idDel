package sg.edu.np.WhackAMole;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main2Activity extends AppCompatActivity {
    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 8.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The functions readTimer() and placeMoleTimer() are to inform the user X seconds before starting and loading new mole.
        - Feel free to modify the function to suit your program.
    */
    private Button userButton;
    private int advancedScore = 0;
    private TextView scoreTrack;
    private static final String TAG = "message";
    private CountDownTimer myCountDown;

    private void readyTimer(){
        /*  HINT:
            The "Get Ready" Timer.
            Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
            Toast message -"Get Ready In X seconds"
            Log.v(TAG, "Ready CountDown Complete!");
            Toast message - "GO!"
            belongs here.
            This timer countdown from 10 seconds to 0 seconds and stops after "GO!" is shown.
         */
        myCountDown = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.v(TAG, "Ready CountDown!" + millisUntilFinished/1000);
                Toast.makeText(getApplicationContext(), "Get Ready in " + millisUntilFinished/1000 + " seconds", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFinish() {
                Log.v(TAG, "Ready CountDown Complete!");
                Toast.makeText(getApplicationContext(), "GO!", Toast.LENGTH_SHORT).show();
                setNewMole();
                placeMoleTimer();
            }
        };
        myCountDown.start();
    }
    private void placeMoleTimer(){
        /* HINT:
           Creates new mole location each second.
           Log.v(TAG, "New Mole Location!");
           setNewMole();
           belongs here.
           This is an infinite countdown timer.
         */
        myCountDown = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {
                Log.v(TAG, "New Mole Location");
            }
            @Override
            public void onFinish() {
                setNewMole();
            }
        };
        myCountDown.start();
    }
    private static final int[] BUTTON_IDS = {
            R.id.Button1, R.id.Button2, R.id.Button3,
            R.id.Button4, R.id.Button5, R.id.Button6,
            R.id.Button7, R.id.Button8, R.id.Button9
        /* HINT:
            Stores the 9 buttons IDs here for those who wishes to use array to create all 9 buttons.
            You may use if you wish to change or remove to suit your codes.*/
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Hint:
            This starts the countdown timers one at a time and prepares the user.
            This also prepares the existing score brought over.
            It also prepares the button listeners to each button.
            You may wish to use the for loop to populate all 9 buttons with listeners.
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Bundle bundle = getIntent().getExtras();
        advancedScore = bundle.getInt("Score");
        scoreTrack =findViewById(R.id.score);
        scoreTrack.setText(String.valueOf(advancedScore));
        Log.v(TAG, "Current User Score: " + scoreTrack);

        readyTimer();
        for(final int id : BUTTON_IDS){
            /*  HINT:
            This creates a for loop to populate all 9 buttons with listeners.
            You may use if you wish to remove or change to suit your codes.
            */
            Button userButton = (Button)findViewById(id);
            userButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = view.getId();
                    Button clickedButton = (Button) findViewById(id);
                    doCheck(clickedButton);
                }
            });
        }
    }
    @Override
    protected void onStart(){
        super.onStart();
    }
    private void doCheck(Button checkButton)
    {
        /* Hint:
            Checks for hit or miss
            Log.v(TAG, "Hit, score added!");
            Log.v(TAG, "Missed, point deducted!");
            belongs here.
        */
        String buttonText = checkButton.getText().toString();
        switch (buttonText){
            case "0":
                Log.v(TAG, "Missed point deducted!");
                advancedScore = advancedScore - 1;
                if (advancedScore < 0){
                    advancedScore = 0;
                    Log.v(TAG, "Come on! Try harder!");
                }
                setNewMole();
                break;
            case "*":
                Log.v(TAG, "Hit, score added!");
                advancedScore = advancedScore + 1;
                setNewMole();
                break;
        }
        String count = String.valueOf(advancedScore);
        scoreTrack.setText(count);
    }

    public void setNewMole()
    {
        /* Hint:
            Clears the previous mole location and gets a new random location of the next mole location.
            Sets the new location of the mole.
         */
        Random ran = new Random();
        int randomLocation = ran.nextInt(9);
        Button newButton = (Button) findViewById(BUTTON_IDS[randomLocation]);
        for (int id : BUTTON_IDS){
            Button previousButton = (Button) findViewById(id);
            if (previousButton.getText().toString() == ""){
                previousButton.setText("0");
            }
            else if (previousButton.getText().toString() == "*"){
                previousButton.setText("0");
            }
        }
        newButton.setText("*");
        placeMoleTimer();
    }
}

