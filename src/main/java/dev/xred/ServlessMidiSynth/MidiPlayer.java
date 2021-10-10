package dev.xred.ServlessMidiSynth;

import javax.sound.midi.*;

public class MidiPlayer {

    private int lastOutputed = 0;

    private static MidiPlayer instance;

    private MidiChannel[] mChannels;
    private Instrument[] instr;

    private int instrumentNum = 0;

    private MidiPlayer(){
        Synthesizer midiSynth = null;

        try {
            midiSynth = MidiSystem.getSynthesizer();
            midiSynth.open();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }

        instr = midiSynth.getDefaultSoundbank().getInstruments();
        mChannels = midiSynth.getChannels();

        if(!midiSynth.loadInstrument(instr[instrumentNum])) System.out.println("cant load instrument - " + instrumentNum);;

    }

    public static MidiPlayer getInstance() {
        if(instance == null)
            instance = new MidiPlayer();

        return instance;
    }

    public MidiChannel getMidiChannel(){
        if (lastOutputed > 3) lastOutputed = 0;
        return mChannels[lastOutputed++];
    }

}
