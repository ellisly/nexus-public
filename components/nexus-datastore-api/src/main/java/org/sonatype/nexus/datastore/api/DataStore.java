/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2008-present Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.datastore.api;

import org.sonatype.goodies.lifecycle.Lifecycle;
import org.sonatype.nexus.transaction.TransactionalStore;
import org.sonatype.nexus.transaction.UnitOfWork;

/**
 * Each {@link DataStore} contains a number of {@link DataAccess} mappings accessible via {@link DataSession}s.
 *
 * @since 3.next
 */
public interface DataStore<S extends DataSession<?>>
    extends TransactionalStore<S>, Lifecycle
{
  /**
   * Configure the data store; changes won't take effect until the store is (re)started.
   */
  void setConfiguration(DataStoreConfiguration configuration);

  /**
   * @return the data store's configuration.
   */
  DataStoreConfiguration getConfiguration();

  /**
   * @return {@code true} if the data store has been started.
   */
  boolean isStarted();

  /**
   * Registers the given {@link DataAccess} type with this store.
   */
  void register(Class<? extends DataAccess> accessType);

  /**
   * Unregisters the given {@link DataAccess} type with this store.
   */
  void unregister(Class<? extends DataAccess> accessType);

  /**
   * Permanently stops this data store.
   */
  void shutdown() throws Exception;

  /**
   * {@link DataAccess} mapping for the given type; requires an open session.
   */
  static <D extends DataAccess> D access(Class<D> type) {
    return UnitOfWork.<DataSession<?>> currentSession().access(type);
  }
}
