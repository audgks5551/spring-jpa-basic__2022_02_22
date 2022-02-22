package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("emf"); // 데이터베이스와 연결됨

        EntityManager em = emf.createEntityManager(); // 개체 매니저 생성 (한 트랜잭션당 하나있어야함)

        EntityTransaction tx = em.getTransaction(); // 트랜잭션 생성



        try {
            tx.begin(); // 트랜잭션 시작

            // entity 생성
            Member member = new Member();
            member.setId(1L);
            member.setName("lee");
            Member member2 = new Member();
            member2.setId(2L);
            member2.setName("kim");

            // 영속성 컨텍스트에 저장
            em.persist(member);
            em.persist(member2);
            em.flush();
            em.clear();

            /////////////////////////////////////////////

            // 전체 조회 쿼리 작성
            List<Member> members = em.createQuery(
                    "select m from Member as m",
                    Member.class
            ).getResultList();

            // 전체 조회 출력
            for (Member m : members) {
                System.out.println(m.getId() + "의 이름 = " + m.getName());
            }

            em.flush();
            em.clear(); // 영속성컨텍스트 비우기

            //////////////////////////////////////////////

            // member id를 통한 데이터 조회 (영속성컨테스트에서 비웠으므로 DB에서 데이터를 가져옴)
            // 데이터베이스 -> 영속성컨텍스트
            Member findMember = em.find(Member.class, member.getId());

            // 저장한 데이터가 잘 저장됬는지 확인
            System.out.println("member.name = " + member.getName());
            System.out.println("findMember.name = " + findMember.getName());

            // 영속성 컨텍스트에 운영중인 객체이므로 setName으로 변경만 해주어도 수정 가능 (수정은 한 트랜잭션안에서만 가능)
            findMember.setName("kim");
            em.flush();
            em.clear(); // 영속성컨텍스트 비우기

            ////////////////////////////////////////////////

            // 다시 불러오기
            // 데이터베이스 -> 영속성컨텍스트
            Member modifyedMember = em.find(Member.class, member.getId());

            // 수정이 잘 되었는지 확인
            System.out.println("member.name = " + member.getName());
            System.out.println("modifyedMember.name = " + modifyedMember.getName());

            // 삭제
            em.remove(modifyedMember);

            tx.commit(); // 영속성컨텍스트 -> 데이터베이스
        } catch (Exception e) {
            tx.rollback(); // 트랜젝션의 처리 과정에서 발생한 변경 사항을 취소하고, 트랜젝션 과정을 종료
        } finally {
            em.close(); // entity manager 반환
        }

        emf.close(); // entity manager factory 반환

    }
}
