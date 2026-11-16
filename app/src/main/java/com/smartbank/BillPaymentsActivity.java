
package com.smartbank;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class BillPaymentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bill_payments);

        // Initialize back button
        setupBackButton();

        // Initialize bill categories
        setupBillCategories();
    }


    // BACK BUTTON

    private void setupBackButton() {

        TextView btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> {

            Toast.makeText(
                    BillPaymentsActivity.this,
                    "Returning to Dashboard",
                    Toast.LENGTH_SHORT
            ).show();

            // Return to previous screen
            finish();

        });

    }


    // BILL CATEGORIES

    private void setupBillCategories() {

        setBillAction(R.id.billElectricity, "Electricity");

        setBillAction(R.id.billWater, "Water");

        setBillAction(R.id.billInternet, "Internet");

        setBillAction(R.id.billMobile, "Mobile Top-up");

        setBillAction(R.id.billTv, "TV");

        setBillAction(R.id.billInsurance, "Insurance");

        setBillAction(R.id.billGas, "Gas");

        setBillAction(R.id.billEducation, "Education");

        findViewById(R.id.billMore).setOnClickListener(v -> {

            showMessage("More payment categories coming soon.");

        });

    }


    // CATEGORY CLICK LISTENER

    private void setBillAction(int viewId, String category) {

        findViewById(viewId).setOnClickListener(v -> {

            showPaymentForm(category);

        });

    }


    // PAYMENT FORM

    private void showPaymentForm(String category) {

        LinearLayout form = new LinearLayout(this);

        form.setOrientation(LinearLayout.VERTICAL);

        form.setPadding(dp(20), dp(10), dp(20), dp(5));


        // Reference Number

        EditText etReference = new EditText(this);

        etReference.setHint("Consumer / Reference Number");

        etReference.setSingleLine(true);

        etReference.setInputType(InputType.TYPE_CLASS_TEXT);


        // Payment Amount

        EditText etAmount = new EditText(this);

        etAmount.setHint("Enter Amount (PKR)");

        etAmount.setSingleLine(true);

        etAmount.setInputType(
                InputType.TYPE_CLASS_NUMBER |
                        InputType.TYPE_NUMBER_FLAG_DECIMAL
        );


        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        dp(55)
                );

        form.addView(etReference, params);


        LinearLayout.LayoutParams amountParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        dp(55)
                );

        amountParams.topMargin = dp(10);

        form.addView(etAmount, amountParams);


        // Payment Dialog

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(category + " Payment")
                .setMessage("Enter your demo payment details.")
                .setView(form)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Continue", null)
                .create();


        dialog.setOnShowListener(d -> {

            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                    .setOnClickListener(v -> {

                        String reference = etReference.getText()
                                .toString().trim();

                        String amountText = etAmount.getText()
                                .toString().trim();


                        // Reference Validation

                        if (reference.isEmpty()) {

                            etReference.setError(
                                    "Enter reference number"
                            );

                            etReference.requestFocus();

                            return;
                        }


                        // Amount Validation

                        if (amountText.isEmpty()) {

                            etAmount.setError(
                                    "Enter payment amount"
                            );

                            etAmount.requestFocus();

                            return;
                        }


                        double amount;

                        try {

                            amount = Double.parseDouble(amountText);

                        } catch (NumberFormatException e) {

                            etAmount.setError("Invalid amount");

                            etAmount.requestFocus();

                            return;
                        }


                        // Check valid amount

                        if (Double.isNaN(amount) ||
                                Double.isInfinite(amount) ||
                                amount <= 0) {

                            etAmount.setError(
                                    "Amount must be greater than zero"
                            );

                            return;
                        }


                        // Close current dialog

                        dialog.dismiss();


                        // Show confirmation

                        showPaymentConfirmation(
                                category,
                                reference,
                                amount
                        );

                    });

        });


        dialog.show();

    }


    // PAYMENT CONFIRMATION

    private void showPaymentConfirmation(
            String category,
            String reference,
            double amount) {

        String details =
                "Category: " + category +
                        "\nReference: " + reference +
                        "\nAmount: PKR " +
                        String.format(
                                Locale.US,
                                "%,.2f",
                                amount
                        ) +
                        "\n\nThis is a simulated demo payment. No real bill will be paid.";


        new AlertDialog.Builder(this)
                .setTitle("Confirm Demo Payment")
                .setMessage(details)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Confirm", (dialog, which) -> {

                    showMessage(
                            "Demo payment simulated successfully!"
                    );

                })
                .show();

    }


    // COMMON TOAST

    private void showMessage(String message) {

        Toast.makeText(
                this,
                message,
                Toast.LENGTH_SHORT
        ).show();

    }


    // DP CONVERSION

    private int dp(float value) {

        return (int) (
                value * getResources()
                        .getDisplayMetrics().density + 0.5f
        );

    }

}
