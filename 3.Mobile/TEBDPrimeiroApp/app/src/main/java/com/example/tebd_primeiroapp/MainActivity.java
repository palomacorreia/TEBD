package com.example.tebd_primeiroapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)	{
        //	Handle ac4on	bar	item	clicks	here.	The	ac4on	bar	will
        //	automa4cally handle	clicks	on the	Home/Up buHon,	so long
        //	as	you specify	a	parent ac4vity	in	AndroidManifest.xml.
        int	id	=	item.getItemId();
        //noinspec4on SimplifiableIfStatement
        if	(id	==	R.id.action_senngs)	{
            Log.i("TAG",	"Senngs");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public	boolean onCreateOptionsMenu(Menu menu)	{
        //	Inflate	the	menu;	this	adds	items	to	the	ac4on	bar	if	it	is	present.
        getMenuInflater().inflate(R.menu.menu_ola_mundo,	menu);
        return	true;
    }
}
