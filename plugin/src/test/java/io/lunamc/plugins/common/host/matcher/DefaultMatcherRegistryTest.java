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

package io.lunamc.plugins.common.host.matcher;

import io.lunamc.common.host.matcher.MatcherRegistry;
import io.lunamc.common.host.matcher.PortMatcher;
import io.lunamc.plugins.common.host.matcher.DefaultMatcherRegistry;
import org.junit.Assert;
import org.junit.Test;

public class DefaultMatcherRegistryTest {

    @Test
    public void testMatcherRegistration() {
        MatcherRegistry registry = new DefaultMatcherRegistry();
        registry.registerMatcher(PortMatcher.class, model -> model::applies);

        Assert.assertFalse(registry.getModelClasses().isEmpty());
        Assert.assertNotNull(registry.createPredicate(new PortMatcher()));
    }

    @Test(expected = IllegalStateException.class)
    public void testRegistrationAfterClosed() {
        DefaultMatcherRegistry registry = new DefaultMatcherRegistry();
        registry.close();
        registry.registerMatcher(PortMatcher.class, model -> model::applies);
    }
}
