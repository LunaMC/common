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

package io.lunamc.common.host;

import io.lunamc.common.login.session.Profile;
import io.lunamc.common.network.AuthorizedConnection;
import io.lunamc.common.network.Connection;
import io.lunamc.common.network.InitializedConnection;
import io.lunamc.common.play.PlayConnectionInitializer;
import io.lunamc.common.status.StatusProvider;

import java.util.Objects;
import java.util.function.Predicate;

public class StaticVirtualHost implements VirtualHost {

    private final Predicate<InitializedConnection> matcher;
    private final StatusProvider statusProvider;
    private final PlayConnectionInitializer playConnectionInitializer;
    private final boolean authenticated;
    private final VirtualHost.Compression compression;

    public StaticVirtualHost(Predicate<InitializedConnection> matcher,
                             StatusProvider statusProvider,
                             PlayConnectionInitializer playConnectionInitializer,
                             boolean authenticated,
                             VirtualHost.Compression compression) {
        this.matcher = matcher != null ? matcher : t -> false;
        this.statusProvider = Objects.requireNonNull(statusProvider, "statusProvider must not be null");
        this.playConnectionInitializer = Objects.requireNonNull(playConnectionInitializer, "playConnectionInitializer must not be null");
        this.authenticated = authenticated;
        this.compression = compression;
    }

    @Override
    public Predicate<InitializedConnection> getMatcher() {
        return matcher;
    }

    @Override
    public StatusProvider getStatusProvider(Connection connection) {
        return statusProvider;
    }

    @Override
    public PlayConnectionInitializer getPlayConnectionInitializer(AuthorizedConnection connection) {
        return playConnectionInitializer;
    }

    @Override
    public boolean isAuthenticated(InitializedConnection connection) {
        return !connection.isLocallyConnected() && authenticated;
    }

    @Override
    public Compression getCompression(InitializedConnection connection, Profile profile) {
        return compression;
    }

    public static class StaticCompression implements Compression {

        private final int threshold;
        private final int compressionLevel;

        public StaticCompression(int threshold, int compressionLevel) {
            this.threshold = threshold;
            this.compressionLevel = compressionLevel;
        }

        @Override
        public int getThreshold() {
            return threshold;
        }

        @Override
        public int getCompressionLevel() {
            return compressionLevel;
        }
    }
}
