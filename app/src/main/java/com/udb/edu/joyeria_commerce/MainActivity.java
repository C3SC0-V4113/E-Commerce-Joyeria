package com.udb.edu.joyeria_commerce;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.SurfaceControl;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;
import com.udb.edu.joyeria_commerce.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;

    String nombre;

    SharedPreferences settings;
/*=======
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button boton;
>>>>>>> 7be23ef709ac4345ad8b4938cc81f0b9a96b170b/*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //boton=findViewById(R.id.button);
        super.onCreate(savedInstanceState);
        settings = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);

        nombre=settings.getString("email","");
        Toast.makeText(getApplicationContext(),"Bienvinido "+nombre,Toast.LENGTH_LONG).show();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
       /* binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.anillosFragment, R.id.pendientesFragment, R.id.productosFragment, R.id.pulserasFragment, R.id.charmsFragment)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        SharedPreferences.Editor edit = settings.edit();
        if (id == R.id.logout) {

            FirebaseAuth.getInstance().signOut();
            if(gsc!=null){
                gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {

                        edit.clear();
                        startActivity(new Intent(MainActivity.this,Login.class));
                        finish();
                    }
                });
            }
            edit.clear().commit();
            startActivity(new Intent(MainActivity.this,Login.class));
            Toast.makeText(this, "Cerró Sesión correctamente", Toast.LENGTH_LONG).show();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();

    }

   /* public void ProductoDetalle(View v){
        Intent intent=new Intent(MainActivity.this, ProductoDetalleActivity.class);
        startActivity(intent);
    }*/
}