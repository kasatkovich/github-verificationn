package com.example.githubclient;

import com.example.githubclient.model.CommitNode;
import com.example.githubclient.model.IssueComment;
import com.example.githubclient.model.Pull;
import com.example.githubclient.model.ReviewComment;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.when;

public class GitHubServiceTest extends AbstractGithubClientTest { //екстендэ от тестов

    String owner = "OWNER";
    String repo = "REPO";
    int number = 8800; //переменные для тестов

    GitHubService service; //экземпляр сервиса, которые устаналивает связь с гитом
    GitHubClient mock; //мокирование гитхаб(подмена настоящего гитхаба нашим экзамепляром)

    @Before//то,что выполянется перед каждым тестом
    public void init() {//ну собственно говоря да
        mock = Mockito.mock(GitHubClient.class, RETURNS_DEEP_STUBS);
        service = new GitHubService(mock);
    }

    @Test//тест
    public void testGetPulls() throws IOException {
        Pull pull = new Pull(); //создание нового экземпляра
        pull.setTitle("Test Pull");//добавляем заголовок
        when(mock.getUserRepoPulls(owner, repo)).thenReturn(Collections.singletonList(pull));//когда пытаемся обратиться к гитхабу, вместо того что на самом деле возрващаем список из одного объекта
        List<Pull> pulls = service.getPulls(owner, repo);//приписываем ему хеллоу
        assertEquals("Hello Test Pull", pulls.get(0).getTitle());//проверяем, что это именно то, что нам надо
    }

    @Test
    public void testGetCommits() throws IOException {
        String testString = "ILOVEWINX";
        CommitNode node = new CommitNode();
        node.setSha(testString);
        when(mock.getCommitNodes(owner, repo, number)).thenReturn(Collections.singletonList(node));
        List<CommitNode> commitNodes = service.getCommitNode(owner, repo, number);
        assertEquals(new StringBuilder(testString).reverse().toString(), commitNodes.get(0).getSha());
    }

    @Test
    public void testGetIssue() throws IOException {
        IssueComment comment = new IssueComment();
        comment.setBody("ASS ASS ASS");
        when(mock.getPullIssue(owner, repo, number)).thenReturn(Collections.singletonList(comment));
        List<IssueComment> issues = service.getIssues(owner, repo, number);
        assertEquals("GOOD ASS ASS ASS", issues.get(0).getBody());
    }

    @Test
    public void testGetReview() throws IOException {
        ReviewComment comment = new ReviewComment();
        comment.setBody("BODY BODY ASS");
        when(mock.getPullReview(owner, repo, number)).thenReturn(Collections.singletonList(comment));
        List<ReviewComment> reviews = service.getReviews(owner, repo, number);
        assertEquals("NICE BODY BODY ASS", reviews.get(0).getBody());
    }



}
