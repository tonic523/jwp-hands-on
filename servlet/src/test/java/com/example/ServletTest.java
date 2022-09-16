package com.example;

import java.net.http.HttpResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ServletTest {

    @Test
    void testSharedCounter() throws Exception {
        // 톰캣 서버 시작
        TomcatStarter tomcatStarter = TestHttpUtils.createTomcatStarter();
        tomcatStarter.start();

        // shared-counter 페이지를 3번 호출한다.
        String PATH = "/shared-counter";
        TestHttpUtils.send(PATH);
        TestHttpUtils.send(PATH);
        HttpResponse<String> response = TestHttpUtils.send(PATH);

        // 톰캣 서버 종료
        tomcatStarter.stop();

        assertThat(response.statusCode()).isEqualTo(200);

        // expected를 0이 아닌 올바른 값으로 바꿔보자.
        // 예상한 결과가 나왔는가? 왜 이런 결과가 나왔을까?
        // A. 각 Servlet은 하나의 인스턴스를 생성하기 때문에
        assertThat(Integer.parseInt(response.body())).isEqualTo(3);
    }

    @Test
    void testLocalCounter() throws Exception {
        // 톰캣 서버 시작
        final var tomcatStarter = TestHttpUtils.createTomcatStarter();
        tomcatStarter.start();

        // local-counter 페이지를 3번 호출한다.
        final var PATH = "/local-counter";
        TestHttpUtils.send(PATH);
        TestHttpUtils.send(PATH);
        final var response = TestHttpUtils.send(PATH);

        // 톰캣 서버 종료
        tomcatStarter.stop();

        assertThat(response.statusCode()).isEqualTo(200);

        // expected를 0이 아닌 올바른 값으로 바꿔보자.
        // 예상한 결과가 나왔는가? 왜 이런 결과가 나왔을까?
        // 지역 변수이기 때문에 스레드끼리 자원을 공유하지 않는다.
        assertThat(Integer.parseInt(response.body())).isEqualTo(1);
    }
}
