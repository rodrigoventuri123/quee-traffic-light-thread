package com.unisociesc.bookfair;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unisociesc.bookfair.domain.Genre;
import com.unisociesc.bookfair.domain.School;
import com.unisociesc.bookfair.service.FileService;
import com.unisociesc.bookfair.service.JsonUtilService;
import com.unisociesc.bookfair.service.ProcessService;
import com.unisociesc.bookfair.domain.StaticData;
import com.unisociesc.bookfair.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;

@SpringBootApplication
public class BookFairApplication implements ApplicationRunner {

    @Autowired
    private FileService fileService;

    @Autowired
    private JsonUtilService jsonUtilService;

    @Autowired
    private ProcessService processService;

    @Autowired
    private QueueService queueService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Começando o processamento.");

        String contentFile = fileService.getFile();

        JsonObject jsonObject = new JsonParser().parse(contentFile).getAsJsonObject();

        List<Genre> genreList = this.jsonUtilService.convertJsonInListGenre(jsonObject.getAsJsonArray("generos"));

        List<School> schoolList = this.jsonUtilService.convertJsonInListSchool(jsonObject.getAsJsonArray("escolas"));

        for (School school: schoolList) {
            this.queueService.addQueue(school);
        }

        StaticData.SCHOOLS.addAll(schoolList);
        StaticData.GENRES.addAll(genreList);

        processService.process();
    }

    private void setConfigThread(ThreadPoolTaskExecutor executor) {
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(3);
        executor.initialize();
    }

    @Bean(name = "attendant1")
    public Executor asyncExecutor1() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        this.setConfigThread(executor);
        return executor;
    }

    @Bean(name = "attendant2")
    public Executor asyncExecutor2() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        this.setConfigThread(executor);
        return executor;
    }

    public static void main(String[] args) throws IOException {
        SpringApplication.run(BookFairApplication.class, args);
    }

}
