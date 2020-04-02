import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class NewStorage {
    public static final String BASE_URL = "http://localhost:8080//newStorage/files";
    public static int successUpload;
    public static int failedUpload;

    public static void UploadFile(String fileName, String fileContent) throws IOException, InterruptedException {
        if (HttpClientFaulty.postFile(BASE_URL, fileName, fileContent)) {
            successUpload++;
        } else {
            System.out.println("Failed to upload " + fileName);
            failedUpload++;
        }
    }

    public static String[] getFiles() throws IOException, InterruptedException {
        String response = HttpClientFaulty.get(BASE_URL);
        if (response == null) {
            System.out.println("БЕБЕБЕ");
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response, String[].class);
    }
}
