package com.example.sc_sac;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.example.sc_sac.bean.Artigo;
import com.example.sc_sac.database.SacDataSource;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {

    //	Splash screen	timer
    private static int SPLASH_TIME_OUT = 5000;
    private TextView textView = null;
    private SacDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        textView = (TextView) this.findViewById(R.id.textView);
        textView.setText(R.string.aguarde_carregando);
        dataSource = new SacDataSource(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new JSONParse().execute();
            }
        }, SPLASH_TIME_OUT);
    }

    public class JSONParse extends AsyncTask<String, String, JSONArray> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SplashActivity.this);
            pDialog.setMessage(getResources().getString(R.string.obtendo_dados));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONArray doInBackground(String... args) {
            JSONArray json = null;
            dataSource.deleteUsuario();
            dataSource.deleteArtigo();
            dataSource.deleteArtigoAutor();

            json = Json();
            try {

                for(int i=0;i<json.length();i++){
                    JSONObject c = json.getJSONObject(i);
                    JSONObject artigo = c.getJSONObject("_id");
                    Artigo artigo1 = dataSource.newArtigo();
                    artigo1.setArtigoId(artigo.getInt("_artigo_id"));
                    artigo1.setArtigoNome(artigo.getString("artigo_titulo"));
                    artigo1.setArtigoResumo(artigo.getString("artigo_resumo"));
                    artigo1.setArtigoQuantidadeRevisores(artigo.getInt("arquivo_qtd_revisores"));
                    dataSource.save(artigo1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(JSONArray json) {
            try {
                pDialog.dismiss();
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public JSONArray Json() {
        JSONArray json = null;
        String resp = null;
        try {
            URL url1 = new URL(" http://tebd-edu-br.umbler.net/artigos");
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            resp = IOUtils.toString(in);
            json = new JSONArray(resp);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
