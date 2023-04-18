package com.tencent.wxcloudrun.service.actor;

import com.tencent.wxcloudrun.dto.actor.ActorAttrRequest;
import com.tencent.wxcloudrun.model.actor.ActorAttr;
import com.tencent.wxcloudrun.model.actor.vo.ActorAttrVO;

/**
 * @Description
 * @Author CentAtankie
 **/
public interface ActorAttrService {
    ActorAttrVO getByOpenId(ActorAttrRequest request);
}
