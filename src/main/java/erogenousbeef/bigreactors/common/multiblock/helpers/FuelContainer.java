package erogenousbeef.bigreactors.common.multiblock.helpers;

import com.hbm.hazard.HazardRegistry;

import erogenousbeef.bigreactors.api.data.ReactorReaction;
import erogenousbeef.bigreactors.api.registry.Reactants;
import erogenousbeef.bigreactors.api.registry.ReactorConversions;
import erogenousbeef.bigreactors.common.BRLog;
import erogenousbeef.bigreactors.common.data.ReactantStack;
import erogenousbeef.bigreactors.common.data.StandardReactants;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Class to help with fuel/waste tracking in reactors.
 * For now, 
 * @author ErogenousBeef
 *
 */
public class FuelContainer extends ReactantContainer {
	private static final String[] tankNames = { "fuel", "waste" };
	private static final int FUEL = 0;
	private static final int WASTE = 1;
	
	private float radiationFuelUsage;
	
	public FuelContainer() {
		super(tankNames, 0);
		radiationFuelUsage = 0f;
	}

	public int getFuelAmount() {
		return getReactantAmount(FUEL);
	}
	
	public int getWasteAmount() {
		return getReactantAmount(WASTE);
	}
	
	
	@Override
	public boolean isReactantValidForStack(int idx, String name) {
		switch(idx) {
		case FUEL:
			return Reactants.isFuel(name);
		case WASTE:
			// Allow anything into our output slot, in case someone wants to do breeders or something
			return true;
		default:
			return false;
		}
	}

	/**
	 * Add some fuel to the current pile, if possible.
	 * @param name The name of the reactant to add.
	 * @param amount The quantity of reactant to add.
	 * @param doAdd If true, this will only simulate a fill and will not alter the fuel amount.
	 * @return The amount of reactant actually added
	 */
	public int addFuel(String name, int amount, boolean doAdd) {
		if(name == null || amount <= 0) { return 0; }
		else {
			return fill(FUEL, name, amount, doAdd);
		}
	}
	
	/**
	 * Add some waste to the current pile, if possible.
	 * @param incoming A FluidStack representing the fluid to fill, and the maximum amount to add to the tank.
	 * @return The amount of waste actually added
	 */
	public int addWaste(String name, int amount) {
		int wasteAdded = fill(WASTE, name, amount, true);
		return wasteAdded;
	}
	
	private int addWaste(int wasteAmt) {
		if(this.getWasteType() == null) {
			BRLog.warning("System is using addWaste(int) when there's no waste present, defaulting to uranium waste");
			return fill(WASTE, StandardReactants.uraniumWaste, wasteAmt, true);
		}
		else {
			return addToStack(WASTE, wasteAmt);
		}
	}

	public int dumpFuel() {
		return dump(FUEL);
	}
	
	public int dumpFuel(int amount) {
		return dump(FUEL, amount);
	}
	
	public int dumpWaste() {
		return dump(WASTE);
	}
	
	public int dumpWaste(int amount) {
		return dump(WASTE, amount);
	}
	
	public String getFuelType() {
		return getReactantType(FUEL);
	}
	
	public String getWasteType() {
		return getReactantType(WASTE);
	}

