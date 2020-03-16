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
    private TextView media_artigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_artigp);

        dataSource = new SacDataSource(this);
        Integer artigo_id = getIntent().getIntExtra(ARTIGO_ID,0);
        artigo = dataSource.getArtigoById(artigo_id);
        autores = dataSource.getUsuByArtigo(artigo_id);

        title_artigo = findViewById(R.id.title_artigo);
        resumo_artigo = findViewById(R.id.resumo_artigo);
        autores_artigo = findViewById(R.id.autores_artigo);
        media_artigo = findViewById(R.id.media_artigo);

        title_artigo.setText(artigo.getArtigoNome());
        resumo_artigo.setText(artigo.getArtigoResumo());
        autores_artigo.setText(autores);
        media_artigo.setText("Média: " +artigo.getArtigoMedia().toString());
    }
}
