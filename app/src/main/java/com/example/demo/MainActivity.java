package com.example.demo;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText edit_email,edit_password;
    LinearLayout layout;
    TextView notification;
    Button bt_signin;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edit_email = findViewById(R.id.edit_email);
        edit_password = findViewById(R.id.edit_password);
        layout = findViewById(R.id.layout_notification);
        bt_signin = findViewById(R.id.button);

        bt_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notification  = new TextView(MainActivity.this);
                notification.setTextColor(Color.RED);
                notification.setTextSize(15);
                if (edit_email.getText().toString().isEmpty() || edit_password.getText().toString().isEmpty())
                {
                    layout.removeAllViews();
                    notification.setText("Please fill in information!");
                    layout.addView(notification);
                }
                else
                {
                    if (!isValidEmail(edit_email.getText().toString()))
                    {
                        layout.removeAllViews();
                        notification.setText("Email is invalid!");
                        layout.addView(notification);
                    }
                    else
                    {
                        User user = new User(edit_email.getText().toString(), edit_password.getText().toString());
                        DatabaseHandler dbHelper = new DatabaseHandler(MainActivity.this);
                        boolean isExisted = dbHelper.checkExistedUser(user);

                        if (isExisted) {
                            //Toast.makeText(MainActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                            layout.removeAllViews();
                            notification.setText("Sign in successfully");
                            layout.addView(notification);
                        } else
                        {
                            //Toast.makeText(MainActivity.this, "Người dùng không tồn tại!", Toast.LENGTH_SHORT).show();
                            layout.removeAllViews();
                            notification.setText("User doesn't exist.");
                            layout.addView(notification);
                        }
                    }
                }
            }
        });
    }
    public boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        String EMAIL_PATTERN =
                "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                        "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }
}