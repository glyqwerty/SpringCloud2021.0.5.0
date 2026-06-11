//package org.example.warehouseservice.tccConfig;
//
//
//public interface WarehouseTccConfig {
//
//    @TwoPhaseBusinessAction(name = "DubboTccActionOne", commitMethod = "commit", rollbackMethod = "rollback")
//    public boolean prepare(BusinessActionContext actionContext, @BusinessActionContextParameter(paramName = "a") String a);
//    public boolean commit(BusinessActionContext actionContext);
//    public boolean rollback(BusinessActionContext actionContext);
//}
