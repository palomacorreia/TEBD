package com.example.nivelamento_tres;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nivelamento_tres.bean.Disciplina;
import com.example.nivelamento_tres.database.DisciplinaDAO;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    protected ListView listView;
    protected Disciplina disciplina;
    protected ArrayAdapter<Disciplina> adapter;
    private  DisciplinaDAO disciplinaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);

        disciplinaDAO = new DisciplinaDAO(this);
        ArrayList<Disciplina> disciplinaArrayList = (ArrayList<Disciplina>) new ArrayList<>(disciplinaDAO.getLista());
        disciplinaDAO.close();

        adapter = new ArrayAdapter<Disciplina>(this,android.R.layout.simple_expandable_list_item_1,disciplinaArrayList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        listView.setOnCreateContextMenuListener(this);
        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(MainActivity.this,"Clicou: "+i, Toast.LENGTH_LONG).show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(	MainActivity.this, adapter.getItem(i).toString(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        disciplinaDAO = new DisciplinaDAO(this);
        ArrayList<Disciplina> disciplinaArrayList =  new ArrayList(disciplinaDAO.getLista());
        disciplinaDAO.close();

        ArrayAdapter<Disciplina> adapter = new ArrayAdapter<Disciplina>(this,android.R.layout.simple_expandable_list_item_1,disciplinaArrayList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem	item)	{

        int	id	=	item.getItemId();
        if	(id	==	R.id.action_new)	{
            Intent intent =	new	Intent(this,	DisciplinaActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int codigo,int reultado,Intent	it) {
        super.onActivityResult(codigo, reultado, it);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)	{
        super.onCreateContextMenu(menu,	v,	menuInfo);
        MenuInflater inflater	=	getMenuInflater();
        inflater.inflate(R.menu.menu_lista_disciplinas,	menu);
    }

    @Override
    public	boolean onCreateOptionsMenu(Menu menu)	{
        getMenuInflater().inflate(R.menu.menu_disciplina,	menu);
        return	true;
    }

    public boolean onContextItemSelected(final	MenuItem	item)	{
        disciplina = (Disciplina) this.adapter.getItem(((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position);
        int	id	=	item.getItemId();
        if	(id	==	R.id.action_new)	{
            Intent intent	=	new	Intent(this,	DisciplinaActivity.class);
            startActivity(intent);
            return true;
        }
        if	(id	==	R.id.action_update)	{
            Intent intent	=	new	Intent(this,	DisciplinaActivity.class);
            intent.putExtra("disciplinaSelecionada",	disciplina);
            startActivity(intent);
            return true;
        }
        if	(id	==	R.id.action_delete)	{
            DisciplinaDAO dao	=	new	DisciplinaDAO(MainActivity.this);
            dao.deletar(disciplina);
            dao.close();
            this.onResume();
            return true;
        }
        return true;
    }
}
