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

import io.lunamc.common.text.BaseComponent;
import io.lunamc.common.text.ComponentColor;
import io.lunamc.common.text.ComponentEvent;
import io.lunamc.common.utils.Builder;

public interface ComponentBuilder<T extends ComponentBuilder, U extends BaseComponent> extends Builder<U> {

    T bold(Boolean bold);

    T italic(Boolean italic);

    T underlined(Boolean underlined);

    T strikethrough(Boolean strikethrough);

    T obfuscated(Boolean obfuscated);

    T color(ComponentColor color);

    T insertion(String insertion);

    T clickEvent(ComponentEvent clickEvent);

    default T clickEvent(ComponentEventBuilder eventBuilder) {
        return clickEvent(eventBuilder.build());
    }

    T hoverEvent(ComponentEvent hoverEvent);

    default T hoverEvent(ComponentEventBuilder eventBuilder) {
        return hoverEvent(eventBuilder.build());
    }

    T addExtra(BaseComponent component);

    default T addExtra(ComponentBuilder<?, ?> componentBuilder) {
        return addExtra(componentBuilder.build());
    }
}
