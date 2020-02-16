package org.crazybun.codegen.ssm.service;

import org.crazybun.codegen.ssm.assist.DefaultColumnConverter;
import org.crazybun.codegen.ssm.assist.DefaultTableConverter;
import org.crazybun.codegen.ssm.assist.ExportInfo;
import org.crazybun.codegen.ssm.assist.ExportService;
import org.crazybun.codegen.ssm.db.DBUtils;
import org.crazybun.codegen.ssm.db.DataBase;
import org.crazybun.codegen.ssm.db.Table;

import java.sql.SQLException;
import java.util.List;

public class ServiceImpl implements IService {

    @Override
    public List<Table> listAllTable(DataBase dataBase) throws ClassNotFoundException, SQLException {
        return DBUtils.listAllTable(dataBase);
    }

    @Override
    public StringBuilder doExport(DataBase dataBase, ExportInfo exportInfo) {
        StringBuilder result = new StringBuilder();
        if (exportInfo.getTableNames() == null || exportInfo.getTableNames().length == 0) {
            return result.append("[Error]请先选择待导出的表！");
        }
        if (dataBase == null || dataBase.getDBType() == null) {
            return result.append("[Error]Session已失效请刷新配置页面！");
        }
        List<Table> tableList = DBUtils.listTableWithColumns(dataBase, exportInfo.getTableNames());
        if (tableList == null || tableList.size() == 0) {
            return result.append("[Warning]未查询到待导出的表,请先刷新页面！");
        }
        result.append("查询到表的数量：" + tableList.size());
        /*
         * 1.如果表、字段的命名规则有变请自行实现 {@link assist.IConverter} 接口,处理数据库字段转换为Java类属性的功能.
         * 2.然后在此处更换Table、Column相应的Converter
         */
        ExportService service = new ExportService(new DefaultTableConverter(), new DefaultColumnConverter());

        /* 以下为具体的导出操作 */
        for (int i = 0, m = tableList.size(); i < m; i++) {
            service.export(i, tableList.get(i), exportInfo, result);
        }
        return result;
    }

}
