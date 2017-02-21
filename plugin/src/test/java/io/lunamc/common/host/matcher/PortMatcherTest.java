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

package io.lunamc.common.host.matcher;

import io.lunamc.common.network.InitializedConnection;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class PortMatcherTest {

    @Test
    public void testPortMatcher() {
        PortMatcher matcher = new PortMatcher();
        matcher.setPort(25565);

        Assert.assertTrue(matcher.applies(mockConnection(25565)));
        Assert.assertFalse(matcher.applies(mockConnection(25564)));
        Assert.assertFalse(matcher.applies(mockConnection(25566)));
    }

    private static InitializedConnection mockConnection(int port) {
        InitializedConnection connection = Mockito.mock(InitializedConnection.class);
        Mockito.when(connection.getServerPort()).thenReturn(port);
        return connection;
    }
}
