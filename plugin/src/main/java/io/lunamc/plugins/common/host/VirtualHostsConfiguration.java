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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "virtualHosts", namespace = "http://lunamc.io/virtualhost/1.0")
@XmlAccessorType(XmlAccessType.FIELD)
public class VirtualHostsConfiguration {

    @XmlElementWrapper(namespace = "http://lunamc.io/virtualhost/1.0", name = "matchingVirtualHosts")
    @XmlElement(namespace = "http://lunamc.io/virtualhost/1.0", name = "matchingVirtualHost")
    private List<MatchingVirtualHost> matchingVirtualHosts;

    @XmlElement(name = "fallbackVirtualHost", namespace = "http://lunamc.io/virtualhost/1.0")
    private VirtualHost fallbackVirtualHost;

    public List<MatchingVirtualHost> getMatchingVirtualHosts() {
        return matchingVirtualHosts;
    }

    public void setMatchingVirtualHosts(List<MatchingVirtualHost> matchingVirtualHosts) {
        this.matchingVirtualHosts = matchingVirtualHosts;
    }

    public VirtualHost getFallbackVirtualHost() {
        return fallbackVirtualHost;
    }

    public void setFallbackVirtualHost(VirtualHost fallbackVirtualHost) {
        this.fallbackVirtualHost = fallbackVirtualHost;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class MatchingVirtualHost extends VirtualHost {

        @XmlElementWrapper(namespace = "http://lunamc.io/virtualhost/1.0", name = "matchers")
        @XmlAnyElement(lax = true)
        private List<Object> matchers;

        public List<Object> getMatchers() {
            return matchers;
        }

        public void setMatchers(List<Object> matchers) {
            this.matchers = matchers;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class VirtualHost {

        @XmlElement(name = "name", namespace = "http://lunamc.io/virtualhost/1.0")
        private String name;

        @XmlElement(name = "statusProvider", namespace = "http://lunamc.io/virtualhost/1.0")
        private ExternalConstructedClass statusProvider;

        @XmlElement(name = "playConnectionInitializer", namespace = "http://lunamc.io/virtualhost/1.0")
        private ExternalConstructedClass playConnectionInitializer;

        @XmlElement(name = "authenticated", namespace = "http://lunamc.io/virtualhost/1.0")
        private boolean authenticated;

        @XmlElement(name = "compression", namespace = "http://lunamc.io/virtualhost/1.0")
        private Compression compression;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ExternalConstructedClass getStatusProvider() {
            return statusProvider;
        }

        public void setStatusProvider(ExternalConstructedClass statusProvider) {
            this.statusProvider = statusProvider;
        }

        public ExternalConstructedClass getPlayConnectionInitializer() {
            return playConnectionInitializer;
        }

        public void setPlayConnectionInitializer(ExternalConstructedClass playConnectionInitializer) {
            this.playConnectionInitializer = playConnectionInitializer;
        }

        public boolean isAuthenticated() {
            return authenticated;
        }

        public void setAuthenticated(boolean authenticated) {
            this.authenticated = authenticated;
        }

        public Compression getCompression() {
            return compression;
        }

        public void setCompression(Compression compression) {
            this.compression = compression;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ExternalConstructedClass {

        @XmlAttribute(name = "impl")
        private String impl;

        @XmlAttribute(name = "plugin")
        private String plugin;

        public String getImpl() {
            return impl;
        }

        public void setImpl(String impl) {
            this.impl = impl;
        }

        public String getPlugin() {
            return plugin;
        }

        public void setPlugin(String plugin) {
            this.plugin = plugin;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Compression {

        @XmlElement(name = "threshold", namespace = "http://lunamc.io/virtualhost/1.0")
        private int threshold;

        @XmlElement(name = "compressionLevel", namespace = "http://lunamc.io/virtualhost/1.0")
        private int compressionLevel;

        public int getThreshold() {
            return threshold;
        }

        public void setThreshold(int threshold) {
            this.threshold = threshold;
        }

        public int getCompressionLevel() {
            return compressionLevel;
        }

        public void setCompressionLevel(int compressionLevel) {
            this.compressionLevel = compressionLevel;
        }
    }
}
