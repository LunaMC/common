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
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@XmlRootElement(name = "and", namespace = "http://lunamc.io/virtualhost-matcher/1.0")
@XmlAccessorType(XmlAccessType.FIELD)
public class AndMatcher {

    @XmlElementWrapper(namespace = "http://lunamc.io/virtualhost-matcher/1.0", name = "matchers")
    @XmlAnyElement(lax = true)
    private List<Object> matchers;

    public List<Object> getMatchers() {
        return matchers;
    }

    public void setMatchers(List<Object> matchers) {
        this.matchers = matchers;
    }

    public boolean applied(MatcherRegistry registry, InitializedConnection connection) {
        Objects.requireNonNull(registry, "registry must not be null");
        List<Object> matchers = getMatchers();
        if (matchers == null || matchers.isEmpty())
            return true;
        for (Object matcher : matchers) {
            Predicate<InitializedConnection> predicate = registry.createPredicate(matcher);
            if (!predicate.test(connection))
                return false;
        }
        return true;
    }

    public static void register(MatcherRegistry registry) {
        Objects.requireNonNull(registry, "registry must not be null");
        registry.registerMatcher(AndMatcher.class, model -> (connection) -> model.applied(registry, connection));
    }
}
