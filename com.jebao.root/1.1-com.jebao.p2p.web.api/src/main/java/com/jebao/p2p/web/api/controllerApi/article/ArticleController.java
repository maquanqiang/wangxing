package com.jebao.p2p.web.api.controllerApi.article;

import com.jebao.jebaodb.entity.article.ArticleInfo;
import com.jebao.jebaodb.entity.article.TbArticle;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.p2p.service.inf.article.IArticleServiceInf;
import com.jebao.p2p.web.api.controllerApi._BaseController;
import com.jebao.p2p.web.api.requestModel.article.ArticleSM;
import com.jebao.p2p.web.api.responseModel.article.ArticleListVM;
import com.jebao.p2p.web.api.responseModel.article.ArticleVM;
import com.jebao.p2p.web.api.responseModel.base.JsonResult;
import com.jebao.p2p.web.api.responseModel.base.JsonResultData;
import com.jebao.p2p.web.api.responseModel.base.JsonResultList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/27.
 */
@RestController
@RequestMapping("/api/article/")
public class ArticleController extends _BaseController {
    @Autowired
    private IArticleServiceInf articleService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult index(ArticleSM model){
        if (model == null) {
            return new JsonResultList<>(null);
        }

        PageWhere page = new PageWhere(model.getPageIndex(), model.getPageSize());
        List<TbArticle> articleList = articleService.selectArticleByTypeIdForIndex(model.getTypeId(), page);
        if (articleList == null || articleList.size() == 0) {
            return new JsonResultList<>(null);
        }
        List<ArticleVM> viewModelList = new ArrayList<>();
        articleList.forEach(o -> viewModelList.add(new ArticleVM(o)));
        return new JsonResultList<>(viewModelList);
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult list(ArticleSM model) {
        if (model == null) {
            return new JsonResultList<>(null);
        }

        PageWhere page = new PageWhere(model.getPageIndex(), model.getPageSize());

        List<ArticleInfo> articleList = articleService.selectArticleByTypeIdForPage(model.getTypeId(), page);
        if (articleList == null || articleList.size() == 0) {
            return new JsonResultList<>(null);
        }
        List<ArticleListVM> viewModelList = new ArrayList<>();
        articleList.forEach(o -> viewModelList.add(new ArticleListVM(o)));

        int count = 0;
        if (model.getPageIndex() == 0) {
            count = articleService.selectArticleByTypeIdForPageCount(model.getTypeId());
        }
        return new JsonResultList<>(viewModelList, count);
    }

    @RequestMapping(value = "details", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult details(Long aId) {
        if (aId == null || aId == 0) {
            return new JsonResultData<>(null);
        }
        TbArticle article = articleService.findArticleById(aId);
        if (article == null) {
            return new JsonResultData<>(null);
        }
        ArticleVM viewModel = new ArticleVM(article);
        return new JsonResultData<>(viewModel);
    }
}
