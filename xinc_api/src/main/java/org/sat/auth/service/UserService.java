package org.xinc.auth.service;

import lombok.extern.slf4j.Slf4j;
import org.xinc.auth.api.dto.UserForm;
import org.xinc.auth.api.dto.UserQueryForm;
import org.xinc.auth.entity.User;
import org.xinc.auth.repo.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    private ApplicationEventPublisher ep;

    @Autowired
    UserRepository userRepository;


    public Object queryUser(String uuid) {
        return null;
    }

    public Page<User> getUserList(UserQueryForm query) {
        Specification<User> specification = (Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(query.getWords())) {
                list.add(criteriaBuilder.or(
                        //名称
                        criteriaBuilder.like(root.get("name").as(String.class), "%" + query.getWords() + "%"),
                        //备注
                        criteriaBuilder.like(root.get("description").as(String.class), "%" + query.getWords() + "%")
                ));
            }
            Predicate[] p = new Predicate[list.size()];
            criteriaQuery.where(criteriaBuilder.and(list.toArray(p)));
            return null;
        };
        return userRepository.findAll(specification, query.getPage());
    }

    public Object removeUser(Long id) {
        userRepository.findById(id).ifPresent(user -> {
            userRepository.delete(user);
        });
        return true;
    }

    public Object saveUser(UserForm userForm) {
        User user = new User();
        BeanUtils.copyProperties(userForm,user);
        user.setIsActive(false);
        userRepository.save(user);
        return true;
    }
}
