package com.tjau.bbs.tjaubbs.domain;

import java.util.Date;

public class Process {

    private String id;
    private String contractId;
    private Contract contract;
    private String title;
    private String describe;
    private String file;
    private Date publicDate;

    public Process() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }


    public Date getPublicDate() {
        return publicDate;
    }

    public void setPublicDate(Date publicDate) {
        this.publicDate = publicDate;
    }

    @Override
    public String toString() {
        return "Process{" +
                "id='" + id + '\'' +
                ", contractId='" + contractId + '\'' +
                ", title='" + title + '\'' +
                ", describe='" + describe + '\'' +
                ", file='" + file + '\'' +
                ", publicDate=" + publicDate +
                '}';
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}
