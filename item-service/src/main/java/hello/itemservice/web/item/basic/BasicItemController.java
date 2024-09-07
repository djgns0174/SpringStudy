package hello.itemservice.web.item.basic;

import hello.itemservice.domain.Item.Item;
import hello.itemservice.domain.Item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(Model model, @PathVariable long itemId) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String add() {
        return "basic/addForm";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {
        itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", item.getId());
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, Model model) {
        Item findItem = itemRepository.findById(itemId);
        model.addAttribute("item", findItem);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item, RedirectAttributes redirectAttributes) {
        itemRepository.update(itemId, item);
        redirectAttributes.addAttribute("itemId", itemId);
        return "redirect:/basic/items/{itemId}";
    }

    @PostConstruct
    public void init() {
        Item item1 = new Item("test1", 1000, 10);
        Item item2 = new Item("test2", 2000, 20);
        itemRepository.save(item1);
        itemRepository.save(item2);
    }
}
