package com.supplierhub.supplierhub.persistence.entity;

import java.time.ZonedDateTime;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Version;

@MappedSuperclass
public class MasterEntity {

	@Id
	@Column(name = "id", nullable = false)
	@UuidGenerator
	private String id;

	@Column(name = "created_at", nullable = false, updatable = false)
	@CreatedDate
	private ZonedDateTime createdAt;

	@Column(name = "updated_at")
	@LastModifiedDate
	private ZonedDateTime updatedAt;

	@Column(name = "version")
	@Version
	private Long version;

	@Column(name = "deleted_at")
	private ZonedDateTime deletedAt;
	
	@PrePersist
	public void prePersist() {
		createdAt = ZonedDateTime.now();
	}

	@PreUpdate
	public void preUpdate() {
		updatedAt = ZonedDateTime.now();
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ZonedDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(ZonedDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public ZonedDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(ZonedDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public ZonedDateTime getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(ZonedDateTime deletedAt) {
		this.deletedAt = deletedAt;
	}
	
	
}
