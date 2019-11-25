package aluno_nota;
import  java.sql.*;

public class PedidoBD{
  private BDjdbc conexao;
  public PedidoBD(BDjdbc conexao_){
	  this.conexao = conexao_; 	
  }
		
	public void AdicionarAlunoBD(Aluno aluno)throws Exception{
	  PreparedStatement Stmt;
	  Stmt = conexao.getConexao().prepareStatement(
	  "INSERT INTO ALUNO (ALU_MATRICULA,ALU_NOME) VALUES (?,?)");
	  Stmt.setString(1,aluno.getMatricula());
	  Stmt.setString(2,aluno.getNome());
	  Stmt.executeUpdate();
	  Stmt.close();
	
	}
	public void AdicionarNotaBD(Nota nota,String matricula)throws Exception{
	  PreparedStatement Stmt;
      Stmt = conexao.getConexao().prepareStatement(
      "INSERT INTO ITEM (NOTA_CODIGO,NOTA_NOTA,NOTA_ALU_MATRICULA) values (?,?,?)");
      Stmt.setInt(1,nota.getCodigo());
      Stmt.setDouble(2,nota.getNota());
      Stmt.setString(3,matricula);
      Stmt.executeUpdate();
      Stmt.close();
	}
	
	public void RemoverAlunoBD(String matricula)throws Exception{
	  PreparedStatement Stmt;
      Stmt = conexao.getConexao().prepareStatement(
      "DELETE FROM ALUNO WHERE  ALU_MATRICULA=?");
      Stmt.setString(1,matricula);
      Stmt.executeUpdate();
      Stmt.close();
      conexao.getConexao().commit();
	}
	
	public Aluno ConsultarMediaAlunoBD(String matricula)throws Exception{   
	  PreparedStatement Stmt;
	  ResultSet  rs;
      Stmt = conexao.getConexao().prepareStatement(
      "SELECT NOME FROM ALUNO WHERE ALU_MATRICULA=?");
      Stmt.setString(1,matricula.toString());
      rs = Stmt.executeQuery();
      if (!(rs.next())) return null;
      
      Aluno p = new Aluno(matricula,rs.getString("ALU_NOME"));
      
      Stmt.close();
      rs.close();
      
      Stmt = conexao.getConexao().prepareStatement(
      "SELECT NOTA_CODIGO,NOTA_NOTA FROM NOTA WHERE NOTA_ALU_MATRICULA=?");
      Stmt.setString(1,matricula.toString());
      rs = Stmt.executeQuery();
//      
      
      Stmt.close();
      rs.close();
      //conexao.getConexao().commit();
      return p;
	}
	
}