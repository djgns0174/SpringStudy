package hello.itemservice.domain.item;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ItemSaveForm {

    private Long id;

    @NotBlank(message = "공백을 입력할 수 없습니다.")
    private String itemName;

    @NotNull
    @Range(min = 1000, max = 10000000)
    private Integer price;

    @NotNull
    @Max(9999)
    private Integer quantity;

    public ItemSaveForm() {
    }

    public ItemSaveForm(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
