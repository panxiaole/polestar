package com.haier.polestar.starter.datasource.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.haier.polestar.common.lock.DistributedLock;

/**
 * service接口父类
 *
 * @author panxiaole
 * @date 2019-04-20
 */
public interface IBaseService<T> extends IService<T> {
    /**
     * 幂等性新增记录
     *
     * @param entity       实体对象
     * @param lock         锁实例
     * @param lockKey      锁的key
     * @param countWrapper 判断是否存在的条件
     * @param msg          对象已存在提示信息
     * @return
     */
    boolean saveIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper, String msg);

    /**
     * 幂等性新增记录
     *
     * @param entity       实体对象
     * @param lock         锁实例
     * @param lockKey      锁的key
     * @param countWrapper 判断是否存在的条件
     * @return
     */
    boolean saveIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper);

    /**
     * 幂等性新增或更新记录
     *
     * @param entity       实体对象
     * @param lock         锁实例
     * @param lockKey      锁的key
     * @param countWrapper 判断是否存在的条件
     * @param msg          对象已存在提示信息
     * @return
     */
    boolean saveOrUpdateIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper, String msg);

    /**
     * 幂等性新增或更新记录
     *
     * @param entity       实体对象
     * @param lock         锁实例
     * @param lockKey      锁的key
     * @param countWrapper 判断是否存在的条件
     * @return
     */
    boolean saveOrUpdateIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper);
}