package org.sat.common;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
public class PageForm extends BaseForm {
    String words;

    int pageSize;

    int pageNum;

    String sortBy = "id";
    boolean sortDesc = true;


    public Pageable getPage() {
        return PageRequest.of(this.pageNum - 1, this.pageSize, Sort.by(new Sort.Order(this.sortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy)));
    }
}
