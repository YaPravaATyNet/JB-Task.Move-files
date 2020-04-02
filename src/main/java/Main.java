public class Main {
    public static void main(String[] args) {
        try {
            String[] files = OldStorage.getFiles();
            for (String fileName : files) {
                String fileContent = OldStorage.getFileContent(fileName);
                if (fileContent == null) {
                    System.out.println("Failed to get content of " + fileName);
                    OldStorage.failedDelete++;
                    NewStorage.failedUpload++;
                    continue;
                }
                NewStorage.UploadFile(fileName, fileContent);
                OldStorage.deleteFile(fileName);
                System.out.println(fileName);
            }
            System.out.println("Delete: " + OldStorage.successDelete);
            System.out.println("Upload: " + NewStorage.successUpload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
