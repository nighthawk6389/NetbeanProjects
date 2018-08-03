 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package job;

import java.util.*;

/**
 *
 * @author Mavis Beacon
 */
public class Baseball {

    Scenario mainScenario;

    public Baseball(){
        mainScenario=Scenario.createRandomScenario();
        //mainScenario.setDecription("Main ");
    }

    public static void  main(String args []){
        Baseball b=new Baseball();
        System.out.println(b.toString());
        b.getAllPossibleScenariosForNextPitch();
    }

    public Set<Scenario> getAllPossibleScenariosForNextPitch(){
        Set<Scenario> possibles=new LinkedHashSet<Scenario>();
        ball(possibles);
        strike(possibles);
        out_regular(possibles);
        hit_regular(possibles);
        hit_flyball(possibles);
        hit_groundball(possibles);
        //hit_foulball(possibles);
        //balk(possibles);
        //bunt(possibles);
        //hit_by_pitch(possibles);

        for(Scenario s:possibles){
            System.out.println(s);
        }
        return possibles;
    }

    private void ball(Set<Scenario> possibles){

        Scenario s;
        Set<Scenario> toAdd;

        //regular ball
        s=this.mainScenario.copy();
        toAdd=s.setBallsStrikes(true);
        for(Scenario temp:toAdd)
            possibles.add(temp);

    }

    private void strike(Set<Scenario> possibles){
         Scenario s;
        Set<Scenario> toAdd;

        //strike
        s=this.mainScenario.copy();
        toAdd=s.setBallsStrikes(false);
        for(Scenario temp:toAdd)
            possibles.add(temp);

    }
    
    private void out_regular(Set<Scenario> possibles){
        Scenario s=this.mainScenario.copy();
        s.anotherOut();
        possibles.add(s);  
    }
    
    private void hit_regular(Set<Scenario> possibles){
        Scenario s;
        Set<Scenario> toAdd;
        
        s=this.mainScenario.copy();
        toAdd=s.setHit(Scenario.SINGLE);
        for(Scenario temp:toAdd)
            possibles.add(temp);
        s=this.mainScenario.copy();

        toAdd=s.setHit(Scenario.DOUBLE);
        for(Scenario temp:toAdd)
            possibles.add(temp);

        s=this.mainScenario.copy();
        toAdd=s.setHit(Scenario.TRIPLE);
        for(Scenario temp:toAdd)
            possibles.add(temp);

        s=this.mainScenario.copy();
        toAdd=s.setHit(Scenario.HOME_RUN);
        for(Scenario temp:toAdd)
            possibles.add(temp);
    }

    private void hit_groundball(Set<Scenario> possibles){
        Scenario s;
        Set<Scenario> toAdd;

        //groundout
        s=this.mainScenario.copy();
        toAdd=s.setGroundOut();
        for(Scenario temp:toAdd)
            possibles.add(temp);

        //double play
        s=this.mainScenario.copy();
        toAdd=s.setDoublePlay();
        for(Scenario temp:toAdd)
            possibles.add(temp);

        //triple play
        s=this.mainScenario.copy();
        toAdd=s.setTriplePlay();
        for(Scenario temp:toAdd)
            possibles.add(temp);
    }

    private void hit_flyball(Set<Scenario> possibles){
        Scenario s;
        Set<Scenario> toAdd;

        s=this.mainScenario.copy();
        toAdd=s.setSacrifice();
        for(Scenario temp:toAdd)
            possibles.add(temp);
    }

    public String toString(){
        return mainScenario.toString();
    }

}

class Scenario {
    private int [] men_on_base;
    private int outs;
    private int strikes;
    private int balls;
    private int [] scoreTeam;
    private int inning;
    private int teamAtBat;
    private String description="";
    static final int HOME_RUN=4;
    static final int TRIPLE=3;
    static final int DOUBLE=2;
    static final int SINGLE=1;
    static int counter=1;

