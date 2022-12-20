package netology.service;

import netology.exception.NotFoundException;
import netology.model.Post;
import netology.repository.PostRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;

public class PostService {
    private final PostRepositoryImpl repository;

    public PostService(PostRepositoryImpl repository) {
        this.repository = repository;
    }

    public List<Post> all() {
        return repository.all();
    }

    public Post getById(long id) {
        return repository.getById(id).orElseThrow(NotFoundException::new);
    }

    public Post save(Post post) {
        final Post postToReturn = repository.save(post);
        if (postToReturn != null) {
            return post;
        } else {
            throw new NotFoundException("Was not able save or update the post");
        }
    }

    public void removeById(long id) {
        try {
            repository.removeById(id);
        } catch (NotFoundException e) {
            throw new NotFoundException();
        }
    }
}