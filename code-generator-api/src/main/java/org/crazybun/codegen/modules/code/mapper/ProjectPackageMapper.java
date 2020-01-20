package org.crazybun.codegen.modules.code.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.crazybun.codegen.modules.code.dto.input.ProjectQueryPara;
import org.crazybun.codegen.modules.code.entity.ProjectPackage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> 代码生成器 - 项目包管理表 Mapper 接口 </p>
 *
 * @author : CrazyBunQnQ
 * @date : 2019-09-09
 */
public interface ProjectPackageMapper extends BaseMapper<ProjectPackage> {

    /**
     * 列表分页
     *
     * @param page
     * @param filter
     * @return
     */
    List<ProjectPackage> selectProjectPackages(Pagination page, @Param("filter") ProjectQueryPara filter);

    /**
     * 列表
     *
     * @param filter
     * @return
     */
    List<ProjectPackage> selectProjectPackages(@Param("filter") ProjectQueryPara filter);
}
