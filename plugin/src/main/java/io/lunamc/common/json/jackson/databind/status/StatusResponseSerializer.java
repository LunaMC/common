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

package io.lunamc.common.json.jackson.databind.status;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.lunamc.common.status.StatusResponse;

import java.io.IOException;

public class StatusResponseSerializer extends JsonSerializer<StatusResponse> {

    public static final StatusResponseSerializer INSTANCE = new StatusResponseSerializer();

    @Override
    public void serialize(StatusResponse value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();

        StatusResponse.Version version = value.getVersion();
        gen.writeObjectFieldStart("version");
        gen.writeStringField("name", version.getName());
        gen.writeNumberField("protocol", version.getProtocol());
        gen.writeEndObject();

        StatusResponse.Players players = value.getPlayers();
        gen.writeObjectFieldStart("players");
        gen.writeNumberField("max", players.getMax());
        gen.writeNumberField("online", players.getOnline());
        gen.writeArrayFieldStart("sample");
        for (StatusResponse.PlayerSample sample : players.getSample()) {
            gen.writeStartObject();
            gen.writeStringField("name", sample.getName());
            gen.writeStringField("id", sample.getId());
            gen.writeEndObject();
        }
        gen.writeEndArray();
        gen.writeEndObject();

        gen.writeObjectField("description", value.getDescription());
        String favicon = value.getFavicon();
        if (favicon != null)
            gen.writeStringField("favicon", favicon);
        else
            gen.writeOmittedField("favicon");

        gen.writeEndObject();
    }
}
