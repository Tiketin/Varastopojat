package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * @Author Pauli Vuolle-Apiala; 26/03/2021
 */

@Entity
@Table(name = "Storage_Location")
public class StorageLocation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Location_id")
	private int locationId; // Primary key

	@Column(name = "Location_name")
	private String locationName;

	public StorageLocation() {
	}

	public StorageLocation(String newLocationName) {
		this.locationName = newLocationName;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
}