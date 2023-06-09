package mirosimo.car_showroom2.model;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import mirosimo.car_showroom2.custom_annotations.CzechChars;
import mirosimo.car_showroom2.custom_annotations.CzechChars1_50;
import mirosimo.car_showroom2.custom_annotations.EnChars1_50;

/*
 * Represent Car Brand e.g. Skoda, Ford, Renault,... 
 * Car Brand is root of hierarchy
 * 
 * 		  1 : N        1 : N		          M	: N
 * CarBrand --> CarModel --> CarEquipmentPack --> CarEngine
 * 
 * */
@Table
@Entity
public class CarBrand {
	
	public CarBrand() {
		this.active = true;
	}
	
	
	@Id
	@SequenceGenerator(
			name = "car_brand_sequence",
			sequenceName = "car_brand_sequence",
			allocationSize = 1
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "car_brand_sequence"
	)
	private long id;
	
	@CzechChars1_50
	private String name;
	
	// name used in url (without diacritic)
	@EnChars1_50
	private String urlName;
	
	@CzechChars1_50
	private String descShort;
	
	@Size(min = 2, max = 300, message = "{app.validation.chars-count2-300}")
	@CzechChars
	private String descLong;
	
	private boolean active;
			
	/* 
	 * This set contains image objects related to  particular car Brand 
	 * Big, Small Brand logo, etc...
	 * 
	 * */
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "f_car_brand_img_id", referencedColumnName = "id")
	private Set<CarBrandImg> carBrandImgSet = new HashSet<>();
	
	@OneToMany(mappedBy="carBrand")
	private Set<CarEngine> carEngines;

	/* Return object containing big logo img (in bytes form) */
	public CarBrandImg getBigLogoImg() {		
		return this.carBrandImgSet.stream()
						.filter(imgObj -> imgObj.getImgType().equals(CarBrandImg.ImgType.BRAND_LOGO) 
								&& imgObj.isActive())
						.findFirst()
						.get();						
	}
	
	/* Return object containing small logo img (in bytes form) */
	public CarBrandImg getSmallLogoImg() {		
		return this.carBrandImgSet.stream()
						.filter(imgObj -> imgObj.getImgType().equals(CarBrandImg.ImgType.BRAND_LOGO_SMALL)
								&& imgObj.isActive())
						.findFirst()
						.get();						
	}
	
	/* Return img represents Car Brand */
	public CarBrandImg getMainBrandImg() {		
		return this.carBrandImgSet.stream()
						.filter(imgObj -> imgObj.getImgType().equals(CarBrandImg.ImgType.BRAND_IMG)
								&& imgObj.isActive())
						.findFirst()
						.get();						
	}
	
	/* Getters, Setters*/
	public String getName() {		
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescShort() {
		return descShort;
	}

	public void setDescShort(String descShort) {
		this.descShort = descShort;
	}

	public String getDescLong() {
		return descLong;
	}

	public void setDescLong(String descLong) {
		this.descLong = descLong;
	}

	public long getId() {
		return id;
	}



	public String getUrlName() {
		return urlName;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Set<CarBrandImg> getCarBrandImgSet() {
		return carBrandImgSet;
	}

	public void setCarBrandImgSet(Set<CarBrandImg> carBrandImgSet) {
		this.carBrandImgSet = carBrandImgSet;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Set<CarEngine> getCarEngines() {
		return carEngines;
	}

	public void setCarEngines(Set<CarEngine> carEngines) {
		this.carEngines = carEngines;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CarBrand entity = (CarBrand) obj;
		return id != 0L && id == entity.getId();		
	}
	
	/* Is used one number for all entities - one bucket for all entities 
	 * Reason why - ID is generated in database and therefore could 
	 * exist entities in transient state which don't have assigned ID yet */
	@Override
	public int hashCode() {
		return 23;
	}
}
