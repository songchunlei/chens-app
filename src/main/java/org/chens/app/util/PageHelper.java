package org.chens.app.util;

import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import org.chens.core.vo.PageResult;
import org.chens.framework.util.BeanUtil;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 分页工具
 *
 * @author songchunlei
 * @since 2018/10/5
 */
public class PageHelper {

    /**
     * 转换Mybatis-plus分页至传统页面分页(PageResult)
     * @param source 原Mybatis-plus分页
     * @param targetClass 目标类型
     * @param <S> 原类型
     * @param <T> 目标类型
     * @return
     */
    public static <S, T> PageResult<T> do2bo4Page(Page<S> source, Class<T> targetClass) {
        if(source == null){
            return null;
        }
        //列表转换
        List<T> result = BeanUtil.do2bo4List(source.getRecords(),targetClass);
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setCurrent(source.getCurrent());
        pageResult.setPages(source.getPages());
        pageResult.setRecords(result);
        pageResult.setSize(source.getSize());
        pageResult.setTotal(source.getTotal());
        return pageResult;
    }
}
