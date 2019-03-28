package starter;


import interceptor.SysAdaptor;
import interceptor.SysInterceptor;
import interceptor.WelcomeViewAdaptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@SpringBootApplication(scanBasePackages = {"controller", "service","common"})
@MapperScan(basePackages = {"mapper"})//扫描mapper接口
@ImportAutoConfiguration(classes = {SysAdaptor.class,WelcomeViewAdaptor.class})//引入分支config
public class SpringConfig {


    /**
     * 文件上传配置
     * @return
     */
    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(1000000);
        multipartResolver.setDefaultEncoding("UTF-8");
        return multipartResolver;
    }

    /**
     * 生成自定义拦截器
     * @return
     */
    @Bean(name = "sysInterceptor")
    public SysInterceptor createInterceptor(){

        return new SysInterceptor();
    }


}
