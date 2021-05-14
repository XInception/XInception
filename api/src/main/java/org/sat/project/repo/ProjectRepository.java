package org.xinc.project.repo;


import org.xinc.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends
        CrudRepository<Project, Long>,
        JpaSpecificationExecutor<Project>,
        JpaRepository<Project, Long> {

    Optional<Project> findFirstByName(String name);
}
