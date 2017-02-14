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

package io.lunamc.common.text.builder;

import io.lunamc.common.text.ComponentEvent;
import io.lunamc.common.text.StaticComponentEvent;

public class DefaultComponentEventBuilder implements ComponentEventBuilder {

    private String action;
    private String value;

    @Override
    public ComponentEventBuilder action(String action) {
        this.action = action;
        return this;
    }

    @Override
    public ComponentEventBuilder value(String value) {
        this.value = value;
        return this;
    }

    @Override
    public ComponentEvent build() {
        return new StaticComponentEvent(action, value);
    }
}
