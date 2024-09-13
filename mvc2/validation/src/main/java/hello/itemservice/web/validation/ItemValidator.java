package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@Slf4j
public class ItemValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Item.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Item item = (Item) o;

        String itemName = item.getItemName();
        Integer price = item.getPrice();
        Integer quantity = item.getQuantity();

        if (!StringUtils.hasText(itemName)) {
            errors.rejectValue("itemName", "required");
        }

        if (price == null || price < 1000 || price > 1000000) {
            errors.rejectValue("price", "range", new Object[]{1000, 10000000}, null);
        }

        if (quantity == null || quantity > 9999) {
            errors.rejectValue("quantity", "max", new Object[]{9999}, null);
        }

        if (price != null && quantity != null) {
            int resultPrice = price * quantity;
            if (resultPrice < 10000) {
                errors.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

    }
}
