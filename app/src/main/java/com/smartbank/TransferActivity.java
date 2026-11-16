
package com.smartbank;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class TransferActivity extends AppCompatActivity {

    private EditText etReceiverName;
    private EditText etAccountNumber;
    private EditText etTransferAmount;
    private EditText etTransferNote;

    private Button btnTransferNow;

    private final double DEMO_BALANCE = 245850.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_transfer);

        initializeViews();

        setupTransfer();

        setupNavigation();
    }

    private void initializeViews() {

        etReceiverName = findViewById(R.id.etReceiverName);

        etAccountNumber = findViewById(R.id.etAccountNumber);

        etTransferAmount = findViewById(R.id.etTransferAmount);

        etTransferNote = findViewById(R.id.etTransferNote);

        btnTransferNow = findViewById(R.id.btnTransferNow);

    }

    // TRANSFER VALIDATION

    private void setupTransfer() {

        btnTransferNow.setOnClickListener(v -> {

            String receiver = etReceiverName.getText()
                    .toString().trim();

            String account = etAccountNumber.getText()
                    .toString().trim();

            String amountText = etTransferAmount.getText()
                    .toString().trim();

            String note = etTransferNote.getText()
                    .toString().trim();

            if (receiver.isEmpty()) {

                etReceiverName.setError(
                        "Enter receiver name"
                );

                etReceiverName.requestFocus();
                return;
            }

            if (account.length() < 8) {

                etAccountNumber.setError(
                        "Enter at least 8 digits"
                );

                etAccountNumber.requestFocus();
                return;
            }

            if (amountText.isEmpty()) {

                etTransferAmount.setError(
                        "Enter transfer amount"
                );

                etTransferAmount.requestFocus();
                return;
            }

            double amount;

            try {

                amount = Double.parseDouble(amountText);

            } catch (NumberFormatException e) {

                etTransferAmount.setError(
                        "Enter a valid amount"
                );

                return;
            }

            if (Double.isNaN(amount) ||
                    Double.isInfinite(amount) ||
                    amount <= 0) {

                etTransferAmount.setError(
                        "Amount must be greater than zero"
                );

                return;
            }

            if (amount > DEMO_BALANCE) {

                etTransferAmount.setError(
                        "Insufficient demo balance"
                );

                return;
            }

            showTransferConfirmation(
                    receiver,
                    account,
                    amount,
                    note
            );

        });

    }

    // CONFIRMATION DIALOG

    private void showTransferConfirmation(
            String receiver,
            String account,
            double amount,
            String note) {

        String message =
                "Receiver: " + receiver +
                        "\nAccount: " + account +
                        "\nAmount: PKR " +
                        String.format(java.util.Locale.US,
                                "%,.2f", amount) +
                        "\nNote: " +
                        (note.isEmpty() ? "No note" : note) +
                        "\n\nThis is a simulated demo transfer. No real money will move.";

        new AlertDialog.Builder(this)
                .setTitle("Confirm Demo Transfer")
                .setMessage(message)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Confirm", (dialog, which) -> {

                    Toast.makeText(
                            this,
                            "Demo transfer simulated successfully!",
                            Toast.LENGTH_LONG
                    ).show();

                    clearForm();

                })
                .show();

    }

    private void clearForm() {

        etReceiverName.setText("");

        etAccountNumber.setText("");

        etTransferAmount.setText("");

        etTransferNote.setText("");

    }

    // BOTTOM NAVIGATION

    private void setupNavigation() {

        findViewById(R.id.navHome).setOnClickListener(v -> {

            startActivity(new Intent(
                    TransferActivity.this,
                    DashboardActivity.class
            ));

            finish();

        });

        findViewById(R.id.navCards).setOnClickListener(v -> {

            startActivity(new Intent(
                    TransferActivity.this,
                    CardsActivity.class
            ));

            finish();

        });

        findViewById(R.id.navTransfer).setOnClickListener(v -> {

            Toast.makeText(
                    this,
                    "You are already on Transfer",
                    Toast.LENGTH_SHORT
            ).show();

        });

        findViewById(R.id.navHistory).setOnClickListener(v -> {

            Toast.makeText(
                    this,
                    "History screen coming next",
                    Toast.LENGTH_SHORT
            ).show();

        });

        findViewById(R.id.navProfile).setOnClickListener(v -> {

            Toast.makeText(
                    this,
                    "Profile screen coming next",
                    Toast.LENGTH_SHORT
            ).show();

        });

        findViewById(R.id.navHistory).setOnClickListener(v -> {

            startActivity(new Intent(
                    TransferActivity.this,
                    HistoryActivity.class
            ));

            finish();

        });

    }

}
