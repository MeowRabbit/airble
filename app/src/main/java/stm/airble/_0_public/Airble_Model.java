package stm.airble._0_public;

import java.time.LocalDateTime;

public class Airble_Model {
    private String SSID = "";                    // 기기의 와이파이명
    private String Nick_Name = "";               // 사용자가 정해준 기기의 이름, 별칭
    private String Model_Num = "";               // 기기의 모델 번호
    private String MAC_Address = "";             // 기기의 MAC 주소
    private String Connected_Router_SSID = "";   // 기기와 연결된 공유기 와이파이명
    private boolean Owner = false;               // 기기 주인 여부 ( true = 주인, false = 공유받음 )
    private LocalDateTime update_date = null;    // 기기의 마지막 데이터 갱신 시간
    private int page_num = 0;                   // 기기의 페이지 번호

    private int CO = 0;     // 기기가 측정한 CO 값
    private int CO2 = 0;    // 기기가 측정한 CO2 값
    private int VOCs = 0;   // 기기가 측정한 VOCs 값
    private int PM = 0;     // 기기가 측정한 미세먼지 값
    private int temp = 0;   // 기기가 측정한 온도 값
    private int humi = 0;   // 기기가 측정한 습도 값

    private int brightness = 100;   // 기기의 밝기
    private int volume = 255;       // 기기의 소리크기

    private String Place_code = "";
    private String Place_name = "";
    private float Outside_Temp = 0.0f;
    private float Outside_Humi = 0.0f;
    private int Outside_PM = 0;

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public String getNick_Name() {
        return Nick_Name;
    }

    public void setNick_Name(String nick_Name) {
        Nick_Name = nick_Name;
    }

    public String getModel_Num() {
        return Model_Num;
    }

    public void setModel_Num(String model_Num) {
        Model_Num = model_Num;
    }

    public String getMAC_Address() {
        return MAC_Address;
    }

    public void setMAC_Address(String MAC_Address) {
        this.MAC_Address = MAC_Address;
    }

    public String getConnected_Router_SSID() {
        return Connected_Router_SSID;
    }

    public void setConnected_Router_SSID(String connected_Router_SSID) {
        Connected_Router_SSID = connected_Router_SSID;
    }

    public boolean isOwner() {
        return Owner;
    }

    public void setOwner(boolean owner) {
        Owner = owner;
    }

    public LocalDateTime getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(LocalDateTime update_date) {
        this.update_date = update_date;
    }

    public int getPage_num() {
        return page_num;
    }

    public void setPage_num(int page_num) {
        this.page_num = page_num;
    }

    public int getCO() {
        return CO;
    }

    public void setCO(int CO) {
        this.CO = CO;
    }

    public int getCO2() {
        return CO2;
    }

    public void setCO2(int CO2) {
        this.CO2 = CO2;
    }

    public int getVOCs() {
        return VOCs;
    }

    public void setVOCs(int VOCs) {
        this.VOCs = VOCs;
    }

    public int getPM() {
        return PM;
    }

    public void setPM(int PM) {
        this.PM = PM;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getHumi() {
        return humi;
    }

    public void setHumi(int humi) {
        this.humi = humi;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getPlace_code() {
        return Place_code;
    }

    public void setPlace_code(String place_code) {
        Place_code = place_code;
    }

    public String getPlace_name() {
        return Place_name;
    }

    public void setPlace_name(String place_name) {
        Place_name = place_name;
    }

    public float getOutside_Temp() {
        return Outside_Temp;
    }

    public void setOutside_Temp(float outside_Temp) {
        Outside_Temp = outside_Temp;
    }

    public float getOutside_Humi() {
        return Outside_Humi;
    }

    public void setOutside_Humi(float outside_Humi) {
        Outside_Humi = outside_Humi;
    }

    public int getOutside_PM() {
        return Outside_PM;
    }

    public void setOutside_PM(int outside_PM) {
        Outside_PM = outside_PM;
    }
}
