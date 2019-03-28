package interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 设置APP默认欢迎页,该路径会被拦截器拦截,所以拦截器中要放行
 *  相当于web.xml文件中的欢迎页
 */
@Configuration
public class WelcomeViewAdaptor implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //访问/index.html时,直接跳转到index.jsp
        registry.addViewController("/index.html").setViewName("index");

    }
}
