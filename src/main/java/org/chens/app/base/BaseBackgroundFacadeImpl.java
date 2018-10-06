package org.chens.app.base;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import org.chens.app.util.PageHelper;
import org.chens.app.vo.BaseEntity;
import org.chens.core.base.BaseBackgroundFacade;
import org.chens.core.exception.BaseExceptionEnum;
import org.chens.core.vo.PageResult;
import org.chens.core.vo.QueryPageEntity;
import org.chens.core.vo.Result;
import org.chens.framework.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

/**
 * 基础增删改查服务实现
 *
 * @author songchunlei
 * @since 2018/10/6
 */
public class BaseBackgroundFacadeImpl<T, S extends IService<E>, E extends BaseEntity> implements BaseBackgroundFacade<T> {

    @Autowired
    private S service;

    @Override
    public Result<Boolean> insert(T t) {
        if(t==null){
            return Result.getError(BaseExceptionEnum.REQUEST_NULL);
        }
        E entity = BeanUtil.do2bo(t, getClassE());
        return Result.getSuccess(service.insert(entity));
    }

    @Override
    public Result<Boolean> deleteById(Serializable serializable) {
        if(serializable==null){
            return Result.getError(BaseExceptionEnum.REQUEST_NULL);
        }
        return Result.getSuccess(service.deleteById(serializable));
    }

    @Override
    public Result<Boolean> updateById(T t) {
        if(t==null){
            return Result.getError(BaseExceptionEnum.REQUEST_NULL);
        }
        E entity = BeanUtil.do2bo(t, getClassE());
        return Result.getSuccess(service.updateById(entity));
    }

    @Override
    public Result<Boolean> insertOrUpdate(T t) {
        if(t==null){
            return Result.getError(BaseExceptionEnum.REQUEST_NULL);
        }
        E entity = BeanUtil.do2bo(t, getClassE());
        return Result.getSuccess(service.insertOrUpdate(entity));
    }

    @Override
    public Result<T> selectById(Serializable serializable) {
        if(serializable==null){
            return Result.getError(BaseExceptionEnum.REQUEST_NULL);
        }
        E e = service.selectById(serializable);
        return Result.getSuccess(BeanUtil.do2bo(e,getClassT()));
    }

    @Override
    public Result<Integer> selectCount(T t) {
        if(t==null){
            return Result.getError(BaseExceptionEnum.REQUEST_NULL);
        }
        E entity = BeanUtil.do2bo(t, getClassE());
        EntityWrapper<E> wrapper = new EntityWrapper<E>(entity);
        return Result.getSuccess(service.selectCount(wrapper));
    }

    @Override
    public Result<List<T>> selectList(T t) {
        if(t==null){
            return Result.getError(BaseExceptionEnum.REQUEST_NULL);
        }
        E entity = BeanUtil.do2bo(t, getClassE());
        EntityWrapper<E> wrapper = new EntityWrapper<E>(entity);
        List<E> eList = service.selectList(wrapper);
        return Result.getSuccess(BeanUtil.do2bo4List(eList,getClassT()));
    }

    @Override
    public Result<PageResult<T>> selectPage(QueryPageEntity<T> queryPageEntity) {
        if(queryPageEntity==null){
            return Result.getError(BaseExceptionEnum.REQUEST_NULL);
        }
        return Result.getSuccess(PageHelper.autoQueryPage(service,queryPageEntity,getClassT(),getClassE()));
    }

    @Override
    public Result<Boolean> deleteBatchIds(Collection<? extends Serializable> collection) {
        if(CollectionUtils.isEmpty(collection)){
            return Result.getError(BaseExceptionEnum.REQUEST_NULL);
        }
        return Result.getSuccess(service.deleteBatchIds(collection));
    }

    /**
     * 获取Mybatis-plus操作数据类型
     * 关键代码：((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()方法，
     * 获得的是当前类所有的泛型类型，而且是有顺序的，顺序为定义类时泛型的位置：
     *
     * @return
     */
    private Class<E> getClassE() {
        Class<E> dc = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[2];
        return dc;
    }

    /**
     * 获取源类型
     * 关键代码：((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()方法，
     * 获得的是当前类所有的泛型类型，而且是有顺序的，顺序为定义类时泛型的位置：
     *
     * @return
     */
    private Class<T> getClassT() {
        Class<T> dc = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return dc;
    }
}
