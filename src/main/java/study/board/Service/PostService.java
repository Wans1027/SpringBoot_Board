package study.board.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import study.board.entity.MainText;
import study.board.entity.Posts;
import study.board.file.FileStore;
import study.board.repository.MainTextRepository;
import study.board.repository.PostsRepository;

import java.util.List;
import java.util.Optional;



@Service
@RequiredArgsConstructor
public class PostService {

    private final PostsRepository postsRepository;
    private final MainTextRepository mainTextRepository;

    private final FileStore fileStore;

    public Posts save(Posts posts){
        return postsRepository.save(posts);
    }

    public Page<Posts> loadAllPostsPageable(Pageable pageable){
        return postsRepository.findAll(pageable);
    }

    public List<Posts> findAllPosts(){
        return postsRepository.findAll();
    }

    public void deleteImages(Long postId){
        //게시글 id로 해당 게시글을 찾는다
        Optional<MainText> mainText = mainTextRepository.findById(postId);
        //찾은 게시글에서 이미지를 가져온다
        String image = mainText.orElseThrow().getImage();
        //파일삭제
        fileStore.deleteFile(image);
    }


}
