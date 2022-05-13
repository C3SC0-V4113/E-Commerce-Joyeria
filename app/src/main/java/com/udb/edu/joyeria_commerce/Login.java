package com.udb.edu.joyeria_commerce;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailTV, passwordTV;
    private Button loginBtn;
    private FloatingActionButton googlebtn;

    SharedPreferences settings;
    SharedPreferences.Editor editor;

    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        settings = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        //gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        //gsc = GoogleSignIn.getClient(this,gso);

        initializeUI();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUserAccount();
            }
        });



        googlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail().build();
                gsc = GoogleSignIn.getClient(getApplicationContext(),gso);
                startActivityForResult(gsc.getSignInIntent(),100);
            }
        });

    }

    private void loginUserAccount() {
        //progressBar.setVisibility(View.VISIBLE);

        String email, password;
        email = emailTV.getText().toString();
        password = passwordTV.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "¡Ingrese su correo...!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "¡Ingrese su contraseña!", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@Nullable Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            System.out.println("REPUESTA DE GOOGLE"+task.getResult());
                            SharedPreferences.Editor edit = settings.edit();
                            edit.putString("email",email);
                            edit.putString("password",password);
                            edit.apply();
                            //Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                            //progressBar.setVisibility(View.GONE);

                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Fallo! Intente de nuevo más tarde", Toast.LENGTH_LONG).show();
                            //progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public  void openRegis(View v){
        Intent intent = new Intent(Login.this, Registro.class);
        startActivity(intent);
        //finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                task.getResult(ApiException.class);
                SharedPreferences.Editor edit = settings.edit();
                edit.putString("email",task.getResult().getEmail());

                edit.apply();
                //Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                //progressBar.setVisibility(View.GONE);

                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish();

            }catch (ApiException e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Algo salio mal",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void initializeUI() {
        emailTV = findViewById(R.id.username);
        passwordTV = findViewById(R.id.password);
        googlebtn = findViewById(R.id.googlebtn);
        loginBtn = findViewById(R.id.loginbtn);
        //progressBar = findViewById(R.id.progressBar);
    }
}