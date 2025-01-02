package org.example.controller

import org.example.constant.MappedConstant
import org.example.service.PongService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification
import spock.lang.Title

/**
 * service test
 * @author liyu.caelus 2024/12/31
 *
 */

@ActiveProfiles("dev")
@SpringBootTest
@Title("PongService unit test")
class PongServiceTest extends Specification {

    @Autowired
    private PongService pongService

    def "service should return with “World”"() {
        given:
        def msg = MappedConstant.REQUEST_MSG

        when:
        def result = pongService.respond(msg)

        then:
        result == MappedConstant.RESPONSE_MSG
    }
}