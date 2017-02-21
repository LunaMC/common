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

package io.lunamc.plugins.common.host;

import io.lunamc.common.host.VirtualHost;
import io.lunamc.common.host.matcher.MatcherRegistry;
import io.lunamc.common.login.session.Profile;
import io.lunamc.common.network.AuthorizedConnection;
import io.lunamc.common.network.Connection;
import io.lunamc.common.network.DecidedConnection;
import io.lunamc.common.network.InitializedConnection;
import io.lunamc.common.play.PlayConnectionInitializer;
import io.lunamc.common.status.BetaStatusResponse;
import io.lunamc.common.status.LegacyStatusResponse;
import io.lunamc.common.status.StatusProvider;
import io.lunamc.common.status.StatusResponse;
import io.lunamc.platform.plugin.PluginDescription;
import io.lunamc.platform.plugin.PluginManager;
import io.lunamc.platform.service.ServiceRegistry;
import io.lunamc.plugins.common.host.matcher.DefaultMatcherRegistry;
import io.lunamc.plugins.common.host.matcher.DefaultMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class VirtualHostConfigurationUtilsTest {

    @Test
    public void testCreateBoundClass() {
        MatcherRegistry registry = new DefaultMatcherRegistry();
        DefaultMatchers.registerDefaultMatchers(registry);

        Collection<Class<?>> matchers = registry.getModelClasses();
        List<Class<?>> boundClasses = Arrays.asList(VirtualHostConfigurationUtils.createBoundClass(registry));
        Assert.assertEquals(matchers.size() + 1, boundClasses.size());
        for (Class<?> matcher : matchers)
            Assert.assertTrue(matcher.getName(), boundClasses.contains(matcher));
        Assert.assertTrue(VirtualHostsConfiguration.class.getName(), boundClasses.contains(VirtualHostsConfiguration.class));
    }

    @Test
    public void testCreateVirtualHost() throws Throwable {
        VirtualHostsConfiguration.VirtualHost source = new VirtualHostsConfiguration.VirtualHost();
        source.setName("test");
        source.setPlayConnectionInitializer(createExternalConstructedClass(DemoPlayConnectionInitializer.class.getName(), "test-plugin"));
        source.setStatusProvider(createExternalConstructedClass(DemoStatusProvider.class.getName(), "test-plugin"));
        source.setAuthenticated(true);
        source.setCompression(createCompression(512, -1));

        MatcherRegistry matcherRegistry = new DefaultMatcherRegistry();
        DefaultMatchers.registerDefaultMatchers(matcherRegistry);
        ServiceRegistry serviceRegistry = mockServiceRegistry();
        PluginManager pluginManager = mockPluginManager();
        VirtualHost result = VirtualHostConfigurationUtils.createVirtualHost(
                source,
                getClass().getClassLoader(),
                matcherRegistry,
                serviceRegistry,
                pluginManager
        );

        Assert.assertEquals("test", result.getName());
        Predicate<InitializedConnection> predicate = result.getMatcher();
        Assert.assertNotNull(predicate);
        Assert.assertFalse(predicate.test(Mockito.mock(InitializedConnection.class)));
        Assert.assertTrue(result.isAuthenticated(Mockito.mock(InitializedConnection.class)));
        StatusProvider statusProvider = result.getStatusProvider(Mockito.mock(InitializedConnection.class));
        Assert.assertTrue(statusProvider instanceof DemoStatusProvider);
        PlayConnectionInitializer playConnectionInitializer = result.getPlayConnectionInitializer(Mockito.mock(AuthorizedConnection.class));
        Assert.assertTrue(playConnectionInitializer instanceof DemoPlayConnectionInitializer);
        VirtualHost.Compression compression = result.getCompression(Mockito.mock(InitializedConnection.class), Mockito.mock(Profile.class));
        Assert.assertNotNull(compression);
        Assert.assertEquals(512, compression.getThreshold());
        Assert.assertEquals(-1, compression.getCompressionLevel());

        Mockito.verify(pluginManager.getPlugin("test-plugin").orElseThrow(RuntimeException::new), Mockito.times(2)).getClassLoader();
        Mockito.validateMockitoUsage();
    }

    private static VirtualHostsConfiguration.Compression createCompression(int threshold, int compressionLevel) {
        VirtualHostsConfiguration.Compression result = new VirtualHostsConfiguration.Compression();
        result.setThreshold(threshold);
        result.setCompressionLevel(compressionLevel);
        return result;
    }

    private static VirtualHostsConfiguration.ExternalConstructedClass createExternalConstructedClass(String impl, String plugin) {
        VirtualHostsConfiguration.ExternalConstructedClass result = new VirtualHostsConfiguration.ExternalConstructedClass();
        result.setImpl(impl);
        result.setPlugin(plugin);
        return result;
    }

    private static ServiceRegistry mockServiceRegistry() throws Throwable {
        ServiceRegistry result = Mockito.mock(ServiceRegistry.class);
        Mockito.when(result.instantiate(Mockito.<Class<?>>any())).then(invocationOnMock -> {
            Class<?> aClass = (Class<?>) invocationOnMock.getArguments()[0];
            if (DemoPlayConnectionInitializer.class.getName().equals(aClass.getName()))
                return new DemoPlayConnectionInitializer();
            else if (DemoStatusProvider.class.getName().equals(aClass.getName()))
                return new DemoStatusProvider();
            else
                throw new UnsupportedOperationException("Unknown class: " + aClass);
        });
        return result;
    }

    private static PluginManager mockPluginManager() {
        PluginManager result = Mockito.mock(PluginManager.class);
        PluginDescription pluginDescription = mockPluginDescription();
        Mockito.when(result.getPlugin(Mockito.anyString())).then(invocationOnMock -> {
            Object pluginName = invocationOnMock.getArguments()[0];
            if ("test-plugin".equals(pluginName))
                return Optional.of(pluginDescription);
            else
                throw new RuntimeException("Requested plugin: " + pluginName);
        });
        return result;
    }

    private static PluginDescription mockPluginDescription() {
        PluginDescription pluginDescription = Mockito.mock(PluginDescription.class);
        Mockito.when(pluginDescription.getClassLoader()).thenReturn(VirtualHostConfigurationUtilsTest.class.getClassLoader());
        return pluginDescription;
    }

    public static class DemoPlayConnectionInitializer implements PlayConnectionInitializer {

        @Override
        public void initialize(AuthorizedConnection connection) {
        }
    }

    public static class DemoStatusProvider implements StatusProvider {

        @Override
        public StatusResponse createStatusResponse(DecidedConnection connection) {
            return null;
        }

        @Override
        public LegacyStatusResponse createLegacy16StatusResponse(DecidedConnection connection) {
            return null;
        }

        @Override
        public LegacyStatusResponse createLegacy14StatusResponse(Connection connection) {
            return null;
        }

        @Override
        public BetaStatusResponse createBetaStatusResponse(Connection connection) {
            return null;
        }
    }
}
