package springlesson.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api")

public class PostResource {
    private final PostService postService;

    public PostResource(PostService postService) {
        this.postService = postService;
    }
    @PostMapping("/posts")
            public ResponseEntity create(@RequestBody PostModel post) {
            PostModel result = postService.save(post);
            return ok(result);
    }

    @GetMapping("/posts")
    public ResponseEntity getAll(@RequestBody PostModel post) {
        Map<String, Object> result = postService.findAll(post.getPostId(),post);
        return ok(result);
    }

    @GetMapping("/posts/{id}/comments")
    public List<PostModel> comments(@PathVariable Long id, @RequestBody PostModel post) {
        List<PostModel> result = postService.comment(id, post);
        return result;

    }
    @GetMapping("/posts/pages")
    public ResponseEntity getAllPages(Pageable pageable) {
       Page<PostDataEntity> result = postService.findAll(pageable);
       return ResponseEntity.ok(result);
    }

    @GetMapping("/posts/params")
    public ResponseEntity getParams(@RequestParam Long postId) {
        List<PostModel> result = postService.getByParam(postId);
        return ResponseEntity.ok(result);
    }


    @PutMapping("/posts/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody PostModel post) {
        PostModel result = postService.update(id, post);
        return ok(result);
    }

}
