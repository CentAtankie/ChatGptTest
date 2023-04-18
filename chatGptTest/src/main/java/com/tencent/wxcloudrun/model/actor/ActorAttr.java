package com.tencent.wxcloudrun.model.actor;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description
 * @Author CentAtankie
 **/
@Data
@TableName("g_actor_attr")
public class ActorAttr {
    @TableId(type = IdType.AUTO)
    /**
     * 自增id
     */
    private Long id;

    /**
     * 用户open_id
     */
    private String open_id;

    /**
     * 名称
     */
    @TableField("`name`")
    private String name;

    /**
     * 生命值
     */
    private Long life;

    /**
     * 法力值
     */
    private Long mana;

    /**
     * 攻击力
     */
    private Long attack;

    /**
     * 防御力
     */
    private Long defense;

    /**
     * 命中
     */
    private Long hit;

    /**
     * 闪避
     */
    private Long dodge;

    /**
     * 幸运
     */
    private Long lucky;

    /**
     * 速度
     */
    private Long speed;

    /**
     * 等级
     */
    private Long level;

    /**
     * 经验值
     */
    private Long exp;

    /**
     * 创建时间
     */
    private Date create_time;
}
