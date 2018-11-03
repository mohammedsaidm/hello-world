package google.com.connectingwebservice;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by Mohammed Said on 02-Nov-18.
 */

public interface PlugService {
    @GET("notes")
    Call<ResponseBody> getPosts();

    @GET("notes/{id}")
    Call<ResponseBody> getPost(@Path("id") String id);

    @POST("notes")
    Call<ResponseBody> postData(@Body Map<String,String> data);

    @DELETE("notes/{id}")
    Call<ResponseBody> deletePost(@Path("id")String id);

    @PUT("notes/{id}")
    Call<ResponseBody> updatePost(@Path("id")String id,@Body Map<String,String> data);

    @GET("rowsnumber")
    Call<ResponseBody> getNumberOfRows();
}
