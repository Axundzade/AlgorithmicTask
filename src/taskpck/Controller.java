package taskpck;

import com.jfoenix.controls.JFXRadioButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.*;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Controller implements Initializable {

    @FXML Label L0;

    @FXML JFXRadioButton ClockWise;
    @FXML JFXRadioButton AntiClockWise;
    @FXML JFXRadioButton InOut;
    @FXML ToggleGroup group;
    @FXML Label Close;
    @FXML Label L1;
    @FXML Label L2;
    @FXML Label L3;

    private int m;
    private int n;
    private String[][] stringArray;
    private List<String> listOfData = new ArrayList<>();

    private int tempx;
    private int tempy;
    private Boolean smt;


    private List<Integer> movements = new ArrayList<>();
    //right = 1;
    //down = 2;
    //left = 3;
    //up = 4;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ClockWise.setSelected(true);

        Preferences preferences = Preferences.userRoot();
        try {
            preferences.sync();
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }

        String data = preferences.get("data", "");
        String mText = preferences.get("mText","");
        String nText = preferences.get("nText","");

        m = Integer.parseInt(mText);
        n = Integer.parseInt(nText);
        stringArray = new String[m][n];

        String[] rows = data.split(":");

        int r = 0;
        for (String row : rows) {
            if (r==m){
                break;
            }
            try {
                stringArray[r++] = row.split(",");
            }catch (ArrayIndexOutOfBoundsException ignore){}

        }
        start1();

        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {

            if (group.getSelectedToggle()!= null){
                JFXRadioButton chk = (JFXRadioButton) newValue.getToggleGroup().getSelectedToggle();
                String tg = chk.getText();

                if (tg.equals("ClockWise")){
                    movements.clear();
                    listOfData.clear();
                    start1();
                }

                if (tg.equals("AntiClockWise")){
                    movements.clear();
                    listOfData.clear();
                    start1();
                }

                if (tg.equals(InOut.getText())){
                    movements.clear();
                    listOfData.clear();
                    start1();
                }
            }
        });
    }

    private void start1(){
        int startx = m / 2;
        int starty = n / 2;

        tempx = startx;
        tempy = starty;

        if (!(m==4 && n ==4)){
            listOfData.add(stringArray[startx][starty]);}


        if (((m%2==0 && m!=2) && (n%2 ==0 && n!=2))||(m!=n)){
            EvenNumMain();
        }
        else {
            if (ClockWise.isSelected()|| InOut.isSelected()){
                ClockWiseMovement(true);
            }else if (AntiClockWise.isSelected()){
                ClockWiseMovement(false);
            }

            movementSwitchMethod();

            if (InOut.isSelected()){
                Collections.reverse(listOfData);
            }

            String listdata = String.join(", ", listOfData);

            L0.setText(listdata);}
    }

    private void movementSwitchMethod() {
        for (int i: movements){
            switch (i){
                case 1:
                    try{
                        tempy +=1;
                        listOfData.add(stringArray[tempx][tempy]);
                    } catch (ArrayIndexOutOfBoundsException ignored){}
                    break;
                case 2:
                    try {
                        tempx +=1;
                        listOfData.add(stringArray[tempx][tempy]);
                    } catch (ArrayIndexOutOfBoundsException ignored){}
                    break;
                case 3:
                    try {
                        tempy -= 1;
                        listOfData.add(stringArray[tempx][tempy]);
                    } catch (ArrayIndexOutOfBoundsException ignored){}
                    break;
                case 4:
                    try {
                        tempx -=1;
                        listOfData.add(stringArray[tempx][tempy]);
                    }catch (ArrayIndexOutOfBoundsException ignored){}
                    break;
            }
        }
    }

    private void ClockWiseMovement(Boolean movement){
        int tempnm =m+n;

        if (m==4 && n==3){
            tempnm = m+n-2;
        }
        for(int i =1; i<tempnm; i++){
            int tempi = i;
            smt = true;
            while (tempi>=1){
                if (i%2==1){
                    if(smt){
                        movements.add(1);
                        if(tempi--==1){
                            smt=false;
                            tempi=i; }
                        continue;
                    }
                    else{
                        if (movement){
                            movements.add(2);}
                        else {
                            movements.add(4);}
                    }
                    tempi--;
                }
                else {
                    if(smt){
                        movements.add(3);
                        if(tempi--==1){
                            smt=false;
                            tempi=i;
                        }
                        continue;
                    }
                    else{

                        if (movement){
                            movements.add(4);}
                        else {
                            movements.add(2);
                        }
                    }
                    tempi--;
                }
            }

        }
    }


    private void EvenNumMain(){

        if (ClockWise.isSelected()|| InOut.isSelected()) {
            ClockWiseMovement(true);
        }else if (AntiClockWise.isSelected()) {
            ClockWiseMovement(false);
        }

        int t1 = tempx;
        int t2 = tempy;

        for (int idk =0; idk <4; idk++) {

            if(m!=n){
            if (m < n && idk == 1) {
                tempy = tempy - 1;
                listOfData.clear();
                listOfData.add(stringArray[tempx][tempy]);
            } else if (m > n && idk == 1) {
                tempx = tempx - 1;
                listOfData.clear();
                listOfData.add(stringArray[tempx][tempy]);
            }}

            if(m==n){
            if (idk == 0) {
                listOfData.add(stringArray[tempx][tempy]);
            }

            if (idk == 1) {
                tempx = tempx - 1;
                listOfData.clear();
                listOfData.add(stringArray[tempx][tempy]);
            } else if (idk == 2) {
                tempy = tempy - 1;
                listOfData.clear();
                listOfData.add(stringArray[tempx][tempy]);
            } else if (idk == 3) {
                tempx = tempx - 1;
                tempy = tempy - 1;
                listOfData.clear();
                listOfData.add(stringArray[tempx][tempy]);
            }}

            movementSwitchMethod();

            if (InOut.isSelected()) {
                Collections.reverse(listOfData);
            }

            String listdata = String.join(", ", listOfData);

            if(m==n){
            if (idk == 0) {
                L0.setText(listdata);
            } else if (idk == 1) {
                L1.setText(listdata);
            } else if (idk == 2) {
                L2.setText(listdata);
            } else {
                L3.setText(listdata);
            }}

            if(m!=n){
            if ((m < n && idk == 0) || (n < m && idk == 0)) {
                L0.setText(listdata);
            } else if ((m < n && idk == 1) || (n < m && idk == 1)) {
                L1.setText(listdata);
            }}
            tempx = t1;
            tempy = t2;
        }
    }

    public void CloseLabel(javafx.scene.input.MouseEvent mouseEvent) {
        Platform.exit();
        System.exit(0);

    }
}