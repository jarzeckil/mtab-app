package com.mtab.mtabapi;

import com.mtab.mtabapi.entity.Customer;
import com.mtab.mtabapi.entity.Item;
import com.mtab.mtabapi.entity.ItemCategory;
import com.mtab.mtabapi.repository.CustomerRepository;
import com.mtab.mtabapi.repository.ItemCategoryRepository;
import com.mtab.mtabapi.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final ItemRepository itemRepository;
    private final ItemCategoryRepository itemCategoryRepository;
    private final CustomerRepository customerRepository;

    @Override
    @Transactional // Dobra praktyka przy operacjach na bazie
    public void run(String... args) throws Exception {

        // Zabezpieczenie: Sprawdź, czy baza jest pusta, żeby nie dublować danych przy restarcie
        if (itemRepository.count() > 0) {
            System.out.println("Database not empty! Aborting seeding.");
            return;
        }

        System.out.println("🌱 SEEDING DATABASE...");

        ItemCategory supplements = new ItemCategory("Supplements");
        ItemCategory clothes = new ItemCategory("Clothes");
        ItemCategory accessories = new ItemCategory("Accessories");

        supplements = itemCategoryRepository.save(supplements);
        clothes = itemCategoryRepository.save(clothes);
        accessories = itemCategoryRepository.save(accessories);


        itemRepository.save(new Item("Creatine", 15.99, supplements));
        itemRepository.save(new Item("Whey Protein", 10.99, supplements));
        itemRepository.save(new Item("Lifting Straps", 8.49, accessories));
        itemRepository.save(new Item("Compression Shirt", 21.19, clothes));

        customerRepository.save(new Customer("Adam", "Mickiewicz", "adammickiewicz@example.com"));
        customerRepository.save(new Customer("Juliusz", "Słowacki", "juliuszslowacki@example.com"));

        System.out.println("DATABASE SEEDED!");
    }
}
