package com.offcn.filter;

import com.offcn.common.utils.JWTUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
@Component
public class InnerFilter extends AbstractGatewayFilterFactory<FilterConf> {

    public InnerFilter() {
        super(FilterConf.class);
    }

    @Override
    public GatewayFilter apply(FilterConf config) {
        return ((exchange, chain) -> {
            //判断过滤器是否执行
            if(config.isIs()){
                //允许执行
                //获取请求对象
                ServerHttpRequest request = exchange.getRequest();
                //获取响应对象
                ServerHttpResponse response = exchange.getResponse();
                //尝试从请求头获取令牌
                String token = request.getHeaders().getFirst("Authorization");
                //判断令牌是否为空
                if(!StringUtils.isEmpty(token)){
                    //调用令牌工具类，校验令牌
                    boolean verify = JWTUtil.verify(token);
                    //判断令牌校验失败
                    if(!verify){
                        //令牌校验失败，结束转发
                        Mono mono = returnFail(response);
                        return  response.writeWith(mono);
                    }else {
                        //令牌校验成功，放行
                        return    chain.filter(exchange);
                    }
                }else {
                    //令牌不存在，结束转发
                    Mono mono = returnFail(response);
                    return  response.writeWith(mono);
                }
            }else {
                //不允许执行
                return chain.filter(exchange);
            }

        });
    }

    @Override
    public List<String> shortcutFieldOrder() {
        //设置返回的集合为Config配置类的属性名称集合
        return Arrays.asList("is");
    }

    //返回错误json提示方法
    private Mono returnFail(ServerHttpResponse response){
        //获取响应对象头
        HttpHeaders headers = response.getHeaders();
        //设置响应头内容Content-Type 响应内容类型
        headers.add("Content-Type","application/json; charset=UTF-8");
        //禁止缓存
        headers.add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        //设置响应内容
        String body="token验证失败";
        //包装成一个DataBuffer
        DataBuffer dataBuffer = response.bufferFactory().wrap(body.getBytes());
        //把DataBuffer设置到Mono
        return Mono.just(dataBuffer);
    }
}

