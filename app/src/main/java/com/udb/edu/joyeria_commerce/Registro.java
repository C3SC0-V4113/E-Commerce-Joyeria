package com.udb.edu.joyeria_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registro extends AppCompatActivity {

    private EditText emailTV, passwordTV;
    private Button regBtn;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        initializeUI();

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerNewUser();
            }
        });
    }

    public void registerNewUser(){
        //progressBar.setVisibility(View.VISIBLE);

        String email,password;
        email = emailTV.getText().toString();
        password = passwordTV.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),"Por favor ingrese correo",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"Por favor ingrese contraseña",Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Registro Exitoso",Toast.LENGTH_LONG).show();
                            //progressBar.setVisibility(View.GONE);

                            Intent intent = new Intent(Registro.this,Login.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"Fallo el registro, intete de nuevo",Toast.LENGTH_LONG).show();
                            //progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }


    private void initializeUI() {
        emailTV = findViewById(R.id.username);
        passwordTV = findViewById(R.id.password);
        regBtn = findViewById(R.id.registrobtn);
        //progressBar = findViewById(R.id.progressBar);
    }
}