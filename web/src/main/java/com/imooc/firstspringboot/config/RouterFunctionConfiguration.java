package com.imooc.firstspringboot.config;

import com.imooc.firstspringboot.domain.User;
import com.imooc.firstspringboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import java.util.Collection;

/**
 * 路由器函数 配置
 * @author zjxjwxk
 */
@Configuration
public class RouterFunctionConfiguration {

    /**
     * Servlet 请求接口：ServletRequest或者HttpServletRequest
     * 响应接口：ServletResponse或者HttpServletResponse
     * Spring 5.0 重新定义了服务请求和响应接口
     * 请求接口：ServerRequest
     * 响应接口：ServerResponse
     * 即可支持 Servlet规范，也可以支持自定义，比如Netty (Web Server)
     * 以本例：
     * 定义GET请求，并且返回所有的用户对象 URI：/person/find/all
     * Flux是0-N个对象集合
     * Mono是0-1个对象集合
     * Reactive中的Flux或者Mono它是异步处理（非阻塞）
     * 集合对象基本上是同步处理（阻塞）
     * Flux或者Mono都是Publisher
     */

    @Bean
    @Autowired
    public RouterFunction<ServerResponse> personFindAll(UserRepository userRepository) {

        return RouterFunctions.route(RequestPredicates.GET("/person/find/all"),
                request -> {
                    // 返回所有的用户对象
                    Collection<User> users = userRepository.findAll();
                    Flux<User> userFlux = Flux.fromIterable(users);
                    return ServerResponse.ok().body(userFlux, User.class);
                });
    }
}
