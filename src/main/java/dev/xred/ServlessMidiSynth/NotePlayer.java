package dev.xred.ServlessMidiSynth;

public class NotePlayer implements Runnable {

    private int note;
    private int velocity;

    public boolean playOn;

    public NotePlayer(int note, int velocity){
        this.note = note;
        this.velocity = velocity;
    }

    public int getNote() {
        return note;
    }

    public int getVelocity() {
        return velocity;
    }

    @Override
    public void run() {

        var midiPlayer = MidiPlayer.getInstance();
        var mc = midiPlayer.getMidiChannel();

        velocity += 50;
        mc.noteOn(note, velocity);

        while(!Thread.interrupted());

        mc.noteOff(note);

    }
}
