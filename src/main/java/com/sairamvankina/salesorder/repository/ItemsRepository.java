package com.sairamvankina.salesorder.repository;

import com.sairamvankina.salesorder.entity.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemsRepository extends CrudRepository<Item,Integer> {
}
