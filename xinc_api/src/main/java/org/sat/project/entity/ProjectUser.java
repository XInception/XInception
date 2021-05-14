package org.xinc.project.entity;


import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.xinc.auth.entity.User;
import org.xinc.common.Base;

import javax.persistence.*;
import java.util.Set;

@Data
@SQLDelete(sql = "update `project_user` SET deleted_at =  unix_timestamp(now()) WHERE id = ?")
@Entity
@Table(name = "project_user")
@Where(clause = "deleted_at is null")
@DynamicUpdate
@Cacheable
public class ProjectUser extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "user_id")
    Long userId;

    @Column(name = "project_id")
    Long projectId;
}
