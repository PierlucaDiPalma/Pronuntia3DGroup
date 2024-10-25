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
    private DBHelper db;
    private TabLayout tabLayout;
    private FrameLayout frameLayout;

    public void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        FragmentManager fragmentManager = getSupportFragmentManager();
        setContentView(R.layout.appuntamenti_genitore);
        db = new DBHelper(this);


        tabLayout = findViewById(R.id.tabLayout);
        frameLayout = findViewById(R.id.FragmentContainer);

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


    private void LoadFragment(int posizioneTab) {

        Fragment fragment = null;

        switch (posizioneTab) {

            case 0:
                fragment = new Appuntamenti_fissati_Fragment();
                break;
            case 1:
                fragment = new Appuntamenti_passati_fragment();
                break;
            case 2:
                db.popolaTabellaAppuntamenti();
                Intent intent = new Intent(AppuntamentiGenitore.this, SceltaLogopedista.class);
                intent.putExtra("nomeActivity", "AppuntamentoGenitore");
                startActivityForResult(intent, 1);
                return;


            default:

                break;

        }
        if (fragment != null) {
            Intent intent = getIntent();
            String email_logopedista = intent.getStringExtra("EMAIL_LOGOPEDISTA");

            Bundle bundle = new Bundle();
            bundle.putString("EMAIL_LOGOPEDISTA", email_logopedista);
            fragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.FragmentContainer, fragment);
            transaction.commit();

        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String emailLogopedista = data.getStringExtra("EMAIL_LOGOPEDISTA");

            if (emailLogopedista != null) {
                RichiestaAppuntamentoFragment fragment = RichiestaAppuntamentoFragment.newInstance(emailLogopedista);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.FragmentContainer, fragment)
                        .addToBackStack(null)
                        .commit();
            }


        }
    }
}
