/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.weld.tests.interceptors.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.interceptor.InvocationContext;

@ApplicationScoped
public class ThreadPool {

    private ExecutorService executor;

    public ThreadPool() {
    }

    @Inject
    public void init() {
        this.executor = Executors.newSingleThreadExecutor();
    }

    public Future<Object> submit(final InvocationContext ctx) {
        return executor.submit(ctx::proceed);
    }

    public <T> Future<T> submit(final Callable<T> callable) {
        return executor.submit(callable);
    }

    @PreDestroy
    public void tearDown() {
        executor.shutdown();
    }
}
