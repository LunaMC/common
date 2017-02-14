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

public abstract class AbstractStaticBaseComponent implements BaseComponent {

    private final Boolean bold;
    private final Boolean italic;
    private final Boolean underlined;
    private final Boolean strikethrough;
    private final Boolean obfuscated;
    private final ComponentColor color;
    private final String insertion;
    private final ComponentEvent clickEvent;
    private final ComponentEvent hoverEvent;
    private final List<BaseComponent> extra;

    public AbstractStaticBaseComponent(Boolean bold,
                                       Boolean italic,
                                       Boolean underlined,
                                       Boolean strikethrough,
                                       Boolean obfuscated,
                                       ComponentColor color,
                                       String insertion,
                                       ComponentEvent clickEvent,
                                       ComponentEvent hoverEvent,
                                       List<BaseComponent> extra) {
        this.bold = bold;
        this.italic = italic;
        this.underlined = underlined;
        this.strikethrough = strikethrough;
        this.obfuscated = obfuscated;
        this.color = color;
        this.insertion = insertion;
        this.clickEvent = clickEvent;
        this.hoverEvent = hoverEvent;
        this.extra = extra != null && !extra.isEmpty() ? Collections.unmodifiableList(new ArrayList<>(extra)) : null;
    }

    @Override
    public Boolean isBold() {
        return bold;
    }

    @Override
    public Boolean isItalic() {
        return italic;
    }

    @Override
    public Boolean isUnderlined() {
        return underlined;
    }

    @Override
    public Boolean isStrikethrough() {
        return strikethrough;
    }

    @Override
    public Boolean isObfuscated() {
        return obfuscated;
    }

    @Override
    public ComponentColor getColor() {
        return color;
    }

    @Override
    public String getInsertion() {
        return insertion;
    }

    @Override
    public ComponentEvent getClickEvent() {
        return clickEvent;
    }

    @Override
    public ComponentEvent getHoverEvent() {
        return hoverEvent;
    }

    @Override
    public List<BaseComponent> getExtra() {
        return extra;
    }
}
