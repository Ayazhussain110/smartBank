
package com.smartbank;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ReceiveActivity extends AppCompatActivity {

    private TextView tvReceiveName;
    private TextView tvReceiveAccount;
    private TextView tvReceiveIban;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_receive);

        initializeViews();

        setupCopyButtons();

        setupShareButton();

        setupBackButton();

    }


    // INITIALIZE VIEWS

    private void initializeViews() {

        tvReceiveName = findViewById(R.id.tvReceiveName);

        tvReceiveAccount = findViewById(R.id.tvReceiveAccount);

        tvReceiveIban = findViewById(R.id.tvReceiveIban);

    }


    // COPY ACCOUNT DETAILS

    private void setupCopyButtons() {

        findViewById(R.id.btnCopyAccount)
                .setOnClickListener(v -> {

                    copyText(
                            "Account Number",
                            tvReceiveAccount.getText().toString()
                    );

                });


        findViewById(R.id.btnCopyIban)
                .setOnClickListener(v -> {

                    copyText(
                            "IBAN",
                            tvReceiveIban.getText().toString()
                    );

                });

    }


    // COPY TO CLIPBOARD

    private void copyText(String label, String value) {

        ClipboardManager clipboard =
                (ClipboardManager) getSystemService(
                        Context.CLIPBOARD_SERVICE
                );

        if (clipboard != null) {

            ClipData clip = ClipData.newPlainText(
                    label,
                    value
            );

            clipboard.setPrimaryClip(clip);

            showMessage(label + " copied successfully");

        }

    }


    // SHARE ACCOUNT DETAILS

    private void setupShareButton() {

        findViewById(R.id.btnShareDetails)
                .setOnClickListener(v -> {

                    String details =
                            "SmartBank Demo Account Details\n\n" +
                                    "Account Holder: " +
                                    tvReceiveName.getText().toString() +
                                    "\nAccount Number: " +
                                    tvReceiveAccount.getText().toString() +
                                    "\nIBAN: " +
                                    tvReceiveIban.getText().toString() +
                                    "\n\nDemo information only. " +
                                    "Not connected to a real bank.";

                    Intent shareIntent = new Intent(
                            Intent.ACTION_SEND
                    );

                    shareIntent.setType("text/plain");

                    shareIntent.putExtra(
                            Intent.EXTRA_TEXT,
                            details
                    );

                    startActivity(
                            Intent.createChooser(
                                    shareIntent,
                                    "Share Account Details"
                            )
                    );

                });

    }


    // BACK BUTTON

    private void setupBackButton() {

        findViewById(R.id.btnBack)
                .setOnClickListener(v -> finish());

    }


    // COMMON TOAST

    private void showMessage(String message) {

        Toast.makeText(
                this,
                message,
                Toast.LENGTH_SHORT
        ).show();

    }

}
