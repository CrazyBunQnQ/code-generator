package org.crazybun.codegen.ssm.assist;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.crazybun.codegen.ssm.db.Column;
import org.crazybun.codegen.ssm.db.IDType;
import org.crazybun.codegen.ssm.db.Table;
import org.crazybun.codegen.ssm.util.FileUtils;
import org.crazybun.codegen.ssm.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class WriteEntity {
    private static final Log logger = LogFactory.getLog(WriteEntity.class);
    private static final String NotNull = "javax.validation.constraints.NotNull";
    private static final String STRING = "java.lang.String";
    private String javaSrcPath;
    private String packageName;
    private Table table;
    private IDType idType;
    private IConverter columnConverter;
    /**
     * 实体名称
     */
    private String entityName;
    private Set<String> importSet = new HashSet<String>();
    @SuppressWarnings("serial")
    private Set<String> annocationSet = new LinkedHashSet<String>() {{
        add("javax.persistence.Column");
        add("javax.persistence.Entity");
        add("javax.persistence.Table");
    }};
    private List<Field> fieldList = new ArrayList<Field>();
    private StringBuilder headBuilder;
    private StringBuilder bodyBuilder;
    private StringBuilder footBuilder;

    public WriteEntity(String javaSrcPath, String packageName, Table table, IDType idType, IConverter tableConverter, IConverter columnConverter) {
        this.javaSrcPath = javaSrcPath;
        this.packageName = packageName;
        this.table = table;
        this.idType = idType;
        this.columnConverter = columnConverter;
        logger.info("源文件路径：" + javaSrcPath);
        logger.info("包名：" + packageName);
        this.entityName = tableConverter.convert(table.getTableName());
        logger.info("导出表" + table.getTableName() + "实体类名为" + entityName);
    }

    public List<Field> getFieldList() {
        return fieldList;
    }

    public String createEntity() throws IOException {
        if (headBuilder == null) {
            headBuilder = new StringBuilder();
        } else {
            headBuilder.setLength(0);
        }
        if (bodyBuilder == null) {
            bodyBuilder = new StringBuilder();
        } else {
            bodyBuilder.setLength(0);
        }
        if (footBuilder == null) {
            footBuilder = new StringBuilder();
        } else {
            footBuilder.setLength(0);
        }
        // Copyright
        this.appendCopyright();
        // package
        this.appendPackage();
        // class头部
        this.appendClass();
        for (Column column : table.getColumns()) {
            this.createProperty(column);
        }
        this.appendImport();
        this.appendEnd();
        File file = FileUtils.createFile(javaSrcPath, entityName + AbstractFinals.SRC_FILE_SUFFIX);
        PrintWriter writer = new PrintWriter(file);
        writer.write(headBuilder.append(bodyBuilder).append(footBuilder).toString());
        writer.flush();
        writer.close();
        return this.entityName;
    }

    private void appendCopyright() {
        headBuilder.append(ExportUtils.getClassComment(AbstractFinals.getCopyright()));
    }

    private void appendPackage() {
        headBuilder.append("package ").append(packageName).append(";\n\n");
    }

    private void appendClass() {
        bodyBuilder.append(ExportUtils.getClassComment(table.getRemarks())).append(ExportUtils.getEntityAnnotation(table.getTableName())).append("public class ").append(entityName).append(" extends ").append(idType.getClassName()).append(" {\n");
    }

    private void createProperty(Column column) {
        String fieldName = columnConverter.convert(column.getColumnName());
        if (StringUtils.isEmpty(fieldName)) {
            return;
        }
        Field field = new Field(fieldName, column.getRemarks());
        // 实体属性(名称、注释)
        fieldList.add(field);
        if (AbstractFinals.IGNORE_ID_FIELD.equalsIgnoreCase(fieldName)) {
            return;
        }
        bodyBuilder.append(ExportUtils.getFieldComment(column.getRemarks()));
        if (!ExportUtils.getNullable(column.getNullable())) {
            bodyBuilder.append("\t@NotNull\n");
            annocationSet.add(NotNull);
            // 不为空
            field.setNotNull(true);
        }
        String fieldType = ExportUtils.getJavaType(column.getDataType(), column.getDecimalDigits());
        // 判断是否为Calendar
        field.setCalendar(ExportUtils.isCalendar(fieldType));
        bodyBuilder.append("\tprivate ").append(fieldType).append(" ").append(fieldName).append(";\n");
        importSet.add(ExportUtils.getImportType(column.getDataType()));
        footBuilder.append(ExportUtils.getColumnAnnotation(column.getColumnName()));
        footBuilder.append(ExportUtils.getMethodGetAndSet(fieldName, fieldType));
    }

    /**
     * 创建类属性之后调用
     */
    private void appendImport() {
        importSet.remove(STRING);
        for (String str : importSet) {
            headBuilder.append("import ").append(str).append(";\n");
        }
        if (importSet.size() > 0) {
            headBuilder.append("\n");
        }
        for (String str : annocationSet) {
            headBuilder.append("import ").append(str).append(";\n");
        }
        headBuilder.append("\nimport ").append(AbstractFinals.ENTITY_PATH).append(idType.getClassName()).append(";\n\n\n");
    }

    private void appendEnd() {
        footBuilder.append("\n}");
    }
}
