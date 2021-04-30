package org.sat.api.entity.user;


import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.sat.api.entity.Base;

import javax.persistence.*;

@Data
@SQLDelete(sql = "update `user` SET deleted_at =  unix_timestamp(now()) WHERE id = ?")
@Entity
@Table(name = "user")
@Where(clause = "deleted_at is null")
@DynamicUpdate
@Cacheable
public class User extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String uuid;

    String name;

    String description;

    String account;

    String password;

    String email;

    String phone;

    Boolean isActive;
    /**
     * 用户微信openid
     */
    String openId;

}
