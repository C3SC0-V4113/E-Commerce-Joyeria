package com.udb.edu.joyeria_commerce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udb.edu.joyeria_commerce.datos.Producto;

import java.util.ArrayList;
import java.util.List;

public class FiltroFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;


    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference refProductos = database.getReference("productos");

    private static List<Producto> productos;

    public static ArrayList<Joyas> joyasList = new ArrayList<Joyas>();

    private ListView listView;

    private String selectedFilter = "todos";
    private String currentSearchText = "";
    private SearchView searchView;
    Button btnTodo,btnPulseras,btnBrazalete,btnAnillo,btnAritos,btnCollar;

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
         btnAnillo = vista.findViewById(R.id.anilloFilter);
         btnTodo = vista.findViewById(R.id.todosFilter);
         btnAritos =vista.findViewById(R.id.aritosFilter);
         btnBrazalete = vista.findViewById(R.id.brazaleteFilter);
         btnPulseras = vista.findViewById(R.id.pulseraFilter);
         btnCollar = vista.findViewById(R.id.collarFilter);
        initSearchWidgets(vista);
        inicializar();
        //setupData(vista);
        setUpList(vista);
        setUpOnclickListener(vista);
        btnTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todosFilterTapped(view);
            }
        });
        btnCollar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collarFilterTapped(view);
            }
        });
        btnAritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aritosFilterTapped(view);
            }
        });
        btnAnillo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anilloFilterTapped(view );
            }
        });
        btnPulseras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pulseraFilterTapped(view);
            }
        });
        btnBrazalete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brazaleteFilterTapped(view);
            }
        });
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
                ArrayList<Producto> filteredJoyas = new ArrayList<Producto>();

                for(Producto joyas: productos)
                {
                    if(joyas.getNombre().toLowerCase().contains(s.toLowerCase()))
                    {
                        if(selectedFilter.equals("todos"))
                        {
                            filteredJoyas.add(joyas);
                        }
                        else
                        {
                            if(joyas.getNombre().toLowerCase().contains(selectedFilter))
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

    public void inicializar(){

        // Mostrar lista de productos ingresados en la base
        productos = new ArrayList<>();

        refProductos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productos.removeAll(productos);
                for(DataSnapshot dato: snapshot.getChildren())
                {
                    Producto producto = dato.getValue(Producto.class);
                    producto.setKey(dato.getKey());
                    productos.add(producto);
                }

                JoyasAdapter adapter = new JoyasAdapter(getContext(), 0, productos);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

        JoyasAdapter adapter = new JoyasAdapter(getContext(), 0, productos);
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

        ArrayList<Producto> filteredJoyas = new ArrayList<Producto>();

        for(Producto joyas: productos)
        {
            if(joyas.getNombre().toLowerCase().contains(status))
            {
                if(currentSearchText == "")
                {
                    filteredJoyas.add(joyas);
                }
                else
                {
                    if(joyas.getNombre().toLowerCase().contains(currentSearchText.toLowerCase()))
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

        JoyasAdapter adapter = new JoyasAdapter(getContext(), 0, productos);
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