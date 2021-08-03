package cn.exrick.xboot.generator.vue;

import cn.exrick.xboot.common.exception.LimitException;
import cn.exrick.xboot.common.exception.XbootException;
import cn.exrick.xboot.common.limit.RedisRaterLimiter;
import cn.exrick.xboot.common.utils.IpInfoUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.generator.XbootGenerator;
import cn.exrick.xboot.generator.bean.Field;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author nikou
 */
@Slf4j
@RestController
@Api(tags = "Vue代码生成")
@RequestMapping(value = "/xboot/generate")
public class XbootVueGenerator {

    @Autowired
    private RedisRaterLimiter redisRaterLimiter;

    @Autowired
    private IpInfoUtil ipInfoUtil;

    @RequestMapping(value = "/table/{vueName}/{rowNum}", method = RequestMethod.POST)
    @ApiOperation(value = "增删改表格生成")
    public Result<Object> generateTable(@PathVariable String vueName,
                                        @PathVariable Integer rowNum,
                                        @RequestBody List<Field> fields,
                                        HttpServletRequest request) throws IOException {

        String drawer = generate("tableDrawerIndex.btl", false, vueName, rowNum, fields);
        String drawerApi = generate("tableDrawerIndex.btl", true, vueName, rowNum, fields);
        String addEdit = generate("addEdit.btl", false, vueName, rowNum, fields);
        String addEditApi = generate("addEdit.btl", true, vueName, rowNum, fields);
        String component = generate("tableIndex.btl", false, vueName, rowNum, fields);
        String componentApi = generate("tableIndex.btl", true, vueName, rowNum, fields);
        String add = generate("add.btl", false, vueName, rowNum, fields);
        String addApi = generate("add.btl", true, vueName, rowNum, fields);
        String edit = generate("edit.btl", false, vueName, rowNum, fields);
        String editApi = generate("edit.btl", true, vueName, rowNum, fields);
        String single = generate("table.btl", false, vueName, rowNum, fields);
        String singleApi = generate("table.btl", true, vueName, rowNum, fields);
        String api = generate("api.btl", true, vueName, rowNum, fields);
        Map<String, String> map = new HashMap<>();
        map.put("drawer", drawer);
        map.put("drawerApi", drawerApi);
        map.put("addEdit", addEdit);
        map.put("addEditApi", addEditApi);
        map.put("component", component);
        map.put("componentApi", componentApi);
        map.put("add", add);
        map.put("addApi", addApi);
        map.put("edit", edit);
        map.put("editApi", editApi);
        map.put("single", single);
        map.put("singleApi", singleApi);
        map.put("api", api);
        return ResultUtil.data(map);
    }

    @RequestMapping(value = "/tree/{vueName}/{rowNum}/{enableTable}", method = RequestMethod.POST)
    @ApiOperation(value = "树形结构生成")
    public Result<Object> generateTree(@PathVariable String vueName,
                                       @PathVariable Integer rowNum,
                                       @PathVariable Boolean enableTable,
                                       @RequestBody List<Field> fields,
                                       HttpServletRequest request) throws IOException {

        // IP限流 在线Demo所需
        Boolean token = redisRaterLimiter.acquireByRedis("generate:" + ipInfoUtil.getIpAddr(request), 1L, 86400000L);
        if (!token) {
            throw new LimitException("您今日的测试生成次数已达上限");
        }

        String drawer = generate("tree.btl", false, true, enableTable, vueName, rowNum, fields);
        String drawerApi = generate("tree.btl", true, true, enableTable, vueName, rowNum, fields);
        String result = generate("tree.btl", false, false, enableTable, vueName, rowNum, fields);
        String resultApi = generate("tree.btl", true, false, enableTable, vueName, rowNum, fields);
        String api = generate("treeApi.btl", true, vueName, rowNum, fields);
        Map<String, String> map = new HashMap<>();
        map.put("drawer", drawer);
        map.put("drawerApi", drawerApi);
        map.put("result", result);
        map.put("resultApi", resultApi);
        map.put("api", api);
        return ResultUtil.data(map);
    }

    @RequestMapping(value = "/getEntityData", method = RequestMethod.GET)
    @ApiOperation(value = "通过实体类生成Vue代码Json数据")
    public Result<Object> getEntityData(@RequestParam String path) {

        String result = "";
        try {
            result = generateEntityData(path);
        } catch (Exception e) {
            return ResultUtil.error("实体类文件不存在");
        }
        return ResultUtil.data(result);
    }

    /**
     * 表格
     */
    public String generate(String template, boolean api, String vueName, Integer rowNum, List<Field> fields) throws IOException {

        return generate(template, api, false, false, vueName, rowNum, fields);
    }

