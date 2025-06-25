package org.yearup.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;
    private String username;
    @JsonIgnore
    @Column(name = "hashed_password")
    private String password;
    @JsonIgnore
    @Transient
    private boolean activated = true;
    @Column(name = "role")
    private String authority;


    public AppUser(int id, String username, String password, String authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.setAuthorities(authorities);
        this.activated = true;
    }

    @JsonIgnore
    public String getRole() {
        return authority != null ? authority : null;
    }

    public void setAuthorities(String authorities) {
        String[] roles = authorities.split(",");
        for (String role : roles) {
            addRole(role);
        }
    }

    public void addRole(String role) {
        this.authority = role.contains("ROLE_") ? role : "ROLE_" + role;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        AppUser appUser = (AppUser) o;
        return getId() != null && Objects.equals(getId(), appUser.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
