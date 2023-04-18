package com.tencent.wxcloudrun.service.actor;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tencent.wxcloudrun.dao.actor.ActorAttrMapper;
import com.tencent.wxcloudrun.dto.actor.ActorAttrRequest;
import com.tencent.wxcloudrun.model.actor.ActorAttr;
import com.tencent.wxcloudrun.model.actor.vo.ActorAttrVO;
import com.tencent.wxcloudrun.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description
 * @Author CentAtankie
 **/
@Service
public class ActorAttrServiceImpl implements ActorAttrService {
    @Resource
    private ActorAttrMapper actorAttrMapper;

    @Override
    public ActorAttrVO getByOpenId(ActorAttrRequest request) {
        LambdaQueryWrapper<ActorAttr> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ActorAttr::getOpen_id, request.getOpen_id());
        ActorAttr actorAttr = actorAttrMapper.selectOne(lambdaQueryWrapper);
        ActorAttrVO actorAttrVO = new ActorAttrVO();
        BeanCopyUtils.copyBean(actorAttr, actorAttrVO);
        return actorAttrVO;

    }
}
