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

package io.lunamc.common.status;

import io.lunamc.common.text.TextComponent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class StaticStatusResponse implements StatusResponse {

    private final Version version;
    private final Players players;
    private final TextComponent description;
    private final String favicon;

    public StaticStatusResponse(Version version, Players players, TextComponent description, String favicon) {
        this.version = version;
        this.players = players;
        this.description = description;
        this.favicon = favicon;
    }

    @Override
    public Version getVersion() {
        return version;
    }

    @Override
    public Players getPlayers() {
        return players;
    }

    @Override
    public TextComponent getDescription() {
        return description;
    }

    @Override
    public String getFavicon() {
        return favicon;
    }

    public static class StaticVersion implements Version {

        private final String name;
        private final int protocol;

        public StaticVersion(String name, int protocol) {
            this.name = name;
            this.protocol = protocol;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public int getProtocol() {
            return protocol;
        }
    }

    public static class StaticPlayers implements Players {

        private final int max;
        private final int online;
        private final List<? extends PlayerSample> sample;

        public StaticPlayers(int max, int online, Collection<? extends PlayerSample> sample) {
            this.max = max;
            this.online = online;
            this.sample = Collections.unmodifiableList(new ArrayList<>(sample));
        }

        @Override
        public int getMax() {
            return max;
        }

        @Override
        public int getOnline() {
            return online;
        }

        @Override
        public List<? extends PlayerSample> getSample() {
            return sample;
        }
    }

    public static class StaticPlayerSample implements PlayerSample {

        private final String name;
        private final String id;

        public StaticPlayerSample(String name, String id) {
            this.name = name;
            this.id = id;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getId() {
            return id;
        }
    }
}
