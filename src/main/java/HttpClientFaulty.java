import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.util.EntityUtils;
import java.io.IOException;

public class HttpClientFaulty {
    public static final int MAX_RETRY = 5;

    public static String get(String URL) throws IOException, InterruptedException {
        Request request = Request.Get(URL);
        return doRequest(request);
    }

    public static boolean postFile(String URL, String fileName, String fileContent) throws IOException, InterruptedException {
        HttpEntity entity = MultipartEntityBuilder.create()
                .addBinaryBody("file", fileContent.getBytes(), ContentType.MULTIPART_FORM_DATA, fileName)
                .build();
        Request request = Request.Post(URL).body(entity);
        String response = doRequest(request);
        if (response == null) {
            return false;
        }
        return true;
    }

    public static boolean delete(String URL) throws IOException, InterruptedException {
        Request request = Request.Delete(URL);
        String response = doRequest(request);
        if (response == null) {
            return false;
        }
        return true;
    }

    public static String doRequest(Request request) throws IOException, InterruptedException {
        for (int attempt = 0; attempt < MAX_RETRY; attempt++) {
            HttpResponse response = request.execute().returnResponse();
            if (response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() < 300) {
                return EntityUtils.toString(response.getEntity());
            }
            Thread.sleep(2000);
        }
        return null;
    }
}
