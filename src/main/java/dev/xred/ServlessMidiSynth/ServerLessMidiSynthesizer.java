package dev.xred.ServlessMidiSynth;

import java.io.*;
import java.util.HashMap;

public class ServerLessMidiSynthesizer {

    private static final String MIDI_DEVICE_NAME = "dmmidi1";

    static HashMap<Byte, Thread> playing = new HashMap<>();

    public static void main(String[] args) throws IOException {

        SimpleLogger logger = new SimpleLogger(ServerLessMidiSynthesizer.class);

        boolean loop = true;
        try(var fis = new FileInputStream("/dev/" + MIDI_DEVICE_NAME); var buf = new BufferedInputStream(fis)){
            byte[] in = buf.readNBytes(3);

            while(loop) {
                NotePlayer notePlayer = null;
                Thread thread = null;
                logger.print((in[2] != 0 ? "pull in " : "pull out") + in[0] + " " + in[1] + " " + in[2]);


            // Cмотрим является ли новый набор байт командой играть ноту или же терминальной командой
                if(in[2] != 0 ){
                    // Играем ноту
                    notePlayer = new NotePlayer(in[1], in[2]);
                    thread = new Thread(notePlayer);
                    thread.start();
                    playing.put(in[1], thread);
                } else {
                    //Прекращаем воспроизведение ноты
                    thread = playing.get(in[1]);

                    if(thread != null){
                        thread.interrupt();
                        thread.stop();

                        playing.remove(in[1]);
                    }

                }
                in = buf.readNBytes(3);
            }

        } catch (IOException io){
            logger.print(io.getLocalizedMessage());
            loop = false;
        }
    }
}



