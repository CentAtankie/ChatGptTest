package com.tencent.wxcloudrun.model.actor.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Description
 * @Author CentAtankie
 **/
@Data
public class FightAttrVO {

    /**
     * 名称
     */
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


}
