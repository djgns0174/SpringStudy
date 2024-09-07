package hello.itemservice.domain.Item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ItemRepositoryTest {

    private ItemRepository itemRepository = new ItemRepository();

    @Test
    void save(){
        Item item = new Item("test", 123, 123123);
        itemRepository.save(item);

        Item findItem = itemRepository.findById(item.getId());
        Assertions.assertThat(findItem).isEqualTo(item);
    }

    @Test
    void findAll(){
        Item item1 = new Item("test1", 123, 123123);
        itemRepository.save(item1);

        Item item2 = new Item("test2", 123, 123123);
        itemRepository.save(item2);

        List<Item> items = itemRepository.findAll();
        Assertions.assertThat(items).hasSize(2);
    }
    
    @Test
    void update(){
        Item item1 = new Item("test1", 123, 123123);
        itemRepository.save(item1);

        Item updateParam = new Item("update", 1234, 12341234);
        itemRepository.update(item1.getId(), updateParam);

        Item item = itemRepository.findById(item1.getId());
        Assertions.assertThat(item.getItemName()).isEqualTo("update");
    }
}