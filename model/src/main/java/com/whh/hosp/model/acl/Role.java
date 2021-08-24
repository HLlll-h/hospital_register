package com.whh.hosp.model.acl;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.whh.hosp.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 角色
 * </p>
 */
@Data
@ApiModel(description = "角色")
@TableName("acl_role")
public class Role extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "角色名称")
	@TableField("role_name")
	private String roleName;

	@ApiModelProperty(value = "备注")
	@TableField("remark")
	private String remark;

}

