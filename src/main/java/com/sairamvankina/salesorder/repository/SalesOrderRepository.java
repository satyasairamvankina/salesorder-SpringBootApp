package com.sairamvankina.salesorder.repository;

import com.sairamvankina.salesorder.entity.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder,Integer> {
}
