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

            Team team =  new Team();
            team.setName("TeamA");
            em.persist(team);

            System.out.println("============1");

            Member member = new Member();
//            member.setTeamId(team.getId()); // 테이블 중심
            member.setUsername("member1");
            member.putTeam(team); // 연관관계 편의 메서드
            em.persist(member);

            System.out.println("==============2");
            em.flush();
            em.clear();
            System.out.println("==============2");

            Member findMember = em.find(Member.class, member.getId());
//            Long findTeamId = findMember.getTeamId(); // 테이블 중심
//            Team findTeam = em.find(Team.class, findTeamId); // 테이블 중심
            Team findTeam = findMember.getTeam();

            System.out.println(member == findMember);
            System.out.println("==============3");

            List<Member> members = findTeam.getMembers();
            for (Member m : members) {
                System.out.println("m = " + m);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();

    }
}
