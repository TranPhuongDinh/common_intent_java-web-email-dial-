package com.example.commonintents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn_web, btn_email, btn_dial;

    EditText et_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btn_web = findViewById(R.id.btn_web);
        btn_email = findViewById(R.id.btn_email);
        btn_dial = findViewById(R.id.btn_dial);
        et_data = findViewById(R.id.text_data);

        btn_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!openWebPage(et_data.getText().toString())) {
                    Toast.makeText(v.getContext(), "Invalid URL!", Toast.LENGTH_SHORT).show();
                }
            }

            public boolean openWebPage(String url) {
                if (!url.startsWith("https://") || !url.startsWith("http://")) {
                    url = "https://" + url;
                }
                Uri webpage = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        btn_email.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String[] addresses = new String[1];
                addresses[0] = et_data.getText().toString();
                if (!sendEmail(addresses, "Email subject")) {
                    Toast.makeText(v.getContext(), "Invalid email!", Toast.LENGTH_SHORT).show();
                }
            }

            private boolean sendEmail(String[] addresses, String subject) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, addresses);
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        btn_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneCard = et_data.getText().toString();
                phoneCard = "*100*" + phoneCard + "#";
                if (!dialPhoneNumber(phoneCard)) {
                    Toast.makeText(v.getContext(), "Invalid phone number!", Toast.LENGTH_SHORT).show();
                }
            }
            public boolean dialPhoneNumber(String phoneNumber) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }
}