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

package io.lunamc.common.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class UuidUtilsTest {

    @Test
    public void testParseDelimitedUuid() {
        UUID uuid = UUID.randomUUID();
        Assert.assertEquals(uuid, UuidUtils.parseUuid(uuid.toString()));
        Assert.assertEquals(uuid, UuidUtils.parseUuid(uuid.toString().toUpperCase()));
    }

    @Test
    public void testParseConcatenatedUuid() {
        UUID uuid = UUID.randomUUID();
        Assert.assertEquals(uuid, UuidUtils.parseUuid(uuid.toString().replace("-", "")));
        Assert.assertEquals(uuid, UuidUtils.parseUuid(uuid.toString().replace("-", "").toUpperCase()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseInvalidUuid1() {
        UuidUtils.parseUuid("test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseInvalidUuid2() {
        UuidUtils.parseUuid("xd0f3863-4251-473d-9dfb-6f602548cf96");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseInvalidUuid3() {
        UuidUtils.parseUuid("xd0f38634251473d9dfb6f602548cf96");
    }
}
