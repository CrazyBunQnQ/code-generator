package org.crazybun.codegen.ssm.assist;

import org.crazybun.codegen.ssm.db.IDType;
import org.crazybun.codegen.ssm.util.StringUtils;

public class ExportInfo {
    private String domain = AbstractFinals.DOMAIN;
    private String project = AbstractFinals.PROJECT;
    private String storePath = AbstractFinals.STORE_PATH;
    private IDType idType = IDType.StringUUIDEntity;
    private Template template;
    private String[] tableNames;
    /**
     * 决定类存放包名
     */
    private String[] moduleNames;
    /**
     * 决定JSP路径[moduleName/menuName/xxx.jsp],moduleName、menuName为空则合并为一个
     */
    private String[] menuNames;
    /**
     * 默认DAO/JPA中合并(即不根据moduleName分别建包名),false时则分包
     */
    private boolean daoMerge = true;
    private String appRealPath;
    private JspAssist jspAssist;
    private PackageAssist pkgAssist;

    public ExportInfo() {}

    public ExportInfo(String domain, String project, String storePath, String idType, String template, String[] tableNames, String[] moduleNames, String[] menuNames) {
        super();
        this.domain = domain;
        this.project = project;
        this.storePath = storePath;
        this.idType = Enum.valueOf(IDType.class, idType);
        this.template = Enum.valueOf(Template.class, template);
        this.tableNames = tableNames;
        this.moduleNames = moduleNames;
        this.menuNames = menuNames;
    }

    public String getTemplatePath() {
        return appRealPath + AbstractFinals.TEMPLATE_PATH + template.getPath();
    }

    public String getPackage(String pkgFlag, int index, boolean isDao) {
        return ((isDao && daoMerge) || StringUtils.isEmpty(moduleNames[index]) ? getBasePackage(pkgFlag) : getBasePackage(pkgFlag).append(AbstractFinals.PKG_SEPARATOR).append(moduleNames[index].toLowerCase())).toString();
    }

    public String getJavaSrcPath(String packageName, String prefixFolder) {
        return new StringBuilder(storePath).append(AbstractFinals.FOLDER_SEPARATOR).append(prefixFolder).append(AbstractFinals.FOLDER_SEPARATOR).append(packageName.replaceAll(AbstractFinals.REGEX_PKG_SEPARATOR, AbstractFinals.FOLDER_SEPARATOR)).append(AbstractFinals.FOLDER_SEPARATOR).toString();
    }

    public String getJspPath(int index) {
        if (jspAssist == null) {
            jspAssist = new JspAssist();
        }
        StringBuilder jspSb = jspAssist.getBuilder();
        if (!StringUtils.isEmpty(moduleNames[index])) {
            jspSb.append(moduleNames[index]).append(AbstractFinals.FOLDER_SEPARATOR);
        }
        if (!StringUtils.isEmpty(menuNames[index])) {
            jspSb.append(menuNames[index]).append(AbstractFinals.FOLDER_SEPARATOR);
        }
        return jspSb.toString();
    }

    private StringBuilder getBasePackage(String pkgFlag) {
        if (pkgAssist == null) {
            pkgAssist = new PackageAssist();
        }
        return pkgAssist.getBuilder().append(pkgFlag);
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getStorePath() {
        return storePath;
    }

    public void setStorePath(String storePath) {
        this.storePath = storePath;
    }

    public IDType getIdType() {
        return idType;
    }

    public void setIdType(IDType idType) {
        this.idType = idType;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public String[] getTableNames() {
        return tableNames;
    }

    public void setTableNames(String[] tableNames) {
        this.tableNames = tableNames;
    }

    public String[] getModuleNames() {
        return moduleNames;
    }

    public void setModuleNames(String[] moduleNames) {
        this.moduleNames = moduleNames;
    }

    public String[] getMenuNames() {
        return menuNames;
    }

    public void setMenuNames(String[] menuNames) {
        this.menuNames = menuNames;
    }

    public boolean isDaoMerge() {
        return daoMerge;
    }

    public void setDaoMerge(boolean daoMerge) {
        this.daoMerge = daoMerge;
    }

    public String getAppRealPath() {
        return appRealPath;
    }

    public void setAppRealPath(String appRealPath) {
        this.appRealPath = appRealPath;
    }

    private class PackageAssist {
        private StringBuilder builder;
        private int firstInitLen;

        PackageAssist() {
            this.builder = new StringBuilder(domain).append(AbstractFinals.PKG_SEPARATOR).append(project).append(AbstractFinals.PKG_SEPARATOR);
            this.firstInitLen = this.builder.length();
        }

        StringBuilder getBuilder() {
            this.builder.setLength(this.firstInitLen);
            return this.builder;
        }
    }

    private class JspAssist {
        private StringBuilder builder;
        private int firstInitLen;

        JspAssist() {
            this.builder = new StringBuilder(storePath).append(AbstractFinals.FOLDER_SEPARATOR).append(AbstractFinals.FOLDER_JSP).append(AbstractFinals.FOLDER_SEPARATOR);
            this.firstInitLen = this.builder.length();
        }

        StringBuilder getBuilder() {
            this.builder.setLength(this.firstInitLen);
            return this.builder;
        }
    }
}
