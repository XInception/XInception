package org.sat.project.entity;


import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.sat.auth.entity.User;
import org.sat.common.Base;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@SQLDelete(sql = "update `project` SET deleted_at =  unix_timestamp(now()) WHERE id = ?")
@Entity
@Table(name = "project")
@Where(clause = "deleted_at is null")
@DynamicUpdate
@Cacheable
public class Project extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    String description;
}
