package aluno_nota;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="aluno")
public class Aluno {
	@Id
	@Column(name = "ALU_MATRICULA")
	private String matricula;
	@Column(name = "ALU_NOME")
	private String nome;
	
	@OneToMany(mappedBy = "aluno", targetEntity = Nota.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	 private List<Double> notas;
		
	public Aluno(String matricula, String nome) {
		super();
		this.matricula = matricula;
		this.nome = nome;
		this.notas = new ArrayList<>();
	}
	
	public Aluno() {
		this.notas = new ArrayList<>();
	}

	
	public String toString(){
		return "Matricula: " + this.matricula + " - " + "Nome:" +
		this.nome + "\n";
	}

	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void addNota( Double nota){
		this.notas.add(nota);
	}
	
	public void somarNota(String matricula) {
		Double total = 0.0;
		Double media;
		
		for(int i=0;i<notas.size();i++) {
			total = total+notas.get(i);
		}
		media = Double.valueOf(total/(notas.size()));
		System.out.println("Média do Aluno de matricula : "+matricula+ " : "+media);
	}

}
