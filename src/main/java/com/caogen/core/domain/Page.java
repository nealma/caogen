package com.caogen.core.domain;

import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.caogen.core.util.JSONFilter;
import com.caogen.core.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 与具体ORM实现无关的分页查询结果封装.
 */
@JSONType(ignores = {"resultMap", "pageNo", "pageSize", "countTotal",
        "orderBy", "orderDir", "sSearch", "start", "sEcho", "prefix",
        "totalItems", "prePage", "nextPage", "offset", "totalPages",
        "firstPage", "lastPage", "orderBySetted", "result"})
public class Page<T> extends PageRequest implements Iterable<T>, Serializable {
    private static final long serialVersionUID = 1L;
    protected List<T> result = null;
    protected long totalItems = -1;
    /**
     * 数据集合result外的其他数据
     */
    protected Map<String, Object> resultMap = null;

    public Page() {
    }

    public Page(PageRequest request) {
        this.pageNo = request.pageNo;
        this.pageSize = request.pageSize;
        this.countTotal = request.countTotal;
        this.orderBy = request.orderBy;
        this.orderDir = request.orderDir;
        this.sSearch = request.sSearch;
        this.start = request.start;
        this.sEcho = request.sEcho;
        this.prefix = request.prefix;
    }

    /**
     * 获得JSON扩展属性
     *
     * @return
     */
    public Map<String, Object> getResultMap() {
        return resultMap;
    }

    /**
     * 设置JSON中根节点属性值，作为JSON属性的扩展
     *
     * @param resultMap
     * @see #toJsonString(Object)
     */
    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    /**
     * 获得页内的记录列表.
     *
     * @return 记录集
     */
    public List<T> getResult() {
        return result;
    }

    /**
     * 设置页内的记录列表.
     *
     * @param result
     */
    public void setResult(final List<T> result) {
        this.result = result;
    }

    /**
     * 获得总记录数, 默认值为-1.
     *
     * @return
     */
    public long getTotalItems() {
        return totalItems;
    }

    /**
     * 设置总记录数.
     *
     * @param totalItems
     */
    public void setTotalItems(final long totalItems) {
        this.totalItems = totalItems;
    }

    /**
     * 实现Iterable接口, 可以for(Object item : page)遍历使用
     */
    public Iterator<T> iterator() {
        return result.iterator();
    }

    /**
     * 根据pageSize与totalItems计算总页数.
     */
    public int getTotalPages() {
        int totalPages = (int) Math.ceil((double) totalItems / (double) getPageSize());
        if (totalPages == 0)
            totalPages = 1;
        return totalPages;

    }

    /**
     * 是否还有下一页.
     *
     * @see #getNextPage()
     * @see #getTotalPages()
     */
    public boolean hasNextPage() {
        return (getPageNo() + 1 <= getTotalPages());
    }

    /**
     * 是否最后一页.
     *
     * @see #hasNextPage()
     */
    public boolean isLastPage() {
        return !hasNextPage();
    }

    /**
     * 取得下页的页号, 序号从1开始. 当前页为尾页时仍返回尾页序号.
     *
     * @see #hasNextPage()
     * @see #getPageNo()
     */
    public int getNextPage() {
        if (hasNextPage()) {
            return getPageNo() + 1;
        } else {
            return getPageNo();
        }
    }

    /**
     * 是否还有上一页.
     *
     * @see #getPageNo()
     */
    public boolean hasPrePage() {
        return (getPageNo() > 1);
    }

    /**
     * 是否第一页.
     *
     * @see #hasPrePage()
     */
    public boolean isFirstPage() {
        return !hasPrePage();
    }

    /**
     * 取得上页的页号, 序号从1开始. 当前页为首页时返回首页序号.
     *
     * @return
     * @see #hasPrePage()
     * @see #getPageNo()
     */
    public int getPrePage() {
        if (hasPrePage()) {
            return getPageNo() - 1;
        } else {
            return getPageNo();
        }
    }

    /**
     * 计算以当前页为中心的页面列表,如"首页,23,24,25,26,27,末页"
     *
     * @param count 需要计算的列表大小
     * @return pageNo列表
     * @see #getTotalPages()
     */
    public List<Integer> getSlider(int count) {
        int halfSize = count / 2;
        int totalPage = getTotalPages();

        int startPageNo = Math.max(getPageNo() - halfSize, 1);
        int endPageNo = Math.min(startPageNo + count - 1, totalPage);

        if (endPageNo - startPageNo < count) {
            startPageNo = Math.max(endPageNo - count, 1);
        }

        List<Integer> result = new ArrayList<Integer>();
        for (int i = startPageNo; i <= endPageNo; i++) {
            result.add(i);
        }
        return result;
    }


    /**
     * 完成JSONFilter处理过程，并根据值（动态参逻辑校数）完成验证
     */
    protected SerializeFilter propertyFilter(Object clazz) {
        SerializeFilter filter = null;
        if (clazz == null) {
            return null;
        }
        try {
            JSONFilter jSONGis = (JSONFilter) clazz.getClass().getAnnotation(JSONFilter.class);
            if (jSONGis != null && jSONGis.ignores().length > 0) {
                final String[] property = includeProperty(jSONGis, clazz,
                        jSONGis.ignores());
                filter = new PropertyFilter() {
                    @Override
                    public boolean apply(Object source, String name,
                                         Object value) {
                        for (String per : property) {
                            if (per.equals(name)) {
                                return false;
                            }
                        }
                        return true;
                    }
                };
            } else if (jSONGis != null
                    && jSONGis.includeSimpleProperty().length > 0) {
                String strs[] = includeProperty(jSONGis, clazz,
                        jSONGis.includeSimpleProperty());
                if (strs != null) {
                    filter = new SimplePropertyPreFilter(clazz.getClass(), strs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return filter;
    }

    /**
     * 从类对象中解析数组，支持动态参数${}，其中动态参数必须包含get方法。（如果是${}类型则进行反射）
     *
     * @param jSONGis
     * @param clazz    类对象
     * @param property 拦截属性
     */
    protected String[] includeProperty(JSONFilter jSONGis, Object clazz,
                                       String[] property) {
        String[] strs = null;
        String[] pro = property;
        List<String> list = new ArrayList<String>();
        for (String include : pro) {
            if (include.indexOf("$") == 0) {
                String[] temp = null;
                try {
                    temp = (String[]) clazz
                            .getClass()
                            .getMethod(
                                    "get"
                                            + StringUtils
                                            .removeUnderscores(include
                                                    .replace("${", "")
                                                    .replace("}", "")))
                            .invoke(clazz);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (temp != null) {
                    for (String in : temp) {
                        list.add(in);
                    }
                }
            } else {
                list.add(include);
            }
        }
        if (list.size() != 0) {
            strs = list.toArray(new String[list.size()]);
        }
        return strs;
    }
}
