//package org.example.billservice.tccconfig;
//
//
//import io.seata.rm.tcc.api.BusinessActionContext;
//import io.seata.rm.tcc.api.BusinessActionContextParameter;
//import io.seata.rm.tcc.api.LocalTCC;
//import io.seata.rm.tcc.api.TwoPhaseBusinessAction;
//
//
//public interface BillTccConfig {
//    @TwoPhaseBusinessAction(name = "DubboTccActionOne", commitMethod = "commit", rollbackMethod = "rollback",useTCCFence = true)
//    public boolean prepare(BusinessActionContext actionContext, @BusinessActionContextParameter(paramName = "userId") String userId, @BusinessActionContextParameter(paramName = "amount") int amount);
//    public boolean commit(BusinessActionContext actionContext);
//    public boolean rollback(BusinessActionContext actionContext);
//}
