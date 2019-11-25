package com.example.nivelamento_tres;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nivelamento_tres.bean.Disciplina;
import com.example.nivelamento_tres.database.DisciplinaDAO;

public class DisciplinaActivity extends AppCompatActivity {

    protected EditText editTextDisciplin;
    protected Disciplina disciplinaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplina);

        Button button =	(Button) findViewById(R.id.botao);

        this.editTextDisciplin	=	(EditText)	findViewById(R.id.disciplina);
        this.editTextDisciplin.setText("teste1");
        Intent intent =	getIntent();
        disciplinaSelecionada =	(Disciplina) intent.getSerializableExtra("disciplinaSelecionada");

        if(disciplinaSelecionada!=null){
            button.setText("Alterar");
            editTextDisciplin.setText(disciplinaSelecionada.getDisciplina());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisciplinaDAO dao =	new DisciplinaDAO(DisciplinaActivity.this);
                if(disciplinaSelecionada==null)	{
                    Disciplina disciplina	= new Disciplina();
                    disciplina.setDisciplina(editTextDisciplin.getText().toString());
                    dao.salvar(disciplina);
                }else{
                    disciplinaSelecionada.setDisciplina(editTextDisciplin.getText().toString());
                    dao.alterar(disciplinaSelecionada);
                }
                finish();
            }
        });
    }
}
