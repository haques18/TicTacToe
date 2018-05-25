package edu.hanover.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class TopLevelActivity extends AppCompatActivity {

    public static boolean REMOVEP2 = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);
    }

    public void on2pBtnPress(View view) {
        Intent intent = new Intent(this, UsernameActivity.class);
        REMOVEP2 = false;
        startActivity(intent);
    }

    public void onAIBtnPress(View view) {
        Intent intent = new Intent(this, UsernameActivity.class);
        REMOVEP2 = true;
        startActivity(intent);
    }
}
