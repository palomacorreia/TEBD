package aluno_nota;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="nota")
public class Nota {
	@Id
	@Column(name = "NOTA_CODIGO")
	private int codigo;
	@Column(name = "NOTA_NOTA")
	private double nota;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="ALU_MATRICULA",insertable=true, updatable=true)
    @Fetch(FetchMode.JOIN)
    @Cascade(CascadeType.SAVE_UPDATE)
	
    private Aluno aluno;
	public Nota(int codigo, double nota,Aluno aluno){
		super();
		this.codigo = codigo;
		this.nota = nota;
		this.aluno = aluno;
	}	

	public Nota(int codigo, double nota,String matricula){
		super();
		this.codigo = codigo;
		this.nota = nota;
		this.aluno = aluno;
	}	
	
	
	public Nota() {
		super();
	}

	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public double getNota() {
		return nota;
	}
	public void setNota(double nota) {
		this.nota = nota;
	}
	
	

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public String toString(){
		return "Codigo: " + this.codigo + " - " + "Nota:" +
		this.nota + "\n";
	}
	
}
