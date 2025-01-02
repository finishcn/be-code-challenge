package org.example.controller

import cn.hutool.json.JSONObject
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
 * @author liyu.caelus 2024/12/31
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

    def "get method test"() {
        given:
        def msg = MappedConstant.REQUEST_MSG
        when:
        def response = webClient.get().uri("/pong/send/" + msg).header(MappedConstant.TRACE_ID, "1")
                .exchange()
        then:
        response.expectStatus().isOk()
        response.expectBody(String.class).returnResult().responseBody == MappedConstant.RESPONSE_MSG
    }

    def "post method test"() {
        given:
        def msg = MappedConstant.REQUEST_MSG
        when:
        JSONObject data = new JSONObject();
        data.putOnce("msg","Hello")
        def response = webClient.post().uri("/pong/service").bodyValue(data.toString())
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