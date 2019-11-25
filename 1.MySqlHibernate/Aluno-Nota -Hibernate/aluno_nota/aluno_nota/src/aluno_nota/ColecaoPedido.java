package aluno_nota;
import java.util.*;
import java.sql.*;


public  class ColecaoPedido{
	
	private static AlunoController con; 
	

//	private NotaController not = new NotaController();
	public static final int ADICIONAR = 1;
	public static final int REMOVER = 2;
	public static final int CONSULTA_MEDIA_ALUNO = 3;
	public static final int CONSULTA_MEDIA_GERAL = 4;
	

	public int criaMenuPrincipal(){
		int opcao;
		System.out.println("Menu de Opcoes:");
		System.out.println("-------------------");
		System.out.println("1. Adicionar Aluno");
		System.out.println("2. Remover Aluno");
		System.out.println("3. Consultar média de um Aluno  ");
		System.out.println("4. Consultar Média Geral");
		System.out.println("-------------------");
		return opcao = Console.readInt();
	}

	public void AdicionarNota(Aluno p) throws Exception {
		String opcao;
		do
		{
			System.out.println("Adicionar Notas:");
			System.out.println("-------------------");
			System.out.print("Código da Nota:");
			int codigo = Console.readInt();
			System.out.print("Nota:");
			double nota = Console.readDouble();
		    con.AdicionarNotaBD(new Nota(codigo,nota,p),p.getMatricula());

			System.out.print("Deseja Adicionar mais uma Nota? [S|N]: ");
			opcao = Console.readLine();
		} while (opcao.compareTo("S") == 0);
	}
		
	public void AdicionarAluno()throws Exception{
		Aluno p;
		String opcao;
		con = new AlunoController();
		do {
			System.out.println("Adicionar Aluno:");
			System.out.println("-------------------");
			System.out.print("Matrícula do Aluno:");
			String matricula = Console.readLine();
			System.out.print("Nome do ALuno:");
			String nome = Console.readLine();
				
			p = new Aluno(matricula, nome);
			con.salvarAluno(p);
			this.AdicionarNota(p);
			
			System.out.print("Deseja Adicionar mais um Aluno? [S|N]: ");
			opcao = Console.readLine();
		} while(opcao.compareTo("S") == 0);
		con.fechar();
	}
	
	public void RemoverAluno()throws Exception{
		int opcao;
		con = new AlunoController();
		System.out.println("Remover Aluno:");
		System.out.println("-------------------");
		System.out.print("Digite a matrícula do aluno:");
		String matricula = Console.readLine();
		con.removerAluno(matricula);
		con.fechar();
	}
	
	public void ConsultarMediaAluno() throws Exception{
		int opcao;
		con = new AlunoController();
		System.out.println("Consultar Aluno:");
		System.out.println("-------------------");
		System.out.print("Digite a matrícula do aluno:");
		String matricula = Console.readLine();
		
		Aluno aluno = con.ConsultarMediaAlunoBD(matricula);
		aluno.somarNota(aluno.getMatricula());
		con.fechar();
	}
	
	public void ConsultarMediaGeral() throws Exception{
		int opcao;
		con = new AlunoController();
		Double media = con.ConsultarMediaGeral();
		System.out.println("Média geral: "+media);
		con.fechar();
		}
	
	public static void main(String args[]){
		
		try {
			ColecaoPedido cp = new ColecaoPedido();
			int opcao;
			while((opcao = cp.criaMenuPrincipal()) != 0){
				if(opcao == ColecaoPedido.ADICIONAR)
					cp.AdicionarAluno();
				else if(opcao == ColecaoPedido.REMOVER)
					cp.RemoverAluno();
				else if(opcao == ColecaoPedido.CONSULTA_MEDIA_ALUNO)
					cp.ConsultarMediaAluno();
				else if(opcao == ColecaoPedido.CONSULTA_MEDIA_GERAL)
					cp.ConsultarMediaGeral();
				else 
					System.out.println("Escolha uma opcao correta.");
			}
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
}