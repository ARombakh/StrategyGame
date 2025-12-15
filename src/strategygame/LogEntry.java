/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

/**
 *
 * @author artyom
 */
public class LogEntry {
    private String description;
    private String eventCategory;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public String getDescription() {
        return description;
    }

    public String getEventCategory() {
        return eventCategory;
    }
    
    public LogEntry(String description, String eventCategory) {
        setDescription(description);
        setEventCategory(eventCategory);
    }
    
    public String toText() {
        return getEventCategory() + "\t" + getDescription();
    }
}