package cn.exrick.xboot.modules.dj.controller;

import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.dj.entity.OnlineExamination;
import cn.exrick.xboot.modules.dj.service.IOnlineExaminationService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 郑为中
 */
@Slf4j
@RestController
@Api(tags = "在线考试管理接口")
@RequestMapping("/xboot/onlineExamination")
@Transactional
public class OnlineExaminationController {

    @Autowired
    private IOnlineExaminationService iOnlineExaminationService;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "通过id获取")
    public Result<OnlineExamination> get(@PathVariable String id) {

        OnlineExamination onlineExamination = iOnlineExaminationService.getById(id);
        return new ResultUtil<OnlineExamination>().setData(onlineExamination);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "获取全部数据")
    public Result<List<OnlineExamination>> getAll() {

        List<OnlineExamination> list = iOnlineExaminationService.list();
        return new ResultUtil<List<OnlineExamination>>().setData(list);
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "分页获取")
    public Result<IPage<OnlineExamination>> getByPage(PageVo page) {

        IPage<OnlineExamination> data = iOnlineExaminationService.page(PageUtil.initMpPage(page));
        return new ResultUtil<IPage<OnlineExamination>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "编辑或更新数据")
    public Result<OnlineExamination> saveOrUpdate(OnlineExamination onlineExamination) {

        if (iOnlineExaminationService.saveOrUpdate(onlineExamination)) {
            return new ResultUtil<OnlineExamination>().setData(onlineExamination);
        }
        return new ResultUtil<OnlineExamination>().setErrorMsg("操作失败");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "批量通过id删除")
    public Result<Object> delAllByIds(@RequestParam String[] ids) {

        for (String id : ids) {
            iOnlineExaminationService.removeById(id);
        }
        return ResultUtil.success("批量通过id删除数据成功");
    }
}
