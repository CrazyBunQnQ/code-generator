package org.crazybun.codegen.modules.code.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.crazybun.codegen.modules.code.dto.input.BsTemplateQueryPara;
import org.crazybun.codegen.modules.code.entity.BsTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  <p> 默认模板表 Mapper 接口 </p>
 *
 * @description :
 * @author : CrazyBunQnQ
 * @date : 2019/8/20 16:09
 */
public interface BsTemplateMapper extends BaseMapper<BsTemplate> {

    /**
     * 列表分页
     *
     * @param page
     * @param filter
     * @return
     */
    List<BsTemplate> selectBsTemplates(Pagination page, @Param("filter") BsTemplateQueryPara filter);

    /**
     * 列表
     *
     * @param filter
     * @return
     */
    List<BsTemplate> selectBsTemplates(@Param("filter") BsTemplateQueryPara filter);
}
