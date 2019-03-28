package test;

import com.alibaba.druid.pool.vendor.SybaseExceptionSorter;
import model.Function;
import model.Menu;
import model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import service.MenuService;
import service.UserService;
import starter.SpringConfig;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringConfig.class})
public class MenuTest {

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserService userService;


    @Test
    public void testFindMenu() throws Exception {

        //用户登录
        User user = new User();
        user.setLoginCode("admin");
        user.setPassword("123456");
        User loginUser = userService.getLoginUser(user);
        if (loginUser != null) {
            menuService.makeMenus(loginUser.getRoleId());
        }
    }

    @Test
    public void testFindFunctions() throws Exception{
        User user = new User();
        user.setLoginCode("admin");
        user.setPassword("123456");
        User loginUser = userService.getLoginUser(user);
        List<Function> list = menuService.makeFunctions(loginUser.getRoleId());
        System.out.println(list);
    }
}
