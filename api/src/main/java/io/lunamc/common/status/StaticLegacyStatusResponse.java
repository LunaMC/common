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

public class StaticLegacyStatusResponse implements LegacyStatusResponse {

    private final int protocolVersion;
    private final String serverVersion;
    private final String messageOfTheDay;
    private final int currentPlayerCount;
    private final int maxPLayerCount;

    public StaticLegacyStatusResponse(int protocolVersion,
                                      String serverVersion,
                                      String messageOfTheDay,
                                      int currentPlayerCount,
                                      int maxPLayerCount) {
        this.protocolVersion = protocolVersion;
        this.serverVersion = serverVersion;
        this.messageOfTheDay = messageOfTheDay;
        this.currentPlayerCount = currentPlayerCount;
        this.maxPLayerCount = maxPLayerCount;
    }

    @Override
    public int getProtocolVersion() {
        return protocolVersion;
    }

    @Override
    public String getServerVersion() {
        return serverVersion;
    }

    @Override
    public String getMessageOfTheDay() {
        return messageOfTheDay;
    }

    @Override
    public int getCurrentPlayerCount() {
        return currentPlayerCount;
    }

    @Override
    public int getMaxPlayerCount() {
        return maxPLayerCount;
    }
}
