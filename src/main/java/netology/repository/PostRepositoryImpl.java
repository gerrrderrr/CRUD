package netology.repository;

import netology.exception.NotFoundException;
import netology.model.Post;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepositoryImpl implements PostRepository {
    private static final Map<Long, Post> SAVED_POSTS = new ConcurrentHashMap<>();
    private static final AtomicLong POST_COUNTER = new AtomicLong();

    @Override
    public List<Post> all() {
        return SAVED_POSTS.values().stream().toList();
    }

    @Override
    public Optional<Post> getById(long id) {
        if (SAVED_POSTS.containsKey(id)) {
            return Optional.of(SAVED_POSTS.get(id));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Post save(Post post) {
        final long id = post.getId();
        if (id == 0) {
            final long newId = POST_COUNTER.addAndGet(1);
            post.setId(newId);
            SAVED_POSTS.put(newId, post);
            return SAVED_POSTS.get(newId);
        } else if (SAVED_POSTS.containsKey(id)) {
            SAVED_POSTS.replace(id, post);
            return SAVED_POSTS.get(id);
        } else {
            return null;
        }
    }

    @Override
    public void removeById(long id) {
        if (!SAVED_POSTS.containsKey(id)) {
            throw new NotFoundException("No Post to remove");
        } else {
            SAVED_POSTS.remove(id);
        }
    }
}