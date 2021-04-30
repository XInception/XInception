package org.sat.common;


import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
@org.hibernate.annotations.TypeDef(name = "json", typeClass = JsonStringType.class)
public class Base {
    Long deletedAt;
    Long createdAt;

    /**
     * 扩展列 动态字段
     */
    @Type(type = "json")
    @Column(columnDefinition = "json")
    Object meta;
}
