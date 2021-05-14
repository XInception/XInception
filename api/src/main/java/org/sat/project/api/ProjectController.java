package org.xinc.project.api;

import org.xinc.auth.service.AuthService;
import org.xinc.common.BaseController;
import org.xinc.common.Page;
import org.xinc.common.ResDto;
import org.xinc.project.api.dto.*;
import org.xinc.project.entity.Project;
import org.xinc.project.service.ProjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project")
public class ProjectController extends BaseController {

    @Autowired
    AuthService authService;

    @Autowired
    ProjectService projectService;

    /**
     * 创建新项目
     * @param form
     * @return
     */
    @PostMapping("/new")
    public ResDto<Boolean> newProject(@RequestBody ProjectForm form) {
        return success(projectService.newProject(form));
    }



    @GetMapping("/list")
    public ResDto<Page<ProjectListDto>> list(ProjectQueryForm query) {
        Page<ProjectListDto> ret = new Page<>();
        org.springframework.data.domain.Page<Project> res = projectService.getProjectList(query);
        res.forEach(s -> {
            ProjectListDto t = new ProjectListDto();
            BeanUtils.copyProperties(s, t);
            ret.getList().add(t);
        });
        return success(ret);
    }

    @GetMapping("/info")
    public ResDto<ProjectDetailForm> query(@RequestParam("id") Long id) {
        return success(projectService.queryProjectDetail(id,authService.getUserInfo()));
    }

    @PostMapping("/remove")
    public ResDto<Boolean> remove(@RequestBody ProjectRemoveForm form) {
        return success(projectService.removeProject(form,authService.getUserInfo()));
    }


}
