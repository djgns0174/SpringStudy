package hello.core.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        log.info("[" + uuid + "] " + "[" + requestURL + "]  : " + message);
    }

    @PostConstruct
    public void init(){
        uuid = UUID.randomUUID().toString();
        log.info("[" + uuid + "] request scope bean create:" + this);
    }

    @PreDestroy
    public void close(){
        log.info("[" + uuid + "] request scope bean close:" + this);
    }
}
