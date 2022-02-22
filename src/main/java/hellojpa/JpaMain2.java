package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain2 {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("emf"); // 데이터베이스와 연결됨

        EntityManager em = emf.createEntityManager(); // 개체 매니저 생성 (한 트랜잭션당 하나있어야함)

        EntityTransaction tx = em.getTransaction(); // 트랜잭션 생성

        tx.begin();

        try {


        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();

    }
}
