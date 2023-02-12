package de.joshizockt.rpcwrapper.commands;

import de.joshizockt.rpcwrapper.RPCWrapper;
import de.joshizockt.rpcwrapper.preset.Preset;
import de.joshizockt.rpcwrapper.util.command.Command;

public class PresetCommand extends Command {


    public PresetCommand() {
        super("preset", "Manage Presets", "presets", "p");
    }

    @Override
    public void execute(String label, String[] args) {
        if(args.length == 0) {
            System.out.println("Usage: " + label + " <list|load> [name]");
            return;
        }
        String arg = args[0];
        switch (arg.toLowerCase()) {
            case "list":
                System.out.println("Presets:");
                System.out.println(" - default");
                for(Preset preset : RPCWrapper.getInstance().getPresetLoader().getPresets()) {
                    System.out.println(" - " + preset.getName());
                }
                break;
            case "load":
                if(args.length == 1) {
                    System.out.println("Usage: " + label + " load <name>");
                    return;
                }
                String name = args[1];
                if(name.equalsIgnoreCase("default") || name.equalsIgnoreCase("def")) {
                    RPCWrapper.updatePresence(RPCWrapper.DEFAULT_PRESENCE);
                    System.out.println("Loaded default preset!");
                    return;
                }
                Preset preset = RPCWrapper.getInstance().getPresetLoader().loadPreset(name);
                if(preset == null) {
                    System.out.println("Preset '" + name + "' not found!");
                    return;
                }
                RPCWrapper.updatePresence(RPCWrapper.getInstance().getPresetLoader().toPresence(preset));
                System.out.println("Preset '" + name + "' loaded!");
                break;
        }
    }

}
