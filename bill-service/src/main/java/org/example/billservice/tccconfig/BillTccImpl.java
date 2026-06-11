package org.example.billservice.tccconfig;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.LocalTCC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 防悬挂、防空回滚、幂等性
 */
@LocalTCC
@Service
public class BillTccImpl implements BillTccConfig{
    Logger log = LoggerFactory.getLogger(BillTccImpl.class);
//    @Autowired
//    private TccFenceMapper fenceMapper;         // 事务控制表操作
    @Override
    public boolean prepare(BusinessActionContext actionContext, String userId,int amount) {
        String xid = actionContext.getXid();
        long branchId = actionContext.getBranchId();
        log.info("Try阶段: xid={}, branchId={}, userId={}, amount={}", xid, branchId, userId, amount);
        return false;
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        return false;
    }
}
