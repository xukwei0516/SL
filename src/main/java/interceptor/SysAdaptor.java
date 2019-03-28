package interceptor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SysAdaptor implements WebMvcConfigurer {

    @Autowired
    ApplicationContext ac;
    /**
     * 添加拦截的路径
     * /为根路径
     * /*为一级路径
     * /** 为所有路径包括多级
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry ) {

        //添加自定义拦截器
        InterceptorRegistration interceptor = registry.addInterceptor(ac.getBean("sysInterceptor",SysInterceptor.class));
        //添加拦截路径
        interceptor.addPathPatterns("/**");
       /* interceptor.addPathPatterns("/backend/**");
        interceptor.addPathPatterns("/informanage/**");
        interceptor.addPathPatterns("/member/**");
        interceptor.addPathPatterns("/message/**");*/

        //排除不拦截的
        interceptor.excludePathPatterns("/login.html");
        interceptor.excludePathPatterns("/main.html");
        interceptor.excludePathPatterns("/statics/**");
        interceptor.excludePathPatterns("/index.html");

    }
}
