package com.jiguang.test.dao.mybatis;

import com.jiguang.test.entity.JpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TestMybatisDao {
    JpaEntity findByNames(Long name);

    void save(JpaEntity jpaEntity);

    void deleteById(Long id);

    void update(String name);
}
