package repository;

import exception.NotFoundException;
import model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {
    private static final Map<Long, Post> SAVED_POSTS = new ConcurrentHashMap<>();
    private static final AtomicLong POST_COUNTER = new AtomicLong();

    public List<Post> all() {
        return SAVED_POSTS.values().stream().toList();
    }

    public Optional<Post> getById(long id) {
        if (SAVED_POSTS.containsKey(id)) {
            return Optional.of(SAVED_POSTS.get(id));
        } else {
            return Optional.empty();
        }
    }

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

    public void removeById(long id) {
        if (!SAVED_POSTS.containsKey(id)) {
            throw new NotFoundException("No Post to remove");
        } else {
            SAVED_POSTS.remove(id);
        }
    }
}