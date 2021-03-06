/*
 * Copyright © Yan Zhenjie. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.msymobile.payment.sdk.paylibs.net.rest;


import com.msymobile.payment.sdk.paylibs.net.NoHttp;

/**
 * <p>
 * Synchronization request executor.
 * </p>
 * Created by Yan Zhenjie on 2016/10/12.
 */
public enum SyncRequestExecutor {

    INSTANCE;

    private RestProtocol mRestProtocol;

    SyncRequestExecutor() {
        mRestProtocol = new RestProtocol(NoHttp.getInitializeConfig().getCacheStore(),
                NoHttp.getInitializeConfig().getNetworkExecutor());
    }

    /**
     * Perform a request.
     */
    public <T> Response<T> execute(ProtocolRequest<?, T> request) {
        return mRestProtocol.request(request);
    }
}
