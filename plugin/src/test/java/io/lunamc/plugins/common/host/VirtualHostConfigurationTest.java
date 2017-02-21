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

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.lunamc.common.host.matcher.AndMatcher;
import io.lunamc.common.host.matcher.PortMatcher;
import io.lunamc.plugins.common.host.matcher.DefaultMatcherRegistry;
import io.lunamc.plugins.common.host.matcher.DefaultMatchers;
import io.lunamc.common.host.matcher.MatcherRegistry;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.List;

public class VirtualHostConfigurationTest {

    @Test
    public void testFullDeserialization() throws Throwable {
        MatcherRegistry registry = new DefaultMatcherRegistry();
        DefaultMatchers.registerDefaultMatchers(registry);

        JAXBContext context = VirtualHostConfigurationUtils.createContext(registry);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        VirtualHostsConfiguration config;
        try (InputStream in = VirtualHostConfigurationTest.class.getResourceAsStream("/example-virtual-hosts.xml")) {
            config = (VirtualHostsConfiguration) unmarshaller.unmarshal(in);
        }

        VirtualHostsConfiguration.VirtualHost fallback = config.getFallbackVirtualHost();
        Assert.assertNotNull(fallback);
        Assert.assertEquals("fallback", fallback.getName());
        VirtualHostsConfiguration.ExternalConstructedClass statusProvider = fallback.getStatusProvider();
        VirtualHostsConfiguration.ExternalConstructedClass playConnectionInitializer = fallback.getPlayConnectionInitializer();
        Assert.assertNotNull(statusProvider);
        Assert.assertNotNull(playConnectionInitializer);
        Assert.assertEquals("AnotherStatusProvider", statusProvider.getImpl());
        Assert.assertEquals("luna-example", statusProvider.getPlugin());
        Assert.assertEquals("AnotherPlayConnectionInitializer", playConnectionInitializer.getImpl());
        Assert.assertEquals("luna-example", statusProvider.getPlugin());
        Assert.assertTrue(fallback.isAuthenticated());
        VirtualHostsConfiguration.Compression compression = fallback.getCompression();
        Assert.assertNull(compression);

        List<VirtualHostsConfiguration.MatchingVirtualHost> matchingVirtualHosts = config.getMatchingVirtualHosts();
        Assert.assertNotNull(matchingVirtualHosts);
        Assert.assertEquals(2, matchingVirtualHosts.size());

        VirtualHostsConfiguration.MatchingVirtualHost matchingVirtualHost = matchingVirtualHosts.get(0);
        Assert.assertEquals("matching-1", matchingVirtualHost.getName());
        statusProvider = matchingVirtualHost.getStatusProvider();
        playConnectionInitializer = matchingVirtualHost.getPlayConnectionInitializer();
        Assert.assertNotNull(statusProvider);
        Assert.assertNotNull(playConnectionInitializer);
        Assert.assertEquals("ExampleStatusProvider", statusProvider.getImpl());
        Assert.assertEquals("luna-example", statusProvider.getPlugin());
        Assert.assertEquals("ExamplePlayConnectionInitializer", playConnectionInitializer.getImpl());
        Assert.assertEquals("luna-example", statusProvider.getPlugin());
        List<Object> matchers = matchingVirtualHost.getMatchers();
        Assert.assertNotNull(matchers);
        Assert.assertEquals(2, matchers.size());
        Object matcher = matchers.get(0);
        Assert.assertTrue(matcher instanceof PortMatcher);
        Integer port = ((PortMatcher) matcher).getPort();
        Assert.assertNotNull(port);
        Assert.assertEquals(25565, port.intValue());
        matcher = matchers.get(1);
        Assert.assertTrue(matcher instanceof AndMatcher);
        List<Object> andMatchers = ((AndMatcher) matcher).getMatchers();
        Assert.assertEquals(1, andMatchers.size());
        matcher = andMatchers.get(0);
        Assert.assertTrue(matcher instanceof PortMatcher);
        port = ((PortMatcher) matcher).getPort();
        Assert.assertEquals(25565, port.intValue());
        Assert.assertTrue(matchingVirtualHost.isAuthenticated());
        compression = matchingVirtualHost.getCompression();
        Assert.assertNotNull(compression);
        Assert.assertEquals(512, compression.getThreshold());
        Assert.assertEquals(-1, compression.getCompressionLevel());

        matchingVirtualHost = matchingVirtualHosts.get(1);
        Assert.assertEquals("matching-2", matchingVirtualHost.getName());
        statusProvider = matchingVirtualHost.getStatusProvider();
        playConnectionInitializer = matchingVirtualHost.getPlayConnectionInitializer();
        Assert.assertNotNull(statusProvider);
        Assert.assertNotNull(playConnectionInitializer);
        Assert.assertEquals("SomeStatusProvider", statusProvider.getImpl());
        Assert.assertEquals("luna-example", statusProvider.getPlugin());
        Assert.assertEquals("SomePlayConnectionInitializer", playConnectionInitializer.getImpl());
        Assert.assertEquals("luna-example", statusProvider.getPlugin());
        matchers = matchingVirtualHost.getMatchers();
        Assert.assertNotNull(matchers);
        Assert.assertEquals(1, matchers.size());
        matcher = matchers.get(0);
        Assert.assertTrue(matcher instanceof PortMatcher);
        port = ((PortMatcher) matcher).getPort();
        Assert.assertNotNull(port);
        Assert.assertEquals(25566, port.intValue());
        Assert.assertFalse(matchingVirtualHost.isAuthenticated());
        compression = matchingVirtualHost.getCompression();
        Assert.assertNull(compression);
    }
}
