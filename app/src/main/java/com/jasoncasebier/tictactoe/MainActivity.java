package com.jasoncasebier.tictactoe;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean clicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clicked = false;

        ImageView b = findViewById(R.id.twoPlayerButtonImageView);
        b.setOnClickListener(this);
        b = findViewById(R.id.onePlayerButtonImageView);
        b.setOnClickListener(this);
        b = findViewById(R.id.shareButtonImageView);
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (clicked) {
            return;
        } else {
            clicked = true;
            if (view.getId() == R.id.twoPlayerButtonImageView) {
                Intent intent = new Intent(getApplicationContext(), TwoPlayerActivity.class);
                startActivity(intent);
                clicked = false;
            } else if (view.getId() == R.id.onePlayerButtonImageView) {
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.dialog_choose, null);
                AlertDialog.Builder chooseAlert = new AlertDialog.Builder(this);
                chooseAlert.setView(alertLayout);
                chooseAlert.setCancelable(true);
                final AlertDialog chooseDialog = chooseAlert.create();
                chooseDialog.show();

                final ImageView xImageView = alertLayout.findViewById(R.id.xImageView);
                xImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SinglePlayerActivity.class);
                        intent.putExtra("PLAY_AS_O", false);
                        startActivity(intent);
                        chooseDialog.dismiss();
                    }
                });
                final ImageView oImageView = alertLayout.findViewById(R.id.oImageView);
                oImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SinglePlayerActivity.class);
                        intent.putExtra("PLAY_AS_O", true);
                        startActivity(intent);
                        chooseDialog.dismiss();
                    }
                });
                clicked = false;
            } else if (view.getId() == R.id.shareButtonImageView) {
                final String appPackageName = getApplicationContext().getPackageName();
                String appLinkStr = getString(R.string.share_text) +
                        "https://play.google.com/store/apps/details?id=" + appPackageName;

                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.share_subject));
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, appLinkStr);

                startActivity(Intent.createChooser(shareIntent, "Share with"));
                clicked = false;
            }
        }
    }

    @Override
    public void onBackPressed() {
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
                MainActivity.super.onBackPressed();
            }
        });
        TextView cancelTextView = alertLayout.findViewById(R.id.cancelTextView);
        cancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quitDialog.dismiss();
            }
        });
    }
}