    public Scenario(){
        men_on_base=new int[3];
        for(int x=0;x<men_on_base.length;x++)
            men_on_base[x]=0;
        scoreTeam=new int[2];
        for(int x=0;x<scoreTeam.length;x++)
            scoreTeam[x]=0;
        outs=strikes=balls=0;
        inning=1;
        teamAtBat=0;
    }

    public Scenario(int [] men_on_base,int outs,int strikes,int balls,int [] scoreTeam,
            int inning,int teamAtBat,String description){

        this.men_on_base=new int[men_on_base.length];
        this.men_on_base[0]=men_on_base[0];
        this.men_on_base[1]=men_on_base[1];
        this.men_on_base[2]=men_on_base[2];
        this.outs=outs;
        this.strikes=strikes;
        this.balls=balls;
        this.scoreTeam=new int[scoreTeam.length];
        this.scoreTeam[0]=scoreTeam[0];
        this.scoreTeam[1]=scoreTeam[1];
        this.inning=inning;
        this.teamAtBat=teamAtBat;
        this.description=description;
    }

    public Scenario copy(){
        Scenario s=new Scenario(this.men_on_base,this.outs,this.strikes,this.balls,
                this.scoreTeam,this.inning,this.teamAtBat,this.description);
        return s;
    }

    public static Scenario createRandomScenario(){
        Scenario s=new Scenario();
        s.populateRandomScenario();
        return s;
    }

    public void populateRandomScenario(){
        for(int x=0;x<men_on_base.length;x++){
            men_on_base[x]=(int)(Math.random()*2-.1);
        }
        for(int x=0;x<scoreTeam.length;x++){
            scoreTeam[x]=(int)(Math.random()*5-.1);
        }
        outs=(int)(Math.random()*3-.1);
        strikes=(int)(Math.random()*3-.1);
        balls=(int)(Math.random()*4-.1);
        inning=(int)(Math.random()*10-.1);
        teamAtBat=(int)(Math.random()*2-.1);
    }

    public Set<Scenario> setBallsStrikes(boolean ball){
        Set<Scenario> possibles=new LinkedHashSet<Scenario>();
        Scenario s=this.copy();
        Scenario s1;

        if(ball){
            s.balls++;
            int amount=s.balls;
            s.description+="Ball "+counter++;
            if(amount==4){
                s.setWalk();
                possibles.add(s);
                return possibles;
            }//end if
            possibles.add(s.copy());
        }//end if
        else if(!ball){
            s.strikes++;
            int amount=s.strikes;
            s.description+="Strike ";
            if(amount==3){
                s.setStrikeout();
            }
            possibles.add(s.copy());
        }

        s1=s.copy();
        if(s1.getTotalMenOnBase()>0){
            s1.advanceRunners();
            s1.description+="Wild Pitch ";
            possibles.add(s1.copy());
        }//end if

        System.out.println("s: "+s.toString());
        s1=s.copy();
        if(s.getTotalMenOnBase()>0 ){
            s.steal(true);
            possibles.add(s.copy());
            s1.steal(false);
            possibles.add(s1.copy());
        }

        return possibles;
    }//end setballs

    public Set<Scenario> setSacrifice(){
        Set<Scenario> possibles=new LinkedHashSet<Scenario>();
        Scenario s=this.copy();
        boolean stillUp=s.anotherOut();
        if(!stillUp){
            s.setDecription("Fly Out");
            possibles.add(s);
            return possibles;
        }
        s.advanceRunners();
        s.description+="Sacrifice";
        possibles.add(s);
        return possibles;
    }

    public void setWalk(){
        this.balls=this.strikes=0;

        if(this.men_on_base[1]+this.men_on_base[0]==2){
            this.men_on_base[1]=1;
            this.men_on_base[2]++;
        }
        if(this.men_on_base[2]==2){
            this.men_on_base[2]=1;
            this.scoreTeam[teamAtBat]++;
        }
        this.men_on_base[0]=1;
        this.description+="Walk issued ";
    }

