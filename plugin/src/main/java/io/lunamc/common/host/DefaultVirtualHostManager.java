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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Predicate;

public class DefaultVirtualHostManager implements VirtualHostManager {

    private final Map<String, VirtualHost> hosts = new LinkedHashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private VirtualHost fallbackHost;

    @Override
    public Optional<VirtualHost> getVirtualHostByName(String name) {
        Lock lock = this.lock.readLock();
        lock.lock();
        try {
            if (fallbackHost != null && name.equals(fallbackHost.getName()))
                return Optional.of(fallbackHost);
            return Optional.ofNullable(hosts.get(name));
        } finally {
            lock.unlock();
        }
    }

    public VirtualHost getFallbackHost() {
        Lock lock = this.lock.readLock();
        lock.lock();
        try {
            if (fallbackHost == null)
                throw new IllegalStateException("No fallback host set");
            return fallbackHost;
        } finally {
            lock.unlock();
        }
    }

    public void setFallbackHost(VirtualHost fallbackHost) {
        Lock lock = this.lock.writeLock();
        lock.lock();
        try {
            this.fallbackHost = fallbackHost;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void addHost(VirtualHost host) {
        String name = host.getName();
        Lock lock = this.lock.writeLock();
        lock.lock();
        VirtualHost previous;
        try {
            previous = hosts.putIfAbsent(name, host);
        } finally {
            lock.unlock();
        }
        if (previous != null)
            throw new IllegalStateException("There is already a virtual host named \"" + name + '"');
    }

    @Override
    public VirtualHost matchHost(InitializedConnection connection) {
        Lock lock = this.lock.readLock();
        lock.lock();
        try {
            Optional<VirtualHost> host = hosts.values().stream()
                    .filter(virtualHost -> {
                        Predicate<InitializedConnection> matcher = virtualHost.getMatcher();
                        return matcher != null && matcher.test(connection);
                    })
                    .findFirst();
            return host.orElse(fallbackHost);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Collection<VirtualHost> getHosts() {
        Lock lock = this.lock.readLock();
        lock.lock();
        try {
            return hosts.values();
        } finally {
            lock.unlock();
        }
    }
}
