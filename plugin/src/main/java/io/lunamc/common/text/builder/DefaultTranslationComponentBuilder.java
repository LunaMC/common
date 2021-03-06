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
import io.lunamc.common.text.StaticTranslationComponent;
import io.lunamc.common.text.TranslationComponent;

import java.util.ArrayList;
import java.util.List;

public class DefaultTranslationComponentBuilder implements TranslationComponentBuilder {

    private Boolean bold;
    private Boolean italic;
    private Boolean underlined;
    private Boolean strikethrough;
    private Boolean obfuscated;
    private ComponentColor color;
    private String insertion;
    private ComponentEvent clickEvent;
    private ComponentEvent hoverEvent;
    private List<BaseComponent> extra;
    private String translate;
    private List<BaseComponent> with;

    @Override
    public TranslationComponentBuilder bold(Boolean bold) {
        this.bold = bold;
        return this;
    }

    @Override
    public TranslationComponentBuilder italic(Boolean italic) {
        this.italic = italic;
        return this;
    }

    @Override
    public TranslationComponentBuilder underlined(Boolean underlined) {
        this.underlined = underlined;
        return this;
    }

    @Override
    public TranslationComponentBuilder strikethrough(Boolean strikethrough) {
        this.strikethrough = strikethrough;
        return this;
    }

    @Override
    public TranslationComponentBuilder obfuscated(Boolean obfuscated) {
        this.obfuscated = obfuscated;
        return this;
    }

    @Override
    public TranslationComponentBuilder color(ComponentColor color) {
        this.color = color;
        return this;
    }

    @Override
    public TranslationComponentBuilder insertion(String insertion) {
        this.insertion = insertion;
        return this;
    }

    @Override
    public TranslationComponentBuilder clickEvent(ComponentEvent clickEvent) {
        this.clickEvent = clickEvent;
        return this;
    }

    @Override
    public TranslationComponentBuilder hoverEvent(ComponentEvent hoverEvent) {
        this.hoverEvent = hoverEvent;
        return this;
    }

    @Override
    public TranslationComponentBuilder addExtra(BaseComponent component) {
        if (extra == null)
            extra = new ArrayList<>();
        extra.add(component);
        return this;
    }

    @Override
    public TranslationComponentBuilder translate(String translate) {
        this.translate = translate;
        return this;
    }

    @Override
    public TranslationComponentBuilder addWith(BaseComponent component) {
        if (with == null)
            with = new ArrayList<>();
        with.add(component);
        return this;
    }

    @Override
    public TranslationComponent build() {
        return new StaticTranslationComponent(
                bold,
                italic,
                underlined,
                strikethrough,
                obfuscated,
                color,
                insertion,
                clickEvent,
                hoverEvent,
                extra,
                translate,
                with
        );
    }
}
