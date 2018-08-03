import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Cone;
import javax.media.j3d.*;
import javax.vecmath.Vector3f;
/*
 * YoYo.java
 *
 * Created on August 12, 2008, 5:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class YoYo {
    private BranchGroup yoyoBG;
    /** Creates a new instance of YoYo */
    public YoYo() {
        yoyoBG= new BranchGroup();
        Transform3D rotate= new Transform3D();
        Transform3D translate= new Transform3D();
        Appearance yoyoAppear= new Appearance();
        
        rotate.rotZ(Math.PI/2.0d);
        TransformGroup yoyoTGR = new TransformGroup(rotate);
        
        translate.set(new Vector3f(0.1f,0.0f,0.0f));
        TransformGroup yoyoTGT= new TransformGroup(translate);
        
        Cylinder cone1= new Cylinder(0.6f,0.2f);
        cone1.setAppearance(yoyoAppear);
        
        yoyoBG.addChild(yoyoTGR);
        yoyoTGR.addChild(yoyoTGT);
        yoyoTGT.addChild(cone1);
        
         rotate.rotZ(-Math.PI/2.0d);
        TransformGroup yoyoTGR2 = new TransformGroup(rotate);
        
        translate.set(new Vector3f(-0.1f,0.0f,0.0f));
        TransformGroup yoyoTGT2= new TransformGroup(translate);
        
        Cone cone2= new Cone(0.6f,0.2f);   
        cone2.setAppearance(yoyoAppear);
        
        yoyoBG.addChild(yoyoTGR2);
        yoyoTGR2.addChild(yoyoTGT2);
        yoyoTGT2.addChild(cone2);
        
        yoyoBG.compile();
    }
    public BranchGroup getBG(){
        return yoyoBG;
    }
}
