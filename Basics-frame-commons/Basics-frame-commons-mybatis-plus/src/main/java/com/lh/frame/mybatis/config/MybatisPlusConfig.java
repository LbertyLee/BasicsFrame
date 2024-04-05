package com.lh.frame.mybatis.config;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.lh.frame.mybatis.handler.AutoMetaObjectHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {

    /**
     * IPage的分页使用的是拦截器，属于物理分页，好处就是处理大量数据时，查询速度快。
     *
     * @return MybatisPlus拦截器
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 向MybatisPlus拦截器链中添加分页拦截器
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }

    /**
     * @Description mybatis提供的主键生成策略【制定雪花】
     */
    @Bean
    public IdentifierGenerator identifierGenerator() {
        return new DefaultIdentifierGenerator();
    }

    /**
     * 自动填充
     */
    @Bean
    @ConditionalOnMissingBean
    public AutoMetaObjectHandler myMetaObjectHandler() {
        return new AutoMetaObjectHandler();
    }

}
