package springlesson.Post;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostDataService {
    private final PostDataRepository postDataRepository;

    public PostDataService(PostDataRepository postDataRepository) {
        this.postDataRepository = postDataRepository;
    }

    public PostDataEntity save(PostDataEntity postData) {
        return postDataRepository.save(postData);
    }

    public Map<String, Object> convertToMap(PostModel[] posts) {
        List<Map<String, Object>> mappedPosts = new ArrayList<>();
        for (PostModel post : posts) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", post.getId());
            map.put("userId", post.getUserId());
            map.put("title", post.getTitle());
            map.put("body", post.getBody());
            mappedPosts.add(map);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("posts", mappedPosts);
        return response;
    }
@Transactional(readOnly = true)
    public Page<PostDataEntity> findAll(Pageable pageable) {
        return postDataRepository.findAll(pageable);
    }
    }
