
package com.smartbank;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class HistoryActivity extends AppCompatActivity {

    private TextView tvTotalIncome;
    private TextView tvTotalExpense;

    private EditText etSearchTransaction;

    private LinearLayout historyContainer;

    private TextView filterAll;
    private TextView filterIncome;
    private TextView filterExpense;

    private String selectedFilter = "All";

    private final ArrayList<Transaction> transactions =
            new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_history);

        initializeViews();

        loadDemoTransactions();

        updateSummary();

        setupFilters();

        setupSearch();

        setupNavigation();

        renderTransactions();

    }


    // INITIALIZE XML VIEWS

    private void initializeViews() {

        tvTotalIncome = findViewById(R.id.tvTotalIncome);

        tvTotalExpense = findViewById(R.id.tvTotalExpense);

        etSearchTransaction =
                findViewById(R.id.etSearchTransaction);

        historyContainer =
                findViewById(R.id.historyContainer);

        filterAll = findViewById(R.id.filterAll);

        filterIncome = findViewById(R.id.filterIncome);

        filterExpense = findViewById(R.id.filterExpense);

    }


    // DEMO TRANSACTION DATA

    private void loadDemoTransactions() {

        transactions.add(new Transaction(
                "S",
                "Salary Deposit",
                "15 Nov 2026 • 09:15 AM",
                185000,
                true,
                "Successful"
        ));

        transactions.add(new Transaction(
                "N",
                "Netflix Subscription",
                "14 Nov 2026 • 10:24 AM",
                1800,
                false,
                "Successful"
        ));

        transactions.add(new Transaction(
                "F",
                "Food & Dining",
                "13 Nov 2026 • 08:20 PM",
                850,
                false,
                "Successful"
        ));

        transactions.add(new Transaction(
                "C",
                "Cashback Reward",
                "12 Nov 2026 • 11:30 AM",
                250,
                true,
                "Successful"
        ));

        transactions.add(new Transaction(
                "E",
                "Electricity Bill",
                "11 Nov 2026 • 02:15 PM",
                2500,
                false,
                "Pending"
        ));

        transactions.add(new Transaction(
                "O",
                "Online Shopping",
                "10 Nov 2026 • 06:45 PM",
                3200,
                false,
                "Successful"
        ));

    }


    // UPDATE INCOME AND EXPENSE SUMMARY

    private void updateSummary() {

        double income = 0;

        double expense = 0;

        for (Transaction transaction : transactions) {

            if (transaction.isIncome) {

                income += transaction.amount;

            } else {

                expense += transaction.amount;

            }

        }

        tvTotalIncome.setText(
                "PKR " + formatMoney(income)
        );

        tvTotalExpense.setText(
                "PKR " + formatMoney(expense)
        );

    }


    // FILTER BUTTONS

    private void setupFilters() {

        filterAll.setOnClickListener(v ->
                changeFilter("All"));

        filterIncome.setOnClickListener(v ->
                changeFilter("Income"));

        filterExpense.setOnClickListener(v ->
                changeFilter("Expense"));

        updateFilterAppearance();

    }


    private void changeFilter(String filter) {

        selectedFilter = filter;

        updateFilterAppearance();

        renderTransactions();

    }


    private void updateFilterAppearance() {

        setFilterStyle(
                filterAll,
                selectedFilter.equals("All")
        );

        setFilterStyle(
                filterIncome,
                selectedFilter.equals("Income")
        );

        setFilterStyle(
                filterExpense,
                selectedFilter.equals("Expense")
        );

    }


    private void setFilterStyle(
            TextView button,
            boolean selected) {

        GradientDrawable background =
                new GradientDrawable();

        background.setCornerRadius(dp(10));

        if (selected) {

            background.setColor(
                    Color.parseColor("#1558E8")
            );

            button.setTextColor(Color.WHITE);

        } else {

            background.setColor(Color.WHITE);

            background.setStroke(
                    dp(1),
                    Color.parseColor("#E5E7EB")
            );

            button.setTextColor(
                    Color.parseColor("#667085")
            );

        }

        button.setBackground(background);

    }


    // SEARCH TRANSACTIONS

    private void setupSearch() {

        etSearchTransaction.addTextChangedListener(
                new TextWatcher() {

                    @Override
                    public void beforeTextChanged(
                            CharSequence s,
                            int start,
                            int count,
                            int after) {
                    }

                    @Override
                    public void onTextChanged(
                            CharSequence s,
                            int start,
                            int before,
                            int count) {

                        renderTransactions();

                    }

                    @Override
                    public void afterTextChanged(
                            Editable s) {
                    }

                }
        );

    }


    // DISPLAY FILTERED TRANSACTIONS

    private void renderTransactions() {

        historyContainer.removeAllViews();

        String query = etSearchTransaction
                .getText()
                .toString()
                .trim()
                .toLowerCase(Locale.ROOT);

        int displayedCount = 0;

        for (Transaction transaction : transactions) {

            // Income / Expense filter

            if (selectedFilter.equals("Income")
                    && !transaction.isIncome) {

                continue;

            }

            if (selectedFilter.equals("Expense")
                    && transaction.isIncome) {

                continue;

            }


            // Search filter

            String searchableText =
                    transaction.name.toLowerCase(Locale.ROOT)
                            + " "
                            + transaction.date.toLowerCase(Locale.ROOT)
                            + " "
                            + transaction.status.toLowerCase(Locale.ROOT);

            if (!searchableText.contains(query)) {

                continue;

            }

            addTransactionRow(transaction);

            displayedCount++;

        }


        // EMPTY RESULT MESSAGE

        if (displayedCount == 0) {

            TextView emptyMessage = new TextView(this);

            emptyMessage.setText(
                    "No transactions found"
            );

            emptyMessage.setTextColor(
                    Color.parseColor("#98A2B3")
            );

            emptyMessage.setTextSize(14);

            emptyMessage.setGravity(Gravity.CENTER);

            emptyMessage.setPadding(
                    10, dp(30), 10, dp(30)
            );

            historyContainer.addView(emptyMessage);

        }

    }


    // CREATE TRANSACTION ROW

    private void addTransactionRow(
            Transaction transaction) {

        LinearLayout row = new LinearLayout(this);

        row.setOrientation(LinearLayout.HORIZONTAL);

        row.setGravity(Gravity.CENTER_VERTICAL);

        row.setPadding(
                dp(12),
                dp(12),
                dp(12),
                dp(12)
        );

        GradientDrawable rowBackground =
                new GradientDrawable();

        rowBackground.setColor(Color.WHITE);

        rowBackground.setCornerRadius(dp(14));

        rowBackground.setStroke(
                dp(1),
                Color.parseColor("#E8EDFA")
        );

        row.setBackground(rowBackground);

        LinearLayout.LayoutParams rowParams =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );

        rowParams.bottomMargin = dp(10);

        row.setLayoutParams(rowParams);


        // ICON

        TextView icon = new TextView(this);

        icon.setText(transaction.icon);

        icon.setTextSize(16);

        icon.setGravity(Gravity.CENTER);

        icon.setTypeface(null,
                android.graphics.Typeface.BOLD);

        int iconBackground = transaction.isIncome
                ? Color.parseColor("#DCFCE7")
                : Color.parseColor("#FFF0DF");

        int iconColor = transaction.isIncome
                ? Color.parseColor("#16A34A")
                : Color.parseColor("#EA580C");

        GradientDrawable iconShape =
                new GradientDrawable();

        iconShape.setShape(GradientDrawable.OVAL);

        iconShape.setColor(iconBackground);

        icon.setBackground(iconShape);

        icon.setTextColor(iconColor);

        row.addView(icon, new LinearLayout.LayoutParams(
                dp(42), dp(42)
        ));


        // DETAILS

        LinearLayout details = new LinearLayout(this);

        details.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams detailParams =
                new LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1f
                );

        detailParams.setMargins(
                dp(10), 0, dp(5), 0
        );

        TextView name = new TextView(this);

        name.setText(transaction.name);

        name.setTextColor(
                Color.parseColor("#111827")
        );

        name.setTextSize(12);

        name.setTypeface(null,
                android.graphics.Typeface.BOLD);

        TextView date = new TextView(this);

        date.setText(transaction.date);

        date.setTextColor(
                Color.parseColor("#98A2B3")
        );

        date.setTextSize(9);

        date.setMaxLines(1);


        TextView status = new TextView(this);

        status.setText("• " + transaction.status);

        status.setTextSize(9);

        status.setTextColor(
                Color.parseColor(
                        transaction.status.equals("Pending")
                                ? "#EA580C"
                                : "#16A34A"
                )
        );

        details.addView(name);

        details.addView(date);

        details.addView(status);

        row.addView(details, detailParams);


        // AMOUNT

        TextView amount = new TextView(this);

        String sign = transaction.isIncome ? "+" : "-";

        amount.setText(
                sign + "PKR " +
                        formatMoney(transaction.amount)
        );

        amount.setTextSize(10);

        amount.setMaxLines(1);

        amount.setTypeface(null,
                android.graphics.Typeface.BOLD);

        amount.setTextColor(
                Color.parseColor(
                        transaction.isIncome
                                ? "#16A34A"
                                : "#111827"
                )
        );

        row.addView(amount);

        historyContainer.addView(row);

    }


    // FORMAT AMOUNT

    private String formatMoney(double amount) {

        return String.format(
                Locale.US,
                "%,.2f",
                amount
        );

    }


    // BOTTOM NAVIGATION

    private void setupNavigation() {

        int[] navigationIds = {
                R.id.navHome,
                R.id.navCards,
                R.id.navTransfer,
                R.id.navHistory,
                R.id.navProfile
        };

        for (int i = 0; i < navigationIds.length; i++) {

            final int position = i;

            findViewById(navigationIds[i])
                    .setOnClickListener(v -> {

                        switch (position) {

                            case 0:
                                openScreen(
                                        DashboardActivity.class
                                );
                                break;

                            case 1:
                                openScreen(
                                        CardsActivity.class
                                );
                                break;

                            case 2:
                                openScreen(
                                        TransferActivity.class
                                );
                                break;

                            case 3:
                                Toast.makeText(
                                        this,
                                        "Already on History",
                                        Toast.LENGTH_SHORT
                                ).show();
                                break;

                            case 4:

                                openScreen(ProfileActivity.class);

                                break;


                        }

                    });

        }

        updateNavigationIcons(navigationIds, 3);

    }


    private void updateNavigationIcons(
            int[] ids,
            int selectedPosition) {

        for (int i = 0; i < ids.length; i++) {

            TextView item = findViewById(ids[i]);

            android.graphics.drawable.Drawable icon =
                    item.getCompoundDrawables()[1];

            if (icon != null) {

                icon = icon.mutate();

                icon.setTint(
                        Color.parseColor(
                                i == selectedPosition
                                        ? "#1558E8"
                                        : "#98A2B3"
                        )
                );

                item.setCompoundDrawablesWithIntrinsicBounds(
                        null, icon, null, null
                );

            }

        }

    }


    private void openScreen(Class<?> activityClass) {

        Intent intent = new Intent(
                HistoryActivity.this,
                activityClass
        );

        startActivity(intent);

        finish();

    }


    // DP CONVERSION

    private int dp(float value) {

        return (int) (
                value * getResources()
                        .getDisplayMetrics().density + 0.5f
        );

    }


    // TRANSACTION MODEL

    private static class Transaction {

        String icon;
        String name;
        String date;
        double amount;
        boolean isIncome;
        String status;

        Transaction(
                String icon,
                String name,
                String date,
                double amount,
                boolean isIncome,
                String status) {

            this.icon = icon;
            this.name = name;
            this.date = date;
            this.amount = amount;
            this.isIncome = isIncome;
            this.status = status;

        }

    }

}
