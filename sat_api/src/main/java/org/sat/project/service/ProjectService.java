package org.sat.project.service;

import org.sat.auth.entity.User;
import org.sat.project.api.dto.ProjectForm;
import org.sat.project.api.dto.ProjectQueryForm;
import org.sat.project.api.dto.ProjectRemoveForm;
import org.sat.project.entity.Project;
import org.springframework.data.domain.Page;

public interface ProjectService {
    Object newProject(ProjectForm form);

    Page<Project> getProjectList(ProjectQueryForm query);

    Object queryProjectDetail(Long id, User userInfo);

    Object removeProject(ProjectRemoveForm form, User userInfo);
}
