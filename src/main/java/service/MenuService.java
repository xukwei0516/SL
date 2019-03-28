package service;

import com.alibaba.fastjson.JSONObject;
import common.RedisAPI;
import model.Authority;
import model.Function;
import model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    private RedisAPI redisAPI;

    @Autowired
    private FunctionService functionService;


    /**
     * 根据登录用户的roleId返回可以访问菜单(主菜单和子菜单)
     *
     * 把List<Menu>放入redis中
     * @param roleId
     * @return
     */
    public String makeMenus(int roleId) throws Exception{

        List<Menu> list = new ArrayList<>();
        Authority authority = new Authority();
        authority.setRoleId(roleId);

        List<Function> mainFuns = functionService.getMainFunctionList(authority);

        for(Function mainFun:mainFuns){
            Menu menu = new Menu();
            menu.setMainFunction(mainFun);//主菜单
            Function function = new Function();
            function.setRoleId(roleId);
            function.setParentId(mainFun.getId());
            menu.setSubsFunction(functionService.getSubFunctionList(function));//子菜单
            list.add(menu);
        }

        //放redis缓存,key=Menu+roleid; value=Json类型的List<Menu>
        String val = JSONObject.toJSONString(list);
        redisAPI.set("Menu"+roleId, val);

        return val;

    }

    /**
     * 根据登录用户的roleId返回可以访问功能
     * 放入redis中
     * 把List<Functions>放入redis中
     * @param roleId
     * @return
     */
    public List<Function> makeFunctions(int roleId) throws Exception{

        Authority authority = new Authority();
        authority.setRoleId(roleId);
        List<Function> list = functionService.getFunctionListByRoId(authority);

        //放入redis缓存,key=fun+rolid;value=String类型的Url
        StringBuffer stringBuffer = new StringBuffer();
        for(Function function:list){
            stringBuffer.append(function.getFuncUrl());
        }
        String val = stringBuffer.toString();
        redisAPI.set("Fun"+roleId, val);
        return list;
    }



}
