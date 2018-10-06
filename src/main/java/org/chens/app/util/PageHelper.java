package org.chens.app.util;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import org.chens.core.vo.PageResult;
import org.chens.core.vo.PageVo;
import org.chens.core.vo.QueryPageEntity;
import org.chens.framework.util.BeanUtil;

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
     *
     * @param source      原Mybatis-plus分页
     * @param targetClass 目标类型
     * @param <S>         原类型
     * @param <T>         目标类型
     * @return
     */
    public static <S, T> PageResult<T> do2bo4Page(Page<S> source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        //列表转换
        List<T> result = BeanUtil.do2bo4List(source.getRecords(), targetClass);
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setCurrent(source.getCurrent());
        pageResult.setPages(source.getPages());
        pageResult.setRecords(result);
        pageResult.setSize(source.getSize());
        pageResult.setTotal(source.getTotal());
        return pageResult;
    }

    /**
     * 查询分页参数转Mybatis-plus分页
     *
     * @param source
     * @param targetClass
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> Page<T> queryPage2Page(QueryPageEntity<S> source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        return new Page<T>(source.getPage().getCurrent(), source.getPage().getSize());
    }

    /**
     * 根据查询分页智能查询分页数据
     *
     * @param source
     * @param targetClass
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> Page<T> autoQueryPage(IService<T> service, QueryPageEntity<S> source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        //获取页面参数
        PageVo pageVo = source.getPage();
        //创建查询条件
        Page<T> page = queryPage2Page(source, targetClass);
        //获取查询实体
        T object = BeanUtil.do2bo(source.getSearch(), targetClass);
        //模糊查询各字段
        EntityWrapper<T> wrapper = EntityWrapperHelper.getQueryEntityWrapperByEntity(object, pageVo);
        //查询
        return service.selectPage(page, wrapper);
    }

    /**
     * 根据查询分页智能查询分页数据
     * @param service
     * @param source
     * @param sourceClass
     * @param targetClass
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> PageResult<S> autoQueryPage(IService<T> service, QueryPageEntity<S> source,Class<S> sourceClass,Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        Page<T> tPage = autoQueryPage(service,source,targetClass);
        return do2bo4Page(tPage,sourceClass);
    }


}
