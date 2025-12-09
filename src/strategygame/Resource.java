/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

/**
 *
 * @author artyom
 */
public class Resource extends FieldObject {
    public static enum ResourceType {
        GOLD,
        LUMBER,
        STONE
    }
    
    private ResourceType resource;

    public ResourceType getResource() {
        return resource;
    }

    public void setResource(ResourceType resource) {
        this.resource = resource;
    }
    
    public Resource(int life, ResourceType resource) {
        super(life);
        
        setResource(resource);
    }
}
