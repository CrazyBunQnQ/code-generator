package org.crazybun.codegen.modules.code.mapper;

import org.crazybun.codegen.modules.code.dto.output.DatabaseView;
import org.crazybun.codegen.modules.code.entity.Database;
import org.crazybun.codegen.modules.code.dto.input.DatabaseQueryPara;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  <p> 项目数据库信息表 Mapper 接口 </p>
 *
 * @description :
 * @author : CrazyBunQnQ
 * @date : 2019/8/20 16:09
 */
public interface DatabaseMapper extends BaseMapper<Database> {

    /**
     * 列表分页
     *
     * @param page
     * @param filter
     * @return
     */
    List<DatabaseView> selectDatabases(Pagination page, @Param("filter") DatabaseQueryPara filter);

    /**
     * 列表
     *
     * @param filter
     * @return
     */
    List<DatabaseView> selectDatabases(@Param("filter") DatabaseQueryPara filter);

    /**
     * 根据数字库名检索信息
     *
     * @param dbName
     * @return
     */
    Database selectByName(String dbName);
}
