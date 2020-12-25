package com.alian.pms.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 品牌表
 * </p>
 *
 * @author zhangzhilian
 * @since 2020-12-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pms_brand")
public class Brand implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    /**
     * 首字母
     */
    private String firstLetter;

    private Integer sort;

    /**
     * 是否为品牌制造商：0->不是；1->是
     */
    private Integer factoryStatus;

    private Integer showStatus;

    /**
     * 产品数量
     */
    private Integer productCount;

    /**
     * 产品评论数量
     */
    private Integer productCommentCount;

    /**
     * 品牌logo
     */
    private String logo;

    /**
     * 专区大图
     */
    private String bigPic;

    /**
     * 品牌故事
     */
    private String brandStory;



}
