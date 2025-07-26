package springlesson.Post;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostService {
    private final RestTemplate restTemplate;
    private final PostDataService postDataService;


    @Value("${api.jsonplaceholder}")
    private String api;

    public PostService(RestTemplate restTemplate, PostDataService postDataService) {
        this.restTemplate = restTemplate;
        this.postDataService = postDataService;
    }

    public PostModel save(PostModel post) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<PostModel> entity = new HttpEntity<>(post,headers);
        PostModel result = restTemplate.postForObject(api+"/posts", entity, PostModel.class);
        return result;
    }
    public PostModel update(Long id, PostModel post) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<PostModel> entity = new HttpEntity<>(post,headers);
        PostModel result = restTemplate.exchange(api+"/posts/"+id,HttpMethod.PUT, entity, PostModel.class).getBody();
        return result;
    }
    public List<PostModel> getByParam(Long postId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<List<PostModel>> entity = new HttpEntity<>(headers);
        String urlTemplete = UriComponentsBuilder.fromHttpUrl(api+"/comments")
                .queryParam("postId","{postId}")
                .encode()
                .toUriString();
        Map<String, Object> params = new HashMap<>();
        params.put("postId",postId);
        List<PostModel> result = restTemplate.exchange(urlTemplete,HttpMethod.GET, entity, List.class,params).getBody();
        return result;
    }
    public List<PostModel> comment(Long id, PostModel post) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<PostModel> entity = new HttpEntity<>(post,headers);
        List<PostModel> result = restTemplate.exchange(api+"/posts/"+id+"/comments",HttpMethod.GET, entity, List.class).getBody();
        return result;
    }

    public Map<String, Object> findAll(Long id, PostModel post) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<PostModel[]> entity = new HttpEntity<PostModel[]>(headers);
        PostModel[] result = restTemplate.exchange(this.api+"/posts",HttpMethod.GET,entity, PostModel[].class).getBody();
        postDataService.convertToMap(result);

        Map<String, Object> response = new HashMap<>();
        response.put("posts", result);
        return response;
    }
    public Page<PostDataEntity> findAll(Pageable pageable){
        return postDataService.findAll(pageable);

    }

}