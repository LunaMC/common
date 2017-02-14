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
import com.fasterxml.jackson.databind.JsonSerializer;
import io.lunamc.common.text.BaseComponent;
import io.lunamc.common.text.ComponentColor;
import io.lunamc.common.text.ComponentEvent;

import java.io.IOException;
import java.util.List;

public abstract class AbstractTextComponentSerializer<T extends BaseComponent> extends JsonSerializer<T> {

    protected static void writeSharedProperties(BaseComponent component, JsonGenerator gen) throws IOException {
        writeBoolean(gen, "bold", component.isBold());
        writeBoolean(gen, "italic", component.isItalic());
        writeBoolean(gen, "underlined", component.isUnderlined());
        writeBoolean(gen, "strikethrough", component.isStrikethrough());
        writeBoolean(gen, "obfuscated", component.isObfuscated());

        String insertion = component.getInsertion();
        if (insertion != null)
            gen.writeStringField("insertion", insertion);
        else
            gen.writeOmittedField("insertion");

        ComponentColor color = component.getColor();
        if (color != null)
            gen.writeStringField("color", color.getName());
        else
            gen.writeOmittedField("color");

        writeComponentEvent(gen, "clickEvent", component.getClickEvent());
        writeComponentEvent(gen, "hoverEvent", component.getHoverEvent());

        List<BaseComponent> extra = component.getExtra();
        if (extra != null && !extra.isEmpty())
            gen.writeObjectField("extra", extra);
        else
            gen.writeOmittedField("extra");
    }

    private static void writeBoolean(JsonGenerator gen, String fieldName, Boolean value) throws IOException {
        if (value != null)
            gen.writeBooleanField(fieldName, value);
        else
            gen.writeOmittedField(fieldName);
    }

    private static void writeComponentEvent(JsonGenerator gen, String fieldName, ComponentEvent value) throws IOException {
        if (value != null) {
            gen.writeObjectFieldStart(fieldName);
            gen.writeStringField("action", value.getAction());
            gen.writeStringField("value", value.getValue());
            gen.writeEndObject();
        } else
            gen.writeOmittedField(fieldName);
    }
}
