package com.share.teacher.bean;

/**
 * @author czq
 * @desc 请用一句话描述它
 * @date 16/5/7
 */
public class CertifyStatus {


    private String idcardStatus;//	身份认证状态	是	Int	0.未认证 1.认证审核中 2.认证通过 3.认证不通过
    private String auditStatus;//	学历认证状态	是	Int	0.未认证 1.认证审核中 2.认证通过 3.认证不通过

    public String getIdcardStatus() {
        return idcardStatus;
    }

    public void setIdcardStatus(String idcardStatus) {
        this.idcardStatus = idcardStatus;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }
}
