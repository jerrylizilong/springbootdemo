# springbootdemo


# 本项目用以记录学习springboot 开发web 项目的过程

# 项目配置和 hello World 接口
 ## 1. 使用 spring intializr 生成项目： https://start.spring.io/
- 填写相关信息
- 注意选择 dependency ： web
 - 点击”Generate Project“按钮生成项目，下载对应代码包   即springboot.zip

 ## 2. idea 中导入项目

 - 在 idea 中 File –> New –> Project from Existing Sources… ，选择解压的目录进行导入，并导入依赖和插件。

注：maven 添加 阿里云镜像进行加速：在 .m2/settings.xml mirrors 目录添加新的镜像：
```
  <mirrors>
      <mirror>
        <id>nexus-aliyun</id>
        <mirrorOf>*</mirrorOf>
        <name>Nexus aliyun</name>
        <url>http://maven.aliyun.com/nexus/content/groups/public</url>
    </mirror>
```

## 3. 编写helloworld

- 新建类 HelloController：
```
@RestController
public class HelloController{
    @RequestMapping("/hello")
    public String index(){
        return "hello word!";
    }

}
```
- 启动项目： SpringbootApplication
- 访问路径： http://localhost:8080/hello， 可以看到页面返回 helloworld

注: 如何修改默认端口
在 resources/application.properties  文件中加入：
```
server.port=8083
```



# restful 接口开发

## 以user 管理为例，设计查询列表、新增、编辑、删除等接口

## 1.创建 User 类，包含 id、name、age 三个属性。并添加对应的 set 和 get 方法

```
package com.jerrytest.springboot;

public class User {
    private Long id;
    private String name;
    private Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }
}

```

## 2.编写对应的 UserController 方法，映射对应路由并实现对应方法（为简单起见，所有请求替换为 get 和post 格式）

```Java
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

    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    public String putUser(@PathVariable Long id, @ModelAttribute User user){
        // 使用 @ModelAttribute绑定user 对象，使用@RequestParam从页面中传递参数
        User u=users.get(id);
        u.setName(user.getName());
        u.setAge(user.getAge());
        users.put(id,u);
        return "success";
    }

}


```

## 3. 访问 http://localhost:8083/users/ 可以查询到对应数据
