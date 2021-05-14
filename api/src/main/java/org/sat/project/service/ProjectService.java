package org.xinc.project.service;

import org.xinc.auth.entity.User;
import org.xinc.project.api.dto.ProjectForm;
import org.xinc.project.api.dto.ProjectQueryForm;
import org.xinc.project.api.dto.ProjectRemoveForm;
import org.xinc.project.entity.Project;
import org.springframework.data.domain.Page;

public interface ProjectService {
    Object newProject(ProjectForm form);

    Page<Project> getProjectList(ProjectQueryForm query);

    Object queryProjectDetail(Long id, User userInfo);

    Object removeProject(ProjectRemoveForm form, User userInfo);
}
