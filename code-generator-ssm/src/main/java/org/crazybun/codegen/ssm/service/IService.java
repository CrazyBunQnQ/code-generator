package org.crazybun.codegen.ssm.service;

import org.crazybun.codegen.ssm.assist.ExportInfo;
import org.crazybun.codegen.ssm.db.DataBase;
import org.crazybun.codegen.ssm.db.Table;

import java.sql.SQLException;
import java.util.List;

public interface IService {
    public List<Table> listAllTable(DataBase dataBase) throws ClassNotFoundException, SQLException;

    public StringBuilder doExport(DataBase dataBase, ExportInfo exportInfo);
}
