package hello.core.common;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogService {

    private final MyLogger myLogger;

    public void log(String id) {
        myLogger.log("Service id : " + id);
    }
}