package study.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import study.board.entity.MainText;
import study.board.exhandler.ErrorResult;
import study.board.file.FileStore;
import study.board.repository.MainTextRepository;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ItemController {
    private final FileStore fileStore;
    private final MainTextRepository mainTextRepository;

    @Value("${file.dir}")
    private String fileDir;

    //-- 게시글 첨부파일 받기 --
    @PostMapping("/items/new/{mainTextId}")
    @Transactional
    public void saveImage(@RequestParam("file") List<MultipartFile> form, @PathVariable("mainTextId") Long id) throws IOException {
        List<String> files = new ArrayList<>();

        System.out.println("isEmpty MultipartFile = " + form);
        for (MultipartFile multipartFile : form) {
            files.add(fileStore.storeFile(multipartFile));//저장된 이름을 리스트에 추가
            //System.out.println("multipartFile = " + multipartFile.getOriginalFilename());
        }

        System.out.println("files = " + files);
        //게시글 id로 해당 게시글을 찾는다
        Optional<MainText> mainText = mainTextRepository.findById(id);
        //찾은 게시글에서 이미지를 가져온다
        String image = mainText.orElseThrow().getImage();
        //파일삭제
        fileStore.deleteFile(image);
        //리스트를 바꾼다.
        mainText.orElseThrow().setImage(files.toString());


    }




    //-- 이미지 호출 URI --
    @GetMapping("/items/get/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable("imageName") String imgName) {

        Resource resource = new FileSystemResource(fileDir + imgName);

        // 로컬 서버에 저장된 이미지 파일이 없을 경우
        if (!resource.exists()) {
            return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND); // 리턴 결과 반환 404
        }


        // 로컬 서버에 저장된 이미지가 있는 경우 로직 처리
        HttpHeaders header = new HttpHeaders();
        Path filePath = null;
        try {
            filePath = Paths.get(fileDir + imgName);
            // 인풋으로 들어온 파일명 .png / .jpg 에 맞게 헤더 타입 설정
            header.add("Content-Type", Files.probeContentType(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 이미지 리턴 실시 [브라우저에서 get 주소 확인 가능]
        return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
    }
}
