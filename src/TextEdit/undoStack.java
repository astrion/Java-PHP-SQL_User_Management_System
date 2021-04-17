package TextEdit;

import java.util.List;

public class lineClass {
    int ID_;
    List<String> lines_;

    public lineClass(int ID, List<String> lines) {
        ID_ = ID;
        lines_ = lines;
    }

    int getID(){
        return ID_;
    }

    List<String> getFullList(){
        return lines_;
    }


    void setID(int ID){
        ID_ = ID;
    }

    void addState(String state){
        lines_.add(state);
    }


}
