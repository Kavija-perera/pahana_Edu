package dao;

import com.pahanaedu.model.item;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class itemDAO {
    private final String filePath;

    public itemDAO(String filePath) {
        this.filePath = filePath;
    }

    public List<item> getAllItems() throws IOException {
        List<item> items = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) return items;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    String itemId = data[0];
                    String name = data[1];
                    double price = Double.parseDouble(data[2]);
                    items.add(new item(itemId, name, price));
                }
            }
        }
        return items;
    }

    public void addItem(item item) throws IOException {
        List<item> items = getAllItems();
        items.add(item);
        saveItems(items);
    }

    public void updateItem(item updatedItem) throws IOException {
        List<item> items = getAllItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getItemId().equals(updatedItem.getItemId())) {
                items.set(i, updatedItem);
                break;
            }
        }
        saveItems(items);
    }

    public void deleteItem(String itemId) throws IOException {
        List<item> items = getAllItems();
        items.removeIf(i -> i.getItemId().equals(itemId));
        saveItems(items);
    }

    public item getItemById(String itemId) throws IOException {
        List<item> items = getAllItems();
        for (item i : items) {
            if (i.getItemId().equals(itemId)) {
                return i;
            }
        }
        return null;
    }

    private void saveItems(List<item> items) throws IOException {
        try (FileWriter fw = new FileWriter(filePath)) {
            for (item i : items) {
                fw.write(i.getItemId() + "," + i.getName() + "," + i.getPrice() + "\n");
            }
        }
    }
}
