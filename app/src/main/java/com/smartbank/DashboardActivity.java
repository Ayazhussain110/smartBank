
package com.smartbank;

import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.graphics.drawable.GradientDrawable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private TextView tvBalance;
    private TextView tvToggleBalance;

    private LinearLayout transactionsContainer;

    private boolean balanceVisible = true;

    private final String actualBalance = "PKR 245,850.00";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);

        initializeViews();

        setupBalance();

        setupQuickActions();

        setupTransactions();

        setupBottomNavigation();

        setupHeader();

    }


    // INITIALIZE VIEWS

    private void initializeViews() {

        tvBalance = findViewById(R.id.tvBalance);

        tvToggleBalance = findViewById(R.id.tvToggleBalance);

        transactionsContainer =
                findViewById(R.id.transactionsContainer);

    }


    // BALANCE HIDE / SHOW

    private void setupBalance() {

        tvToggleBalance.setOnClickListener(v -> {

            balanceVisible = !balanceVisible;

            if (balanceVisible) {

                tvBalance.setText(actualBalance);

                tvToggleBalance.setText("Hide");

            } else {

                tvBalance.setText("PKR ••••••••");

                tvToggleBalance.setText("Show");

            }

        });

    }


    // QUICK ACTIONS

    private void setupQuickActions() {

        // Send opens Transfer screen
        findViewById(R.id.quickSend).setOnClickListener(v -> {

            Intent intent = new Intent(
                    DashboardActivity.this,
                    TransferActivity.class
            );

            startActivity(intent);

        });
        findViewById(R.id.tvSeeAll).setOnClickListener(v -> {

            startActivity(new Intent(
                    DashboardActivity.this,
                    HistoryActivity.class
            ));

        });


        // Other demo actions

        findViewById(R.id.quickReceive).setOnClickListener(v -> {

            Intent intent = new Intent(
                    DashboardActivity.this,
                    ReceiveActivity.class
            );

            startActivity(intent);

        });

        setAction(R.id.quickDeposit, "Deposit");


        findViewById(R.id.quickBills).setOnClickListener(v -> {

            Intent intent = new Intent(
                    DashboardActivity.this,
                    BillPaymentsActivity.class
            );

            startActivity(intent);

        });


        findViewById(R.id.tvSeeAll).setOnClickListener(v -> {
            showMessage("Transaction History selected");
        });

    }


    private void setAction(int id, String action) {

        findViewById(id).setOnClickListener(v -> {

            showMessage(action + " - Demo");

        });

    }


    // RECENT TRANSACTIONS

    private void setupTransactions() {

        addTransaction(
                "N",
                "Netflix Subscription",
                "Today, 10:24 AM",
                "-PKR 1,800.00",
                false
        );

        addTransaction(
                "S",
                "Salary Deposit",
                "Yesterday, 09:15 AM",
                "+PKR 185,000.00",
                true
        );

        addTransaction(
                "F",
                "Food & Dining",
                "Sat, 08:20 PM",
                "-PKR 850.00",
                false
        );

    }



    private void addTransaction(
            String icon,
            String name,
            String date,
            String amount,
            boolean isIncome) {

        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setPadding(dp(6), dp(8), dp(6), dp(8));
        row.setMinimumHeight(dp(58));

        // Transaction icon
        TextView tvIcon = new TextView(this);
        tvIcon.setText(icon);
        tvIcon.setTextSize(15);
        tvIcon.setGravity(Gravity.CENTER);
        tvIcon.setTypeface(null, android.graphics.Typeface.BOLD);

        int backgroundColor;
        int textColor;

        if (name.toLowerCase().contains("netflix")) {
            backgroundColor = Color.parseColor("#FCE7E7");
            textColor = Color.parseColor("#DC2626");
        } else if (isIncome) {
            backgroundColor = Color.parseColor("#DCFCE7");
            textColor = Color.parseColor("#16A34A");
        } else {
            backgroundColor = Color.parseColor("#FFF0DF");
            textColor = Color.parseColor("#EA580C");
        }

        GradientDrawable iconBackground = new GradientDrawable();
        iconBackground.setShape(GradientDrawable.OVAL);
        iconBackground.setColor(backgroundColor);

        tvIcon.setBackground(iconBackground);
        tvIcon.setTextColor(textColor);

        row.addView(tvIcon, new LinearLayout.LayoutParams(
                dp(40), dp(40)
        ));

        // Transaction details
        LinearLayout details = new LinearLayout(this);
        details.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams detailParams =
                new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1
                );

        detailParams.setMargins(dp(12), 0, dp(5), 0);

        TextView tvName = new TextView(this);
        tvName.setText(name);
        tvName.setTextSize(12);
        tvName.setTextColor(Color.parseColor("#111827"));
        tvName.setTypeface(null, android.graphics.Typeface.BOLD);

        TextView tvDate = new TextView(this);
        tvDate.setText(date);
        tvDate.setTextSize(10);
        tvDate.setTextColor(Color.parseColor("#98A2B3"));

        details.addView(tvName);
        details.addView(tvDate);

        row.addView(details, detailParams);

        // Transaction amount
        TextView tvAmount = new TextView(this);
        tvAmount.setText(amount);
        tvAmount.setTextSize(11);
        tvAmount.setTypeface(null, android.graphics.Typeface.BOLD);

        tvAmount.setTextColor(
                Color.parseColor(isIncome ? "#16A34A" : "#111827")
        );

        row.addView(tvAmount);

        transactionsContainer.addView(row);
    }



    // BOTTOM NAVIGATION




    private void setupBottomNavigation() {

        int[] navIds = {
                R.id.navHome,
                R.id.navCards,
                R.id.navTransfer,
                R.id.navHistory,
                R.id.navProfile
        };

        for (int i = 0; i < navIds.length; i++) {

            final int position = i;

            findViewById(navIds[i]).setOnClickListener(v -> {

                switch (position) {

                    case 1:
                        startActivity(new Intent(
                                DashboardActivity.this,
                                CardsActivity.class
                        ));
                        break;

                    case 2:
                        startActivity(new Intent(
                                DashboardActivity.this,
                                TransferActivity.class
                        ));
                        break;

                    case 3:
                        startActivity(new Intent(
                                DashboardActivity.this,
                                HistoryActivity.class
                        ));
                        break;

                    case 4:{
                        startActivity(new Intent(
                                DashboardActivity.this,
                                ProfileActivity.class

                        ));
                        break;
                    }

                    case 5:
                        showMessage("Profile screen coming next");
                        break;

                }

            });

        }
    }







    private void updateNavigation(
            int[] navIds,
            int selectedPosition) {

        for (int i = 0; i < navIds.length; i++) {

            TextView item = findViewById(navIds[i]);

            int iconColor = (i == selectedPosition)
                    ? Color.parseColor("#1558E8")
                    : Color.parseColor("#98A2B3");

            android.graphics.drawable.Drawable icon =
                    item.getCompoundDrawables()[1];

            if (icon != null) {

                icon = icon.mutate();

                icon.setTint(iconColor);

                item.setCompoundDrawablesWithIntrinsicBounds(
                        null, icon, null, null
                );
            }
        }
    }


    // HEADER BUTTONS

    private void setupHeader() {

        findViewById(R.id.btnNotification)
                .setOnClickListener(v -> {

                    showMessage("Notifications - Demo");

                });

        findViewById(R.id.btnProfile)
                .setOnClickListener(v -> {

                    showMessage("Profile selected");

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
    private int dp(float value) {
        return (int) (value * getResources()
                .getDisplayMetrics().density + 0.5f);
    }

}
