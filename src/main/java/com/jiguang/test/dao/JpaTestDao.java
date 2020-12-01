package com.jiguang.test.dao;

import com.jiguang.test.entity.JpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface JpaTestDao extends JpaRepository<JpaEntity, Long> {
    List<JpaEntity> findByName(String name);
}
