package com.lh.frame.es.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
@ConfigurationProperties(prefix = "es.cluster")
public class EsConfigProperties {

    /**
     * ES集群配置列表。这个私有字段用于存储ES集群的配置信息。
     * -- GETTER --
     *  获取ES集群配置列表。
     *
     * @return 返回ES集群配置的列表，这个列表可以被外部调用者读取但不能修改。

     */
    private List<EsClusterConfig> esConfigs = new ArrayList<>();

    /**
     * 设置ES集群配置列表。
     * @param esConfigs 要设置的ES集群配置列表。允许外部调用者设置这个字段的值。
     */
    public void setEsConfigs(List<EsClusterConfig> esConfigs) {
        this.esConfigs = esConfigs;
    }
}
