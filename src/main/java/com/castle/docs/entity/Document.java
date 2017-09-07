package com.castle.docs.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.castle.repo.jpa.DataEntity;

@Entity
@Table(name = "tbl_document")
public class Document extends DataEntity<Admin, Long> {

	private static final long serialVersionUID = -1715788778238878681L;

	@NotNull
	@Size(max = 200)
	@Column(length = 200, nullable = false)
	private String title;

	@Size(max = 1000)
	@Column(length = 1000)
	private String summary;

	@Lob
	private String content;

	@Lob
	private String catalog;

	@Lob
	private String htmlContent;

	private boolean published = false;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

}
