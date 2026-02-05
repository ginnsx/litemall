package org.linlinjava.litemall.admin;

import org.junit.jupiter.api.Test;

import org.linlinjava.litemall.core.qcode.QCodeService;
import org.linlinjava.litemall.db.domain.LitemallGoods;
import org.linlinjava.litemall.db.service.LitemallGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.web.WebAppConfiguration;


@SpringBootTest
@org.junit.jupiter.api.Disabled("Date dependency")
public class CreateShareImageTest {
    @Autowired
    QCodeService qCodeService;
    @Autowired
    LitemallGoodsService litemallGoodsService;

    @Test
    public void test() {
        LitemallGoods good = litemallGoodsService.findById(1181010);
        qCodeService.createGoodShareImage(good.getId().toString(), good.getPicUrl(), good.getName());
    }
}
