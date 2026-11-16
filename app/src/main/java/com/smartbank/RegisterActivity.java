
package com.smartbank;

import android.os.Bundle;
import android.content.Intent;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText etFullName, etRegisterEmail, etPhone;
    EditText etRegisterPassword, etConfirmPassword;

    CheckBox cbTerms;
    Button btnCreateAccount;
    TextView tvBackToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Connect XML views
        etFullName = findViewById(R.id.etFullName);
        etRegisterEmail = findViewById(R.id.etRegisterEmail);
        etPhone = findViewById(R.id.etPhone);
        etRegisterPassword = findViewById(R.id.etRegisterPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        cbTerms = findViewById(R.id.cbTerms);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        tvBackToLogin = findViewById(R.id.tvBackToLogin);

        // Back to Login
        tvBackToLogin.setOnClickListener(v -> finish());

        // Create Account
        btnCreateAccount.setOnClickListener(v -> validateRegistration());
    }

    private void validateRegistration() {

        String name = etFullName.getText().toString().trim();
        String email = etRegisterEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String password = etRegisterPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        // Name validation
        if (name.isEmpty()) {
            etFullName.setError("Please enter your full name");
            etFullName.requestFocus();
            return;
        }

        // Email validation
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etRegisterEmail.setError("Enter a valid email address");
            etRegisterEmail.requestFocus();
            return;
        }

        // Phone validation
        if (!phone.matches("03[0-9]{9}")) {
            etPhone.setError("Enter a valid 11-digit number");
            etPhone.requestFocus();
            return;
        }

        // Password validation
        if (password.length() < 8) {
            etRegisterPassword.setError("Password must be at least 8 characters");
            etRegisterPassword.requestFocus();
            return;
        }

        // Confirm password validation
        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords do not match");
            etConfirmPassword.requestFocus();
            return;
        }

        // Terms validation
        if (!cbTerms.isChecked()) {
            Toast.makeText(this,
                    "Please accept Terms & Conditions",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // All validations passed
        Intent intent = new Intent(RegisterActivity.this, OtpActivity.class);
        startActivity(intent);
    }
}