	public NBTTagCompound writeToNBT(NBTTagCompound destination) {
		super.writeToNBT(destination);
		
		destination.setFloat("fuelUsage", radiationFuelUsage);
		return destination;
	}
	
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		
		if(data.hasKey("fuelUsage")) {
			radiationFuelUsage = data.getFloat("fuelUsage");
		}
	}
	
	public void emptyFuel() {
		setReactant(FUEL, null);
	}
	
	public void emptyWaste() {
		setReactant(WASTE, null);
	}
	
	public void setFuel(ReactantStack newFuel) {
		setReactant(FUEL, newFuel);
	}
	
	public void setWaste(ReactantStack newWaste) {
		setReactant(WASTE, newWaste);
	}
	
	public void merge(FuelContainer other) {
		radiationFuelUsage = Math.max(radiationFuelUsage, other.radiationFuelUsage);
		super.merge(other);
	}
	
	public void onRadiationUsesFuel(float fuelUsed) {
		if(Float.isInfinite(fuelUsed) || Float.isNaN(fuelUsed)) { return; }
		
		radiationFuelUsage += fuelUsed;
		
		if(radiationFuelUsage < 1f) {
			return;
		}

		int fuelToConvert = Math.min(getFuelAmount(), (int)radiationFuelUsage);

		if(fuelToConvert <= 0) { return; }
		
		radiationFuelUsage = Math.max(0f, radiationFuelUsage - fuelToConvert);

		String fuelType = getFuelType();
		if(fuelType != null) {
			this.dumpFuel(fuelToConvert);
			
			if(getWasteType() != null) {
				// If there's already waste, just keep on producing the same type.
				this.addWaste(fuelToConvert);
			}
			else {
				// Create waste type from registry
				ReactorReaction reaction = ReactorConversions.get(fuelType);
				String wasteType = reaction == null ? null : reaction.getProduct();

				if(wasteType == null) {
					BRLog.warning("Could not locate waste for reaction of fuel type " + fuelType + "; using cyanite");
					wasteType = StandardReactants.mixPlutonium;
				}
				
				this.addWaste(wasteType, fuelToConvert);
			}
		}
		else {
			BRLog.warning("Attempting to use %d fuel and there's no fuel in the tank", fuelToConvert);
		}
	}
	
	public float getFuelReactivity() {
		String reactant = getFuelType();
		ReactorReaction reaction = ReactorConversions.get(reactant);
		if(reaction == null) {
			BRLog.warning("Could not locate reaction data for reactant type " + reactant + "; using default value for reactivity");
			return ReactorReaction.standardReactivity;
		}
		else {
			return reaction.getReactivity();
		}
	}
	
	public float getFuelRadioactivity() {
		String reactant = getFuelType();
		if(reactant==StandardReactants.thorium)
		{
			return HazardRegistry.thf*(getFuelAmount()/(Reactants.standardSolidReactantAmount*6));
		}
		/*if(reactant==StandardReactants.natUranium)
		{
			return 0.5F*(getFuelAmount()/(Reactants.standardSolidReactantAmount*9));
		}*/
		if(reactant==StandardReactants.uranium)
		{
			return HazardRegistry.uf*(getFuelAmount()/(Reactants.standardSolidReactantAmount*6));
		}
		if(reactant==StandardReactants.MOX)
		{
			return HazardRegistry.mox*(getFuelAmount()/(Reactants.standardSolidReactantAmount*6));
		}
		if(reactant==StandardReactants.plutonium)
		{
			return HazardRegistry.puf*(getFuelAmount()/(Reactants.standardSolidReactantAmount*6));
		}
		if(reactant==StandardReactants.schrabidium)
		{
			return HazardRegistry.saf*(getFuelAmount()/(Reactants.standardSolidReactantAmount*6));
		}		
		return 0;		
	}
	
	public float getWasteRadioactivity() {
		String reactant = getFuelType();
		if(reactant==StandardReactants.thoriumWaste)
		{
			return 20F*(getWasteAmount()/(Reactants.standardSolidReactantAmount*6));
		}
		/*if(reactant==StandardReactants.mixPlutonium)
		{
			return 40F*(getWasteAmount()/(Reactants.standardSolidReactantAmount*6));
		}*/
		if(reactant==StandardReactants.uraniumWaste)
		{
			return 40F*(getWasteAmount()/(Reactants.standardSolidReactantAmount*6));
		}
		if(reactant==StandardReactants.MOXWaste)
		{
			return 50F*(getWasteAmount()/(Reactants.standardSolidReactantAmount*6));
		}
		if(reactant==StandardReactants.plutoniumWaste)
		{
			return 55F*(getWasteAmount()/(Reactants.standardSolidReactantAmount*6));
		}
		if(reactant==StandardReactants.schrabidiumWaste)
		{
			return 60F*(getWasteAmount()/(Reactants.standardSolidReactantAmount*6));
		}		
		return 0;		
	}
}
