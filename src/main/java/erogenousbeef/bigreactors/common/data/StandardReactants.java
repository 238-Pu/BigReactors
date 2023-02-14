package erogenousbeef.bigreactors.common.data;

import erogenousbeef.bigreactors.api.data.SourceProductMapping;
import erogenousbeef.bigreactors.api.registry.Reactants;

public class StandardReactants {

	//public static final String yellorium = "yellorium";
	//public static final String cyanite = "cyanite";
	//public static final String blutonium = "blutonium";
	
	public static final String thorium = "thorium";
	public static final String natUranium = "natUranium";
	public static final String uranium = "uranium";
	public static final String MOX = "mox";	
	public static final String plutonium = "plutonium";
	public static final String schrabidium = "schrabidium";
	
	public static final String thoriumWaste = "thoriumWaste";
	public static final String mixPlutonium = "mixPlutonium";
	public static final String uraniumWaste = "uraniumWaste";
	public static final String MOXWaste = "moxWaste";
	public static final String plutoniumWaste = "plutoniumWaste";
	public static final String schrabidiumWaste = "schrabidiumWaste";
	
	//public static final int colorYellorium = BigReactors.defaultFluidColorFuel;
	//public static final int colorCyanite = BigReactors.defaultFluidColorWaste;
	
	public static final int colorThorium = 0x665448;
	public static final int colorUranium = 0x9AA196;
	//public static final int colorUranium = 0xA6F6A4;
	public static final int colorMOX = 0x89918A;
	public static final int colorPlutonium = 0x78817E;
	public static final int colorSchrabidium = 0x9BBAAF;

	public static final int colorThoriumWaste = 0x6E451F;
	public static final int colorUraniumWaste = 0x6E5639;
	public static final int colorMOXWaste = 0x735F3E;
	public static final int colorPlutoniumWaste = 0x62492D;
	public static final int colorSchrabidiumWaste = 0x6F6242;
	
	// These are used as fallbacks
	//public static SourceProductMapping yelloriumMapping;
	//public static SourceProductMapping cyaniteMapping;

	public static SourceProductMapping thoriumMapping;
	public static SourceProductMapping thoriumWasteMapping;
	public static SourceProductMapping uraniumMapping;
	public static SourceProductMapping uraniumWasteMapping;
	public static SourceProductMapping moxMapping;
	public static SourceProductMapping moxWasteMapping;
	public static SourceProductMapping plutoniumMapping;
	public static SourceProductMapping plutoniumWasteMapping;
	public static SourceProductMapping schrabidiumMapping;
	public static SourceProductMapping schrabidiumWasteMapping;
	
	public static void register() {
		//Reactants.registerReactant(yellorium, 0, colorYellorium);
		//Reactants.registerReactant(cyanite, 1, colorCyanite);
		//Reactants.registerReactant(blutonium, 0, colorYellorium);
		
		Reactants.registerReactant(thorium, 0, colorThorium);
		Reactants.registerReactant(natUranium, 2, colorUranium);
		Reactants.registerReactant(uranium, 0, colorUranium);
		Reactants.registerReactant(MOX, 0, colorMOX);
		Reactants.registerReactant(plutonium, 0, colorPlutonium);
		Reactants.registerReactant(schrabidium, 0, colorSchrabidium);

		Reactants.registerReactant(thoriumWaste, 1, colorThoriumWaste);
		Reactants.registerReactant(uraniumWaste, 1, colorUraniumWaste);
		Reactants.registerReactant(MOXWaste, 1, colorMOXWaste);
		Reactants.registerReactant(mixPlutonium, 3, colorPlutonium);
		Reactants.registerReactant(plutoniumWaste, 1, colorPlutoniumWaste);
		Reactants.registerReactant(schrabidiumWaste, 1, colorSchrabidiumWaste);
	}
	
}
