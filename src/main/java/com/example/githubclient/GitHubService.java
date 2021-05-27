package com.example.githubclient;

import com.example.githubclient.model.CommitNode;
import com.example.githubclient.model.IssueComment;
import com.example.githubclient.model.Pull;
import com.example.githubclient.model.ReviewComment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GitHubService {

    private final GitHubClient client;

    public GitHubService(GitHubClient client) {
        this.client = client;
    }//клон клиента
    //каждая функция( отправка, получение, отправка и получение)

    public void sendTitleMessage(String owner, String repo) throws IOException {// делает и то и то

        String titles = client.getUserRepoPulls(owner, repo).stream()// все пул реквесты
                .map(Pull::getTitle)//от каждого пул реквеста берем заголовок, напишем их через заголовок
                .collect(Collectors.joining(", "));

        client.createPullIssue(owner, repo, 1, titles);
    }

    public void sendVerificationMessage(String owner, String repo, int number) throws IOException {//проаерка заголовка пул реквеста, что написано правильно


//кис кис кис женщина сюрприз
        List<CommitNode> commitList = client.getCommitNodes(owner, repo, number).stream()//берем список коммитов
                .sorted(Comparator.comparing(CommitNode::getDate))
                .collect(Collectors.toList());

        List<IssueComment> issueCommentList = client.getPullIssue(owner, repo, number).stream()
                .sorted(Comparator.comparing(IssueComment::getCreationDate))
                .collect(Collectors.toList());

        List<ReviewComment> reviewCommentList = client.getPullReview(owner, repo, number).stream()
                .sorted(Comparator.comparing(ReviewComment::getCreationDate))
                .collect(Collectors.toList());
//берем все
        String message = MessageTemplateVerifier.buildMessage(issueCommentList, reviewCommentList, commitList);//отправляем в мессендж (на проаерку)

        if (!message.isEmpty()) { //проверка на пустоту
            client.createPullIssue(owner, repo, number, message);
        }
    }

    public List<Pull> getPulls(String owner, String repo) throws IOException {
        List<Pull> list = client.getUserRepoPulls(owner, repo);
        list.forEach(x -> x.setTitle("Hello " + x.getTitle()));
        return list;

    }

    public List<CommitNode> getCommitNode(String owner, String repo, int number) throws IOException {
        List<CommitNode> list = client.getCommitNodes(owner, repo, number);
        list.forEach(x -> x.setSha(new StringBuilder(x.getSha()).reverse().toString()));
        return list;
    }

    public List<IssueComment> getIssues(String owner, String repo, int number) throws IOException {
        List<IssueComment> list = client.getPullIssue(owner, repo, number);
        list.forEach(x -> x.setBody("NICE " + x.getBody()));
        return list;
    }

    public List<ReviewComment> getReviews(String owner, String repo, int number) throws IOException {
        List<ReviewComment> list = client.getPullReview(owner, repo, number);
        list.forEach(x -> x.setBody("NICE " + x.getBody()));
        return list;
    }
}
