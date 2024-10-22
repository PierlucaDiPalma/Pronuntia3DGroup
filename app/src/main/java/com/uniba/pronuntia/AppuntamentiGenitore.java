package com.uniba.pronuntia;

import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceControl;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

public class AppuntamentiGenitore extends AppCompatActivity {

    private TabLayout tabLayout;
    private FrameLayout frameLayout;

    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
FragmentManager fragmentManager=getSupportFragmentManager();
        setContentView(R.layout.appuntamenti_genitore);



tabLayout=findViewById(R.id.tabLayout);
frameLayout=findViewById(R.id.FragmentContainer);

tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
    @Override
    public void onTabSelected(TabLayout.Tab tab) {


LoadFragment(tab.getPosition());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
});
LoadFragment(0);


    }



    private void LoadFragment(int posizioneTab){

        Fragment fragment = null;

        switch (posizioneTab){

            case 0:
fragment=new Appuntamenti_fissati_Fragment();
                break;
            case 1:

                break;
            case 2:
fragment=new RichiestaAppuntamentoFragment();
                break;
            default:

                break;

        }
        if(fragment!=null){
            Intent intent=getIntent();
            String email_logopedista= intent.getStringExtra("EMAIL_LOGOPEDISTA");

            Bundle bundle=new Bundle();
            bundle.putString("EMAIL_LOGOPEDISTA",email_logopedista);
            fragment.setArguments(bundle);
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction transaction=fragmentManager.beginTransaction();
            transaction.replace(R.id.FragmentContainer,fragment);
            transaction.commit();

        }






    }



}

