package interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Constants;
import common.RedisAPI;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class SysInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisAPI redisAPI;



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        User sessionUser = (User)request.getSession(false).getAttribute(Constants.SESSION_USER);
        if(sessionUser==null){
            request.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(request,response );
        }

        String allowedUrl = redisAPI.get("Fun"+sessionUser.getRoleId());
        //访问路径
        String requestUrl = request.getRequestURI();

        if(allowedUrl.indexOf(requestUrl)>0){
            return true;
        }else{
            request.getRequestDispatcher("/WEB-INF/pages/401.jsp").forward(request,response );
            return false;
        }
    }

}
