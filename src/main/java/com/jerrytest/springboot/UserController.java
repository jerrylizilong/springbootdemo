package com.jerrytest.springboot;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/users")  // 通过这里配置使下面的映射都在/users下     可以理解为模块级别的目录配置
public class UserController {

    static Map<Long,User> users = Collections.synchronizedMap(new HashMap<Long,User>());   // 创建线程安全的Map   暂时未理解具体原理

    @RequestMapping(value = "/lists",method = RequestMethod.GET)
    public List<User> getUserList(){
        // 处理"/users/lists"的GET请求，用来获取用户列表
         List<User> r = new ArrayList<>(users.values());    // 将user 对象转换为数组对象返回
        return r;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String postUser(@ModelAttribute User user){
        users.put(user.getId(),user);
        return "success";
    }

    @RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
    public User getUser(@PathVariable Long id){
        return users.get(id);
    }


    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    public String putUser(@PathVariable Long id, @ModelAttribute User user){
        // 使用 @ModelAttribute绑定user 对象，使用@RequestParam从页面中传递参数
        User u=users.get(id);
        u.setName(user.getName());
        u.setAge(user.getAge());
        users.put(id,u);
        return "success";
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.POST)
    public String deleteUser(@PathVariable Long id){
        users.remove(id);
        return "success";
    }


}