    public void setStrikeout(){
        this.balls=this.strikes=0;
        this.anotherOut();
        this.description+="Strikeout ";
    }

    public Set<Scenario> setGroundOut(){
        Set<Scenario> possibles=new LinkedHashSet<Scenario>();
        Scenario s=this.copy();

        //throw out batter
        s.anotherOut();
        s.advanceRunners();
        s.description+="Batter grounded out";
        possibles.add(s);

        //throw out runners on base/fielders choice
        s=this.copy();
        for(int x=0;x<this.getTotalMenOnBase();x++){
            s.throwOutRunner(x);
            s.advanceRunners();
            s.men_on_base[0]=1;
            s.description+="Fielders Choice "+x;
            possibles.add(s);
            s=this.copy();
        }

        return possibles;
    }

    public Set<Scenario> setDoublePlay(){
        Set<Scenario> possibles=new LinkedHashSet<Scenario>();
        Scenario s=this.copy();

        for(int x=-1;x<this.getTotalMenOnBase();x++){
            for(int y=x+1;y<this.getTotalMenOnBase();y++){
                System.out.println("DP before: "+s.toString());
                s.throwOutRunner(x);
                if(s.outs!=0){//inning not over
                    s.throwOutRunner(x);
                    s.advanceRunners();
                    if(x!=-1)
                        s.men_on_base[0]=1;
                s.description+="Double Play "+x+" "+y;
                possibles.add(s);
                }
                s=this.copy();
            }
            s=this.copy();
        }

        return possibles;
    }
    
    public Set<Scenario> setTriplePlay(){
        Set<Scenario> possibles=new LinkedHashSet<Scenario>();
        Scenario s=this.copy();

        for(int x=-1;x<this.getTotalMenOnBase();x++){
            for(int y=x+1;y<this.getTotalMenOnBase();y++){
                for(int z=y+2;z<this.getTotalMenOnBase();z++){
                    s.throwOutRunner(x);
                    System.out.println("TP: "+x+" being thrown out");
                    if(s.outs!=0){//inning not over
                        s.throwOutRunner(y);
                        System.out.println("TP: "+y+" being thrown out");
                        if(x!=-1)
                            s.men_on_base[0]=1;
                    }
                    if(s.outs!=0){//inning not over
                        s.throwOutRunner(y);
                        System.out.println("TP: "+z+" being thrown out");
                        s.advanceRunners();
                        System.out.println("After TP: "+s.toString());
                    }
                    s.description+="Triple Play "+x+" "+y+" "+z;
                    possibles.add(s);
                    s=this.copy();
                }
                s=this.copy();
            }
            s=this.copy();
        }

        return possibles;
    }

    public void throwOutRunner(int runner){
        int x=0;

        if(runner==-1){
            this.anotherOut();//throw out batter
            return;
        }

        if(runner==x && this.men_on_base[0]==1){
            this.anotherOut();
            this.men_on_base[0]=0;
            return;
        }
        else if(this.men_on_base[0]==1){
            x++;
        }

        if(runner==x && this.men_on_base[1]==1){
            this.anotherOut();
            this.men_on_base[1]=0;
            return;
        }
        else if(this.men_on_base[1]==1){
            x++;
        }

        if(runner==x && this.men_on_base[2]==1){
            this.anotherOut();
            this.men_on_base[2]=0;
            return;
        }
        else if(this.men_on_base[2]==1){
            x++;
        }


    }

    public void advanceRunners(){
        scoreTeam[teamAtBat]+=this.men_on_base[2];
        this.men_on_base[2]=this.men_on_base[1];
        this.men_on_base[1]=this.men_on_base[0];
        this.men_on_base[0]=0;
        this.description+=" Runners advanced ";

    }

