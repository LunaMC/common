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

package io.lunamc.common.json.jackson.databind.login.session;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import io.lunamc.common.login.session.Profile;
import io.lunamc.common.login.session.StaticProfile;
import io.lunamc.common.utils.UuidUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProfileDeserializer extends JsonDeserializer<Profile> {

    public static final ProfileDeserializer INSTANCE = new ProfileDeserializer();

    @Override
    public Profile deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        return fromJsonNode(ctx.readValue(p, JsonNode.class));
    }

    private static StaticProfile fromJsonNode(JsonNode node) {
        String id = node.get("id").asText();
        if (id.length() != 36)
            id = UuidUtils.normalize(id);
        return new StaticProfile(
                id,
                node.get("name").asText(),
                profilePropertiesFromJsonNode(node.get("properties"))
        );
    }

    private static List<Profile.Property> profilePropertiesFromJsonNode(JsonNode node) {
        List<Profile.Property> result = new ArrayList<>(2);
        Iterator<JsonNode> iterator = node.elements();
        while (iterator.hasNext())
            result.add(profilePropertyFromJsonNode(iterator.next()));
        return result;
    }

    private static Profile.Property profilePropertyFromJsonNode(JsonNode node) {
        return new StaticProfile.StaticProperty(
                node.get("name").asText(),
                node.get("value").asText(),
                node.get("signature").asText()
        );
    }
}
