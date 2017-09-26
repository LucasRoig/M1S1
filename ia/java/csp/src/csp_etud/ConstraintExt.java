package csp_etud;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Pour manipuler des contraintes en extension
 *
 */
public class ConstraintExt extends Constraint {

    private ArrayList<ArrayList<Object>> tuples;	// ensemble des tuples de la contrainte

    /**
     * Construit une contrainte d'extension vide à partir d'une liste de
     * variables
     *
     * @param var la liste de variables
     */
    public ConstraintExt(ArrayList<String> var) {
        super(var);
        tuples = new ArrayList<ArrayList<Object>>();
    }

    /**
     * Construit une contrainte d'extension vide à partir d'une liste de
     * variables et d'un nom
     *
     * @param var la liste de variables
     * @param name son nom
     */
    public ConstraintExt(ArrayList<String> var, String name) {
        super(var, name);
        tuples = new ArrayList<ArrayList<Object>>();
    }

    /**
     * Construit une contrainte en extension à partir d'une représentation
     * textuelle de la contrainte. La liste de variables est donnée sous la
     * forme : Var1;Var2;... Puis le nombre de tuples est indiqué et enfin
     * chaque tupe est donné sous la forme d'une suite de valeurs "String" :
     * Val1;Val2;... Aucune vérification n'est prévue si la syntaxe n'est pas
     * respectée !!
     *
     * @param in le buffer de lecture de la représentation textuelle de la
     * contrainte
     * @throws Exception en cas d'erreur de format
     */
    public ConstraintExt(BufferedReader in) throws Exception {
        super(in);
        tuples = new ArrayList<ArrayList<Object>>();
        int nbTuples = Integer.parseInt(in.readLine());		// nombre de tuples de valeurs
        for (int j = 1; j <= nbTuples; j++) {
            ArrayList<Object> tuple = new ArrayList<Object>();
            for (String v : in.readLine().split(";")) {
                tuple.add(v);	// Val1;Val2;...;Val(arity)
            }
            if (tuple.size() != varList.size()) {
                System.err.println("Le tuple " + tuple + " n'a pas l'arité " + varList.size() + " de la contrainte " + name);
            } else if (!tuples.add(tuple)) {
                System.err.println("Le tuple " + tuple + " est déjà présent dans la contrainte " + name);
            }
        }
    }

    /**
     * Ajoute un tuple de valeur à la contrainte
     *
     * @param valTuple le tuple à ajouter
     */
    public void addTuple(ArrayList<Object> valTuple) {
        if (valTuple.size() != varList.size()) {
            System.err.println("Le tuple " + valTuple + " n'a pas l'arité " + varList.size() + " de la contrainte " + name);
        } else if (!tuples.add(valTuple)) {
            System.err.println("Le tuple " + valTuple + " est déjà présent dans la contrainte " + name);
        }
    }


    /* (non-Javadoc)
	 * @see Constraint#violation(Assignment)
     */
    public boolean violation(Assignment a) {
        List<Object> varValues = new ArrayList<>();
        for(String varName : this.varList){
            if(a.containsKey(varName)){
                varValues.add(a.get(varName));
            }else{
                //Certaines variables de la portee de la contrainte n'ont pas d'assignation
                //Il n'y a donc pas de violation
                return false;
            }
        }
        return !this.tuples.contains(varValues);
    }

    /* (non-Javadoc)
	 * @see Constraint#toString()
     */
    public String toString() {
        return "\n\t Ext " + super.toString() + " : " + tuples;
    }

}
