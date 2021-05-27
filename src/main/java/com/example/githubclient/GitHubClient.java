package com.example.githubclient;

import com.example.githubclient.model.CommitNode;
import com.example.githubclient.model.IssueComment;
import com.example.githubclient.model.Pull;
import com.example.githubclient.model.ReviewComment;
import org.springframework.stereotype.Service;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
public class GitHubClient {

    static final String API_BASE_URL = "https://api.github.com/"; //константы(сслыка)
    static final String API_VERSION_SPEC = "application/vnd.github.v3+json";//версия
    static final String JSON_CONTENT_TYPE = "application/json";//то, что ожидаем

    private final String accessToken;//это понятно (биекция очевидна Вазгену)

    private final GitHubApiInterface service; //наш интефрейс сервиса(ссылки и тд)
//конструктор клиента
    public GitHubClient() throws IOException {
        Retrofit retrofit = new Retrofit.Builder() //ретрофит - подгаталивает запросы к вызову(из интерфейса)
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(GitHubApiInterface.class);//идет связка между запросыми и сервисом ретрофита
        BufferedReader br = new BufferedReader(new FileReader("src/roken"));
        this.accessToken = "token " + br.readLine(); // System.getenv("ACCESS_TOKEN");
        br.close();
    }

    public List<Pull> getUserRepoPulls(String owner, String repo) throws IOException { //сами методы

        Call<List<Pull>> retrofitCall = service.listUserRepoPulls(accessToken, API_VERSION_SPEC, owner, repo);//забивает параметры в вызов(хедер и тд)
        Response<List<Pull>> response = retrofitCall.execute();//отправка запроса и получение ответа

        if (!response.isSuccessful()) { //проверка на получение результата
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }

        return response.body(); //возвращение тела ответа
    }

    public List<CommitNode> getCommitNodes(String owner, String repo, int number) throws IOException {
        Call<List<CommitNode>> retrofitCall = service.listCommits(accessToken, API_VERSION_SPEC, owner, repo, number);

        Response<List<CommitNode>> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }


        return response.body();
    }

    public List<ReviewComment> getPullReview(String owner, String repo, int number) throws IOException {
        Call<List<ReviewComment>> retrofitCall = service.listReviewComments(accessToken, API_VERSION_SPEC, owner, repo, number);

        Response<List<ReviewComment>> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }


        return response.body();
    }

    public List<IssueComment> getPullIssue(String owner, String repo, int number) throws IOException {
        Call<List<IssueComment>> retrofitCall = service.listIssueComments(accessToken, API_VERSION_SPEC, owner, repo, number);

        Response<List<IssueComment>> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }


        return response.body();
    }

    public ReviewComment createPullReview(ReviewComment reviewComment, String owner, String repo, int number) throws IOException { //создаем тело еще
        Call<ReviewComment> retrofitCall = service.createReviewComment(reviewComment, accessToken, API_VERSION_SPEC, JSON_CONTENT_TYPE, owner, repo, number);

        Response<ReviewComment> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }

        return response.body();
    }

    public IssueComment createPullIssue(String owner, String repo, int number, String message) throws IOException {
        IssueComment comm = new IssueComment();
        comm.setBody(message);
        Call<IssueComment> retrofitCall = service.createIssueComment(comm, accessToken, API_VERSION_SPEC, JSON_CONTENT_TYPE, owner, repo, number);

        Response<IssueComment> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }

        return response.body();
    }

}


