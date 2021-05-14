package org.xinc.auth.api.dto;

import lombok.Data;
import org.xinc.common.PageForm;

@Data
public class UserQueryForm extends PageForm {
    Long organizationId;
}
