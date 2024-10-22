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

public class Appuntamenti_fissati_Fragment extends Fragment {
private  DBHelper db;
    private RecyclerView recyclerView;
     private List<itemAppuntamento> itemAppuntamentoList;
     private String emailGenitore;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.appuntamenti_fissati_fragment,container,false);
db=new DBHelper(getContext());
SharedPreferences sdp= getActivity().getSharedPreferences("UserPrefs", getContext().MODE_PRIVATE);

emailGenitore=sdp.getString("userEmail",null);
        recyclerView=view.findViewById(R.id.ListaAppuntamentiFissati);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemAppuntamentoList=db.getInfoAppuntamentoFissato(emailGenitore);
        AppuntamentiFissatiAdapter adapter=new AppuntamentiFissatiAdapter(getActivity(),itemAppuntamentoList);
        recyclerView.setAdapter(adapter);


        return  view;

    }
}
