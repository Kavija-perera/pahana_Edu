package dao;

import com.pahanaedu.model.Item;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    private final String filePath;

    public ItemDAO(String filePath) {
        this.filePath = filePath;
    }

    public List<Item> getAllItems() throws IOException {
        List<Item> Items = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) return Items;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    String itemId = data[0];
                    String name = data[1];
                    double price = Double.parseDouble(data[2]);
                    Items.add(new Item(itemId, name, price));
                }
            }
        }
        return Items;
    }

    public void addItem(Item item) throws IOException {
        List<Item> Items = getAllItems();
        Items.add(item);
        saveItems(Items);
    }

    public void updateItem(Item updatedItem) throws IOException {
        List<Item> Items = getAllItems();
        for (int i = 0; i < Items.size(); i++) {
            if (Items.get(i).getItemId().equals(updatedItem.getItemId())) {
                Items.set(i, updatedItem);
                break;
            }
        }
        saveItems(Items);
    }

    public void deleteItem(String itemId) throws IOException {
        List<Item> Items = getAllItems();
        Items.removeIf(i -> i.getItemId().equals(itemId));
        saveItems(Items);
    }

    public Item getItemById(String itemId) throws IOException {
        List<Item> Items = getAllItems();
        for (Item i : Items) {
            if (i.getItemId().equals(itemId)) {
                return i;
            }
        }
        return null;
    }

    private void saveItems(List<Item> Items) throws IOException {
        try (FileWriter fw = new FileWriter(filePath)) {
            for (Item i : Items) {
                fw.write(i.getItemId() + "," + i.getName() + "," + i.getPrice() + "\n");
            }
        }
    }



}
