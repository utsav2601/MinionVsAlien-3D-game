package com.CMPE202.Team31Project;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author melombuki
 */
public class AlienShip extends Enemy {
    private final static int MAX_ALIENS = 10;
    private Node target;
    private Spatial s;
    private DroneControl control;
    private GhostControl gControl;
    private final float innerRadius = 40;
    private final float outterRadius = 150;
    private long lastSpawnTime = 0;
    private int health = 100;
    public static final int hitPoint = 5;
    public static final int killPoint = 75;
    
    private Set<Alien> alien;
    private Set<Gumball> gball;
  
    
    public AlienShip(String name, Material mat, Node target, AssetManager assetManager) {
        s =  assetManager.loadModel("Models/Mothership/drone.j3o");
        s.setLocalTranslation(0f, -1f, 0f);
        s.setLocalScale(0.3f);
        s.setName(name);
        
        // Store handle to the target to pass to alien
        this.target = target;
        
        // Set up the minion queue
        alien = new HashSet<Alien>();
        gball = new HashSet<Gumball>();
        
        // Creates a rough approximation of the shape and makes it float at Y = 35 
        control = new DroneControl(new SphereCollisionShape(innerRadius), 3f, target, 15f);
        control.setLinearDamping(0.7f);
        control.setAngularDamping(1.0f);
        control.setFriction(0f);
        
        // Set up the ghost control as a proximity detector
        gControl = new GhostControl(new SphereCollisionShape(outterRadius));
        
        s.addControl(control);
        s.addControl(gControl);
    }
    
    public Spatial getSpatial() {return s;}
    
    public RigidBodyControl getRigidBodyControl() {return control;}
    
    public GhostControl getGhostControl() {return gControl;}
    
    public Alien createAlien(Material mat, Vector3f playerLocation, AssetManager assetManager) {
        if(System.currentTimeMillis() - lastSpawnTime > 1000 &&
                getAlien().size() <= MAX_ALIENS) {
            lastSpawnTime = System.currentTimeMillis();
            Alien m = new Alien("Alien", mat, target, assetManager);
            getAlien().add(m);
            return m;
        }
        return null; // no minion was added to the scene
    }
    
    public Gumball createGumball(Material mat, Vector3f playerLocation) {
        if(System.currentTimeMillis() - lastSpawnTime > 1000 &&
                getAlien().size() <= MAX_ALIENS) {
            lastSpawnTime = System.currentTimeMillis();
            Gumball m = new Gumball("Gumball", mat, target);
            getGumball().add(m);
            return m;
        }
        return null; // no minion was added to the scene
    }

    public int gethealth() {return health;}
    
    @Override
    public void hit() {
        super.hit();
        health -= 10;
    }
    
    @Override
    public void unhit() {
        super.unhit();
        this.health = 100;
    }

    /**
     * @return the alien
     */
    public Set<Alien> getAlien() {
        return alien;
    }
    
    public Set<Gumball> getGumball() {
        return gball;
    }
    
    public boolean removeAlien(Spatial m) {
        for(Alien md : alien) {
            if(md.getGeo().hashCode() == m.hashCode()) {
                if(alien.remove(md)) {
                    return false;
                }
            }
        }
        return false;
    }
    
    public boolean removeGumball(Spatial m) {
        for(Gumball md : gball) {
            if(md.getGeo().hashCode() == m.hashCode()) {
                if(gball.remove(md)) {
                    return false;
                }
            }
        }
        return false;
    }
    
    public void clearAlien() {
        alien.clear();
    }
    
    public void clearGumball() {
        gball.clear();
    }
}
