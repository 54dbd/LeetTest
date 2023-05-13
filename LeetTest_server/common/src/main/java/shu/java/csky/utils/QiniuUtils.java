package shu.java.csky.utils;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class QiniuUtils {
    public static final String API_URL = "https://api.imgbb.com/1/upload";

    @Value("fbf8062423ef62eeb02bf17f1a1a0863")
    private String accessSecretKey;

    public String picUrl;

    public String upload(MultipartFile file, String fileName) {
        try {
            byte[] imageData = file.getBytes();
            OkHttpClient client = new OkHttpClient();
            // body include key and image
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("key", accessSecretKey)
                    .addFormDataPart("image", fileName,
                            RequestBody.create(MediaType.parse("image/png"), imageData))
                    .build();

            Request request = new Request.Builder()
                    .url(API_URL)
                    .post(requestBody)
                    .build();

            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            System.out.println(responseBody);
            //{"data":{"id":"WHhxKPv","title":"9f2daa10-6c1d-4766-9352-3ff788a8e3dd","url_viewer":"https:\/\/ibb.co\/WHhxKPv","url":"https:\/\/i.ibb.co\/mRjDzX5\/9f2daa10-6c1d-4766-9352-3ff788a8e3dd.png","display_url":"https:\/\/i.ibb.co\/Fhrx704\/9f2daa10-6c1d-4766-9352-3ff788a8e3dd.png","width":1024,"height":1280,"size":1920213,"time":1683970979,"expiration":0,"image":{"filename":"9f2daa10-6c1d-4766-9352-3ff788a8e3dd.png","name":"9f2daa10-6c1d-4766-9352-3ff788a8e3dd","mime":"image\/png","extension":"png","url":"https:\/\/i.ibb.co\/mRjDzX5\/9f2daa10-6c1d-4766-9352-3ff788a8e3dd.png"},"thumb":{"filename":"9f2daa10-6c1d-4766-9352-3ff788a8e3dd.png","name":"9f2daa10-6c1d-4766-9352-3ff788a8e3dd","mime":"image\/png","extension":"png","url":"https:\/\/i.ibb.co\/WHhxKPv\/9f2daa10-6c1d-4766-9352-3ff788a8e3dd.png"},"medium":{"filename":"9f2daa10-6c1d-4766-9352-3ff788a8e3dd.png","name":"9f2daa10-6c1d-4766-9352-3ff788a8e3dd","mime":"image\/png","extension":"png","url":"https:\/\/i.ibb.co\/Fhrx704\/9f2daa10-6c1d-4766-9352-3ff788a8e3dd.png"},"delete_url":"https:\/\/ibb.co\/WHhxKPv\/6fa6e4de3931c59906eb9a8feeddf915"},"success":true,"status":200}
            String regex = "\"url\":\"(.*?)\"";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(responseBody);
            if (matcher.find()) {
                this.picUrl= matcher.group(1);
                System.out.println(this.picUrl);
            }
            return this.picUrl;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
