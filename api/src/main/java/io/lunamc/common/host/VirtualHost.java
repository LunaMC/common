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

import java.util.function.Predicate;

public interface VirtualHost {

    String getName();

    Predicate<InitializedConnection> getMatcher();

    StatusProvider getStatusProvider(Connection connection);

    default StatusProvider getStatusProvider(InitializedConnection connection) {
        return getStatusProvider((Connection) connection);
    }

    PlayConnectionInitializer getPlayConnectionInitializer(AuthorizedConnection connection);

    boolean isAuthenticated(InitializedConnection connection);

    Compression getCompression(InitializedConnection connection, Profile profile);

    interface Compression {

        int getThreshold();

        int getCompressionLevel();
    }
}
