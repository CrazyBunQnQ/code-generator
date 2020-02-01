package org.crazybun.codegen.modules.common.generator;

import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import org.crazybun.codegen.modules.code.entity.ProjectTemplate;
import org.crazybun.codegen.modules.code.entity.TableConfig;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 插件基类，用于属性配置 设计成抽象类主要是用于后期可扩展，共享参数配置。
 * </p>
 * @author CrazyBunQnQ
 */
@Data
public abstract class AbstractCodeGenerator {

    /**
     * 数据源配置
     */
    private DataSourceConfig dataSource;

    /**
     * 数据库表配置
     */
    private StrategyConfig strategy;

    /**
     * 包 相关配置，用 map 封装动态包
     */
    private Map<String,String> packageInfo;

    /**
     * 项目模板列表
     */
    protected List<ProjectTemplate> templateList;

    /**
     * 项目中生成配置参数（如搜索条件的字段）
     */
    protected TableConfig myTableConfig;

    /**
     * 全局 相关配置
     */
    private GlobalConfig globalConfig;

    protected MyConfigBuilder config;

    protected InjectionConfig injectionConfig;

    /**
     * 初始化配置
     */
    protected void initConfig() {
        if (null == config) {
            config = new MyConfigBuilder(packageInfo, dataSource, strategy, templateList, globalConfig);
//            if (null != injectionConfig) {
//                injectionConfig.setConfig(config);
//            }
        }
    }

}
