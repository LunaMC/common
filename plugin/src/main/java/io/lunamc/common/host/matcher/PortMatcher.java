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

package io.lunamc.common.host.matcher;

import io.lunamc.common.network.InitializedConnection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "port", namespace = "http://lunamc.io/virtualhost-matcher/1.0")
@XmlAccessorType(XmlAccessType.FIELD)
public class PortMatcher {

    @XmlElement(name = "port", namespace = "http://lunamc.io/virtualhost-matcher/1.0")
    private Integer port;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public boolean applies(InitializedConnection connection) {
        return port != null && connection.getServerPort() == port;
    }

    public static void register(MatcherRegistry registry) {
        Objects.requireNonNull(registry, "registry must not be null");
        registry.registerMatcher(PortMatcher.class, model -> model::applies);
    }
}
