
package com.smartbank;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;


public class OtpActivity extends AppCompatActivity {

    private final String DEMO_OTP = "123456";

    private EditText etOtp;
    private Button btnVerifyOtp;
    private TextView tvResendOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        etOtp = findViewById(R.id.etOtp);
        btnVerifyOtp = findViewById(R.id.btnVerifyOtp);
        tvResendOtp = findViewById(R.id.tvResendOtp);

        // Verify button click

        btnVerifyOtp.setOnClickListener(v -> {

            String enteredOtp = etOtp.getText().toString().trim();

            if (enteredOtp.isEmpty()) {
                etOtp.setError("Please enter OTP");
                return;
            }

            if (enteredOtp.equals(DEMO_OTP)) {

                Toast.makeText(OtpActivity.this,
                        "OTP verified successfully!",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(
                        OtpActivity.this,
                        DashboardActivity.class
                );

                startActivity(intent);

                finish();

            }
            else {

                Toast.makeText(OtpActivity.this,
                        "Incorrect OTP!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
