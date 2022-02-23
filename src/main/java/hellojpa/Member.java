package hellojpa;

import javax.persistence.*;

@Entity
public class Member {
    @Id @GeneratedValue
    private Long id;

    @Column(name = "USERNAME")
    private String username;

//    @Column(name = "TEAM_ID")
//    private Long teamId;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

    /**
     * 연관관계 편의 메서드
     * 반대쪽 세팅을 주인에서 해준다.
     * 일단 단방향 매핑만으로 설계한다.
     * @param team
     */
    public void putTeam(Team team) {
        this.team = team;
        team.getMembers().add(this); // 객체지향점으로 봤을 때는 주인이 아닌쪽에도 값을 넣어주어야 함
    }
}
