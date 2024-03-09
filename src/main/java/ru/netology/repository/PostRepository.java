package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {
    private final AtomicLong counter = new AtomicLong(0);
    private final Map<Long, Post> posts = new ConcurrentHashMap<>();

    public List<Post> all() {
        return Collections.emptyList();
    }

    public Optional<Post> getById(long id) {
        return Optional.of(posts.get(id));
    }

    public Post save(Post post) {
        Post tempPost;
        if (post.getId() == 0) {
            counter.addAndGet(1);
            posts.put(counter.get(), new Post(counter.get(), post.getContent()));
            tempPost = posts.get(counter.get());
        } else if (posts.containsKey(post.getId())) {
            posts.replace(post.getId(), posts.get(post.getId()), post);
            tempPost = post;
        } else {
            throw new NotFoundException("There is no post with specified id");
        }
        return tempPost;
    }

    public void removeById(long id) {
        if (posts.containsKey(id)) {
            posts.remove(id);
        } else {
            throw new NotFoundException("There is no post with specified id");
        }
    }
}