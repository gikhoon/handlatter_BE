package handlatter.domain.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.ProtocolFamily;

@Entity
@NoArgsConstructor
@Getter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String nickName ="";
    private String oauthName;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String nickName, String oauthName, Role role) {
        this.nickName = nickName;
        this.oauthName = oauthName;
        this.role = role;
    }

    public Member update(String nickName){
        this.nickName = nickName;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
