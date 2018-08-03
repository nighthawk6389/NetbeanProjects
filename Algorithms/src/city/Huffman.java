
package city;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Comparator;



/**
 *
 * @author Mavis Beacon
 */
public class Huffman {

    public static void main(String args []){

        //Initialize Charachter array
        char [] cArray={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
                        '.',',','!','?',' ','`','(',')','-',':',';','T','L','\''};

        //Initialize to default Huffman array which will be the main array
        HuffmanNode [] array=new HuffmanNode[40];
        for(int x=0;x<array.length;x++)
            array[x]=new HuffmanNode();


        String s="Raphael The Sun sings out, in ancient mode,His note among his brother-spheres,And ends his pre-determined road, With peals of thunder for our ears.The sight of him gives Angels power,Though none can understand the way:The inconceivable work is ours,As bright as on the primal day.                                                                     Gabriel And swift, and swift, beyond conceiving,The splendour of the Earth turns round,A Paradisial light is interleaving,With night’s awesome profound.The ocean breaks with shining foam,                                                                Against the rocky cliffs deep base,And rock and ocean whirl and go,In the spheres’ swift eternal race. Michael And storms are roaring in their raceFrom sea to land, and land to sea,                                                                   Their raging forms a fierce embrace,All round, of deepest energy.The lightning’s devastations blazeAlong the thunder-crashes’ way:Yet, Lord, your messengers, shall praise  The gentle passage of your day.All Three The sight of it gives Angels powerThough none can understand the way,And all your noble work is ours,As bright as on the primal day. Mephistopheles Since, O Lord, you near me once again,To ask how all below is doing now,And usually receive me without pain,You see me too among the vile crowd.Forgive me: I can’t speak in noble style, And since I’m still reviled by this whole crew,My pathos would be sure to make you smile,If you had not renounced all laughter too.You’ll get no word of suns and worlds from me.How men torment themselves is all I see. The little god of Earth sticks to the same old way,And is as strange as on that very first day.He might appreciate life a little more: he might,If you hadn’t lent him a gleam of Heavenly light:He calls it Reason, but only uses it   To be more a beast than any beast as yet.He seems to me, saving Your Grace,Like a long-legged grasshopper: through spaceHe’s always flying: he flies and then he springs,And in the grass the same old song he sings.  If he’d just lie there in the grass it wouldn’t hurt!But he buries his nose in every piece of dirt. God Have you nothing else to name?Do you always come here to complain?Does nothing ever go right on the Earth? Mephistopheles No, Lord! I find, as always, it couldn’t be worse.I’m so involved with Man’s wretched ways,I’ve even stopped plaguing them, myself, these days. God Do you know, Faust? Mephistopheles The Doctor? God  My servant, first! Mephistopheles    In truth! He serves you in a peculiar manner. There’s no earthly food or drink at that fool’s dinner.He drives his spirit outwards, far,Half-conscious of its maddened dart:From Heaven demands the brightest star,And from the Earth, Joy’s highest art, And all the near and all the far,Fails to release his throbbing heart. God Though he’s still confused at how to serve me,I’ll soon lead him to a clearer dawning,In the green sapling, can’t the gardener see  The flowers and fruit the coming years will bring. Mephistopheles What do you wager? I might win him yet!If you give me your permission first,I’ll lead him gently on the road I set. God As long as he’s alive on Earth,  So long as that I won’t forbid it,For while man strives he errs. Mephistopheles My thanks: I’ve never willingly seen fitTo spend my time amongst the dead,I much prefer fresh cheeks instead. To corpses, I close up my house:Or it’s too like a cat with a mouse. God Well and good, you’ve said what’s needed!Divert this spirit from his source,You know how to trap him, lead him,On your downward course, And when you must, then stand, amazed:A good man, in his darkest yearning,Is still aware of virtue’s ways. Mephistopheles That’s fine! There’s hardly any waiting.  My wager’s more than safe I’m thinking.When I achieve my goal, in winning,You’ll let me triumph with a swelling heart.He’ll eat the dust, and with an art,Like the snake my mother, known for sinning. God You can appear freely too:Those like you I’ve never hated.Of all the spirits who deny, it’s you,The jester, who’s most lightly weighted.Man’s energies all too soon seek the level, He quickly desires unbroken slumber,So I gave him you to join the number,To move, and work, and play the devil.But you the genuine sons of light,Enjoy the living beauty bright! Becoming, that works and lives forever,Embrace you in love’s limits dear,And all that may as Appearance waver,Fix firmly with everlasting Idea! (Heaven closes, and the Archangels separate.) Mephistopheles (alone) I like to hear the Old Man’s words, from time to time, And take care, when I’m with him, not to spew.It’s very nice when such a great Gentleman,Chats with the devil, in ways so human, too!";
        s=s.toLowerCase();
        char [] st=s.toCharArray();
        //count amount of each char by using its character code as array number and incrementing eveyrtime we see one
        for(int x=0;x<st.length;x++){
       
            switch(st[x]){
                case 46:array[26].freq++;break;
                case 44:array[27].freq++;break;
                case 33:array[28].freq++;break;
                case 63:array[29].freq++;break;
                case 32:array[30].freq++;break;
                case 39:array[31].freq++;break;
                case 40:array[32].freq++;break;
                case 41:array[33].freq++;break;
                case 45:array[34].freq++;break;
                case 58:array[35].freq++;break;
                case 59:array[36].freq++;break;
                case 9:array[37].freq++;break;
                case 10:array[38].freq++;break;
                case 8217:array[39].freq++;break;
                default : array[st[x]-97].freq++;
            }//end switch
        }//end for

        //Setting the charachter for each huffmanNode and just outputting(no reason)
        for(int x=0;x<array.length;x++){
            array[x].c=cArray[x]; 
            System.out.print(cArray[x]+"="+array[x].freq+",");
        }
        System.out.println();

        //Sort array by using the compareTo method found in huffmanNode. Needed before Huffman code
        Arrays.sort(array);

        //Just output(no reason)
        for(int x=0;x<array.length;x++){
            System.out.print(array[x].c+"="+array[x].freq+",");
        }

        //Call to actual huffmanCode
        System.out.println();
        System.out.println("Start");
        HuffmanNode e=computeHuffman(array);
        System.out.println("End");
        System.out.println("");

        System.out.println(s.length()+" "+s.length()*8);

        int count=0;
                for(int x=0;x<st.length;x++){

            switch(st[x]){
                case 46:count+=array[26].code.length();break;
                case 44:count+=array[27].code.length();break;
                case 33:count+=array[28].code.length();break;
                case 63:count+=array[29].code.length();break;
                case 32:count+=array[30].code.length();break;
                case 39:count+=array[31].code.length();break;
                case 40:count+=array[32].code.length();break;
                case 41:count+=array[33].code.length();break;
                case 45:count+=array[34].code.length();break;
                case 58:count+=array[35].code.length();break;
                case 59:count+=array[36].code.length();break;
                case 9:count+=array[37].code.length();break;
                case 10:count+=array[38].code.length();break;
                case 8217:count+=array[39].code.length();break;
                default : count+=array[st[x]-97].code.length();
            }//end switch
        }//end for


        System.out.println(count);

        System.out.println();
        for(int x=0;x<array.length;x++)
        System.out.print(array[x].c+"="+array[x].code+" ");
        System.out.println();

    }

