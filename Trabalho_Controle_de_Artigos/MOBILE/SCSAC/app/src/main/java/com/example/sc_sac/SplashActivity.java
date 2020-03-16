package com.example.sc_sac;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.example.sc_sac.bean.Artigo;
import com.example.sc_sac.bean.ArtigoAutor;
import com.example.sc_sac.bean.Usuario;
import com.example.sc_sac.database.SacDataSource;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.mongodb.lang.NonNull;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.stitch.android.core.auth.StitchUser;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;
import com.mongodb.stitch.core.auth.providers.anonymous.AnonymousCredential;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteUpdateResult;

import org.apache.commons.io.IOUtils;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
            final StitchAppClient client =
                    Stitch.initializeDefaultAppClient("talitaconection-nzmmg");

            final RemoteMongoClient mongoClient =
                    client.getServiceClient(RemoteMongoClient.factory, "mongodb-atlas");

            final RemoteMongoCollection<Document> coll =
                    mongoClient.getDatabase("Cluster0").getCollection("artigo");
            coll.find().first();


            client.getAuth().loginWithCredential(new AnonymousCredential()).addOnSuccessListener(new OnSuccessListener<StitchUser>() {
                @Override
                public void onSuccess(StitchUser stitchUser) {
                    coll.find().first().getResult();
                }
            });
//            client.getAuth().loginWithCredential(new AnonymousCredential()).continueWithTask(
//                    new Continuation<StitchUser, Task<List<Document>>>() {
//
//                        @Override
//                        public Task<List<Document>> then(@NonNull Task<StitchUser> task) throws Exception {
//                            if (!task.isSuccessful()) {
//                                Log.e("STITCH", "Update failed!");
//                                throw task.getException();
//                            }
//                            List<Document> docs = new ArrayList<>();
//                            docs.add(coll.find().first().getResult());
//                            return (Task<List<Document>>) docs;
//                        }
//                    }
//            ).addOnCompleteListener(new OnCompleteListener<List<Document>>() {
//                @Override
//                public void onComplete(@NonNull Task<List<Document>> task) {
//                    if (task.isSuccessful()) {
//                        Log.d("STITCH", "Found docs: " + task.getResult().toString());
//                        return;
//                    }
//                    Log.e("STITCH", "Error: " + task.getException().toString());
//                    task.getException().printStackTrace();
//                }
//            });

//            MongoClientURI uri = new MongoClientURI( "mongodb://talita:1234@cluster0-shard-00-00-qwqoz.mongodb.net:27017,cluster0-shard-00-01-qwqoz.mongodb.net:27017,cluster0-shard-00-02-qwqoz.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true&w=majority");
//            MongoClient mongoClient = new MongoClient(uri);
//            MongoDatabase db = mongoClient.getDatabase("Cluster0");
//            MongoCollection<Document> collection_artigo = db.getCollection("artigo");
//            collection_artigo.find().first();
//            MongoCollection<Document> collection_autor = db.getCollection("usuario");
//            MongoCollection<Document> collection_artigo_autor = db.getCollection("artigo_autor");
//            collection_artigo.count();
            JSONObject json = null;
            dataSource.deleteUsuario();
            dataSource.deleteArtigo();
            dataSource.deleteArtigoAutor();

            JSONArray artigo = null;
            JSONArray autores = null;
            JSONArray artigo_autor = null;
            json = Json();
            try {
                artigo = json.getJSONArray("artigo");
                autores = json.getJSONArray("usuario");
                artigo_autor = json.getJSONArray("artigo_autor");

                for(int i=0;i<artigo.length();i++){
                    JSONObject c = artigo.getJSONObject(i);
                    Artigo artigo1 = dataSource.newArtigo();
                    artigo1.setArtigoId(c.getInt("artigo_id"));
                    artigo1.setArtigoNome(c.getString("artigo_titulo"));
                    artigo1.setArtigoResumo(c.getString("artigo_resumo"));
                    artigo1.setArtigoQuantidadeRevisores(c.getInt("artigo_qtd_revisores"));
                    artigo1.setArtigoMedia(Float.valueOf(c.getString("artigo_media")));
                    artigo1.setArtigoArquivo(c.getString("artigo_arquivo"));
                    dataSource.save(artigo1);
                }

                for(int j=0;j<autores.length();j++){
                    JSONObject c = autores.getJSONObject(j);
                    Usuario usuario = dataSource.newUsuario();
                    usuario.setUsuarioId(c.getInt("usuario_id"));
                    usuario.setUsuarioNome(c.getString("usuario_nome"));
                    usuario.setUsuarioEndereco(c.getString("usuario_endereco"));
                    usuario.setUsuarioTelefone(c.getString("usuario_telefone"));
                    usuario.setUsuarioEmail(c.getString("usuario_email"));
                    usuario.setLocalTrabalho(c.getString("usuario_local_trabalho"));
                    usuario.setRevisor(c.getInt("usuario_is_revisor") == 1 ? true : false);
                    usuario.setAutor( c.getInt("usuario_is_autor") == 1 ? true : false);
                    dataSource.save(usuario);
                }

                for(int l=0;l<artigo_autor.length();l++){
                    JSONObject c = artigo_autor.getJSONObject(l);
                    ArtigoAutor artigoAutor = dataSource.newArtigoAutor();
                    artigoAutor.setArtigoAutorId(c.getInt("artigo_autor_id"));
                    artigoAutor.setUsuarioId(c.getInt("usuario_id"));
                    artigoAutor.setArtigoId(c.getInt("artigo_id"));
                    artigoAutor.setEmail_autor(c.getString("email_autor"));
                    dataSource.save(artigoAutor);
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
                e.printStackTrace();
            }
        }
    }

    public JSONObject Json() {
        JSONObject json = null;
        String resp = null;
        try {
            URL url1 = new URL("https://my-json-server.typicode.com/palomacorreia/artigos/db");
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            resp = IOUtils.toString(in);
            json = new JSONObject(resp);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
