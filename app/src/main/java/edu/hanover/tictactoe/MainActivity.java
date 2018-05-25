package edu.hanover.tictactoe;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import static edu.hanover.tictactoe.TopLevelActivity.REMOVEP2;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private boolean player1Turn = true;

    private int roundCount;

    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    private String p1Name;
    private String p2Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);
        p1Name = getIntent().getExtras().getString("p1Name");
        p2Name = getIntent().getExtras().getString("p2Name");
        textViewPlayer1.setText(p1Name + ": 0");
        textViewPlayer2.setText(p2Name + ": 0");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setBackgroundResource(android.R.drawable.btn_default);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
        Button buttonResetBoard = findViewById(R.id.button_reset_board);
        buttonResetBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBoard();
            }
        });
    }

    @Override
    public void onClick(View v) {
//        int index;
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        if (!REMOVEP2) {
            if (player1Turn) {
                ((Button) v).setText("X");
            }
            else {
                ((Button) v).setText("O");
            }
            roundCount++;

            if (checkForWin()) {
                if (player1Turn) {
                    player1Wins();
                    return;
                } else {
                    player2Wins();
                    return;
                }
            } else if (roundCount == 9) {
                draw();
                return;
            } else {
                player1Turn = !player1Turn;
            }
        } else {
            ((Button) v).setText("X");
            roundCount++;
            if (checkForWin()) {
                player1Wins();
                return;
            } else if (roundCount == 9) {
                draw();
                return;
            }
            int resID = chooseButton();
            Button va = findViewById(resID);
            while (!((Button) va).getText().toString().equals("")) {

                resID = chooseButton();
                va = findViewById(resID);
            }
            ((Button) va).setText("O");
            roundCount++;

            if (checkForWin()) {
                player2Wins();
                return;
            } else if (roundCount == 9) {
                draw();
                return;
            }
        }

    }

    public int chooseButton() {
        int[] arr = new int[] {0,1,2,3,4,5,6,7,8};
        Random r = new Random();
        int index = r.nextInt(arr.length);
        int arrVal = arr[index];
        int rowNum = arrVal/3;
        int colNum = arrVal%3;
        String buttonID = "button_" + rowNum + colNum;
        int resID = getResources().getIdentifier(buttonID, "id", getPackageName());

        return resID;
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                buttons[i][0].setBackgroundColor(Color.GREEN);
                buttons[i][1].setBackgroundColor(Color.GREEN);
                buttons[i][2].setBackgroundColor(Color.GREEN);
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                buttons[0][i].setBackgroundColor(Color.GREEN);
                buttons[1][i].setBackgroundColor(Color.GREEN);
                buttons[2][i].setBackgroundColor(Color.GREEN);
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            buttons[0][0].setBackgroundColor(Color.GREEN);
            buttons[1][1].setBackgroundColor(Color.GREEN);
            buttons[2][2].setBackgroundColor(Color.GREEN);
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            buttons[0][2].setBackgroundColor(Color.GREEN);
            buttons[1][1].setBackgroundColor(Color.GREEN);
            buttons[2][0].setBackgroundColor(Color.GREEN);
            return true;
        }

        return false;
    }

    private void player1Wins() {
        player1Points++;
        Toast.makeText(this, p1Name + " wins! Board will automatically update in 1 second", Toast.LENGTH_SHORT).show();
        updatePointsText();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                resetBoard();
            }
        }, 1000);
    }

    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, p2Name + " wins! Board will automatically update in 1 second", Toast.LENGTH_SHORT).show();
        updatePointsText();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                resetBoard();
            }
        }, 1000);
    }

    private void draw() {
        Toast.makeText(this, "Draw! Board will automatically update in 1 second", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                resetBoard();
            }
        }, 1000);
    }

    private void updatePointsText() {
        textViewPlayer1.setText(p1Name + ": " + player1Points);
        textViewPlayer2.setText(p2Name + ": " + player2Points);
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setBackgroundResource(android.R.drawable.btn_default);
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        player1Turn = true;
    }

    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}
