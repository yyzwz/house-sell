package cn.exrick.xboot.modules.base.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Exrickx
 */
@Data
public class AutoChatSetting implements Serializable {

    @ApiModelProperty(value = "对话框标题")
    private String title;

    @ApiModelProperty(value = "机器人头像")
    private String avatar;

    @ApiModelProperty(value = "初始化系统消息")
    private String sysMessage;

    @ApiModelProperty(value = "初始化消息")
    private String welcomeMessage;

    @ApiModelProperty(value = "初始化富文本消息")
    private String initMessage;

    @ApiModelProperty(value = "快捷短语")
    private String quickReplies;

    @ApiModelProperty(value = "输入框占位符")
    private String placeholder;

    @ApiModelProperty(value = "右侧公告类型")
    private String noticeType;

    @ApiModelProperty(value = "右侧公告标题")
    private String noticeTitle;

    @ApiModelProperty(value = "右侧公告内容")
    private String noticeContent;

    @ApiModelProperty(value = "点赞后显示的文本")
    private String textOfGood;

    @ApiModelProperty(value = "点踩后显示的文本")
    private String textOfBad;

    @ApiModelProperty(value = "未找到匹配回答答复")
    private String noDataReply;
}
