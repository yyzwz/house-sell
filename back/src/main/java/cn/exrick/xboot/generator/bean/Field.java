package cn.exrick.xboot.generator.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author nikou
 */
@Data
@Accessors(chain = true)
public class Field {

    private String field;

    private String name;

    private String dictType;

    private String customUrl;

    private String searchDictType;

    private String searchCustomUrl;

    private String level;

    private Boolean tableShow;

    private BigDecimal sortOrder;

    private Boolean searchable;

    private Boolean editable = false;

    private String type;

    private Boolean validate = false;

    private String searchType;

    private String searchLevel;

    private Boolean sortable;

    private Boolean defaultSort;

    private String defaultSortType;
}
