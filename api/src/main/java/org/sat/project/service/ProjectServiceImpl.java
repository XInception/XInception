package org.xinc.project.service;

import org.xinc.auth.entity.User;
import org.xinc.project.api.dto.ProjectForm;
import org.xinc.project.api.dto.ProjectQueryForm;
import org.xinc.project.api.dto.ProjectRemoveForm;
import org.xinc.project.entity.Project;
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
