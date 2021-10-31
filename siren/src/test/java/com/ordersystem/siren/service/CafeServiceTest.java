package com.ordersystem.siren.service;

import com.ordersystem.siren.domain.Address;
import com.ordersystem.siren.domain.Branch;
import com.ordersystem.siren.domain.Cafe;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CafeServiceTest {
    @Autowired private CafeService cafeService;

    @Test
    public void addCafe() throws Exception {
        //given
        List<Branch> branchList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Branch branch = Branch.createBranch(Long.toString(i), new Address("cist", "state", "street", "zip"));
            branchList.add(branch);
        }
        Cafe cafe = Cafe.createCafe("Starbucks", branchList);
        //when
        //then

    }

}