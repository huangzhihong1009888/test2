package com.jiguang.test.dao.mybatis;

import com.jiguang.test.entity.JpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TestMybatisDao {
    List<JpaEntity> findByNames(String name);

    Object save(JpaEntity jpaEntity);

    void deleteById(Long id);

    Object update(String name);
}
