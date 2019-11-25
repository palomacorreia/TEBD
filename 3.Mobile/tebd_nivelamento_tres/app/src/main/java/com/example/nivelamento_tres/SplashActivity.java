package com.example.nivelamento_tres;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nivelamento_tres.bean.Disciplina;
import com.example.nivelamento_tres.database.DisciplinaDAO;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SplashActivity extends Activity {

    //	Splash screen	timer
    private static int SPLASH_TIME_OUT = 3000;
    private TextView textView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        textView = (TextView) this.findViewById(R.id.textView);
        textView.setText(R.string.aguarde_carregando);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new JSONParse().execute();
            }
        }, SPLASH_TIME_OUT);
    }

    public class JSONParse extends AsyncTask<String, String, JSONObject> {
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
        protected JSONObject doInBackground(String... args) {
            JSONObject json = null;
            DisciplinaDAO disciplinaDAO = new DisciplinaDAO(SplashActivity.this);
            disciplinaDAO.dropAll();
            JSONArray link = null;
            json = Json();
            try {
                link = json.getJSONArray("Lista");
                for (int i = 0; i < link.length(); i++) {
                    JSONObject c = link.getJSONObject(i);
                    Disciplina disciplinaValue = new Disciplina();
                    disciplinaValue.setDisciplina(c.getString("disciplina"));
                    disciplinaDAO.salvar(disciplinaValue);
                    disciplinaDAO.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                pDialog.dismiss();
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            } catch (Exception e) {
            }
        }
    }

    public static JSONObject Json() {
        JSONObject json = null;
        String resp = null;
        try {
            URL url1 = new URL("http://www.ictios.com.br/emjorge/appfaculdade/" + "index1.php");
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            InputStream inputStream = conn.getInputStream();
            resp = IOUtils.toString(inputStream);
            json = new JSONObject(resp);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}