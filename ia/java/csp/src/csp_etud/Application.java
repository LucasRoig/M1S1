/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csp_etud;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lroig
 */
public class Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String fileName = "coloration";
        Network myNetwork = null;
        BufferedReader reader = null;
        try{
            System.out.println("Chargement du fichier " + new java.io.File(".").getCanonicalPath() + "/" + fileName);
            reader = new BufferedReader(new FileReader(fileName));
            myNetwork = new Network(reader);
        }
        catch(Exception ex){
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if(myNetwork != null){
            CSP csp = new CSP(myNetwork);
            System.out.println(csp.searchSolution());
        }
    }
    
}
