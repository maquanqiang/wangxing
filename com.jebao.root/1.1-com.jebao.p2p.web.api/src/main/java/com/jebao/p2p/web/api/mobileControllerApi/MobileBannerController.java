package com.jebao.p2p.web.api.mobileControllerApi;

import com.jebao.p2p.web.api.responseModel.base.JsonResult;
import com.jebao.p2p.web.api.responseModel.base.JsonResultList;
import com.jebao.p2p.web.api.responseModel.mobilebanner.MobileBannerVm;
import org.springframework.web.bind.annotation.RequestMapping;
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
        List<MobileBannerVm> bannerList = new ArrayList<>();
        bannerList.add(new MobileBannerVm(1, "html/phone/images/banner01.png", "html/phone/security.html"));
        bannerList.add(new MobileBannerVm(2, "html/phone/images/banner02.png", "html/phone/rose.html"));
        bannerList.add(new MobileBannerVm(3, "html/phone/images/banner03.png", "html/phone/NewWebsite.html"));

        return new JsonResultList<>(bannerList);
    }
}
