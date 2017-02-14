/*
 *  Copyright 2017 LunaMC.io
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.lunamc.plugins.common;

import io.lunamc.platform.plugin.PluginAdapter;
import io.lunamc.platform.plugin.PluginContext;
import io.lunamc.platform.plugin.annotation.LunaPlugin;
import io.lunamc.platform.service.ServiceRegistry;
import io.lunamc.common.async.DefaultExecutorProvider;
import io.lunamc.common.async.ExecutorProvider;
import io.lunamc.common.host.DefaultVirtualHostManager;
import io.lunamc.common.host.VirtualHostManager;
import io.lunamc.common.json.JsonMapper;
import io.lunamc.common.json.jackson.JacksonJsonMapper;
import io.lunamc.common.login.encryption.DefaultEncryptionFactory;
import io.lunamc.common.login.encryption.EncryptionFactory;
import io.lunamc.common.text.builder.ComponentBuilderFactory;
import io.lunamc.common.text.builder.DefaultComponentBuilderFactory;

@LunaPlugin(
        id = "luna-common",
        version = "0.0.1"
)
public class CommonPlugin extends PluginAdapter {

    @Override
    public void initialize(PluginContext context) {
        ServiceRegistry serviceRegistry = context.getServiceRegistry();

        serviceRegistry.setService(ExecutorProvider.class, new DefaultExecutorProvider());
        serviceRegistry.setService(VirtualHostManager.class, new DefaultVirtualHostManager());
        serviceRegistry.setService(JsonMapper.class, new JacksonJsonMapper());
        serviceRegistry.setService(EncryptionFactory.class, new DefaultEncryptionFactory());
        serviceRegistry.setService(ComponentBuilderFactory.class, new DefaultComponentBuilderFactory());
    }
}