    /**
     * 树
     */
    public String generate(String template, boolean api, boolean isDrawer, boolean enableTable, String vueName, Integer rowNum, List<Field> fields) throws IOException {

        // 模板路径
        ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader("/btl/vue/");
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);

        Template tableTemplate = gt.getTemplate(template);
        // 排序
        Collections.sort(fields, Comparator.comparing(Field::getSortOrder));
        // 绑定变量
        tableTemplate.binding("api", api);
        tableTemplate.binding("isDrawer", isDrawer);
        tableTemplate.binding("enableTable", enableTable);
        tableTemplate.binding("vueName", XbootGenerator.name(vueName, false));
        tableTemplate.binding("apiName", XbootGenerator.name(vueName, true));

        // 判断有无相关组件和日期范围搜索等组件
        Boolean upload = false, uploadThumb = false, editor = false, password = false, dict = false, customList = false,
                searchDict = false, searchCustomList = false, fileUpload = false;
        for (Field f : fields) {
            if ("upload".equals(f.getType())) {
                upload = true;
            }
            if ("uploadThumb".equals(f.getType())) {
                uploadThumb = true;
            }
            if ("editor".equals(f.getType())) {
                editor = true;
            }
            if ("password".equals(f.getType())) {
                password = true;
            }
            if ("dict".equals(f.getType())) {
                dict = true;
            }
            if ("dict".equals(f.getSearchType())) {
                searchDict = true;
            }
            if ("customList".equals(f.getType())) {
                customList = true;
            }
            if ("customList".equals(f.getSearchType())) {
                searchCustomList = true;
            }
            if ("fileUpload".equals(f.getType())) {
                fileUpload = true;
            }
        }
        tableTemplate.binding("upload", upload);
        tableTemplate.binding("uploadThumb", uploadThumb);
        tableTemplate.binding("editor", editor);
        tableTemplate.binding("password", password);
        tableTemplate.binding("dict", dict);
        tableTemplate.binding("customList", customList);
        tableTemplate.binding("searchDict", searchDict);
        tableTemplate.binding("searchCustomList", searchCustomList);
        tableTemplate.binding("fileUpload", fileUpload);
        if ("table.btl".equals(template) || "tableIndex.btl".equals(template) || "tableDrawerIndex.btl".equals(template)) {
            // 判断有无upload和日期范围搜索
            Boolean daterangeSearch = false;
            for (Field f : fields) {
                if (f.getSearchable() && "daterange".equals(f.getSearchType())) {
                    daterangeSearch = true;
                }
            }
            tableTemplate.binding("daterangeSearch", daterangeSearch);
            // 统计搜索栏个数 判断是否隐藏搜索栏
            Boolean hideSearch = false;
            List<Field> firstTwo = new ArrayList<>();
            List<Field> rest = new ArrayList<>();
            Integer count = 0;
            for (Field f : fields) {
                if (f.getSearchable()) {
                    count++;
                    if (count <= 2) {
                        firstTwo.add(f);
                    } else {
                        rest.add(f);
                    }
                }
            }
            if (count >= 4) {
                hideSearch = true;
                tableTemplate.binding("firstTwo", firstTwo);
                tableTemplate.binding("rest", rest);
            }
            tableTemplate.binding("searchSize", count);
            tableTemplate.binding("hideSearch", hideSearch);
            // 获取默认排序字段
            String defaultSort = "", defaultSortType = "";
            for (Field f : fields) {
                if (f.getDefaultSort()) {
                    defaultSort = f.getField();
                    defaultSortType = f.getDefaultSortType();
                    break;
                }
            }
            tableTemplate.binding("defaultSort", defaultSort);
            tableTemplate.binding("defaultSortType", defaultSortType);
        }
        if ("tree.btl".equals(template)) {
            if (!containsField(fields, "xbootTreeTitle")) {
                fields.add(0, new Field().setType("xbootTreeTitle").setSortOrder(new BigDecimal("-1")).setEditable(true));
            }
            if (!containsField(fields, "xbootTreeChoose")) {
                fields.add(0, new Field().setType("xbootTreeChoose").setSortOrder(new BigDecimal("-2")).setEditable(true));
            }
            if (!containsField(fields, "xbootTreeSortOrder")) {
                fields.add(new Field().setType("xbootTreeSortOrder").setSortOrder(new BigDecimal("999")).setEditable(true));
            }
        }
        tableTemplate.binding("fields", fields);
        // 计算可编辑字段总行数
        int totalField = (int) fields.stream().filter(e -> e.getEditable()).count();
        int totalRow = (int) Math.ceil(totalField * 1.0 / rowNum);
        tableTemplate.binding("totalRow", totalRow);
        // 一行几列
        tableTemplate.binding("rowNum", rowNum);

        if ("tree.btl".equals(template)) {
            totalRow = (int) Math.ceil((fields.size() - 1) * 1.0 / rowNum);
            tableTemplate.binding("totalRowTree", totalRow);
        }

