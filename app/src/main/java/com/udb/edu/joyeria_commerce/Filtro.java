package com.udb.edu.joyeria_commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import com.udb.edu.joyeria_commerce.R;

import java.util.ArrayList;

import com.udb.edu.joyeria_commerce.DetailActivity;
import com.udb.edu.joyeria_commerce.Joyas;
import com.udb.edu.joyeria_commerce.JoyasAdapter;

/*import self.is.ccahill.com.au.shapelist.DetailActivity;
import self.is.ccahill.com.au.shapelist.Joyas;
import self.is.ccahill.com.au.shapelist.JoyasAdapter;*/

public class Filtro extends AppCompatActivity
{

    public static ArrayList<Joyas> joyasList = new ArrayList<Joyas>();

    private ListView listView;

    private String selectedFilter = "todos";
    private String currentSearchText = "";
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSearchWidgets();
        setupData();
        setUpList();
        setUpOnclickListener();
    }

    private void initSearchWidgets()
    {
        searchView = (SearchView) findViewById(R.id.joyasListSearchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                currentSearchText = s;
                ArrayList<Joyas> filteredJoyas = new ArrayList<Joyas>();

                for(Joyas joyas: joyasList)
                {
                    if(joyas.getName().toLowerCase().contains(s.toLowerCase()))
                    {
                        if(selectedFilter.equals("todos"))
                        {
                            filteredJoyas.add(joyas);
                        }
                        else
                        {
                            if(joyas.getName().toLowerCase().contains(selectedFilter))
                            {
                                filteredJoyas.add(joyas);
                            }
                        }
                    }
                }
                //JoyasAdapter adapter = new JoyasAdapter(getApplicationContext(), 0, filteredJoyas);
                //listView.setAdapter(adapter);

                return false;
            }
        });
    }

    private void setupData()
    {
        Joyas collar = new Joyas("0", "Collar", R.drawable.collar);
        joyasList.add(collar);

        Joyas pulsera = new Joyas("1","Pulsera", R.drawable.pulsera);
        joyasList.add(pulsera);

        Joyas brazalete = new Joyas("2","Brazalete", R.drawable.brazalete);
        joyasList.add(brazalete);

        Joyas aritos = new Joyas("3","Aritos", R.drawable.aritos);
        joyasList.add(aritos);

        Joyas anillo = new Joyas("4","Anillo", R.drawable.anillo);
        joyasList.add(anillo);

        Joyas collar2 = new Joyas("5", "Collar 2", R.drawable.collar2);
        joyasList.add(collar2);

        Joyas pulsera2 = new Joyas("6","Pulsera 2", R.drawable.pulsera2);
        joyasList.add(pulsera2);

        Joyas brazalete2 = new Joyas("7","Brazalete 2", R.drawable.brazalete2);
        joyasList.add(brazalete2);

        Joyas aritos2 = new Joyas("8","Aritos 2", R.drawable.aritos2);
        joyasList.add(aritos2);

        Joyas anillo2 = new Joyas("9","Anillo 2", R.drawable.anillo2);
        joyasList.add(anillo2);
    }

    private void setUpList()
    {
        listView = (ListView) findViewById(R.id.joyasListView);

        //JoyasAdapter adapter = new JoyasAdapter(getApplicationContext(), 0, joyasList);
        //listView.setAdapter(adapter);
    }

    private void setUpOnclickListener()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Joyas selectJoyas = (Joyas) (listView.getItemAtPosition(position));
                Intent showDetail = new Intent(getApplicationContext(), DetailActivity.class);
                showDetail.putExtra("id",selectJoyas.getId());
                startActivity(showDetail);
            }
        });

    }



    private void filterList(String status)
    {
        selectedFilter = status;

        ArrayList<Joyas> filteredJoyas = new ArrayList<Joyas>();

        for(Joyas joyas: joyasList)
        {
            if(joyas.getName().toLowerCase().contains(status))
            {
                if(currentSearchText == "")
                {
                    filteredJoyas.add(joyas);
                }
                else
                {
                    if(joyas.getName().toLowerCase().contains(currentSearchText.toLowerCase()))
                    {
                        filteredJoyas.add(joyas);
                    }
                }
            }
        }

        //JoyasAdapter adapter = new JoyasAdapter(getApplicationContext(), 0, filteredJoyas);
        //listView.setAdapter(adapter);
    }




    public void todosFilterTapped(View view)
    {
        selectedFilter = "todos";

        //JoyasAdapter adapter = new JoyasAdapter(getApplicationContext(), 0, joyasList);
        //listView.setAdapter(adapter);
    }

    public void pulseraFilterTapped(View view)
    {

        filterList("pulsera");
    }

    public void brazaleteFilterTapped(View view)
    {

        filterList("brazalete");
    }

    public void anilloFilterTapped(View view)
    {

        filterList("anillo");
    }

    public void aritosFilterTapped(View view)
    {

        filterList("aritos");
    }

    public void collarFilterTapped(View view)
    {

        filterList("collar");
    }
}