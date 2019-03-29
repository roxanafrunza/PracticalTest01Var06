package ro.pub.cs.systems.eim.practicaltest01var06;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class PracticalTest01Var06PlayActivity extends AppCompatActivity {
    Button generateButton;
    Button checkButton;
    Button backButton;
    EditText guessEditText;
    EditText scoreEditText;

    Random random = new Random();
    int previousScore;
    int score = 0;
    int serviceStatus = Constants.SERVICE_STOPPED;
    IntentFilter intentFilter = new IntentFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var06_play);

        generateButton = (Button) findViewById(R.id.generateButton);
        generateButton.setOnClickListener(guessButtonListener);
        checkButton = (Button) findViewById(R.id.checkButton);
        checkButton.setOnClickListener(checkButtonListener);
        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(backButtonListener);

        guessEditText = (EditText) findViewById(R.id.guessEditText);
        scoreEditText = (EditText) findViewById(R.id.scoreEditText);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey(Constants.MAIN_SCORE)) {
            previousScore = intent.getIntExtra(Constants.MAIN_SCORE, -1);
            Toast.makeText(this, String.valueOf(previousScore), Toast.LENGTH_LONG).show();
        }

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(Constants.GUESS_COUNT)) {
                guessEditText.setText(savedInstanceState.getString(Constants.GUESS_COUNT));
            } else {
                guessEditText.setText("");
            }
            if (savedInstanceState.containsKey(Constants.SCORE_COUNT)) {
                scoreEditText.setText(savedInstanceState.getString(Constants.SCORE_COUNT));
            } else {
                scoreEditText.setText(String.valueOf(0));
            }
        } else {
            scoreEditText.setText(String.valueOf(0));
            guessEditText.setText("");
        }

       Intent intent2 = new Intent(getApplicationContext(), PracticalTest01Var06Service.class);
        getApplicationContext().startService(intent2);
        serviceStatus = Constants.SERVICE_STARTED;
        intentFilter.addAction(Constants.actionType);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(Constants.GUESS_COUNT, guessEditText.getText().toString());
        savedInstanceState.putString(Constants.SCORE_COUNT, scoreEditText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(Constants.GUESS_COUNT)) {
            guessEditText.setText(savedInstanceState.getString(Constants.GUESS_COUNT));
        } else {
            guessEditText.setText("");
        }
        if (savedInstanceState.containsKey(Constants.SCORE_COUNT)) {
            scoreEditText.setText(savedInstanceState.getString(Constants.SCORE_COUNT));
        } else {
            scoreEditText.setText(String.valueOf(0));
        }
    }

    GuessButtonClickListener guessButtonListener = new GuessButtonClickListener();
    private class GuessButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            int number = random.nextInt(9);
            guessEditText.setText(String.valueOf(number));
        }
    }

    BackButtonClickListener backButtonListener = new BackButtonClickListener();
    private class BackButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            setResult(RESULT_OK, null);
            finish();
        }
    }

    CheckButtonClickListener checkButtonListener = new CheckButtonClickListener();
    private class CheckButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int guess = Integer.parseInt(guessEditText.getText().toString());
            if(guess == previousScore) {
                score++;
                scoreEditText.setText(String.valueOf(score));
            }
        }
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var06Service.class);
        stopService(intent);
        super.onDestroy();
    }

   private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int number = intent.getIntExtra(Constants.NUMBER_STRING, -1);
            Log.d(Constants.LOG_TAG, String.valueOf(number));
            guessEditText.setText(String.valueOf(number));

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }
}
