package org.xinc.project.api.dto;

import lombok.Data;
import org.xinc.common.BaseDto;

@Data
public class ProjectListDto implements BaseDto {
    Long id;
    String name;
    String description;
}