    public static HuffmanNode computeHuffman(HuffmanNode [] C){
        int n=C.length;

        //Initialize the COmparator that is needed for the priorityQueue to keep nodes in order
        Comparator<HuffmanNode> comparator = new HuffmanComparator();

        //initialize PriorityQueue, q, that will keep the HuffmanNodes
        PriorityQueue<HuffmanNode> q=new PriorityQueue<HuffmanNode>(C.length,comparator);

        //Adding HuffmanNodes that were given as paramteter into PrioirtyQueue q
        for(int x=0;x<C.length;x++)
            q.add(C[x]);


        //HuffmanCode
        HuffmanNode x,y;
        for(int i=0;i<n-1;i++){
            HuffmanNode z=new HuffmanNode();
            z.left=x=((HuffmanNode)(q.poll()));
            z.right=y=((HuffmanNode)(q.poll()));
            z.freq=x.freq+y.freq;
            q.add(z);

        }

        //Remove remaining node and set as top
        HuffmanNode top=q.poll();

        //Send top to outputHuffmanCode which outputs the code for each letter
        outputHuffmanCode(top,"");

        //Returning Top in case needed
        return top;

    }//end CH

    public static void outputHuffmanCode(HuffmanNode node,String code){

        //Recursive method that keeps calling the nodes of the tree until its null. The code
        //is continually added as it goes down the tree and outputted when it finds
        //a leaf
        if(node.left!=null){
            outputHuffmanCode(node.left,code+"0");
            node.code=code+"0";
        }
        if(node.right!=null){
            outputHuffmanCode(node.right,code+"1");
            node.code=code+"1";
        }

        if(node.right==null || node.left==null){
            System.out.println(node.c+"="+code);
            node.code=code;
        }

    }

}//end class Huffman


class HuffmanComparator implements Comparator<HuffmanNode>
{
    //Needed for priorityQueue

    @Override
    public int compare(HuffmanNode x, HuffmanNode y)
    {

        if (x.freq < y.freq)
        {
            return -1;
        }
        if (x.freq > y.freq)
        {
            return 1;
        }
        return 0;
    }
}//end class Huffman Comparator

class HuffmanNode implements Comparable {
    //Each Huffman Node stores a left node, right node, the charachter it represents(c)
    //and the amount of times it appears(freq)

    HuffmanNode left;
    HuffmanNode right;
    int freq;
    char c; 
    String code;

    public HuffmanNode(){
        left=right=null;
        freq=0;
    }
    public HuffmanNode(int freq){
        left=right=null;
        this.freq=freq;
    }
    public HuffmanNode(HuffmanNode left,HuffmanNode right,int freq){
        this.left=left;
        this.right=right;
        this.freq=freq;
    }
    public int compareTo(Object i2){
        if(((HuffmanNode)(i2)).freq==this.freq)
            return 0;
        else if(((HuffmanNode)(i2)).freq>this.freq)
            return -1;
        else
            return 1;

    }
}//end HuffmanNode

