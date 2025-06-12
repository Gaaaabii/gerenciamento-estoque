package testes;

import jakarta.persistence.*;
import models.Estoque;
import models.Usuario;

public class TesteCriaTabela {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Usuario u = new Usuario();
        u.setNome("Gabrielle");
        u.setEmail("gabriellestieler@gmail.com");
        u.setLogin("gabrielle");
        u.setSenha("1234567");

        em.persist(u);

//        Estoque er = new Estoque();
//        er.setNome("Camisa Polo");
//        er.setQuantidade(10);
//        er.setPrecoUnitario(25.99);
//        er.setCategoria("Camisa");
//        er.setCor("Preto");
//        er.setTamanho("P");

//        em.persist(er);

        em.getTransaction().commit();
        em.close();
        emf.close();

        System.out.println("Item salvo com sucesso!");
    }
}
