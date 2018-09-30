package org.chens.app.validator;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import org.chens.core.constants.CommonConstants;
import org.chens.app.util.EntityWrapperHelper;
import org.chens.app.vo.BaseEntity;
import org.chens.core.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 通用校验框架
 *
 * @author songchunlei@qq.com
 * @since 2018/3/29
 */
public class BaseValidator<S extends IService<T>, T extends BaseEntity<T>> {

    @Autowired
    protected S service;

    /**
     * 判断是否存在，传入查询条件
     *
     * @param t
     * @return
     */
    public boolean checkIsNotExist(T t) throws Exception {

        String id = t.getId();
        //先置空
        t.setId(null);
        //根据特定字段查询是否存在
        PageVo pageVo = new PageVo();
        pageVo.setLike(false);
        pageVo.setAnd(true);
        EntityWrapper<T> wrapper = EntityWrapperHelper.getQueryEntityWrapperByEntity(t, pageVo);
        if (StringUtils.isNotEmpty(id)) {
            wrapper.ne(CommonConstants.BASE_COLUMN_ID, id);
        }
        int count = service.selectCount(wrapper);
        if (count > 0) {
            return false;
        }
        //设置回去
        t.setId(id);
        return true;
    }

}
