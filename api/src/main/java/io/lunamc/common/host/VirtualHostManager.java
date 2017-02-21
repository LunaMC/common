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

import io.lunamc.common.network.InitializedConnection;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface VirtualHostManager {

    Optional<VirtualHost> getVirtualHostByName(String name);

    void addHost(VirtualHost host);

    VirtualHost matchHost(InitializedConnection connection);

    Collection<VirtualHost> getHosts();

    VirtualHost getFallbackHost();

    void setFallbackHost(VirtualHost fallbackHost);
}
