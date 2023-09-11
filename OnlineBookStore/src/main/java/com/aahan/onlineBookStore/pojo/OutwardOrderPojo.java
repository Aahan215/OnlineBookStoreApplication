package com.aahan.onlineBookStore.pojo;

import com.aahan.onlineBookStore.model.EnumData;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class OutwardOrderPojo {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderSeqGen")
	@SequenceGenerator(name = "orderSeqGen", sequenceName = "orderSeqGen", initialValue = 1001, allocationSize = 1)
	private Integer id;

	@Column(nullable = false)
	private LocalDateTime dateTime;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private EnumData.Status status;

	public OutwardOrderPojo() {
		this.dateTime = LocalDateTime.now();
		this.status = EnumData.Status.PROCESSING;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public EnumData.Status getStatus() {
		return status;
	}

	public void setStatus(EnumData.Status status) {
		this.status = status;
	}
}
