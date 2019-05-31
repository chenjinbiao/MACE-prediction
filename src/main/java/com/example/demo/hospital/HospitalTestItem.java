package com.example.demo.hospital;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name="TestItem")
public class HospitalTestItem {
    @Id
    @Column(name = "identifier")
    private String identifier;
    @Column(name = "abnormalIndicator")
    private String abnormalIndicator;
    @Column(name = "itemCode")
    private String itemCode;

    @Column(name = "itemNO")
    private Integer itemNO;
    @Column(name = "itemName")
    private String itemName;
    @Column(name = "referenceUnits")
    private String referenceUnits;
    @Column(name = "referenceValue")
    private String referenceValue;
    @Column(name = "result")
    private String result;
    @Column(name = "units")
    private String units;
    @Column(name = "reportItemCode")
    private String reportItemCode;
    @Column(name = "reportItemName")
    private String reportItemName;
    @Column(name = "resultDateTime")
    private Timestamp resultDateTime;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getAbnormalIndicator() {
        return abnormalIndicator;
    }

    public void setAbnormalIndicator(String abnormalIndicator) {
        this.abnormalIndicator = abnormalIndicator;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Integer getItemNO() {
        return itemNO;
    }

    public void setItemNO(Integer itemNO) {
        this.itemNO = itemNO;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getReferenceUnits() {
        return referenceUnits;
    }

    public void setReferenceUnits(String referenceUnits) {
        this.referenceUnits = referenceUnits;
    }

    public String getReferenceValue() {
        return referenceValue;
    }

    public void setReferenceValue(String referenceValue) {
        this.referenceValue = referenceValue;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getReportItemCode() {
        return reportItemCode;
    }

    public void setReportItemCode(String reportItemCode) {
        this.reportItemCode = reportItemCode;
    }

    public String getReportItemName() {
        return reportItemName;
    }

    public void setReportItemName(String reportItemName) {
        this.reportItemName = reportItemName;
    }

    public Timestamp getResultDateTime() {
        return resultDateTime;
    }

    public void setResultDateTime(Timestamp resultDateTime) {
        this.resultDateTime = resultDateTime;
    }

    public HospitalTestItem(String identifier, String abnormalIndicator, String itemCode, Integer itemNO, String itemName, String referenceUnits, String referenceValue, String result, String units, String reportItemCode, String reportItemName, Timestamp resultDateTime) {
        this.identifier = identifier;
        this.abnormalIndicator = abnormalIndicator;
        this.itemCode = itemCode;
        this.itemNO = itemNO;
        this.itemName = itemName;
        this.referenceUnits = referenceUnits;
        this.referenceValue = referenceValue;
        this.result = result;
        this.units = units;
        this.reportItemCode = reportItemCode;
        this.reportItemName = reportItemName;
        this.resultDateTime = resultDateTime;
    }

    public HospitalTestItem() {

    }
}
