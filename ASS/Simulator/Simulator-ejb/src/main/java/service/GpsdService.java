package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.PositionInfo;

/**
 * GpsdService Class.
 * @author Melanie
 */
public class GpsdService {
    private static final Logger LOGGER =
            Logger.getLogger(GpsdService.class.getCanonicalName());
    private static final String GPSD_PIPE = "/tmp/gps";
    
    private BufferedWriter pipeWriter;
    
    /**
     * Sends NMEA RMC report to linux gps daemon, gpsd via predetermined pipe.
     *
     * @param position coordinates.
     */
    public void updatePosition(PositionInfo position) {
        // an NMEA RMC position sentence (report) is of form:
        // $GPRMC,124426,A,5920.7019,N,02803.2893,E,,,121212,,
        Calendar cal = Calendar.getInstance();
        Integer hour = cal.get(Calendar.HOUR_OF_DAY);
        Integer minute = cal.get(Calendar.MINUTE);
        Integer second = cal.get(Calendar.SECOND);
        Integer date = cal.get(Calendar.DATE);
        // java Calendar month
        Integer month = cal.get(Calendar.MONTH) + 1; 
        // starts at 0
        // convert to 2 digit year
        Integer year = cal.get(Calendar.YEAR) % 100; 

        String sHour = String.format("%02d", hour);
        String sMinute = String.format("%02d", minute);
        String sSecond = String.format("%02d", second);
        String sDate = String.format("%02d", date);
        String sMonth = String.format("%02d", month);
        String sYear = String.format("%02d", year);

        String sentence;
        
        if (position == null) {
            sentence = "$GPRMC," + sHour + sMinute + sSecond + ",A,,,,,,," + 
                    sDate + sMonth + sYear + ",,";
        } else {
            double lat = position.getPosition().getLatitude();
            double lon = position.getPosition().getLongitude();

            String latSuffix = "N";
            
            if (lat < 0) {
                latSuffix = "S";
            }
            
            lat = Math.abs(lat);
            String lonSuffix = "E";
            
            if (lon < 0) {
                lonSuffix = "W";
            }
            
            lon = Math.abs(lon);
            
            // degree part
            long latDeg = (long) lat; 
            // minute part
            double latMin = (lat - latDeg) * 60; 
            // whole part of minute
            long latMinWhole = (long) latMin; 
            // fractional part of
            Double latMinFrac = latMin - latMinWhole; 
            // minute
            String sLatDeg = String.format("%02d", latDeg);
            String sLatMinWhole = String.format("%02d", latMinWhole);
            String sLatMinFrac = latMinFrac.toString().replace("0.", ".");
            // degree part
            long lonDeg = (long) lon; 
            // minute part
            double lonMin = (lon - lonDeg) * 60; 
            // whole part of minute
            long lonMinWhole = (long) lonMin; 
            // fractional part of
            Double lonMinFrac = lonMin - lonMinWhole;
            // minute
            String sLonDeg = String.format("%02d", lonDeg);
            String sLonMinWhole = String.format("%02d", lonMinWhole);
            String sLonMinFrac = lonMinFrac.toString().replace("0.", ".");

            sentence = "$GPRMC," + sHour + sMinute + sSecond + ",A," + sLatDeg 
                    + sLatMinWhole + sLatMinFrac + ","
                    + latSuffix + "," + sLonDeg + sLonMinWhole + sLonMinFrac 
                    + "," + lonSuffix + ",,," + sDate + sMonth
                    + sYear + ",,";
        }

        try {
            this.pipeWriter.write(sentence + "\r\n");
            this.pipeWriter.flush();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
    
    /**
     * Initialize gpsd.
     * 
     * @throws java.io.IOException .
     * @throws java.lang.InterruptedException .
     */
    public void initGpsd() throws IOException, InterruptedException {
        this.startProc("killall -9 gpsd", false);
        this.startProc("rm -f " + GpsdService.GPSD_PIPE, false);
        this.startProc("mkfifo " + GpsdService.GPSD_PIPE, false);
        this.startProc("gpsd " + GpsdService.GPSD_PIPE, false);
        //writer for gpsd pipe
        this.pipeWriter = new BufferedWriter(
                new FileWriter(GpsdService.GPSD_PIPE));
    }

    /**
     * Start given process.
     *
     * @param command.
     * @param wait for process to exit.
     * @return int.
     * @throws java.lang.InterruptedException .
     */
    public int startProc(String command, Boolean wait) throws IOException, 
            InterruptedException {
        String[] commandArray = command.split(" ");
        ProcessBuilder pb = new ProcessBuilder(commandArray);
        //redirect errorstream and outputstream to single stream.
        pb.redirectErrorStream(true);   
        Process proc = pb.start();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                proc.getInputStream()));
        String line;
        
        //empty the output buff of the proc.
        while ((line = in.readLine()) != null) {
            LOGGER.log(Level.INFO, line);
        }

        if (wait) {
            return proc.waitFor();
        } else {
            return 0;
        }
    }
}
