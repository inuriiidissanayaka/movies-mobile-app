package com.example.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import org.xmlpull.v1.XmlPullParser;
import androidx.annotation.NonNull;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProfileActivity extends AppCompatActivity {

    EditText Name, Password, Email, Age;
    TextView Greeting;
    Button Rentals, Due, History;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        Name = findViewById(R.id.txtName);
        Password = findViewById(R.id.txtPassword);
        Email = findViewById(R.id.txtEmail);
        Age = findViewById(R.id.txtAge);
        Greeting = findViewById(R.id.txtGreeting);

        Rentals = findViewById(R.id.btnCurrent);
        Due = findViewById(R.id.btnDue);
        History = findViewById(R.id.btnHistory);

        db = openOrCreateDatabase("MovieRentApp", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT, password TEXT, membership TEXT )");

        fetchXMLUserData();
    }

    private void fetchXMLUserData()
    {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://randomuser.me/api/?format=xml").addHeader("Accept", "application/xml").build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                runOnUiThread(() -> loadFromDatabase());
            }

            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        InputStream inputStream = response.body().byteStream();
                        XmlPullParser parser = Xml.newPullParser();
                        parser.setInput(inputStream, null);

                        String name = "";
                        String email = "";
                        String password = "";
                        String age = "";

                        int eventType = parser.getEventType();
                        while (eventType != XmlPullParser.END_DOCUMENT) {
                            String tagName = parser.getName();

                            if (eventType == XmlPullParser.START_TAG) {
                                switch (tagName) {
                                    case "first":
                                        parser.next();
                                        name += parser.getText() + " ";
                                        break;
                                    case "email":
                                        parser.next();
                                        email += parser.getText();
                                        break;
                                    case "password":
                                        parser.next();
                                        password += parser.getText();
                                        break;
                                    case "age":
                                        parser.next();
                                        age = parser.getText();
                                        break;
                                }
                            }

                            eventType = parser.next();
                        }

                        String finalName = name.trim();
                        String finalEmail = email;
                        String finalPassword = password;
                        String finalAge = age;

                        saveToDatabase(finalName, finalEmail, finalPassword, finalAge);

                        runOnUiThread(() -> {
                            Name.setText(finalName);
                            Email.setText(finalEmail);
                            Password.setText(finalPassword);
                            Age.setText(finalAge);
                            Greeting.setText("Hi " + finalName +"!");
                        });

                    } catch (Exception e) {
                        Log.e("XML_ERROR", e.toString());
                        runOnUiThread(() -> loadFromDatabase());
                    }
                } else {
                    runOnUiThread(() -> loadFromDatabase());
                }
            }
        });

    }
    private void saveToDatabase(String name, String email, String password, String membership)
    {
        db.execSQL("DELETE FROM user");
        db.execSQL("INSERT INTO user(name, email, password, membership) VALUES ('"+name+"','"+email+"','"+password+"','"+membership+"')");

    }

    private void loadFromDatabase() {
        Cursor cursor = db.rawQuery("SELECT * FROM user LIMIT 1", null);
        if (cursor.moveToFirst()) {
            Name.setText(cursor.getString(1));
            Email.setText(cursor.getString(2));
            Password.setText(cursor.getString(3));
            Age.setText(cursor.getString(4));
        }

        cursor.close();
    }
}

