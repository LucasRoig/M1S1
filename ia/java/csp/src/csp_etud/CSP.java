package csp_etud;

import java.util.ArrayList;
import java.util.List;

/**
 * Solveur : permet de résoudre un problème de contrainte par Backtrack : Calcul
 * d'une solution, Calcul de toutes les solutions
 *
 */
public class CSP {

    private Network network;			// le réseau à résoudre
    private ArrayList<Assignment> solutions; 	// les solutions du réseau (résultat de searchAllSolutions)
    private Assignment assignment;		// l'assignation courante (résultat de searchSolution)
    int cptr;					// le compteur de noeuds explorés

    /**
     * Crée un problème de résolution de contraintes pour un réseau donné
     *
     * @param r le réseau de contraintes à résoudre
     */
    public CSP(Network r) {
        network = r;
        solutions = new ArrayList<Assignment>();
        assignment = new Assignment();

    }

    /**
     * ******************** BACKTRACK UNE SOLUTION ******************************************
     */
    /**
     * Cherche une solution au réseau de contraintes
     *
     * @return une assignation solution du réseau, ou null si pas de solution
     */
    public Assignment searchSolution() {
        cptr = 1;

        // Implanter appel a backtrack
        Assignment sol = this.backtrack();
        System.out.println(cptr + " noeuds ont été explorés");
        return sol;
    }

    /**
     * Exécute l'algorithme de backtrack à la recherche d'une solution en
     * étendant l'assignation courante Utilise l'attribut assignment
     *
     * @return la prochaine solution ou null si pas de nouvelle solution
     */
    private Assignment backtrack() {
        if(this.assignment.getVars().size() == this.network.getVarNumber()){
            //Toutes les variables ont une assignation
            return this.assignment;
        }else{
            String var = this.chooseVar();
            for (Object value : this.tri(network.getDom(var))){
                this.assignment.put(var, value);
                this.cptr ++;
                if(this.consistant(var)){
                    return this.backtrack();
                }
                this.assignment.remove(var);
            }
            return null;
        }
    }

    /**
     * ******************** BACKTRACK TOUTES SOLUTIONS ******************************************
     */
    /**
     * Calcule toutes les solutions au réseau de contraintes
     *
     * @return la liste des assignations solution
     *
     */
    public ArrayList<Assignment> searchAllSolutions() {
        this.cptr = 1;
        this.solutions.clear();
        this.backtrackAll();
        System.out.println(cptr + " noeuds ont été explorés");
        return this.solutions;
    }

    /**
     * Exécute l'algorithme de backtrack à la recherche de toutes les solutions
     * étendant l'assignation courante
     *
     */
    private void backtrackAll() {
        if(this.assignment.getVars().size() == this.network.getVarNumber()){
            //Toutes les variables ont une assignation
            this.solutions.add(this.assignment.clone());
        }else{
            String var = this.chooseVar();
            for (Object value : this.tri(network.getDom(var))){
                this.assignment.put(var, value);
                this.cptr ++;
                if(this.consistant(var)){
                    this.backtrackAll();
                }
                this.assignment.remove(var);
            }
        }
    }

    /**
     * Retourne la prochaine variable à assigner étant donné assignment (qui
     * doit contenir la solution partielle courante)
     *
     * @return une variable non encore assignée
     */
    private String chooseVar() {
        //Choisir la premiere variable non assignee
        List<String> allVar = this.network.getVars();
        List<String> assignedVars = this.assignment.getVars();
        allVar.removeAll(assignedVars);
        if(!allVar.isEmpty()){
            return allVar.get(0);
        }else{
            throw new RuntimeException("chooseVar : plus de variable sans valeur");
        }
    }
    
    /**
     * Fixe un ordre de prise en compte des valeurs d'un domaine
     *
     * @param values une liste de valeurs
     * @return une liste de valeurs
     */
    private ArrayList<Object> tri(ArrayList<Object> values) {
        return values; // donc en l'état n'est pas d'une grande utilité !
    }

    /**
     * Teste si l'assignation courante stokée dans l'attribut assignment est
     * consistante, c'est à dire qu'elle ne viole aucune contrainte.
     *
     * @param lastAssignedVar la variable que l'on vient d'assigner à cette
     * étape
     * @return vrai ssi l'assignment courante ne viole aucune contrainte
     */
    private boolean consistant(String lastAssignedVar) {
        for(Constraint constraint : this.network.getConstraints(lastAssignedVar)){
            if(constraint.violation(this.assignment)) return false;
        }
        return true;
    }
}
