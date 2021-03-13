package com.hb0730.spring.boot.file.sample.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author bing_huang
 */
@RestController
public class FileStreamDownloadController {

    @PostMapping("/down")
    public void down(HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        try {
            outputStream.write("测试测试测试".getBytes(StandardCharsets.UTF_8));
            response.setHeader("Content-Disposition", "attachment;filename=test.txt");
            response.setContentType("application/octet-stream; charset=utf-8");
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            outputStream.close();
        }
    }

    @PostMapping("/down2")
    public void down(@RequestParam("path") String path, @RequestParam("name") String name, HttpServletResponse response) throws IOException {
        FileInputStream fileInputStream = null;
        OutputStream outputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            byte[] bytes = readInputStream(fileInputStream);
            outputStream = response.getOutputStream();
            response.addHeader("Content-Disposition", "attachment;filename=" + name);
            response.addHeader("Content-Length", bytes.length + "");
            response.setContentType("application/octet-stream");
            outputStream.write(bytes);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert outputStream != null;
            outputStream.close();
            fileInputStream.close();
        }
    }

    /**
     * 从输入流中获取数据
     *
     * @param fis 输入流
     */
    private byte[] readInputStream(InputStream fis) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = fis.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        fis.close();
        return outStream.toByteArray();
    }
}
