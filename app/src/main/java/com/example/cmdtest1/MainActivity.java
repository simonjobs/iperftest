package com.example.cmdtest1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("iperf");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }



    public void buttonPress(View view) {
        TextView txt = findViewById(R.id.txt);
        txt.setText(runCmd());

    }

    public String runCmd() {
        EditText txtInput = findViewById(R.id.txtInput);
        String cmd = txtInput.getText().toString();

        try {

            Process process = Runtime.getRuntime().exec(cmd);


             BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            int read;
            char[] buffer = new char[4096];
            StringBuffer output = new StringBuffer();
            while ((read = reader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
            reader.close();

            // Waits for the command to finish.
            process.waitFor();

            return output.toString();
        } catch (IOException e) {
            //return "error1";
            throw new RuntimeException(e);

        } catch (InterruptedException e) {
            //return "error2";
            throw new RuntimeException(e);
        }

    }
}