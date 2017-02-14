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

package io.lunamc.common.json.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import io.lunamc.common.json.jackson.databind.login.session.ProfileDeserializer;
import io.lunamc.common.json.jackson.databind.text.ComponentEventSerializer;
import io.lunamc.common.json.jackson.databind.text.ScoreComponentSerializer;
import io.lunamc.common.json.jackson.databind.status.StatusResponseSerializer;
import io.lunamc.common.json.jackson.databind.text.TextComponentSerializer;
import io.lunamc.common.json.jackson.databind.text.TranslationComponentSerializer;
import io.lunamc.common.login.session.Profile;
import io.lunamc.common.status.StatusResponse;
import io.lunamc.common.text.ComponentEvent;
import io.lunamc.common.text.ScoreComponent;
import io.lunamc.common.text.TextComponent;
import io.lunamc.common.text.TranslationComponent;

class LunaJacksonModule extends SimpleModule {

    LunaJacksonModule() {
        addDeserializer(Profile.class, ProfileDeserializer.INSTANCE);

        addSerializer(StatusResponse.class, StatusResponseSerializer.INSTANCE);

        addSerializer(ScoreComponent.class, ScoreComponentSerializer.INSTANCE);
        addSerializer(TextComponent.class, TextComponentSerializer.INSTANCE);
        addSerializer(TranslationComponent.class, TranslationComponentSerializer.INSTANCE);
        addSerializer(ComponentEvent.class, ComponentEventSerializer.INSTANCE);
    }
}
