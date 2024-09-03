package hello.core;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StatefulService {
    private int price;

    public void order(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
