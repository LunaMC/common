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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lunamc.common.json.JsonMapper;

import java.io.IOException;

public class JacksonJsonMapper implements JsonMapper {

    private ObjectMapper objectMapper;

    public ObjectMapper getObjectMapper() {
        if (objectMapper == null)
            objectMapper = createObjectMapper();
        return objectMapper;
    }

    @Override
    public String serialize(Object model) {
        try {
            return getObjectMapper().writeValueAsString(model);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T deserialize(Class<T> targetClass, String json) {
        try {
            return getObjectMapper().readValue(json, targetClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected ObjectMapper createObjectMapper() {
        return new ObjectMapper()
                .registerModule(new LunaJacksonModule());
    }
}
