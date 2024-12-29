/**
 * liyu.caelus 2024/12/29
 * Copyright
 */
package org.example.base;

import java.nio.channels.FileLock;

/**
 * bask lock interface
 *
 * @author liyu.caelus 2024/12/29
 */
public interface LimitedLock {

    FileLock lock();

    void unlock(FileLock lock);
}
