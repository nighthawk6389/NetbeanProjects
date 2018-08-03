/*
 *      @(#)TexturedText3DApp.java 1.1 00/09/25 02:09
 *
 * Copyright (c) 1996-2000 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to Sun.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 */

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.*;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import com.sun.j3d.utils.applet.MainFrame; 
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.image.*;
import javax.media.j3d.*;
import javax.vecmath.*;

/*   TexturedText3DApp renders a single, rotating Text3D Object.
 *   The Text3D object has material properties specified
 *   along with lights so that the Text3D object is
 *   shaded.
 */

public class TexturedText3DApp extends Applet {

     public BranchGroup createSceneGraph() {
         // Create the root of the branch graph
         BranchGroup objRoot = new BranchGroup();

         Transform3D t3D = new Transform3D();
         t3D.setTranslation(new Vector3f(0.0f, 0.0f, -3.0f));
         TransformGroup objMove = new TransformGroup(t3D);
         objRoot.addChild(objMove);
 
         // Create the transform group node and initialize it to the 
         // identity. Add it to the root of the subgraph.
         TransformGroup objSpin = new TransformGroup();
         objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
         objMove.addChild(objSpin);

        Appearance textAppear = new Appearance();

        PolygonAttributes polyAttrib = new PolygonAttributes();
        polyAttrib.setCullFace(PolygonAttributes.CULL_NONE);
        textAppear.setPolygonAttributes(polyAttrib);

        TexCoordGeneration tcg = new TexCoordGeneration(TexCoordGeneration.OBJECT_LINEAR,
                                                        TexCoordGeneration.TEXTURE_COORDINATE_2);
        textAppear.setTexCoordGeneration(tcg);

        String filename = "brick.gif";
        System.out.println("attempt to load texture from file: "+filename);
        TextureLoader loader = new TextureLoader(filename, this);
        ImageComponent2D image = loader.getImage();

        if(image == null) {
            System.out.println("load failed for texture: "+filename);
        }

        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA,
                                      image.getWidth(), image.getHeight());
        texture.setImage(0, image);
        texture.setEnable(true);

        textAppear.setTexture(texture);

 
         // Create a simple shape leaf node, add it to the scene graph.
         Font3D font3D = new Font3D(new Font("Helvetica", Font.PLAIN, 1),
                                    new FontExtrusion());
         Text3D textGeom = new Text3D(font3D, new String("Textures"));
         textGeom.setAlignment(Text3D.ALIGN_CENTER);
         Shape3D textShape = new Shape3D();
         textShape.setGeometry(textGeom);
         textShape.setAppearance(textAppear);
         objSpin.addChild(textShape);

         // Create a new Behavior object that will perform the desired
         // operation on the specified transform object and add it into
         // the scene graph.
         Alpha rotationAlpha = new Alpha(-1, 10000);
  
         RotationInterpolator rotator =
                 new RotationInterpolator(rotationAlpha, objSpin);
 
         // a bounding sphere specifies a region a behavior is active
         // create a sphere centered at the origin with radius of 100
         BoundingSphere bounds = new BoundingSphere();
         rotator.setSchedulingBounds(bounds);
         objSpin.addChild(rotator);

         return objRoot;
     } // end of CreateSceneGraph method


     public TexturedText3DApp() {
        setLayout(new BorderLayout());
        GraphicsConfiguration config =
           SimpleUniverse.getPreferredConfiguration();

        Canvas3D canvas3D = new Canvas3D(config);
        add("Center", canvas3D);
        canvas3D.setStereoEnable(false);

        NewTextureLoader.setImageObserver(canvas3D);

        BranchGroup scene = createSceneGraph();

        // SimpleUniverse is a Convenience Utility class
        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

	// This will move the ViewPlatform back a bit so the
	// objects in the scene can be viewed.
        simpleU.getViewingPlatform().setNominalViewingTransform();

        simpleU.addBranchGraph(scene);
    } // end of TexturedText3DApp (constructor)

    //  The following allows this to be run as an application
    //  as well as an applet

    public static void main(String[] args) {
        System.out.println("TexturedText3DApp.java - a demonstration of Text3D in Java 3D");
        System.out.println("The scene is of a rotating textured Text3D object.");
        System.out.println("The Java 3D Tutorial is available on the web.");
        Frame frame = new MainFrame(new TexturedText3DApp(), 256, 256);
    } // end of main (method of TexturedText3DApp)

} // end of class TexturedText3DApp
