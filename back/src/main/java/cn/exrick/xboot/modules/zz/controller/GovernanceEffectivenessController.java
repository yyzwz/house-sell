package cn.exrick.xboot.modules.zz.controller;

import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.zz.entity.GovernanceEffectiveness;
import cn.exrick.xboot.modules.zz.service.IGovernanceEffectivenessService;
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
@Api(tags = "治理成效管理接口")
@RequestMapping("/xboot/governanceEffectiveness")
@Transactional
public class GovernanceEffectivenessController {

    @Autowired
    private IGovernanceEffectivenessService iGovernanceEffectivenessService;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "通过id获取")
    public Result<GovernanceEffectiveness> get(@PathVariable String id) {

        GovernanceEffectiveness governanceEffectiveness = iGovernanceEffectivenessService.getById(id);
        return new ResultUtil<GovernanceEffectiveness>().setData(governanceEffectiveness);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "获取全部数据")
    public Result<List<GovernanceEffectiveness>> getAll() {

        List<GovernanceEffectiveness> list = iGovernanceEffectivenessService.list();
        return new ResultUtil<List<GovernanceEffectiveness>>().setData(list);
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "分页获取")
    public Result<IPage<GovernanceEffectiveness>> getByPage(PageVo page) {

        IPage<GovernanceEffectiveness> data = iGovernanceEffectivenessService.page(PageUtil.initMpPage(page));
        return new ResultUtil<IPage<GovernanceEffectiveness>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "编辑或更新数据")
    public Result<GovernanceEffectiveness> saveOrUpdate(GovernanceEffectiveness governanceEffectiveness) {

        if (iGovernanceEffectivenessService.saveOrUpdate(governanceEffectiveness)) {
            return new ResultUtil<GovernanceEffectiveness>().setData(governanceEffectiveness);
        }
        return new ResultUtil<GovernanceEffectiveness>().setErrorMsg("操作失败");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "批量通过id删除")
    public Result<Object> delAllByIds(@RequestParam String[] ids) {

        for (String id : ids) {
            iGovernanceEffectivenessService.removeById(id);
        }
        return ResultUtil.success("批量通过id删除数据成功");
    }
}
