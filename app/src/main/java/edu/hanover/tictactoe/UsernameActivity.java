package edu.hanover.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;
import android.widget.EditText;

import static edu.hanover.tictactoe.TopLevelActivity.REMOVEP2;

public class UsernameActivity extends AppCompatActivity {

    private TextView tv2;
    private EditText et2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);
        tv2 = (TextView) findViewById(R.id.textView2);
        et2 = (EditText) findViewById(R.id.p2);
        if (REMOVEP2) {
            tv2.setVisibility(View.INVISIBLE);
            et2.setVisibility(View.INVISIBLE);
        }
    }

    public void onStartBtnPress(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        EditText p1et = (EditText) findViewById(R.id.p1);
        String p1Name = p1et.getText().toString();
        String p2Name = "AI";
        if (!REMOVEP2) {
            EditText p2et = (EditText) findViewById(R.id.p2);
            p2Name = p2et.getText().toString();
        }

        intent.putExtra("p1Name", p1Name);
        intent.putExtra("p2Name", p2Name);
        startActivity(intent);
    }
}
