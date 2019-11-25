package com.example.tebd_atividadei_iii;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    protected EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState)	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b	=	(Button)	findViewById(R.id.button2);
        this.editText	=	(EditText)	findViewById(R.id.editText);
        Toast.makeText(this,	"TEXTO",	Toast.LENGTH_LONG).show();

        b.setOnClickListener(new	View.OnClickListener()	{
            @Override
            public void onClick(View	v)	{
                Log.i("TAG",	"Botao	Clicado");
                Toast.makeText(MainActivity.this,	"CLICOU!",	Toast.LENGTH_LONG).show();
                editText.setText("Lucas ||Paloma||Talita");
            }
        });
    }
}
