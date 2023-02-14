package erogenousbeef.bigreactors.api.data;

import erogenousbeef.bigreactors.common.BigReactors;

public class ReactantData {

	public enum ReactantType {
		Fuel,
		Waste,
		Breeder,
		Bred,
	};
	public static final ReactantType[] s_Types = ReactantType.values();
	
	private String name;
	private ReactantType type;
	private int color;
	
	public String getName() { return name; }
	public ReactantType getType() { return type; }
	public int getColor() { return color; }

	/**
	 * Declare a new set of reactant data with a default color.
	 * Waste will by Cyanite Cyan and fuel will be Yellorium Yellow.
	 * @param name The name of this reactant. Must be unique.
	 * @param type The type of this reactant. Fuel/Waste.
	 * @param color The color to use when rendering fuel rods with this reactant in it.
	 */
	public ReactantData(String name, ReactantType type) {
		this.name = name;
		this.type = type;
		if(type == ReactantType.Fuel || type == ReactantType.Breeder)
		{
			this.color=BigReactors.defaultFluidColorFuel;
		}
		else if(type == ReactantType.Waste || type == ReactantType.Bred)
		{
			this.color=BigReactors.defaultFluidColorWaste;
		}
	//this.color = type == ReactantType.Fuel ? BigReactors.defaultFluidColorFuel : BigReactors.defaultFluidColorWaste;
	}
	
	/**
	 * Declare a new set of reactant data.
	 * @param name The name of this reactant. Must be unique.
	 * @param type The type of this reactant. Fuel/Waste.
	 * @param color The color to use when rendering fuel rods with this reactant in it.
	 */
	public ReactantData(String name, ReactantType type, int color) {
		this.name = name;
		this.type = type;
		this.color = color;
	}
	
	/**
	 * @return True if this reactant is considered fuel.
	 */
	public boolean isFuel() {
		return type == ReactantType.Fuel;
	}
	
	/**
	 * @return True if this reactant is considered waste.
	 */
	public boolean isWaste() {
		return type == ReactantType.Waste;
	}
	
	/**
	 * @return True if this reactant is considered breeder material.
	 */
	public boolean isBreeder() {
		return type == ReactantType.Breeder;
	}
	
	/**
	 * @return True if this reactant is considered irradiated breeder material.
	 */
	public boolean isBred() {
		return type == ReactantType.Bred;
	}
}
