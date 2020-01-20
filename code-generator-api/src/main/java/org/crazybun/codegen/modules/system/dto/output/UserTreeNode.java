package org.crazybun.codegen.modules.system.dto.output;

import com.google.common.collect.Lists;
import org.crazybun.codegen.modules.system.entity.User;
import lombok.Data;

import java.util.List;

/**
 *  <p> 用户树节点 </p>
 *
 * @description :
 * @author : CrazyBunQnQ
 * @date : 2019/8/20 19:16
 */
@Data
public class UserTreeNode extends User {

    List<UserTreeNode> children = Lists.newArrayList();

}
