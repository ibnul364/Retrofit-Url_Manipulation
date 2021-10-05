package com.example.retrofiturlmanipulation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.retrofiturlmanipulation.model.Comment;
import com.example.retrofiturlmanipulation.model.Post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView resultView;
    private JsonPlaceHolderApi api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultView = findViewById(R.id.text_view_result);

        Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://jsonplaceholder.typicode.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

         api = retrofit.create(JsonPlaceHolderApi.class);

        //getCommentPosts();
        getPosts();


    }

    private void getPosts() {

        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId","1");
        parameters.put("_sort","2");
        parameters.put("_order","asc");

        Call<List<Post>> call = api.getPosts(parameters);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if(!response.isSuccessful()){
                    resultView.setText("Code:" + response.code());
                    return;
                }

                List<Post> posts = response.body();

                for(Post post : posts){
                    String content ="";
                    content += "ID: " +post.getId() + "\n";
                    content += "USER ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() + "\n\n";

                    resultView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                resultView.setText(t.getMessage());
            }
        });
    }

    private void getCommentPosts() {

        Call<List<Comment>> call = api.getComments(5);

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(!response.isSuccessful()){
                    resultView.setText("Code:" + response.code());
                    return;
                }

                List<Comment> comments = response.body();
                for(Comment comment : comments){
                    String content ="";
                    content += "Post ID: " +comment.getPostId() + "\n";
                    content += "ID: " +comment.getId() + "\n";
                    content += "Name: " +comment.getName() + "\n";
                    content += "Email: " +comment.getEmail() + "\n";
                    content += "Text: " + comment.getText() + "\n\n";

                    resultView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                resultView.setText(t.getMessage());

            }
        });
    }
}