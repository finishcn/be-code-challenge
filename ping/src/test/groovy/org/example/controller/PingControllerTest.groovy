package org.example.controller

import org.example.component.FileLimitedLock
import org.example.constant.MappedConstant
import org.example.filter.WebTraceFilter
import org.example.service.PingService
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
@Title("pingController unit test")
@WebFluxTest(controllers = PingController.class)
@Import([WebTraceFilter.class, FileLimitedLock.class, PingService.class])
class PingControllerTest extends Specification {

    @Autowired
    private WebTestClient webClient;

    def "Ping Controller attempts to says 'Hello'"() {
        given:
        // msg = MappedConstant.REQUEST_MSG
        when:
        def response = webClient.get().uri("/ping/service").header(MappedConstant.TRACE_ID, "1")
                .exchange()

        then:
        response.expectStatus().isOk()
        response.expectBody(String.class).returnResult().responseBody == MappedConstant.RESPONSE_MSG
    }

    def "Ping Controller attempts to says 'Hello' without TraceId"() {
        given:
        // msg = MappedConstant.REQUEST_MSG
        when:
        def response = webClient.get().uri("/ping/service").exchange()

        then:
        response.expectStatus().isOk()
    }
}