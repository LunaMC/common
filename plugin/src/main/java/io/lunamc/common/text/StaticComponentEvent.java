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

package io.lunamc.common.text;

import java.util.Objects;

public class StaticComponentEvent implements ComponentEvent {

    private final String action;
    private final String value;

    public StaticComponentEvent(String action, String value) {
        this.action = Objects.requireNonNull(action, "action must not be null");
        this.value = Objects.requireNonNull(value, "value must not be null");
    }

    @Override
    public String getAction() {
        return action;
    }

    @Override
    public String getValue() {
        return value;
    }
}
