package com.mtab.mtabapi;

import com.mtab.mtabapi.entity.Customer;
import com.mtab.mtabapi.entity.Item;
import com.mtab.mtabapi.entity.ItemCategory;
import com.mtab.mtabapi.repository.CustomerRepository;
import com.mtab.mtabapi.repository.ItemCategoryRepository;
import com.mtab.mtabapi.repository.ItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MtabAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MtabAppApplication.class, args);
    }

}
