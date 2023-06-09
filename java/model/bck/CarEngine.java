package mirosimo.car_showroom2.model.bck;


import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import mirosimo.car_showroom2.enums.PropellantType;
import mirosimo.car_showroom2.model.CarBrand;
import mirosimo.car_showroom2.model.CarEngineImg;
import mirosimo.car_showroom2.model.CarEquipmentPackCarEngine;


/*
 * Relation between Car Brand and Car Engine.
 * 1 : N
 * Each type of Engine could belong to one Car Brand, 
 * each Car Brand could have more types of engines.
 * In real world Car companies share the Engines, but in most case have slightly different
 * engine tuning. So here in system is simplification - each type of Engine can belong just to one Car brand.
 * Relation is one to many. 
 *  
 * Relation between Car Engine and Equipment Pack
 * M : N  
 * Each equipments pack could have multiple types of engines.
 * And Each Engine can appear in more equipments pack.
 * 
 * Some types of engines available in one equipmant pack couldn't be available
 * in another equipment pack.
 * e.g. In basic equipment pack couldn't be possible for customer to order 
 *   the most powerfull engine.
 *   
 *        1 : N         1 : N                M : N
 * CarBrand --> CarModel --> CarEquipmentPack --> CarEngine
 * 
 * */
@Table
@Entity(name = "bckcarengine")
public class CarEngine {		
	
	@Id
	@SequenceGenerator(
			name = "car_engine_sequence",
			sequenceName = "car_engine_sequence",
			allocationSize = 1
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "car_engine_sequence"
	)
	@Column(name = "ID")
	private long id;
	
	@ManyToOne
    @JoinColumn(name="car_brand_id")
	CarBrand carBrand;
	
	/*
	 * Petrol, Deisel, Electric, LPG
	 */
	@Enumerated(EnumType.STRING)
	private PropellantType propellantType;   // GAS, DIESEL, ELECTRIC
	
	/* 
	 * Because of Sorting reasons - some engines could 
	 * belong to certain engine family (TSI, MPI, DIESEL etc...)
	 * 
	 */
	private String engineGroup;	
	
	/*
	 * Engine code
	 */
	private String code;
	
	/*
	 * Power in Kw
	 */
	@NotNull
    @Min(10)
	@Max(10000)
	private int power; 		
	
	@Size(min = 2, max = 50, message = "{app.validation.chars-count2-50}")
	@Pattern(regexp="^[.-ěščřžýáíéóúůďťňĎŇŤŠČŘŽÝÁÍÉÚŮĚÓa-zA-Z0-9\s]{2,50}",message="{app.validation.chars}")
	private String name;
	
	@Size(min = 2, max = 50, message = "{app.validation.chars-count2-50}")
	@Pattern(regexp="^[.-ěščřžýáíéóúůďťňĎŇŤŠČŘŽÝÁÍÉÚŮĚÓa-zA-Z0-9\s]{2,50}",message="{app.validation.chars}")
	private String nameMarketing;
	
	
	
	/* 
	 * Each engine could have set of images... 
	 * */
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "f_car_engine_id", referencedColumnName = "id")
	private Set<CarEngineImg> carEngineImgs = new HashSet<>();
	

	@OneToMany(mappedBy = "primaryKey.carEngine",
            cascade = CascadeType.ALL)
	private Set<CarEquipmentPackCarEngine> carEquipmentPackCarEngines = new HashSet<>();
	
	
	/* 
	 * Comparator - order CarEngine by following fields
	 *  - propellantType (Diesel, Petrol, Electro, etc...)
	 *  - engine group (TSI, MPI, DIESEL, etc...)
	 *  - name (1.4 MPI, 1.8 TSI, etc...)
	 *  
	 */
	public static Comparator<CarEngine> getComparatorByFuelGroupName() {
		Comparator<CarEngine> cc = Comparator
				.comparing((CarEngine carEngine) -> carEngine.getPropellantType().getNiceValue())						
				.thenComparing(Comparator.comparing(CarEngine::getEngineGroup))
				.thenComparing(Comparator.comparing(CarEngine::getName));
		return cc;
	}
	
	
	/* getters, setters*/
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameMarketing() {
		return nameMarketing;
	}

	public void setNameMarketing(String nameMarketing) {
		this.nameMarketing = nameMarketing;
	}

	public Set<CarEngineImg> getCarEngineImgs() {
		return carEngineImgs;
	}	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCarEngineImgs(Set<CarEngineImg> carEngineImgs) {
		this.carEngineImgs = carEngineImgs;
	}



	public CarBrand getCarBrand() {
		return carBrand;
	}

	public void setCarBrand(CarBrand carBrand) {
		this.carBrand = carBrand;
	}

	public Set<CarEquipmentPackCarEngine> getCarEquipmentPackCarEngines() {
		return carEquipmentPackCarEngines;
	}

	public void setCarEquipmentPackCarEngines(Set<CarEquipmentPackCarEngine> carEquipmentPackCarEngines) {
		this.carEquipmentPackCarEngines = carEquipmentPackCarEngines;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CarEngine entity = (CarEngine) obj;
		return id != 0L && id == entity.getId();		
	}
	
	/* Is used one number for all entities - one bucket for all entities 
	 * Reason why - ID is generated in database and therefore could 
	 * exist entities in transient state which don't have assigned ID yet */
	@Override
	public int hashCode() {
		return 23;
	}

	public PropellantType getPropellantType() {
		return propellantType;
	}

	public void setPropellantType(PropellantType propellantType) {
		this.propellantType = propellantType;
	}

	public String getEngineGroup() {
		return engineGroup;
	}

	public void setEngineGroup(String engineGroup) {
		this.engineGroup = engineGroup;
	}
	
}
