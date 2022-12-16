package servlet;

import controller.PostController;
import repository.PostRepository;
import service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
    private PostController controller;
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String DELETE = "DELETE";
    private static final String PATH = "/api/posts";
    private static final String REGEX = "/\\d+";

    @Override
    public void init() {
        final PostRepository repository = new PostRepository();
        final PostService service = new PostService(repository);
        controller = new PostController(service);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        try {
            final String path = request.getRequestURI();
            final String method = request.getMethod();
            if (method.equals(GET) && path.equals(PATH)) {
                controller.all(response);
                return;
            }
            if (method.equals(GET) && path.matches(PATH + REGEX)) {
                final long id = getId(path);
                controller.getById(id, response);
                return;
            }
            if (method.equals(POST) && path.equals(PATH)) {
                controller.save(request.getReader(), response);
                return;
            }
            if (method.equals(DELETE) && path.matches(PATH + REGEX)) {
                final long id = getId(path);
                controller.removeById(id, response);
                return;
            }
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private long getId(String path) {
        return Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
    }
}