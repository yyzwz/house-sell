package cn.exrick.xboot.modules.dj.controller;

import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.dj.entity.Meeting;
import cn.exrick.xboot.modules.dj.service.IMeetingService;
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
@Api(tags = "会议管理接口")
@RequestMapping("/xboot/meeting")
@Transactional
public class MeetingController {

    @Autowired
    private IMeetingService iMeetingService;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "通过id获取")
    public Result<Meeting> get(@PathVariable String id) {

        Meeting meeting = iMeetingService.getById(id);
        return new ResultUtil<Meeting>().setData(meeting);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "获取全部数据")
    public Result<List<Meeting>> getAll() {

        List<Meeting> list = iMeetingService.list();
        return new ResultUtil<List<Meeting>>().setData(list);
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "分页获取")
    public Result<IPage<Meeting>> getByPage(PageVo page) {

        IPage<Meeting> data = iMeetingService.page(PageUtil.initMpPage(page));
        return new ResultUtil<IPage<Meeting>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "编辑或更新数据")
    public Result<Meeting> saveOrUpdate(Meeting meeting) {

        if (iMeetingService.saveOrUpdate(meeting)) {
            return new ResultUtil<Meeting>().setData(meeting);
        }
        return new ResultUtil<Meeting>().setErrorMsg("操作失败");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "批量通过id删除")
    public Result<Object> delAllByIds(@RequestParam String[] ids) {

        for (String id : ids) {
            iMeetingService.removeById(id);
        }
        return ResultUtil.success("批量通过id删除数据成功");
    }
}
