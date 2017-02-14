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

package io.lunamc.common.login.session;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class StaticProfile implements Profile {

    private final String id;
    private final String name;
    private final List<Property> properties;

    public StaticProfile(String id, String name, List<Property> properties) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.properties = properties != null && !properties.isEmpty() ? Collections.unmodifiableList(new ArrayList<>(properties)) : null;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Property> getProperties() {
        return properties;
    }

    public static StaticProfile createOfflineProfile(String name) {
        return new StaticProfile(createOfflineUuid(name).toString(), name, null);
    }

    private static UUID createOfflineUuid(String name) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(StandardCharsets.UTF_8));
    }

    public static class StaticProperty implements Property {

        private final String name;
        private final String value;
        private final String signature;

        public StaticProperty(String name, String value, String signature) {
            this.name = Objects.requireNonNull(name, "name must not be null");
            this.value = Objects.requireNonNull(value, "value must not be null");
            this.signature = signature;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public String getSignature() {
            return signature;
        }
    }
}
