package hello.itemservice;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import java.util.Locale;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    private MessageSource messageSource;

    @Test
    public void testMessageSource() {
        String hello = messageSource.getMessage("hello", null, Locale.ENGLISH);
        Assertions.assertThat(hello).isEqualTo("hello");

        String result = messageSource.getMessage("hello.name", new Object[]{"spring"}, Locale.KOREA);
        Assertions.assertThat(result).isEqualTo("hello_ko spring");
    }
}
