package ro.pub.cs.systems.eim.practicaltest01var06;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01Var06ChooseNumber extends AppCompatActivity {
    Button playButton;
    EditText numberEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var06_choose_number);

        playButton = (Button) findViewById(R.id.playButton);
        playButton.setOnClickListener(playButtonListener);
        numberEditText = (EditText) findViewById(R.id.numberEditText);
    }

    PlayButtonListener playButtonListener = new PlayButtonListener();
    private class PlayButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), PracticalTest01Var06PlayActivity.class);
            String digits = numberEditText.getText().toString();
            if(digits.length() == 1) {
                int digit = Integer.parseInt(digits);
                intent.putExtra(Constants.MAIN_SCORE, digit);
                startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);

            }
        }
    }

   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == Constants.SECONDARY_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(this, "The back button was pressed", Toast.LENGTH_LONG).show();

        }
    }
}
