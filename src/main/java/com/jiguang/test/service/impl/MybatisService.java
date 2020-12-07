package com.jiguang.test.service.impl;

import com.jiguang.test.dao.mybatis.TestMybatisDao;
import com.jiguang.test.entity.JpaEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

@Service
public class MybatisService {
    @Resource
    private TestMybatisDao testMybatisDao;

    public JpaEntity findByName(Long name) {


        return testMybatisDao.findByNames(name);
    }

    public Object add(String name) {
        JpaEntity jpaEntity = new JpaEntity();
        jpaEntity.setName(name);
        testMybatisDao.save(jpaEntity);
        return null;
    }

    public void del(Long id) {
        testMybatisDao.deleteById(id);
    }

    public Object update(String name) {
        testMybatisDao.update(name);
        return null;
    }
}
