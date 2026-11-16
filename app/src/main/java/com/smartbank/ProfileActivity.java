
package com.smartbank;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvProfileName;
    private TextView tvProfileEmail;

    private int[] navigationIds = {
            R.id.navHome,
            R.id.navCards,
            R.id.navTransfer,
            R.id.navHistory,
            R.id.navProfile
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        initializeViews();

        setupProfileActions();

        setupNavigation();

    }


    // INITIALIZE VIEWS

    private void initializeViews() {

        tvProfileName = findViewById(R.id.tvProfileName);

        tvProfileEmail = findViewById(R.id.tvProfileEmail);

    }


    // PROFILE ACTIONS

    private void setupProfileActions() {

        // PERSONAL INFORMATION

        findViewById(R.id.btnPersonalInfo)
                .setOnClickListener(v -> showPersonalInfo());


        // SECURITY SETTINGS

        findViewById(R.id.btnSecurity)
                .setOnClickListener(v -> showSecuritySettings());


        // HELP AND SUPPORT

        findViewById(R.id.btnHelpSupport)
                .setOnClickListener(v -> showHelpSupport());


        // LOGOUT

        findViewById(R.id.btnLogout)
                .setOnClickListener(v -> confirmLogout());

    }


    // PERSONAL INFORMATION DIALOG

    private void showPersonalInfo() {

        String details =
                "Full Name: " + tvProfileName.getText().toString() +
                        "\n\nAccount Number: •••• •••• 2048" +
                        "\n\nAccount Type: Savings Account" +
                        "\n\nEmail: Not configured in demo";

        new AlertDialog.Builder(this)
                .setTitle("Personal Information")
                .setMessage(details)
                .setPositiveButton("Close", null)
                .show();

    }


    // SECURITY SETTINGS DIALOG

    private void showSecuritySettings() {

        String details =
                "Account Security\n\n" +
                        "OTP Verification: Enabled (Demo)\n\n" +
                        "Card Status: Manage from Cards screen\n\n" +
                        "Security information is simulated.";

        new AlertDialog.Builder(this)
                .setTitle("Security Settings")
                .setMessage(details)
                .setPositiveButton("OK", null)
                .show();

    }


    // HELP AND SUPPORT

    private void showHelpSupport() {

        new AlertDialog.Builder(this)
                .setTitle("Help & Support")
                .setMessage(
                        "Welcome to SmartBank Demo Support.\n\n" +
                                "This is a demonstration application. " +
                                "No live banking support is connected."
                )
                .setPositiveButton("OK", null)
                .show();

    }


    // LOGOUT CONFIRMATION

    private void confirmLogout() {

        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage(
                        "Are you sure you want to logout?"
                )
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Logout", (dialog, which) -> {

                    performLogout();

                })
                .show();

    }


    // PERFORM LOGOUT


    private void performLogout() {

        Intent intent = new Intent(
                ProfileActivity.this,
                MainActivity.class
        );

        intent.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK
        );

        startActivity(intent);

    }



    // BOTTOM NAVIGATION

    private void setupNavigation() {

        for (int i = 0; i < navigationIds.length; i++) {

            final int position = i;

            TextView item = findViewById(navigationIds[i]);

            item.setOnClickListener(v -> {

                switch (position) {

                    case 0:
                        openScreen(DashboardActivity.class);
                        break;

                    case 1:
                        openScreen(CardsActivity.class);
                        break;

                    case 2:
                        openScreen(TransferActivity.class);
                        break;

                    case 3:
                        openScreen(HistoryActivity.class);
                        break;

                    case 4:
                        showMessage("Already on Profile");
                        break;

                }

            });

        }

        updateNavigationIcons(4);

    }


    // SELECTED NAVIGATION ICON

    private void updateNavigationIcons(int selectedPosition) {

        for (int i = 0; i < navigationIds.length; i++) {

            TextView item = findViewById(navigationIds[i]);

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


    // OPEN OTHER SCREENS

    private void openScreen(Class<?> activityClass) {

        Intent intent = new Intent(
                ProfileActivity.this,
                activityClass
        );

        startActivity(intent);

        finish();

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
