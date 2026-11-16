
package com.smartbank;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

public class CardsActivity extends AppCompatActivity {

    private TextView tvCardStatus;
    private Button btnFreezeCard;
    private boolean isCardFrozen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cards);

        initializeViews();

        setupCardFunctions();

        setupNavigation();
    }

    // CONNECT XML VIEWS

    private void initializeViews() {

        tvCardStatus = findViewById(R.id.tvCardStatus);

        btnFreezeCard = findViewById(R.id.btnFreezeCard);

    }

    // FREEZE / UNFREEZE CARD

    private void setupCardFunctions() {

        btnFreezeCard.setOnClickListener(v -> {

            isCardFrozen = !isCardFrozen;

            if (isCardFrozen) {

                tvCardStatus.setText("● Frozen");

                tvCardStatus.setTextColor(
                        Color.parseColor("#DC2626")
                );

                btnFreezeCard.setText("Unfreeze Card");

                showMessage("Demo card frozen successfully");

            } else {

                tvCardStatus.setText("● Active");

                tvCardStatus.setTextColor(
                        Color.parseColor("#16A34A")
                );

                btnFreezeCard.setText("Freeze Card");

                showMessage("Demo card activated successfully");

            }

        });

        // CARD DETAILS

        findViewById(R.id.btnCardDetails)
                .setOnClickListener(v -> showCardDetails());

    }

    // SHOW CARD DETAILS

    private void showCardDetails() {

        String details =
                "Cardholder: AYAZ HUSSAIN\n\n" +
                        "Card Number: •••• •••• •••• 2048\n\n" +
                        "Expiry Date: 12/29\n\n" +
                        "CVV: Hidden\n\n" +
                        "Card Status: " +
                        (isCardFrozen ? "Frozen" : "Active") +
                        "\n\nDemo card information only.";

        new AlertDialog.Builder(this)
                .setTitle("SmartBank Card Details")
                .setMessage(details)
                .setPositiveButton("OK", null)
                .show();

    }

    // BOTTOM NAVIGATION

    private void setupNavigation() {

        findViewById(R.id.navHome).setOnClickListener(v -> {

            finish();

        });

        findViewById(R.id.navCards).setOnClickListener(v -> {

            showMessage("You are already on Cards");

        });

        findViewById(R.id.navTransfer).setOnClickListener(v -> {

            showMessage("Transfer screen coming next");

        });

        findViewById(R.id.navHistory).setOnClickListener(v -> {

            showMessage("History screen coming next");

        });

        findViewById(R.id.navProfile).setOnClickListener(v -> {

            showMessage("Profile screen coming next");

        });

        findViewById(R.id.navTransfer).setOnClickListener(v -> {

            startActivity(new Intent(
                    CardsActivity.this,
                    TransferActivity.class
            ));

        });

        findViewById(R.id.navHistory).setOnClickListener(v -> {

            startActivity(new Intent(
                    CardsActivity.this,
                    HistoryActivity.class
            ));

            finish();

        });
        findViewById(R.id.navProfile).setOnClickListener ( v ->{
                startActivity(new Intent(
                        CardsActivity.this,
                        ProfileActivity.class
                ));
                finish();
        });

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
