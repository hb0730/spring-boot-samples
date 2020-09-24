package com.hb0730.datasource.export.services;

import com.hb0730.datasource.export.services.inter.TableServiceInter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * table test  class
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TableServiceImplTest {

    @Autowired
    private TableServiceInter service;

    @Test
    public void listTable() {
    }

    @Test
    public void listTableColumn() {
    }
}
