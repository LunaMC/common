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

package io.lunamc.common.async;

import io.lunamc.platform.service.Shutdownable;
import io.lunamc.platform.service.Startable;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DefaultExecutorProvider implements ExecutorProvider, Startable, Shutdownable {

    private ExecutorService cachedExecutorService;
    private ExecutorService indestructibleCachedExecutorService;

    @Override
    public ExecutorService getCachedExecutorService() {
        if (indestructibleCachedExecutorService == null)
            throw new IllegalStateException("Not started");
        return indestructibleCachedExecutorService;
    }

    @Override
    public void start() {
        cachedExecutorService = Executors.newCachedThreadPool();
        indestructibleCachedExecutorService = new IndestructibleExecutorService(cachedExecutorService);
    }

    @Override
    public int getStartPriority() {
        return 100;
    }

    @Override
    public void shutdown() {
        if (cachedExecutorService != null) {
            cachedExecutorService.shutdown();
            try {
                if (!cachedExecutorService.awaitTermination(10, TimeUnit.SECONDS))
                    cachedExecutorService.shutdownNow();
            } catch (InterruptedException ignore) {
            }
        }
        indestructibleCachedExecutorService = null;
    }

    @Override
    public int getShutdownPriority() {
        return 0;
    }

    private static class IndestructibleExecutorService implements ExecutorService {

        private final ExecutorService delegate;

        public IndestructibleExecutorService(ExecutorService delegate) {
            this.delegate = Objects.requireNonNull(delegate, "delegate must not be null");
        }

        @Override
        public void shutdown() {
            throw new UnsupportedOperationException("Not allowed on a " + getClass().getSimpleName());
        }

        @Override
        public List<Runnable> shutdownNow() {
            throw new UnsupportedOperationException("Not allowed on a " + getClass().getSimpleName());
        }

        @Override
        public boolean isShutdown() {
            return delegate.isShutdown();
        }

        @Override
        public boolean isTerminated() {
            return delegate.isTerminated();
        }

        @Override
        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            return delegate.awaitTermination(timeout, unit);
        }

        @Override
        public <T> Future<T> submit(Callable<T> task) {
            return delegate.submit(task);
        }

        @Override
        public <T> Future<T> submit(Runnable task, T result) {
            return delegate.submit(task, result);
        }

        @Override
        public Future<?> submit(Runnable task) {
            return delegate.submit(task);
        }

        @Override
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
            return delegate.invokeAll(tasks);
        }

        @Override
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
            return delegate.invokeAll(tasks, timeout, unit);
        }

        @Override
        public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
            return delegate.invokeAny(tasks);
        }

        @Override
        public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return delegate.invokeAny(tasks, timeout, unit);
        }

        @Override
        public void execute(Runnable command) {
            delegate.execute(command);
        }
    }
}
