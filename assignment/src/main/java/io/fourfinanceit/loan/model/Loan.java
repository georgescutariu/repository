package io.fourfinanceit.loan.model;

import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.fourfinanceit.loan.util.DateUtil;

@Entity
public class Loan {

	@Id
	@GeneratedValue
	private long id;

	@NotNull
	private BigInteger amount;

	@NotNull
	private Integer term;
	private LocalDateTime start;

	private String ipAddress;

	@JsonIgnore
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Client client;

	public BigInteger getAmount() {
		return amount;
	}

	public void setAmount(BigInteger amount) {
		this.amount = amount;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getLastDay() {
		return start.plusDays(this.term);
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Override
	public String toString() {
		return "Loan [id=" + id + ", amount=" + amount + ", term=" + term + ", start=" + DateUtil.formatDateTime(start) + ", ipAddress="
				+ ipAddress + "]";
	}
}
