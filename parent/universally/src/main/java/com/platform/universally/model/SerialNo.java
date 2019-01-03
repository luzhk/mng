package com.platform.universally.model;

import javax.persistence.*;

@Entity
@Table(name="base_make_serialno")
public class SerialNo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

	@Column(name="module_type")
    private String moduleType;

	@Column(name="serial_type")
    private String serialType;

	@Column(name="serial_no")
    private Long serialNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType == null ? null : moduleType.trim();
    }

    public String getSerialType() {
        return serialType;
    }

    public void setSerialType(String serialType) {
        this.serialType = serialType == null ? null : serialType.trim();
    }

    public Long getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Long serialNo) {
        this.serialNo = serialNo;
    }
}