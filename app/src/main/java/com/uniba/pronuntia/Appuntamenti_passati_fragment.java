package com.uniba.pronuntia;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class Appuntamenti_passati_fragment extends Fragment {
    private RecyclerView recyclerView;
    private List<itemAppuntamento> appuntamentiPassati;
    private DBHelper db;
    private String emailGenitore;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.appuntamenti_passati_fragment,container,false);
db=new DBHelper(getActivity());
        SharedPreferences sdp= getActivity().getSharedPreferences("UserPrefs", getContext().MODE_PRIVATE);

        emailGenitore=sdp.getString("userEmail",null);
appuntamentiPassati=db.getInfoAppuntamentiPassati(emailGenitore);
      recyclerView=view.findViewById(R.id.ListaAppuntamentiPassati);
      recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
AppuntamentiFissatiAdapter adapter=new AppuntamentiFissatiAdapter(getActivity(),appuntamentiPassati);
recyclerView.setAdapter(adapter);









        return view;
    }
}
