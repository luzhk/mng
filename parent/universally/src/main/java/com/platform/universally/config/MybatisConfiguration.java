package com.platform.universally.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import com.platform.universally.basemapper.MyBaseMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.core.JdbcTemplate;
//import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.jdbc.core.JdbcTemplate;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * 数据库层配置，有些工具对springboot的支持不够完善
 * Created by Administrator on 2018/8/5.
 */
//@ImportResource(locations = "classpath:spring/spring-mybatis.xml")
@Configuration
@EnableAutoConfiguration//表示启动ConfigurationProperties注解
//和MapperScannerConfigurer中的basepackage应该指的是同一个操作
//@MapperScan(basePackages = {"com.platform.universally.manager.mapper.*", "com.platform.universally.mapper.*"})
public class MybatisConfiguration {

    private final static Logger logger = LoggerFactory.getLogger(MybatisConfiguration.class);

    private final static String XML_LOCATION = "classpath*:com/platform/universally/**/*Mapper.xml";
    //private final static String MAPPER_LOCATION = "com.platform.**.mapper";这种配置方式有问题，会把basemapper包下的MyBaseMapper扫面进来
    private final static String MAPPER_LOCATION = "com.platform.universally.manager.mapper, com.platform.universally.mapper";

    private final static String ENTITY_TYPE_ALIAS = "com.platform.universally.model, com.platform.universally.manager.model";
    @Primary//当一个接口有多个实现是，默认注入的对象
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "db.jdbc")//自匹配功能，读取properties配置文件，然后配装实体类
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        return dataSource;
    }


    @Bean(name = "sqlSessionFactory")
    @Qualifier("dataSource")//根据名称注入对应的对象
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage(ENTITY_TYPE_ALIAS);
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("spring/mybatis-config.xml"));
        PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(patternResolver.getResources(XML_LOCATION));
//        sqlSessionFactoryBean.setPlugins();//在此处设置分页插件
        return sqlSessionFactoryBean.getObject();
    }


    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        Properties properties = new Properties();
        properties.put("mappers", MyBaseMapper.class.getName());
        properties.setProperty("notEmpty", "true");
        properties.setProperty("IDENTITY", "MYSQL");
        mapperScannerConfigurer.setProperties(properties);
        // 这里要特别注意，不要把MyMapper放到 basePackage 中，也就是不能同其他Mapper一样被扫描到。
        mapperScannerConfigurer.setBasePackage(MAPPER_LOCATION);
        mapperScannerConfigurer.setSqlSessionTemplateBeanName("sqlSessionTemplate");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return mapperScannerConfigurer;
    }

    /*@Bean
    public MapperScannerConfigurer scannerConfigurer(){
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        configurer.setSqlSessionTemplateBeanName("sqlSessionTemplate");
        configurer.setBasePackage("com.pbh.springbootdemo.mapper.*");
        configurer.setMarkerInterface(Mapper.class);
        return configurer;
    }*/

    @Bean("sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean("jdbcTemplate")
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * 分页插件
     *
     * @return
     * @author SHANHY
     * @create  2016年2月18日
     */
    @Bean
    public PageHelper pageHelper() {
        logger.info("注册MyBatis分页插件PageHelper");
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        pageHelper.setProperties(p);
        return pageHelper;
    }

}
