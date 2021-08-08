package cn.exrick.xboot.modules.zz.controller;

import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.base.utils.DateUtils;
import cn.exrick.xboot.modules.base.utils.NullUtils;
import cn.exrick.xboot.modules.zz.entity.VillageIntroduce;
import cn.exrick.xboot.modules.zz.service.IVillageIntroduceService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author 郑为中
 */
@Slf4j
@RestController
@Api(tags = "村情介绍管理接口")
@RequestMapping("/xboot/villageIntroduce")
@Transactional
public class VillageIntroduceController {

    @Autowired
    private IVillageIntroduceService iVillageIntroduceService;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "通过id获取")
    public Result<VillageIntroduce> get(@PathVariable String id) {

        VillageIntroduce villageIntroduce = iVillageIntroduceService.getById(id);
        return new ResultUtil<VillageIntroduce>().setData(villageIntroduce);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "获取全部数据")
    public Result<List<VillageIntroduce>> getAll() {

        List<VillageIntroduce> list = iVillageIntroduceService.list();
        return new ResultUtil<List<VillageIntroduce>>().setData(list);
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "分页获取")
    public Result<IPage<VillageIntroduce>> getByPage(@ModelAttribute VillageIntroduce introduce,@ModelAttribute PageVo page) {
        QueryWrapper<VillageIntroduce> qw = new QueryWrapper<>();
        if(introduce.getTitle() != null && !NullUtils.isNull(introduce.getTitle())) {
            qw.like("title",introduce.getTitle());
        }
        if(introduce.getAuditStatus() == 666) {
            qw.eq("audit_status",1);
        }
        IPage<VillageIntroduce> data = iVillageIntroduceService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<VillageIntroduce>>().setData(data);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "编辑或更新数据")
    public Result<VillageIntroduce> insert(VillageIntroduce villageIntroduce) {
        villageIntroduce.setAuditStatus(0);
        villageIntroduce.setCreateDate(DateUtils.getZwzNowTime());
        if (iVillageIntroduceService.saveOrUpdate(villageIntroduce)) {
            return new ResultUtil<VillageIntroduce>().setData(villageIntroduce);
        }
        return new ResultUtil<VillageIntroduce>().setErrorMsg("操作失败");
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑或更新数据")
    public Result<VillageIntroduce> update(VillageIntroduce villageIntroduce) {
        if (iVillageIntroduceService.saveOrUpdate(villageIntroduce)) {
            return new ResultUtil<VillageIntroduce>().setData(villageIntroduce);
        }
        return new ResultUtil<VillageIntroduce>().setErrorMsg("操作失败");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "批量通过id删除")
    public Result<Object> delAllByIds(@RequestParam String[] ids) {
        for (String id : ids) {
            iVillageIntroduceService.removeById(id);
        }
        return ResultUtil.success("批量通过id删除数据成功");
    }

    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public Result<Object> audit(@RequestParam String id,@RequestParam int status) {
        VillageIntroduce introduce = iVillageIntroduceService.getById(id);
        if(introduce != null) {
            introduce.setAuditStatus(status);
            iVillageIntroduceService.saveOrUpdate(introduce);
            return ResultUtil.success("OK");
        }
        return ResultUtil.error("NULL");
    }

    @RequestMapping(value = "/getHtmlPage", method = RequestMethod.GET)
    @ApiOperation(value = "获取H5页面")
    public void getHtmlPage(@RequestParam String id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        VillageIntroduce introduce = iVillageIntroduceService.getById(id);
        response.setContentType("text/html;charset=utf-8;pageEncoding=\"utf-8\"");
        PrintWriter out = response.getWriter();
        out.println("<h1 style=\"text-align: center;\">" + introduce.getTitle() + "</h1>");
        out.println("");
        out.println(introduce.getContent());
        out.println("");
        out.println("");
        out.println("");
        out.println("");
    }
}
