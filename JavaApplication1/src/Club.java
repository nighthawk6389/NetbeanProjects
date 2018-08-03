
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Store details of club memberships.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Club
{
    // Define any necessary fields here ...

    ArrayList<Membership> members;

    /**
     * Constructor for objects of class Club
     */
    public Club()
    {
        members=new ArrayList<Membership>();
    }

    /**
     * Add a new member to the club's list of members.
     * @param member The member object to be added.
     */
    public void join(Membership member)
    {
        members.add(member);
    }

    /**
     * @return The number of members (Membership objects) in
     *         the club.
     */
    public int numberOfMembers()
    {
        return members.size();
    }

    public int joinedInMonth(int month){
        if(month<1 || month>12){
            System.out.println("Error: outside of range");
            return 0;
        }

        int amount=0;
        Iterator<Membership> it=members.iterator();
        while(it.hasNext()){
            Membership mem=it.next();
            if(mem.getMonth()==month){
                amount=amount+1;
            }
        }
        return amount;
    }

    public ArrayList<Membership> purge(int month, int year){
        if(month<1 || month>12){
            System.out.println("Error: outside of range");
            return null;
        }

        ArrayList<Membership> purged=new ArrayList<Membership>();
        Iterator<Membership> it=members.iterator();
        while(it.hasNext()){
            Membership mem=it.next();
            if(mem.getMonth()==month && mem.getYear()==year){
                purged.add(mem);
                it.remove();
            }
        }

        return purged;
    }


    /**
 * Store details of a club membership.
 *
 * @author David J. Barnes and Michael Kolling
 * @version 2008.03.30
 */
public class Membership
{
    // The name of the member.
    private String name;
    // The month in which the membership was taken out.
    private int month;
    // The year in which the membership was taken out.
    private int year;

    /**
     * Constructor for objects of class Membership.
     * @param name The name of the member.
     * @param month The month in which they joined. (1 ... 12)
     * @param year The year in which they joined.
     */
    public Membership(String name, int month, int year)
        throws IllegalArgumentException
    {
        if(month < 1 || month > 12) {
            throw new IllegalArgumentException(
                "Month " + month + " out of range. Must be in the range 1 ... 12");
        }
        this.name = name;
        this.month = month;
        this.year = year;
    }

    /**
     * @return The member's name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return The month in which the member joined.
     *         A value in the range 1 ... 12
     */
    public int getMonth()
    {
        return month;
    }

    /**
     * @return The year in which the member joined.
     */
    public int getYear()
    {
        return year;
    }

    /**
     * @return A string representation of this membership.
     */
    public String toString()
    {
        return "Name: " + name +
               " joined in month " +
               month + " of " + year;
    }
}

}
