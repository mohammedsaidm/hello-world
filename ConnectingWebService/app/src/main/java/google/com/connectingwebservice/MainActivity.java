package google.com.connectingwebservice;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView contentTv;
    private EditText id,title,content,idToDelete ,idToUpdate,newTitle,newContent;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contentTv=findViewById(R.id.content_tv);
        id=findViewById(R.id.id);
        title=findViewById(R.id.title);
        content=findViewById(R.id.content);
        idToDelete=findViewById(R.id.idToDelete);
        idToUpdate=findViewById(R.id.idToUpdate);
        newTitle=findViewById(R.id.newTitle);
        newContent=findViewById(R.id.newContent);
    }

    public void loadData(View view) {
        //https://jsonplaceholder.typicode.com/posts
        //for virtual device http://10.0.2.2:8181/api/
        //for phone http://192.168.0.142:8181/api/
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:8181/api/").addConverterFactory(GsonConverterFactory.create()).build();
        PlugService plugService = retrofit.create(PlugService.class);

        plugService.getPosts().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    contentTv.setText(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Error :( ",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadIdData(View view) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:8181/api/").addConverterFactory(GsonConverterFactory.create()).build();
        PlugService plugService = retrofit.create(PlugService.class);

        plugService.getPost(id.getText().toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    contentTv.setText(response.body().string());
                } catch (IOException e) {
                    Toast.makeText(MainActivity.this,"Error :( no data here",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Error :( ",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void postData(View view) {
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://10.0.2.2:8181/api/").addConverterFactory(GsonConverterFactory.create()).build();
        PlugService plugService=retrofit.create(PlugService.class);

        Map<String,String> body=new HashMap<>();
        body.put("title",title.getText().toString());
        body.put("content",content.getText().toString());

        plugService.postData(body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    contentTv.setText(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Error :( ",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteIdData(View view) {
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://10.0.2.2:8181/api/").addConverterFactory(GsonConverterFactory.create()).build();
        PlugService plugService=retrofit.create(PlugService.class);

        plugService.deletePost(idToDelete.getText().toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    contentTv.setText(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Error :( ",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateData(View view) {
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://10.0.2.2:8181/api/").addConverterFactory(GsonConverterFactory.create()).build();
        PlugService plugService=retrofit.create(PlugService.class);

        Map<String,String> newBody=new HashMap<>();
        newBody.put("title",newTitle.getText().toString());
        newBody.put("content",newContent.getText().toString());
        plugService.updatePost(idToUpdate.getText().toString(),newBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    contentTv.setText(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Error :( ",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void notifiyButton(View view) {
        addNotification();
    }
    private void getNumberOfRows(){
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://10.0.2.2:8181/api/notes/").addConverterFactory(GsonConverterFactory.create()).build();
        PlugService plugService=retrofit.create(PlugService.class);


        plugService.getNumberOfRows();
    }
    private void addNotification() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        final int HELLO_ID = 1;
        scheduler.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit=new Retrofit.Builder().baseUrl("http://10.0.2.2:8181/api/notes/").addConverterFactory(GsonConverterFactory.create()).build();
                PlugService plugService=retrofit.create(PlugService.class);


                plugService.getNumberOfRows().enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        NotificationCompat.Builder builder =
                                null;
                        try {
                            builder = new NotificationCompat.Builder(MainActivity.this)
                                    .setSmallIcon(R.drawable.ic_announcement)
                                    .setContentTitle("Notification")
                                    .setContentText("number of rows is : "+response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Intent notificationIntent = new Intent(MainActivity.this, MainActivity.class);
                        PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this, 0, notificationIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setContentIntent(contentIntent);

                        // Add as notification
                        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        manager.notify(0, builder.build());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(MainActivity.this,"Error :( ",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        },3,3, TimeUnit.SECONDS);
    }

    public void sendButton(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction("google.com.connectingwebservice");
        sendIntent.putExtra("numRows", "سلامو عيكو :) .");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}
