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

import java.util.ArrayList;

public class SinglePlayerActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean turn;
    private boolean clicked;
    private int counter;
    private int player1Score;
    private int player2Score;
    private boolean playAsO;
    private String playerTag;
    private String computerTag;
    private int playerBannerImageView;
    private int playerBannerImage;
    private int playerBannerMoveImage;
    private int playerMoveImage;
    private int computerBannerImageView;
    private int computerBannerImage;
    private int computerBannerMoveImage;
    private int computerMoveImage;
    private int gamesCompleted;
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
        turn = true;
        clicked = false;
        counter = 0;

        Intent intent = getIntent();
        player1Score = intent.getIntExtra("player1Score", 0);
        player2Score = intent.getIntExtra("player2Score", 0);
        playAsO = intent.getBooleanExtra("PLAY_AS_O", false);
        gamesCompleted = intent.getIntExtra("gamesCompleted", 0);
        xFirstMove = intent.getBooleanExtra("xFirstMove", true);

        if (playAsO) {
            playerTag = "o";
            computerTag = "x";
            playerBannerImageView = R.id.oBannerImageView;
            playerBannerImage = R.drawable.bannero;
            playerBannerMoveImage = R.drawable.oturn;
            playerMoveImage = R.drawable.o;
            computerBannerImageView = R.id.xBannerImageView;
            computerBannerImage = R.drawable.bannerx;
            computerBannerMoveImage = R.drawable.xturn;
            computerMoveImage = R.drawable.x;
        } else {
            playerTag = "x";
            computerTag = "o";
            playerBannerImageView = R.id.xBannerImageView;
            playerBannerImage = R.drawable.bannerx;
            playerBannerMoveImage = R.drawable.xturn;
            playerMoveImage = R.drawable.x;
            computerBannerImageView = R.id.oBannerImageView;
            computerBannerImage = R.drawable.bannero;
            computerBannerMoveImage = R.drawable.oturn;
            computerMoveImage = R.drawable.o;
        }

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

        if (playAsO && turn) {
            clicked = true;
            new CountDownTimer(1800, 1) {
                public void onTick(long millisUntilFinished) {
                    if (millisUntilFinished < 1800) {
                        ImageView oImageView = findViewById(computerBannerImageView);
                        oImageView.setImageResource(computerBannerMoveImage);
                    }
                }

                public void onFinish() {
                    computerMove();
                }
            }.start();
        } else if (!playAsO && !turn) {
            clicked = true;
            new CountDownTimer(1800, 1) {
                public void onTick(long millisUntilFinished) {
                    if (millisUntilFinished < 1800) {
                        ImageView oImageView = findViewById(computerBannerImageView);
                        oImageView.setImageResource(computerBannerMoveImage);
                    }
                }

                public void onFinish() {
                    computerMove();
                }
            }.start();
        }
    }

    @Override
    public void onClick(View view) {
        if (clicked) {
            return;
        } else {
            clicked = true;
            counter++;
            ImageView xImageView = findViewById(playerBannerImageView);
            ImageView iv = findViewById(view.getId());
            iv.setImageResource(playerMoveImage);
            iv.setTag(playerTag);
            iv.setEnabled(false);
            if (checkForWinner()) {
                xImageView.setImageResource(playerBannerImage);
                displayWinnerDialog();
                return;
            } else if (counter == 9) {
                xImageView.setImageResource(playerBannerImage);
                displayTieDialog();
                return;
            }
            xImageView.setImageResource(playerBannerImage);
            turn = !turn;
            new CountDownTimer(1300, 1) {
                public void onTick(long millisUntilFinished) {
                    if (millisUntilFinished < 1200) {
                        ImageView oImageView = findViewById(computerBannerImageView);
                        oImageView.setImageResource(computerBannerMoveImage);
                    }
                }

                public void onFinish() {
                    computerMove();
                }
            }.start();
        }
    }

    private void computerMove() {
        ImageView oImageView = findViewById(computerBannerImageView);
        ImageView xImageView = findViewById(playerBannerImageView);
        counter++;
        ArrayList<ImageView> available = getAvailable();
        if (available.size() > 1) {
            for (int i = 0; i < available.size(); i++) {
                available.get(i).setTag(computerTag);
                if (checkForWinner()) {
                    available.get(i).setImageResource(computerMoveImage);
                    available.get(i).setEnabled(false);
                    oImageView.setImageResource(computerBannerImage);
                    displayWinnerDialog();
                    return;
                } else {
                    available.get(i).setTag("empty");
                }
            }
            for (int i = 0; i < available.size(); i++) {
                available.get(i).setTag(playerTag);
                if (checkForWinner()) {
                    available.get(i).setImageResource(computerMoveImage);
                    available.get(i).setTag(computerTag);
                    available.get(i).setEnabled(false);
                    oImageView.setImageResource(computerBannerImage);
                    xImageView.setImageResource(playerBannerMoveImage);
                    turn = !turn;
                    clicked = false;
                    return;
                } else {
                    available.get(i).setTag("empty");
                }
            }
            int coinFlip = (int)(Math.random() * 3);
            if (coinFlip == 1) {
                ArrayList<ImageView> emptyCorners = getEmptyCorners();
                if (emptyCorners.size() >= 1) {
                    int move = (int) (Math.random() * (emptyCorners.size() - 1));
                    emptyCorners.get(move).setImageResource(computerMoveImage);
                    emptyCorners.get(move).setTag(computerTag);
                    emptyCorners.get(move).setEnabled(false);
                    oImageView.setImageResource(computerBannerImage);
                    xImageView.setImageResource(playerBannerMoveImage);
                    turn = !turn;
                    clicked = false;
                    return;
                }
            }
            int move = (int)(Math.random() * (available.size() - 1));
            available.get(move).setImageResource(computerMoveImage);
            available.get(move).setTag(computerTag);
            available.get(move).setEnabled(false);
            oImageView.setImageResource(computerBannerImage);
            xImageView.setImageResource(playerBannerMoveImage);
            clicked = false;
            turn = !turn;
        } else {
            available.get(0).setImageResource(computerMoveImage);
            available.get(0).setTag(computerTag);
            available.get(0).setEnabled(false);
            if (checkForWinner()) {
                oImageView.setImageResource(computerBannerImage);
                displayWinnerDialog();
                return;
            } else if (counter == 9) {
                oImageView.setImageResource(computerBannerImage);
                displayTieDialog();
                return;
            }
            turn = !turn;
            clicked = false;
        }
    }

    private void displayTieDialog() {
        gamesCompleted++;
        xFirstMove = !xFirstMove;
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
                Intent intent = new Intent(getApplicationContext(), SinglePlayerActivity.class);
                intent.putExtra("player1Score", player1Score);
                intent.putExtra("player2Score", player2Score);
                intent.putExtra("PLAY_AS_O", playAsO);
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
    }

    private void displayWinnerDialog() {
        gamesCompleted++;
        xFirstMove = !xFirstMove;
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
                Intent intent = new Intent(getApplicationContext(), SinglePlayerActivity.class);
                intent.putExtra("player1Score", player1Score);
                intent.putExtra("player2Score", player2Score);
                intent.putExtra("PLAY_AS_O", playAsO);
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

    private ArrayList<ImageView> getAvailable() {
        ArrayList<ImageView> available = new ArrayList<>();

        ImageView iv = findViewById(R.id.iv00);
        if (iv.getTag().toString().equals("empty")) {
            available.add(iv);
        }
        iv = findViewById(R.id.iv01);
        if (iv.getTag().toString().equals("empty")) {
            available.add(iv);
        }
        iv = findViewById(R.id.iv02);
        if (iv.getTag().toString().equals("empty")) {
            available.add(iv);
        }
        iv = findViewById(R.id.iv10);
        if (iv.getTag().toString().equals("empty")) {
            available.add(iv);
        }
        iv = findViewById(R.id.iv11);
        if (iv.getTag().toString().equals("empty")) {
            available.add(iv);
        }
        iv = findViewById(R.id.iv12);
        if (iv.getTag().toString().equals("empty")) {
            available.add(iv);
        }
        iv = findViewById(R.id.iv20);
        if (iv.getTag().toString().equals("empty")) {
            available.add(iv);
        }
        iv = findViewById(R.id.iv21);
        if (iv.getTag().toString().equals("empty")) {
            available.add(iv);
        }
        iv = findViewById(R.id.iv22);
        if (iv.getTag().toString().equals("empty")) {
            available.add(iv);
        }

        return available;
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
                    SinglePlayerActivity.super.onBackPressed();
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

    private ArrayList<ImageView> getEmptyCorners() {
        ArrayList<ImageView> emptyCorners = new ArrayList<>();

        ImageView iv = findViewById(R.id.iv00);
        if (iv.getTag().toString().equals("empty")) {
            emptyCorners.add(iv);
        }
        iv = findViewById(R.id.iv02);
        if (iv.getTag().toString().equals("empty")) {
            emptyCorners.add(iv);
        }
        iv = findViewById(R.id.iv20);
        if (iv.getTag().toString().equals("empty")) {
            emptyCorners.add(iv);
        }
        iv = findViewById(R.id.iv22);
        if (iv.getTag().toString().equals("empty")) {
            emptyCorners.add(iv);
        }
        iv = findViewById(R.id.iv11);
        if (iv.getTag().toString().equals("empty")) {
            emptyCorners.add(iv);
        }

        return emptyCorners;
    }
}
