package com.tencent.wxcloudrun.model.actor.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Description
 * @Author CentAtankie
 **/
@Data
public class ActorAttrVO {


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
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;
}
