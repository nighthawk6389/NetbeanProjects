import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.awt.*;
import javax.media.j3d.*;
import java.applet.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
/*
 * Text.java
 *
 * Created on August 29, 2008, 11:56 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class Text extends Applet{
    
    /** Creates a new instance of Text */
    public Text() {
        setLayout(new BorderLayout());
        GraphicsConfiguration config= SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas= new Canvas3D(config);
        add("Center",canvas);
        
        BranchGroup bg= createSceneGraph();
        bg.compile();
        
        SimpleUniverse simpleU = new SimpleUniverse(canvas);
        simpleU.getViewingPlatform().setNominalViewingTransform();
        simpleU.addBranchGraph(bg);
    }

    private BranchGroup createSceneGraph() {
        BranchGroup bg = new BranchGroup();
        
        Font3D font = new Font3D(new Font("SansSeriff",Font.ITALIC,1), new FontExtrusion());
        Text3D text= new Text3D(font,new String("Kicker"), new Point3f(-2.0f,0.0f,-3.0f));
        Shape3D shape = new Shape3D(text);
        bg.addChild(shape);
         
        BoundingSphere bounds= new BoundingSphere();
        
        DirectionalLight light= new DirectionalLight();
        light.setInfluencingBounds(bounds);
        light.setDirection(new Vector3f(0.0f, 0.0f, -1.0f));
        light.setColor(new Color3f(1.0f, 0.0f, 1.0f));
        bg.addChild(light);
        
        AmbientLight lightA = new AmbientLight();
        lightA.setInfluencingBounds(bounds);
        bg.addChild(lightA);
        return bg;
    }
    public static void main(String args []){
        Frame frame= new MainFrame(new Text(),500,500);
    }
}
