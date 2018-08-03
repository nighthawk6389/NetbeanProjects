import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.*;
import javax.vecmath.Vector3f;
/*
 * HelloJava3D.java
 *
 * Created on July 20, 2008, 11:38 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class HelloJava3D extends Applet{
    
    /** Creates a new instance of HelloJava3D */
    public HelloJava3D(){  
        setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas3d= new Canvas3D(config);
        add("Center",canvas3d);
        
        YoYo yoyo = new YoYo(); 
        BranchGroup scene=createSceneGraph();
        scene.compile();
        
        SimpleUniverse simpleU = new SimpleUniverse(canvas3d);
        simpleU.getViewingPlatform().setNominalViewingTransform();
        
        simpleU.addBranchGraph(scene);
        
    }

    private BranchGroup createSceneGraph() {
        BranchGroup objroot = new BranchGroup();
        
        Transform3D rotate= new Transform3D();
        Transform3D tempRotate = new Transform3D();
        Transform3D translate = new Transform3D();
        Transform3D translate0=new Transform3D();
        
        rotate.rotX(Math.PI/4.0d);
        tempRotate.rotY(Math.PI/5.0d);
        rotate.mul(tempRotate);
        TransformGroup objRotate= new TransformGroup(rotate);
        tempRotate.rotX(Math.PI/4.0d);
        rotate.rotY(Math.PI/5.0d);
        rotate.mul(tempRotate);
        TransformGroup objRotateO= new TransformGroup(rotate);
        
        TransformGroup objSpin= new TransformGroup();
        objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        TransformGroup objSpinO = new TransformGroup();
        objSpinO.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        
        translate.setTranslation(new Vector3f(0.6f,0.0f,-0.6f));
        TransformGroup objTrans = new TransformGroup();
        objTrans.setTransform(translate);
        TransformGroup objTrans0= new TransformGroup();
        translate0.setTranslation(new Vector3f(-0.6f,0.0f,-0.6f));
        objTrans0.setTransform(translate0);
         
        objroot.addChild(objTrans);
        objroot.addChild(objTrans0);
        objTrans.addChild(objRotate);
        objRotate.addChild(objSpin);
        objTrans0.addChild(objRotateO);
        objRotateO.addChild(objSpinO);
        objSpin.addChild(new ColorCube(0.4f));
        objSpinO.addChild((new ColorCube(0.4f)));
        
        Alpha rotationAlpha = new Alpha(-1,4000);
        Transform3D yAxis = new Transform3D();
        RotationInterpolator rotator =new RotationInterpolator(rotationAlpha, objSpin, yAxis, 0.0f, (float)Math.PI*2.0f);
        RotationInterpolator rotatorO =new RotationInterpolator(rotationAlpha, objSpinO, yAxis,0.0f,(float)Math.PI*2.0f);
        
        BoundingSphere bounds = new BoundingSphere();
        rotator.setSchedulingBounds(bounds);
        rotatorO.setSchedulingBounds(bounds);
        
        objSpin.addChild(rotator);
        objSpinO.addChild(rotatorO);
        
        return objroot;
    }
    
    public static void main(String args []){
        Frame frame = new MainFrame(new HelloJava3D(),256,256);
    }
    
}