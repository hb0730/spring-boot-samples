package com.hb0730.datasource.export.export;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EasyPoiExportTest {

    @Test
    public void export() throws IOException {
        EasyPoiExport.export();
    }

    @Test
    public void getData() {
    }
}
