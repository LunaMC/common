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

import io.lunamc.common.host.matcher.MatcherRegistry;
import io.lunamc.platform.plugin.PluginAdapter;
import io.lunamc.platform.plugin.PluginContext;
import io.lunamc.platform.plugin.PluginManager;
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
import io.lunamc.plugins.common.host.VirtualHostConfigurationUtils;
import io.lunamc.plugins.common.host.VirtualHostsConfiguration;
import io.lunamc.plugins.common.host.matcher.DefaultMatcherRegistry;
import io.lunamc.plugins.common.host.matcher.DefaultMatchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@LunaPlugin(
        id = "luna-common",
        version = "0.0.1"
)
public class CommonPlugin extends PluginAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonPlugin.class);

    @Override
    public void initialize(PluginContext context) {
        ServiceRegistry serviceRegistry = context.getServiceRegistry();

        serviceRegistry.setService(ExecutorProvider.class, new DefaultExecutorProvider());
        serviceRegistry.setService(VirtualHostManager.class, new DefaultVirtualHostManager());
        serviceRegistry.setService(JsonMapper.class, new JacksonJsonMapper());
        serviceRegistry.setService(EncryptionFactory.class, new DefaultEncryptionFactory());
        serviceRegistry.setService(ComponentBuilderFactory.class, new DefaultComponentBuilderFactory());

        MatcherRegistry matcherRegistry = new DefaultMatcherRegistry();
        DefaultMatchers.registerDefaultMatchers(matcherRegistry);
        serviceRegistry.setService(MatcherRegistry.class, matcherRegistry);
    }

    @Override
    public void start(PluginContext context) {
        ServiceRegistry serviceRegistry = context.getServiceRegistry();

        File configFile = new File(context.getDescription().getDataDirectory(), "virtual-hosts.xml");
        VirtualHostManager virtualHostManager = serviceRegistry.getService(VirtualHostManager.class).requireInstance();
        MatcherRegistry matcherRegistry = serviceRegistry.getService(MatcherRegistry.class).requireInstance();
        VirtualHostsConfiguration config = load(configFile, matcherRegistry);
        if (config != null)
            applyVirtualHosts(config, virtualHostManager, matcherRegistry, context.getDescription().getClassLoader(), serviceRegistry, context.getPluginManager());
    }

    private static void applyVirtualHosts(VirtualHostsConfiguration config,
                                          VirtualHostManager virtualHostManager,
                                          MatcherRegistry matcherRegistry,
                                          ClassLoader defaultClassLoader,
                                          ServiceRegistry serviceRegistry,
                                          PluginManager pluginManager) {
        for (VirtualHostsConfiguration.VirtualHost source : config.getMatchingVirtualHosts())
            virtualHostManager.addHost(VirtualHostConfigurationUtils.createVirtualHost(source, defaultClassLoader, matcherRegistry, serviceRegistry, pluginManager));
        virtualHostManager.setFallbackHost(VirtualHostConfigurationUtils.createVirtualHost(config.getFallbackVirtualHost(), defaultClassLoader, matcherRegistry, serviceRegistry, pluginManager));
    }

    private static VirtualHostsConfiguration load(File file, MatcherRegistry matcherRegistry) {
        if (file.isFile()) {
            try (FileInputStream inputStream = new FileInputStream(file)) {
                JAXBContext jaxbContext = VirtualHostConfigurationUtils.createContext(matcherRegistry);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                return (VirtualHostsConfiguration) unmarshaller.unmarshal(inputStream);
            } catch (FileNotFoundException e) {
                LOGGER.info("Configuration file " + file.getName() + " does not exists.");
            } catch (IOException e) {
                LOGGER.error("An exception occurred while loading " + file.getName(), e);
            } catch (JAXBException e) {
                LOGGER.error("An exception occurred while parsing " + file.getName(), e);
            }
        }
        return null;
    }
}
