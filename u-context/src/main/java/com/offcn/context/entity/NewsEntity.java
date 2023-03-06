package com.offcn.context.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 内容-资讯表
 * 
 * @author xy
 * @email 2264303779@qq.com
 * @date 2023-03-06 15:38:40
 */
@Data
@TableName("cms_news")
public class NewsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 图片路径
	 */
	private String imageUrl;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 排序
	 */
	private Integer displayOrder;
	/**
	 * 跳转路径
	 */
	private String renderUrl;
	/**
	 * 是否显示
	 */
	private Integer enable;
	/**
	 * 删除标记（0-正常，1-删除）
	 */
	private Integer delFlag;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;

}
