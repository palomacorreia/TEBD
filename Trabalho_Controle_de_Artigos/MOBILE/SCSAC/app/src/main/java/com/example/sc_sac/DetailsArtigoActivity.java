package com.example.sc_sac;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.sc_sac.bean.Artigo;
import com.example.sc_sac.database.SacDataSource;

public class DetailsArtigoActivity extends AppCompatActivity {

    public final static String ARTIGO_ID = "artigo_id";
    private SacDataSource dataSource;
    private Artigo artigo;
    private String autores;
    private TextView title_artigo;
    private TextView resumo_artigo;
    private TextView autores_artigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_artigp);

        dataSource = new SacDataSource(this);
        Integer artigo_id = getIntent().getIntExtra(ARTIGO_ID,0);
        artigo = dataSource.getArtigoById(artigo_id);

        title_artigo = findViewById(R.id.title_artigo);
        resumo_artigo = findViewById(R.id.resumo_artigo);

        title_artigo.setText(artigo.getArtigoNome());
        resumo_artigo.setText(artigo.getArtigoResumo());
    }
}
