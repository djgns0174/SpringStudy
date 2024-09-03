package hello.core.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

@Slf4j
public class NetworkClient {

    private String url;

    public NetworkClient() {
        log.info("생성자 호출 : url = {}", url);
    }

    public void connect() {
        log.info("connect : {}", url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void disconnect(){
        log.info("disconnect : {}", url);
    }

    @PostConstruct
    public void init(){
        connect();
        log.info("초기화 연결 메시지");
    }

    @PreDestroy
    public void close(){
        disconnect();
    }
}
