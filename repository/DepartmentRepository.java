package com.mirosimo.car_showroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mirosimo.car_showroom.model.Department;

public interface DepartmentRepository  extends JpaRepository<Department, Long>{

}
