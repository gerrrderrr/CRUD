package netology.config;

import netology.controller.PostController;
import netology.repository.PostRepositoryImpl;
import netology.service.PostService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfig {
    @Bean
    public PostController postController(PostService service) {
        return new PostController(service);
    }

    @Bean
    public PostService postService(PostRepositoryImpl repository) {
        return new PostService(repository);
    }

    @Bean
    public PostRepositoryImpl postRepositoryImpl() {
        return new PostRepositoryImpl();
    }
}
