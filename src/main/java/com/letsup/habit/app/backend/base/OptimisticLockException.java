package com.letsup.habit.app.backend.base;

import com.letsup.habit.app.backend.constants.ApiResultConstants;

/**
 * 乐观锁异常
 */
public class OptimisticLockException extends GlobalException {
    public OptimisticLockException(ApiResultConstants.ExceptionMessage em) {
        super(em);
    }
}
