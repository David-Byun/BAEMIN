package c.lone.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
    addResourceHandler 스프링에서 확인할 위치를 나타내고,
    addResourceLocations는 실제 시스템의 폴더 위치를 나타내는데 예를 들어 실제 파일은 c:/insta/upload/image.jpg 라면 view에서 이를 불러오기 위해서는 img src="/upload/image.jpg"라고 써줘야함
    MultipartFile file에 저장된 파일 정보를 외부 폴더에 저장하기 위해 utils 패키지 안에 클래스를 추가
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:////Users/david/upload/");
    }
}
