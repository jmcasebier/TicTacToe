package com.jasoncasebier.tictactoe;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class TwoPlayerActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean turn;
    private int counter;
    private int player1Score;
    private int player2Score;
    private int gamesCompleted;
    private boolean clicked;
    private AdView adView;
    private boolean xFirstMove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        MobileAds.initialize(this, "ca-app-pub-9235774958736861~3178208823");
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        final ImageView xImageView = findViewById(R.id.xBannerImageView);
        final ImageView oImageView = findViewById(R.id.oBannerImageView);
        counter = 0;
        clicked = false;

        Intent intent = getIntent();
        player1Score = intent.getIntExtra("player1Score", 0);
        player2Score = intent.getIntExtra("player2Score", 0);
        gamesCompleted = intent.getIntExtra("gamesCompleted", 0);
        xFirstMove = intent.getBooleanExtra("xFirstMove", true);

        TextView player1ScoreTextView = findViewById(R.id.player1ScoreTextView);
        player1ScoreTextView.setText(String.valueOf(player1Score));
        if (player1Score > 9) {
            player1ScoreTextView.setTextScaleX(0.5f);
        }
        TextView player2ScoreTextView = findViewById(R.id.player2ScoreTextView);
        player2ScoreTextView.setText(String.valueOf(player2Score));
        if (player2Score > 9) {
            player2ScoreTextView.setTextScaleX(0.5f);
        }

        ImageView iv = findViewById(R.id.iv00);
        iv.setOnClickListener(this);
        iv = findViewById(R.id.iv01);
        iv.setOnClickListener(this);
        iv = findViewById(R.id.iv02);
        iv.setOnClickListener(this);
        iv = findViewById(R.id.iv10);
        iv.setOnClickListener(this);
        iv = findViewById(R.id.iv11);
        iv.setOnClickListener(this);
        iv = findViewById(R.id.iv12);
        iv.setOnClickListener(this);
        iv = findViewById(R.id.iv20);
        iv.setOnClickListener(this);
        iv = findViewById(R.id.iv21);
        iv.setOnClickListener(this);
        iv = findViewById(R.id.iv22);
        iv.setOnClickListener(this);

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_rules, null);
        AlertDialog.Builder rulesAlert = new AlertDialog.Builder(this);
        rulesAlert.setView(alertLayout);
        final AlertDialog rulesDialog = rulesAlert.create();
        rulesDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        rulesDialog.setCancelable(false);
        rulesDialog.setCanceledOnTouchOutside(false);
        ImageView rulesImageView = alertLayout.findViewById(R.id.rulesImageView);

        if (xFirstMove) {
            rulesImageView.setImageResource(R.drawable.xfirst);
            turn = true;
        } else {
            rulesImageView.setImageResource(R.drawable.ofirst);
            turn = false;
        }

        new CountDownTimer(1400, 1) {
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished < 1400) {
                    clicked = true;
                    rulesDialog.show();
                }
            }

            public void onFinish() {
                rulesDialog.dismiss();
                clicked = false;
                if (xFirstMove) {
                    xImageView.setImageResource(R.drawable.xturn);
                } else {
                    oImageView.setImageResource(R.drawable.oturn);
                }
            }
        }.start();
    }

    @Override
    public void onClick(View view) {
        if (clicked) {
            return;
        } else {
            clicked = true;
            counter++;
            ImageView xImageView = findViewById(R.id.xBannerImageView);
            ImageView oImageView = findViewById(R.id.oBannerImageView);
            ImageView iv = findViewById(view.getId());
            if (turn) {
                iv.setImageResource(R.drawable.x);
                iv.setTag("x");
            } else {
                iv.setImageResource(R.drawable.o);
                iv.setTag("o");
            }
            iv.setEnabled(false);
            if (checkForWinner()) {
                gamesCompleted++;
                xFirstMove = !xFirstMove;
                oImageView.setImageResource(R.drawable.bannero);
                xImageView.setImageResource(R.drawable.bannerx);
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.dialog_win, null);
                AlertDialog.Builder winnerAlert = new AlertDialog.Builder(this);
                winnerAlert.setView(alertLayout);
                AlertDialog winnerDialog = winnerAlert.create();
                winnerDialog.setCanceledOnTouchOutside(false);
                winnerDialog.setCancelable(false);
                winnerDialog.show();
                ImageView winnerImageView = alertLayout.findViewById(R.id.winnerImageView);
                if (turn) {
                    winnerImageView.setImageResource(R.drawable.xfirst);
                    player1Score++;
                    TextView player1ScoreTextView = findViewById(R.id.player1ScoreTextView);
                    player1ScoreTextView.setText(String.valueOf(player1Score));
                    if (player1Score > 9) {
                        player1ScoreTextView.setTextScaleX(0.5f);
                    }
                    TextView player2ScoreTextView = findViewById(R.id.player2ScoreTextView);
                    player2ScoreTextView.setText(String.valueOf(player2Score));
                    if (player2Score > 9) {
                        player2ScoreTextView.setTextScaleX(0.5f);
                    }
                } else {
                    winnerImageView.setImageResource(R.drawable.ofirst);
                    player2Score++;
                    TextView player1ScoreTextView = findViewById(R.id.player1ScoreTextView);
                    player1ScoreTextView.setText(String.valueOf(player1Score));
                    if (player1Score > 9) {
                        player1ScoreTextView.setTextScaleX(0.5f);
                    }
                    TextView player2ScoreTextView = findViewById(R.id.player2ScoreTextView);
                    player2ScoreTextView.setText(String.valueOf(player2Score));
                    if (player2Score > 9) {
                        player2ScoreTextView.setTextScaleX(0.5f);
                    }
                }
                TextView playAgainTextView = alertLayout.findViewById(R.id.cancelTextView);
                playAgainTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                        Intent intent = new Intent(getApplicationContext(), TwoPlayerActivity.class);
                        intent.putExtra("player1Score", player1Score);
                        intent.putExtra("player2Score", player2Score);
                        intent.putExtra("gamesCompleted", gamesCompleted);
                        intent.putExtra("xFirstMove", xFirstMove);
                        startActivity(intent);
                    }
                });
                TextView quitTextView = alertLayout.findViewById(R.id.quitTextView);
                quitTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
            } else if (counter == 9) {
                gamesCompleted++;
                xFirstMove = !xFirstMove;
                oImageView.setImageResource(R.drawable.bannero);
                xImageView.setImageResource(R.drawable.bannerx);
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.dialog_tie, null);
                AlertDialog.Builder tieAlert = new AlertDialog.Builder(this);
                tieAlert.setView(alertLayout);
                AlertDialog tieDialog = tieAlert.create();
                tieDialog.setCanceledOnTouchOutside(false);
                tieDialog.setCancelable(false);
                tieDialog.show();
                TextView playAgainTextView = alertLayout.findViewById(R.id.cancelTextView);
                playAgainTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                        Intent intent = new Intent(getApplicationContext(), TwoPlayerActivity.class);
                        intent.putExtra("player1Score", player1Score);
                        intent.putExtra("player2Score", player2Score);
                        intent.putExtra("gamesCompleted", gamesCompleted);
                        intent.putExtra("xFirstMove", xFirstMove);
                        startActivity(intent);
                    }
                });
                TextView quitTextView = alertLayout.findViewById(R.id.quitTextView);
                quitTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
            } else {
                if (turn) {
                    xImageView.setImageResource(R.drawable.bannerx);
                    oImageView.setImageResource(R.drawable.oturn);
                } else {
                    oImageView.setImageResource(R.drawable.bannero);
                    xImageView.setImageResource(R.drawable.xturn);
                }
            }
            turn = !turn;
            clicked = false;
        }
    }

    private boolean checkForWinner() {
        boolean winner = false;

        if (checkVertical() || checkHorizontal() || checkDiagonal()) {
            winner = true;
        }

        return winner;
    }

    private boolean checkVertical() {
        boolean winner = false;
        String[][] gameBoard = getGameBoard();

        if (gameBoard[0][0].equals("x") && gameBoard[1][0].equals("x") && gameBoard[2][0].equals("x") ||
                gameBoard[0][0].equals("o") && gameBoard[1][0].equals("o") && gameBoard[2][0].equals("o")) {
            winner = true;
        } else if (gameBoard[0][1].equals("x") && gameBoard[1][1].equals("x") && gameBoard[2][1].equals("x") ||
                gameBoard[0][1].equals("o") && gameBoard[1][1].equals("o") && gameBoard[2][1].equals("o")) {
            winner = true;
        } else if (gameBoard[0][2].equals("x") && gameBoard[1][2].equals("x") && gameBoard[2][2].equals("x") ||
                gameBoard[0][2].equals("o") && gameBoard[1][2].equals("o") && gameBoard[2][2].equals("o")) {
            winner = true;
        }

        return winner;
    }

    private boolean checkHorizontal() {
        boolean winner = false;
        String[][] gameBoard = getGameBoard();

        if (gameBoard[0][0].equals("x") && gameBoard[0][1].equals("x") && gameBoard[0][2].equals("x") ||
                gameBoard[0][0].equals("o") && gameBoard[0][1].equals("o") && gameBoard[0][2].equals("o")) {
            winner = true;
        } else if (gameBoard[1][0].equals("x") && gameBoard[1][1].equals("x") && gameBoard[1][2].equals("x") ||
                gameBoard[1][0].equals("o") && gameBoard[1][1].equals("o") && gameBoard[1][2].equals("o")) {
            winner = true;
        } else if (gameBoard[2][0].equals("x") && gameBoard[2][1].equals("x") && gameBoard[2][2].equals("x") ||
                gameBoard[2][0].equals("o") && gameBoard[2][1].equals("o") && gameBoard[2][2].equals("o")) {
            winner = true;
        }

        return winner;
    }

    private boolean checkDiagonal() {
        boolean winner = false;
        String[][] gameBoard = getGameBoard();

        if (gameBoard[0][0].equals("x") && gameBoard[1][1].equals("x") && gameBoard[2][2].equals("x") ||
                gameBoard[0][0].equals("o") && gameBoard[1][1].equals("o") && gameBoard[2][2].equals("o")) {
            winner = true;
        } else if (gameBoard[2][0].equals("x") && gameBoard[1][1].equals("x") && gameBoard[0][2].equals("x") ||
                gameBoard[2][0].equals("o") && gameBoard[1][1].equals("o") && gameBoard[0][2].equals("o")) {
            winner = true;
        }

        return winner;
    }

    private String[][] getGameBoard() {
        String[][] gameBoard = new String[3][3];

        ImageView iv = findViewById(R.id.iv00);
        gameBoard[0][0] = iv.getTag().toString();
        iv = findViewById(R.id.iv01);
        gameBoard[0][1] = iv.getTag().toString();
        iv = findViewById(R.id.iv02);
        gameBoard[0][2] = iv.getTag().toString();
        iv = findViewById(R.id.iv10);
        gameBoard[1][0] = iv.getTag().toString();
        iv = findViewById(R.id.iv11);
        gameBoard[1][1] = iv.getTag().toString();
        iv = findViewById(R.id.iv12);
        gameBoard[1][2] = iv.getTag().toString();
        iv = findViewById(R.id.iv20);
        gameBoard[2][0] = iv.getTag().toString();
        iv = findViewById(R.id.iv21);
        gameBoard[2][1] = iv.getTag().toString();
        iv = findViewById(R.id.iv22);
        gameBoard[2][2] = iv.getTag().toString();

        return gameBoard;
    }

    @Override
    public void onBackPressed() {
        if (counter >= 1 || gamesCompleted >= 1) {
            LayoutInflater inflater = getLayoutInflater();
            View alertLayout = inflater.inflate(R.layout.dialog_quit, null);
            AlertDialog.Builder quitAlert = new AlertDialog.Builder(this);
            quitAlert.setView(alertLayout);
            quitAlert.setCancelable(true);
            final AlertDialog quitDialog = quitAlert.create();
            quitDialog.show();
            TextView quitTextView = alertLayout.findViewById(R.id.quitTextView);
            quitTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TwoPlayerActivity.super.onBackPressed();
                }
            });
            TextView cancelTextView = alertLayout.findViewById(R.id.cancelTextView);
            cancelTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    quitDialog.dismiss();
                }
            });
        } else {
            super.onBackPressed();
        }
    }
}
