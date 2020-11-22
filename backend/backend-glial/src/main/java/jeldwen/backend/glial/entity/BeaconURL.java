package jeldwen.backend.glial.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "beacon_urls", uniqueConstraints = {
		@UniqueConstraint(columnNames = "machine")
})
@Data
@Accessors(chain = true)
public class BeaconURL {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private long id;
	
	@Column
	private String machine;
	
	@Column(length = Short.MAX_VALUE)
	private String url;
	
}