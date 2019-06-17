package com.unisociesc.bookfair.service;

import com.unisociesc.bookfair.BookFairApplication;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class FileService {

    private static String FILE_NAME = "data.json";

    public String getFile() throws IOException {
        ClassLoader classLoader = new BookFairApplication().getClass().getClassLoader();
        File file = new File(classLoader.getResource(FileService.FILE_NAME).getFile());
        return new String(Files.readAllBytes(file.toPath()));
    }


}
