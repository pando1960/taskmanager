package com.petermts.tmbackend.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Task {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="title", nullable=false, length=60)
	private String title;
	
	@Column(name="description", nullable=true, length=512)
	private String description;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="status")
	private Status status;
	
	@Column(name="due", nullable=false)
	@JsonFormat(pattern="EEE dd MMM yyyy HH:mm")
	private Date due;

	// constructors.
	public Task() {
		super();
	}

	public Task(String title, String description, Status status, Date due) {
		super();
		this.title = title;
		this.description = description;
		this.status = status;
		this.due = due;
	}
	
	// setters and getters.
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getDue() {
		return due;
	}

	public void setDue(Date due) {
		this.due = due;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", title=" + title + ", description=" + description + ", status=" + status + ", due="
				+ due + "]";
	}
}
