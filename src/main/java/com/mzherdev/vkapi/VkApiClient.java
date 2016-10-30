package com.mzherdev.vkapi;

import com.mzherdev.vkapi.model.Group;
import com.mzherdev.vkapi.model.Post;
import com.mzherdev.vkapi.model.User;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by macuser on 26.10.16.
 */
public final class VkApiClient {

    private VkApiClient() {}

    // replace CLIENT_ID value with client_id of your vk-web-application
    public static final String CLIENT_ID = "5686719";
    // replace SECURE_KEY value with client_id of your vk-web-application
    public static final String SECURE_KEY = "Ws0JIbSdRB0gxTmMg0d2";
    public static final String SCOPE = "notify,status,groups,stats,offline";
    public static final String REDIRECT_URI = "http://localhost:8080/vk/login";
    public static final String DISPLAY = "popup";
    public static final String RESPONSE_TYPE = "code";
    public static final String V = "5.57";

    public static final String AUTH_URL = "http://oauth.vk.com/authorize?" +
            "client_id=" + CLIENT_ID +
            "&scope=" + SCOPE +
            "&redirect_uri=" + REDIRECT_URI +
            "&display=" + DISPLAY +
            "&response_type=" + RESPONSE_TYPE +
            "&v=" + V;

    public static final String TOKEN_URL = "https://oauth.vk.com/access_token?" +
            "client_id=" + CLIENT_ID +
            "&client_secret=" + SECURE_KEY +
            "&redirect_uri=" + REDIRECT_URI +
            "&code=%s";

    public static final String GET_USER_INFO_URL = "https://api.vk.com/method/users.get?access_token=%s";

    public static final String GET_GROUPS_URL = "groups.get?extended=1";

    public static final String SEARCH_GROUP_URL = "groups.search?q=%s";

    public static final String WALL_INFO_URL = "wall.get?owner_id=-%s";

    public static final String METHOD_URL = "https://api.vk.com/method/%s&access_token=%s";


    public static String getAccessToken(String code) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(String.format(TOKEN_URL, code));
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);

            InputStream inputStream = response.getEntity().getContent();
            String res = "";
            while (inputStream.available() > 0) {
                res += (char) inputStream.read();
            }
            inputStream.close();

            JSONObject jsonObject = new JSONObject(res);
            String accessToken = jsonObject.getString("access_token");
            return accessToken;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static User getAccountInfo(String accessToken) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(String.format(GET_USER_INFO_URL, accessToken));
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            String res = readData(response);

            JSONObject jsonObject = new JSONObject(res);
            JSONArray array = jsonObject.getJSONArray("response");
            JSONObject jsonObj = array.getJSONObject(0);

            Long id = jsonObj.getLong("uid");
            String name = jsonObj.getString("first_name");
            String lastName = jsonObj.getString("last_name");

            User user = new User(id, name, lastName);
            return user;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Group> getGroups(String accessToken) {
        List<Group> groups = new ArrayList<Group>();

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(String.format(METHOD_URL, GET_GROUPS_URL, accessToken));
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            String res = readData(response);

            JSONObject jsonObject = new JSONObject(res);
            JSONArray array = jsonObject.getJSONArray("response");
            for(int i = 1; i < array.length(); i++) {
                JSONObject jsonObj = array.getJSONObject(i);

                Long id = jsonObj.getLong("gid");
                String name = jsonObj.getString("name");
                String link = "https://vk.com/" + jsonObj.getString("screen_name");
                boolean subscribed = jsonObj.getInt("is_member") == 1;
                String photo = jsonObj.getString("photo_medium");

                Group group = new Group(id, name, subscribed, link, photo);
                groups.add(group);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return groups;
    }

    public static List<Post> getPosts(Long groupId, String accessToken) {
        List<Post> posts = new ArrayList<Post>();

        String wallUrl = String.format(WALL_INFO_URL, String.valueOf(groupId));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(String.format(METHOD_URL, wallUrl, accessToken));

        System.out.println(String.format(METHOD_URL, wallUrl, accessToken));
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            String res = readData(response);

            JSONObject jsonObject = new JSONObject(res);
            JSONArray array = jsonObject.getJSONArray("response");
            for(int i = 1; i < array.length(); i++) {
                JSONObject jsonObj = array.getJSONObject(i);
                Long id = jsonObj.getLong("id");
                String text = jsonObj.getString("text");
                Date date = Date.from(Instant.ofEpochSecond(jsonObj.getLong("date")));
                Post post = new Post();
                post.setId(id);
                post.setText(text);
                post.setDate(date);
                posts.add(post);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return posts;
    }

    public static Group addGroup(String query, String accessToken) {
        String searchUrl = String.format(SEARCH_GROUP_URL, query);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(String.format(METHOD_URL, searchUrl, accessToken));

        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            String res = readData(response);

            JSONObject jsonObject = new JSONObject(res);
            JSONArray array = jsonObject.getJSONArray("response");
            for(int i = 1; i < array.length(); i++) {
                JSONObject jsonObj = array.getJSONObject(i);

                Long id = jsonObj.getLong("gid");
                String name = jsonObj.getString("name");
                String link = "https://vk.com/" + jsonObj.getString("screen_name");
                boolean subscribed = jsonObj.getInt("is_member") == 1;
                String photo = jsonObj.getString("photo_medium");

                Group group = new Group(id, name, subscribed, link, photo);
                return group;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String readData(CloseableHttpResponse response) throws IOException {
        InputStream inputStream = response.getEntity().getContent();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String res = "";
        String line;
//        while (inputStream.available() > 0) {
//            res += (char) inputStream.read();
//        }
        while ((line = bufferedReader.readLine()) != null)
            res += line;
        inputStream.close();
        return res;
    }
}
