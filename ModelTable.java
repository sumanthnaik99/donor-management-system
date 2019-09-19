/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temple_app;

/**
 *
 * @author hp
 */
public class ModelTable {
    
    String id, name, date, amount, mode;

    public ModelTable(String id, String name, String date, String amount, String mode) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.amount = amount;
        this.mode = mode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
    
    
    
}
