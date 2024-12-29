package org.example.controller

import org.example.component.FileLimitedLock
import org.example.constant.HttpCode
import org.example.constant.MappedConstant
import org.example.service.PingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification
import spock.lang.Title

/**
 * service test
 * @author liyu.caelus 2024/12/29
 *
 */

@ActiveProfiles("dev")
@SpringBootTest
@Title("PingService unit test")
@Import([FileLimitedLock.class, PingService.class])
class PingServiceTest extends Specification {

    @Autowired
    private PingService pingService
    @Autowired
    private FileLimitedLock fileLimitedLock

    def "Ping Service attempts to says “Hello” to Pong Service around every 1 secondand then Pong service should respond with “World”"() {
        given:
        def msg = MappedConstant.REQUEST_MSG

        when:
        def result = pingService.service(msg)

        then:
        result == MappedConstant.RESPONSE_MSG
    }

    def "Request not send as being 'rate limited'"() {
        given:
        def msg = MappedConstant.REQUEST_MSG

        when:
        def result
        for (int i = 0; i < 10; i++) {
            if (null == fileLimitedLock.lock()) {
                result = pingService.service(msg)
                break
            }
        }
        fileLimitedLock.destroy()

        then:
        result == HttpCode.PING_LIMITED_MSG
    }
}