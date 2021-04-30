package org.sat.project.service;

import org.sat.auth.entity.User;
import org.sat.project.api.dto.ProjectForm;
import org.sat.project.api.dto.ProjectQueryForm;
import org.sat.project.api.dto.ProjectRemoveForm;
import org.sat.project.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Override
    public Object newProject(ProjectForm form) {
        return null;
    }

    @Override
    public Page<Project> getProjectList(ProjectQueryForm query) {
        return null;
    }

    @Override
    public Object queryProjectDetail(Long id, User userInfo) {
        return null;
    }

    @Override
    public Object removeProject(ProjectRemoveForm form, User userInfo) {
        return null;
    }
}
