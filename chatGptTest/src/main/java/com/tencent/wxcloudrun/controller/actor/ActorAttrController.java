package com.tencent.wxcloudrun.controller.actor;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.actor.ActorAttrRequest;
import com.tencent.wxcloudrun.model.actor.vo.ActorAttrVO;
import com.tencent.wxcloudrun.model.actor.vo.FightAttrVO;
import com.tencent.wxcloudrun.service.actor.ActorAttrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author CentAtankie
 **/
@RestController
public class ActorAttrController {
    final Logger logger;
    final ActorAttrService actorAttrService;

    public ActorAttrController(@Autowired ActorAttrService actorAttrService) {
        this.actorAttrService = actorAttrService;
        this.logger = LoggerFactory.getLogger(ActorAttrController.class);
    }

    /**
     * 获取用户属性
     * @param request
     * @return
     */
    @PostMapping(value = "/actor/attr/info")
    ApiResponse create(@RequestBody ActorAttrRequest request) {
        ActorAttrVO actorAttr = actorAttrService.getByOpenId(request);
        return ApiResponse.ok(actorAttr);
    }

    public static void main(String[] args) {
        FightAttrVO actor  = new FightAttrVO();
        actor.setName("A");
        actor.setLife(50L);
        actor.setMana(50L);
        actor.setAttack(5L);
        actor.setDefense(2L);
        actor.setHit(0L);
        actor.setDodge(0L);
        actor.setLucky(80L);
        actor.setSpeed(5L);

        FightAttrVO actor2  = new FightAttrVO();
        actor2.setName("B");
        actor2.setLife(50L);
        actor2.setMana(50L);
        actor2.setAttack(5L);
        actor2.setDefense(2L);
        actor2.setHit(0L);
        actor2.setDodge(0L);
        actor2.setLucky(0L);
        actor2.setSpeed(5L);
        
        String name = fight(actor,actor2);
        System.out.println(name);
    }

    private static String fight(FightAttrVO role1, FightAttrVO role2) {
        boolean role1First = role1.getSpeed() >= role2.getSpeed();
        FightAttrVO firstAttacker = role1First ? role1 : role2;
        FightAttrVO secondAttacker = role1First ? role2 : role1;

        System.out.println("战斗开始！" + firstAttacker.getName() + "先攻击！");

        while (firstAttacker.getLife() > 0 && secondAttacker.getLife() > 0) {
            Long damage = firstAttacker.getAttack();
            if (isCriticalHit(firstAttacker)) {
                System.out.println(firstAttacker.getName() + "对" + secondAttacker.getName() + "触发了暴击！");
                damage *= 2; // 攻击力翻倍
            }
            if (!isDodged(firstAttacker, secondAttacker)) {
                damage = Math.max(damage - secondAttacker.getDefense(), 0);
                secondAttacker.setLife(secondAttacker.getLife() - damage);
                System.out.println(firstAttacker.getName() + "对" + secondAttacker.getName() + "造成了" + damage + "点伤害！");
            } else {
                System.out.println(secondAttacker.getName() + "成功闪避了" + firstAttacker.getName() + "的攻击！");
            }

            if (secondAttacker.getLife() <= 0) {
                return secondAttacker.getName();
            }

            // 交换攻击者和被攻击者的位置
            FightAttrVO temp = firstAttacker;
            firstAttacker = secondAttacker;
            secondAttacker = temp;
        }

        return firstAttacker.getLife() > 0 ? firstAttacker.getName() : secondAttacker.getName();
    }

    /**
     * 判断是否触发暴击
     * @param attacker 攻击者
     * @return 是否触发暴击
     */
    public static boolean isCriticalHit(FightAttrVO attacker) {
        return Math.random()*100 <= attacker.getLucky();
    }

    /**
     * 判断是否被闪避
     * @param attacker 攻击者
     * @param defender 防御者
     * @return 是否被闪避
     */
    public static boolean isDodged(FightAttrVO attacker, FightAttrVO defender) {
        return Math.random()*100 <= defender.getDodge();
    }


}
