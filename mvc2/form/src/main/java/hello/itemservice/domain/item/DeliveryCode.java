package hello.itemservice.domain.item;

import lombok.Data;

@Data
public class DeliveryCode {
    private String code;
    private String displayName;

    public DeliveryCode(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}