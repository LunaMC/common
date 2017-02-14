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

package io.lunamc.common.json.jackson.databind.text;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.lunamc.common.text.ScoreComponent;

import java.io.IOException;

public class ScoreComponentSerializer extends AbstractTextComponentSerializer<ScoreComponent> {

    public static final ScoreComponentSerializer INSTANCE = new ScoreComponentSerializer();

    @Override
    public void serialize(ScoreComponent value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        writeSharedProperties(value, gen);

        ScoreComponent.Score score = value.getScore();
        if (score == null)
            throw new IOException("score of score component not provided");
        gen.writeObjectFieldStart("score");
        gen.writeStringField("name", score.getName());
        gen.writeStringField("value", score.getValue());
        gen.writeStringField("objective", score.getObjective());
        gen.writeEndObject();

        gen.writeEndObject();
    }
}
