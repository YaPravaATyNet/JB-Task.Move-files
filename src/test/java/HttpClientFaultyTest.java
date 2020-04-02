import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.localserver.LocalTestServer;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class HttpClientFaultyTest {
    private LocalTestServer server = new LocalTestServer(null, null);

    @Before
    public void setUp() throws Exception {
        server.start();
        server.register("/ok", new HttpRequestHandler() {
            public void handle(HttpRequest httpRequest, HttpResponse httpResponse, HttpContext httpContext) throws IOException {
                httpResponse.setStatusCode(200);
                httpResponse.setEntity(new StringEntity("Content"));
            }
        });
        server.register("/wrong", new HttpRequestHandler() {
            public void handle(HttpRequest httpRequest, HttpResponse httpResponse, HttpContext httpContext) throws IOException {
                httpResponse.setStatusCode(500);
                httpResponse.setEntity(new StringEntity("Content"));
            }
        });
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void get() throws IOException, InterruptedException {
        String response = HttpClientFaulty.get("http:/" + server.getServiceAddress().toString() + "/ok");
        assertEquals("Content", response);
        response = HttpClientFaulty.get("http:/" + server.getServiceAddress().toString() + "/wrong");
        assertNull(response);
    }

    @Test
    public void postFile() throws IOException, InterruptedException {
        boolean response = HttpClientFaulty.postFile("http:/" + server.getServiceAddress().toString() + "/ok", "fileName", "fileContent");
        assertTrue(response);
        response = HttpClientFaulty.postFile("http:/" + server.getServiceAddress().toString() + "/wrong", "fileName", "fileContent");
        assertFalse(response);
    }

    @Test
    public void delete() throws IOException, InterruptedException {
        boolean response = HttpClientFaulty.delete("http:/" + server.getServiceAddress().toString() + "/ok");
        assertTrue(response);
        response = HttpClientFaulty.delete("http:/" + server.getServiceAddress().toString() + "/wrong");
        assertFalse(response);
    }
}
