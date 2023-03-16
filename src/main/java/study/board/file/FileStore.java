package study.board.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public String storeFile(MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String originalFilename = multipartFile.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            String ext = extracted(originalFilename);

            String storeFileName = uuid + "." + ext;

            multipartFile.transferTo(new File(getFullPath(storeFileName)));

            return storeFileName;
        }
        throw new MultipartException("multipartFile is Null");
    }



    private String extracted(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    public void deleteFile(String image) {
        if(image != null){
            String substring = image.substring(1, image.length() - 1);
            String[] filenames = substring.split(", ");
            for (String filename : filenames) {
                System.out.println("filename = " + filename);
                File file = new File(fileDir + filename);
                boolean delete = file.delete();
            }
        }
    }

}
