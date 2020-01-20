package org.crazybun.codegen.modules.code.dto.input;

import org.crazybun.codegen.modules.common.dto.input.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *  <p> 项目管理查询参数 </p>
 *
 * @description :
 * @author : CrazyBunQnQ
 * @date : 2019/8/22 11:12
 */
@Data
@ApiModel(description = "项目管理查询参数")
public class ProjectQueryPara extends BaseQuery {
    @ApiModelProperty(value = "项目id")
    private Integer id;
    @ApiModelProperty(value = "项目名称")
    private String name;
}
