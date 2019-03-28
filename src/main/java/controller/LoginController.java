package controller;
import com.alibaba.fastjson.JSONObject;
import common.Constants;
import common.RedisAPI;
import model.Menu;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.MenuService;
import service.UserService;

import javax.servlet.http.HttpSession;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.logging.LoggingMXBean;

@Controller
public class LoginController {

    private final static Logger logger = LoggerFactory.getLogger(LoginController .class);
    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RedisAPI redisAPI;
    //需织入公告的Service和资讯的Service

    /**
     * 登录成功页面
     * @return
     */
    @RequestMapping("/main.html")
    public String toMain(){

        return "main";
    }


    /**
     * 登录认证
     * @param user
     * @param session
     * @return
     */

    @RequestMapping("/login.html")
    @ResponseBody
    public String login(User user, HttpSession session){

       String json = null;
        try {
            User findUser = userService.getLoginUser(user);
            if(findUser !=null){
                //把用户放入session
                session.setAttribute(Constants.SESSION_USER, findUser);

                //调用makeMenus().......
                String key = "Menu"+findUser.getRoleId();
                boolean flag = redisAPI.exist(key);
                if(flag){
                    //直接从缓存中读取
                    json = redisAPI.get(key);
                }else{
                    json= menuService.makeMenus(findUser.getRoleId());
                }

                //调用makeFunctions().....
                String key2 = "Fun"+findUser.getRoleId();
                boolean flag2 = redisAPI.exist(key2);
                if(!flag2){
                    menuService.makeFunctions(findUser.getRoleId());
                }

                session.setAttribute("menus", json);

                return Constants.LOGIN_SUCCESS;
            }
        } catch (Exception e) {
            logger.error("登录失败", e);
        }

        return Constants.LOGIN_FAILED;
    }

}
