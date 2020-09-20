package jeldwen.backend.beacon.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "product_families", uniqueConstraints = {
		@UniqueConstraint(columnNames = "key")
})
@Embeddable
@Data
@Accessors(chain = true)
public class ProductFamily {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column
	private String key;
	
	@Column
	private String name;
	
	@Column
	private long cycleTime;
	
}