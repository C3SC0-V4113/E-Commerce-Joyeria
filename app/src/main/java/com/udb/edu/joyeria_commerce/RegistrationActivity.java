package com.udb.edu.joyeria_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import sv.edu.udb.appfarmaciadsm.R;

public class RegistrationActivity extends AppCompatActivity {

    public String email, password;
    private FirebaseAuth mAuth;
    private EditText txtUser, txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();
        //Inicializando campos a utilizar
        txtUser=(EditText)findViewById(R.id.editTxtUser2);
        txtPass=(EditText)findViewById(R.id.editTxtPass2);
        //Asignando método al boton
        findViewById(R.id.btnSignUp2).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {   Register();  }
        });
    }

    public void Register()
    {
        email = txtUser.getText().toString();
        password = txtPass.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Por favor ingresar un correo.", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Por favor ingresar una contraseña.", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "¡Usuario registrado!", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "El usuario no pudo ser registrado.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}