/**
 * liyu.caelus 2024/12/29
 * Copyright
 */
package org.example.component;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.StrBuilder;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.example.base.LimitedLock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liyu.caelus 2024/12/29
 */
@Slf4j
@Component
public class FileLimitedLock implements LimitedLock {

    @Value("${config.file-lock}")
    private String lockFile;
    @Value("${config.num:2}")
    private Integer num;

    private final List<FileChannel> channelList = new ArrayList<>();

    @PostConstruct
    public void init() throws FileNotFoundException {
        final StrBuilder builder = new StrBuilder();
        for (int i = 0; i < num; ++i) {
            builder.append(lockFile).append(".").append(i);
            File file = FileUtil.touch(builder.toString());
            log.info(file.getAbsolutePath());
            channelList.add(new FileOutputStream(file, true).getChannel());
            builder.reset();
        }
    }

    @Override
    public FileLock lock() {
        FileLock lock = null;
        for (FileChannel channel : channelList) {
            try {
                lock = channel.tryLock();
            } catch (Exception ig) {
                log.error("try lock error");
            }
            if (null != lock) {
                return lock;
            }
        }
        return null;
    }

    @Override
    public void unlock(FileLock lock) {
        try {
            lock.release();
        } catch (IOException ig) {
            log.error("lock release error");
        }
    }

    @PreDestroy
    public void destroy() {
        channelList.forEach(channel -> {
                    try {
                        channel.close();
                    } catch (IOException ig) {
                        log.error("close file error");
                    }
                }
        );
    }
}
