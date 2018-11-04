package pl.wojtek.system_ksiegowy.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_role")
public class SystemUserRole implements Serializable, Comparable<SystemUserRole>
{
    private static final long serialVersionUID = 13165465465464l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roleName;

    public SystemUserRole() {}

    public SystemUserRole(String roleName) {
        this.roleName = roleName;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return roleName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj instanceof SystemUserRole)
        {
            SystemUserRole role = (SystemUserRole) obj;
            return id == role.id &&
                    roleName.equals(role.roleName);
        }
        return false;
    }

    @Override
    public int compareTo(SystemUserRole o) {
        return id.compareTo(o.getId());
    }
}