    public void steal(boolean safe){
        this.advanceRunners();
        this.description+="Runner attempts to steal: ";
        if(!safe && this.men_on_base[1]==1){
            this.men_on_base[1]=0;
            this.anotherOut();
            this.description+="Thrown out ";
        }
        else if(!safe && this.men_on_base[2]==1){
            this.men_on_base[2]=0;
            this.anotherOut();
            this.description+="Thrown out ";
        }
        else
            this.description+="Safe ";
    }

    public boolean anotherOut(){
        this.outs++;
        this.description+="Out ";
        if(this.outs==3){
            this.outs=0;
            this.teamAtBat=(this.teamAtBat+1)%2;
            this.description+="Inning Over ";
            return false;
        }
        return true;
    }

    public void setMenOnBase(int first,int second,int third){
        this.men_on_base[0]=first;
        this.men_on_base[1]=second;
        this.men_on_base[2]=third;
    }
    private int getTotalMenOnBase(){
        return this.men_on_base[0]+this.men_on_base[1]+this.men_on_base[2];
    }
    public Set<Scenario> setHit(int typeOfHit){
        Set<Scenario> possibles=new LinkedHashSet<Scenario>();
        Scenario s=this.copy();
        if(typeOfHit==Scenario.HOME_RUN){
            s.scoreTeam[teamAtBat]+=this.getTotalMenOnBase()+1;
            s.setMenOnBase(0,0,0);
            s.setDecription("HomeRun ");
            possibles.add(s);

        }
        s=this.copy();
        if(typeOfHit==Scenario.TRIPLE){
            s.scoreTeam[teamAtBat]+=this.getTotalMenOnBase();
            s.setMenOnBase(0,0,1);
            s.setDecription("Triple ");
            possibles.add(s);
        }
        s=this.copy();
        if(typeOfHit==Scenario.DOUBLE){
            s.scoreTeam[teamAtBat]+=this.getTotalMenOnBase();
            s.setMenOnBase(0,1,0);
            s.setDecription("Double ");
            possibles.add(s);
        }
        s=this.copy();
        if(typeOfHit==Scenario.DOUBLE ){
            s.scoreTeam[teamAtBat]+=this.men_on_base[1]+this.men_on_base[2];
            s.setMenOnBase(0,1,this.men_on_base[0]);
            s.setDecription("Double ");
            possibles.add(s);
        }
        s=this.copy();
        if(typeOfHit==Scenario.SINGLE ){
            s.scoreTeam[teamAtBat]+=this.men_on_base[1]+this.men_on_base[2];
            s.setMenOnBase(1,this.men_on_base[0],0);
            s.setDecription("Single ");
            possibles.add(s);
        }
        s=this.copy();
        if(typeOfHit==Scenario.SINGLE ){
            s.scoreTeam[teamAtBat]+=this.men_on_base[2];
            s.setMenOnBase(1,this.men_on_base[0],this.men_on_base[1]);
            s.setDecription("Single ");
            possibles.add(s);
        }

        return possibles;
    }//end setHit

    public void setDecription(String description){
        this.description+=description;
    }

        @Override
    public String toString(){
        return "Strikes: "+strikes+" Balls: "+balls+" Outs: "+outs+" MenOnBase: "+
                men_on_base[0]+"/"+men_on_base[1]+"/"+men_on_base[2]+" Score: "+
                scoreTeam[0]+"/"+scoreTeam[1]+" Inning: "+inning+" TeamAtBar: "+teamAtBat+" Description: "+description+"\n";
    }

    @Override
    public boolean equals(Object o){
        if(this.hashCode()==o.hashCode())
            return true;
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Arrays.hashCode(this.men_on_base);
        hash = 79 * hash + this.outs;
        hash = 79 * hash + this.strikes;
        hash = 79 * hash + this.balls;
        hash = 79 * hash + Arrays.hashCode(this.scoreTeam);
        hash = 79 * hash + this.inning;
        hash = 79 * hash + this.teamAtBat;
        hash = 79 * hash + this.description.hashCode();
        return hash;
    }
}
