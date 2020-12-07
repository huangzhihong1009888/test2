package com.jiguang.test.service.impl;

import com.jiguang.test.dao.JpaTestDao;
import com.jiguang.test.entity.JpaEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Optional;

@Service
public class JpaService {
    @Resource
    private JpaTestDao jpaTestDao;

    public Object findByName(String name) throws SQLException {


        return jpaTestDao.findByName(name);
    }

    public Object add(String name) {
        JpaEntity jpaEntity = new JpaEntity();
        jpaEntity.setName(name);
        jpaEntity.setTenantId("6000");
        return jpaTestDao.save(jpaEntity);
    }

    public void del(Long id) {
         jpaTestDao.deleteById(id);
    }

    public Object update(String name) {
        Optional<JpaEntity> optional = jpaTestDao.findById(1L);
        optional.get().setName(name);
        return jpaTestDao.save(optional.get());
    }
}
