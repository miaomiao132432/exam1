import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AIPaintingEngine {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public static final String AI_URL = "https://api.replicate.com/v1/predictions";
    public static final String AI_VERSION = "27b93a2413e7f36cd83da926f3656280b2931564ff050bf9575f1fdf9bcd7478";
    public static final String AUTH_STRING = "Token 14fba1fb8f7e41ba87c8e3ebed52cafd27b03416";

    OkHttpClient client = new OkHttpClient();

    String getPaintingId(Response response) throws IOException {
        String responseData = response.body().string();
        JSONObject jsonObject = new JSONObject(responseData);
        return jsonObject.getString("id");
    }

    String getPaintingURL(Response response) throws IOException {
        String responseData = response.body().string();
        JSONObject jsonObject = new JSONObject(responseData);
        String status = jsonObject.getString("status");
        if (status.equals("succeeded")) {
            JSONArray jsonArray = jsonObject.getJSONArray("output");
            return jsonArray.get(0).toString();
        }
        return "";
    }

    Request buildPaintingReq(String prompt) {
        JSONObject jo = new JSONObject();
        jo.put("version", AI_VERSION);
        JSONObject po = new JSONObject();
        po.put("prompt", prompt);
        jo.put("input", po);

        RequestBody body = RequestBody.create(jo.toString(), JSON);
        Request request = new Request.Builder()
                .url(AI_URL)
                .addHeader("Authorization", AUTH_STRING)
                .post(body)
                .build();
        return request;
    }

    Request buildGetPaintingReq(String pid) {
        Request request = new Request.Builder()
                .url(AI_URL + "/" + pid)
                .addHeader("Authorization", AUTH_STRING)
                .build();
        return request;
    }

    String paint(String prompt) throws IOException {
        Request request = buildPaintingReq(prompt);
        try (Response response = client.newCall(request).execute()) {
            return getPaintingId(response);
        }
    }

    String get(String pid) throws IOException {
        Request request = buildGetPaintingReq(pid);
        try (Response response = client.newCall(request).execute()) {
            return getPaintingURL(response);
        }
    }

    public static void main(String args[]) {
        AIPaintingEngine engine = new AIPaintingEngine();
        try {
            String id = engine.paint("a cat on Mars");
            System.out.println(id);
            String path = engine.get(id);
            System.out.println(path);
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            path = engine.get(id);
            System.out.println(path);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
