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
import io.lunamc.common.text.ScoreComponent;
import io.lunamc.common.text.StaticScoreComponent;

import java.util.ArrayList;
import java.util.List;

public class DefaultScoreComponentBuilder implements ScoreComponentBuilder {

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
    private String scoreName;
    private String scoreObjective;
    private String scoreValue;

    @Override
    public ScoreComponentBuilder bold(Boolean bold) {
        this.bold = bold;
        return this;
    }

    @Override
    public ScoreComponentBuilder italic(Boolean italic) {
        this.italic = italic;
        return this;
    }

    @Override
    public ScoreComponentBuilder underlined(Boolean underlined) {
        this.underlined = underlined;
        return this;
    }

    @Override
    public ScoreComponentBuilder strikethrough(Boolean strikethrough) {
        this.strikethrough = strikethrough;
        return this;
    }

    @Override
    public ScoreComponentBuilder obfuscated(Boolean obfuscated) {
        this.obfuscated = obfuscated;
        return this;
    }

    @Override
    public ScoreComponentBuilder color(ComponentColor color) {
        this.color = color;
        return this;
    }

    @Override
    public ScoreComponentBuilder insertion(String insertion) {
        this.insertion = insertion;
        return this;
    }

    @Override
    public ScoreComponentBuilder clickEvent(ComponentEvent clickEvent) {
        this.clickEvent = clickEvent;
        return this;
    }

    @Override
    public ScoreComponentBuilder hoverEvent(ComponentEvent hoverEvent) {
        this.hoverEvent = hoverEvent;
        return this;
    }

    @Override
    public ScoreComponentBuilder addExtra(BaseComponent component) {
        if (extra == null)
            extra = new ArrayList<>();
        extra.add(component);
        return this;
    }

    @Override
    public ScoreComponentBuilder scoreName(String scoreName) {
        this.scoreName = scoreName;
        return this;
    }

    @Override
    public ScoreComponentBuilder scoreObjective(String scoreObjective) {
        this.scoreObjective = scoreObjective;
        return this;
    }

    @Override
    public ScoreComponentBuilder scoreValue(String scoreValue) {
        this.scoreValue = scoreValue;
        return this;
    }

    @Override
    public ScoreComponent build() {
        return new StaticScoreComponent(
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
                new StaticScoreComponent.StaticScore(
                        scoreName,
                        scoreObjective,
                        scoreValue
                )
        );
    }
}
