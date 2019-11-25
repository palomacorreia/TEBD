package aluno_nota;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.function.Predicate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.criteria.SetJoin;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class AlunoController {
	EntityManagerFactory emf;
	EntityManager em;

	public AlunoController() {
		emf = Persistence.createEntityManagerFactory("aluno");
		em= emf.createEntityManager();
	}
	
	
	public void salvarAluno(Aluno aluno){
		em.getTransaction().begin();
		em.persist(aluno);
		em.getTransaction().commit();
	}
	
	
	public void removerAluno(String matricula){
		Aluno aluno = em.find(Aluno.class, matricula);
		System.out.println(aluno.getNome());
		em.getTransaction().begin();
		em.remove(aluno);
		em.getTransaction().commit();
	}
	
	public void AdicionarNotaBD(Nota nota,String matricula)throws Exception{
		Aluno aluno = em.find(Aluno.class, matricula);
		nota.setAluno(aluno);
		em.getTransaction().begin();
		em.persist(nota);
		em.getTransaction().commit();
	}
	
	
	public Aluno ConsultarMediaAlunoBD(String matricula)throws Exception{  

		Aluno aluno = em.find(Aluno.class, matricula);
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Nota> criteria = builder.createQuery(Nota.class);
		Root<Nota> root = criteria.from(Nota.class);
		criteria.where(
		    builder.equal(root.get("aluno"), aluno)
		);
		List<Nota> list =  em.createQuery(criteria).getResultList();
		
		Aluno a = new Aluno();
		a.setMatricula(aluno.getMatricula());
		a.setNome(aluno.getNome());
		for(int i=0;i<list.size();i++) {
			a.addNota(list.get(i).getNota());
		}
		return a;
	}
	
	public Double ConsultarMediaGeral()throws Exception{  
		Double media=0.0,total = 0.0;
		List <Aluno> alunos = em.createQuery(" FROM " + Aluno.class.getName()).getResultList();
		
		for(int i=0;i<alunos.size();i++) {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Nota> criteria = builder.createQuery(Nota.class);
			Root<Nota> root = criteria.from(Nota.class);
			criteria.where(
			    builder.equal(root.get("aluno"), alunos.get(i))
			);
			List<Nota> list =  em.createQuery(criteria).getResultList();
			if(list.size()>0) {
				for(int j=0;j<list.size();j++) {
					total = total + list.get(j).getNota();
				}
				total = total/list.size();
				media = media + total;
				total = 0.0;
			}
		}
		media = media/alunos.size();
		System.out.println(media);
      return media;
	}
	
	public void fechar() {
		emf.close();
	}
}
