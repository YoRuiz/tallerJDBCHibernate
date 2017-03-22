package org.springframework.samples.petclinic.owner;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Digits;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.visit.Visit;

@Entity
@Table(name="facturas")
public class Bill{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Digits(integer=10, fraction = 0)
	private Long id;
	
	@Column(name="fechaPago")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaPago;
	
	@Column(name="cuantia")
	private Double cuantia;
	
	@OneToOne(fetch=FetchType.EAGER)
	private Visit visit;
	
	@ManyToOne
	@JoinColumn(name="owner_id")
	private Owner owner;
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getFechaPago() {
		return fechaPago;
	}
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}
	public Double getCuantia() {
		return cuantia;
	}
	public void setCuantia(Double cuantia) {
		this.cuantia = cuantia;
	}
	public Visit getVisit() {
		return visit;
	}
	public void setVisit(Visit visit) {
		this.visit = visit;
	}
	public Owner getOwner() {
		return owner;
	}
	public void setOwner(Owner owner) {
		this.owner = owner;
	}
	public Bill() {
	}
	
}
