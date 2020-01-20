package org.crazybun.codegen.modules.code.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import org.crazybun.codegen.modules.code.dto.input.BsTemplateQueryPara;
import org.crazybun.codegen.modules.code.entity.BsTemplate;

import java.util.List;

/**
 *  <p> 项目代码模板表 服务类 </p>
 *
 * @description :
 * @author : CrazyBunQnQ
 * @date : 2019/8/20 16:08
 */
public interface IBsTemplateService extends IService<BsTemplate> {

    /**
     * 项目代码模板表列表分页
     *
     * @param page
     * @param filter
     * @return
     */
    void listPage(Page<BsTemplate> page, BsTemplateQueryPara filter);

    /**
     * 保存项目代码模板表
     *
     * @param input
     */
    Integer save(BsTemplate input);

    /**
     * 项目代码模板表列表
     *
     * @param filter
     * @return
     */
    List<BsTemplate> list(BsTemplateQueryPara filter);
}
