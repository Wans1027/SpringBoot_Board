package study.board.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import study.board.entity.Posts;
import study.board.repository.PostsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostsRepository postsRepository;

    public Posts save(Posts posts){
        return postsRepository.save(posts);
    }

    public Page<Posts> loadAllPostsPageable(Pageable pageable){
        return postsRepository.findAll(pageable);
    }

    public List<Posts> findAllPosts(){
        return postsRepository.findAll();
    }
}
