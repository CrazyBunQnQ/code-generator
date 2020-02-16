package org.crazybun.codegen.ssm.assist;

import java.util.Calendar;

/**
 * 常量定义(配置定义)
 *
 * @author CrazyBunQnQ
 */
public abstract class AbstractFinals {
    public static final String MODIFIER_PUBLIC = "public";
    public static final String MODIFIER_PRIVATE = "private";
    public static final String SRC_FILE_SUFFIX = ".java";
    public static final String JSP_LINE_FEED = "<br/>";
    public static final String PKG_SEPARATOR = ".";
    public static final String REGEX_PKG_SEPARATOR = "\\.";
    /**
     * 统一使用反斜杠
     */
    public static final String FOLDER_SEPARATOR = "/";
    public static final String TEMPLATE_PATH = "template/";
    public static final String FOLDER_WEB = "src/web";
    public static final String FOLDER_MANAGER = "src/manager";
    public static final String FOLDER_DAO = "src/dao";
    public static final String FOLDER_JSP = "war/WEB-INF/jsp";
    /**
     * 包名标记 controller
     */
    public static final String PKG_FLAG_WEB = "web";
    public static final String PKG_FLAG_MANAGER = "manager";
    public static final String PKG_FLAG_DAO = "dao";
    public static final String PKG_FLAG_JPA = "dao.jpa";
    public static final String PKG_FLAG_ENTITY = "entity";
    public static final String PKG_FLAG_MODEL = "model";

    public static final String ENTITY_PATH = "cn.com.taiji.common.entity.";
    public static final String DOMAIN = "cn.com.taiji";
    public static final String PROJECT = "sample";
    public static final String STORE_PATH = "D:/export";
    // 待忽略的实体ID属性名
    public static final String IGNORE_ID_FIELD = "id";

    public static String getCopyright() {
        return new StringBuilder("Copyright (C) ").append(Calendar.getInstance().get(Calendar.YEAR)).append(" TAIJI Computer Corporation Limited All Rights Reserved.").toString();
    }
}
