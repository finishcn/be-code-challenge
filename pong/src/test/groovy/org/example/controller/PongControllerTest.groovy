package org.example.controller

import org.example.constant.MappedConstant
import org.example.filter.LogFilter
import org.example.filter.ThrottlingInterceptor
import org.example.filter.WebFluxTraceFilter
import org.example.service.PongService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification
import spock.lang.Title

/**
 * PongController test
 * @author liyu.caelus 2024/12/29
 *
 */

@ActiveProfiles("dev")
@Title("PongController unit test")
@WebFluxTest(controllers = PongController.class)
@Import([PongService.class, LogFilter.class, ThrottlingInterceptor.class, WebFluxTraceFilter.class])
class PongControllerTest extends Specification {

    @Autowired
    private WebTestClient webClient
    @Autowired
    private ThrottlingInterceptor throttlingInterceptor

    def "client send “Hello” to Pong Controller then should respond with “World”"() {
        given:
        def msg = MappedConstant.REQUEST_MSG
        when:
        def response = webClient.get().uri("/pong/service/" + msg).header(MappedConstant.TRACE_ID, "1")
                .exchange()
        then:
        response.expectStatus().isOk()
        response.expectBody(String.class).returnResult().responseBody == MappedConstant.RESPONSE_MSG
    }

    def "client send “Hello” to Pong Controller then expected to be throttled with 429 status"() {
        given:
        def msg = MappedConstant.REQUEST_MSG
        when:
        def response
        for (int i = 0; i < 10; i++) {
            if (!throttlingInterceptor.rateLimiter.tryAcquire()) {
                response = webClient.get().uri("/pong/service/" + msg).exchange()
                break
            }
        }

        then:
        response.expectStatus().is4xxClientError()
    }
}