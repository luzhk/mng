package com.platform.universally.config;

import com.platform.universally.auth.author.JwtFilter;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * shiro配置
 * Created by Administrator on 2018/8/6.
 */
@Configuration
public class ShiroConfiguration {

    /*@Bean
    public Set<Realm> shiroRealms() {
        Set<Realm> set = new HashSet<>();
        set.add(new SecurityRealm());
        return set;
    }*/

    /*@Bean
    public Authorizer authorizer(Set<Realm> shiroRealms){
        ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
        authorizer.setRealms(shiroRealms);
        return authorizer;
    }*/

    @Bean
    public CacheManager cacheManager(){
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return cacheManager;
    }


    //权限管理，配置主要是Realm的管理认证
    @Bean
    public SecurityManager securityManager(CacheManager cacheManager, Realm securityRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        securityManager.setAuthorizer(authorizer);
        securityManager.setCacheManager(cacheManager);
        securityManager.setRealm(securityRealm);
        return securityManager;
    }

    //Filter工厂，设置对应的过滤条件和跳转条件
    /**
     * 原理：当前的所有请求都通过过滤器，然后不同的过滤器负责不同的功能
     * shiroFilter过滤器的功能
     * 1、在登录时调用了SecurityRealm中的doGetAuthenticationInfo认证管理方法
     * 2、在进行资源访问时调用了SecurityRealm中的doGetAuthorizationInfo授权管理方法
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwt", new JwtFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        Map<String,String> map = new HashMap<>();
        /*静态文件不认证，这里一定要添加，不然在jsp访问静态文件时会因为没登陆而无法访问，坑。。。。。。。。。。。。。。
        map.put("/adminStatic","anon");
        map.put("/loginCheck","anon");
        map.put("/common","anon");
        map.put("/decorators","anon");
        map.put("/service", "anon");*/
        //登出
        map.put("/logout","logout");
        map.put("/login", "anon");
        map.put("/signup", "anon");
        //对所有用户认证
        map.put("/**","authc");
        map.put("/**", "jwt");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    //加入注解的使用，不加入这个注解不生效
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }


}
