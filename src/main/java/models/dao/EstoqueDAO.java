package dao;

import jakarta.persistence.*;
import models.Estoque;
import java.util.List;

public class EstoqueDAO {

    private EntityManagerFactory emf;

    public EstoqueDAO() {
        this.emf = Persistence.createEntityManagerFactory("PU");
    }

    public void salvar(Estoque roupa) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(roupa);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Estoque> listarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("FROM Estoque", Estoque.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Estoque buscarPorId(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Estoque.class, id);
        } finally {
            em.close();
        }
    }

    public void atualizar(Estoque roupa) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(roupa);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void remover(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Estoque roupa = em.find(Estoque.class, id);
            if (roupa != null) {
                em.getTransaction().begin();
                em.remove(roupa);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
    }
}