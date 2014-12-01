package com.CMPE202.Team31Project;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;

/**
 * This class contains fields necessary to create a static physical sphere. And
 * also keeps track of whether it has been hit already by the player or not.
 * 
 * @author Joshua P. Hemen
 */
public class Gumball extends Enemy {
    private Sphere s;
    private Geometry g;
    private DroneControl control;
    private final float radius = 2;
    public final static int points = 25;
    private Spatial sp;
  
    public Gumball(String name, Material mat, Node target) {
        s = new Sphere(32, 32, radius);
        g = new Geometry(name, s);
        g.setMaterial(mat);
        g.setName("gumball");
        //sp=assetManager.loadModel("Models/Mothership/Alien.j3o");
        //sp.setName(name);
        
        // Greater radius than geo radius makes for much hit better detection
        control = new DroneControl(new SphereCollisionShape(radius + 0.1f), 1f, target);
        control.setImpulse(Vector3f.UNIT_XYZ.mult(0.2f));
        control.setLinearDamping(0.8f);
        g.addControl(control);
        //sp.addControl(control);
    }
    
    public Geometry getGeo() {return g;}
    
    public RigidBodyControl getRigidBodyControl() {return control;}
}
