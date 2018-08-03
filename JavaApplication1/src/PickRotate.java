import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.picking.behaviors.PickRotateBehavior;
import com.sun.j3d.utils.picking.behaviors.PickTranslateBehavior;
import com.sun.j3d.utils.picking.behaviors.PickZoomBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.applet.Applet;
import java.awt.GraphicsConfiguration;
import java.awt.*;
import javax.media.j3d.*;
import javax.vecmath.Vector3f;
/*
 * PickRotate.java
 *
 * Created on August 20, 2008, 11:17 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class PickRotate extends Applet{
    
    /** Creates a new instance of PickRotate */
    public PickRotate() {
        setLayout(new GridLayout());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas= new Canvas3D(config);
        add("Center",canvas);
        
        BranchGroup scene= createSceneGraph(canvas);
        
        SimpleUniverse simpleU = new SimpleUniverse(canvas);
        simpleU.getViewingPlatform().setNominalViewingTransform();
        simpleU.addBranchGraph(scene);
    }

    private BranchGroup createSceneGraph(Canvas3D canvas) {
        BranchGroup objRoot= new BranchGroup();
        Transform3D transform= new Transform3D();
        TransformGroup objRotate= new TransformGroup();
        BoundingSphere bounds = new BoundingSphere();
        
        transform.setTranslation(new Vector3f(-0.6f,0.0f,-0.6f));
        objRotate.setTransform(transform);
        objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objRotate.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
        objRoot.addChild(objRotate);
        objRotate.addChild(new ColorCube(0.4));
        
        PickRotateBehavior behavior= new PickRotateBehavior(objRoot,canvas,bounds);
        objRoot.addChild(behavior);
        PickZoomBehavior zoom= new PickZoomBehavior(objRoot,canvas,bounds);
        objRoot.addChild(zoom);
        PickTranslateBehavior trans = new PickTranslateBehavior(objRoot,canvas,bounds);
        objRoot.addChild(trans);
         
        transform.setTranslation(new Vector3f(0.6f,0.0f,-0.6f));
        objRotate = new TransformGroup(transform);
        objRotate.setTransform(transform);
        objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objRotate.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
        objRoot.addChild(objRotate);
        objRotate.addChild(new ColorCube(0.4f));
        
        objRoot.compile();
        
        return objRoot;
    }
    public static void main(String args []){
        Frame frame= new MainFrame(new PickRotate(),256,256);
    }
}
