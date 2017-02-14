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
import io.lunamc.common.text.BaseComponent;
import io.lunamc.common.text.TranslationComponent;

import java.io.IOException;
import java.util.List;

public class TranslationComponentSerializer extends AbstractTextComponentSerializer<TranslationComponent> {

    public static final TranslationComponentSerializer INSTANCE = new TranslationComponentSerializer();

    @Override
    public void serialize(TranslationComponent value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        writeSharedProperties(value, gen);

        gen.writeStringField("translate", value.getTranslate());
        List<BaseComponent> with = value.getWith();
        if (with != null && !with.isEmpty())
            gen.writeObjectField("with", with);

        gen.writeEndObject();
    }
}
