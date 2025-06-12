package testes;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TesteConexaoJPA {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("taskhubPU");
        EntityManager em = emf.createEntityManager();

        System.out.println("Conexão com o banco de dados realizada com sucesso!");

        em.close();
        emf.close();
    }
}
