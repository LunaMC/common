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

import io.lunamc.common.host.StaticVirtualHost;
import io.lunamc.common.host.VirtualHost;
import io.lunamc.common.host.matcher.MatcherRegistry;
import io.lunamc.common.network.InitializedConnection;
import io.lunamc.common.play.PlayConnectionInitializer;
import io.lunamc.common.status.StatusProvider;
import io.lunamc.platform.plugin.PluginManager;
import io.lunamc.platform.service.ServiceRegistry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public class VirtualHostConfigurationUtils {

    private VirtualHostConfigurationUtils() {
        throw new UnsupportedOperationException(getClass().getSimpleName() + " is a utility class and should not be constructed");
    }

    public static JAXBContext createContext(MatcherRegistry registry) throws JAXBException {
        return JAXBContext.newInstance(createBoundClass(registry));
    }

    public static Class<?>[] createBoundClass(MatcherRegistry registry) {
        Collection<Class<?>> modelClasses = registry.getModelClasses();
        Class<?>[] boundClasses = new Class<?>[modelClasses.size() + 1];
        modelClasses.toArray(boundClasses);
        boundClasses[boundClasses.length - 1] = VirtualHostsConfiguration.class;
        return boundClasses;
    }

    public static VirtualHost createVirtualHost(VirtualHostsConfiguration.VirtualHost source,
                                                ClassLoader defaultClassLoader,
                                                MatcherRegistry matcherRegistry,
                                                ServiceRegistry serviceRegistry,
                                                PluginManager pluginManager) {
        return new StaticVirtualHost(
                source instanceof VirtualHostsConfiguration.MatchingVirtualHost ? createPredicate(matcherRegistry, ((VirtualHostsConfiguration.MatchingVirtualHost) source).getMatchers()) : t -> false,
                createClass(StatusProvider.class, defaultClassLoader, source.getStatusProvider(), serviceRegistry, pluginManager),
                createClass(PlayConnectionInitializer.class, defaultClassLoader, source.getPlayConnectionInitializer(), serviceRegistry, pluginManager),
                source.isAuthenticated(),
                createCompression(source.getCompression())
        );
    }

    public static VirtualHost.Compression createCompression(VirtualHostsConfiguration.Compression source) {
        return source != null ? new StaticVirtualHost.StaticCompression(source.getThreshold(), source.getCompressionLevel()) : null;
    }

    public static <T> T createClass(Class<T> requiredClass,
                                    ClassLoader defaultClassLoader,
                                    VirtualHostsConfiguration.ExternalConstructedClass externalConstructedClass,
                                    ServiceRegistry serviceRegistry,
                                    PluginManager pluginManager) {
        ClassLoader classLoader;
        String plugin = externalConstructedClass.getPlugin();
        if (plugin != null)
            classLoader = pluginManager.getPlugin(plugin).orElseThrow(UnsupportedOperationException::new).getClassLoader();
        else
            classLoader = defaultClassLoader;
        String impl = externalConstructedClass.getImpl();
        Class<?> aClass;
        try {
            aClass = classLoader.loadClass(impl);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (!requiredClass.isAssignableFrom(aClass))
            throw new IllegalArgumentException(impl + " must inherit from " + requiredClass.getName());
        try {
            return requiredClass.cast(serviceRegistry.instantiate(aClass));
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static Predicate<InitializedConnection> createPredicate(MatcherRegistry matcherRegistry, List<Object> matchers) {
        Predicate<InitializedConnection> predicate = null;
        for (Object matcher : matchers) {
            Predicate<InitializedConnection> currentPredicate = matcherRegistry.createPredicate(matcher);
            if (predicate == null)
                predicate = currentPredicate;
            else
                predicate = predicate.or(currentPredicate);
        }
        return predicate;
    }
}
