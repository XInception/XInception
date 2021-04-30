package org.sat.auth.api.dto;

import lombok.Data;
import org.sat.common.PageForm;

@Data
public class UserQueryForm extends PageForm {
    Long organizationId;
}
