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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class StaticTranslationComponent extends AbstractStaticBaseComponent implements TranslationComponent {

    private final String translate;
    private final List<BaseComponent> with;

    public StaticTranslationComponent(Boolean bold,
                                      Boolean italic,
                                      Boolean underlined,
                                      Boolean strikethrough,
                                      Boolean obfuscated,
                                      ComponentColor color,
                                      String insertion,
                                      ComponentEvent clickEvent,
                                      ComponentEvent hoverEvent,
                                      List<BaseComponent> extra,
                                      String translate,
                                      List<BaseComponent> with) {
        super(bold, italic, underlined, strikethrough, obfuscated, color, insertion, clickEvent, hoverEvent, extra);

        this.translate = Objects.requireNonNull(translate, "translate must not be null");
        this.with = with != null && !with.isEmpty() ? Collections.unmodifiableList(new ArrayList<>(with)) : null;
    }

    @Override
    public String getTranslate() {
        return translate;
    }

    @Override
    public List<BaseComponent> getWith() {
        return with;
    }
}
