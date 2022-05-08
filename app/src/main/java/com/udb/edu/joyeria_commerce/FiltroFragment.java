package com.udb.edu.joyeria_commerce;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class FiltroFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public static ArrayList<Joyas> joyasList = new ArrayList<Joyas>();

    private ListView listView;

    private String selectedFilter = "todos";
    private String currentSearchText = "";
    private SearchView searchView;


    public static FiltroFragment newInstance(String param1, String param2) {
        FiltroFragment fragment = new FiltroFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FiltroFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         View vista=inflater.inflate(R.layout.fragment_filtro, container, false);
        initSearchWidgets(vista);
        setupData(vista);
        setUpList(vista);
        setUpOnclickListener(vista);
        return vista;
    }

    private void initSearchWidgets(View v)
    {
        searchView = (SearchView) v.findViewById(R.id.joyasListSearchView);

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
                JoyasAdapter adapter = new JoyasAdapter(getContext(), 0, filteredJoyas);
                listView.setAdapter(adapter);

                return false;
            }
        });
    }

    private void setupData(View v)
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

    private void setUpList(View v)
    {
        listView = (ListView) v.findViewById(R.id.joyasListView);

        JoyasAdapter adapter = new JoyasAdapter(getContext(), 0, joyasList);
        listView.setAdapter(adapter);
    }

    private void setUpOnclickListener(View v)
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Joyas selectJoyas = (Joyas) (listView.getItemAtPosition(position));
                Intent showDetail = new Intent(getContext(), DetailActivity.class);
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

        JoyasAdapter adapter = new JoyasAdapter(getContext(), 0, filteredJoyas);
        listView.setAdapter(adapter);
    }




    public void todosFilterTapped(View view)
    {
        selectedFilter = "todos";

        JoyasAdapter adapter = new JoyasAdapter(getContext(), 0, joyasList);
        listView.setAdapter(adapter);
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