        tableTemplate.binding("labelPosition", "top");

        if (rowNum == 1) {
            tableTemplate.binding("modalWidth", 500);
            tableTemplate.binding("span", "24");
            if (!"addEdit.btl".equals(template)) {
                tableTemplate.binding("labelPosition", "left");
            }
            tableTemplate.binding("treeSpan", ":sm=\"8\" :md=\"8\" :lg=\"8\" :xl=\"6\"");
            tableTemplate.binding("treeEditSpan", ":sm=\"16\" :md=\"16\" :lg=\"16\" :xl=\"9\"");
        } else if (rowNum == 2) {
            tableTemplate.binding("modalWidth", 720);
            tableTemplate.binding("span", "12");
            tableTemplate.binding("treeSpan", ":sm=\"8\" :md=\"8\" :lg=\"8\" :xl=\"6\"");
            tableTemplate.binding("treeEditSpan", ":sm=\"16\" :md=\"16\" :lg=\"16\" :xl=\"12\"");
        } else if (rowNum == 3) {
            tableTemplate.binding("modalWidth", 920);
            if ("add.btl".equals(template) || "edit.btl".equals(template)) {
                tableTemplate.binding("modalWidth", "100%");
            }
            tableTemplate.binding("span", "8");
            tableTemplate.binding("treeSpan", ":span=\"6\"");
            tableTemplate.binding("treeEditSpan", ":span=\"18\"");
        } else if (rowNum == 4) {
            tableTemplate.binding("modalWidth", 1120);
            if ("add.btl".equals(template) || "edit.btl".equals(template)) {
                tableTemplate.binding("modalWidth", "100%");
            }
            tableTemplate.binding("span", "6");
            tableTemplate.binding("treeSpan", ":span=\"5\"");
            tableTemplate.binding("treeEditSpan", ":span=\"19\"");
        } else {
            throw new XbootException("rowNum仅支持数字1-4");
        }
        // 生成代码
        String result = tableTemplate.render();
        return result;
    }

    public Boolean containsField(List<Field> fields, String type) {

        Boolean flag = false;
        for (Field field : fields) {
            if (type.equals(field.getType())) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public String generateEntityData(String path) throws Exception {

        Class c = Class.forName(path);

        Object obj = c.getDeclaredConstructor().newInstance();

        String start = "{\n" +
                "    \"data\": [";
        String end = "\n    ]\n" +
                "}";
        String fieldAll = "";
        java.lang.reflect.Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            java.lang.reflect.Field field = fields[i];
            field.setAccessible(true);
            // 字段名
            String fieldName = field.getName();
            String fieldType = field.getType().getName();
            // 白名单
            if ("serialVersionUID".equals(fieldName) || "actBusinessId".equals(fieldName) || "applyUser".equals(fieldName)
                    || "routeName".equals(fieldName) || "procInstId".equals(fieldName) || "applyTime".equals(fieldName)) {
                continue;
            }

            // 获得字段注解
            ApiModelProperty myFieldAnnotation = field.getAnnotation(ApiModelProperty.class);
            String fieldNameCN = fieldName;
            if (myFieldAnnotation != null) {
                fieldNameCN = myFieldAnnotation.value();
            }
            fieldNameCN = StrUtil.isBlank(fieldNameCN) ? fieldName : fieldNameCN;

            String type = "text";
            String searchType = "text";
            // 日期数字字段特殊处理 其他一律按字符串处理
            if (fieldType.contains("Date")) {
                type = "date";
                searchType = "daterange";
            } else if (fieldType.contains("BigDecimal") || fieldType.contains("Integer") || fieldType.contains("Long")) {
                type = "number";
            }
            String fieldJson = "\n        {\n" +
                    "            \"sortOrder\": " + i + ",\n" +
                    "            \"field\": \"" + fieldName + "\",\n" +
                    "            \"name\": \"" + fieldNameCN + "\",\n" +
                    "            \"level\": \"2\",\n" +
                    "            \"tableShow\": true,\n" +
                    "            \"editable\": true,\n" +
                    "            \"type\": \"" + type + "\",\n" +
                    "            \"searchType\": \"" + searchType + "\",\n" +
                    "            \"searchLevel\": \"2\",\n" +
                    "            \"validate\": false,\n" +
                    "            \"searchable\": false,\n" +
                    "            \"sortable\": false,\n" +
                    "            \"defaultSort\": false,\n" +
                    "            \"defaultSortType\": \"desc\"\n" +
                    "        }";
            String splitChar = StrUtil.isBlank(fieldAll) ? "" : ",";
            fieldAll = fieldAll + splitChar + fieldJson;
        }
        String json = start + fieldAll + end;
        return json;
    }
}
