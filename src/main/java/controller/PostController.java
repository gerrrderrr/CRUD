package controller;

import com.google.gson.Gson;
import exception.NotFoundException;
import model.Post;
import service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    public void all(HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final List<Post> data = service.all();
        if (data.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            final var gson = new Gson();
            response.getWriter().print(gson.toJson(data));
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    public void getById(long id, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        Post post = null;
        try {
            post = service.getById(id);
        } catch (NotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        if (post != null) {
            final Gson gson = new Gson();
            response.getWriter().print(gson.toJson(post));
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    public void save(Reader body, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final Gson gson = new Gson();
        final Post post = gson.fromJson(body, Post.class);
        Post data = null;
        try {
            data = service.save(post);
        } catch (NotFoundException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        if (data != null) {
            response.getWriter().print(gson.toJson(data));
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    public void removeById(long id, HttpServletResponse response) {
        boolean deleted = true;
        try {
            service.removeById(id);
        } catch (NotFoundException e) {
            deleted = false;
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        if (deleted) {
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}