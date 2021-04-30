package org.sat.project.api.dto;

import lombok.Data;
import org.sat.common.BaseDto;

@Data
public class ProjectListDto implements BaseDto {
    Long id;
    String name;
    String description;
}
