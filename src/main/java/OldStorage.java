import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class OldStorage {
    public static final String BASE_URL = "http://localhost:8080//oldStorage/files/";
    public static int successDelete;
    public static int failedDelete;


    public static String[] getFiles() throws IOException, InterruptedException {
        String response = HttpClientFaulty.get(BASE_URL);
        if (response == null) {
            throw new RuntimeException("Failed to get file list");
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response, String[].class);
    }
    public static String getFileContent(String fileName) throws IOException, InterruptedException {
        return HttpClientFaulty.get(BASE_URL + fileName);
    }
    public static void deleteFile(String fileName) throws IOException, InterruptedException {
        if (HttpClientFaulty.delete(BASE_URL + fileName)){
            successDelete++;
        } else {
            System.out.println("Unable to delete " + fileName);
            failedDelete++;
        }
    }
}
