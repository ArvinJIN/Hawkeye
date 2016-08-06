package com.king.hawkeye.server.channel;

import com.king.hawkeye.remote.core.RemoteInfo;
import com.king.hawkeye.router.channel.AbstractRouterChannel;
import com.king.hawkeye.router.channel.RouterQueue;
import com.king.hawkeye.router.filter.AbstractFilterChain;

/**
 * Created by King on 16/4/16.
 */
public class RemoteInfoChannel extends AbstractRouterChannel<RemoteInfo> {

    public RemoteInfoChannel() {
        super(new AbstractFilterChain<RemoteInfo>() {
            public String getFilterContent(RemoteInfo info) {
                return (String) info.getInfo();
            }
        }, new RouterQueue<RemoteInfo>());
    }

}
