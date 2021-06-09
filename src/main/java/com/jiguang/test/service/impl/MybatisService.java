package com.jiguang.test.service.impl;

import com.jiguang.test.dao.mybatis.TestMybatisDao;
import com.jiguang.test.entity.JpaEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Slf4j
@Service
public class MybatisService {
    @Resource
    private TestMybatisDao testMybatisDao;


    public JpaEntity findByName(Long name) {


        return testMybatisDao.findByNames(name);
    }
    @Transactional
    public Object add(String name) {
        try {
            JpaEntity jpaEntity = new JpaEntity();
            jpaEntity.setName(name);
            testMybatisDao.save(jpaEntity);
            throw new RuntimeException("c=出错了");
        } catch (Exception e) {
            log.error("-----------------",e.getMessage());
            throw  e;
        }


    }

    public void del(Long id) {
        testMybatisDao.deleteById(id);
    }

    public Object update(String name) {
        testMybatisDao.update(name);
        return null;
    }


}
