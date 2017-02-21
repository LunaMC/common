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
import io.lunamc.common.network.InitializedConnection;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class DefaultMatcherRegistry implements MatcherRegistry {

    private final ConcurrentMap<Class<?>, Function<Object, Predicate<InitializedConnection>>> factories = new ConcurrentHashMap<>();
    private volatile boolean closed;

    @Override
    public <T> void registerMatcher(Class<T> modelClass, Function<T, Predicate<InitializedConnection>> factory) {
        if (closed)
            throw new IllegalStateException("Matchers must be registered during initialization");
        factories.put(modelClass, (t) -> factory.apply(modelClass.cast(t)));
    }

    @Override
    public Collection<Class<?>> getModelClasses() {
        return factories.keySet();
    }

    @Override
    public Predicate<InitializedConnection> createPredicate(Object modelClass) {
        Objects.requireNonNull(modelClass, "modelClass must not be null");
        Function<Object, Predicate<InitializedConnection>> factory = factories.get(modelClass.getClass());
        if (factory == null)
            throw new UnsupportedOperationException("Unknown model class: " + modelClass.getClass().getName());
        return factory.apply(modelClass);
    }

    public void close() {
        closed = true;
    }
}
