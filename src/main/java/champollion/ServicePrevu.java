package champollion;

public class ServicePrevu {

    private int volumeCM;
    private int volumeTD;
    private int volumeTP;
    private UE ue;

    public ServicePrevu(int volumeCM, int volumeTD, int volumeTP, UE ue) {
        this.volumeCM = volumeCM;
        this.volumeTD = volumeTD;
        this.volumeTP = volumeTP;
        this.ue = ue;
    }

    public int getServiceType(TypeIntervention type) {
        int volume = 0;
        if (type.equals(TypeIntervention.CM)){
            volume = volume+  this.getVolumeCM();
        }
        if (type.equals(TypeIntervention.TD)){
            volume = volume + this.getVolumeTD();
        }
        if (type.equals(TypeIntervention.TP)){
            volume = volume + this.getVolumeTP();
        }
        return volume;
    }

    public int getVolumeCM() {
        return volumeCM;
    }

    public int getVolumeTD() {
        return volumeTD;
    }

    public int getVolumeTP() {
        return volumeTP;
    }

    public UE getUe() {
        return ue;
    }

    public void addToCM(int plus){
        volumeCM =   volumeCM + plus;
    }

    public void addToTD(int plus){
        volumeTD =   volumeTD + plus;
    }

    public void addToTP(int plus){
        volumeTP =  volumeTP + plus;
    }
}