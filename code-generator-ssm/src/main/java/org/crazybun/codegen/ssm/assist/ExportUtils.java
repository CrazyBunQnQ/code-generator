package org.crazybun.codegen.ssm.assist;

import org.crazybun.codegen.ssm.util.StringUtils;

import java.sql.Types;

public class ExportUtils {

    private static final String PLACEHOLDER = "${placeholder}";
    private static final String CLASS_COMMENT_TEMPLATE = "/**\n * ${placeholder}\n */\n";
    private static final String FIELD_COMMENT_TEMPLATE = "\t/**\n\t * ${placeholder}\n\t */\n";
    private static final String ENTITY_ANNOTATION_TEMPLATE = "@Entity\n@Table(name = \"${placeholder}\")\n";
    private static final String COLUMN_ANNOTATION_TEMPLATE = "\n\t@Column(name = \"${placeholder}\")";
    private static final String CLASS_PLACEHOLDER = "\\$\\{classType\\}";
    private static final String UPPER_PLACEHOLDER = "\\$\\{Upper\\}";
    private static final String LOWER_PLACEHOLDER = "\\$\\{Lower\\}";
    private static final String METHOD_GET_AND_SET_TEMPLATE = "\n\tpublic ${classType} get${Upper}() {\n\t\treturn ${Lower};\n\t}\n" + "\tpublic void set${Upper}(${classType} ${Lower}) {\n\t\tthis.${Lower} = ${Lower};\n\t}\n";

    public static String getJavaType(int dataType, int precision) {
        switch (dataType) {
            case Types.VARCHAR:
                return "String";
            case Types.DECIMAL:
                return (precision > 0 ? "Double" : "Integer");
            case Types.NUMERIC:
                return (precision > 0 ? "Double" : "Integer");
            case Types.TIMESTAMP:
                return "Calendar";
            case Types.DATE:
                return "Calendar";
            case Types.DOUBLE:
                return "Double";
            case Types.FLOAT:
                return "Double";
            case Types.REAL:
                return "Float";
            case Types.BIGINT:
                return "Long";
            case Types.INTEGER:
                return "Integer";
            case Types.SMALLINT:
                return "Short";
            case Types.TINYINT:
                return "Short";
            case Types.BIT:
                return "Boolean";
            default:
                return "String";
        }
    }

    public static boolean isCalendar(String fieldType) {
        return "Calendar".equals(fieldType);
    }

    public static String getImportType(int dataType) {
        switch (dataType) {
            case Types.DATE:
                return "java.util.Calendar";
            case Types.TIMESTAMP:
                return "java.util.Calendar";
            default:
                return "java.lang.String";
        }
    }

    public static boolean getNullable(int nullable) {
        switch (nullable) {
            case 0:
                return false;
            default:
                return true;
        }
    }

    public static String upperFirstChar(String fieldName) {
        return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    public static String lowerFirstChar(String fieldName) {
        return fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
    }

    private static String doReplace(String template, String replacement) {
        return template.replace(PLACEHOLDER, !StringUtils.isEmpty(replacement) ? replacement : "");
    }

    public static String getClassComment(String specComment) {
        return doReplace(CLASS_COMMENT_TEMPLATE, specComment);
    }

    public static String getFieldComment(String specComment) {
        return doReplace(FIELD_COMMENT_TEMPLATE, specComment);
    }

    public static String getEntityAnnotation(String tableName) {
        return doReplace(ENTITY_ANNOTATION_TEMPLATE, tableName);
    }

    public static String getColumnAnnotation(String columnName) {
        return doReplace(COLUMN_ANNOTATION_TEMPLATE, columnName);
    }

    public static String getMethodGetAndSet(String fieldName, String fieldType) {
        return METHOD_GET_AND_SET_TEMPLATE.replaceAll(LOWER_PLACEHOLDER, fieldName).replaceAll(UPPER_PLACEHOLDER, upperFirstChar(fieldName)).replaceAll(CLASS_PLACEHOLDER, fieldType);
    }
}
