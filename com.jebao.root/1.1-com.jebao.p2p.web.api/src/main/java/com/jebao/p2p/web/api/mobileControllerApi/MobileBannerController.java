package com.jebao.p2p.web.api.mobileControllerApi;

import com.jebao.p2p.web.api.responseModel.base.JsonResult;
import com.jebao.p2p.web.api.responseModel.base.JsonResultList;
import com.jebao.p2p.web.api.responseModel.mobileBanner.MobileBannerVM;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2017/2/17.
 * 移动端 banner 列表
 */
@RestController
@RequestMapping("mobileApi/banner")
public class MobileBannerController {

    @RequestMapping("bannerList")
    public JsonResult bannerList(){
        List<MobileBannerVM> bannerList = new ArrayList<>();
        bannerList.add(new MobileBannerVM(1, "html/phone/images/mobile-banner1.jpg", "html/phone/security.html"));
        bannerList.add(new MobileBannerVM(2, "html/phone/images/mobile-banner2.jpg", "html/phone/rose.html"));
        bannerList.add(new MobileBannerVM(3, "html/phone/images/mobile-banner3.jpg", "html/phone/NewWebsite.html"));

        return new JsonResultList<>(bannerList);
    }
}
