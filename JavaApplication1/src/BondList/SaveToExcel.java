package BondList;

import java.io.*;
import java.util.HashMap;
//import org.dom4j.*;
/*
 * SaveToExcel.java
 *
 * Created on May 7, 2009, 5:37 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class SaveToExcel {
    private File outputFile=null;
    /** Creates a new instance of SaveToExcel */
    public SaveToExcel() {
    }
    /*
    public static void main(String[] args) {
       try{
           SaveToExcel j = new SaveToExcel();
           j.write("C:\\JavaExcel.xml");
       } catch(Exception e) {e.printStackTrace();}
    }

    
    public void write(String file) throws Exception{
        outputFile=new File(file);
        Document doc=DocumentHelper.createDocument();
        HashMap pmap= new HashMap();
        pmap.put("progid","Excel.sheet");
        doc.addProcessingInstruction("mso-application",pmap);
        doc.add(this.createElements());
        FileWriter out= new FileWriter(outputFile);
        doc.write(out);
        out.flush();
        out.close();
    }
    public void write(String file, Bond [] bond) throws Exception{
        outputFile=new File(file);
        Document doc=DocumentHelper.createDocument();
        HashMap pmap= new HashMap();
        pmap.put("progid","Excel.sheet");
        doc.addProcessingInstruction("mso-application",pmap);
        doc.add(this.createElements(bond));
        FileWriter out= new FileWriter(outputFile);
        doc.write(out);
        out.flush();
        out.close();
    }
    //This function creates the root of the SpreadsheetML and
    //populates it with necesary elements.
    private Element createElements() throws Exception {
        //Create all Elements
        Element workbook = this.createWorkbook();
        Element worksheet = this.createWorksheet("TestSheet");
        workbook.add(worksheet);
        Element table = this.createTable();
        worksheet.add(table);
        Element row1 = this.createRow("1");
        table.add(row1);
        Element cellA1 = this.createCell();
        row1.add(cellA1);
        Element dataA1 = this.createData("Number","121");
        cellA1.add(dataA1);
        Element row2 = this.createRow("2");
        table.add(row2);
        Element cellA2 = this.createCell();
        row2.add(cellA2);
        Element dataA2 = this.createData("String","121 is 11*11");
        cellA2.add(dataA2);
        return workbook;
    }
    private Element createElements(Bond [] bond) throws Exception {
        Element workbook = this.createWorkbook();
        Element worksheet = this.createWorksheet("List Of Bonds");
        workbook.add(worksheet);
        Element table = this.createTable();
        worksheet.add(table);
        
        Element [] row=new Element[bond.length];
        Element cell [][]=new Element[bond.length][9];
        
        for(int x=0;x<bond.length;x++){
        row[x] = this.createRow(new Integer(x).toString());
        table.add(row[x]);
        
        cell[x][0] = this.createCell();
        row[x].add(cell[x][0]);
        Element dataA1 = this.createData("String",bond[x].getName());
        cell[x][0].add(dataA1);
        
        cell[x][1] = this.createCell();
        row[x].add(cell[x][1]);
        Element dataA2 = this.createData("String",bond[x].getCompany());
        cell[x][1].add(dataA2);
        
        cell[x][2] = this.createCell();
        row[x].add(cell[x][2]);
        Element dataA3 = this.createData("String",bond[x].getPurchased().toString());
        cell[x][2].add(dataA3);
        
        cell[x][3] = this.createCell();
        row[x].add(cell[x][3]);
        Element dataA4 = this.createData("String",bond[x].getDue().toString());
        cell[x][3].add(dataA4);
        
        cell[x][4] = this.createCell();
        row[x].add(cell[x][4]);
        Element dataA5 = this.createData("String",bond[x].getCalled().toString());
        cell[x][4].add(dataA5);
        
        cell[x][5] = this.createCell();
        row[x].add(cell[x][5]);
        Element dataA6 = this.createData("Number",new Double(bond[x].yield).toString());
        cell[x][5].add(dataA6);
        
        cell[x][6] = this.createCell();
        row[x].add(cell[x][6]);
        Element dataA7 = this.createData("Number",new Integer(bond[x].getWAB()).toString());
        cell[x][6].add(dataA7);
        
        cell[x][7] = this.createCell();
        row[x].add(cell[x][7]);
        Element dataA8 = this.createData("Number",new Integer(bond[x].getWAS()).toString());
        cell[x][7].add(dataA8);
        
        cell[x][8] = this.createCell();
        row[x].add(cell[x][8]);
        Element dataA9 = this.createData("Number",new Integer(bond[x].getWN()).toString());
        cell[x][8].add(dataA9);
        }
        
        return workbook;
    }
    
    private Element createWorksheet(String name) {
        Element e = DocumentHelper.createElement("Worksheet");
        e.addAttribute("ss:Name", name);
        e.setQName(new QName("Worksheet",new Namespace(null,"urn:schemas-microsoft-com:office:spreadsheet")));
        return e;
    }
    
    private Element createTable() {
        return DocumentHelper.createElement("ss:Table");
    }
    
    private Element createRow(String index) {
        Element e = DocumentHelper.createElement("ss:Row");
        e.addAttribute("ss:Index",index);
        return e;
    }
    
    private Element createCell() {
        return DocumentHelper.createElement("ss:Cell");
    }
    
    private Element createData(String type, String data) {
        Element e = DocumentHelper.createElement("ss:Data");
        e.addAttribute("ss:Type",type);
        e.setText(data);
        return e;
    }
    
    private Element createWorkbook() throws Exception {
        Element root =  DocumentHelper.createElement("Workbook");
        //Set up the necessary namespaces
        root.add(new Namespace("x","urn:schemas-microsoft-com:office:excel"));
        root.add(new Namespace("ss","urn:schemas-microsoft-com:office:spreadsheet"));
        root.setQName(new QName("Workbook",new Namespace(null,"urn:schemas-microsoft-com:office:spreadsheet")));
        return root;
    }

     * 
     */
}
