package org.example.controller

import org.example.component.FileLimitedLock
import org.example.constant.MappedConstant
import org.example.service.PingClient
import org.example.service.PingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import org.springframework.data.mongodb.core.MongoTemplate
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
@Title("pingController unit test")
@WebFluxTest(controllers = PingController.class)
@Import([FileLimitedLock.class, PingClient.class, PingService.class])
class PingControllerTest extends Specification {

    @Autowired
    private WebTestClient webClient;

    def "Ping Controller do send service"() {
        given:
        //def msg = MappedConstant.REQUEST_MSG
        when:
        def response = webClient.get().uri("/ping/send").header(MappedConstant.TRACE_ID, "1")
                .exchange()

        then:
        response.expectStatus().isOk()
        response.expectBody(String.class).returnResult().responseBody == MappedConstant.RESPONSE_MSG
    }

    def "Ping Controller do send service set send message"() {
        given:
        def msg = MappedConstant.REQUEST_MSG
        when:
        def response = webClient.post().uri("/ping/message?msg=" + msg).exchange()

        then:
        response.expectStatus().isOk()
    }
}