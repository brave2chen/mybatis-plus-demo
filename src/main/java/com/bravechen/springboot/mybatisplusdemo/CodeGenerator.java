package com.bravechen.springboot.mybatisplusdemo;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.bravechen.springboot.mybatisplusdemo.entity.DataEntity;
import com.bravechen.springboot.mybatisplusdemo.rest.BaseController;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class CodeGenerator {

    public static void main(String[] args) {
        AutoGenerator autoGenerator = new AutoGenerator();

        DataSourceConfig dataSourceConfig = getDataSourceConfig();
        GlobalConfig globalConfig = getGlobalConfig();
        StrategyConfig strategy = getStrategy();
        PackageConfig packageConfig = getPackageConfig();

//        customTemplateConfig(autoGenerator, packageConfig);

        autoGenerator.setDataSource(dataSourceConfig);
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());
        autoGenerator.setGlobalConfig(globalConfig);
        autoGenerator.setStrategy(strategy);
        autoGenerator.setPackageInfo(packageConfig);

        TemplateConfig templateConfig = new TemplateConfig();
        // 不生成xml
        templateConfig.setXml(null);
        // 不生成service
        templateConfig.setService(null);
        autoGenerator.setTemplate(templateConfig);

        globalConfig.setFileOverride(true);
        strategy.setInclude("user");

        // 生成entity, dao, service, controller
        autoGenerator.execute();
    }

    private static PackageConfig getPackageConfig() {
        PackageConfig pc = new PackageConfig();
        pc.setParent(MybatisplusDemoApplication.class.getPackage().getName());
        pc.setModuleName(null);
        pc.setEntity("entity");
        pc.setMapper("mapper");
        pc.setXml("mapper.xml");
        pc.setService("service");
        pc.setServiceImpl("service");
        pc.setController("rest");
        return pc;
    }

    private static StrategyConfig getStrategy() {
        StrategyConfig strategy = new StrategyConfig();
        strategy.setCapitalMode(false);
        strategy.setSkipView(false);
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setTablePrefix();
        strategy.setFieldPrefix();
        strategy.setSuperEntityClass(DataEntity.class.getName());
        strategy.setSuperEntityColumns("create_time,update_time,create_user,update_user".split(","));
//        strategy.setLogicDeleteFieldName("is_deleted");
        strategy.setSuperMapperClass(BaseMapper.class.getName());
        strategy.setSuperServiceClass(null);
        strategy.setSuperServiceImplClass(ServiceImpl.class.getName());
        strategy.setSuperControllerClass(BaseController.class.getName());

        strategy.setEntityColumnConstant(true);
        strategy.setEntityBuilderModel(false);
        strategy.setEntityLombokModel(true);
        strategy.setEntityBooleanColumnRemoveIsPrefix(true);
        strategy.setRestControllerStyle(true);
        strategy.setControllerMappingHyphenStyle(false);
        strategy.setEntityTableFieldAnnotationEnable(true);

        return strategy;
    }

    private static DataSourceConfig getDataSourceConfig() {
        //mysql
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert());
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        dsc.setUrl("jdbc:mysql://localhost:3306/mybatis-plus?setUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8");
        return dsc;
    }

    private static GlobalConfig getGlobalConfig() {
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(CodeGenerator.class.getResource("").getPath().split("/target/")[0] + "/src/main/java");
        gc.setFileOverride(false);
        gc.setOpen(false);
        gc.setEnableCache(false);
        gc.setAuthor("陈庆勇");

        gc.setKotlin(false);
        gc.setActiveRecord(true);
        gc.setSwagger2(false);
        gc.setIdType(IdType.AUTO);

        gc.setEnableCache(false);

        gc.setBaseResultMap(false);
        gc.setBaseColumnList(false);
        gc.setDateType(DateType.TIME_PACK);

        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("I%sService");
        gc.setServiceImplName("%sService");
        gc.setControllerName("%sController");
        return gc;
    }

    private static void customTemplateConfig(AutoGenerator autoGenerator, PackageConfig packageConfig) {
        autoGenerator.setCfg(new InjectionConfig() {
            public static final String TEMPLATE_API_JS = "/templates/api.js.ftl";
            public static final String TEMPLATE_FORM_JS = "/templates/form.js.ftl";
            public static final String TEMPLATE_MANAGE_JS = "/templates/manage.js.ftl";
            public static final String TEMPLATE_MANAGE_HTML = "/templates/manage.html.ftl";

            public final String OUTPUT_PATH = CodeGenerator.class.getResource("").getPath().split("/target/")[0] + "/static";

            @Override
            public void initMap() {
                // 自定义属性
                this.setMap(new HashMap<String, Object>(2) {{
                    put("jSqlBox", true);
                    put("application", "datasource-manage");
                }});

                // 自定义文件输出
                this.setFileOutConfigList(new ArrayList<FileOutConfig>() {{
                    // API JS
                    add(new FileOutConfig(TEMPLATE_API_JS) {
                        @Override
                        public String outputFile(TableInfo tableInfo) {
                            return String.format((OUTPUT_PATH + File.separator + "%s%s" + File.separator + "%s.API.js")
                                    , StringUtils.isBlank(packageConfig.getModuleName()) ? "" : packageConfig.getModuleName() + File.separator
                                    , tableInfo.getEntityPath()
                                    , tableInfo.getEntityName());
                        }
                    });

                    // FORM JS
                    add(new FileOutConfig(TEMPLATE_FORM_JS) {
                        @Override
                        public String outputFile(TableInfo tableInfo) {
                            return String.format((OUTPUT_PATH + File.separator + "%s%s" + File.separator + "%sForm.js")
                                    , StringUtils.isBlank(packageConfig.getModuleName()) ? "" : packageConfig.getModuleName() + File.separator
                                    , tableInfo.getEntityPath()
                                    , tableInfo.getEntityName());
                        }
                    });

                    // HTML JS
                    add(new FileOutConfig(TEMPLATE_MANAGE_JS) {
                        @Override
                        public String outputFile(TableInfo tableInfo) {
                            return String.format((OUTPUT_PATH + File.separator + "%s%s" + File.separator + "%sManage.js")
                                    , StringUtils.isBlank(packageConfig.getModuleName()) ? "" : packageConfig.getModuleName() + File.separator
                                    , tableInfo.getEntityPath()
                                    , tableInfo.getEntityPath());
                        }
                    });

                    // HTML
                    add(new FileOutConfig(TEMPLATE_MANAGE_HTML) {
                        @Override
                        public String outputFile(TableInfo tableInfo) {
                            return String.format((OUTPUT_PATH + File.separator + "%s%s" + File.separator + "%sManage.html")
                                    , StringUtils.isBlank(packageConfig.getModuleName()) ? "" : packageConfig.getModuleName() + File.separator
                                    , tableInfo.getEntityPath()
                                    , tableInfo.getEntityPath());
                        }
                    });
                }});
            }
        });
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        templateConfig.setService(null);
        autoGenerator.setTemplate(templateConfig);
    }
}
