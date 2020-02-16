package org.crazybun.codegen.ssm.assist;

import freemarker.template.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.crazybun.codegen.ssm.db.Table;
import org.crazybun.codegen.ssm.util.FileUtils;
import org.crazybun.codegen.ssm.util.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ExportService {
    private static final Log logger = LogFactory.getLog(ExportService.class);
    private IConverter tableConverter;
    private IConverter columnConverter;

    public ExportService(IConverter tableConverter, IConverter columnConverter) {
        this.tableConverter = tableConverter;
        this.columnConverter = columnConverter;
    }

    public void export(int index, Table table, ExportInfo exportInfo, StringBuilder result) {
        if (table == null) {
            result.append(AbstractFinals.JSP_LINE_FEED).append(exportInfo.getTableNames()[index]).append(" 表未查询到,请刷新页面!");
            return;
        }
        String entityPkgName = exportInfo.getPackage(AbstractFinals.PKG_FLAG_ENTITY, index, true);
        String javaSrcPath = exportInfo.getJavaSrcPath(entityPkgName, AbstractFinals.FOLDER_DAO);
        WriteEntity entity = new WriteEntity(javaSrcPath, entityPkgName, table, exportInfo.getIdType(), tableConverter, columnConverter);
        String entityName = null, info = null;
        try {
            entityName = entity.createEntity();
            info = "导出实体[" + entityName + "]成功.";
            logger.info(info);
            result.append(AbstractFinals.JSP_LINE_FEED).append(info);
        } catch (IOException e) {
            e.printStackTrace();
            info = "[Entity]导出表[" + table.getTableName() + "]的实体失败!";
            result.append(AbstractFinals.JSP_LINE_FEED).append(info);
            logger.error(info);
            return;
        }
        String daoPkg = exportInfo.getPackage(AbstractFinals.PKG_FLAG_DAO, index, true);
        String daoJpaPkg = exportInfo.getPackage(AbstractFinals.PKG_FLAG_JPA, index, true);
        String modelPkg = exportInfo.getPackage(AbstractFinals.PKG_FLAG_MODEL, index, false);
        String managerPkg = exportInfo.getPackage(AbstractFinals.PKG_FLAG_MANAGER, index, false);
        String controllerPkg = exportInfo.getPackage(AbstractFinals.PKG_FLAG_WEB, index, false);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("entityVar", ExportUtils.lowerFirstChar(entityName));
        dataMap.put("moduleName", exportInfo.getModuleNames()[index]);
        dataMap.put("menuName", exportInfo.getMenuNames()[index]);
        dataMap.put("project", exportInfo.getProject());
        dataMap.put("entityName", entityName);
        dataMap.put("entitPkg", entityPkgName);
        dataMap.put("daoPkg", daoPkg);
        dataMap.put("daoJpaPkg", daoJpaPkg);
        dataMap.put("modelPkg", modelPkg);
        dataMap.put("managerPkg", managerPkg);
        dataMap.put("controllerPkg", controllerPkg);
        dataMap.put("entityComment", table.getRemarks());
        dataMap.put("fieldList", entity.getFieldList());
        Configuration config = new Configuration();
        config.setDefaultEncoding(StringUtils.UTF8);
        String templatePath = exportInfo.getTemplatePath();
        try {
            config.setDirectoryForTemplateLoading(new File(templatePath));
            // 导出DAO
            this.exportByTemplate(config, "EntityDao.java", dataMap, FileUtils.createFile(exportInfo.getJavaSrcPath(daoPkg, AbstractFinals.FOLDER_DAO), entityName + "Dao.java"), result);
            // 导出JAP
            this.exportByTemplate(config, "EntityDaoJpa.java", dataMap, FileUtils.createFile(exportInfo.getJavaSrcPath(daoJpaPkg, AbstractFinals.FOLDER_DAO), entityName + "DaoJpa.java"), result);
            // 导出Manager
            this.exportByTemplate(config, "EntityManager.java", dataMap, FileUtils.createFile(exportInfo.getJavaSrcPath(managerPkg, AbstractFinals.FOLDER_MANAGER), entityName + "Manager.java"), result);
            // 导出ManagerImpl
            this.exportByTemplate(config, "EntityManagerImpl.java", dataMap, FileUtils.createFile(exportInfo.getJavaSrcPath(managerPkg, AbstractFinals.FOLDER_MANAGER), entityName + "ManagerImpl.java"), result);
            // 导出Model
            this.exportByTemplate(config, "EntityModel.java", dataMap, FileUtils.createFile(exportInfo.getJavaSrcPath(modelPkg, AbstractFinals.FOLDER_MANAGER), entityName + "Model.java"), result);
            // 导出Controller
            this.exportByTemplate(config, "EntityController.java", dataMap, FileUtils.createFile(exportInfo.getJavaSrcPath(controllerPkg, AbstractFinals.FOLDER_WEB), entityName + "Controller.java"), result);
            String[] jspFiles = exportInfo.getTemplate().getJspFiles();
            if (jspFiles == null || jspFiles.length == 0) {
                return;
            }
            // 导出JSP manage、add、edit、view、row
            String jspFilePath = exportInfo.getJspPath(index);
			/*this.exportByTemplate(config, "manage.jsp", dataMap, FileUtils.createFile(jspFilePath, "manage.jsp"), result);
			this.exportByTemplate(config, "add.jsp", dataMap, FileUtils.createFile(jspFilePath, "add.jsp"), result);
			this.exportByTemplate(config, "edit.jsp", dataMap, FileUtils.createFile(jspFilePath, "edit.jsp"), result);
			this.exportByTemplate(config, "view.jsp", dataMap, FileUtils.createFile(jspFilePath, "view.jsp"), result);
			this.exportByTemplate(config, "row.jsp", dataMap, FileUtils.createFile(jspFilePath, "row.jsp"), result);
			// 拷贝JSP queryResult、result
			FileUtils.copyFile(templatePath+"queryResult.jsp", jspFilePath, "queryResult.jsp");
			FileUtils.copyFile(templatePath+"result.jsp", jspFilePath, "result.jsp");*/
            for (String jspFileName : jspFiles) {
                this.exportByTemplate(config, jspFileName, dataMap, FileUtils.createFile(jspFilePath, jspFileName), result);
            }
        } catch (IOException e) {
            e.printStackTrace();
            info = "[Template]配置模板或根据模板导出失败!";
            result.append(AbstractFinals.JSP_LINE_FEED).append(info);
            logger.error(info);
        }
    }

    private void exportByTemplate(Configuration config, String templateName, Map<String, Object> dataMap, File outFile, StringBuilder result) {
        Writer writer = null;
        String info = null;
        try {
            freemarker.template.Template template = config.getTemplate(templateName);
            writer = new OutputStreamWriter(new FileOutputStream(outFile));
            template.process(dataMap, writer);
            writer.flush();
            info = "[Template]根据模板导出文件[" + outFile.getAbsolutePath() + "]成功.";
            result.append(AbstractFinals.JSP_LINE_FEED).append(info);
            logger.info(info);
        } catch (Exception e) {//IOException、TemplateException
            e.printStackTrace();
            info = "[Template]根据模板导出文件[" + outFile.getAbsolutePath() + "]失败.";
            result.append(AbstractFinals.JSP_LINE_FEED).append(info);
            logger.error(info);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
