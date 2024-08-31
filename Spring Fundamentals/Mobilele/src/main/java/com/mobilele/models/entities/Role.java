package com.mobilele.models.entities;

import com.mobilele.models.enums.Roles;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Role extends Base {

    @Column
    @Enumerated(value = EnumType.STRING)
    private Roles name;
}
