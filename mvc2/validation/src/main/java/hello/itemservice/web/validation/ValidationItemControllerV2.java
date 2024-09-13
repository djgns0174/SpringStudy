package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j

@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        String itemName = item.getItemName();
        Integer price = item.getPrice();
        Integer quantity = item.getQuantity();

        if (!StringUtils.hasText(itemName)) {
            bindingResult.addError(new FieldError("item", "itemName", itemName,
                    false, null, null, "상품이름은 필수입니다."));
        }

        if (price == null || price < 1000 || price > 1000000) {
            bindingResult.addError(new FieldError("item", "price", price,
                    false, null, null, "가격은 1000~1,000,000 사이입니다."));
        }

        if (quantity == null || quantity > 9999) {
            bindingResult.addError(new FieldError("item", "quantity", quantity,
                    false, null, null, "수량은 9,999까지입니다."));
        }

        if (price != null && quantity != null) {
            if (price * quantity < 10000) {
                bindingResult.addError(new ObjectError("item", null, null,
                        "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + price * quantity));
            }
        }



        //실패로직
        if (bindingResult.hasErrors()) {
            return "validation/v2/addForm";
        }

        //성공로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        String itemName = item.getItemName();
        Integer price = item.getPrice();
        Integer quantity = item.getQuantity();

        if (!StringUtils.hasText(itemName)) {
            bindingResult.addError(new FieldError("item", "itemName", itemName,
                    false, new String[]{"required.item.itemName"}, null, "error"));
        }

        if (price == null || price < 1000 || price > 1000000) {
            bindingResult.addError(new FieldError("item", "price", price,
                    false, new String[]{"range.item.price"}, new Object[]{1000, 10000000}, "error"));
        }

        if (quantity == null || quantity > 9999) {
            bindingResult.addError(new FieldError("item", "quantity", quantity,
                    false, new String[]{"max.item.quantity"}, new Object[]{9999}, "error"));
        }

        if (price != null && quantity != null) {
            if (price * quantity < 10000) {
                bindingResult.addError(new ObjectError("item", new String[]{"totalPriceMin"}, new Object[]{10000, price * quantity},
                        "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + price * quantity));
            }
        }

        //실패로직
        if (bindingResult.hasErrors()) {
            return "validation/v2/addForm";
        }

        //성공로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    @PostMapping("/add")
    public String addItemV4(@Validated  @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        //실패로직
        if (bindingResult.hasErrors()) {
            return "validation/v2/addForm";
        }

        //성공로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v2/items/{itemId}";
    }

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        log.info("init binder {}", dataBinder);
        dataBinder.addValidators(itemValidator);
    }

}

