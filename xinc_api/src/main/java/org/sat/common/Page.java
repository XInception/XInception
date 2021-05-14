package org.xinc.common;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Page<T extends BaseDto> {
    Integer pageSize = 1;
    Long total = 0L;
    List<T> list = new ArrayList<>();
}
