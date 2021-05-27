package com.example.githubclient;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class VerifierExecutor {

    private final GitHubService service;

    String owner = "kasatkovich";
    String repo = "java_au";
    int number = 20;

    public VerifierExecutor(GitHubService service) {
        this.service = service;
    }

    @Scheduled(cron = "*/5 * * ? * *")
    public void verify() {
        System.out.println("Влад Котов, где ТЗ? Ты же обещал");
    }

    @Scheduled(cron = "*/10 * * ? * *") //каждые 10 секунд пытаемся отправить сообщение
    public void sendTestMessage() throws IOException {
        service.sendVerificationMessage(owner, repo, number);
    }
}
